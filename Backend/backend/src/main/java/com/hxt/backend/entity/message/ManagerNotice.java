package com.hxt.backend.entity.message;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "manager_system_notice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ManagerNotice {
    @Id
    @Column(name = "system_notice_id")
    private Integer system_notice_id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_public")
    private Boolean is_public;

    @Column(name = "receiver_id")
    private Integer receiver_id;

    @Column(name = "publish_time")
    private Timestamp publish_time;

    @Column(name = "pushed")
    private Boolean pushed;
}
