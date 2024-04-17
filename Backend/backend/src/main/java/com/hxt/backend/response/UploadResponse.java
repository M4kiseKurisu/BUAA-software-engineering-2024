package com.hxt.backend.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UploadResponse {
    private boolean success;
    private String info;
    private String url;
}
