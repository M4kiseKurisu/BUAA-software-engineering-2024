package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.checkInResponse.*;
import com.hxt.backend.service.CheckInService;
import com.hxt.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CheckInController {
    private final CheckInService checkInService;
    private final ReviewService reviewService;
    
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
    
    @GetMapping (value = "/pyq/square")
    public CheckInSquareResponse getCheckInSquare() {
        List<CheckInSquareIntroResponse> checkIn = checkInService.getCheckInSquare();
        return new CheckInSquareResponse(checkIn);
    }
    
    //用户是否点赞打卡
    @GetMapping (value = "/pyq/isLike")
    public IsLikeResponse isLikeCheckIn(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id
    
    ) {
        if (user_id.equals("")) {
            return new IsLikeResponse(false);
        }
        //获取点赞状态
        Integer status = checkInService.checkInLikeStatus(post_id, Integer.parseInt(user_id));
        
        if (status == 1) {
            return new IsLikeResponse(true);
        }
        
        return new IsLikeResponse(false);
    }
    
    //用户点赞打卡
    @RequestMapping (value="/pyq/like")
    public IsSuccessResponse likePost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id
    
    ) {
        if (user_id.equals("")) {
            return new IsSuccessResponse(false);
        }
        
        //点赞/取消点赞
        Integer status = checkInService.thumbCheckIn(post_id, Integer.parseInt(user_id));
        
        if (status == 1) {
            //帖子点赞数 +1
            checkInService.updateCheckInLikeCount(post_id, 1);
        } else if (status == 2) {
            //帖子点赞数 -1
            checkInService.updateCheckInLikeCount(post_id, -1);
        } else {
            return new IsSuccessResponse(false);
        }
        
        return new IsSuccessResponse(true);
    }
    
    
    //用户评论打卡
    @PostMapping (value = "/pyq/comment")
    public IsSuccessResponse checkInComment(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id,
            @RequestParam(name = "content", required = false) String content
    ) throws IOException {
        if (user_id.equals("")) {
            return new IsSuccessResponse(false);
        }
        
        //审核评论
        if (!reviewService.textReview(content)) {
            return new IsSuccessResponse(false);
        }
        //新增评论
        Integer res = checkInService.checkInComment(post_id, Integer.parseInt(user_id), content);
        
        if (res == 0) {
            return new IsSuccessResponse(false);
        }
        return new IsSuccessResponse(true);
    }
    
    //删除评论
    @DeleteMapping (value = "/pyq/comment/delete")
    public IsSuccessResponse deleteComment(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id
    ) {
        if (user_id.equals("")) {
            return new IsSuccessResponse(false);
        }
        
        //删除评论
        Integer res = checkInService.deleteComment(post_id, Integer.parseInt(user_id));
        if (res == 0) {
            return new IsSuccessResponse(false);
        }
        return new IsSuccessResponse(true);
    }
    
    //删除打卡
    @DeleteMapping (value = "/pyq/delete")
    public IsSuccessResponse deleteCheckIn(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "post_id", required = false) Integer post_id
    ) {
        if (user_id.equals("")) {
            return new IsSuccessResponse(false);
        }
        
        //删除打卡
        Integer res = checkInService.deleteCheckIn(post_id, Integer.parseInt(user_id));
        if (res == 0) {
            return new IsSuccessResponse(false);
        }
        return new IsSuccessResponse(true);
    }
    
}
