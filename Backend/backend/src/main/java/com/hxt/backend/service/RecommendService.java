package com.hxt.backend.service;


import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.recommend.UserPreference;
import com.hxt.backend.entity.recommend.ViewHistory;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.postResponse.PostIntroResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendService {
    
    private static final int MAX_KEYWORDS = 1000;
    private static final int MAX_HISTORY = 300;
    private static final double TITLE_WEIGHT = 1.0;
    private static final double INTRO_WEIGHT = 0.5;
    private static final double CONTENT_WEIGHT = 2.0;
    private static final double TAG_WEIGHT = 0.8;
    
    @Resource
    private RecommendMapper recommendMapper;
    
    @Resource
    private PostMapper postMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private ImageMapper imageMapper;
    
    @Resource
    private SectionMapper sectionMapper;
    
    
    //更新用户偏好
    public void updateUserPreference(Integer userId, Map<String, Double> keywordTfIdf) {
        
        List<String> existKeywords = recommendMapper.getUserPreferenceBYUserId(userId);
        
        // 添加或更新关键词
        for (String keyword : keywordTfIdf.keySet()) {
            Double tfIdf = keywordTfIdf.get(keyword);
            if (!existKeywords.contains(keyword)) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                recommendMapper.insertUserPreference(userId, keyword, tfIdf, timestamp);
            } else {
                recommendMapper.updateUserPreference(userId, keyword, tfIdf);
            }
        }
    
        // 按时间降序获取用户当前关键词
        List<UserPreference> userPreferences = recommendMapper.getUserPreferenceByUserIdOrderByTime(userId);
        int currentSize = userPreferences.size();
        
        // 如果关键词数量超过上限，删除最旧的关键词
        if (currentSize > MAX_KEYWORDS) {
            // 删除最旧的关键词
            Timestamp oldestTime = userPreferences.get(MAX_KEYWORDS - 1).getTime();
            recommendMapper.deleteUserPreferenceByTime(userId, oldestTime);
        }
        
    }
    
    // 获取浏览帖子的TF-IDF特征
    public Map<String, Double> calculatePostTFIDF(Integer postId) {
        Post post = postMapper.getPost(postId);
        List<Post> posts = postMapper.getAllPost();
        return calculateTFIDF(post, posts);
    }
    
    
    // 基于内容的帖子推荐
    public List<PostIntroResponse> recommendPostsByContent(Integer userId) {
        Map<Post, Double> postScores = new HashMap<>();
        
        /*获取所有的帖子
        List<Post> posts = postMapper.getAllPost();
        */
        
        //获取升学版块所有帖子
        List<Post> posts = sectionMapper.selectPostBySectionId(0);
        
        //获取该用户浏览记录
        List<Integer> viewedPosts = recommendMapper.getViewedPostIdByUserId(userId);
        
        // 获取用户兴趣的 TF-IDF 特征
        Map<String, Double> userInterestTFIDF = new HashMap<>();
        List<UserPreference> userPreferences = recommendMapper.getUserPreferenceByUserIdOrderByTime(userId);
        for (UserPreference userPreference : userPreferences) {
            userInterestTFIDF.put(userPreference.getKeyword(), userPreference.getPreference());
        }
        
        // 计算用户兴趣与每个未浏览帖子的相似度
        for (Post post : posts) {
            if (!viewedPosts.contains(post.getPost_id())) {
                Map<String, Double> postTFIDF = calculateTFIDF(post, posts);
                double similarityScore = calculateCosineSimilarity(userInterestTFIDF, postTFIDF);
                postScores.put(post, similarityScore);
            }
        }
        
        // 根据相似度排序帖子，推荐前20篇帖子
        List<Post> recommendedPosts = new ArrayList<>(postScores.keySet());
        recommendedPosts.sort((post1, post2) -> Double.compare(postScores.get(post2), postScores.get(post1)));
        recommendedPosts.subList(0, Math.min(20, recommendedPosts.size()));
        
        // 返回推荐帖子
        List<PostIntroResponse> postIntroResponses = new ArrayList<>();
        
        for (Post post : recommendedPosts) {
            PostIntroResponse postIntroResponse = new PostIntroResponse(post);
            String authorName = userMapper.getUserNameById(post.getAuthor_id());
            List<String> tags = postMapper.getTagNameByPost(post.getPost_id());
            
            //第一张图url
            String imageUrl = null;
            List<Integer> imageIds = postMapper.getImageIdByPost(post.getPost_id());
            if (!imageIds.isEmpty()) {
                imageUrl = imageMapper.getImage(imageIds.get(0));
            }
    
            postIntroResponse.setAuthor_name(authorName);
            postIntroResponse.setTag_list(tags);
            postIntroResponse.setPost_photo(imageUrl);
            
        
            postIntroResponses.add(postIntroResponse);
        }
        
        return postIntroResponses;
    }
    
    
    //更新用户浏览记录
    public void updateViewHistory(Integer userId, Integer postId) {
        // 获取用户浏览记录
        ViewHistory viewHistory = recommendMapper.getViewHistoryBYUserIdPostId(userId, postId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        if (viewHistory == null) {
            recommendMapper.insertViewHistory(userId, postId, timestamp);
        } else {
            recommendMapper.updateViewHistory(viewHistory.getView_history_id(), timestamp);
        }
        
        
        // 按时间降序获取用户浏览记录
        List<ViewHistory> viewHistories = recommendMapper.getViewHistoryByUserIdOrderByTime(userId);
        int currentSize = viewHistories.size();
        
        // 如果浏览记录数量超过上限，删除最旧的浏览记录
        if (currentSize > MAX_HISTORY) {
            // 删除最早的浏览记录
            Integer viewHistoryId = viewHistories.get(currentSize - 1).getView_history_id();
            recommendMapper.deleteViewHistory(viewHistoryId);
        }
        
    }
    
    
    // 获取帖子的TF-IDF特征
    private Map<String, Double> calculateTFIDF(Post post, List<Post> posts) {
        Map<String, Double> tfidfScores = new LinkedHashMap<>();
        String title = post.getTitle();
        String intro = post.getIntro();
        String content = extractTextFromHtml(post.getContent());
        
        /*
        List<String> tags = postMapper.getTagNameByPost(post.getPost_id());
         */
        
        // 提取关键词并合并
        Set<String> keywords = new HashSet<>();
        
        List<String> titleKeywords = extractKeywords(title);
        List<String> introKeywords = extractKeywords(intro);
        List<String> contentKeywords = extractKeywords(content);
        
        
        keywords.addAll(titleKeywords);
        if (!introKeywords.isEmpty()) {
            keywords.addAll(introKeywords);
        }
        keywords.addAll(contentKeywords);
        
        /*
        for (String tag : tags) {
            keywords.addAll(extractKeywords(tag));
        }
        */
        
        
        // 计算每个关键词的 TF-IDF
        for (String keyword : keywords) {
            double tf = calculateTF(titleKeywords, keyword) * TITLE_WEIGHT +
                    calculateTF(introKeywords, keyword) * INTRO_WEIGHT +
                    calculateTF(contentKeywords, keyword) * CONTENT_WEIGHT;
            
            /*
            for (String tag : tags) {
                tf += calculateTF(tag, keyword) * TAG_WEIGHT;
            }
             */
            
            double idf = calculateIDF(keyword, posts);
            tfidfScores.put(keyword, tf * idf);
        }

        return tfidfScores;
    }
    
    // 计算两个向量的余弦相似度
    private double calculateCosineSimilarity(Map<String, Double> vector1, Map<String, Double> vector2) {
        double dotProduct = 0.0;
        double normVector1 = 0.0;
        double normVector2 = 0.0;
        
        for (Map.Entry<String, Double> entry : vector1.entrySet()) {
            String keyword = entry.getKey();
            double tfidf1 = entry.getValue();
            double tfidf2 = vector2.getOrDefault(keyword, 0.0);
            
            dotProduct += tfidf1 * tfidf2;
            normVector1 += Math.pow(tfidf1, 2);
            normVector2 += Math.pow(tfidf2, 2);
        }
        
        if (normVector1 == 0 || normVector2 == 0) {
            return 0.0; // 避免除以零
        }
        
        return dotProduct / (Math.sqrt(normVector1) * Math.sqrt(normVector2));
    }
    
    
    //从html里提取文本
    public String extractTextFromHtml(String htmlContent) {
        Document doc = Jsoup.parse(htmlContent);
    
        // 移除所有图片元素及其内容
        Elements imgs = doc.select("img");
        imgs.remove();
    
        // 提取纯文本内容
        String text = doc.text();
    
        return text;
    }
    
    //从文本中提取关键词
    private List<String> extractKeywords(String content) {
        if (content == null) {
            return new ArrayList<>();
        }
        
        List<Term> terms = HanLP.segment(content);
        // 定义需要保留的词性
        List<String> importantPosTags = Arrays.asList("n", "v"); // 名词和动词
        int minWordLength = 2; // 最小词语长度阈值
        
        // 过滤结果
        List<String> importantWords = new ArrayList<>();
        for (Term term : terms) {
            
            if (importantPosTags.contains(String.valueOf(term.nature.firstChar()))
                    && term.word.length() >= minWordLength) {
                importantWords.add(term.word.toLowerCase());
            }
        }
        
        return importantWords;
    }
    
    // 计算 TF（词频）
    private double calculateTF(List<String> words, String keyword) {
        if (words.isEmpty()) {
            return 0;
        }
        int keywordFrequency = Collections.frequency(words, keyword);
       
        return (double) keywordFrequency / words.size();
    }
    
    // 计算 IDF（逆文档频率）
    private double calculateIDF(String keyword, List<Post> posts) {
        int docsWithKeyword = countDocumentsContainingKeyword(keyword, posts);
        return Math.log((double) posts.size() / (docsWithKeyword + 1));
    }
    
    // 计算包含特定关键词的帖子数量
    private int countDocumentsContainingKeyword(String keyword, List<Post> posts) {
        int count = 0;
        for (Post post : posts) {
            String allText = post.getTitle() + " " + post.getIntro() + " " + extractTextFromHtml(post.getContent());
            
            /*
            List<String> tags = postMapper.getTagNameByPost(post.getPost_id());
            for (String tag : tags) {
                allText += " " + tag;
            }
             */
            
            if (allText.contains(keyword)) {
                count++;
            }
        }
        return count;
    }
    
    
}
