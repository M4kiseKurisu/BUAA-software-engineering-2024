package com.hxt.backend.mapper;

import com.hxt.backend.entity.section.Section;
import com.hxt.backend.entity.section.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface SectionMapper {

    @Select("SELECT name FROM section WHERE section_id = #{id};")
    String getSectionNameById(int id);

    @Select("select * from section where section_id = #{id};")
    Section selectSectionById(int id);

    @Select("select * from section where name like '%#{name}%';")
    ArrayList<Section> selectSectionByName(String name);

    @Select("SELECT * FROM section;")
    ArrayList<Section> selectAllSection();

    @Select("select * from course_teacher where section_id = #{id};")
    ArrayList<Teacher> selectTeacherBySectionId(Integer id);

    @Select("select count(*) from post where section_id = #{id};")
    Integer getPostCountBySectionId(Integer id);

    @Select("select count(*) from section_follow where section_id = #{id};")
    Integer getFollowCountBySectionId(Integer id);
}
