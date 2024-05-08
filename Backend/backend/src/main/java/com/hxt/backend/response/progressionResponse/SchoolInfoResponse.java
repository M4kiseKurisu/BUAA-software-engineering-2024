package com.hxt.backend.response.progressionResponse;

import com.hxt.backend.entity.progression.School;
import com.hxt.backend.response.postResponse.PostIntroResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SchoolInfoResponse {
    private boolean success;
    private String school_name;
    private String school_badge;
    private String school_intro;
    private String school_web;
    private List<PostIntroResponse> posts;
    
    public SchoolInfoResponse(School school) {
        this.school_name = school.getName();
        this.school_badge = school.getBadge();
        this.school_intro = school.getIntro();
        this.school_web = school.getWeb();
    }
}
