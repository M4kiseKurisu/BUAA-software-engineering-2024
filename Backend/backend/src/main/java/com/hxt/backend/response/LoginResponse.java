package com.hxt.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private boolean success;
    private Integer id;
    private String info;
    private String token;
}
