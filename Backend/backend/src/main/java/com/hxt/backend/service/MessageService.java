package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.message.*;
import com.hxt.backend.mapper.MessageMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.group.GroupMessageElement;
import com.hxt.backend.response.messageResponse.*;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    @Resource
    MessageMapper messageMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    public ArrayList<ChatElement> getChatList(Integer id) {
        ArrayList<ChatElement> list = new ArrayList<>();
        List<PrivateChat> elements = messageMapper.selectPrivateChatListByUserId(id);
        elements.sort(Comparator.comparing(PrivateChat::getLast_message_time));
        for (PrivateChat element: elements) {
            if (element.getSender_id().equals(id)) {
                list.add(new ChatElement(element.getSender_id(),element.getReceiver_id(),
                        element.getLast_message_content(),
                        element.getLast_message_time().toString(), false));
            }
            else {
                list.add(new ChatElement(element.getSender_id(),element.getPrivate_chat_id(),
                        element.getLast_message_content(),
                        element.getLast_message_time().toString(), element.getIs_read()));
            }
        }

        List<Integer> follows = userMapper.getFollow(id);
        for (Integer follow: follows) {
            PrivateChat chat1 = messageMapper.selectPrivateChatItem(follow, id);
            PrivateChat chat2 = messageMapper.selectPrivateChatItem(id, follow);
            if (chat1 == null && chat2 == null){
                list.add(new ChatElement(id, follow,"","",false));
            }
        }
        return list;
    }

    public ArrayList<PrivateElement> getPrivateMessage(Integer senderId, Integer receiverId) {
        List<PrivateMessage> messages = messageMapper.selectPrivateMessageListByUserId(senderId,receiverId);
        messageMapper.updatePrivateMessageIsRead(receiverId,senderId);
        messages.sort(Comparator.comparing(PrivateMessage::getSend_time));
        ArrayList<PrivateElement> list = new ArrayList<>();
        for (PrivateMessage message: messages) {
            PrivateElement element = new PrivateElement();
            element.setId(message.getPrivate_message_id());
            element.setSender_id(message.getSender_id());
            element.setReceiver_id(message.getReceiver_id());
            element.setContent(message.getContent());
            element.setTime(message.getSend_time().toString());
            element.setIs_read(message.getIs_read());
            list.add(element);
        }
        return list;
    }

    public Boolean sendPrivateMessage(Integer senderId, Integer receiverId, String content, boolean is_read, Timestamp time) {
        messageMapper.insertPrivateMessage(senderId,receiverId,time,content,is_read);
        messageMapper.deletePrivateChatById(senderId,receiverId);
        messageMapper.deletePrivateChatById(receiverId,senderId);
        messageMapper.insertPrivateChatList(senderId,receiverId,content,time,is_read);
        return false;
    }

    public Boolean sendGroupMessage(Integer senderId, Integer groupId, String content, Timestamp time) {
        messageMapper.insertGroupMessage(content,groupId,senderId,time);
        return false;
    }

    public List<GroupMessageElement> getGroupMessage(Integer groupId) {
        List<GroupMessageElement> list = new ArrayList<>();
        List<GroupMessage> messages =  messageMapper.selectGroupMessageByGroupId(groupId);
        messages.sort(Comparator.comparing(GroupMessage::getTime));
        for (GroupMessage message: messages) {
            GroupMessageElement element = new GroupMessageElement();
            element.setId(message.getGroup_message_id());
            Integer sender = message.getSend_id();
            element.setSender_id(sender);
            element.setSender_name(userMapper.getUserNameById(sender));
            element.setSender_avatar(userService.getUserHead(sender));
            element.setContent(message.getContent());
            element.setTime(message.getTime().toString());
            list.add(element);
        }
        return list;
    }


    // todo
    public ArrayList<ApplyElement> getApplyMessage(Integer id) {
        ArrayList<ApplyElement> list = new ArrayList<>();
        return list;
    }
    // todo
    public ArrayList<ReplyElement> getReplyMessage(Integer id) {
        ArrayList<ReplyElement> list = new ArrayList<>();
        return list;
    }

    public ArrayList<NoticeElement> getNoticeMessage(Integer userId) {
        List<UserNotice> notices = messageMapper.getNoticeByUserId(userId);
        ArrayList<NoticeElement> list = new ArrayList<>();

        if (notices.isEmpty()) {
            return list;
        }

        messageMapper.updateUserSystemNoticeIsRead(userId);

        // 按照推送时间排序
        notices.sort(Comparator.comparing(UserNotice::getPull_time));

        for (UserNotice notice: notices) {
            ManagerNotice managerNotice = messageMapper.selectManagerSystemNoticeById(notice.getSystem_notice_id());
            User user = userMapper.selectUserById(notice.getReceiver_id());
            list.add(new NoticeElement(notice.getUser_notice_id(),user.getName(),
                    managerNotice.getTitle(), managerNotice.getContent(), notice.getPull_time().toString()));
        }
        return list;
    }

    // 每分钟执行一次，将管理员通知推送给每个用户
    @Scheduled(cron = "0 * * * * *")
    public void updateUserNotice() {
        List<ManagerNotice> unpushedNotices= messageMapper.selectUnpushedNotice();
        if (unpushedNotices.isEmpty()) {
            return;
        }
        List<Integer> allUserId = userMapper.selectAllUserId();
        for (ManagerNotice notice: unpushedNotices) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (notice.getIs_public()) {
                for (Integer id: allUserId) {
                    messageMapper.insertUserNotice(notice.getSystem_notice_id(), id, timestamp);
                }
            }
            else {
                messageMapper.insertUserNotice(notice.getSystem_notice_id(), notice.getReceiver_id(), timestamp);
            }
            messageMapper.updateManagerSystemNoticePushed(notice.getSystem_notice_id());
        }
    }

}
