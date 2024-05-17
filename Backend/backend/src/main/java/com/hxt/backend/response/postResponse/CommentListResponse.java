package com.hxt.backend.response.postResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CommentListResponse {
    private boolean success;
    private List<CommentResponse> comments;
}
