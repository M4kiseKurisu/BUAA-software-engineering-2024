package com.hxt.backend.response.sectionResponse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostElement {
    private Integer post_id;
    private Integer author_id;
    private String author_name;
    private String post_title;
    private String post_intro;
    private String post_content;
    private Integer post_favorites;
    private Integer post_likes;
    private List<String> tag_list;
    private String post_photo;
    private String post_time;
    @JsonIgnore
    private Timestamp post_reply_time;
}
