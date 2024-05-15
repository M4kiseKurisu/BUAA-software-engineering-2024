package com.hxt.backend.response.checkInResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CheckInDaysResponse {
    private boolean success;
    private Integer days;
}
