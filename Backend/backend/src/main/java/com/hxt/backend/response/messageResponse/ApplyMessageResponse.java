package com.hxt.backend.response.messageResponse;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplyMessageResponse {
    private Boolean success;
    private String info;
    private Integer apply_count;
    private ArrayList<ApplyElement> apply_list;
}
