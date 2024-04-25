package com.hxt.backend.mapper;

import com.hxt.backend.entity.Image;
import org.apache.ibatis.annotations.*;
import com.hxt.backend.entity.Tag;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
public interface TagMapper {
    
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO tag (name)" + " VALUES (#{name})")
    int insertTag(String name);
    
    @Select("SELECT * from tag where tag_id = #{id}")
    @Results({
            @Result(column = "tag_id", property = "tag_id", id = true),
    })
    Tag getTag(Integer id);
    
    @Select("SELECT tag_id from tag where name = #{name}")
    @Results({
            @Result(column = "tag_id", property = "tag_id", id = true),
    })
    Tag getTagByName(String name);
    
    @Delete("DELETE FROM tag WHERE tag_id = #{id}")
    int deleteTag(Integer id);
}
