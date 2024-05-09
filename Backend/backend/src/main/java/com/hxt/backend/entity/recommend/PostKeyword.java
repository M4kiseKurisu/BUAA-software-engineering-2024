package com.hxt.backend.entity.recommend;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_keyword")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PostKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk_id;
    private Integer post_id;
    private String title_keyword;
    private String intro_keyword;
    private String content_keyword;
}
