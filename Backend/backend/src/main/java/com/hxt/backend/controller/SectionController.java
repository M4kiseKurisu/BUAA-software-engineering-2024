package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.sectionResponse.*;
import com.hxt.backend.service.ReviewService;
import com.hxt.backend.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @GetMapping("/section/search")
    public SearchSectionResponse searchSection(
            @CookieValue(name = "user_id", defaultValue = "") String userId,
            @RequestParam(name = "keyword", defaultValue = "") String keyWord,
            @RequestParam(name = "sort", defaultValue = "0") String sort,
            @RequestParam(name = "type", defaultValue = "0") String type,
            @RequestParam(name = "academy", defaultValue = "") String academy
    ) {
        if (userId.isEmpty()) {
            return new SearchSectionResponse(false,"用户未登录",0,null);
        }
        ArrayList<SectionElement> list = sectionService.searchSection(keyWord,Integer.parseInt(sort),
                Integer.parseInt(type),academy,Integer.parseInt(userId));
        if (list.isEmpty()) {
            return new SearchSectionResponse(true,"未检索到响应结果",0,list);
        }
        else {
            return new SearchSectionResponse(true,"", list.size(), list);
        }
    }

    @GetMapping("/section/hots")
    public SearchSectionResponse getHotSection(
            @CookieValue(name = "user_id", defaultValue = "") String userId
    ) {
        if (userId.isEmpty()) {
            return new SearchSectionResponse(false,"用户未登录",0,null);
        }
        ArrayList<SectionElement> list = sectionService.getHotSections(Integer.parseInt(userId));
        if (list.isEmpty()) {
            return new SearchSectionResponse(true,"未检索到相关结果",0,list);
        }
        else {
            return new SearchSectionResponse(true,"", list.size(), list);
        }
    }

    @PostMapping("/section/focus")
    public BasicInfoResponse focusSection(
            @CookieValue(name = "user_id",defaultValue = "") String user_id,
            @RequestParam(name = "section_id", defaultValue = "") String section_id
    ){
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false,"用户未登录");
        }
        if (section_id.isEmpty()) {
            return new BasicInfoResponse(false,"请求参数缺失");
        }

        if (sectionService.focusSection(Integer.parseInt(user_id),Integer.parseInt(section_id))) {
            return new BasicInfoResponse(true,"");
        }
        else {
            return new BasicInfoResponse(false,"操作失败");
        }
    }

    @PostMapping("/section/unfocus")
    public BasicInfoResponse unfocusSection(
            @CookieValue(name = "user_id",defaultValue = "") String user_id,
            @RequestParam(name = "section_id", defaultValue = "") String section_id
    ){
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false,"用户未登录");
        }
        if (section_id.isEmpty()) {
            return new BasicInfoResponse(false,"请求参数缺失");
        }

        if (sectionService.unfocusSection(Integer.parseInt(user_id),Integer.parseInt(section_id))) {
            return new BasicInfoResponse(true,"");
        }
        else {
            return new BasicInfoResponse(false,"操作失败");
        }
    }

    @GetMapping("/section/posts")
    public PostListResponse getSectionPosts(
            @RequestParam(name = "section_id", defaultValue = "") String sectionId,
            @RequestParam(name = "sort", defaultValue = "0") String sort,
            @RequestParam(name = "post_type", defaultValue = "0") String postType,
            @RequestParam(name = "tag_name", defaultValue = "") String tagName
    ) {
        if (sectionId.isEmpty()) {
            return new PostListResponse(false,null);
        }
        ArrayList<PostElement> list = sectionService.getSectionPosts(Integer.parseInt(sectionId),sort,postType,tagName);
        return new PostListResponse(true,list);
    }

    @GetMapping("/section/info")
    public SectionInfoResponse getSectionInfo(
            @RequestParam(name = "section_id", defaultValue = "") String sectionId
    ) {
        if (sectionId.isEmpty()) {
            SectionInfoResponse response = new SectionInfoResponse();
            response.setSuccess(false);
            return response;
        }
        return sectionService.getSectionInfo(Integer.parseInt(sectionId));
    }
    
}
