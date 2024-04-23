package com.hxt.backend.response.postResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class WritePostResponse {
    private boolean success;
    private String info;
    private Integer postId;
}
