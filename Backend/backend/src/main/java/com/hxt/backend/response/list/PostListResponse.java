package com.hxt.backend.response.list;

import com.hxt.backend.response.singleInfo.PostResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PostListResponse {
    private int post_count;
    private List<PostResponse> posts;
}
