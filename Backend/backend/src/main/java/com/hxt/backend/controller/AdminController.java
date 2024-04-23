package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.service.AdminService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;
    private String hasEmptyResponse = "信息填写不完整！";

    @RequestMapping("/admin/section/add/course")
    public BasicInfoResponse addCourse(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "intro", required = false) String intro,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "academy", required = false) String academy,
            @RequestParam(name = "credit", required = false) Integer credit,
            @RequestParam(name = "capacity", required = false) Integer capacity
    ) {
        if (user_id == null) {
            return new BasicInfoResponse(false, "user id is null");
        } else if (name == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        boolean res = adminService.addCourse(name, intro, type, academy, credit, capacity);
        String info = res? "" : "服务器错误，未能添加";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/section/add/school")
    public BasicInfoResponse addSchool(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "intro", required = false) String intro,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "web", required = false) String web
    ) {
        if (user_id == null) {
            return new BasicInfoResponse(false, "user id is null");
        } else if (name == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        boolean res = adminService.addSchool(name, intro, category, web);
        String info = res? "" : "服务器错误，未能添加";
        return new BasicInfoResponse(res, info);
    }
}
