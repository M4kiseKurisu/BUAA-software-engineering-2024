package com.hxt.backend.response.singleInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
    private Integer post_id;
    private String post_title;
    private String post_content;
    private String author_name;
    private Integer author_id;
    private String section;
    private List<String> post_tag;
}
