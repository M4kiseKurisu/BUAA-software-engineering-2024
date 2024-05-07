package com.hxt.backend.response.singleInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Setter
public class TimeValueResponse {
    private String time;
    private Integer value;
}
