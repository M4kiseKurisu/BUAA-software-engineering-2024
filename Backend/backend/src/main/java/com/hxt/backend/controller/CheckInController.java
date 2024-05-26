package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.PagesCountResponse;
import com.hxt.backend.response.checkInResponse.*;
import com.hxt.backend.response.postResponse.WritePostResponse;
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
    @GetMapping(value = "/pyq/userInfo")
    public CheckInIntroListResponse userPyqInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.equals("")) {
            return new CheckInIntroListResponse(null);
        }
        
        //获取pyq列表
        return new CheckInIntroListResponse(checkInService.getPyqList(Integer.parseInt(user_id)));
    }
    
    //  获取连续打卡天数
    @GetMapping(value = "/pyq/days")
    public CheckInDaysResponse userCheckInDays(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (user_id.equals("")) {
            //return new CheckInDaysResponse(false, 0);
        } else if (id == null) {
            id = Integer.parseInt(user_id);
        }
        return new CheckInDaysResponse(true, checkInService.getCheckInDays(id));
    }
    
    
    @PostMapping(value = "/pyq/send")
    public BasicInfoResponse sendCheckIn(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "image_urls[]", required = false) List<String> image_urls,
            @RequestParam(name = "content", required = false) String content,
            @RequestParam(name = "authority", required = false) Integer authority
    ) throws IOException {
        if (user_id.equals("")) {
            return new BasicInfoResponse(false, "用户未登录");
        }
        
        if (image_urls.isEmpty() || content == null) {
            return new BasicInfoResponse(false, "打卡内容不完整");
        }
        
        if (authority == null) {
            authority = 0;  //  默认权限为0（所有人均可见）
        }
        
        if (image_urls.size() > 9) {
            return new BasicInfoResponse(false, "打卡图片最多为9张！");
        }
        
        //审核打卡内容
        if (!reviewService.textReview(content)) {
            return new BasicInfoResponse(false, "打卡内容违规");
        }
        
        for (String imageUrl : image_urls) {
            //审核图片是否合规
            if (!reviewService.imageReview(imageUrl)) {
                return new BasicInfoResponse(false, "图片不合规");
            }
        }
        
        Integer res = checkInService.insertCheckIn(Integer.parseInt(user_id), image_urls, content, authority);
        if (res == 0) {
            return new BasicInfoResponse(false, "系统出错");
        }
        return new BasicInfoResponse(true, "打卡成功");
    }
    
    @GetMapping(value = "/pyq/detail")
    public CheckInDetailResponse getCheckInDetail(
            @RequestParam(name = "post_id", required = false) Integer post_id
    ) {
        if (post_id == null) {
            return new CheckInDetailResponse(null);
        }
        
        return checkInService.getCheckInDetail(post_id);
    }
    
    @GetMapping(value = "/pyq/square")
    public CheckInSquareResponse getCheckInSquare(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        if (user_id.equals("")) {
            return new CheckInSquareResponse(null);
        }
        List<CheckInSquareIntroResponse> checkIn = checkInService.getCheckInSquare(Integer.parseInt(user_id), page);
        return new CheckInSquareResponse(checkIn);
    }
    
    @GetMapping(value = "/pyq/follow")
    public CheckInSquareResponse getFollowedCheckIn(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "page", defaultValue = "0") Integer page
    ) {
        if (user_id.equals("")) {
            return new CheckInSquareResponse(null);
        }
        List<CheckInSquareIntroResponse> checkIn = checkInService.getFollowedCheckIn(Integer.parseInt(user_id), page);
        return new CheckInSquareResponse(checkIn);
    }
    
    @GetMapping(value = "/pyq/pages")
    public PagesCountResponse getCheckInPages(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "limit", defaultValue = "0") Integer limit
    ) {
        if (user_id.equals("")) {
            return new PagesCountResponse(null);
        }
        return new PagesCountResponse(checkInService.getCheckInPages(Integer.parseInt(user_id), limit));
    }
    
    //用户是否点赞打卡
    @GetMapping(value = "/pyq/isLike")
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
    @RequestMapping(value = "/pyq/like")
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
    @PostMapping(value = "/pyq/comment")
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
    @RequestMapping(value = "/pyq/comment/delete")
    public IsSuccessResponse deleteComment(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "comment_id", required = false) Integer comment_id
    ) {
        if (user_id.equals("")) {
            return new IsSuccessResponse(false);
        }
        
        //删除评论
        Integer res = checkInService.deleteComment(comment_id, Integer.parseInt(user_id));
        if (res == 0) {
            return new IsSuccessResponse(false);
        }
        return new IsSuccessResponse(true);
    }
    
    //删除打卡
    @RequestMapping(value = "/pyq/delete")
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
