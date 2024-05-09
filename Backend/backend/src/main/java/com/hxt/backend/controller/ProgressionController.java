package com.hxt.backend.controller;

import com.hxt.backend.response.postResponse.*;
import com.hxt.backend.response.progressionResponse.*;
import com.hxt.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProgressionController {
    private final ProgressionService progressionService;
    private final RecommendService recommendService;
    
    @RequestMapping (value="/progression/discussion")
    public DiscussionResponse showDiscussion(
            @RequestParam(name = "section_id", required = false) Integer section_id
    ) {
        if (section_id == null) {
            section_id = 0;
        }
        
        //获取模块帖子总数
        Integer totalPosts = progressionService.getTotalPostNum(section_id);
        
        //获取热门作者
        List<Integer> hotAuthorId = progressionService.getHotAuthorId(section_id);
        
        return new DiscussionResponse(true, totalPosts, hotAuthorId);
        
    }
    
    //获取升学模块所有帖子
    @RequestMapping (value = "/progression/totalPosts")
    public PostListResponse totalPosts(
            @RequestParam(name = "section_id", required = false) Integer section_id
    ) {
        
        section_id = (section_id == null) ? 0 : section_id;
        
        //获取模块下所有帖子
        List<PostIntroResponse> postIntroResponses = progressionService.getTotalPost(section_id);
        
        return new PostListResponse(true, postIntroResponses);
    }
    
    
    //筛选帖子
    @RequestMapping (value = "/progression/filter")
    public PostListResponse filter(
            @RequestParam(name = "target", required = false) String target,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "sort", required = false) Integer sort,
            @RequestParam(name = "recommend", required = false) boolean recommend,
            @CookieValue(name = "user_id", defaultValue = "") String userId
    ) throws IOException {
    
        if (userId == "") {
            return new PostListResponse(false,null);
        }
        
        if (recommend) {
            //推荐帖子
            List<PostIntroResponse> list = recommendService.recommendPostsByContent(Integer.parseInt(userId));
            if (list.isEmpty()) {
                return new PostListResponse(false, list);
            }
            return new PostListResponse(true, list);
        }
        
        //筛选帖子
        List<PostIntroResponse> list = progressionService.filterPost(target, type, keyword, sort);
        
        if (list.isEmpty()) {
            return new PostListResponse(false, list);
        }
        return new PostListResponse(true, list);
        
    }
    
    
    
    
    //获取学校列表
    @RequestMapping (value = "/progression/schools")
    public SchoolIntroListResponse totalPosts() {
        
        //获取所有学校列表
        List<SchoolIntroResponse> schoolIntroResponses = progressionService.getSchoolList();
        
        return new SchoolIntroListResponse(true, schoolIntroResponses);
    }
    
    
    //获取学校详细信息
    @RequestMapping(value = "/progression/schoolInfo")
    public SchoolInfoResponse schoolInfo(
            @RequestParam(name = "school_id", required = false) Integer school_id
    ) {
        
        if (school_id == null) {
            return new SchoolInfoResponse(false, null, null, null, null, null);
        }
        
        return progressionService.getSchoolInfo(school_id);
    }
}
