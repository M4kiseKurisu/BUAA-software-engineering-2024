package com.hxt.backend.response.checkInResponse;

import com.hxt.backend.entity.checkIn.CheckIn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CheckInDetailResponse {
    private Integer poster_id;
    private String poster_avatar;
    private String poster_name;
    private String poster_sign;
    private String poster_time;
    private String poster_content;
    private List<String> image_urls;
    private List<FavorsInfoResponse> favors_list;
    private List<CommentInfoResponse> comments_list;
    
    public CheckInDetailResponse(CheckIn checkIn) {
        this.poster_id = checkIn.getAuthor_id();
        this.poster_content = checkIn.getContent();
        String[] time = checkIn.getTime().toString().split(":");
        this.poster_time = time[0] + ":" + time[1];
    }
}
