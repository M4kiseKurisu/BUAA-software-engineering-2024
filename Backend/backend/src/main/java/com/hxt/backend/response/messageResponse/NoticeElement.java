package com.hxt.backend.response.messageResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoticeElement {
    Integer notice_id;
    String notice_user_name;
    String notice_title;
    String notice_content;
    String notice_time;
}
