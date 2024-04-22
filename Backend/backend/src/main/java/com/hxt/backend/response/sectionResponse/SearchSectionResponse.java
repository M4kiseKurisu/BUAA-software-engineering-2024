package com.hxt.backend.response.sectionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchSectionResponse {

    private Boolean success;

    private  String info;

    private  Integer section_count;

    private ArrayList<SectionElement> sections;
}
