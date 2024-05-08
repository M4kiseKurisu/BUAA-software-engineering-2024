package com.hxt.backend.response.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupSearchResponse {
    private boolean success;
    private String info;
    private Integer group_count;
    private List<GroupElement> group;
}
