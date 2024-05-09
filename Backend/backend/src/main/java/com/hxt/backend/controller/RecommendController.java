package com.hxt.backend.controller;


import com.hxt.backend.response.postResponse.PostIntroResponse;
import com.hxt.backend.response.postResponse.SearchResponse;
import com.hxt.backend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;
    
    @RequestMapping(value = "/posts/recommend")
    public SearchResponse recommendPost(
            @CookieValue(name = "user_id", defaultValue = "") String userId
    ) throws IOException {
        
        if (userId.isEmpty()) {
            return new SearchResponse(false,"用户未登录",null);
        }
        
        //推荐帖子
        List<PostIntroResponse> list = recommendService.recommendPostsByContent(Integer.parseInt(userId));
        
        if (list.isEmpty()) {
            return new SearchResponse(true,"未找到未浏览帖子",list);
        } else {
            return new SearchResponse(true,"推荐成功",  list);
        }
        
    }
}
