package com.hxt.backend.controller;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.postResponse.*;
import com.hxt.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ImageService imageService;
    private final TagService tagService;
    private final ResourceService resourceService;
    private final UserService userService;
    private final RecommendService recommendService;
    private final ReviewService reviewService;
    private final FrequencyLogService frequencyLogService;
    private final String authorityError = "权限不匹配！";
    private final String hasEmptyResponse = "信息填写不完整！";
    private final String frequencyResponse = "操作太频繁了，休息一下再来吧！";
    
    //帖子详情
    @RequestMapping (value="/posts/post")
    public PostResponse showPost(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) throws IOException {
        if (user_id.equals("")) {
            return new PostResponse(false, null, null, null, null,
                    null, null, null, null, null, null,
                    null, null, null, null);
        }
        
        if (post_id == null) {
            Post post = null;
            PostResponse postResponse = new PostResponse(post);
            postResponse.setSuccess(false);
            return postResponse;
        }
        
        // 浏览数加1
        postService.updateViewCount(post_id);
        
        //获取帖子基本信息
        PostResponse postResponse = postService.getPost(post_id);
        
        //更新用户浏览偏好和浏览记录
        
        Map<String, Double> postTFIDF = recommendService.calculatePostTFIDF(post_id);
        recommendService.updateUserPreference(Integer.parseInt(user_id), postTFIDF);
        recommendService.updateViewHistory(Integer.parseInt(user_id), post_id);
        
        // 获取发帖者名字和头像
        List<String> nameAndHead = postService.getAuthorNameAndHead(post_id);
        String authorName = nameAndHead.get(0);
        String authorHead = nameAndHead.get(1);

        
        postResponse.setAuthor_name(authorName);
        postResponse.setAuthor_head(authorHead);
        
        // 获取帖子Tag
        List<String> tags = postService.getPostTag(post_id);
        postResponse.setTags(tags);
        
        //获取帖子图片
        /*
        List<String> images = postService.getPostImage(post_id);
        postResponse.setImages(images);
        */
        
        //获取帖子资源
        List<String> resources = postService.getPostResourceUrl(post_id);
        postResponse.setResources(resources);
        
        
        
        postResponse.setSuccess(true);
        return postResponse;
    }
    
    
    //获取帖子评论
    @RequestMapping (value="/posts/post/comments")
    public CommentListResponse showComment(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "comment_sort", required = false) Integer comment_sort,
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        
        if (user_id.equals("")) {
            return new CommentListResponse(false, null);
        }
    
        if (comment_sort == null) {
            comment_sort = 0;  // 默认时间升序
        }
        
        //获取评论
        List<CommentResponse> comments = postService.getPostComments(post_id, comment_sort, Integer.parseInt(user_id));
        return new CommentListResponse(true, comments);
    }
    
    //查看评论回复
    @RequestMapping (value="/posts/post/replies")
    public ReplyListResponse showReply(
            @RequestParam(name = "comment_id", required = false) Integer comment_id,
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        
        if (user_id.equals("")) {
            return new ReplyListResponse(false, null);
        }
        
        //获取回复
        List<ReplyResponse> replies = postService.getCommentReplies(comment_id, Integer.parseInt(user_id));
        return new ReplyListResponse(true, replies);
    }
    
    
    
    // 用户发帖
    @RequestMapping (value="/posts/write")
    public WritePostResponse writePost(
            @RequestParam(name = "section_id", required = false) Integer section_id,
            @RequestParam(name = "author_id", required = false) Integer author_id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "intro", required = false) String intro,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "tags[]", required = false) String[] tags,
            @RequestParam(name = "images[]", required = false) String[] images,
            @RequestParam(name = "resources[]", required = false) String[] resources
    ) throws IOException {
        //检查用户是否被封禁、检查用户操作频率
        if (userService.checkGlobalBlocked(author_id) || userService.checkSectionBlocked(author_id, section_id)) {
            return new WritePostResponse(false, "您已被封禁，禁止发帖！", null);
        } else if (frequencyLogService.checkFrequency(author_id)) {
            return new WritePostResponse(false, frequencyResponse, null);
        }
        
        //检查是否允许上传资源
        if (resources != null) {
            if (category == 0) {
                return new WritePostResponse(false, "请选择帖子类型为资源贴！", null);
            }
        }
    
    
        //后端检测内容字数
        Document doc = Jsoup.parse(content);
        Elements imgs = doc.select("img");
        imgs.remove();
        String text = doc.text();
        if (text.length() > 2000) {
            return new WritePostResponse(false, "内容上限为2000字！", null);
        }

        //审核帖子内容
        if (!reviewService.textReview(title + " " + intro + " " + content)) {
            return new WritePostResponse(false, "帖子内容违规", null);
        }
        
        //获取帖子封面
        String cover = null;
        if (images != null) {
            boolean flag = true;
            for (String imageUrl : images) {
                if (content.contains(imageUrl)) {
                    if (flag) {
                        cover = imageUrl;
                        flag = false;
                    }
                    //审核图片是否合规
                    if (!reviewService.imageReview(imageUrl)) {
                        return new WritePostResponse(false, "图片不合规", null);
                    }
                }
            }
        }
        
    
        //创建帖子并存入数据库
        Integer post_id = postService.createPost(title, intro, content, category, section_id, author_id, cover);
        if(post_id == -1) {
            return new WritePostResponse(false, "帖子内容不全", null);
        } else if (post_id == 0) {
            return new WritePostResponse(false, "发帖出错", null);
        }
        
        // 向 post_image表中插入数据
        if (images != null) {
            for (String imageUrl : images) {
                if (content.contains(imageUrl)) {
                    Integer image_id = imageService.getImageIdByUrl(imageUrl);
                    postService.postInsertImage(post_id, image_id);
                }
            }
        }
        
        // 向 post_resource表中插入数据
        if (resources != null) {
            for (String resourceUrl : resources) {
                Integer resource_id = resourceService.getResourceIdByUrl(resourceUrl);
                postService.postInsertResource(post_id, resource_id);
            }
        }
        
        //向 post_tag表插入数据
        if (tags != null) {
            for (String tagName : tags) {
                //审核tag
                /*
                if (!reviewService.textReview(tagName)) {
                    return new WritePostResponse(false, "tag违规", null);
                }
               
                 */
                Integer tagId = tagService.getIdByName(tagName);
                if (tagId == null) {
                    tagService.addTag(tagName);
                    tagId = tagService.getIdByName(tagName);
                }
                postService.postInsertTag(post_id, tagId);
            }
        }
        
        //向post_keyword中插入关键词
        recommendService.extractPostKeywords(post_id);
        
        frequencyLogService.setLog(author_id, 5);
        return new WritePostResponse(true, "发帖成功", post_id);
    }
    
    
    //用户删除帖子
    @RequestMapping (value="/posts/delete")
    public BasicInfoResponse deletePost(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new BasicInfoResponse(false, "信息不完整！");
        }
        Integer res = postService.deletePost(Integer.parseInt(user_id), post_id, false);
        
        if (res == -1) {
            return new BasicInfoResponse(false, "所选帖子不存在");
        } else if (res == -2) {
            return new BasicInfoResponse(false, authorityError);
        }
        
        return new BasicInfoResponse(true, "删帖成功");
    }
    
    
    //获取单个帖子简介
    @RequestMapping (value = "/posts/specify")
    public PostIntroResponse getSpecifyPost(
        @RequestParam(name = "post_id", required = false) Integer post_id,
        @CookieValue(name = "user_id", defaultValue = "") String userId
    ) {
        if (userId.equals("")) {
            return new PostIntroResponse(null);
        }
        
        return postService.getPostIntroByPostId(post_id);
    }
    
    @RequestMapping(value = "/posts/search")
    public SearchResponse searchPost(
            @CookieValue(name = "user_id", defaultValue = "") String userId,
            @RequestParam(name = "section_id", required = false) Integer section_id,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "sort", required = false) Integer sort,
            @RequestParam(name = "tag", required = false) String tag,
            @RequestParam(name = "type", required = false) Integer type
    ) {
        if (userId.equals("")) {
            return new SearchResponse(false,"用户未登录",null);
        }
    
        List<PostIntroResponse> list = postService.searchPost(section_id, keyword, sort, tag, type);
        
        if (list == null || list.isEmpty()) {
            return new SearchResponse(true,"未检索到响应结果",list);
        } else {
            return new SearchResponse(true,"检索成功",  list);
        }
        
    }
    
    //用户点赞帖子
    @RequestMapping (value="/posts/like")
    public StatusResponse likePost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id

    ) {
        if (user_id.equals("")) {
            return new StatusResponse(false,"用户未登录",null);
        }
        //更改点赞状态
        Integer status = postService.thumbPost(post_id, Integer.parseInt(user_id));
        
        String info = "";
        if (status == 1) {
            info = "点赞成功";
            //帖子点赞数 +1
            postService.updatePostLikeCount(post_id, 1);
        } else if (status == 0) {
            info = "取消点赞成功";
            //帖子点赞数 -1
            postService.updatePostLikeCount(post_id, -1);
        }
    
        return new StatusResponse(true, info, status);
    }
    
    //用户是否点赞帖子
    @RequestMapping (value="/posts/isLike")
    public IsLikeResponse isLikePost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id
    
    ) {
        if (user_id.equals("")) {
            return new IsLikeResponse(false);
        }
        //获取点赞状态
        Integer status = postService.postLikeStatus(post_id, Integer.parseInt(user_id));
        
        if (status == 1) {
            return new IsLikeResponse(true);
        }
        
        return new IsLikeResponse(false);
    }
    
    //用户收藏帖子
    @RequestMapping (value="/posts/favorite")
    public BasicInfoResponse favoritePost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id

    ) {
        if (user_id.equals("")) {
            return new BasicInfoResponse(false, "用户未登录");
        }
        //向favorite表中插入数据
        Integer status = postService.favoritePost(post_id, Integer.parseInt(user_id));
    
        String info = "";
        if (status == 1) {
            info = "收藏成功";
            //帖子收藏数 +1
            postService.updatePostFavoriteCount(post_id, 1);
        } else if (status == 0) {
            info = "已收藏该帖子";
            return  new BasicInfoResponse(false, info);
        }
    
        return new BasicInfoResponse(true, info);
    }
    
    //用户取消收藏帖子
    @RequestMapping (value="/posts/unfavorite")
    public BasicInfoResponse unfavoritePost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id

    ) {
        if (user_id.equals("")) {
            return new BasicInfoResponse(false, "用户未登录");
        }
        //从favorite表中删除数据
        Integer status = postService.unfavoritePost(post_id, Integer.parseInt(user_id));
    
        String info = "";
        if (status == 1) {
            info = "取消收藏成功！";
            //帖子收藏数 -1
            postService.updatePostFavoriteCount(post_id, -1);
        } else if (status == 0) {
            info = "未收藏该帖子！";
            return  new BasicInfoResponse(false, info);
        }
    
        return new BasicInfoResponse(true, info);
    }
    
    //获取帖子收藏状态
    @RequestMapping (value="/posts/isFavorite")
    public IsLikeResponse isFavoritePost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id

    ) {
        if (user_id.equals("")) {
            return new IsLikeResponse(false);
        }
        //获取帖子收藏状态
        Integer status = postService.postFavoriteStatus(post_id, Integer.parseInt(user_id));
        
        if (status == 1) {
            return new IsLikeResponse(true);
        }
    
        return new IsLikeResponse(false);
    }
    
    //用户发表评论
    @RequestMapping (value="/posts/comment")
    public BasicInfoResponse writeComment(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "author_id", required = false) Integer author_id,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "images[]", required = false) String[] images,
            @RequestParam(name = "resources[]", required = false) String[] resources
    ) throws IOException {
        //检查用户是否被封禁
        if (userService.checkGlobalBlocked(author_id)
                || userService.checkSectionBlocked(author_id, postService.getPostSection(post_id))) {
            return new BasicInfoResponse(false, "您已被封禁，禁止评论！");
        } else if (frequencyLogService.checkFrequency(author_id)) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        
        //检查是否允许上传资源
        if (resources != null) {
            Integer postCategory = postService.getCategoryByPostId(post_id);
            if (postCategory == 0) {
                return new BasicInfoResponse(false, "不允许上传资源");
            }
        }
        
        //审核评论是否合规
        if (!reviewService.textReview(content)) {
            return new BasicInfoResponse(false, "评论违规");
        }

        if (images != null) {
            for (String imageUrl : images) {
                if (content.contains(imageUrl)) {
                    //审核图片是否合规
                    if (!reviewService.imageReview(imageUrl)) {
                        return new BasicInfoResponse(false, "图片不合规");
                    }
                }
            }
        }
        
        Integer comment_id = postService.createComment(content, post_id, author_id);
        if(comment_id == -1) {
            return new BasicInfoResponse(false, "评论内容不全");
        } else if (comment_id == 0) {
            return new BasicInfoResponse(false, "发表评论出错");
        }
    
        // 向 comment_image表中插入数据
        if (images != null) {
            for (String imageUrl : images) {
                if (content.contains(imageUrl)) {
                    Integer image_id = imageService.getImageIdByUrl(imageUrl);
                    postService.commentInsertImage(comment_id, image_id);
                }
            }
        }
        
        // 向 post_resource表中插入数据
        if (resources != null) {
            for (String resourceUrl : resources) {
                if (content.contains(resourceUrl)) {
                    Integer resource_id = resourceService.getResourceIdByUrl(resourceUrl);
                    postService.commentInsertResource(comment_id, resource_id);
                }
            }
        }
        //帖子评论数 +1
        postService.updatePostCommentCount(post_id, 1);
        frequencyLogService.setLog(author_id, 6);
        
        return new BasicInfoResponse(true, "评论成功");
    }
    
    //删除评论
    @RequestMapping (value="/posts/comment/delete")
    public BasicInfoResponse deleteComment(
            @RequestParam(name = "comment_id", required = false) Integer comment_id,
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        
        if (user_id.equals("")) {
            return new BasicInfoResponse(false, "用户未登录");
        }
        
        Integer post_id = postService.getPostIdByCommentId(comment_id);
        Integer res = postService.deleteComment(false, Integer.parseInt(user_id), comment_id);
        
        if (res == -1) {
            return new BasicInfoResponse(false, "所选评论不存在");
        } else if (res == -2) {
            return new BasicInfoResponse(false, authorityError);
        }
    
        // 该帖子的评论数 -1
        postService.updatePostCommentCount(post_id, -res);
        
        return new BasicInfoResponse(true, "删除评论成功");
    }
    
    //用户点赞评论
    @RequestMapping (value="/posts/comment/like")
    public StatusResponse likeComment(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "comment_id", required = false) Integer comment_id
    
    ) {
        if (user_id.equals("")) {
            return new StatusResponse(false, "用户未登录", null);
        }
        //更改点赞状态
        Integer status = postService.thumbComment(comment_id, Integer.parseInt(user_id));
        
        String info = "";
        if (status == 1) {
            info = "点赞成功";
            //评论点赞数 +1
            postService.updateCommentLikeCount(comment_id, 1);
        } else if (status == 0) {
            info = "取消点赞成功";
            //评论点赞数 -1
            postService.updateCommentLikeCount(comment_id, -1);
        }
        
        return new StatusResponse(true, info, status);
    }
    
    //用户是否点赞评论
    @RequestMapping (value="/posts/comment/isLike")
    public IsLikeResponse isLikeComment(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "comment_id", required = false) Integer comment_id
    
    ) {
        if (user_id.equals("")) {
            return new IsLikeResponse(false);
        }
        //获取点赞状态
        Integer status = postService.commentLikeStatus(comment_id, Integer.parseInt(user_id));
        
        if (status == 1) {
            return new IsLikeResponse(true);
        }
        
        return new IsLikeResponse(false);
    }
    
    // 用户回复
    @RequestMapping (value="/posts/reply")
    public BasicInfoResponse writeReply(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "comment_id", required = false) Integer comment_id,
            @RequestParam(name = "replied_author_id", required = false) Integer replied_author_id,
            @RequestParam(name = "author_id", required = false) Integer author_id,
            @RequestParam(name = "content", required = false) String content
            
    ) throws IOException {
        //检查用户是否被封禁
        if (userService.checkGlobalBlocked(author_id)
                || userService.checkSectionBlocked(author_id, postService.getPostSection(post_id))) {
            return new BasicInfoResponse(false, "您已被封禁，禁止回复！");
        } else if (frequencyLogService.checkFrequency(author_id)) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        
        //审核回复是否违规
        if (!reviewService.textReview(content)) {
            return new BasicInfoResponse(false, "回复内容违规");
        }

        //向数据库插入 reply
        Integer res = postService.createReply(comment_id, replied_author_id, author_id, content);
        
        if(res == -1) {
            return new BasicInfoResponse(false, "回复失败");
        }
        
        // 该评论的回复数 +1
        postService.updateCommentReplyCount(comment_id, 1);
        
        // 该帖子的评论数 +1
        postService.updatePostCommentCount(post_id, 1);
        frequencyLogService.setLog(author_id, 7);
        return new BasicInfoResponse(true, "回复成功");
    }
    
    //用户删除回复
    @RequestMapping (value="/posts/reply/delete")
    public BasicInfoResponse deleteReply(
            @RequestParam(name = "reply_id", required = false) Integer reply_id,
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new BasicInfoResponse(false, "用户未登录");
        }
        
        Integer comment_id = postService.getCommentIdByReplyId(reply_id);
        Integer post_id = postService.getPostIdByCommentId(comment_id);
        Integer res = postService.deleteReply(false, Integer.parseInt(user_id), reply_id);
        
        if (res == -1) {
            return new BasicInfoResponse(false, "所选评论不存在");
        } else if (res == -2) {
            return new BasicInfoResponse(false, authorityError);
        }
    
        // 该评论的回复数 -1
        postService.updateCommentReplyCount(comment_id, -1);
    
        // 该帖子的评论数 -1
        postService.updatePostCommentCount(post_id, -1);
        
        return new BasicInfoResponse(true, "删除评论成功");
    }
    
    //用户点赞回复
    @RequestMapping (value="/posts/reply/like")
    public StatusResponse likeReply(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "reply_id", required = false) Integer reply_id
    
    ) {
        if (user_id.equals("")) {
            return new StatusResponse(false, "用户未登录", null);
        }
        //更改点赞状态
        Integer status = postService.thumbReply(reply_id, Integer.parseInt(user_id));
        
        String info = "";
        if (status == 1) {
            info = "点赞成功";
            //回复点赞数 +1
            postService.updateReplyLikeCount(reply_id, 1);
        } else if (status == 0) {
            info = "取消点赞成功";
            //回复点赞数 -1
            postService.updateReplyLikeCount(reply_id, -1);
        }
        
        return new StatusResponse(true, info, status);
    }
    
    //用户是否点赞回复
    @RequestMapping (value="/posts/reply/isLike")
    public IsLikeResponse isLikeReply(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "reply_id", required = false) Integer reply_id
    
    ) {
        if (user_id.equals("")) {
            return new IsLikeResponse(false);
        }
        //获取点赞状态
        Integer status = postService.replyLikeStatus(reply_id, Integer.parseInt(user_id));
        
        if (status == 1) {
            return new IsLikeResponse(true);
        }
        
        return new IsLikeResponse(false);
    }

    @RequestMapping("/posts/report")
    public BasicInfoResponse reportPost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "detail", required = false) String detail
    ) {
        if (user_id.isEmpty() || id == null || detail == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        boolean res = postService.reportPost(Integer.parseInt(user_id), id, detail);
        String info = res? "" : "服务器错误！";
        if (res) {
            frequencyLogService.setLog(Integer.parseInt(user_id), 0);
        }
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/comment/report")
    public BasicInfoResponse reportComment(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "detail", required = false) String detail
    ) {
        if (user_id.isEmpty() || id == null || detail == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        boolean res = postService.reportComment(Integer.parseInt(user_id), id, detail);
        String info = res? "" : "服务器错误！";
        if (res) {
            frequencyLogService.setLog(Integer.parseInt(user_id), 1);
        }
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/reply/report")
    public BasicInfoResponse reportReply(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "detail", required = false) String detail
    ) {
        if (user_id.isEmpty() || id == null || detail == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        boolean res = postService.reportReply(Integer.parseInt(user_id), id, detail);
        String info = res? "" : "服务器错误！";
        if (res) {
            frequencyLogService.setLog(Integer.parseInt(user_id), 2);
        }
        return new BasicInfoResponse(res, info);
    }
}
