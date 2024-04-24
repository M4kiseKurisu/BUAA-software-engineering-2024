package com.hxt.backend.mapper;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.section.Section;
import com.hxt.backend.entity.section.Teacher;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface SectionMapper {

    @Select("SELECT name FROM section WHERE section_id = #{id};")
    String getSectionNameById(int id);

    @Select("select * from section where section_id = #{id};")
    Section selectSectionById(int id);

    @Select("select * from section where name like '%${name}%';")
    ArrayList<Section> selectSectionByName(String name);

    @Select("SELECT * FROM section;")
    ArrayList<Section> selectAllSection();

    @Select("select * from course_teacher where section_id = #{id};")
    ArrayList<Teacher> selectTeacherBySectionId(Integer id);

    @Select("select user_id from authority where section_id = #{id} and category like \"助教\";")
    ArrayList<Integer> selectAssistantBySectionId(Integer id);

    @Select("select count(*) from post where section_id = #{id};")
    Integer getPostCountBySectionId(Integer id);

    @Select("select count(*) from section_follow where section_id = #{id};")
    Integer getFollowCountBySectionId(Integer id);

    @Insert("insert into section_follow (user_id, section_id, time) VALUES (#{userId}, #{sectionId}, #{time});")
    Integer insertUserFocusSection(Integer userId, Integer sectionId, Timestamp time);

    @Delete("delete from section_follow where section_id = #{sectionId} and  user_id = #{userId};")
    Integer deleteUserFocusSection(Integer userId, Integer sectionId);

    @Select("select count(*) from section_follow where section_id = #{sectionId} and  user_id = #{userId};")
    Integer getUserSectionFocusState(Integer userId, Integer sectionId);

    @Select("select * from post where section_id = #{id};")
    @Results({
            @Result(column = "post_id", property = "post_id", id = true),
            @Result(column = "time", property = "postTime"),
            @Result(column = "author_id", property = "authorId")
    })
    ArrayList<Post> selectPostBySectionId(Integer id);

    //  供管理员使用
    @Select("SELECT COUNT(*) FROM section WHERE flag = 0")
    int getCourseNum();

    @Select("SELECT COUNT(*) FROM section WHERE flag = 1")
    int getSchoolNum();

    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO section (name, intro, flag, type, academy, credit, capacity) VALUES (#{name}, #{intro}, 0, " +
            "#{type}, #{academy}, #{credit}, #{capacity})")
    int insertCourse(String name, String intro, String type,
                      String academy, Integer credit, Integer capacity);

    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO section (name, intro, flag, school_category, web) VALUES (#{name}, #{intro}, 1, " +
            "#{category}, #{web})")
    int insertSchool(String name, String intro, String category, String web);
}
