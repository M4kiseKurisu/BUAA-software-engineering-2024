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
    private Integer message_sender_id;
    private Integer message_receiver_id;
    private String message_content;
    private String message_time;
    private Boolean is_read;
}
