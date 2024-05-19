package com.hxt.backend.mapper;

import com.hxt.backend.entity.group.Group;
import com.hxt.backend.entity.group.GroupApply;
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


    @Select("SELECT t.name " +
            "FROM group_tag gt " +
            "JOIN tag t ON gt.tag_id = t.tag_id " +
            "WHERE gt.group_id = #{group_id};")
    List<String> selectGroupTag(Integer group_id);

    @Delete("delete from study_group where group_id = #{groupId};")
    int deleteGroupById(Integer groupId);

    @Select("select promoter_id from study_group where group_id = #{groupId};")
    int selectPromoterIdByGroupId(Integer groupId);

    @Select("select * from study_group where permitted_num != member_count;")
    List<Group> selectGroup();

    @Select("select * from study_group where name like '%${keyword}%';")
    List<Group> selectGroupByKeyword(String keyword);

    @Select("select * from study_group where group_id = #{groupId}")
    Group selectGroupById(Integer groupId);

    @Update("update study_group" +
            " set member_count = member_count + #{num}" +
            " where group_id = #{groupId};")
    int updateGroupMember(Integer groupId, Integer num);


    @Select("select is_examine from study_group where group_id = #{groupId};")
    boolean selectExamineById(Integer groupId);

    // 群体成员部分

    @Select("select group_id from group_member where user_id = #{userId};")
    List<Integer> selectJoinedGroupsByUserId(Integer userId);

    @Select("select user_id from group_member where group_id = #{groupId};")
    List<Integer> selectMemberByGroupId(Integer groupId);

    @Options(useGeneratedKeys = true)
    @Insert("insert into group_member (user_id, group_id) VALUES (#{userId},#{groupId});")
    int insertGroupMember(Integer groupId, Integer userId);

    @Delete("delete from group_member where user_id = #{userId} and group_id = #{groupId};")
    int deleteGroupMember(Integer groupId, Integer userId);

    @Select("select count(*) from group_member where user_id = #{userId} and group_id = #{groupId};")
    int getGroupJoinState(Integer groupId, Integer userId);

    @Options(useGeneratedKeys = true)
    @Select("insert into apply_group (user_id, group_id, content, pushed) VALUES (#{userId},#{groupId},#{content}, false);")
    int insertGroupJoinApply(Integer groupId, Integer userId, String content);

    @Update("update apply_group " +
            "set pushed = true " +
            "where ag_id = #{ag_id};")
    int updateApplyPushState(Integer ag_id);

    @Select("select * from apply_group where pushed = false;")
    List<GroupApply> selectUnpushedApply();

    //  删除小组的附加信息（若直接删除组不会导致外键异常，则以下方法不需要）
    @Delete("DELETE FROM apply_group WHERE group_id = #{id}")
    int deleteGroupApply(Integer id);

    @Delete("DELETE FROM group_member WHERE group_id = #{id}")
    int deleteGroupMemberAll(Integer id);

    @Delete("DELETE FROM group_tag WHERE group_id = #{id}")
    int deleteGroupTag(Integer id);

    @Delete("DELETE FROM group_message WHERE group_id = #{id}")
    int deleteGroupMessage(Integer id);

    @Delete("DELETE FROM apply_notice WHERE group_id = #{id}")
    int deleteGroupApplyNotice(Integer id);

    //  数据可视化用
    @Select("SELECT COUNT(*) FROM study_group")
    Integer getGroupCount();
}
