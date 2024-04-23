package com.hxt.backend.controller;

import com.hxt.backend.response.sectionResponse.SearchSectionResponse;
import com.hxt.backend.response.sectionResponse.SectionElement;
import com.hxt.backend.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @GetMapping("/section/search")
    public SearchSectionResponse searchSection(
            @RequestParam(name = "keyword", defaultValue = "") String keyWord,
            @RequestParam(name = "sort", defaultValue = "0") String sort,
            @RequestParam(name = "type", defaultValue = "0") String type,
            @RequestParam(name = "academy", defaultValue = "") String academy
    ) {
        System.out.println("111111");
        ArrayList<SectionElement> list = sectionService.searchSection(keyWord,Integer.parseInt(sort),
                Integer.parseInt(type),academy);
        if (list.isEmpty()) {
            return new SearchSectionResponse(true,"未检索到响应结果",0,list);
        }
        else {
            return new SearchSectionResponse(true,"", list.size(), list);
        }
    }

    @GetMapping("/section/hots")
    public SearchSectionResponse getHotSection() {
        ArrayList<SectionElement> list = sectionService.getHotSections();
        if (list.isEmpty()) {
            return new SearchSectionResponse(true,"未检索到响应结果",0,list);
        }
        else {
            return new SearchSectionResponse(true,"", list.size(), list);
        }
    }
    
}
