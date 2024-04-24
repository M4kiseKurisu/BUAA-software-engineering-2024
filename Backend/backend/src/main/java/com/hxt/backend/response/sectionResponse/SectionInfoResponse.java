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
    private Boolean success;
    private String course_name;
    private String course_type;
    private Integer course_credit;
    private Integer course_capacity;
    private Integer course_follows;
    private Integer course_posts;
    private String course_info;
    private String course_college;
    private ArrayList<TeacherElement> teachers;
    private ArrayList<Integer> assistants;
}
