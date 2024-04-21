package com.hxt.backend.entity.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "private_chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PrivateChat {

    @Id
    @Column(name = "private_chat_id")
    private Integer private_chat_id;

    @Column(name = "sender_id")
    private Integer sender_id;

    @Column(name = "receiver_id")
    private Integer receiver_id;

    @Column(name = "last_message_content")
    private String last_message_content;

    @Column(name = "last_message_time")
    private Timestamp last_message_time;

    @Column(name = "is_read")
    private Boolean is_read;
}
