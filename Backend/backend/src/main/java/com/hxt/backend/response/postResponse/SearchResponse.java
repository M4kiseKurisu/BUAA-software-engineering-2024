package com.hxt.backend.response.postResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SearchResponse {
    private boolean success;
    private String info;
    private List<PostIntroResponse> postIntroResponses;
}
