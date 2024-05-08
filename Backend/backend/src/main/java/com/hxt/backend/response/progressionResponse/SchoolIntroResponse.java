package com.hxt.backend.response.progressionResponse;

import com.hxt.backend.entity.progression.School;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class SchoolIntroResponse {
    private Integer school_id;
    private String school_name;
    private String school_badge;
    
    public SchoolIntroResponse(School school) {
        this.school_id = school.getSchool_id();
        this.school_name = school.getName();
        this.school_badge = school.getBadge();
    }
}
