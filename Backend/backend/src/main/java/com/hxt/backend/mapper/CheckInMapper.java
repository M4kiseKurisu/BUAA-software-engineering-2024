package com.hxt.backend.mapper;

import com.hxt.backend.entity.checkIn.CheckIn;
import com.hxt.backend.entity.checkIn.CheckInComment;
import com.hxt.backend.entity.checkIn.CheckInLike;
import com.hxt.backend.entity.progression.School;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface CheckInMapper {
    
    //获取用户的所有打卡
    @Select("SELECT * FROM check_in WHERE author_id = #{authorId} " +
            "order by check_in_id DESC")
    List<CheckIn> getCheckInByAuthorId(Integer authorId);

    @Select("SELECT * FROM check_in WHERE author_id = #{authorId} order by time desc")
    List<CheckIn> getCheckInByAuthorIdDesc(Integer authorId);

    //插入打卡
    //插入打卡图片
    @Options(useGeneratedKeys = true, keyProperty = "check_in_id", keyColumn = "check_in_id")
    @Insert("INSERT INTO check_in (content, author_id, time, like_count, authority)" +
            " VALUES (#{content}, #{author_id}, #{time}, #{like_count}, #{authority})")
    int insertCheckIn(CheckIn checkIn);
    
    
    //插入打卡图片
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO checkin_image (check_in_id, image_url)" +
            " VALUES (#{checkInId}, #{imageUrl})")
    int insertCheckInImage(Integer checkInId, String imageUrl);
    
    //根据打卡id获取打卡
    @Select("SELECT * FROM check_in WHERE check_in_id = #{checkInId}")
    CheckIn getCheckInById(Integer checkInId);
    
    //根据打卡id获取打卡图片
    @Select("SELECT image_url from checkin_image WHERE check_in_id = #{checkInId} " +
            "order by cii_id ASC")
    List<String> getImageByCheckInId(Integer checkInId);
    
    //获取打卡所有点赞者id
    @Select("SELECT user_id from checkin_like WHERE check_in_id = #{checkInId}")
    List<Integer> getFavorsIdByCheckInId(Integer checkInId);
    
    //获取打卡所有评论
    @Select("SELECT * from checkin_comment WHERE check_in_id = #{checkInId}")
    List<CheckInComment> getCommentByCheckInId(Integer checkInId);
    
    //获取所有打卡（时间倒序）
    @Select("select * from check_in where authority = 0 or authority is null or " +
            "(author_id = any (select follow_id from user_follow where user_id = #{user}) and authority = 1) " +
            "or author_id = #{user} order by time DESC")
    List<CheckIn> getAllCheckIn(Integer user);

    @Select("select * from check_in where (author_id = any (select follow_id from user_follow where user_id = #{user}) " +
            ") and authority != 2 or author_id = #{user} order by time desc")
    List<CheckIn> getFollowedCheckIn(Integer user);
    
    //查询是否点赞打卡
    @Select("select * from checkin_like " +
            "where check_in_id = #{checkInId} and user_id = #{userId}")
    CheckInLike getCheckInLike(Integer checkInId, Integer userId);
    
    //点赞打卡
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO checkin_like (check_in_id, user_id, time)" +
            " VALUES (#{checkInId}, #{userId}, #{time})")
    int insertCheckInLike(Integer checkInId, Integer userId, Timestamp time);
    
    //删除打卡点赞
    @Delete("DELETE FROM checkin_like WHERE check_in_id = #{checkInId} and user_id = #{userId}")
    int deleteCheckInLike(Integer checkInId, Integer userId);
    
    // 更新打卡点赞数
    @Update("UPDATE check_in SET like_count = like_count + #{op} WHERE check_in_id = #{id}")
    int updateCheckInLikeCount(Integer id, Integer op);
    
    //评论打卡
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO checkin_comment (content, author_id, check_in_id, time)" +
            " VALUES (#{content}, #{authorId}, #{checkInId}, #{time})")
    int insertCheckInComment(String content, Integer authorId, Integer checkInId, Timestamp time);
    
    //删除评论
    @Delete("DELETE FROM checkin_comment WHERE cic_id = #{commentId} and author_id = #{userId}")
    int deleteCheckInComment(Integer commentId, Integer userId);
    
    //删除打卡
    @Delete("DELETE FROM check_in WHERE check_in_id = #{checkInId} and author_id = #{userId}")
    int deleteCheckIn(Integer checkInId, Integer userId);
}
