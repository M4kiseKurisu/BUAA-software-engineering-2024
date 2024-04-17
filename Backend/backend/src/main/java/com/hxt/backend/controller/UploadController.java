package com.hxt.backend.controller;
import com.hxt.backend.response.UploadResponse;
import com.hxt.backend.service.ImageService;
import com.hxt.backend.service.ObsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController

public class UploadController {
    
    @Autowired
    private ObsService obsService;
    private ImageService imageService = new ImageService();
    
    @RequestMapping ( "/upload")
    public UploadResponse uploadImage(@RequestParam("file") MultipartFile file) {
        // 上传文件到云服务器并返回图片在云服务器上的 URL
        String url = obsService.uploadFile(file);
        
        // 将图片的 URL 保存到数据库
        Integer response = imageService.uploadImage(url);
        
        // 返回图片 URL 给前端
        boolean isSuccess = (response == 1);
        String info = isSuccess ? "上传成功！" :
                response == -1? "上传出错，请重新上传！" : "服务器错误！";
        
        return new UploadResponse(isSuccess, info, url);
    }
}

