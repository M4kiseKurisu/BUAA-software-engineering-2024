package com.hxt.backend.mapper;

import org.apache.ibatis.annotations.*;
import com.hxt.backend.entity.Image;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
@Mapper
public interface ImageMapper {
    
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO image (url)" + " VALUES (#{url})")
    int insertImage(String url);
    
    @Select("SELECT image_id from image where url = #{url}")
    Integer getImageIdByUrl(String url);

    @Select("SELECT url from image where image_id = #{id}")
    String getImage(Integer id);
    
    @Select("SELECT * from image where image_id = #{id}")
    @Result(column = "image_id", property = "image_id", id = true)
    Image seekImage(Integer id);
    
    @Delete("DELETE FROM image WHERE image_id = #{id}")
    int deleteImage(Integer id);
}
