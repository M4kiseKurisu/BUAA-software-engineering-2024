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
public class PostListResponse {
    private Boolean success;
    private ArrayList<PostElement> posts;
}
