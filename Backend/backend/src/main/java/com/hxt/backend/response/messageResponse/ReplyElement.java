package com.hxt.backend.response.messageResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyElement {
    private Integer reply_id;
    private Integer reply_user_id;
    private String reply_user_name;
    private Boolean reply_to_post;
    private String reply_content;
    private String reply_time;
    private Integer post_id;
    private String post_title;
    private Integer comment_id;
    private String comment_content;
}
