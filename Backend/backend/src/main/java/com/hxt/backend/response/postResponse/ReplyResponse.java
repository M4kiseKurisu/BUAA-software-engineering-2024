package com.hxt.backend.response.postResponse;

import com.hxt.backend.entity.post.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class ReplyResponse {
    private Integer reply_id;
    private Integer replied_author_id;
    private Integer reply_author_id;
    private String reply_author_head;
    private String reply_author_name;
    private String reply_content;
    private String reply_create_time;
    private Integer reply_like_count;
    
    public ReplyResponse(Reply reply) {
        this.reply_id = reply.getReplyId();
        this.replied_author_id = reply.getRepliedAuthorId();
        this.reply_author_id = reply.getAuthorId();
        this.reply_content = reply.getContent();
        this.reply_create_time = reply.getReplyTime().toString();
        this.reply_like_count = reply.getLikeCount();
    }
}
