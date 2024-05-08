package com.hxt.backend.response.messageResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PrivateElement {
    private Integer id;
    private Integer sender_id;
    private Integer receiver_id;
    private String content;
    private String time;
    private Boolean is_read;
}
