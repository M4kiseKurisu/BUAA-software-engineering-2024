package com.hxt.backend.response.messageResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyMessageResponse {
    private Boolean success;
    private String info;
    private Integer reply_count;
    private ArrayList<ReplyElement> reply_list;
}
