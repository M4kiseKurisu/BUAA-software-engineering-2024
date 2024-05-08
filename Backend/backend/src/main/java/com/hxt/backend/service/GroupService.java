package com.hxt.backend.service;

import com.hxt.backend.entity.group.Group;
import com.hxt.backend.mapper.GroupMapper;
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

    public BasicInfoResponse deleteGroup(Integer userId, Integer groupId) {
        int promoterId = groupMapper.selectPromoterIdByGroupId(groupId);
        if (promoterId != userId) {
            return new BasicInfoResponse(false,"无操作权限");
        }
        groupMapper.deleteGroupById(groupId);
        return new BasicInfoResponse(true,"");
    }

    public List<GroupElement> getGroupList() {
        List<Group> groups = groupMapper.selectGroup();
        List<GroupElement> list = new ArrayList<>();
        for (Group group: groups) {
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

    public List<GroupElement> searchGroup(String keyword, String tag) {
        List<GroupElement> list = new ArrayList<>();
        if (keyword.isEmpty()) {

        }
        else if (tag.isEmpty()) {

        }
        else {

        }
        return list;
    }

    public BasicInfoResponse applyJoinGroup(Integer userId, Integer groupId, String info) {

        return null;
    }

    public List<GroupElement> getJoinedGroup(Integer userId) {

        return null;
    }

    public BasicInfoResponse exitGroup(Integer userId, Integer groupId) {

        return null;
    }

    public List<MemberElement> getGroupMember(Integer groupId) {

        return null;
    }

    public GroupElement getGroupInfo(Integer groupId) {

        return null;
    }


}
