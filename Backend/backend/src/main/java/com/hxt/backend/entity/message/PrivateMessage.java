package com.hxt.backend.entity.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


import java.sql.Timestamp;

@Entity
@Table(name = "private_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PrivateMessage {
    @Id
    @Column(name = "private_message_id")
    private Integer private_message_id;

    @Column(name = "content")
    private String content;

    @Column(name = "sender_id")
    private Integer sender_id;

    @Column(name = "receiver_id")
    private Integer receiver_id;

    @Column(name = "send_time")
    private Timestamp send_time;

    @Column(name = "is_read")
    Boolean is_read;
}
