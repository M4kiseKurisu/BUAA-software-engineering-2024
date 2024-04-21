package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.message.ManagerNotice;
import com.hxt.backend.entity.message.PrivateChat;
import com.hxt.backend.entity.message.UserNotice;
import com.hxt.backend.mapper.MessageMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.messageResponse.*;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    @Resource
    MessageMapper messageMapper;

    @Resource
    UserMapper userMapper;

    public ArrayList<ChatElement> getChatList(Integer id) {
        ArrayList<ChatElement> list = new ArrayList<>();
        List<PrivateChat> elements = messageMapper.selectPrivateChatListByUserId(id);
        Collections.sort(elements, new Comparator<>() {
            @Override
            public int compare(PrivateChat chat1, PrivateChat chat2) {
                return chat1.getLast_message_time().compareTo(chat2.getLast_message_time());
            }
        });
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
        return list;
    }

    public ArrayList<PrivateElement> getPrivateMessage(Integer senderId, Integer receiverId) {
        ArrayList<PrivateElement> list = new ArrayList<>();
        return list;
    }

    public Boolean sendPrivateMessage(Integer senderId, Integer receiverId, String content) {

        return false;
    }

    public ArrayList<ApplyElement> getApplyMessage(Integer id) {
        ArrayList<ApplyElement> list = new ArrayList<>();
        return list;
    }

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
        Collections.sort(notices, new Comparator<>() {
            @Override
            public int compare(UserNotice notice1, UserNotice notice2) {
                return notice1.getPull_time().compareTo(notice2.getPull_time());
            }
        });

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
