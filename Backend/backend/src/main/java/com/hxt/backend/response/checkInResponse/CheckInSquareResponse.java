package com.hxt.backend.response.checkInResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CheckInSquareResponse {
    private List<CheckInSquareIntroResponse> posts;
}
