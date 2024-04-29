package com.hxt.backend.response.postResponse;

import com.hxt.backend.entity.post.Post;
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
    
    public PostIntroResponse(Post post) {
        this.post_id = post.getPost_id();
        this.post_title = post.getTitle();
        this.post_intro = post.getIntro();
        this.post_author_id = post.getAuthor_id();
        String[] time = post.getPostTime().toString().split(":");
        this.post_create_time = time[0] + ":" + time[1];
    }
    
}
