package com.hxt.backend.entity.post;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comment_resource")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer crId;
    private Integer commentId;
    private Integer resourceId;
}
