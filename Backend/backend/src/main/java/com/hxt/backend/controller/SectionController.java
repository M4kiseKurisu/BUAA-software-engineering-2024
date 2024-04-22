package com.hxt.backend.controller;

import com.hxt.backend.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;
    
}
