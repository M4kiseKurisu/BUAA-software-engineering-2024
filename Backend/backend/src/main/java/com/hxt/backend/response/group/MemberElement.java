package com.hxt.backend.response.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberElement {
    private Integer user_id;
    private String image;
    private String name;
}
