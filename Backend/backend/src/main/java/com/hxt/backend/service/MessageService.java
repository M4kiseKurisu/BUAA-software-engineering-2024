package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.group.GroupApply;
import com.hxt.backend.entity.message.*;
import com.hxt.backend.mapper.GroupMapper;
import com.hxt.backend.mapper.MessageMapper;
import com.hxt.backend.mapper.PostMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.group.GroupMessageElement;
import com.hxt.backend.response.messageResponse.*;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;

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

    @Resource
    GroupMapper groupMapper;

    @Resource
    GroupService groupService;

    @Resource
    PostMapper postMapper;

    //  私信、群聊内容解密用。勿动
    private final String headString = "$%^%^>:{!@#@))%^}";
    private final int[] move1 = {1, 2, 3};
    private final int[] move2 = {1, -2, 3, -4, 5};
    private final int[] move3 = {-1, 2, -3, 4};

    public ArrayList<ChatElement> getChatList(Integer id, boolean all) {
        resetNewNotice(id);
        ArrayList<ChatElement> list = new ArrayList<>();
        List<PrivateChat> elements = messageMapper.selectPrivateChatListByUserId(id);
        elements.sort(Comparator.comparing(PrivateChat::getLast_message_time).reversed());
        for (PrivateChat element: elements) {
            if (element.getSender_id().equals(id)) {
                list.add(new ChatElement(element.getSender_id(),element.getReceiver_id(),
                        decodeMessage(element.getLast_message_content()),
                        element.getLast_message_time().toString(), false));
            }
            else {
                list.add(new ChatElement(element.getSender_id(),element.getPrivate_chat_id(),
                        decodeMessage(element.getLast_message_content()),
                        element.getLast_message_time().toString(), element.getIs_read()));
            }
        }
        if (!all) {
            return list;
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
            element.setContent(decodeMessage(message.getContent()));
            element.setTime(message.getSend_time().toString());
            element.setIs_read(message.getIs_read());
            list.add(element);
        }
        return list;
    }

    public Boolean sendPrivateMessage(Integer senderId, Integer receiverId, String content, boolean is_read, Timestamp time) {
        insertNewNotice(receiverId);
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
            element.setContent(decodeMessage(message.getContent()));
            element.setTime(message.getTime().toString());
            list.add(element);
        }
        return list;
    }

    public BasicInfoResponse updateApply(Integer applyId, boolean result) {
        ApplyNotice apply = messageMapper.selectApplyNoticeById(applyId);
        if (result) {
            if (!groupService.joinGroup(apply.getUser_id(),apply.getGroup_id())) {
                return new BasicInfoResponse(false,"用户已在群体中");
            }
        }
        insertNewNotice(apply.getUser_id());
        messageMapper.updateFeedBack(applyId,result);
        return new BasicInfoResponse(true,"");
    }

    public ArrayList<ApplyElement> getApplyMessage(Integer userId) {
        resetNewNotice(userId);
        ArrayList<ApplyElement> list = new ArrayList<>();
        // 获取申请反馈
        List<ApplyNotice> notices = messageMapper.selectApplyGroupFeedbackByUserId(userId);
        for (ApplyNotice notice: notices) {
            ApplyElement element = new ApplyElement();

            element.setApply_id(notice.getAn_id());
            element.setUser_id(notice.getUser_id());
            element.setGroup_id(notice.getGroup_id());
            element.setGroup_leader_id(notice.getPromoter_id());
            element.setApply_title("申请反馈");
            element.setApply_content(notice.getContent());
            element.setIs_apply_feedback(true);
            element.setApply_feedback_info(notice.getResult());
            element.setGroup_name(groupMapper.selectGroupById(notice.getGroup_id()).getName());
            element.setUser_name(userMapper.getUserNameById(notice.getUser_id()));
            element.setUser_avatar(userService.getUserHead(notice.getUser_id()));

            list.add(element);
        }
        // 待处理申请
        notices = messageMapper.selectUnprocessedApplyNoticeByUserId(userId);
        for (ApplyNotice notice: notices) {
            ApplyElement element = new ApplyElement();

            element.setApply_id(notice.getAn_id());
            element.setUser_id(notice.getUser_id());
            element.setGroup_id(notice.getGroup_id());
            element.setGroup_leader_id(notice.getPromoter_id());
            element.setApply_title("加入群体申请");
            element.setApply_content(notice.getContent());
            element.setIs_apply_feedback(false);
            element.setApply_feedback_info(false);
            element.setGroup_name(groupMapper.selectGroupById(notice.getGroup_id()).getName());
            element.setUser_name(userMapper.getUserNameById(notice.getUser_id()));
            element.setUser_avatar(userService.getUserHead(notice.getUser_id()));

            list.add(element);
        }

        return list;
    }

//    @Scheduled(cron = "0 * * * * *")
//    public void updateGroupApply() {
//        List<GroupApply> applies = groupMapper.selectUnpushedApply();
//        for (GroupApply apply: applies) {
//            Integer groupId = apply.getGroup_id();
//            Integer userId = apply.getUser_id();
//            String content = apply.getContent();
//            Integer promoterId = groupMapper.selectPromoterIdByGroupId(groupId);
//            messageMapper.insertApplyNotice(groupId, userId, content, promoterId);
//            groupMapper.updateApplyPushState(apply.getAg_id());
//        }
//    }


    public ArrayList<ReplyElement> getReplyMessage(Integer userId) {
        resetNewNotice(userId);
        ArrayList<ReplyElement> list = new ArrayList<>();
        List<ReplyNotice> notices = messageMapper.selectReplyNoticeByUserId(userId);
        Collections.reverse(notices);
        for (ReplyNotice notice: notices) {
            ReplyElement element = new ReplyElement();
            element.setReply_id(notice.getRn_id());
            element.setReply_user_id(notice.getAuthor_id());
            element.setReply_user_name(userMapper.getUserNameById(notice.getAuthor_id()));
            element.setReply_to_post(notice.getReply_to_post());
            element.setReply_time(notice.getReply_time().toString());
            element.setReply_content(notice.getContent());
            element.setPost_id(notice.getPost_id());
            element.setPost_title(postMapper.getPost(notice.getPost_id()).getTitle());
            element.setComment_id(notice.getComment_id());
            element.setComment_content(null);
            list.add(element);
        }
        return list;
    }

    public void createReplyNotice(Integer userId, Integer authorId, String content, Timestamp time, boolean isReplyToPost, Integer postId, Integer commentId) {
        insertNewNotice(userId);
        messageMapper.insertReplyNotice(userId,authorId,content,time,isReplyToPost,postId,commentId);
    }

    public ArrayList<NoticeElement> getNoticeMessage(Integer userId) {
        resetNewNotice(userId);
        List<UserNotice> notices = messageMapper.getNoticeByUserId(userId);
        ArrayList<NoticeElement> list = new ArrayList<>();

        if (notices.isEmpty()) {
            return list;
        }

        messageMapper.updateUserSystemNoticeIsRead(userId);

        // 按照推送时间排序
        //notices.sort(Comparator.comparing(UserNotice::getPull_time));

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
                    insertNewNotice(id);
                    messageMapper.insertUserNotice(notice.getSystem_notice_id(), id, timestamp);
                }
            }
            else {
                insertNewNotice(notice.getReceiver_id());
                messageMapper.insertUserNotice(notice.getSystem_notice_id(), notice.getReceiver_id(), timestamp);
            }
            messageMapper.updateManagerSystemNoticePushed(notice.getSystem_notice_id());
        }
    }

    public String decodeMessage(String input) {
        Base64.Decoder decoder = Base64.getDecoder();
        String decode;
        try {
            decode = new String(decoder.decode(input), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return input;
        }
        if (decode.startsWith(headString)) {
            decode = decode.substring(headString.length());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < decode.length(); i++) {
                sb.append((char)(((int)decode.charAt(i)) - (move1[i % 3] * move2[i % 5] + move3[i % 4])));
            }
            return sb.toString();
        } else {
            return input;
        }
    }

    public NewNoticeResponse hasNewNotice(Integer id) {
        if (messageMapper.selectNewNotice(id) == 0) {
            return new NewNoticeResponse(true,"",false);
        }
        else {
            return new NewNoticeResponse(true,"",true);
        }
    }

    public void resetNewNotice(Integer id) {
        messageMapper.deleteNewNotice(id);
    }

    public void insertNewNotice(Integer id) {
        if (messageMapper.selectNewNotice(id) == 0) {
            messageMapper.insertNewNotice(id);
        }
    }
}
