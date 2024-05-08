package com.hxt.backend.response.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupElement {
    private Integer group_id;
    private String name;
    private Integer creater_id;
    private Integer member_count;
    private String content;
    private Integer permitted_num;
    private Boolean is_examine;
    private String image;
    private List<String> tags;
}
