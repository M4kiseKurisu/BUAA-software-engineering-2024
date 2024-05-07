package com.hxt.backend.controller;

import com.hxt.backend.response.checkInResponse.CheckInIntroListResponse;
import com.hxt.backend.response.messageResponse.ChatListResponse;
import com.hxt.backend.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;
    
    //获取用户所有打卡简略信息
    @GetMapping (value = "/pyq/userInfo")
    public CheckInIntroListResponse userPyqInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new CheckInIntroListResponse(null);
        }
        
        //获取pyq列表
        return new CheckInIntroListResponse(checkInService.getPyqList(Integer.parseInt(user_id)));
    }
}
