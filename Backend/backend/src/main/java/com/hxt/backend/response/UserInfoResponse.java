package com.hxt.backend.response;

import com.hxt.backend.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoResponse {
    private String name;
    private String account;
    private String major;
    private String phone_number;
    private String email;
    private Integer enrollment_year;
    private String sign;

    public UserInfoResponse(User user) {
        if (user == null) {
            return;
        }
        this.name = user.getName();
        this.account = user.getAccount();
        this.major = user.getMajor();
        this.phone_number = user.getPhoneNum();
        this.email = user.getEmail();
        this.enrollment_year = user.getGraduateYear();
        this.sign = user.getSign();
    }
}
