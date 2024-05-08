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

    @Select("select * from study_group where name like '%${keyword}%';")
    List<Group> selectGroupByKeyword(String keyword);

    @Select("select * from study_group where group_id = #{groupId}")
    Group selectGroupById(Integer groupId);

    @Update("update study_group" +
            "set member_count = member_count + #{num}" +
            "where group_id = #{groupId};")
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
    @Select("insert into apply_group (user_id, group_id, content) VALUES (#{userId},#{groupId},#{content});")
    int insertGroupJoinApply(Integer groupId, Integer userId, String content);
}
