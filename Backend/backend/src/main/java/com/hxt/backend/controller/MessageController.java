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
    public ChatListResponse getChatList() {
        return null;
    }

    @GetMapping("/message/private")
    public PrivateMessageResponse getPrivate(
            @RequestParam(name = "receiver_id", required = false) Integer receiver_id
    ) {
        return null;
    }

    @PostMapping("/message/private")
    public BasicInfoResponse sendPrivate(
            @RequestParam(name = "receiver_id", required = false) Integer receiver_id,
            @RequestParam(name = "content", required = false) String content
    ) {
        return null;
    }

    @GetMapping("/message/reply")
    public ReplyMessageResponse getReply() {
        return null;
    }

    /*
    @GetMapping("/message.apply")
    public ApplyMessageResponse getApply(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new ApplyMessageResponse(false,"用户未登录",0,null);
        }
        Integer id = Integer.parseInt(user_id);
        return null;
    }
    */
    

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
