package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.post.Comment;
import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.post.Reply;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.postResponse.CommentResponse;
import com.hxt.backend.response.postResponse.PostResponse;
import com.hxt.backend.response.postResponse.ReplyResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PostService {
    
    @Resource
    private ImageMapper imageMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private PostMapper postMapper;
    
    @Resource
    private ResourceMapper resourceMapper;
    
    @Resource
    private TagMapper tagMapper;
    
    public Integer addPost(String title, String content, Integer category, Integer sectionId, Integer authorId) {
        if (title == null  || sectionId == null || authorId == null || category == null) {
            return -1;
        }
        Timestamp postTime = new Timestamp(System.currentTimeMillis());
        return postMapper.insertPost(title, content, category, sectionId, authorId,
                0, 0, 0, 0, postTime);
    }
    
    public Integer deletePost(Integer id) {
        if (postMapper.getPost(id) == null) {
            return -1;
        }
        return postMapper.deletePost(id);
    }
    
    public PostResponse getPost(Integer id) {
        if (postMapper.getPost(id) == null) {
            return null;
        }
        return new PostResponse(postMapper.getPost(id));
    }
    
    public Integer getAuthorId(Integer id) {
        if (postMapper.getPost(id) == null) {
            return null;
        }
        return postMapper.getPost(id).getAuthorId();
    }
    
    public User getAuthor(Integer id) {
        Integer authorId = getAuthorId(id);
        User author = userMapper.selectUserById(authorId);
        return author;
    }
    
    public String getAuthorName(Integer id) {
        Integer authorId = getAuthorId(id);
        User author = userMapper.selectUserById(authorId);
        return author.getName();
    }
    
    
    // 获取帖子图片的url
    public List<String> getPostImage(Integer postId) {
        List<Integer> imageIds = postMapper.getImageIdByPost(postId);
        List<String> imageUrls = new ArrayList<>();
        for (Integer imageId : imageIds) {
            if (imageMapper.getImage(imageId) != null) {
                imageUrls.add(imageMapper.getImage(imageId));
            }
        }
        return imageUrls;
    }
    
    // 获取帖子资源的id和名称
    public Map<Integer, String> getPostResource(Integer postId) {
        List<Integer> resourceIds = postMapper.getResourceIdByPost(postId);
        Map<Integer, String> resourceMap = new LinkedHashMap<>();
        for (Integer resourceId : resourceIds) {
            if (resourceMapper.getResource(resourceId) != null) {
                resourceMap.put(resourceId, resourceMapper.getResource(resourceId).getName());
            }
        }
        return resourceMap;
    }
    
    // 获取帖子 tag 的名称
    public List<String> getPostTag(Integer postId) {
        List<Integer> tagIds = postMapper.getTagIdByPost(postId);
        List<String> tagNames = new ArrayList<>();
        for (Integer tagId : tagIds) {
            if (tagMapper.getTag(tagId) != null) {
                tagNames.add(tagMapper.getTag(tagId).getName());
            }
        }
        return tagNames;
    }
    
    // 获取帖子的评论
    public List<CommentResponse> getPostComments(Integer postId, Integer sort) {
        List<Comment> commentList;
        List<CommentResponse> commentResponses = new ArrayList<>();
        switch (sort) {
            case 0:
                commentList = postMapper.getCommentSortByTimeAsc(postId);
                break;
            case 1:
                commentList = postMapper.getCommentSortByHotAsc(postId);
                break;
            case 2:
                commentList = postMapper.getCommentSortByTimeDesc(postId);
                break;
            default:
                commentList = null;
        }
        for (Comment comment : commentList) {
            CommentResponse commentResponse = new CommentResponse(comment);
            
            //获取评论者的名称和头像
            Integer authorId = comment.getAuthorId();
            String authorName = userMapper.selectUserById(authorId).getName();
            String authorHead = imageMapper.getImage(userMapper.selectUserById(authorId).getHeadId());
            commentResponse.setComment_author_name(authorName);
            commentResponse.setComment_author_head(authorHead);
            
            //获取评论的图片
            List<Integer> imageIds = postMapper.getImageIdByComment(postId);
            List<String> imageUrls = new ArrayList<>();
            for (Integer imageId : imageIds) {
                if (imageMapper.getImage(imageId) != null) {
                    imageUrls.add(imageMapper.getImage(imageId));
                }
            }
            commentResponse.setComment_images(imageUrls);
            
            //获取评论的资源
            List<Integer> resourceIds = postMapper.getResourceIdByComment(postId);
            Map<Integer, String> resourceMap = new LinkedHashMap<>();
            for (Integer resourceId : resourceIds) {
                if (resourceMapper.getResource(resourceId) != null) {
                    resourceMap.put(resourceId, resourceMapper.getResource(resourceId).getName());
                }
            }
            commentResponse.setComment_resources(resourceMap);
            
            //获取评论的回复
            List<Reply> replies = postMapper.getReplyByCommentId(comment.getCommentId());
            List<ReplyResponse> replyResponses = new ArrayList<>();
            for (Reply reply : replies) {
                ReplyResponse replyResponse = new ReplyResponse(reply);
                
                //获取回复者的名称和头像
                Integer replyAuthorId = reply.getAuthorId();
                String replyAuthorName = userMapper.selectUserById(replyAuthorId).getName();
                String replyAuthorHead = imageMapper.getImage(userMapper.selectUserById(replyAuthorId).getHeadId());
                replyResponse.setReply_author_name(replyAuthorName);
                replyResponse.setReply_author_head(replyAuthorHead);
                replyResponses.add(replyResponse);
            }
            commentResponse.setReplies(replyResponses);
            
            commentResponses.add(commentResponse);
        }
        
        return commentResponses;
    }
    
    public void updateViewCount(Integer post_id) {
        Integer newViewCount = postMapper.getPost(post_id).getViewCount() + 1;
        postMapper.updateViewCount(post_id, newViewCount);
    }
}
