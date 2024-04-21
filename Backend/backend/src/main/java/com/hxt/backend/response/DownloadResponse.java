package com.hxt.backend.response;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class DownloadResponse {
    private boolean success;
    private String info;
    private String url;
}
