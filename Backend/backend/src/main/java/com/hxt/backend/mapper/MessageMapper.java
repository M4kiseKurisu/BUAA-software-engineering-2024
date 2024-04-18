package com.hxt.backend.mapper;

import com.hxt.backend.entity.message.ManagerNotice;
import com.hxt.backend.entity.message.UserNotice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface MessageMapper {
    // 管理员系统通知表部分

    // 获取未推送通知
    @Select("select * from manager_system_notice where pushed = false;")
    List<ManagerNotice> selectUnpushedNotice();

    // 更新推送状态
    @Update("UPDATE manager_system_notice SET pushed = true WHERE system_notice_id = #{systemNoticeId};")
    int updateManagerSystemNoticePushed(Integer systemNoticeId);

    @Select("select * from manager_system_notice where system_notice_id = #{id};")
    ManagerNotice selectManagerSystemNoticeById(Integer id);

    // 用户系统通知表

    // 插入新的通知
    @Insert("INSERT INTO user_system_notice (is_read, system_notice_id, receiver_id, pull_time)\n" +
            "VALUES (false, #{system_notice_id}, #{receiver_id}, #{pull_time});")
    int insertUserNotice(Integer system_notice_id, Integer receiver_id, Timestamp pull_time);

    // 根据用户id获取通知
    @Select("select * from user_system_notice where receiver_id = #{id};")
    List<UserNotice> getNoticeByUserId(Integer id);

    // 更新已读状态
    @Update("UPDATE user_system_notice SET is_read = true WHERE receiver_id = #{id};")
    int updateUserSystemNoticeIsRead(Integer id);

}
