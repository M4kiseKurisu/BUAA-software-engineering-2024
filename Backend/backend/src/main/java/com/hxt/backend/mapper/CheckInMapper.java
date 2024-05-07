package com.hxt.backend.mapper;

import com.hxt.backend.entity.checkIn.CheckIn;
import com.hxt.backend.entity.progression.School;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CheckInMapper {
    
    //获取用户的所有打卡
    @Select("SELECT * FROM check_in WHERE author_id = #{authorId}")
    List<CheckIn> getCheckInByAuthorId(Integer authorId);
    
    //根据打卡id获取所有图片url
    @Select("select i.url from image i " +
            "join checkin_image cii on cii.image_id = i.image_id " +
            "where cii.check_in_id = #{checkInId}")
    List<String> getUrlByCheckInId(Integer checkInId);
}
