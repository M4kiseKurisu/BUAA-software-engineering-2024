package com.hxt.backend.mapper;

import com.hxt.backend.entity.message.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    @Options(useGeneratedKeys = true)
    @Insert("INSERT INTO manager_system_notice (title, content, is_public, receiver_id, publish_time, pushed)\n" +
            "VALUES (#{title}, #{content}, true, 0, NOW(), 0);")
    int sendSystemNoticeToAll(String title, String content);

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

    // 申请加入学习团体通知表部分

    @Select("SELECT * FROM apply_notice WHERE an_id = #{id};")
    ApplyNotice selectApplyNoticeById(Integer id);

    // 获取加入申请
    @Select("SELECT * FROM apply_notice WHERE processed = FALSE AND promoter_id = #{userId}")
    List<ApplyNotice> selectUnprocessedApplyNoticeByUserId(Integer userId);

    // 获取申请反馈
    @Select("select * from apply_notice where processed = true and user_id = #{userId};")
    List<ApplyNotice> selectApplyGroupFeedbackByUserId(Integer userId);

    // 修改申请结果
    @Update(" update apply_notice" +
            " set processed = true," +
            "    result = #{result}" +
            " where an_id = #{an_id};")
    int updateFeedBack(Integer an_id, Boolean result);

    // 插入新的申请
    @Options(useGeneratedKeys = true)
    @Insert("insert into apply_notice (group_id, user_id, content, promoter_id, processed, result) " +
            "values (#{groupId},#{user_id},#{content},#{promoter_id},false,false)")
    int insertApplyNotice(Integer groupId, Integer user_id, String content, Integer promoter_id);

    @Select("SELECT COUNT(*) FROM apply_notice WHERE group_id = #{groupId} AND user_id = #{userId};")
    int selectApplyCount(Integer groupId, Integer userId);


    // 回复通知表

    @Options(useGeneratedKeys = true)
    @Insert("insert into reply_notice (user_id, author_id, content, reply_time, reply_to_post, post_id, comment_id) " +
            "VALUES (#{userId},#{authorId},#{content},#{replyTime},#{replyToPost},#{postId},#{commentId});")
    int insertReplyNotice(Integer userId, Integer authorId, String content, Timestamp replyTime, Boolean replyToPost, Integer postId, Integer commentId);


    @Select("select * from reply_notice where user_id = #{userId};")
    List<ReplyNotice> selectReplyNoticeByUserId(Integer userId);


    // 新的通知表

    // 查看用户当前是否有新的通知
    @Select("select count(*) from new_notice where user_id = #{userId};")
    int selectNewNotice(Integer userId);

    // 清空用户通知
    @Delete("delete from new_notice where user_id = #{userId};")
    int deleteNewNotice(Integer userId);

    // 插入新的通知
    @Insert("insert into new_notice (user_id) values (#{userId});")
    int insertNewNotice(Integer userId);
}
