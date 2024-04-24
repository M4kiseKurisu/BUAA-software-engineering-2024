package com.hxt.backend.response.postResponse;

import com.hxt.backend.entity.post.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class CommentResponse {
    private Integer comment_id;
    private Integer comment_author_id;
    private String comment_author_head;
    private String comment_author_name;
    private String comment_content;
    private String comment_create_time;
    private List<String> comment_images;
    private Map<Integer, String> comment_resources;
    private Integer comment_like_count;
    private Integer comment_reply_count;
    private boolean comment_isLike;
    private List<ReplyResponse> replies;
    
    public CommentResponse(Comment comment) {
        this.comment_id = comment.getComment_id();
        this.comment_author_id = comment.getAuthor_id();
        this.comment_content = comment.getContent();
        this.comment_create_time = comment.getCommentTime().toString();
        this.comment_like_count = comment.getLike_count();
        this.comment_reply_count = comment.getReply_count();
    }
}
