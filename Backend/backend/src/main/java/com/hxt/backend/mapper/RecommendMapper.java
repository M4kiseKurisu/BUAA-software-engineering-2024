package com.hxt.backend.mapper;

import com.hxt.backend.entity.post.Post;
import com.hxt.backend.entity.recommend.*;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;
public interface RecommendMapper {
    
    
    //user_preference
    
    //插入用户偏好
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO user_preference (user_id, keyword, preference, time)" +
            " VALUES (#{userId}, #{keyword}, #{preference}, #{time})")
    int insertUserPreference(Integer userId, String keyword, Double preference, Timestamp time);
    
    //更新用户偏好
    @Update("UPDATE user_preference SET preference = preference + #{tfIdf} " +
            "WHERE user_id = #{userId} AND keyword = #{keyword}")
    int updateUserPreference(Integer userId, String keyword, Double tfIdf);
    
    //删除用户偏好
    @Delete("DELETE FROM user_preference WHERE user_id = #{userId} AND time < #{time} ")
    int deleteUserPreferenceByTime(Integer userId, Timestamp time);
    
    //按时间降序获取用户偏好
    @Select("SELECT * FROM user_preference WHERE user_id = #{userId} ORDER BY time DESC")
    List<UserPreference> getUserPreferenceByUserIdOrderByTime(Integer userId);
    
    //根据用户id和keyword获取用户偏好
    @Select("SELECT * FROM user_preference WHERE user_id = #{userId} AND keyword = #{keyword}")
    UserPreference getUserPreferenceBYUserIdKeyword(Integer userId, String keyword);
    
    @Select("SELECT keyword FROM user_preference WHERE user_id = #{userId}")
    List<String> getUserPreferenceBYUserId(Integer userId);
    
    
    
    
    
    
    // view_history
    
    // 新增历史记录
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO view_history (user_id, post_id, time) " +
            "VALUES (#{userId}, #{postId}, #{time})")
    int insertViewHistory(Integer userId, Integer postId, Timestamp time);
    
    //根据用户id和帖子id获取历史记录
    @Select("SELECT * FROM view_history WHERE user_id = #{userId} AND post_id = #{postId}")
    ViewHistory getViewHistoryBYUserIdPostId(Integer userId, Integer postId);
    
    //删除用户早于某时的浏览记录
    @Delete("DELETE FROM view_history WHERE user_id = #{userId} AND time < #{time} ")
    int deleteViewHistoryByTime(Integer userId, Timestamp time);
    
    //删除用户早于某时的浏览记录
    @Delete("DELETE FROM view_history WHERE view_history_id = #{viewHistoryId}")
    int deleteViewHistory(Integer viewHistoryId);
    
    //按时间降序获取用户浏览记录
    @Select("SELECT * FROM view_history WHERE user_id = #{userId} ORDER BY time DESC")
    List<ViewHistory> getViewHistoryByUserIdOrderByTime(Integer userId);
    
    //获取用户浏览过的帖子id
    @Select("SELECT post_id FROM view_history WHERE user_id = #{userId}")
    List<Integer> getViewedPostIdByUserId(Integer userId);
    
    //更新用户浏览记录（时间）
    @Update("UPDATE view_history SET time = #{time} " +
            "WHERE view_history_id = #{viewHistoryId}")
    int updateViewHistory(Integer viewHistoryId, Timestamp time);
    
    
    //插入帖子关键词
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO post_keyword (post_id, title_keyword, intro_keyword, content_keyword) " +
            "VALUES (#{postId}, #{titleKeyword}, #{introKeyword}, #{contentKeyword})")
    int insertPostKeyword(Integer postId, String titleKeyword, String introKeyword, String contentKeyword);
    
    //根据id 获取帖子关键词
    @Select("SELECT * from post_keyword where post_id = #{postId}")
    PostKeyword getKeywordByPostId(Integer postId);
}
