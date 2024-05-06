package com.hxt.backend.response.progressionResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SchoolIntroListResponse {
    private boolean success;
    private List<SchoolIntroResponse> schools;
}
