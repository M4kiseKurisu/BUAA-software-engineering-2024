package com.hxt.backend.response.postResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class StatusResponse {
    private boolean success;
    private String info;
    private Integer status;
}
