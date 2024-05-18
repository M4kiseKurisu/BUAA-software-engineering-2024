package com.hxt.backend.service;

import com.hxt.backend.entity.group.Group;
import com.hxt.backend.mapper.GroupMapper;
import com.hxt.backend.mapper.MessageMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.group.GroupElement;
import com.hxt.backend.response.group.GroupMessageElement;
import com.hxt.backend.response.group.MemberElement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupMapper groupMapper;
    private final TagService tagService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    public BasicInfoResponse createGroup(String name,Integer promoter_id, Integer permitted_num, String content, boolean is_examine, String image, List<String> tags) {
        if (permitted_num < 3) {
            return new BasicInfoResponse(false,"群体人数限制");
        }
        Group group = new Group(0,name,promoter_id,0,content,permitted_num,is_examine,image);
        groupMapper.insertGroup(group);
        Integer groupId = group.getGroup_id();
        for (String tag: tags) {
            Integer tagId = tagService.getIdByName(tag);
            if (tagId == null) {
                tagService.addTag(tag);
                tagId = tagService.getIdByName(tag);
            }
            groupMapper.insertGroupTag(groupId, tagId);
        }
        groupMapper.insertGroupMember(groupId,promoter_id);
        groupMapper.updateGroupMember(groupId,1);
        return new BasicInfoResponse(true,"");
    }

    public BasicInfoResponse deleteGroup(Integer userId, Integer groupId, boolean isAdmin) {
        int promoterId = groupMapper.selectPromoterIdByGroupId(groupId);
        if (!isAdmin && promoterId != userId) {
            return new BasicInfoResponse(false,"无操作权限");
        }
        groupMapper.deleteGroupById(groupId);
        return new BasicInfoResponse(true,"");
    }

    public List<GroupElement> getGroupList(Integer userId) {
        List<Group> groups = groupMapper.selectGroup();
        List<GroupElement> list = new ArrayList<>();
        List<Integer> joined = groupMapper.selectJoinedGroupsByUserId(userId);
        for (Group group: groups) {
            if (joined.contains(group.getGroup_id())) {
                continue;
            }
            GroupElement element = new GroupElement();
            element.setGroup_id(group.getGroup_id());
            element.setName(group.getName());
            element.setCreater_id(group.getPromoter_id());
            element.setMember_count(group.getMember_count());
            element.setContent(group.getContent());
            element.setPermitted_num(group.getPermitted_num());
            element.setIs_examine(group.is_examine());
            element.setImage(group.getImage());
            element.setTags(groupMapper.selectGroupTag(group.getGroup_id()));
            list.add(element);
        }
        return list;
    }

    public List<GroupElement> searchGroup(String keyword, String tag, Integer userId) {
        List<GroupElement> list = new ArrayList<>();
        List<Group> groups;
        if (keyword.isEmpty()) {
            groups = groupMapper.selectGroup();
        }
        else {
            groups = groupMapper.selectGroupByKeyword(keyword);
        }
        List<Integer> joined = groupMapper.selectJoinedGroupsByUserId(userId);
        for (Group group: groups) {
            if (joined.contains(group.getGroup_id())) {
                continue;
            }
            GroupElement element = new GroupElement();
            element.setGroup_id(group.getGroup_id());
            element.setName(group.getName());
            element.setCreater_id(group.getPromoter_id());
            element.setMember_count(group.getMember_count());
            element.setContent(group.getContent());
            element.setPermitted_num(group.getPermitted_num());
            element.setIs_examine(group.is_examine());
            element.setImage(group.getImage());
            element.setTags(groupMapper.selectGroupTag(group.getGroup_id()));
            list.add(element);
        }
        if (!tag.isEmpty()) {
            list = list.stream().filter(e -> e.getTags().contains(tag)).toList();
        }
        return list;
    }

    public BasicInfoResponse applyJoinGroup(Integer userId, Integer groupId, String info) {
        if (groupMapper.getGroupJoinState(groupId,userId) != 0) {
            return new BasicInfoResponse(false,"您已在该团体中");
        }
        if (!groupMapper.selectExamineById(groupId)) {
            groupMapper.insertGroupMember(groupId,userId);
            groupMapper.updateGroupMember(groupId,1);
            return new BasicInfoResponse(true,"已成功加入");
        }
        if (messageMapper.selectApplyCount(groupId,userId) != 0) {
            return new BasicInfoResponse(false, "请勿重复申请");
        }
        Integer promoterId = groupMapper.selectPromoterIdByGroupId(groupId);
        messageMapper.insertApplyNotice(groupId, userId, info, promoterId);
        return new BasicInfoResponse(true,"等待审核中");
    }

    public boolean joinGroup(Integer userId, Integer groupId) {
        if (groupMapper.getGroupJoinState(groupId,userId) != 0) {
            return false;
        }
        groupMapper.insertGroupMember(groupId,userId);
        groupMapper.updateGroupMember(groupId,1);
        return true;
    }

    public List<GroupElement> getJoinedGroup(Integer userId) {
        List<Integer> groupIds = groupMapper.selectJoinedGroupsByUserId(userId);
        List<GroupElement> list = new ArrayList<>();
        for (Integer groupId: groupIds) {
            Group group = groupMapper.selectGroupById(groupId);
            GroupElement element = new GroupElement();
            element.setGroup_id(groupId);
            element.setName(group.getName());
            element.setCreater_id(group.getPromoter_id());
            element.setMember_count(group.getMember_count());
            element.setContent(group.getContent());
            element.setPermitted_num(group.getPermitted_num());
            element.setIs_examine(group.is_examine());
            element.setImage(group.getImage());
            groupMapper.selectGroupTag(groupId);
            element.setTags(groupMapper.selectGroupTag(groupId));
            list.add(element);
        }
        return list;
    }

    public BasicInfoResponse exitGroup(Integer userId, Integer groupId) {
        if (groupMapper.getGroupJoinState(groupId,userId) == 0) {
            return new BasicInfoResponse(false,"您不在该团体中");
        }

        if (groupMapper.selectPromoterIdByGroupId(groupId) == userId) {
            return new BasicInfoResponse(false,"群主不可退出");
        }

        groupMapper.deleteGroupMember(groupId, userId);
        groupMapper.updateGroupMember(groupId,-1);
        return new BasicInfoResponse(true,"");
    }

    public List<MemberElement> getGroupMember(Integer groupId) {
        List<Integer> users = groupMapper.selectMemberByGroupId(groupId);
        List<MemberElement> list = new ArrayList<>();
        for (Integer userId: users) {
            MemberElement element = new MemberElement();
            element.setUser_id(userId);
            element.setName(userMapper.getUserNameById(userId));
            element.setImage(userService.getUserHead(userId));
            list.add(element);
        }
        return list;
    }

    public GroupElement getGroupInfo(Integer groupId) {
        Group group = groupMapper.selectGroupById(groupId);
        GroupElement element = new GroupElement();
        element.setGroup_id(group.getGroup_id());
        element.setName(group.getName());
        element.setCreater_id(group.getPromoter_id());
        element.setMember_count(group.getMember_count());
        element.setContent(group.getContent());
        element.setPermitted_num(group.getPermitted_num());
        element.setIs_examine(group.is_examine());
        element.setImage(group.getImage());
        element.setTags(groupMapper.selectGroupTag(group.getGroup_id()));
        return element;
    }


}
