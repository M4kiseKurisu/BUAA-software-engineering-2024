package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final MessageService messageService;

    private BasicInfoResponse createPrivateChat(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "receiver", defaultValue = "", required = false) String receiver
    ) {

    }
}
