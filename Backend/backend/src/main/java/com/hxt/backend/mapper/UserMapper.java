package com.hxt.backend.mapper;

import com.hxt.backend.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

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
    @Results({
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "graduate_year", property = "graduateYear"),
            @Result(column = "token_time", property = "tokenTime")
    })
    User selectUserById(Integer id);

    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO user_info (account, name, email, phonenum, major, graduate_year, password, token_time)" +
            " VALUES (#{name}, '', #{email}, #{phone}, #{major}, #{year}, #{password}, 0)")
    int insertUser(String name, String email, String phone,
                   String major, Integer year, String password);

    @Update("UPDATE user_info SET name = #{name} WHERE user_id = #{id}")
    int updateName(Integer id, String name);

    @Update("UPDATE user_info SET major = #{major} WHERE user_id = #{id}")
    int updateMajor(Integer id, String major);

    @Update("UPDATE user_info SET graduate_year = #{year} WHERE user_id = #{id}")
    int updateYear(Integer id, Integer year);

    @Update("UPDATE user_info SET sign = #{sign} WHERE user_id = #{id}")
    int updateSign(Integer id, String sign);

    @Update("UPDATE user_info SET phonenum = #{phone} WHERE user_id = #{id}")
    int updatePhone(Integer id, String phone);

    @Update("UPDATE user_info SET token = #{token}, token_time = #{time} WHERE user_id = #{id}")
    int setToken(Integer id, String token, Integer time);

    @Update("UPDATE user_info SET token = '', token_time = 0 WHERE user_id = #{id}")
    int resetToken(Integer id);

    @Update("UPDATE user_info SET password = #{password} WHERE user_id = #{id}")
    int resetPassword(Integer id, String password);

    @Select("select user_id from user_info;")
    List<Integer> selectAllUserId();
}
