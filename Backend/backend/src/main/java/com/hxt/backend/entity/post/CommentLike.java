package com.hxt.backend.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "comment_like")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cl_id")
    private Integer cl_id;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "comment_id")
    private Integer comment_id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time")
    private Timestamp likeTime;
}
