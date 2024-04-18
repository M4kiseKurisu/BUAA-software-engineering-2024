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
}
