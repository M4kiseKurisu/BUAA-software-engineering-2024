package com.hxt.backend.response.postResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@Getter
@Setter
public class PostIntroResponse {
    private Integer post_id;
    private String post_title;
    private String post_intro;
    private Integer post_author_id;
    private String post_author_name;
    private List<String> tags;
    private String post_create_time;
}
