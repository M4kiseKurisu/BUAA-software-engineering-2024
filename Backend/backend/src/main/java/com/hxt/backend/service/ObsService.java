package com.hxt.backend.service;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObsService {
    private String bucketName = "hxt-2024";
    private String accessKey = "3Y96J7IARUL9LMDZ2RQR";
    private String secretKey = "D1DTvmfyJyq4S6XDUi6lOgz5wnfp81THay9P7mpV";
    private String endpoint = "https://obs.cn-north-4.myhuaweicloud.com";
    private ObsClient obsClient;

   
    
    public String uploadFile(MultipartFile file) {
        try {
            //创建唯一新文件名
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();

            //创建云服务器
            obsClient= new ObsClient(accessKey, secretKey, endpoint);
            PutObjectResult result = obsClient.putObject(bucketName, fileName, inputStream);
            obsClient.close();
            if (result != null) {
                return result.getObjectUrl(); // 返回 url
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean deleteFile(String url) {
        if (url == null) {
            return false;
        }
        try {
            
            //从url获取文件名
            String[] cut = url.split("/");
            String fileName = cut[cut.length - 1];
            System.out.println(fileName);
            
            //创建云服务器
            ObsClient obsClient = new ObsClient(accessKey, secretKey, endpoint);
            // 删除文件
            obsClient.deleteObject(bucketName, fileName);
            // 关闭连接
            obsClient.close();
            return true;
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
