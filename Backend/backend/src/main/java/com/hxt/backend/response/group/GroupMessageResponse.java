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
public class GroupMessageResponse {
    private boolean success;
    private String info;
    private Integer message_count;
    private List<GroupMessageElement> message_list;
}
