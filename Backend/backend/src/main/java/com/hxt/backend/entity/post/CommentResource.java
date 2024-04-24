package com.hxt.backend.entity.post;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Options;

@Entity
@Table(name = "Comment_resource")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cr_id")
    private Integer cr_id;
    @Column(name = "comment_id")
    private Integer comment_id;
    @Column(name = "resource_id")
    private Integer resource_id;
}
