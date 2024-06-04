package com.hxt.backend.response.singleInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSectionBlockResponse {
    private Integer user_id;
    private String user_name;
    private Integer section_id;
    private String section_name;
    private String block_time;
    private Integer block_days;

    @JsonIgnore
    private Timestamp block_timestamp;
}
