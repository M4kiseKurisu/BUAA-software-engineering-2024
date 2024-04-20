package com.hxt.backend.mapper;

import com.hxt.backend.entity.Image;
import com.hxt.backend.entity.MyResource;
import com.hxt.backend.entity.Tag;
import com.hxt.backend.entity.post.Comment;
import com.hxt.backend.entity.post.Reply;
import org.apache.ibatis.annotations.*;
import com.hxt.backend.entity.post.Post;
import java.sql.Timestamp;
import java.util.List;

// 数据库查询接口
public interface PostMapper {

    @Select("SELECT COUNT(*) FROM post WHERE user_id = #{userId}")
    int getPostNum(int userId);
    
    
    //插入新帖子
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post (title, content, category, section_id, publisher_id, like_count, " +
            "collect_count, comment_count, view_count, time)" +
            " VALUES (#{title}, #{content}, #{category}, #{sectionId}, #{authorId}, #{likeCount}, " +
            "#{collectCount}, #{commentCount}, #{viewCount}, #{postTime})")
    int insertPost(String title, String content, Integer category, Integer sectionId, Integer authorId,
                   Integer likeCount, Integer collectCount, Integer commentCount,Integer viewCount, Timestamp postTime);
    
    //删除帖子
    @Delete("DELETE FROM post WHERE post_id = #{id}")
    int deletePost(Integer id);
    
    //获取帖子
    @Select("SELECT * from post where post_id = #{id}")
    Post getPost(Integer id);
    
    //更新帖子浏览数
    @Update("UPDATE post SET view_count = #{viewCount} WHERE post_id = #{id}")
    int updateViewCount(Integer id, Integer viewCount);
    
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
    int insertPR(Integer postId, Integer resourceId);
    
    //获取某帖子的所有资源的id
    @Select("SELECT resource_id from post_resource where post_id = #{id} ORDER BY pr_id ASC")
    List<Integer> getResourceIdByPost(Integer id);
    
    
    
    //帖子-Tag
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post_tag (post_id, tag_id) VALUES (#{postId}, #{tagId})")
    int insertPT(Integer postId, Integer tagId);
    
    //获取某帖子的所有Tag的id
    @Select("SELECT tag_id from post_tag where post_id = #{id} ORDER BY pt_id ASC")
    List<Integer> getTagIdByPost(Integer id);
    
    
    
    //评论
    
    //添加评论
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO comment (post_id, author_id, content, time, reply_count, like_count) " +
            "VALUES (#{postId}, #{authorId}, #{content}, #{commentTime}, #{replyCount}, #{likeCount})")
    int insertComment(Integer postId, Integer authorId, String content,
                      Timestamp commentTime, Integer replyCount, Integer likeCount);
    
    //删除评论
    @Delete("DELETE FROM comment WHERE comment_id = #{id}")
    int deleteComment(Integer id);
    
    //获取某帖子的所有评论(正序)
    @Select("SELECT * from comment where post_id = #{id} ORDER BY time ASC, comment_id ASC")
    List<Comment> getCommentSortByTimeAsc(Integer id);
    
    //获取某帖子的所有评论(倒序)
    @Select("SELECT * from comment where post_id = #{id} ORDER BY time DESC, comment_id DESC")
    List<Comment> getCommentSortByTimeDesc(Integer id);
    
    //获取某帖子的所有评论(热度)
    @Select("SELECT * from comment where post_id = #{id} ORDER BY " +
            "(like_count + reply_count * 2) DESC, comment_id DESC")
    List<Comment> getCommentSortByHotAsc(Integer id);
    
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
    List<Reply> getReplyByCommentId(Integer id);
    
}
