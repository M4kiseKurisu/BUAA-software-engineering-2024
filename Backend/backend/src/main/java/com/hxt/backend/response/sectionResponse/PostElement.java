package com.hxt.backend.response.sectionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostElement {
    private Integer post_id;
    private Integer author_id;
    private String author_name;
    private String post_title;
    private String post_content;
    private Integer post_favorites;
    private Integer post_like;
    private ArrayList<String> tag_list;
    private String post_photo;
}
