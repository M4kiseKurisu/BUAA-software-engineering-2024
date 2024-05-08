package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.LoginResponse;
import com.hxt.backend.response.list.PostTimeInfoResponse;
import com.hxt.backend.response.list.ReportListResponse;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.singleInfo.TotalInfoResponse;
import com.hxt.backend.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;
    private String hasEmptyResponse = "信息填写不完整！";
    private String authorityResponse = "该账号不具有相应权限！";

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
            adminService.setUserCookie("user_id", String.valueOf(id), response);
            adminService.setUserCookie("type", "admin", response);
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

    @RequestMapping("/admin/data/post")
    public PostTimeInfoResponse getPostTimeInfo() {
        return adminService.getPostTimeInfo();
    }

    @RequestMapping("/admin/section/add/course")
    public BasicInfoResponse addCourse(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "intro", required = false) String intro,
            @RequestParam(name = "type", required = false) String course_type,
            @RequestParam(name = "academy", required = false) String academy,
            @RequestParam(name = "credit", required = false) Integer credit,
            @RequestParam(name = "capacity", required = false) Integer capacity
    ) {
        if (name == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.addCourse(name, intro, course_type, academy, credit, capacity);
        String info = res? "" : "服务器错误，未能添加";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/section/add/school")
    public BasicInfoResponse addSchool(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "intro", required = false) String intro,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "web", required = false) String web
    ) {
        if (name == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.addSchool(name, intro, category, web);
        String info = res? "" : "服务器错误，操作失败";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/section/delete")
    public BasicInfoResponse deleteSection(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "move_id", required = false) Integer moveId
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        String info = adminService.deleteSection(id, moveId);
        boolean res = info.isEmpty();
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/list/user")
    public UserListResponse getUserList() {
        return adminService.getUserList();
    }

    @RequestMapping("admin/list/report/post")
    private ReportListResponse getPostReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(0);
    }

    @RequestMapping("admin/list/report/comment")
    private ReportListResponse getCommentReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(1);
    }

    @RequestMapping("admin/list/report/reply")
    private ReportListResponse getReplyReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(2);
    }

    @RequestMapping("admin/list/report/user")
    private ReportListResponse getUserReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(3);
    }

    @RequestMapping("admin/list/apply")
    private ReportListResponse getAuthorityApplyList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(4);
    }

    @RequestMapping("/admin/block")
    public BasicInfoResponse globalBlockUser(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "days", required = false) Integer days
    ) {
        System.out.println();
        if (id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.blockUser(id, days);
        String info = (res)? "" : "服务器错误，操作失败";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/unblock")
    public BasicInfoResponse globalUnblockUser(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.unblockUser(id);
        String info = (res)? "" : "服务器错误，操作失败";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/authority/add")
    public BasicInfoResponse setAuthority(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "section", required = false) Integer section,
            @RequestParam(name = "type", required = false) String auth_type
    ) {
        if (id == null || section == null || auth_type == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin") && !adminService.checkAuthority(Integer.parseInt(user_id), section)) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.setAuthority(id, section, auth_type);
        String info = res? "" : "服务器错误！";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/authority/delete")
    public BasicInfoResponse deleteAuthority(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "section", required = false) Integer section
    ) {
        if (id == null || section == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin") && !adminService.checkAuthority(Integer.parseInt(user_id), section)) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.deleteAuthority(id, section);
        String info = res? "" : "服务器错误！";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/authority/global/add")
    public BasicInfoResponse setGlobalAuthority(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.setGlobalAuthority(id);
        String info = res? "" : "服务器错误！";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/authority/global/delete")
    public BasicInfoResponse deleteGlobalAuthority(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.deleteGlobalAuthority(id);
        String info = res? "" : "服务器错误！";
        return new BasicInfoResponse(res, info);
    }
}
