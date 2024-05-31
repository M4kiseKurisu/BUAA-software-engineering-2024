package com.hxt.backend.response.messageResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatElement {
    private Integer id;
    private Integer sender_id;
    private Integer receiver_id;
    private String last_message_content;
    private String last_message_time;
    private Boolean is_read;
}
