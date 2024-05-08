package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.checkInResponse.CheckInDetailResponse;
import com.hxt.backend.response.checkInResponse.CheckInIntroListResponse;
import com.hxt.backend.response.messageResponse.ChatListResponse;
import com.hxt.backend.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    
    
    @PostMapping (value = "/pyq/send")
    public BasicInfoResponse sendCheckIn(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "image_urls[]", required = false) List<String> image_urls,
            @RequestParam(name = "content", required = false) String content
    ) {
        if (user_id.equals("")) {
            return new BasicInfoResponse(false, "用户未登录");
        }
        
        if(image_urls.isEmpty() || content == null) {
            return new BasicInfoResponse(false, "打卡内容不完整");
        }
        
        Integer res = checkInService.insertCheckIn(Integer.parseInt(user_id), image_urls, content);
        if (res == 0) {
            return new BasicInfoResponse(false, "系统出错");
        }
        return new BasicInfoResponse(true, "打卡成功");
    }
    
    @GetMapping (value = "/pyq/detail")
    public CheckInDetailResponse getCheckInDetail(
            @RequestParam(name = "post_id", required = false) Integer post_id
    ) {
        if (post_id == null) {
            return new CheckInDetailResponse(null);
        }
        
        return checkInService.getCheckInDetail(post_id);
    }
    
}
