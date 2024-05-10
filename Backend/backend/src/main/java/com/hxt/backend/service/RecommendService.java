package com.hxt.backend.service;


import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.recommend.PostKeyword;
import com.hxt.backend.entity.recommend.UserPreference;
import com.hxt.backend.entity.recommend.ViewHistory;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.postResponse.PostIntroResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import okhttp3.*;
import org.json.JSONObject;


import java.io.*;
@Service
@RequiredArgsConstructor
public class RecommendService {
    
    private static final int MAX_KEYWORDS = 1000;
    private static final int MAX_HISTORY = 300;
    private static final double TITLE_WEIGHT = 1.0;
    private static final double INTRO_WEIGHT = 0.5;
    private static final double CONTENT_WEIGHT = 2.0;
    private static final double TAG_WEIGHT = 0.8;
    
    public static final String API_KEY = "7gDdZD2zqPiLUIuCCy0v1KA1";
    public static final String SECRET_KEY = "MQejxu4vJUeW0SScDHEtyHgJutqqIUcz";
    
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
    
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
        List<Post> posts = postMapper.getAllPost();
        return calculateTFIDF(postId, posts);
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
                Map<String, Double> postTFIDF = calculateTFIDF(post.getPost_id(), posts);
                double similarityScore = calculateCosineSimilarity(userInterestTFIDF, postTFIDF);
                postScores.put(post, similarityScore);
            }
        }
        
        // 根据相似度排序帖子，推荐前20篇帖子
        List<Post> recommendedPosts = new ArrayList<>(postScores.keySet());
        recommendedPosts.sort((post1, post2) -> Double.compare(postScores.get(post2), postScores.get(post1)));
        if (recommendedPosts.isEmpty()) {
            for (int i = 0; i < viewedPosts.size() && i < 10; i++) {
                Post post = postMapper.getPost(viewedPosts.get(i));
                recommendedPosts.add(post);
            }
        }
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
    
    //提取帖子的关键词并存入表中
    public Integer extractPostKeywords(Integer postId) throws IOException {
        
        //读取停用词
        /*
        File file = new File("Backend/backend/src/main/java/com/hxt/backend/service/stopWords.txt");
        
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");//避免中文乱码
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        List<String> stopWordlist = new ArrayList<>();
        String stopWord = "";
        //逐行读取文本
        while ((stopWord = bufferedReader.readLine()) != null) {
            stopWordlist.add(stopWord);
        }
         */
        
        Post post = postMapper.getPost(postId);
        String title = post.getTitle();
        String intro = post.getIntro();
        String content = extractTextFromHtml(post.getContent());
        
        //关键词列表
        List<String> titleKeywords = extractKeywords(title);
        List<String> introKeywords = extractKeywords(intro);
        List<String> contentKeywords = extractKeywordBaidu(content, 10);
        if (contentKeywords.isEmpty()) {
            contentKeywords = extractKeywords(content);
        }
        //用逗号隔开的关键词
        StringBuilder titleKeywordSepByCommas = new StringBuilder();
        StringBuilder introKeywordSepByCommas = new StringBuilder();
        StringBuilder contentKeywordSepByCommas = new StringBuilder();
        for (String word : titleKeywords) {
            System.out.println(word);
                if (word.length() >= 2) {
                    titleKeywordSepByCommas.append(word);
                    titleKeywordSepByCommas.append(",");
                }
        }
        for (String word : introKeywords) {
            if (word.length() >= 2) {
                introKeywordSepByCommas.append(word);
                introKeywordSepByCommas.append(",");
            }
        }
        for (String word : contentKeywords) {
            if (word.length() >= 2) {
                contentKeywordSepByCommas.append(word);
                contentKeywordSepByCommas.append(",");
            }
        }
        
        return recommendMapper.insertPostKeyword(postId, titleKeywordSepByCommas.toString(),
                introKeywordSepByCommas.toString(), contentKeywordSepByCommas.toString());
        
    }
    
    
    // 获取帖子的TF-IDF特征
    private Map<String, Double> calculateTFIDF(Integer postId, List<Post> posts) {
        
        PostKeyword postKeyword = recommendMapper.getKeywordByPostId(postId);
        
        Map<String, Double> tfidfScores = new LinkedHashMap<>();
        
        if (postKeyword == null) {
            return tfidfScores;
        }
        
        String title = postKeyword.getTitle_keyword();
        String intro = postKeyword.getIntro_keyword();
        String content = postKeyword.getContent_keyword();
        
        /*
        List<String> tags = postMapper.getTagNameByPost(post.getPost_id());
         */
        
        // 提取关键词并合并
        Set<String> keywords = new HashSet<>();
        
        List<String> titleKeywords = Arrays.stream(title.split(",")).toList();
        List<String> introKeywords = Arrays.stream(intro.split(",")).toList();
        List<String> contentKeywords = Arrays.stream(content.split(",")).toList();
        
        
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
        Post post = postMapper.getPost(postId);
        // 计算每个关键词的 TF-IDF
        for (String keyword : keywords) {
            if (keyword != null && !keyword.equals("")) {
                double tf = calculateTF(post.getTitle(), keyword) * TITLE_WEIGHT +
                        calculateTF(post.getIntro(), keyword) * INTRO_WEIGHT +
                        calculateTF(extractTextFromHtml(post.getContent()), keyword) * CONTENT_WEIGHT;
            
            /*
            for (String tag : tags) {
                tf += calculateTF(tag, keyword) * TAG_WEIGHT;
            }
             */
    
                double idf = calculateIDF(keyword, posts);
                tfidfScores.put(keyword, tf * idf);
            }
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
        
        int size = 10;
        if (content.length() >= 400) {
            size = content.length() / 50;
        }
        
        List<String> keywords = HanLP.extractKeyword(content, size);
        
        return keywords;
    }
    
    private List<String> extractKeywordByHand(String content) {
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
    private double calculateTF(String text, String keyword) {
        List<String> words = extractKeywordByHand(text);
        if (words.isEmpty()) {
            return 0;
        }
        int keywordFrequency = StringUtils.countMatches(text, keyword);
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
    
    public List<String> extractKeywordBaidu(String text, Integer num) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "{\"text\":[\""+text+"\"],\"num\":"+num+"}");
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rpc/2.0/nlp/v1/txt_keywords_extraction?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        //System.out.println(response.body().string());
        JSONObject jsonObject = new JSONObject(response.body().string());
        
        List<String> words = new ArrayList<>();
        if (jsonObject.has("error_msg")) {
            return words;
        }
        
        JSONArray resultsArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject resultObject = resultsArray.getJSONObject(i);
            String word = resultObject.getString("word");
            words.add(word);
        }
        return words;
    }
    
    static String getAccessToken() throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }
}
