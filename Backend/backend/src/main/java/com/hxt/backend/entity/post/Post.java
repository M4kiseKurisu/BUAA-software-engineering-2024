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
    private Integer postId;
    private String title;
    private String content;
    private Integer category; //帖子类型 0：普通，1：资源
    private Integer sectionId;
    private Integer authorId; //发帖人id
    private Integer likeCount; //点赞数
    private Integer collectCount; //收藏数
    private Integer commentCount;
    private Integer viewCount;
    private Timestamp postTime;
    
}
