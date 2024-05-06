package com.hxt.backend.mapper;

import com.hxt.backend.entity.progression.School;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface ProgressionMapper {
    
    //插入学校
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO school (name, intro, badge, web)" +
            " VALUES (#{name}, #{intro}, #{badge}, #{web})")
    int insertSchool(Integer userId, String keyword, Double preference, Timestamp time);
    
    
    //获取热门作者id(根据板块下发帖数、点赞数、评论数，取前20）
    @Select("SELECT author_id, (post_count + like_count + comment_count) AS activity_score\n" +
            "FROM (\n" +
            "    SELECT p.author_id, COUNT(*) AS post_count\n" +
            "    FROM post p\n" +
            "    WHERE p.section_id = #{sectionId}\n" +
            "    GROUP BY p.author_id\n" +
            ") AS post_counts\n" +
            "LEFT JOIN (\n" +
            "    SELECT p.author_id, COUNT(l.like_id) AS like_count\n" +
            "    FROM post p\n" +
            "    LEFT JOIN post_like l ON p.post_id = l.post_id\n" +
            "    WHERE p.section_id = #{sectionId}\n" +
            "    GROUP BY p.author_id\n" +
            ") AS like_counts ON post_counts.author_id = like_counts.author_id\n" +
            "LEFT JOIN (\n" +
            "    SELECT p.author_id, COUNT(c.comment_id) AS comment_count\n" +
            "    FROM post p\n" +
            "    LEFT JOIN comment c ON p.post_id = c.post_id\n" +
            "    WHERE p.section_id = #{sectionId}\n" +
            "    GROUP BY p.author_id\n" +
            ") AS comment_counts ON post_counts.author_id = comment_counts.author_id\n" +
            "ORDER BY activity_score DESC\n" +
            "LIMIT 20;")
    List<Integer> getHotAuthorId(Integer sectionId);
    
    
    
    // 获取全部学校
    @Select("SELECT * FROM school")
    List<School> getTotalSchool();
    
    //根据学校id获取学校
    @Select("SELECT * FROM school WHERE school_id = #{schoolId}")
    School getSchoolById(Integer school);
}
