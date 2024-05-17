package com.hxt.backend.entity.message;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "reply_notice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ReplyNotice {
    @Id
    private Integer rn_id;
    private Integer user_id;
    private Integer author_id;
    private String content;
    private Timestamp reply_time;
    private Boolean reply_to_post;
    private Integer post_id;
    private Integer comment_id;
}
