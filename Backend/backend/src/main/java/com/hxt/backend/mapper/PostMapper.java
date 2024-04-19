package com.hxt.backend.mapper;

import com.hxt.backend.entity.Post;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

// 数据库查询接口
public interface PostMapper {

    //  帖子(post)表
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post (title, content, category, section_id, publisher_id, like_count, " +
            "collect_count, comment_count, view_count, time)" +
            " VALUES (#{title}, #{content}, #{category}, #{sectionId}, #{publisherId}, #{likeCount}, " +
            "#{collectCount}, #{commentCount}, #{viewCount}, #{postTime})")
    int insertPost(String title, String content, String category, Integer sectionId, Integer publisherId,
                   Integer likeCount, Integer collectCount, Integer commentCount,Integer viewCount, Timestamp postTime);

    @Select("SELECT COUNT(*) FROM post WHERE publisher_id = #{userId}")
    int getUserPostNum(int userId);

    @Select("SELECT * FROM post WHERE post_id = #{postId}")
    @Results({
            @Result(column = "post_id", property = "postId", id = true),
            @Result(column = "like_count", property = "likeCount"),
            @Result(column = "collect_count", property = "collectCount"),
            @Result(column = "comment_count", property = "commentCount"),
            @Result(column = "section_id", property = "sectionId"),
            @Result(column = "publisher_id", property = "publisherId")
    })
    Post getPostById(int postId);

    //  回帖(comment)表
    @Select("SELECT COUNT(*) FROM comment WHERE user_id = #{id}")
    int getUserCommentNum(int id);

    //  tag表与帖子-tag表
    @Select("SELECT name FROM tag join post_tag pt on tag.tag_id = pt.tag_id WHERE post_id = #{postId}")
    List<String> getTagNames(int postId);
}
