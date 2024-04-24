package com.hxt.backend.response.singleInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSocialInfoResponse {
    private String name;
    private Integer user_id;
    private String user_avatar;
    private Integer following_count; //  关注人数
    private Integer follower_count; //  被关注人数（粉丝数）
    private Integer post_count;
    private Integer comment_count;
    private Integer like_count;
    private String sign;
    private boolean flag_follow;    //  是否关注了该用户
    private boolean flag_blocked;     //  是否被封禁
}
