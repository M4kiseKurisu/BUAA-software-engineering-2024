package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.LoginResponse;
import com.hxt.backend.response.singleInfo.TotalInfoResponse;
import com.hxt.backend.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @RequestMapping("/admin/login")
    public LoginResponse login(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "password", required = false) String password,
            HttpServletResponse response
    ) {
        if (name == null)  {
            return new LoginResponse(false, -1, "未填写用户名！", "");
        } else if (password == null) {
            return new LoginResponse(false, -1, "未填写密码！", "");
        } else if (!adminService.lengthCheck(name, 1, 16)) {
            return new LoginResponse(false, -1, "用户名不合法！", "");
        }
        int id = adminService.checkPassword(name, password);
        if (id > 0) {
            Cookie cookie = new Cookie("user_id",String.valueOf(id));
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new LoginResponse(true, id, "", "");
        } else if (id >= -3) {
            String info = String.format("用户名或密码错误，还有%d次尝试机会！", 4 + id);
            return new LoginResponse(false, id, info, "");
        } else if (id == -4) {
            return new LoginResponse(false, id, "账号密码错误次数过多，管理员账号已上锁24小时！", "");
        } else {
            return new LoginResponse(false, id, "管理员不存在！", "");
        }
    }

    @RequestMapping("/admin/password/update")
    public BasicInfoResponse setPassword(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "old_password", required = false) String op,
            @RequestParam(name = "new_password", required = false) String np
    ) {
        if (user_id.isEmpty() || op == null || np == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!adminService.checkPassword(Integer.parseInt(user_id), op)) {
            return new BasicInfoResponse(false, "旧密码错误！");
        } else if (!adminService.lengthCheck(np, 6, 18)) {
            return new BasicInfoResponse(false, "密码过长或过短！");
        } else {
            adminService.resetPassword(Integer.parseInt(user_id), np);
            return new BasicInfoResponse(true, "");
        }
    }

    @RequestMapping("/admin/info")
    public TotalInfoResponse getTotalInfo() {
        return adminService.getTotalInfo();
    }

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
