package com.hxt.backend.entity.message;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "user_system_notice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_notice_id")
    private Integer user_notice_id;

    @Column(name = "is_read")
    private Boolean is_read;

    @Column(name = "system_notice_id")
    private Integer system_notice_id;

    @Column(name = "receiver_id")
    private Integer receiver_id;

    @Column(name = "pull_time")
    private Timestamp pull_time;
}
