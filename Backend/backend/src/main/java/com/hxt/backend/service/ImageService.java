package com.hxt.backend.service;

import com.hxt.backend.mapper.ImageMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ImageService {
    @Autowired
    private ImageMapper imageMapper;
    
    
    public Integer uploadImage(String url) {
        if (url == null) {
            return -1;
        }
        return imageMapper.insertImage(url);
    }
}
