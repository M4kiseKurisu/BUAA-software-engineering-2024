package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.DownloadResponse;
import com.hxt.backend.response.UploadResponse;
import com.hxt.backend.service.ImageService;
import com.hxt.backend.service.ObsService;
import com.hxt.backend.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class UploadController {
    
    
    private final ObsService obsService;
    
    
    private final ImageService imageService;
    
    
    private final ResourceService resourceService;
    
    @RequestMapping (value="/posts/write/uploadImage")
    public UploadResponse uploadImage(
            @RequestParam(name = "file", required = false) MultipartFile file
    ) {
        if (file.isEmpty()) {
            return new UploadResponse(false, "图片为空", "");
        }
        // 上传文件到云服务器并返回图片在云服务器上的 URL
        String[] res = obsService.uploadFile(file);
        String url = res[0];
        String md5 = res[1];
        Integer response = 1;
        
        // 将图片的 URL 保存到数据库
        if (!md5.isEmpty()) {
            response = imageService.uploadImage(url, md5);
        }
        
        // 返回图片 URL 给前端
        boolean isSuccess = (response == 1);
        String info = isSuccess ? "上传成功！" :
                response == -1? "上传出错，请重新上传！" : "服务器错误！";
        
        return new UploadResponse(isSuccess, info, url);
    }
    
    @RequestMapping (value="/posts/write/deleteImage")
    public BasicInfoResponse deleteImage(
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, "所选图片不存在");
        }
        //在数据库获取图片url
        String url = imageService.getUrl(id);
        //在数据库删除图片
        Integer response = imageService.deleteImage(id);
        //在OBS删除文件
        boolean isObsDelete = obsService.deleteFile(url);
        
        if (response == 1 && isObsDelete) {
            return new BasicInfoResponse(true, "删除成功");
        } else {
            return new BasicInfoResponse(false, "删除出错");
        }
        
    }
    
    @RequestMapping (value="/posts/downloadImage")
    public DownloadResponse downloadImage(
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new DownloadResponse(false, "请求为空", null);
        }
        //在数据库获取图片url
        String imageUrl = imageService.getUrl(id);
        //在数据库删除图片
        if (imageUrl == null) {
            // 处理图片不存在的情况
            return new DownloadResponse(false, "图片不存在", null);
        }
    
        /*
        Resource resource;
        try {
            resource = new UrlResource(imageUrl);
        } catch (MalformedURLException e) {
            // 处理URL格式错误的情况
            e.printStackTrace();
            return new DownloadResponse(false, "下载出错", null);
        }
        */
        
       return new DownloadResponse(true, "下载成功", imageUrl);
        
    }
    
    @PostMapping (value="/posts/write/uploadResource")
    public UploadResponse uploadResource(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "publisher_id", required = false) Integer publisherId,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam(name = "type", required = false) String type
    ) {
        
        if (file.isEmpty()) {
            return new UploadResponse(false, "文件为空", "");
        }

        // 上传文件到云服务器并返回图片在云服务器上的 URL
        String[] res = obsService.uploadFile(file);
        String url = res[0];
        String md5 = res[1];
        Integer response = 1;

        // 将图片的 URL 保存到数据库
        if (!md5.isEmpty()) {
            response = resourceService.uploadResource(name, publisherId, url, type, md5);
        }

        // 返回图片 URL 给前端
        boolean isSuccess = (response == 1);
        String info = isSuccess ? "上传成功！" :
                response == -1? "上传出错，请重新上传！" : "服务器错误！";
        
        return new UploadResponse(isSuccess, info, url);
    }
    
    @RequestMapping (value="/posts/write/deleteResource")
    public BasicInfoResponse deleteResource(
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new BasicInfoResponse(false, "所选文件不存在");
        }
        //在数据库获取资源 url
        String url = resourceService.getUrl(id);
        //在数据库删除资源
        Integer response = resourceService.deleteResource(id);
        //在OBS删除文件
        boolean isObsDelete = obsService.deleteFile(url);
        
        if (response == 1 && isObsDelete) {
            return new BasicInfoResponse(true, "删除成功");
        } else {
            return new BasicInfoResponse(false, "删除出错");
        }
        
    }
    
    @RequestMapping (value="/posts/downloadResource")
    public DownloadResponse downloadResource(
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new DownloadResponse(false, "请求为空", null);
        }
        //在数据库获取资源 url 和文件名
        String resourceUrl = resourceService.getUrl(id);
        String resourceName = resourceService.getName(id);
        
        if (resourceUrl == null) {
            // 处理资源不存在的情况
            return new DownloadResponse(false, "资源不存在", null);
        }
        
        /*
        try {
            InputStream inputStream = new URL(resourceUrl).openStream();
            
            // 设置HTTP响应头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", resourceName);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        } catch (IOException e) {
            // 处理URL格式错误的情况
            e.printStackTrace();
            return new DownloadResponse(false, "下载出错", null);
        }
        */
        return new DownloadResponse(true, "下载成功", resourceUrl);
        
    }
    
}

