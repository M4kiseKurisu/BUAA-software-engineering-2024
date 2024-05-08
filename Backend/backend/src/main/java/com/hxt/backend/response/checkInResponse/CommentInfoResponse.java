package com.hxt.backend.response.checkInResponse;

import com.hxt.backend.entity.checkIn.CheckInComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CommentInfoResponse {
    private Integer writer_id;
    private String writer_name;
    private String writer_avatar;
    private String comment_content;
    private Integer comment_id;
    
    public CommentInfoResponse (CheckInComment comment) {
        this.writer_id = comment.getAuthor_id();
        this.comment_content = comment.getContent();
        this.comment_id = comment.getCic_id();
    }
    
}
