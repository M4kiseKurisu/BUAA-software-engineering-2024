package com.hxt.backend.controller;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.mapper.TagMapper;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.postResponse.CommentResponse;
import com.hxt.backend.response.postResponse.PostResponse;
import com.hxt.backend.response.postResponse.WritePostResponse;
import com.hxt.backend.service.ImageService;
import com.hxt.backend.service.PostService;
import com.hxt.backend.service.ResourceService;
import com.hxt.backend.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final ImageService imageService;
    private final TagService tagService;
    private final ResourceService resourceService;
    
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
    
    @RequestMapping (value="/posts/write")
    public WritePostResponse writePost(
            @RequestParam(name = "section_id", required = false) Integer section_id,
            @RequestParam(name = "author_id", required = false) Integer author_id,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "category", required = false) Integer category,
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "images", required = false) List<String> images,
            @RequestParam(name = "resources", required = false) List<String> resources
    ) {
        //创建帖子并存入数据库
        Integer post_id = postService.createPost(title, content, category, section_id, author_id);
        if(post_id == -1) {
            return new WritePostResponse(false, "帖子内容不全", null);
        } else if (post_id == 0) {
            return new WritePostResponse(false, "发帖出错", null);
        }
        
        // 向 post_image表中插入数据
        for (String imageUrl : images) {
            if (content.contains(imageUrl)) {
                Integer image_id = imageService.getImageIdByUrl(imageUrl);
                postService.postInsertImage(post_id, image_id);
            }
        }
    
        // 向 post_resource表中插入数据
        for (String resourceUrl : resources) {
            if (content.contains(resourceUrl)) {
                Integer resource_id = resourceService.getResourceIdByUrl(resourceUrl);
                postService.postInsertResource(post_id, resource_id);
            }
        }
        
        //向 post_tag表插入数据
    
        for (String tagName : tags) {
            Integer tagId;
            if (tagService.getIdByName(tagName) == null) {
                tagService.addTag(tagName);
            }
            tagId = tagService.getIdByName(tagName);
            postService.postInsertTag(post_id, tagId);
        }
        
        return new WritePostResponse(true, "发帖成功", post_id);
    }
    
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
    
    @RequestMapping (value="/posts/comment")
    public BasicInfoResponse writeComment(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "author_id", required = false) Integer author_id,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "images", required = false) List<String> images,
            @RequestParam(name = "resources", required = false) List<String> resources
    ) {
        Integer comment_id = postService.createComment(content, post_id, author_id);
        if(comment_id == -1) {
            return new BasicInfoResponse(false, "评论内容不全");
        } else if (comment_id == 0) {
            return new BasicInfoResponse(false, "发表评论出错");
        }
    
        // 向 comment_image表中插入数据
        for (String imageUrl : images) {
            if (content.contains(imageUrl)) {
                Integer image_id = imageService.getImageIdByUrl(imageUrl);
                postService.commentInsertImage(comment_id, image_id);
            }
        }
    
        // 向 post_resource表中插入数据
        for (String resourceUrl : resources) {
            if (content.contains(resourceUrl)) {
                Integer resource_id = resourceService.getResourceIdByUrl(resourceUrl);
                postService.commentInsertResource(comment_id, resource_id);
            }
        }
        
        //帖子评论数 +1
        postService.updatePostCommentCount(post_id, 1);
        
        return new BasicInfoResponse(true, "评论成功");
    }
    
    @RequestMapping (value="/posts/comment/delete")
    public BasicInfoResponse deleteComment(
            @RequestParam(name = "comment_id", required = false) Integer comment_id
    
    ) {
        Integer res = postService.deleteComment(comment_id);
        
        if (res == -1) {
            return new BasicInfoResponse(false, "所选评论不存在");
        }
        
        return new BasicInfoResponse(true, "删除评论成功");
    }
    
    @RequestMapping (value="/posts/reply")
    public BasicInfoResponse writeReply(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "comment_id", required = false) Integer comment_id,
            @RequestParam(name = "replied_author_id", required = false) Integer replied_author_id,
            @RequestParam(name = "author_id", required = false) Integer author_id,
            @RequestParam(name = "content", required = false) String content
            
    ) {
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
    
    /*
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
    
        // 该评论的回复数 +1
        postService.updateCommentReplyCount(comment_id, 1);
    
        // 该帖子的评论数 +1
        postService.updatePostCommentCount(post_id, 1);
        
        return new BasicInfoResponse(true, "删除评论成功");
    }
    */
}
