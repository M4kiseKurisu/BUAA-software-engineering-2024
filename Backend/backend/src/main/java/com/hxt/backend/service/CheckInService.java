package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.checkIn.CheckIn;
import com.hxt.backend.entity.checkIn.CheckInComment;
import com.hxt.backend.entity.checkIn.CheckInLike;
import com.hxt.backend.entity.post.PostLike;
import com.hxt.backend.mapper.CheckInMapper;
import com.hxt.backend.mapper.ImageMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.checkInResponse.*;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInService {
    @Resource
    private CheckInMapper checkInMapper;
    
    @Resource
    private UserMapper userMapper;
    
    @Resource
    private ImageMapper imageMapper;
    
    public List<CheckInIntroResponse> getPyqList(Integer userId) {
        List<CheckInIntroResponse> checkInIntroResponses = new ArrayList<>();
        
        List<CheckIn> checkIns = checkInMapper.getCheckInByAuthorId(userId);
        for (CheckIn checkIn : checkIns) {
            CheckInIntroResponse checkInIntroResponse = new CheckInIntroResponse(checkIn);
            List<String> imageUrls = checkInMapper.getImageByCheckInId(checkIn.getCheck_in_id());
            checkInIntroResponse.setImage_urls(imageUrls);
            
            checkInIntroResponses.add(checkInIntroResponse);
        }
        
        return checkInIntroResponses;
    }
    
    public Integer insertCheckIn(Integer userId, List<String> images, String content) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CheckIn checkIn = new CheckIn(0, content, userId, timestamp, 0);
        Integer res = checkInMapper.insertCheckIn(checkIn);
        if (res == 0) {
            return 0;
        }
        
        Integer checkInId = checkIn.getCheck_in_id();
        for (String image : images) {
            Integer insertImage = checkInMapper.insertCheckInImage(checkInId, image);
            if (insertImage == 0) {
                return 0;
            }
        }
        return 1;
    }
    
    public CheckInDetailResponse getCheckInDetail(Integer checkInId) {
        //获取打卡
        CheckIn checkIn = checkInMapper.getCheckInById(checkInId);
        CheckInDetailResponse checkInDetailResponse = new CheckInDetailResponse(checkIn);
        
        //获取打卡基本信息
        Integer authorId = checkIn.getAuthor_id();
        User author = userMapper.selectUserById(authorId);
        String authorName = author.getName();
        String authorSign = author.getSign();
        String headUrl = imageMapper.getImage(author.getHeadId());
        List<String> images = checkInMapper.getImageByCheckInId(checkInId);
        
        checkInDetailResponse.setPoster_avatar(headUrl);
        checkInDetailResponse.setPoster_name(authorName);
        checkInDetailResponse.setPoster_sign(authorSign);
        checkInDetailResponse.setImage_urls(images);
        
        //获取打卡点赞信息
        List<Integer> favorsId = checkInMapper.getFavorsIdByCheckInId(checkInId);
        List<FavorsInfoResponse> favorsInfoResponses = new ArrayList<>();
        for (Integer favorId : favorsId) {
            User favor = userMapper.selectUserById(favorId);
            String favorAvatar = imageMapper.getImage(favor.getHeadId());
            FavorsInfoResponse favorsInfoResponse = new FavorsInfoResponse(favorId, favorAvatar);
            
            favorsInfoResponses.add(favorsInfoResponse);
        }
        checkInDetailResponse.setFavors_list(favorsInfoResponses);
        
        //获取打卡评论信息
        List<CheckInComment> checkInComments = checkInMapper.getCommentByCheckInId(checkInId);
        List<CommentInfoResponse> commentInfoResponses = new ArrayList<>();
        for (CheckInComment comment : checkInComments) {
            CommentInfoResponse commentInfoResponse = new CommentInfoResponse(comment);
            Integer commentAuthorId = comment.getAuthor_id();
            User commentAuthor = userMapper.selectUserById(commentAuthorId);
            String commentAuthorName = commentAuthor.getName();
            String commentAuthorHead = imageMapper.getImage(commentAuthor.getHeadId());
            commentInfoResponse.setWriter_name(commentAuthorName);
            commentInfoResponse.setWriter_avatar(commentAuthorHead);
            
            commentInfoResponses.add(commentInfoResponse);
        }
        
        checkInDetailResponse.setComments_list(commentInfoResponses);
        
        return checkInDetailResponse;
    }
    
    public List<CheckInSquareIntroResponse> getCheckInSquare() {
        List<CheckInSquareIntroResponse> checkInSquareIntroResponses = new ArrayList<>();
        
        List<CheckIn> checkIns = checkInMapper.getAllCheckIn();
        for (CheckIn checkIn : checkIns) {
            CheckInSquareIntroResponse checkInSquareIntroResponse = new CheckInSquareIntroResponse(checkIn);
    
            Integer authorId = checkIn.getAuthor_id();
            User author = userMapper.selectUserById(authorId);
            String authorName = author.getName();
            String headUrl = imageMapper.getImage(author.getHeadId());
            List<String> images = checkInMapper.getImageByCheckInId(checkIn.getCheck_in_id());
            checkInSquareIntroResponse.setAuthor_name(authorName);
            checkInSquareIntroResponse.setAuthor_avatar(headUrl);
            if (!images.isEmpty()) {
                checkInSquareIntroResponse.setImage_url(images.get(0));
            }
            
            checkInSquareIntroResponses.add(checkInSquareIntroResponse);
        }
        return checkInSquareIntroResponses;
    }
    
    
    //获取打卡-点赞状态
    public Integer checkInLikeStatus(Integer postId, Integer user_id) {
        CheckInLike checkInLike = checkInMapper.getCheckInLike(postId, user_id);
        if (checkInLike == null) {
            return 0;
        }
        return 1;
        
    }
    
    //点赞/取消点赞打卡
    public Integer thumbCheckIn(Integer postId, Integer user_id) {
        Integer res = checkInMapper.deleteCheckInLike(postId, user_id);
        if (res == 0) {
            Timestamp likeTime = new Timestamp(System.currentTimeMillis());
            return checkInMapper.insertCheckInLike(postId, user_id, likeTime); //点赞
            
        }
        return 2;   //取消点赞
        
    }
    
    //更新打卡点赞数
    public Integer updateCheckInLikeCount(Integer checkInId, Integer op) {
        return checkInMapper.updateCheckInLikeCount(checkInId, op);
    }
    
    //评论打卡
    public Integer checkInComment(Integer checkInId, Integer userId, String content) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        return checkInMapper.insertCheckInComment(content, userId, checkInId, time);
    }
    
    //删除评论
    public Integer deleteComment(Integer commentId, Integer authorId) {
        return checkInMapper.deleteCheckInComment(commentId, authorId);
    }
    
    //删除打卡
    public Integer deleteCheckIn(Integer checkInId, Integer authorId) {
        return checkInMapper.deleteCheckIn(checkInId, authorId);
    }
    
    
    
}
