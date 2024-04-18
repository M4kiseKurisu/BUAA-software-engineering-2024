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
public class NoticeMessageResponse {
    private boolean success;
    private String info;
    private int notice_count;
    private ArrayList<NoticeElement> notice_list;
}