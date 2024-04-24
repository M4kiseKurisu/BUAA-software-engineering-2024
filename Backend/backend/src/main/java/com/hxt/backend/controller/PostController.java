package com.hxt.backend.controller;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.mapper.TagMapper;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.postResponse.*;
import com.hxt.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    
    //帖子详情
    @RequestMapping (value="/posts/post")
    public PostResponse showPost(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "comment_sort", required = false) Integer comment_sort
    ) {
        if (post_id == null) {
            Post post = null;
            PostResponse postResponse = new  PostResponse(post);
            postResponse.setSuccess(false);
            return postResponse;
        }
        
        if (comment_sort == null) {
            comment_sort = 0;  // 默认时间升序
        }
        // 浏览数加1
        postService.updateViewCount(post_id);
        
        //获取帖子基本信息
        PostResponse postResponse = postService.getPost(post_id);
        
        // 获取发帖者名字和头像
        String authorName = postService.getAuthorName(post_id);
        String authorHead = postService.getAuthorName(post_id);
        
        postResponse.setAuthor_name(authorName);
        postResponse.setAuthor_head(authorHead);
        
        // 获取帖子Tag
        List<String> tags = postService.getPostTag(post_id);
        postResponse.setTags(tags);
        
        //获取帖子图片
        List<String> images = postService.getPostImage(post_id);
        postResponse.setImages(images);
        
        //获取帖子资源
        Map<Integer, String> resources = postService.getPostResource(post_id);
        postResponse.setResources(resources);
        
        //获取帖子评论
        List<CommentResponse> comments = postService.getPostComments(post_id, comment_sort);
        postResponse.setComments(comments);
        
        postResponse.setSuccess(true);
        return postResponse;
    }
    
    // 用户发帖
    @RequestMapping (value="/posts/write")
    public WritePostResponse writePost(
            @RequestBody String info,
            @RequestParam(name = "section_id", required = false) Integer section_id,
            @RequestParam(name = "author_id", required = false) Integer author_id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "images", required = false) List<String> images,
            @RequestParam(name = "resources", required = false) List<String> resources
    ) {
        //检查用户是否被封禁
        if (userService.checkBlocked(author_id)) {
            return new WritePostResponse(false, "您已被封禁，禁止发帖！", null);
        }
        
        //检查是否允许上传资源
        if (resources != null) {
            if (category == 0) {
                return new WritePostResponse(false, "请选择帖子类型为资源贴！", null);
            }
        }

        //创建帖子并存入数据库
        System.out.println(info);
        Integer post_id = postService.createPost(title, content, category, section_id, author_id);
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
                if (content.contains(resourceUrl)) {
                    Integer resource_id = resourceService.getResourceIdByUrl(resourceUrl);
                    postService.postInsertResource(post_id, resource_id);
                }
            }
        }
        
        //向 post_tag表插入数据
        if (tags != null) {
            for (String tagName : tags) {
                Integer tagId;
                if (tagService.getIdByName(tagName) == null) {
                    tagService.addTag(tagName);
                }
                tagId = tagService.getIdByName(tagName);
                postService.postInsertTag(post_id, tagId);
            }
        }
        
        return new WritePostResponse(true, "发帖成功", post_id);
    }
    
    
    //用户删除帖子
    @RequestMapping (value="/posts/delete")
    public BasicInfoResponse deletePost(
            @RequestParam(name = "post_id", required = false) Integer post_id
            
    ) {
        Integer res = postService.deletePost(post_id);
        
        if (res == -1) {
            return new BasicInfoResponse(false, "所选帖子不存在");
        }
        
        return new BasicInfoResponse(true, "删帖成功");
    }
    
    //用户点赞帖子
    @RequestMapping (value="/posts/like")
    public StatusResponse likePost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id

    ) {
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
        //获取点赞状态
        Integer status = postService.postLikeStatus(post_id, Integer.parseInt(user_id));
        System.out.println("-----------");
        System.out.println(status);
        System.out.println(post_id);
        if (status == 1) {
            System.out.println("test");
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
            @RequestParam(name = "images", required = false) List<String> images,
            @RequestParam(name = "resources", required = false) List<String> resources
    ) {
        //检查用户是否被封禁
        if (userService.checkBlocked(author_id)) {
            return new BasicInfoResponse(false, "您已被封禁，禁止评论！");
        }
        
        //检查是否允许上传资源
        if (resources != null) {
            Integer postCategory = postService.getCategoryByPostId(post_id);
            if (postCategory == 0) {
                return new BasicInfoResponse(false, "不允许上传资源");
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
        
        return new BasicInfoResponse(true, "评论成功");
    }
    
    //删除评论
    @RequestMapping (value="/posts/comment/delete")
    public BasicInfoResponse deleteComment(
            @RequestParam(name = "comment_id", required = false) Integer comment_id
    
    ) {
        Integer post_id = postService.getPostIdByCommentId(comment_id);
        Integer res = postService.deleteComment(comment_id);
        
        if (res == -1) {
            return new BasicInfoResponse(false, "所选评论不存在");
        }
    
        // 该帖子的评论数 -1
        postService.updatePostCommentCount(post_id, -1);
        
        return new BasicInfoResponse(true, "删除评论成功");
    }
    
    //用户点赞评论
    @RequestMapping (value="/posts/comment/like")
    public StatusResponse likeComment(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "comment_id", required = false) Integer comment_id
    
    ) {
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
            
    ) {
        //检查用户是否被封禁
        if (userService.checkBlocked(author_id)) {
            return new BasicInfoResponse(false, "您已被封禁，禁止回复！");
        }
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(comment_id);
        System.out.println(replied_author_id);
        System.out.println(author_id);
        //向数据库插入 reply
        Integer res = postService.createReply(comment_id, replied_author_id, author_id, content);
        
        if(res == -1) {
            return new BasicInfoResponse(false, "回复失败");
        }
        
        // 该评论的回复数 +1
        postService.updateCommentReplyCount(comment_id, 1);
        
        // 该帖子的评论数 +1
        postService.updatePostCommentCount(post_id, 1);
        
        return new BasicInfoResponse(true, "回复成功");
    }
    
    //用户删除回复
    @RequestMapping (value="/posts/reply/delete")
    public BasicInfoResponse deleteReply(
            @RequestParam(name = "reply_id", required = false) Integer reply_id
    
    ) {
        Integer comment_id = postService.getCommentIdByReplyId(reply_id);
        Integer post_id = postService.getPostIdByCommentId(comment_id);
        Integer res = postService.deleteReply(reply_id);
        
        if (res == -1) {
            return new BasicInfoResponse(false, "所选评论不存在");
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
        //获取点赞状态
        Integer status = postService.replyLikeStatus(reply_id, Integer.parseInt(user_id));
        
        if (status == 1) {
            return new IsLikeResponse(true);
        }
        
        return new IsLikeResponse(false);
    }
    
}
