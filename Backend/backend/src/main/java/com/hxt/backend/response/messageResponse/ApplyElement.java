package com.hxt.backend.response.messageResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplyElement {
    private Integer apply_id;
    private Integer user_id;
    private Integer group_id;
    private Integer group_leader_id;
    private String apply_title;
    private String apply_content;
    private Boolean is_apply_feedback;
    private Boolean apply_feedback_info;
    private String group_name;
    private String user_name;
    private String user_avatar;
}
