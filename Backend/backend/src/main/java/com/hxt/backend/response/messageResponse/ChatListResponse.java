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
public class ChatListResponse {
    private Boolean success;
    private String info;
    private Integer chat_count;
    private ArrayList<ChatElement> chat_list;
}
