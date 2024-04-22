package com.hxt.backend.response.sectionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SectionInfoResponse {
    private String section_name;
    private Integer post_count;
    private Integer follower_count;
    private String section_introduction;
    private Timestamp create_time;
    private ArrayList<TeacherElement> teachers;
    private ArrayList<AssistantsElement> assistants;
}
