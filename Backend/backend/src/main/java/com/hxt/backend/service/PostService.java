package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.post.*;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.postResponse.CommentResponse;
import com.hxt.backend.response.postResponse.PostResponse;
import com.hxt.backend.response.postResponse.ReplyResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private MessageMapper messageMapper;
    
    // 创建帖子
    public Integer createPost(String title, String intro, String content,
                              Integer category, Integer sectionId, Integer authorId) {
        if (title == null || intro == null || sectionId == null || authorId == null || category == null) {
            return -1;
        }
        Timestamp postTime = new Timestamp(System.currentTimeMillis());
        Post post = new Post(0, title, intro, content, category, sectionId, authorId,
                0, 0, 0, 0, postTime);
        Integer res = postMapper.insertPost(post);
        if (res == 0) {
            return 0;
        }
        return post.getPost_id();
    }
    
    
    
    
    public Integer deletePost(Integer userId, Integer postId) {
        Post p = postMapper.getPost(postId);
        if (p == null) {
            return -1;
        }
        //  检查权限
        if (!p.getAuthor_id().equals(userId) && !(adminMapper.checkGlobalAuthority(userId) > 0)
                && !(adminMapper.checkAuthority(userId, p.getSection_id()) > 0)) {
            return -2;
        }
        List<Comment> comments = postMapper.getCommentSortByTimeAsc(postId);
        List<Integer> favoriteIds = postMapper.getFavoriteUsersByPost(postId);
        for (Comment comment : comments) {
            deleteComment(true, 0, comment.getComment_id());
        }
        postMapper.deletePostImage(postId);
        postMapper.deletePostTag(postId);
        postMapper.deletePostLike(postId);
        postMapper.deletePostFavorite(postId);
        postMapper.deletePostResource(postId);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messageMapper.sendSystemNoticeToUser("删帖通知",
                String.format("您发表的帖子 “%s” 于 %s 被删除", p.getTitle(), formatter.format(date)),
                p.getAuthor_id());
        for (Integer id : favoriteIds) {
            messageMapper.sendSystemNoticeToUser("删帖通知",
                    String.format("您收藏的帖子 “%s” 于 %s 被删除", p.getTitle(), formatter.format(date)), id);
        }
        return postMapper.deletePost(postId);
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
        return postMapper.getPost(id).getAuthor_id();
    }
    
    public User getAuthor(Integer id) {
        Integer authorId = getAuthorId(id);
        User author = userMapper.selectUserById(authorId);
        return author;
    }
    
    public List<String> getAuthorNameAndHead(Integer id) {
        List<String> ans = new ArrayList<>();
        Integer authorId = getAuthorId(id);
        User author = userMapper.selectUserById(authorId);
        ans.add(author.getName());
        String headUrl = imageMapper.getImage(author.getHeadId());
        ans.add(headUrl);
        return ans;
    }
    
    
    
    
    public Integer postInsertImage(Integer postId, Integer imageId) {
        return postMapper.insertPostImage(postId, imageId);
    }
    
    public Integer postInsertTag(Integer postId, Integer tagId) {
        return postMapper.insertPostTag(postId, tagId);
    }
    
    public Integer postInsertResource(Integer postId, Integer resourceId) {
        return postMapper.insertPostResource(postId, resourceId);
    }
    
    public Integer getCategoryByPostId(Integer postId) {
        return postMapper.getCategoryByPostId(postId);
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
    public List<CommentResponse> getPostComments(Integer postId, Integer sort, Integer userId) {
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
            Integer authorId = comment.getAuthor_id();
            String authorName = userMapper.selectUserById(authorId).getName();
            String authorHead = imageMapper.getImage(userMapper.selectUserById(authorId).getHeadId());
            commentResponse.setComment_author_name(authorName);
            commentResponse.setComment_author_head(authorHead);
            
            //获取用户是否点赞评论
            Integer status = commentLikeStatus(comment.getComment_id(), userId);
            if (status == 1) {
                commentResponse.setComment_isLike(true);
            } else {
                commentResponse.setComment_isLike(false);
            }
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println(status);

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
            List<Reply> replies = postMapper.getReplyByCommentId(comment.getComment_id());
            List<ReplyResponse> replyResponses = new ArrayList<>();
            for (Reply reply : replies) {
                ReplyResponse replyResponse = new ReplyResponse(reply);
                
                //获取回复者的名称和头像
                Integer replyAuthorId = reply.getAuthor_id();
                String replyAuthorName = userMapper.selectUserById(replyAuthorId).getName();
                String replyAuthorHead = imageMapper.getImage(userMapper.selectUserById(replyAuthorId).getHeadId());
                replyResponse.setReply_author_name(replyAuthorName);
                replyResponse.setReply_author_head(replyAuthorHead);
                
                //获取用户是否点赞回复
                Integer replyStatus = replyLikeStatus(reply.getReply_id(), userId);
                if (replyStatus == 1) {
                    replyResponse.setReply_isLike(true);
                } else {
                    replyResponse.setReply_isLike(false);
                }
                
                //获取被评论用户名字
                Integer repliedAuthorId = reply.getReplied_author_id();
                String name = userMapper.getUserNameById(repliedAuthorId);
                replyResponse.setReplied_author_name(name);
                
                replyResponses.add(replyResponse);
            }
            commentResponse.setReplies(replyResponses);
            
            commentResponses.add(commentResponse);
        }
        
        return commentResponses;
    }
    
    //点赞帖子
    public Integer thumbPost(Integer postId, Integer user_id) {
        PostLike postLike = postMapper.getPostLike(postId, user_id);
        if (postLike == null) {
            Timestamp likeTime = new Timestamp(System.currentTimeMillis());
            postMapper.insertPostLike(postId, user_id, 1, likeTime);
            return 1;
        } else {
            Integer newStatus = 1 - postLike.getStatus();
            postMapper.updatePostLikeStatus(postLike.getPl_id(), newStatus);
            return newStatus;
        }
    }
    
    //获取帖子-点赞状态
    public Integer postLikeStatus(Integer postId, Integer user_id) {
        PostLike postLike = postMapper.getPostLike(postId, user_id);
        if (postLike == null) {
            return 0;
        } else {
            return postLike.getStatus();
        }
    }
    
    //更新帖子点赞数
    public Integer updatePostLikeCount(Integer postId, Integer op) {
        postMapper.updatePostLikeCount(postId, op);
        Post post = postMapper.getPost(postId);
        return post.getLike_count();
    }
    
    // 收藏帖子
    public Integer favoritePost(Integer postId, Integer user_id) {
        Favorite favorite = postMapper.getFavorite(postId, user_id);
    
        if (favorite != null) {
            return 0;
        } else {
            Timestamp favoriteTime = new Timestamp(System.currentTimeMillis());
            postMapper.insertPostFavorite(postId, user_id, favoriteTime);
            return 1;
        }
    }
    
    // 取消收藏帖子
    public Integer unfavoritePost(Integer postId, Integer user_id) {
        Favorite favorite = postMapper.getFavorite(postId, user_id);
        if (favorite != null) {
            postMapper.deleteFavorite(postId, user_id);
            return 1;
        }
        return 0;
    }
    
    // 获取帖子收藏状态
    public Integer postFavoriteStatus(Integer postId, Integer user_id) {
        Favorite favorite = postMapper.getFavorite(postId, user_id);
        if (favorite == null) {
            return 0;
        }
        return 1;
    }
    
    //更新帖子收藏数
    public Integer updatePostFavoriteCount(Integer postId, Integer op) {
        postMapper.updatePostFavoriteCount(postId, op);
        Post post = postMapper.getPost(postId);
        return post.getCollect_count();
    }
    
    
    //创建评论
    public Integer createComment(String content, Integer postId, Integer authorId) {
        if (postId == null || authorId == null) {
            return -1;
        }
        Timestamp commentTime = new Timestamp(System.currentTimeMillis());
        Comment comment = new Comment(0, postId, authorId, content, commentTime, 0, 0);
        Integer res = postMapper.insertComment(comment);
        if (res == 0) {
            return 0;
        }
        return comment.getComment_id();
    }
    
    //评论插图
    public Integer commentInsertImage(Integer commentId, Integer imageId) {
        return postMapper.insertCommentImage(commentId, imageId);
    }
    
    //评论插资源
    public Integer commentInsertResource(Integer commentId, Integer resourceId) {
        return postMapper.insertCommentResource(commentId, resourceId);
    }
    
    //删除评论
    public Integer deleteComment(boolean flag, Integer userId, Integer commentId) {
        Comment c = postMapper.getCommentById(commentId);
        Post p = postMapper.getPost(postMapper.getPostIdByCommentId(commentId));
        if (c == null) {
            return -1;
        }
        if (!flag && !c.getAuthor_id().equals(userId) && !(adminMapper.checkGlobalAuthority(userId) > 0)
                && !(adminMapper.checkAuthority(userId, p.getSection_id()) > 0)) {
            return -2;
        }
        List<Reply> replies = postMapper.getReplyByCommentId(commentId);
        for (Reply reply : replies) {
            deleteReply(true, 0, reply.getReply_id());
        }
        postMapper.deleteCommentLike(commentId);
        postMapper.deleteCommentImage(commentId);
        postMapper.deleteCommentResource(commentId);
        return postMapper.deleteComment(commentId) + replies.size();
    }
    
    //通过评论id获取帖子id
    public Integer getPostIdByCommentId(Integer commentId) {
        return postMapper.getPostIdByCommentId(commentId);
    }
    
    public void updateViewCount(Integer post_id) {
        Integer newViewCount = postMapper.getPost(post_id).getView_count() + 1;
        postMapper.updateViewCount(post_id, newViewCount);
    }
    
    public void updatePostCommentCount(Integer post_id, Integer op) {
        Integer newCommentCount = postMapper.getPost(post_id).getComment_count() + op;
        postMapper.updateCommentCount(post_id, newCommentCount);
    }
    
    public void updateCommentReplyCount(Integer comment_id, Integer op) {
        Integer newCommentCount = postMapper.getCommentById(comment_id).getReply_count() + op;
        postMapper.updateViewCount(comment_id, newCommentCount);
    }
    
    public Integer getCommentIdByReplyId(Integer replyId) {
        return postMapper.getCommentIdByReplyId(replyId);
    }
    
    //点赞评论
    public Integer thumbComment(Integer commentId, Integer user_id) {
        CommentLike commentLike = postMapper.getCommentLike(commentId, user_id);
        if (commentLike == null) {
            Timestamp likeTime = new Timestamp(System.currentTimeMillis());
            postMapper.insertCommentLike(commentId, user_id, 1, likeTime);
            return 1;
        } else {
            Integer newStatus = 1 - commentLike.getStatus();
            postMapper.updateCommentLikeStatus(commentLike.getCl_id(), newStatus);
            return newStatus;
        }
    }
    
    //获取评论-点赞状态
    public Integer commentLikeStatus(Integer commentId, Integer user_id) {
         CommentLike commentLike= postMapper.getCommentLike(commentId, user_id);
        if (commentLike == null) {
            return 0;
        } else {
            return commentLike.getStatus();
        }
    }
    
    //更新评论点赞数
    public Integer updateCommentLikeCount(Integer commentId, Integer op) {
        postMapper.updateCommentLikeCount(commentId, op);
        Comment comment = postMapper.getCommentById(commentId);
        return comment.getLike_count();
    }
    
    // 创建回复
    public Integer createReply(Integer commentId, Integer repliedAuthorId, Integer authorId, String content) {
        if (commentId == null  || repliedAuthorId == null || authorId == null) {
            return -1;
        }
        Timestamp replyTime = new Timestamp(System.currentTimeMillis());
        return postMapper.insertReply(commentId, repliedAuthorId, authorId, content, replyTime, 0);
    }
    
    public Integer deleteReply(boolean flag, Integer userId, Integer replyId) {
        Reply r = postMapper.getReplyById(replyId);
        if (r == null) {
            return -1;
        }
        Post p = postMapper.getPost(postMapper.getPostIdByCommentId(postMapper.getCommentIdByReplyId(replyId)));
        if (!flag && !r.getAuthor_id().equals(userId) && !(adminMapper.checkGlobalAuthority(userId) > 0)
                && !(adminMapper.checkAuthority(userId, p.getSection_id()) > 0)) {
            return -2;
        }
        postMapper.deleteReplyLike(replyId);
        return postMapper.deleteReply(replyId);
    }
    
    //点赞评论
    public Integer thumbReply(Integer replyId, Integer user_id) {
        ReplyLike replyLike = postMapper.getReplyLike(replyId, user_id);
        System.out.println(replyLike);
        System.out.println(replyLike.getStatus());
        System.out.println("&&&&&&&&&&&&&&&&&&&&&");
        if (replyLike == null) {
            Timestamp likeTime = new Timestamp(System.currentTimeMillis());
            postMapper.insertReplyLike(replyId, user_id, 1, likeTime);
            return 1;
        } else {
            Integer newStatus = 1 - replyLike.getStatus();
            postMapper.updateReplyLikeStatus(replyLike.getRl_id(), newStatus);
            return newStatus;
        }
    }
    
    //获取回复-点赞状态
    public Integer replyLikeStatus(Integer replyId, Integer user_id) {
        ReplyLike replyLike= postMapper.getReplyLike(replyId, user_id);
        if (replyLike == null) {
            return 0;
        } else {
            return replyLike.getStatus();
        }
    }
    
    //更新回复点赞数
    public Integer updateReplyLikeCount(Integer replyId, Integer op) {
        postMapper.updateReplyLikeCount(replyId, op);
        Reply reply = postMapper.getReplyById(replyId);
        return reply.getLike_count();
    }
    
}
