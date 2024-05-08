package com.hxt.backend.mapper;

import com.hxt.backend.entity.group.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GroupMapper {

    //  插入学习团体
    @Options(useGeneratedKeys = true, keyProperty = "group_id", keyColumn = "group_id")
    @Insert("insert into study_group (name, promoter_id, member_count, content, permitted_num, is_examine, image) " +
            "VALUES (#{name}, #{promoter_id}, #{member_count}, #{content}, #{permitted_num}, #{is_examine}, #{image});")
    int insertGroup(Group group);

    // tag
    @Options(useGeneratedKeys = true)
    @Insert("insert into group_tag (group_id, tag_id) VALUES (#{groupId}, #{tag_id});")
    int insertGroupTag(Integer groupId, Integer tag_id);


    @Select("SELECT t.name AS tag_name" +
            "FROM group_tag gt" +
            "         JOIN tag t ON gt.tag_id = t.tag_id" +
            "WHERE gt.group_id = #{group_id};")
    List<String> selectGroupTag(Integer group_id);

    @Delete("delete from study_group where group_id = #{groupId};")
    int deleteGroupById(Integer groupId);

    @Select("select promoter_id from study_group where group_id = #{groupId};")
    int selectPromoterIdByGroupId(Integer groupId);

    @Select("select * from study_group where permitted_num != member_count;")
    List<Group> selectGroup();

    List<Group> selectGroupByKeyword(String keyword);

    Group selectGroupById(Integer groupId);

    int updateGroupMember(Integer groupId, Integer num);

    boolean selectExamineById(Integer groupId);

    // 群体成员部分

    List<Integer> selectJoinedGroupsByUserId(Integer userId);

    List<Integer> selectMemberByGroupId(Integer groupId);

    int insertGroupMember(Integer groupId, Integer userId);

    int deleteGroupMember(Integer groupId, Integer userId);

    int getGroupJoinState(Integer groupId, Integer userId);
}
