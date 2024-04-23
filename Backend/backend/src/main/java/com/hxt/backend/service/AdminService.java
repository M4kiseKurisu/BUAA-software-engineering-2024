package com.hxt.backend.service;

import com.hxt.backend.mapper.SectionMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Resource
    SectionMapper sectionMapper;

    public boolean addCourse(String name, String intro, String type,
                             String academy, Integer credit, Integer capacity) {
        return sectionMapper.insertCourse(name, intro, type, academy, credit, capacity) > 0;
    }

    public boolean addSchool(String name, String intro, String category, String web) {
        return sectionMapper.insertSchool(name, intro, category, web) > 0;
    }
}
