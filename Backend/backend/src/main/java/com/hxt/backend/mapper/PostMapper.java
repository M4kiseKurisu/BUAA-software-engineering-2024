package com.hxt.backend.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;

// 数据库查询接口
public interface PostMapper {

    @Select("SELECT COUNT(*) FROM post WHERE user_id = #{userId}")
    int getPostNum(int userId);
    
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post (title, content, category, section_id, publisher_id, like_count, " +
            "collect_count, comment_count, view_count, time)" +
            " VALUES (#{title}, #{content}, #{category}, #{sectionId}, #{publisherId}, #{likeCount}, " +
            "#{collectCount}, #{commentCount}, #{viewCount}, #{postTime})")
    int insertPost(String title, String content, String category, Integer sectionId, Integer publisherId,
                   Integer likeCount, Integer collectCount, Integer commentCount,Integer viewCount, Timestamp postTime);
}
