package com.hxt.backend.mapper;

import org.apache.ibatis.annotations.Select;

// 数据库查询接口
public interface PostMapper {

    @Select("SELECT COUNT(*) FROM post WHERE user_id = #{userId}")
    int getPostNum(int userId);
}
