package com.hxt.backend.mapper;

import com.hxt.backend.entity.Image;
import com.hxt.backend.entity.MyResource;
import com.hxt.backend.entity.Tag;
import com.hxt.backend.entity.post.Comment;
import com.hxt.backend.entity.post.Reply;
import org.apache.ibatis.annotations.*;
import com.hxt.backend.entity.post.Post;

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
    
    //插入新帖子
    @Options(useGeneratedKeys = true, keyProperty = "post_id", keyColumn = "post_id")
    @Insert("INSERT INTO post (title, content, category, section_id, author_id, like_count, " +
            "collect_count, comment_count, view_count, time)" +
            " VALUES (#{title}, #{content}, #{category}, #{section_id}, #{authorId}, #{like_count}, " +
            "#{collect_count}, #{comment_count}, #{view_count}, #{postTime})")
    int insertPost(Post post);
    /*
    int insertPost(String title, String content, Integer category, Integer sectionId, Integer authorId,
                   Integer likeCount, Integer collectCount, Integer commentCount,Integer viewCount, Timestamp postTime);
    */
    
    
    //删除帖子
    @Delete("DELETE FROM post WHERE post_id = #{id}")
    int deletePost(Integer id);
    
    //获取帖子
    @Select("SELECT * from post where post_id = #{id}")
    @Results({
            @Result(column = "post_id", property = "post_id", id = true),
            @Result(column = "time", property = "postTime"),
            @Result(column = "author_id", property = "authorId")
    })
    Post getPost(Integer id);

    @Select("SELECT COUNT(*) FROM post")
    int getPostNum();

    @Select("SELECT COUNT(*) FROM post WHERE time > #{time}")
    int getPostNumRecent(Timestamp time);
    
    //更新帖子浏览数
    @Update("UPDATE post SET view_count = #{viewCount} WHERE post_id = #{id}")
    int updateViewCount(Integer id, Integer viewCount);
    
    //更新帖子评论数（含回复）
    @Update("UPDATE post SET reply_count = #{replyCount} WHERE post_id = #{id}")
    int updateCommentCount(Integer id, Integer replyCount);
    
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
    //评论
    
    //添加评论
    @Options(useGeneratedKeys = true, keyProperty = "comment_id", keyColumn = "comment_id")
    @Insert("INSERT INTO comment (post_id, author_id, content, time, reply_count, like_count) " +
            "VALUES (#{postId}, #{authorId}, #{content}, #{commentTime}, #{replyCount}, #{likeCount})")
    int insertComment(Comment comment);
    

    
    
    //删除评论
    @Delete("DELETE FROM comment WHERE comment_id = #{id}")
    int deleteComment(Integer id);
    
    //根据评论id获取评论
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
}
