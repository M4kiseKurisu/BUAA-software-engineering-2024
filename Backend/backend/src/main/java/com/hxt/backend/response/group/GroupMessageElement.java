package com.hxt.backend.response.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupMessageElement {
    private Integer id;
    private Integer sender_id;
    private String sender_name;
    private String sender_avatar;
    private String content;
    private String time;
}
