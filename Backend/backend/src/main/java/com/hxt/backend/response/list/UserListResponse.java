package com.hxt.backend.response.list;

import com.hxt.backend.response.singleInfo.UserSocialInfoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class UserListResponse {
    private int count;
    private ArrayList<UserSocialInfoResponse> user;
}
