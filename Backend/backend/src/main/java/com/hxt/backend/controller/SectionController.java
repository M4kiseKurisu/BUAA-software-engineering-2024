package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.SectionAuthorityResponse;
import com.hxt.backend.response.PagesCountResponse;
import com.hxt.backend.response.sectionResponse.*;
import com.hxt.backend.service.AdminService;
import com.hxt.backend.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    private final AdminService adminService;
    private String hasEmptyResponse = "信息填写不完整！";

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
            @RequestParam(name = "tag_name", defaultValue = "") String tagName,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        if (sectionId.isEmpty()) {
            return new PostListResponse(false,null);
        }
        ArrayList<PostElement> list;
        if (keyword.isEmpty()) {
            list = sectionService.getSectionPosts(Integer.parseInt(sectionId), sort, postType, tagName, page);
        } else {
            list = sectionService.searchSectionPosts(Integer.parseInt(sectionId), sort, postType, tagName, keyword);
        }
        return new PostListResponse(true, list);
    }

    @GetMapping("/section/pages")
    public PagesCountResponse getSectionPages(
            @RequestParam(name = "section_id", defaultValue = "") String sectionId,
            @RequestParam(name = "post_type", defaultValue = "0") String postType,
            @RequestParam(name = "tag_name", defaultValue = "") String tagName
    ) {
        if (sectionId.isEmpty()) {
            return new PagesCountResponse(null);
        }
        return new PagesCountResponse(sectionService.getPageCount(Integer.parseInt(sectionId), postType, tagName));
    }

    @GetMapping("/section/info")
    public SectionInfoResponse getSectionInfo(
            @CookieValue(name = "user_id", defaultValue = "") String userId,
            @RequestParam(name = "section_id", defaultValue = "") String sectionId
    ) {
        if (sectionId.isEmpty() || userId.isEmpty()) {
            SectionInfoResponse response = new SectionInfoResponse();
            response.setSuccess(false);
            return response;
        }
        return sectionService.getSectionInfo(Integer.parseInt(sectionId),Integer.parseInt(userId));
    }

    @RequestMapping("/section/authority")
    public SectionAuthorityResponse getSectionAuthority(
            @RequestParam(name = "id", defaultValue = "") Integer sectionId
    ) {
        if (sectionId == null) {
            return new SectionAuthorityResponse(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }
        return sectionService.getSectionAuthority(sectionId);
    }

    @RequestMapping("/section/add/course")
    public BasicInfoResponse tryAddCourse(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "intro", required = false) String intro,
            @RequestParam(name = "type", required = false) String course_type,
            @RequestParam(name = "academy", required = false) String academy,
            @RequestParam(name = "credit", required = false) Integer credit,
            @RequestParam(name = "capacity", required = false) Integer capacity
    ) {
        if (name == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (sectionService.checkCourseName(name)) {
            return new BasicInfoResponse(false, "板块重名！");
        }
        boolean res;
        if (adminService.checkGlobalAuthority(Integer.parseInt(user_id))) {
            Integer id = sectionService.addCourse(name, intro, course_type, academy, credit, capacity);
            res = (id != null);
            adminService.setAuthority(Integer.parseInt(user_id), id, "teacher");    //  自动获得版块教师身份
        } else {
            res = sectionService.addCourseRequest(name, intro, course_type, academy, credit, capacity);
        }
        String info = res? "" : "服务器错误，未能添加";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/section/set")
    public BasicInfoResponse trySetSectionInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer section,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "intro", required = false) String intro,
            @RequestParam(name = "type", required = false) String course_type,
            @RequestParam(name = "academy", required = false) String academy,
            @RequestParam(name = "credit", required = false) Integer credit,
            @RequestParam(name = "capacity", required = false) Integer capacity
    ) {
        if (adminService.checkAuthority(Integer.parseInt(user_id), section)) {
            if (sectionService.checkCourseName(name)) {
                return new BasicInfoResponse(false, "板块重名！");
            }
            return sectionService.setSectionInfo(section, name, intro, course_type, academy, credit, capacity);
        } else {
            //  暂时不允许修改
            return new BasicInfoResponse(false, "没有相应权限！");
        }
    }

    @RequestMapping("/section/add/assistant")
    public BasicInfoResponse tryAddAssistant(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "section", required = false) Integer section,
            @RequestParam(name = "assistant", required = false) Integer assistant
    ) {
        if (user_id.isEmpty() || section == null || assistant == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        return sectionService.tryAddAssistant(Integer.parseInt(user_id), section, assistant);
    }

    @RequestMapping("/section/delete/assistant")
    public BasicInfoResponse tryDeleteAssistant(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "section", required = false) Integer section,
            @RequestParam(name = "assistant", required = false) Integer assistant
    ) {
        if (user_id.isEmpty() || section == null || assistant == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        return sectionService.tryDeleteAssistant(Integer.parseInt(user_id), section, assistant);
    }

    @RequestMapping("/section/block")
    public BasicInfoResponse tryBlockUser(
            @CookieValue(name = "type", defaultValue = "") String type,
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "section", required = false) Integer section,
            @RequestParam(name = "id", required = false) Integer user,
            @RequestParam(name = "days", required = false) Integer days
    ) {
        if (!type.equals("admin") && (user_id.isEmpty() || section == null || user == null)) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (days != null && days <= 0) {
            return new BasicInfoResponse(false, "填写的天数非法！");
        }
        return sectionService.tryBlockUser(type.equals("admin"), Integer.parseInt(user_id), section, user, days);
    }

    @RequestMapping("/section/unblock")
    public BasicInfoResponse tryUnblockUser(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "section", required = false) Integer section,
            @RequestParam(name = "id", required = false) Integer user
    ) {
        if (user_id.isEmpty() || section == null || user == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        return sectionService.tryUnblockUser(Integer.parseInt(user_id), section, user);
    }

    @RequestMapping("/section/academy")
    public SectionAcademyResponse getAcademyList() {
        return sectionService.getAcademyList();
    }
}
