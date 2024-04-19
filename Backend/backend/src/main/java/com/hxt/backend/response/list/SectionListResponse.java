package com.hxt.backend.response.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class SectionListResponse {
    private int count;
    private List<Map<String, Object>> sections;
}
