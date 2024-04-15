package com.hxt.backend.mapper;

import com.hxt.backend.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user_info WHERE account = #{name}")
    @Results({
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "graduate_year", property = "graduateYear"),
            @Result(column = "token_time", property = "tokenTime")
    })
    User selectUserByName(String name);

    @Select("SELECT * FROM user_info WHERE user_id = #{id}")
    User selectUserById(Integer id);

    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO user_info (account, name, email, phonenum, major, graduate_year, password, token_time)" +
            " VALUES (#{name}, '', #{email}, #{phone}, #{major}, #{year}, #{password}, 0)")
    int insertUser(String name, String email, String phone,
                   String major, Integer year, String password);

    @Update("UPDATE user_info SET token = #{token}, token_time = #{time} WHERE user_id = #{id}")
    int setToken(Integer id, String token, Integer time);

    @Update("UPDATE user_info SET token = '', token_time = 0 WHERE user_id = #{id}")
    int resetToken(Integer id);
}
