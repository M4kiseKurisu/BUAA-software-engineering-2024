package com.hxt.backend.response.progressionResponse;

import com.hxt.backend.response.postResponse.PostIntroResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PostListResponse {
    private boolean success;
    private List<PostIntroResponse> posts;
}
