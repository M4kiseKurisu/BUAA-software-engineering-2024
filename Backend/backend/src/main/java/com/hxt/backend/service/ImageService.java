package com.hxt.backend.service;

import com.hxt.backend.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ImageService {
    @Autowired
    private ImageMapper imageMapper;
    
    
    public Integer uploadImage(String url, String md5) {
        if (url == null) {
            return -1;
        }
        return imageMapper.insertImage(url, md5);
    }
    
    public Integer deleteImage(Integer id) {
        if (imageMapper.seekImage(id) == null) {
            return -1;
        }
        return imageMapper.deleteImage(id);
    }
    
    public String getUrl(Integer id) {
        if (imageMapper.seekImage(id) == null) {
            return null;
        }
        return imageMapper.seekImage(id).getUrl();
    }
    
    public Integer getImageIdByUrl(String url) {
        if (imageMapper.getImageIdByUrl(url) == null) {
            return null;
        }
        return imageMapper.getImageIdByUrl(url);
    }
    
    
   
}
