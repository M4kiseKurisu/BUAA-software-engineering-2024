package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.group.*;
import com.hxt.backend.service.FrequencyLogService;
import com.hxt.backend.service.GroupService;
import com.hxt.backend.service.MessageService;
import com.hxt.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;
    private final MessageService messageService;
    private final FrequencyLogService frequencyLogService;
    private final String hasEmptyResponse = "信息填写不完整！";
    private final String frequencyResponse = "操作太频繁了，休息一下再来吧！";

    @PostMapping("/group/create")
    public BasicInfoResponse createGroup(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "permitted_num", defaultValue = "") String permitted_num,
            @RequestParam(name = "content", defaultValue = "") String content,
            @RequestParam(name = "is_examine", defaultValue = "") Boolean is_examine,
            @RequestParam(name = "image", defaultValue = "") String image,
            @RequestParam(name = "tags[]") String[] tags
            ) {
        if (user_id.isEmpty() || name.isEmpty() || permitted_num.isEmpty() || content.isEmpty() || is_examine == null || image.isEmpty() || tags.length == 0) {
            return new BasicInfoResponse(false,"请填写所有信息");
        }
        return groupService.createGroup(name,Integer.parseInt(user_id),Integer.parseInt(permitted_num),content,is_examine,image, Arrays.stream(tags).toList());
    }

    @PostMapping("/group/delete")
    public BasicInfoResponse deleteGroup(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "group_id", defaultValue = "") String group_id
    ) {
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false,"用户未登录");
        }
        if (group_id.isEmpty()) {
            return new BasicInfoResponse(false,"请选择学习团体");
        }

        return groupService.deleteGroup(Integer.parseInt(user_id),Integer.parseInt(group_id), false);
    }

    @GetMapping("/group/search")
    public GroupSearchResponse searchAvailableGroup(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "tag", defaultValue = "") String tag,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        List<GroupElement> list = groupService.searchGroup(keyword,tag,Integer.parseInt(user_id));
        GroupSearchResponse response = new GroupSearchResponse();
        response.setSuccess(true);
        if (list.isEmpty()) {
            response.setInfo("未搜索到相关学习团体");
            response.setGroup_count(0);
        }
        else {
            response.setGroup_count(list.size());
            response.setGroup(list);
        }
        return response;
    }

    @GetMapping("/group/list")
    public GroupSearchResponse getAvailableGroupList(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        List<GroupElement> list = groupService.getGroupList(Integer.parseInt(user_id));
        GroupSearchResponse response = new GroupSearchResponse();
        response.setSuccess(true);
        if (list.isEmpty()) {
            response.setInfo("未搜索到相关学习团体");
            response.setGroup_count(0);
        }
        else {
            response.setGroup_count(list.size());
            response.setGroup(list);
        }
        return response;
    }

    @PostMapping("/group/join")
    public BasicInfoResponse applyToJoinGroup(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "group_id", defaultValue = "") String group_id,
            @RequestParam(name = "submit_info", defaultValue = "") String submit_info
    ) {
        if (user_id.isEmpty() || group_id.isEmpty()) {
            return new BasicInfoResponse(false,"信息缺失");
        }
        return groupService.applyJoinGroup(Integer.parseInt(user_id),Integer.parseInt(group_id),submit_info);
    }

    @GetMapping("/group/joined")
    public GroupSearchResponse getJoinedGroup(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        GroupSearchResponse response = new GroupSearchResponse();
        if (user_id.isEmpty()) {
            response.setSuccess(false);
            response.setInfo("用户未登录");
            return response;
        }
        List<GroupElement> list = groupService.getJoinedGroup(Integer.parseInt(user_id));
        if (list.isEmpty()) {
            response.setInfo("用户未加入学习团体");
        }
        else {
            response.setGroup_count(list.size());
            response.setGroup(list);
        }
        response.setSuccess(true);
        return response;
    }

    @PostMapping("group/exit")
    public BasicInfoResponse exitGroup(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "group_id", defaultValue = "") String group_id
    ) {
        if (user_id.isEmpty() || group_id.isEmpty()) {
            return new BasicInfoResponse(false,"信息缺失");
        }
        return groupService.exitGroup(Integer.parseInt(user_id),Integer.parseInt(group_id));
    }

    @GetMapping("/group/memberList")
    public MemberResponse getGroupMember(
            @RequestParam(name = "group_id", defaultValue = "") String group_id
    ) {
        MemberResponse response = new MemberResponse();
        if (group_id.isEmpty()) {
            response.setSuccess(false);
            response.setInfo("信息缺失");
            return response;
        }
        List<MemberElement> list = groupService.getGroupMember(Integer.parseInt(group_id));
        if (list.isEmpty()) {
            response.setMember_count(0);
            response.setInfo("暂无成员");
        }
        else {
            response.setMember_count(list.size());
            response.setMember(list);
        }
        response.setSuccess(true);
        return response;
    }

    @GetMapping("/group/message")
    public GroupMessageResponse getGroupMessage(
            @RequestParam(name = "group_id", defaultValue = "") String group_id
    ) {
        GroupMessageResponse response = new GroupMessageResponse();
        if (group_id.isEmpty()) {
            response.setSuccess(false);
            response.setInfo("信息缺失");
        }
        List<GroupMessageElement> list = messageService.getGroupMessage(Integer.parseInt(group_id));
        if (list.isEmpty()) {
            response.setInfo("无聊天记录");
            response.setMessage_count(0);
        }
        else {
            response.setMessage_count(list.size());
            response.setMessage_list(list);
        }
        response.setSuccess(true);
        return response;
    }

    @GetMapping("/group/info")
    public GroupSearchResponse getGroupInfo(
            @RequestParam(name = "group_id", defaultValue = "") String group_id
    ) {
        GroupSearchResponse response = new GroupSearchResponse();
        if (group_id.isEmpty()) {
            response.setSuccess(false);
            response.setInfo("信息缺失");
            return response;
        }
        GroupElement element = groupService.getGroupInfo(Integer.parseInt(group_id));
        if (element == null) {
            response.setSuccess(false);
            response.setInfo("未查找到相关信息");
        }
        else {
            List<GroupElement> list = new ArrayList<>();
            list.add(element);
            response.setGroup(list);
            response.setSuccess(true);
        }
        return response;
    }

    @PostMapping("/group/accept")
    public BasicInfoResponse acceptApply(
            @RequestParam(name = "id", defaultValue = "") String id,
            @RequestParam(name = "accept", defaultValue = "") String accept
    ) {
        if (id.isEmpty() || accept.isEmpty()) {
            return new BasicInfoResponse(false, "信息缺失");
        }
        return messageService.updateApply(Integer.parseInt(id), accept.equals("1"));
    }

    @RequestMapping("/group/report")
    public BasicInfoResponse reportGroup(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "detail", required = false) String detail
    ) {
        if (user_id.isEmpty() || id == null || detail == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        boolean res = groupService.reportGroup(Integer.parseInt(user_id), id, detail);
        String info = res? "" : "服务器错误！";
        if (res) {
            frequencyLogService.setLog(Integer.parseInt(user_id), 10);
        }
        return new BasicInfoResponse(res, info);
    }
}
