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
    private Integer author_id;
    private String author_name;
    private Integer post_favorites;
    private Integer post_likes;
    private List<String> tag_list;
    private String post_time;
    private String post_photo;
    
    
    public PostIntroResponse(Post post) {
        this.post_id = post.getPost_id();
        this.post_title = post.getTitle();
        this.post_intro = post.getIntro();
        this.author_id = post.getAuthor_id();
        String[] time = post.getPostTime().toString().split(":");
        this.post_time = time[0] + ":" + time[1];
        this.post_favorites = post.getCollect_count();
        this.post_likes = post.getLike_count();
    }
    
}
