package com.hxt.backend.service;

import com.hxt.backend.mapper.SectionMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectionService {

    @Resource
    private SectionMapper sectionMapper;




    public boolean focusSection(Integer userId, Integer sectionId) {
        return false;
    }

    public boolean unfocusSection(Integer userId, Integer sectionId) {
        return false;
    }

}
