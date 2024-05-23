package com.hxt.backend.service;

import com.hxt.backend.mapper.ResourceMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
@RequiredArgsConstructor
public class ResourceService {
    @Resource
    private ResourceMapper resourceMapper;
    
    
    public Integer uploadResource(String name, Integer publisherId, String url, String type, String md5) {
        if (url == null) {
            return -1;
        }
        Timestamp uploadTime = new Timestamp(System.currentTimeMillis());
        return resourceMapper.insertResource(name, publisherId, url, type, uploadTime, md5);
    }
    
    public Integer deleteResource(Integer id) {
        if (resourceMapper.getResource(id) == null) {
            return -1;
        }
        return resourceMapper.deleteResource(id);
    }
    
    public String getUrl(Integer id) {
        if (resourceMapper.getResource(id) == null) {
            return null;
        }
        return resourceMapper.getResource(id).getUrl();
    }
    
    public String getName(Integer id) {
        if (resourceMapper.getResource(id) == null) {
            return null;
        }
        return resourceMapper.getResource(id).getName();
    }
    
    public Integer getResourceIdByUrl(String url) {
        if (resourceMapper.getResourceIdByUrl(url) == null) {
            return null;
        }
        return resourceMapper.getResourceIdByUrl(url);
    }
}
