package com.hxt.backend.response.messageResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

@AllArgsConstructor
@Setter
@Getter
public class NewNoticeResponse {
    private Boolean success;
    private String info;
    private Boolean result;
}
