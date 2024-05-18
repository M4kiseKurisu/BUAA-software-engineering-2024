package com.hxt.backend.response.singleInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TotalInfoResponse {
    Integer login_24;
    Integer post_24;
    Integer content_24;
    Integer user_all;
    Integer course_all;
    Integer school_all;
    Integer post_all;
    Integer content_all;
    Integer group_all;
    Integer checkin_all;
}
