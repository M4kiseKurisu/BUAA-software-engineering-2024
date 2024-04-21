package com.hxt.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SectionMapper {

    @Select("SELECT name FROM section WHERE section_id = #{id}")
    String getSectionNameById(int id);
}
