package com.hxt.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BasicInfoResponse {
    private boolean success;
    private String info;
}
