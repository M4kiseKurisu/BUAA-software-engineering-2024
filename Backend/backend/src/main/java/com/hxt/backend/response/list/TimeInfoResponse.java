package com.hxt.backend.response.list;

import com.hxt.backend.response.singleInfo.TimeValueResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class TimeInfoResponse {
    private ArrayList<TimeValueResponse> post_24h;
    private ArrayList<TimeValueResponse> post_30d;
    private ArrayList<TimeValueResponse> comment_24h;
    private ArrayList<TimeValueResponse> comment_30d;
    private ArrayList<TimeValueResponse> checkin_24h;
    private ArrayList<TimeValueResponse> checkin_30d;
}
