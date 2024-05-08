package com.hxt.backend.response.progressionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DiscussionResponse {
    private boolean success;
    private Integer totalPosts;
    private List<Integer> author_id;
}
