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
    @Insert("INSERT INTO resource (name, publisher_id, url, type, time, md5)" +
            " VALUES (#{name}, #{publisherId}, #{url}, #{type}, #{uploadTime}, #{md5})")
    int insertResource(String name, Integer publisherId, String url, String type, Timestamp uploadTime, String md5);
    
    
    //获取某用户发布的所有资源
    @Select("SELECT * from resource where publisher_id = #{id}")
    @Results({
            @Result(column = "resource_id", property = "resource_id", id = true),
            @Result(column = "time", property = "uploadTime")
    })
    List<MyResource> getResourceByPublisher(Integer id);
    
    //根据id获取资源
    @Select("SELECT * from resource where resource_id = #{id}")
    @Results({
        @Result(column = "resource_id", property = "resource_id", id = true),
        @Result(column = "time", property = "uploadTime")
    })
    MyResource getResource(Integer id);
    
    //根据url获取资源id
    @Select("SELECT resource_id from resource where url = #{url}")
    Integer getResourceIdByUrl(String url);
    
    //删除资源
    @Delete("DELETE FROM resource WHERE resource_id = #{id}")
    int deleteResource(Integer id);

    @Select("SELECT url FROM resource WHERE md5 = #{md5}")
    String getSameMd5ResourceUrl(String md5);
}
