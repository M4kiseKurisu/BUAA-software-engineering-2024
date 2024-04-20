package com.hxt.backend.mapper;
import com.hxt.backend.entity.MyResource;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

import java.sql.Timestamp;
import java.util.List;

public interface ResourceMapper {
    
    //插入资源
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO resource (name, publisher_id, url, type, time)" +
            " VALUES (#{name}, #{publisherId}, #{url}, #{type}, #{updateTime})")
    int insertResource(String name, Integer publisherId, String url, String type, Timestamp uploadTime);
    
    
    //获取某用户发布的所有资源
    @Select("SELECT * from resource where resource_id = #{id}")
    List<MyResource> getResourceByPublisher(Integer id);
    
    @Select("SELECT * from resource where resource_id = #{id}")
    MyResource getResource(Integer id);
    
    
    //删除资源
    @Delete("DELETE FROM resource WHERE resource_id = #{id}")
    int deleteResource(Integer id);
}