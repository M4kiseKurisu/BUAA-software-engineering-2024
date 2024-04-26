package com.hxt.backend.mapper;

import com.hxt.backend.entity.Image;
import com.hxt.backend.entity.MyResource;
import com.hxt.backend.entity.Tag;
import com.hxt.backend.entity.post.*;
import org.apache.ibatis.annotations.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

// 数据库查询接口
public interface PostMapper {

    //  帖子统计信息
    @Select("SELECT COUNT(*) FROM post WHERE author_id = #{userId}")
    int getUserPostNum(int userId);

    //  回帖统计信息
    @Select("SELECT COUNT(*) FROM comment WHERE author_id = #{userId}")
    int getUserCommentNum(int userId);

    //  帖子点赞统计信息
    @Select("SELECT SUM(like_count) FROM post WHERE author_id = #{userId}")
    Integer getUserPostLikeNum(int userId);

    //  回帖点赞统计信息
    @Select("SELECT SUM(like_count) FROM comment WHERE author_id = #{userId}")
    Integer getUserCommentLikeNum(int userId);

    //  楼中楼点赞统计信息
    @Select("SELECT SUM(like_count) FROM reply WHERE author_id = #{userId}")
    Integer getUserReplyLikeNum(int userId);

    //插入新帖子
    @Options(useGeneratedKeys = true, keyProperty = "post_id", keyColumn = "post_id")
    @Insert("INSERT INTO post (title, intro, content, category, section_id, author_id, like_count, " +
            "collect_count, comment_count, view_count, time)" +
            " VALUES (#{title}, #{intro}, #{content}, #{category}, #{section_id}, #{author_id}, #{like_count}, " +
            "#{collect_count}, #{comment_count}, #{view_count}, #{postTime})")
    int insertPost(Post post);
    
    
    //删除帖子
    @Delete("DELETE FROM post WHERE post_id = #{id}")
    int deletePost(Integer id);
    
    //获取帖子
    @Select("SELECT * from post where post_id = #{id}")
    @Results({
            @Result(column = "post_id", property = "post_id", id = true),
            @Result(column = "time", property = "postTime"),
    })
    Post getPost(Integer id);
    
    @Select("SELECT category from post where post_id = #{id}")
    int getCategoryByPostId(Integer id);
    
    @Select("SELECT COUNT(*) FROM post")
    int getPostNum();

    @Select("SELECT COUNT(*) FROM post WHERE time > #{time}")
    int getPostNumRecent(Timestamp time);
    
    //更新帖子浏览数
    @Update("UPDATE post SET view_count = #{viewCount} WHERE post_id = #{id}")
    int updateViewCount(Integer id, Integer viewCount);
    
    //更新帖子评论数（含回复）
    @Update("UPDATE post SET comment_count = #{commentCount} WHERE post_id = #{id}")
    int updateCommentCount(Integer id, Integer commentCount);
    
    //更新帖子点赞数
    @Update("UPDATE post SET like_count = like_count + #{op} WHERE post_id = #{id}")
    int updatePostLikeCount(Integer id, Integer op);
    
    //帖子-图片
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post_image (post_id, image_id) VALUES (#{postId}, #{imageId})")
    int insertPostImage(Integer postId, Integer imageId);
    
    //获取某帖子的所有图片的id
    @Select("SELECT image_id from post_image where post_id = #{id} ORDER BY pi_id ASC")
    List<Integer> getImageIdByPost(Integer id);
    
    
    
    
    //帖子-资源
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post_resource (post_id, resource_id) VALUES (#{postId}, #{resourceId})")
    int insertPostResource(Integer postId, Integer resourceId);
    
    //获取某帖子的所有资源的id
    @Select("SELECT resource_id from post_resource where post_id = #{id} ORDER BY pr_id ASC")
    List<Integer> getResourceIdByPost(Integer id);
    
    
    
    //帖子-Tag
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post_tag (post_id, tag_id) VALUES (#{postId}, #{tagId})")
    int insertPostTag(Integer postId, Integer tagId);
    
    //获取某帖子的所有Tag的id
    @Select("SELECT tag_id from post_tag where post_id = #{id} ORDER BY pt_id ASC")
    List<Integer> getTagIdByPost(Integer id);

    @Select("SELECT name from tag join post_tag pt on tag.tag_id = pt.tag_id where post_id = #{id} ORDER BY pt_id")
    List<String> getTagNameByPost(Integer id);
    
    //帖子-点赞
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post_like (post_id, user_id, status, time) VALUES (#{postId}, #{userId}, #{status}, #{time})")
    int insertPostLike(Integer postId, Integer userId, Integer status, Timestamp time);
    
    //获取帖子点赞
    @Select("SELECT * from post_like where post_id = #{postId} and user_id = #{userId}")
    @Result(column = "time", property = "likeTime")
    PostLike getPostLike(Integer postId, Integer userId);
    
    //更新帖子点赞状态
    @Update("UPDATE post_like SET status = #{status} WHERE pl_id = #{id}")
    int updatePostLikeStatus(Integer id, Integer status);
    
    // 帖子-收藏
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO favorite (post_id, user_id, time) VALUES (#{postId}, #{userId}, #{time})")
    int insertPostFavorite(Integer postId, Integer userId, Timestamp time);
    
    // 帖子-取消收藏
    @Delete("DELETE FROM favorite WHERE post_id = #{postId} AND user_id = #{userId}")
    int deleteFavorite(Integer postId, Integer userId);
    
    //获取帖子收藏
    @Select("SELECT * from favorite where post_id = #{postId} and user_id = #{userId}")
    @Result(column = "time", property = "favoriteTime")
    Favorite getFavorite(Integer postId, Integer userId);

    //  获取所有收藏某帖子的用户id
    @Select("SELECT user_id from favorite where post_id = #{postId}")
    List<Integer> getFavoriteUsersByPost(Integer postId);
    
    //更新帖子收藏数
    @Update("UPDATE post SET collect_count = collect_count + #{op} WHERE post_id = #{id}")
    int updatePostFavoriteCount(Integer id, Integer op);

    //  以下为删除帖子时删除附加信息用
    @Delete("DELETE FROM post_like WHERE post_id = #{id}")
    int deletePostLike(Integer id);

    @Delete("DELETE FROM post_image WHERE post_id = #{id}")
    int deletePostImage(Integer id);

    @Delete("DELETE FROM post_resource WHERE post_id = #{id}")
    int deletePostResource(Integer id);

    @Delete("DELETE FROM post_tag WHERE post_id = #{id}")
    int deletePostTag(Integer id);

    @Delete("DELETE FROM favorite WHERE post_id = #{id}")
    int deletePostFavorite(Integer id);
    //评论
    
    //添加评论
    @Options(useGeneratedKeys = true, keyProperty = "comment_id", keyColumn = "comment_id")
    @Insert("INSERT INTO comment (post_id, author_id, content, time, reply_count, like_count) " +
            "VALUES (#{post_id}, #{author_id}, #{content}, #{commentTime}, #{reply_count}, #{like_count})")
    int insertComment(Comment comment);
    
    
    
    
    //删除评论
    @Delete("DELETE FROM comment WHERE comment_id = #{id}")
    int deleteComment(Integer id);
    
    //根据评论id获取帖子
    @Select("SELECT * from comment where comment_id = #{id}")
    @Result(column = "time", property = "commentTime")
    Comment getCommentById(Integer id);
    
    //根据评论id获取帖子id
    @Select("SELECT post_id from comment where comment_id = #{id}")
    Integer getPostIdByCommentId(Integer id);
    
    //获取某帖子的所有评论(正序)
    @Select("SELECT * from comment where post_id = #{id} ORDER BY time ASC, comment_id ASC")
    @Result(column = "time", property = "commentTime")
    List<Comment> getCommentSortByTimeAsc(Integer id);
    
    //获取某帖子的所有评论(倒序)
    @Select("SELECT * from comment where post_id = #{id} ORDER BY time DESC, comment_id DESC")
    @Result(column = "time", property = "commentTime")
    List<Comment> getCommentSortByTimeDesc(Integer id);
    
    //获取某帖子的所有评论(热度)
    @Select("SELECT * from comment where post_id = #{id} ORDER BY " +
            "(like_count + reply_count * 2) DESC, comment_id DESC")
    @Result(column = "time", property = "commentTime")
    List<Comment> getCommentSortByHotAsc(Integer id);

    @Select("SELECT COUNT(*) FROM comment")
    int getCommentNum();

    @Select("SELECT COUNT(*) FROM comment WHERE time > #{time}")
    int getCommentNumRecent(Timestamp time);
    
    //评论-图片
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO comment_image (comment_id, image_id) VALUES (#{commentId}, #{imageId})")
    int insertCommentImage(Integer commentId, Integer imageId);
    
    
    //获取某评论的所有图片的id
    @Select("SELECT image_id from comment_image where comment_id = #{id} ORDER BY ci_id ASC")
    List<Integer> getImageIdByComment(Integer id);
    
    //评论-资源
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO comment_resource (comment_id, resource_id) VALUES (#{commentId}, #{resourceId})")
    int insertCommentResource(Integer commentId, Integer resourceId);
    
    //获取某评论的所有资源的id
    @Select("SELECT resource_id from comment_resource where comment_id = #{id} ORDER BY cr_id ASC")
    List<Integer> getResourceIdByComment(Integer id);
    
    //评论-点赞
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO comment_like (comment_id, user_id, status, time) " +
            "VALUES (#{commentId}, #{userId}, #{status}, #{time})")
    int insertCommentLike(Integer commentId, Integer userId, Integer status, Timestamp time);
    
    //获取评论-点赞
    @Select("SELECT * from comment_like where comment_id = #{commentId} and user_id = #{userId}")
    CommentLike getCommentLike(Integer commentId, Integer userId);
    
    //更新评论点赞状态
    @Update("UPDATE comment_like SET status = #{status} WHERE cl_id = #{id}")
    int updateCommentLikeStatus(Integer id, Integer status);
    
    //更新评论回复数
    @Update("UPDATE comment SET reply_count = #{replyCount} WHERE comment_id = #{id}")
    int updateReplyCount(Integer id, Integer replyCount);
    
    //更新评论点赞数
    @Update("UPDATE comment SET like_count = like_count + #{op} WHERE comment_id = #{id}")
    int updateCommentLikeCount(Integer id, Integer op);

    //  以下为删除评论时删除附加信息用
    @Delete("DELETE FROM comment_like WHERE comment_id = #{id}")
    int deleteCommentLike(Integer id);

    @Delete("DELETE FROM comment_image WHERE comment_id = #{id}")
    int deleteCommentImage(Integer id);

    @Delete("DELETE FROM comment_resource WHERE comment_id = #{id}")
    int deleteCommentResource(Integer id);
    
    //回复
    
    //添加回复
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO reply (comment_id, replied_author_id, author_id, content, time, like_count) " +
            "VALUES (#{commentId}, #{repliedAuthorId}, #{authorId}, #{content}, #{replyTime}, #{likeCount})")
    int insertReply(Integer commentId, Integer repliedAuthorId, Integer authorId, String content,
                      Timestamp replyTime, Integer likeCount);
    
    @Delete("DELETE FROM reply WHERE reply_id = #{id}")
    int deleteReply(Integer id);
    
    
    
    //获取某评论的所有回复
    @Select("SELECT * from reply where comment_id = #{id} ORDER BY time ASC")
    @Result(column = "time", property = "replyTime")
    List<Reply> getReplyByCommentId(Integer id);
    
    //根据回复id获取回复
    @Select("SELECT * from reply where reply_id = #{id}")
    @Result(column = "time", property = "replyTime")
    Reply getReplyById(Integer id);
    
    //根据回复id获取评论id
    @Select("SELECT comment_id from reply where reply_id = #{id}")
    Integer getCommentIdByReplyId(Integer id);

    @Select("SELECT COUNT(*) FROM reply")
    int getReplyNum();

    @Select("SELECT COUNT(*) FROM reply WHERE time > #{time}")
    int getReplyNumRecent(Timestamp time);
    
    //回复-点赞
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO reply_like (reply_id, user_id, status, time) " +
            "VALUES (#{replyId}, #{userId}, #{status}, #{time})")
    int insertReplyLike(Integer replyId, Integer userId, Integer status, Timestamp time);
    
    //获取回复点赞
    @Select("SELECT * from reply_like where reply_id = #{replyId} and user_id = #{userId}")
    ReplyLike getReplyLike(Integer replyId, Integer userId);
    
    //更新回复点赞状态
    @Update("UPDATE reply_like SET status = #{status} WHERE rl_id = #{id}")
    int updateReplyLikeStatus(Integer id, Integer status);
    
    //更新回复点赞数
    @Update("UPDATE reply SET like_count = like_count + #{op} WHERE reply_id = #{id}")
    int updateReplyLikeCount(Integer id, Integer op);

    //  删除回复时删除所有点赞信息
    @Delete("DELETE FROM reply_like WHERE reply_id = #{id}")
    int deleteReplyLike(Integer id);
}
