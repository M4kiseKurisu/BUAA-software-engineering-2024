package com.hxt.backend.mapper;
import com.hxt.backend.entity.Image;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ImageMapper {
    
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO image (url)" + " VALUES (#{url})")
    int insertImage(String url);
}
