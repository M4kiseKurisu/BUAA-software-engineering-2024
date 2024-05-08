package com.hxt.backend.response.checkInResponse;

import com.hxt.backend.entity.checkIn.CheckIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CheckInSquareIntroResponse {
    private String image_url;
    private String content;
    private Integer post_id;
    private Integer favors;
    private Integer author_id;
    private String author_name;
    private String author_avatar;
    
    public CheckInSquareIntroResponse(CheckIn checkIn) {
        this.content = checkIn.getContent();
        this.post_id = checkIn.getCheck_in_id();
        this.favors = checkIn.getLike_count();
        this.author_id = checkIn.getAuthor_id();
    }
}
