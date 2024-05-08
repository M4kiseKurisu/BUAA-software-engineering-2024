package com.hxt.backend.mapper;

import com.hxt.backend.entity.Report;
import com.hxt.backend.response.singleInfo.UserAuthorityInfo;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

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

    //  权限管理
    @Insert("INSERT INTO authority(section_id, user_id, category) VALUES (#{section}, #{id}, #{type})")
    int setAuthority(Integer id, Integer section, String type);

    @Select("SELECT authority_id FROM authority WHERE user_id = #{id} AND section_id = #{section}")
    Integer checkAuthority(Integer id, Integer section);

    @Select("SELECT category FROM authority WHERE user_id = #{id} AND section_id = #{section}")
    String checkAuthorityType(Integer id, Integer section);

    @Select("SELECT section_id, category FROM authority WHERE user_id = #{id}")
    @Results({
            @Result(column = "section_id", property = "section")
    })
    List<UserAuthorityInfo> getUserAuthorities(Integer id);

    @Delete("DELETE FROM authority WHERE user_id = #{id} AND section_id = #{section}")
    int deleteAuthority(Integer id, Integer section);

    @Update("UPDATE user_info SET global_authority = 1 WHERE user_id = #{id}")
    int setGlobalAuthority(Integer id);

    @Select("SELECT global_authority FROM user_info WHERE user_id = #{id}")
    Integer checkGlobalAuthority(Integer id);

    @Update("UPDATE user_info SET global_authority = 0 WHERE user_id = #{id}")
    int deleteGlobalAuthority(Integer id);

    //  举报管理
    @Select("SELECT report_id, user_id, target, detail, resource FROM report WHERE type = #{type} and active = 1")
    @Results({
            @Result(column = "report_id", property = "reportId"),
            @Result(column = "user_id", property = "userId")
    })
    List<Report> getUnhandledReports(Integer type);

    @Update("UPDATE report SET active = 0 WHERE report_id = #{id}")
    int handleReport(Integer id);
}
