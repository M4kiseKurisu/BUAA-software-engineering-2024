package com.hxt.backend.response.sectionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectionElement {

    private Integer section_id;

    private String section_name;

    private Integer section_follower_count;

    private String section_introduction;

    private String section_academy;

    private String section_type;

    private boolean section_is_following;
}
