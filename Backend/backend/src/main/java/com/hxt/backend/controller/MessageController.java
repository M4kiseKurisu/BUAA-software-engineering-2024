package com.hxt.backend.controller;


import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.messageResponse.*;
import com.hxt.backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @GetMapping("/message/chats")
    public ChatListResponse getChats(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new ChatListResponse(false,"用户未登录",0,null);
        }
        Integer id = Integer.parseInt(user_id);
        ArrayList<ChatElement> list = messageService.getChatList(id);
        if (list.isEmpty()) {
            return new ChatListResponse(true,"暂无聊天记录",0,list);
        }
        else {
            return new ChatListResponse(true,"", list.size(), list);
        }
    }

    @GetMapping("/message/private")
    public PrivateMessageResponse getPrivate(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "receiver_id", defaultValue = "", required = false) String receiver_id
    ) {
        if (user_id.equals("")) {
            return new PrivateMessageResponse(false,"",0,null);
        }
        if (receiver_id.equals("")) {
            return new PrivateMessageResponse(false,"缺少请求参数",0,null);
        }
        Integer id = Integer.parseInt(user_id);
        Integer receiver = Integer.parseInt(receiver_id);
        ArrayList<PrivateElement> list = messageService.getPrivateMessage(id, receiver);
        if (list.isEmpty()) {
            return new PrivateMessageResponse(true,"无聊天记录",0,list);
        }
        else {
            return new PrivateMessageResponse(true,"",list.size(),list);
        }
    }

    @PostMapping("/message/private")
    public BasicInfoResponse sendPrivate(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "receiver_id",  defaultValue = "", required = false) String receiver_id,
            @RequestParam(name = "content",  defaultValue = "", required = false) String content
    ) {
        if (user_id.equals("")) {
            return new BasicInfoResponse(false,"用户未登录");
        }
        if (receiver_id.equals("") || content.equals("")) {
            return new BasicInfoResponse(false,"缺少请求参数");
        }
        Integer id = Integer.parseInt(user_id);
        Integer receiver = Integer.parseInt(receiver_id);
        Boolean status = messageService.sendPrivateMessage(id, receiver, content);
        if (status) {
            return new BasicInfoResponse(true,"");
        }
        else {
            return new BasicInfoResponse(false,"服务器错误");
        }
    }

    @GetMapping("/message/reply")
    public ReplyMessageResponse getReply(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new ReplyMessageResponse(false,"用户未登录",0,null);
        }
        Integer id = Integer.parseInt(user_id);
        ArrayList<ReplyElement> list = messageService.getReplyMessage(id);
        if (list.isEmpty()) {
            return new ReplyMessageResponse(true,"暂未收到回复",0,list);
        }
        else {
            return new ReplyMessageResponse(true,"", list.size(), list);
        }
    }


    @GetMapping("/message/apply")
    public ApplyMessageResponse getApply(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new ApplyMessageResponse(false,"用户未登录",0,null);
        }
        Integer id = Integer.parseInt(user_id);
        ArrayList<ApplyElement> list = messageService.getApplyMessage(id);
        if (list.isEmpty()) {
            return new ApplyMessageResponse(true,"暂无申请信息",0,list);
        }
        else {
            return new ApplyMessageResponse(true,"", list.size(), list);
        }
    }

    

    @GetMapping("/message/notice")
    public NoticeMessageResponse getNotice(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new NoticeMessageResponse(false,"用户未登录",0,null);
        }
        Integer id = Integer.parseInt(user_id);
        ArrayList<NoticeElement> list = messageService.getNoticeMessage(id);
        if (list.isEmpty()) {
            return new NoticeMessageResponse(true,"暂无系统通知",0,list);
        }
        else {
            return new NoticeMessageResponse(true,"", list.size(), list);
        }
    }

}
