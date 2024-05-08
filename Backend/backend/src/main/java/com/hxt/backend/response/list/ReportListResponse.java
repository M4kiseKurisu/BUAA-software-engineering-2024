package com.hxt.backend.response.list;

import com.hxt.backend.response.singleInfo.ReportResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ReportListResponse {
    private int count;
    private List<ReportResponse> report;
}
