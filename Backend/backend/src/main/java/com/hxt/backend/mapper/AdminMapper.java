package com.hxt.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;

@Mapper
public interface AdminMapper {

    @Select("SELECT admin_id FROM admin WHERE name = #{name} and password = #{password}")
    Integer tryLogin(String name, String password);

    @Select("SELECT COUNT(*) FROM admin WHERE admin_id = #{id} and password = #{password}")
    int checkPassword(Integer id, String password);

    @Select("SELECT fail FROM admin WHERE name = #{name}")
    Integer checkFail(String name);

    @Select("SELECT lock_time FROM admin WHERE name = #{name}")
    Timestamp checkLock(String name);

    @Update("UPDATE admin set fail = #{fail} WHERE name = #{name}")
    int setFail(String name, Integer fail);

    @Update("UPDATE admin set lock_time = #{time} WHERE name = #{name}")
    int setLock(String name, Timestamp time);

    @Update("UPDATE admin SET password = #{password} WHERE admin_id = #{id}")
    int resetPassword(Integer id, String password);

}
