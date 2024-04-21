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
public class PrivateMessageResponse {
    private Boolean success;
    private String info;
    private Integer message_count;
    private ArrayList<PrivateElement> message_list;
}
