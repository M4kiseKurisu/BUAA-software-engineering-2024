package com.hxt.backend.service;

import com.hxt.backend.mapper.ImageMapper;
import com.hxt.backend.mapper.TagMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    @Resource
    private TagMapper tagMapper;
    
    
    public Integer addTag(String name) {
        if (name == null) {
            return -1;
        }
        return tagMapper.insertTag(name);
    }
    
    public Integer deleteTag(Integer id) {
        if (tagMapper.getTag(id) == null) {
            return -1;
        }
        return tagMapper.deleteTag(id);
    }
    
    public String getName(Integer id) {
        if (tagMapper.getTag(id) == null) {
            return null;
        }
        return tagMapper.getTag(id).getName();
    }
}
