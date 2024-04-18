package com.hxt.backend.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    private String category; //帖子类型
    private Integer sectionId;
    private Integer publisherId; //发帖人id
    private Integer likeCount; //点赞数
    private Integer collectCount; //收藏数
    private Integer commentCount;
    private Integer viewCount;
    private Timestamp postTime;
    
}
