package com.hxt.backend.controller;

import com.hxt.backend.entity.post.Comment;
import com.hxt.backend.entity.post.Post;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.DownloadResponse;
import com.hxt.backend.response.UploadResponse;
import com.hxt.backend.response.postResponse.CommentResponse;
import com.hxt.backend.response.postResponse.PostResponse;
import com.hxt.backend.service.ImageService;
import com.hxt.backend.service.ObsService;
import com.hxt.backend.service.PostService;
import com.hxt.backend.service.ResourceService;
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
    public BasicInfoResponse writePost(
            @RequestParam(name = "section_id", required = false) Integer section_id,
            @RequestParam(name = "author_id", required = false) Integer author_id
            //待完善
    ) {
        //待完善
        
        return new BasicInfoResponse(true, "发帖成功");
    }
    
    
    @RequestMapping (value="/posts/post/comment")
    public BasicInfoResponse writeComment(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "author_id", required = false) Integer author_id
            //待完善
    ) {
        //待完善
        
        return new BasicInfoResponse(true, "评论成功");
    }
    
    @RequestMapping (value="/posts/post/reply")
    public BasicInfoResponse writeReply(
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "comment_id", required = false) Integer comment_id,
            @RequestParam(name = "replied_anthor_id", required = false) Integer replied_author_id,
            @RequestParam(name = "author_id", required = false) Integer author_id
            //待完善
    ) {
        //待完善
        
        return new BasicInfoResponse(true, "回复成功");
    }
}
