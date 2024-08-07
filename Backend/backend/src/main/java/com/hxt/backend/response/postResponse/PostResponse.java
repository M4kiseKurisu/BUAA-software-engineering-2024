package com.hxt.backend.response.postResponse;

import com.hxt.backend.entity.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
    private boolean success;
    private Integer section_id;
    private String title;
    private String intro;
    private Integer author_id;
    private String author_name;
    private String author_head;
    private String content;
    private String create_time;
    private List<String> tags;
    //private List<String> images;
    private List<String> resources;
   // private List<CommentResponse> comments;
    private Integer likeCount; //点赞数
    private Integer collectCount; //收藏数
    private Integer commentCount;
    private Integer viewCount;
    
    public PostResponse (Post post){
        this.section_id = post.getSection_id();
        this.title = post.getTitle();
        this.intro = post.getIntro();
        this.author_id = post.getAuthor_id();
        this.content = post.getContent();
        String[] time = post.getPostTime().toString().split(":");
        this.create_time = time[0] + ":" + time[1];
        this.commentCount = post.getComment_count();
        this.likeCount = post.getLike_count();
        this.collectCount = post.getCollect_count();
        this.viewCount = post.getView_count();
    }
}
