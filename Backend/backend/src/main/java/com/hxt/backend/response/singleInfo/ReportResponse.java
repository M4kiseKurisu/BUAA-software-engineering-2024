package com.hxt.backend.response.singleInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportResponse {
    private Integer report_id;
    private Integer target_id;
    private String target_info;
    private Integer target_belong_id;
    private Integer target_user;
    private Integer reporter;
    private String detail;
    private String resource;
}
