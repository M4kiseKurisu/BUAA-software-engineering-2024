package com.hxt.backend.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_image")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ci_id")
    private Integer ciId;
    @Column(name = "comment_id")
    private Integer commentId;
    @Column(name = "image_id")
    private Integer imageId;
}
