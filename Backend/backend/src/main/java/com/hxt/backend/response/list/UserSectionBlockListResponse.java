package com.hxt.backend.response.list;

import com.hxt.backend.response.singleInfo.UserSectionBlockResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class UserSectionBlockListResponse {
    private Integer count;
    private List<UserSectionBlockResponse> info;
}
