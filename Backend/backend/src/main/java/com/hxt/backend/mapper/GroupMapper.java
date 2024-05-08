package com.hxt.backend.mapper;

import com.hxt.backend.entity.group.Group;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GroupMapper {

    //  插入学习团体
    @Options(useGeneratedKeys = true)
    @Insert("insert into study_group (name, promoter_id, member_count, content, permitted_num, is_examine) " +
            "VALUES (#{name}, #{promoter_id}, #{member_count}, #{content}, #{permitted_num}, #{is_examine});")
    int insertGroup(String name, Integer promoter_id, Integer member_count, String content, Integer permitted_num, Boolean is_examine);

    // 加tag
    @Options(useGeneratedKeys = true)
    @Insert("insert into group_tag (group_id, tag_id) VALUES (#{groupId}, #{tag_id});")
    int insertGroupTag(Integer groupId, Integer tag_id);

    @Delete("delete from study_group where group_id = #{groupId};")
    int deleteGroupById(Integer groupId);

    @Select("select promoter_id from study_group where group_id = #{groupId};")
    int selectPromoterIdByGroupId(Integer groupId);

    @Select("select * from study_group where permitted_num != member_count;")
    List<Group> selectGroup();

}
