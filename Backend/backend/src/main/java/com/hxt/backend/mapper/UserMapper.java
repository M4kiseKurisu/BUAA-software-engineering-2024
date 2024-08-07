package com.hxt.backend.mapper;

import com.hxt.backend.entity.User;
import com.hxt.backend.response.singleInfo.UserSectionBlockResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //  用户表
    @Select("SELECT * FROM user_info WHERE account = #{name}")
    @Results({
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "graduate_year", property = "graduateYear"),
            @Result(column = "token_time", property = "tokenTime"),
            @Result(column = "head_id", property = "headId")
    })
    User selectUserByAccount(String name);

    @Select("SELECT * FROM user_info WHERE name = #{name}")
    @Results({
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "graduate_year", property = "graduateYear"),
            @Result(column = "token_time", property = "tokenTime"),
            @Result(column = "head_id", property = "headId")
    })
    User selectUserByName(String name);

    @Select("SELECT user_id FROM user_info WHERE name like '%${name}%' ORDER BY LENGTH(name), token_time DESC")
    List<Integer> searchUserByName(String name);

    @Select("SELECT * FROM user_info WHERE user_id = #{id}")
    @Results({
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "graduate_year", property = "graduateYear"),
            @Result(column = "token_time", property = "tokenTime"),
            @Result(column = "head_id", property = "headId")
    })
    User selectUserById(Integer id);

    @Select("SELECT name FROM user_info WHERE user_id = #{id}")
    String getUserNameById(Integer id);

    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO user_info (account, name, email, phonenum, major, graduate_year, password," +
            " token_time, sign, global_authority, show_post, show_favorite)" +
            " VALUES (#{name}, #{name}, #{email}, #{phone}, #{major}, #{year}, #{password}, 0, 'Hello World!', 0, 1, 1)")
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
    
    @Update("UPDATE user_info SET head_id = #{headId} WHERE user_id = #{id}")
    int updateHead(Integer id, Integer headId);

    @Update("UPDATE user_info SET show_post = #{showPost} WHERE user_id = #{id}")
    int updateShowPost(Integer id, Integer showPost);

    @Update("UPDATE user_info SET show_favorite = #{showFavorite} WHERE user_id = #{id}")
    int updateShowFavorite(Integer id, Integer showFavorite);

    @Select("SELECT show_post FROM user_info WHERE user_id = #{id}")
    Integer checkUserShowPost(Integer id);

    @Select("SELECT show_favorite FROM user_info WHERE user_id = #{id}")
    Integer checkUserShowFavorite(Integer id);

    @Update("UPDATE user_info SET token = #{token}, token_time = #{time} WHERE user_id = #{id}")
    int setToken(Integer id, String token, Integer time);

    @Update("UPDATE user_info SET token = '', token_time = 0 WHERE user_id = #{id}")
    int resetToken(Integer id);

    @Update("UPDATE user_info SET password = #{password} WHERE user_id = #{id}")
    int resetPassword(Integer id, String password);

    @Select("select user_id from user_info order by user_id")
    List<Integer> selectAllUserId();

    @Select("SELECT COUNT(*) FROM user_info")
    int getUserNum();

    @Select("SELECT COUNT(*) FROM user_info WHERE token_time > #{time}")
    int getLoginNumRecent(Integer time);

    //  封禁操作
    @Update("UPDATE user_info SET block_point = NOW(), block_days = #{day} WHERE user_id = #{id}")
    int globalBlockUser(Integer id, Integer day);

    @Select("select timestampdiff(day, block_point, now()) < block_days from user_info where user_id = #{id}")
    Integer isGlobalBlocked(Integer id);

    @Update("UPDATE user_info SET block_days = 0 WHERE user_id = #{id}")
    int globalUnblockUser(Integer id);

    @Update("UPDATE section_block SET block_time = NOW(), block_days = #{days} " +
            "WHERE user_id = #{user} AND section_id = #{section}")
    int updateSectionBlock(Integer user, Integer section, Integer days);

    @Insert("INSERT INTO section_block (user_id, section_id, block_time, block_days) " +
            "VALUES (#{user}, #{section}, NOW(), #{days})")
    int sectionBlockUser(Integer user, Integer section, Integer days);

    @Select("select timestampdiff(day, block_time, now()) < block_days from section_block " +
            "where user_id = #{user} and section_id = #{section}")
    Integer isSectionBlocked(Integer user, Integer section);

    @Delete("DELETE FROM section_block WHERE user_id = #{user} and section_id = #{section}")
    int sectionUnblockUser(Integer user, Integer section);

    @Select("SELECT * from section_block WHERE TIMESTAMPDIFF(DAY, block_time, NOW()) < block_days order by user_id")
    @Results({
            @Result(column = "block_time", property = "block_timestamp")
    })
    List<UserSectionBlockResponse> getGlobalSectionBlockListOrderByUser();

    @Select("SELECT * from section_block WHERE TIMESTAMPDIFF(DAY, block_time, NOW()) < block_days order by section_id")
    @Results({
            @Result(column = "block_time", property = "block_timestamp")
    })
    List<UserSectionBlockResponse> getGlobalSectionBlockListOrderBySection();

    @Select("SELECT * from section_block WHERE TIMESTAMPDIFF(DAY, block_time, NOW()) < block_days order by block_time desc")
    @Results({
            @Result(column = "block_time", property = "block_timestamp")
    })
    List<UserSectionBlockResponse> getGlobalSectionBlockListOrderByTime();

    @Select("SELECT * from section_block WHERE TIMESTAMPDIFF(DAY, block_time, NOW()) < block_days AND section_id = #{section} order by user_id")
    @Results({
            @Result(column = "block_time", property = "block_timestamp")
    })
    List<UserSectionBlockResponse> getSectionBlockListOrderByUser(Integer section);

    @Select("SELECT * from section_block WHERE TIMESTAMPDIFF(DAY, block_time, NOW()) < block_days AND section_id = #{section} order by section_id")
    @Results({
            @Result(column = "block_time", property = "block_timestamp")
    })
    List<UserSectionBlockResponse> getSectionBlockListOrderBySection(Integer section);

    @Select("SELECT * from section_block WHERE TIMESTAMPDIFF(DAY, block_time, NOW()) < block_days AND section_id = #{section} order by block_time desc")
    @Results({
            @Result(column = "block_time", property = "block_timestamp")
    })
    List<UserSectionBlockResponse> getSectionBlockListOrderByTime(Integer section);

    //  用户关注表
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO user_follow (user_id, follow_id, time) VALUES (#{userId}, #{followId}, NOW())")
    int followUser(Integer userId, Integer followId);

    @Delete("DELETE FROM user_follow WHERE user_id = #{userId} AND follow_id = #{followId}")
    int unfollowUser(Integer userId, Integer followId);

    @Select("SELECT follow_id FROM user_follow WHERE user_id = #{id} ORDER BY uf_id DESC")
    List<Integer> getFollow(Integer id);    //  关注

    @Select("SELECT COUNT(*) FROM user_follow WHERE user_id = #{id}")
    int getFollowCount(Integer id);

    @Select("SELECT COUNT(*) FROM user_follow WHERE user_id = #{userId} and follow_id = #{followId}")
    int isFollow(Integer userId, Integer followId);

    @Select("SELECT user_id FROM user_follow WHERE follow_id = #{id} ORDER BY uf_id DESC")
    List<Integer> getFollower(Integer id);  //  被关注，备用

    @Select("SELECT COUNT(*) FROM user_follow WHERE follow_id = #{id}")
    int getFollowerCount(Integer id);

    //  用户收藏表
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO favorite (user_id, post_id) VALUES (#{userId}, #{postId})")
    int CollectPost(Integer userId, Integer postId);

    @Select("SELECT post_id FROM favorite WHERE user_id = #{userId} ORDER BY favorite_id DESC")
    List<Integer> getCollect(Integer userId);

    @Select("SELECT COUNT(*) FROM favorite WHERE post_id = #{postId}")
    int getCollectedCount(Integer postId);

    //板块关注表
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO section_follow (user_id, section_id) VALUES (#{userId}, #{sectionId})")
    int focusSection(Integer userId, Integer sectionId);

    @Select("SELECT section_id FROM section_follow WHERE user_id = #{userId} ORDER BY sf_id DESC")
    List<Integer> getFocus(Integer userId);

    @Select("SELECT COUNT(*) FROM section_follow WHERE section_id = #{sectionId}")
    int getFocusCount(Integer sectionId);

    //  用户帖子列表
    @Select("SELECT post_id FROM post WHERE author_id = #{userId} ORDER BY post_id DESC")
    List<Integer> getPosts(Integer userId);

    //  找回密码
    @Insert("INSERT INTO user_reset (user_id, code, user_time) VALUES (#{userId}, #{resetCode}, NOW())")
    int setResetCode(Integer userId, String resetCode);

    @Insert("SELECT ur_id FROM user_reset WHERE user_id = #{userId} " +
            "AND timestampdiff(minute, user_time, now()) < 1")
    Integer checkHasResetCode(Integer userId);

    @Select("SELECT ur_id FROM user_reset WHERE user_id = #{userId} AND code = #{resetCode} " +
            "AND timestampdiff(minute, user_time, now()) < 10")
    Integer checkResetCode(Integer userId, String resetCode);

    @Delete("DELETE FROM user_reset WHERE user_id = #{userId}")
    int deleteResetCode(Integer userId);
}
