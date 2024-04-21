package com.hxt.backend.entity.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_resource")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer prId;
    private Integer postId;
    private Integer resourceId;
}
