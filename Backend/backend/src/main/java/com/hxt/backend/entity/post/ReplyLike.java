package com.hxt.backend.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "reply_like")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)

public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rl_id")
    private Integer rl_id;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "reply_id")
    private Integer reply_id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time")
    private Timestamp likeTime;
}
