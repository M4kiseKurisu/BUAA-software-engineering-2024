package com.hxt.backend.mapper;

import com.hxt.backend.entity.message.*;
import org.apache.ibatis.annotations.*;

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

    //  发送系统通知
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO manager_system_notice (title, content, is_public, receiver_id, publish_time, pushed)\n" +
            "VALUES (#{title}, #{content}, 0, #{receiver}, NOW(), 0);")
    int sendSystemNoticeToUser(String title, String content, Integer receiver);

    // 用户系统通知表

    // 插入新的通知
    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO user_system_notice (is_read, system_notice_id, receiver_id, pull_time)\n" +
            "VALUES (false, #{system_notice_id}, #{receiver_id}, #{pull_time});")
    int insertUserNotice(Integer system_notice_id, Integer receiver_id, Timestamp pull_time);

    // 根据用户id获取通知
    @Select("select * from user_system_notice where receiver_id = #{id};")
    List<UserNotice> getNoticeByUserId(Integer id);

    // 更新已读状态
    @Update("UPDATE user_system_notice SET is_read = true WHERE receiver_id = #{id};")
    int updateUserSystemNoticeIsRead(Integer id);

    // 私聊表部分

    // 获取用户私聊列表
    @Select("select * from private_chat where receiver_id = #{id} or sender_id = #{id};")
    List<PrivateChat> selectPrivateChatListByUserId(Integer id);

    @Select("select * from private_chat where receiver_id = #{id1} and sender_id = #{id2};")
    PrivateChat selectPrivateChatItem(Integer id1, Integer id2);

    // 发送新消息后更新私聊列表

    @Delete("delete from private_chat where sender_id = #{sender} and receiver_id = #{receiver};")
    int deletePrivateChatById(Integer sender, Integer receiver);

    @Options(useGeneratedKeys = true)
    @Insert("insert into private_chat (sender_id, receiver_id, last_message_content, last_message_time, is_read) " +
            "VALUES (#{sender}, #{receiver}, #{content}, #{time}, #{isRead})")
    int insertPrivateChatList(Integer sender, Integer receiver, String content, Timestamp time, boolean isRead);

    // 私信表部分

    // 获取用户间私信信息
    @Select("select * from private_message where (receiver_id = #{id1} and sender_id = #{id2}) or (receiver_id = #{id2} and sender_id = #{id1});")
    List<PrivateMessage> selectPrivateMessageListByUserId(Integer id1, Integer id2);

    // 发送新的私信
    @Options(useGeneratedKeys = true)
    @Insert("insert into private_message (content, sender_id, receiver_id, send_time, is_read) \n" +
            "values (#{content}, #{sender}, #{receiver}, #{time}, #{is_read})")
    int insertPrivateMessage(Integer sender, Integer receiver, Timestamp time, String content, boolean is_read);

    // 更新私信阅读状态
    @Update("update private_message set is_read = true where sender_id = #{sender} and receiver_id = #{receiver};")
    int updatePrivateMessageIsRead(Integer sender, Integer receiver);

    //  群聊信息表部分

    // 发送新的群聊消息
    @Options(useGeneratedKeys = true)
    @Insert("insert into group_message (content, group_id, send_id, time) VALUES (#{content},#{group_id},#{send_id},#{time});")
    int insertGroupMessage(String content, Integer group_id, Integer send_id, Timestamp time);

    //获取群聊消息
    @Select("select * from group_message where group_id = #{group_id};")
    List<GroupMessage> selectGroupMessageByGroupId(Integer group_id);
}
