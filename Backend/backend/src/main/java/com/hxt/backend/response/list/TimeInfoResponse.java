package com.hxt.backend.response.list;

import com.hxt.backend.response.singleInfo.TimeValueResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class PostTimeInfoResponse {
    private ArrayList<TimeValueResponse> post_24h;
    private ArrayList<TimeValueResponse> post_30d;
    private ArrayList<TimeValueResponse> comment_24h;
    private ArrayList<TimeValueResponse> comment_30d;
}
