package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.LoginResponse;
import com.hxt.backend.response.group.GroupElement;
import com.hxt.backend.response.group.GroupMessageElement;
import com.hxt.backend.response.group.GroupSearchResponse;
import com.hxt.backend.response.list.TimeInfoResponse;
import com.hxt.backend.response.list.ReportListResponse;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.list.UserSectionBlockListResponse;
import com.hxt.backend.response.sectionResponse.SearchSectionResponse;
import com.hxt.backend.response.sectionResponse.SectionElement;
import com.hxt.backend.response.singleInfo.TotalInfoResponse;
import com.hxt.backend.service.AdminService;
import com.hxt.backend.service.FileGenerateService;
import com.hxt.backend.service.GroupService;
import com.hxt.backend.service.MessageService;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private GroupService groupService;
    @Resource
    private MessageService messageService;
    @Resource
    private FileGenerateService fileGenerateService;
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
            adminService.setUserCookie("admin_id", String.valueOf(id), response);
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
            @CookieValue(name = "admin_id", defaultValue = "") String user_id,
            @RequestParam(name = "old_password", required = false) String op,
            @RequestParam(name = "new_password", required = false) String np
    ) {
        if (user_id.isEmpty() || op == null || np == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!adminService.checkPassword(Integer.parseInt(user_id), op)) {
            return new BasicInfoResponse(false, "旧密码错误！");
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
    public TimeInfoResponse getPostTimeInfo() {
        return adminService.getTimeInfo();
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

    @RequestMapping("/admin/group/delete")
    public BasicInfoResponse deleteGroup(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        return groupService.deleteGroup(0, id, true);
    }

    @RequestMapping("/admin/list/user")
    public UserListResponse getUserList() {
        return adminService.getUserList();
    }

    @GetMapping("/admin/list/section")
    public SearchSectionResponse getSectionList() {
        ArrayList<SectionElement> list = adminService.getSectionList();
        if (list.isEmpty()) {
            return new SearchSectionResponse(true,"未检索到相关结果",0,list);
        }
        else {
            return new SearchSectionResponse(true,"", list.size(), list);
        }
    }

    @GetMapping("/admin/list/section/block")
    public UserSectionBlockListResponse getSectionBlockList(
            @RequestParam(name = "order", required = false) Integer order,
            @RequestParam(name = "section", defaultValue = "0") Integer section
    ) {
        if (order == null || order < 0 || order > 2) {
            order = 0;
        }
        return adminService.getSectionBlockList(order, section);
    }

    @GetMapping("/admin/list/group")
    public GroupSearchResponse getGroupList() {
        List<GroupElement> list = groupService.getGroupList(0);
        GroupSearchResponse response = new GroupSearchResponse();
        response.setSuccess(true);
        response.setGroup_count(list.size());
        response.setGroup(list);
        return response;
    }

    @GetMapping("/admin/history/group")
    public BasicInfoResponse downloadGroupMessageList(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            HttpServletResponse response
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        List<GroupMessageElement> list = messageService.getGroupMessage(id);
        StringBuilder stringBuilder = new StringBuilder();
        for (GroupMessageElement element: list) {
            stringBuilder.append(element.getSender_name()).append(" ").append(element.getTime()).append("\n");
            stringBuilder.append(element.getContent()).append("\n\n");
        }
        String fileName = String.format("%s(id:%d)聊天记录.txt",groupService.getGroupInfo(id).getName(), id);
        fileGenerateService.StringToTxt(fileName, stringBuilder.toString(), response);
        return new BasicInfoResponse(true, "");
    }

    @RequestMapping("/admin/list/report/post")
    private ReportListResponse getPostReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(0);
    }

    @RequestMapping("/admin/list/report/comment")
    private ReportListResponse getCommentReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(1);
    }

    @RequestMapping("/admin/list/report/reply")
    private ReportListResponse getReplyReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(2);
    }

    @RequestMapping("/admin/list/report/user")
    private ReportListResponse getUserReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(3);
    }

    @RequestMapping("/admin/list/apply")
    private ReportListResponse getAuthorityApplyList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(4);
    }

    @RequestMapping("/admin/list/report/group")
    private ReportListResponse getGroupReportList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        return adminService.getUnhandledReports(5);
    }

    @RequestMapping("/admin/list/apply/course")
    private SearchSectionResponse getCourseApplyList(@CookieValue(name = "type", defaultValue = "") String type) {
        if (!type.equals("admin")) {
            return null;
        }
        ArrayList<SectionElement> list = adminService.getCourseApply();
        return new SearchSectionResponse(true,"", list.size(), list);
    }

    @RequestMapping("/admin/handle")
    public BasicInfoResponse handleReport(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "choice", required = false) Boolean choice,
            @RequestParam(name = "days", required = false) Integer days
    ) {
        if (id == null || choice == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.handleReport(id, choice, days);
        String info = res? "" : "处理发生异常！";
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/admin/handle/course")
    public BasicInfoResponse handleCourseRequest(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "choice", required = false) Boolean choice
    ) {
        if (id == null || choice == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.handleCourseRequest(id, choice);
        String info = res? "" : "处理发生异常！";
        return new BasicInfoResponse(res, info);
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
        } else if (days != null && days <= 0) {
            return new BasicInfoResponse(false, "填写的天数非法！");
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

    @RequestMapping("admin/unblock/section")
    public BasicInfoResponse sectionUnblockUser(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "section", required = false) Integer section,
            @RequestParam(name = "id", required = false) Integer user
    ) {
        if (user == null || section == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.sectionUnblockUser(user, section);
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

    @RequestMapping("/admin/message/broadcast")
    public BasicInfoResponse systemBroadcast(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "info", required = false) String info
    ) {
        if (info == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.systemBroadcast(info);
        String i = res? "" : "服务器错误！";
        return new BasicInfoResponse(res, i);
    }

    @RequestMapping("/admin/message/send")
    public BasicInfoResponse systemSend(
            @CookieValue(name = "type", defaultValue = "") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "info", required = false) String info
    ) {
        if (id == null || info == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!type.equals("admin")) {
            return new BasicInfoResponse(false, authorityResponse);
        }
        boolean res = adminService.systemMessage(id, info);
        String i = res? "" : "服务器错误！";
        return new BasicInfoResponse(res, i);
    }
}
