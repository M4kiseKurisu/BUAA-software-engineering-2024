package com.hxt.backend.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "post_like")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pl_id")
    private Integer pl_id;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "post_id")
    private Integer post_id;
    @Column(name = "status")
    private Integer status;
    @Column(name = "time")
    private Timestamp likeTime;
}
