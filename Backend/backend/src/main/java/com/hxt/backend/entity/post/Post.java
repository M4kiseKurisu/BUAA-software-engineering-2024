package com.hxt.backend.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer post_id;
    private String title;
    private String intro;
    private String content;
    private Integer category; //帖子类型 0：普通，1：资源
    private Integer section_id;
    @Column(name = "author_id")
    private Integer author_id; //发帖人id
    private Integer like_count; //点赞数
    private Integer collect_count; //收藏数
    private Integer comment_count;
    private Integer view_count;
    @Column(name = "time")
    private Timestamp postTime;
    private String cover;
}
