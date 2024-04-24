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
    private Integer rlId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "reply_id")
    private Integer replyId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time")
    private Timestamp likeTime;
}
