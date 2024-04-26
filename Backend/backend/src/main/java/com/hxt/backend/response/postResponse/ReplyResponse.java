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
    private String replied_author_name;
    private Integer reply_author_id;
    private String reply_author_head;
    private String reply_author_name;
    private String reply_content;
    private String reply_create_time;
    private Integer reply_like_count;
    private boolean reply_isLike;
    
    public ReplyResponse(Reply reply) {
        this.reply_id = reply.getReply_id();
        this.replied_author_id = reply.getReplied_author_id();
        this.reply_author_id = reply.getAuthor_id();
        this.reply_content = reply.getContent();
        String[] time = reply.getReplyTime().toString().split(":");
        this.reply_create_time = time[0] + ":" + time[1];
        this.reply_like_count = reply.getLike_count();
    }
}
