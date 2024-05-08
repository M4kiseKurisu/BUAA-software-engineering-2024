package com.hxt.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class SectionAuthorityResponse {
    List<Integer> teacher;
    List<Integer> assistant;
    List<Integer> others;
}
