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
@Table(name = "group_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GroupMessage {
    @Id
    @Column(name = "group_message_id")
    private Integer group_message_id;

    @Column(name = "content")
    private String content;

    @Column(name = "group_id")
    private Integer group_id;

    @Column(name = "send_id")
    private Integer send_id;

    @Column(name = "time")
    private Timestamp time;
}
