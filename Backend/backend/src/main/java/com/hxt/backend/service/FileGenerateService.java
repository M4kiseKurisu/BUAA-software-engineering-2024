package com.hxt.backend.service;

import com.hxt.backend.response.BasicInfoResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileGenerateService {
    public void StringToTxt(String fileName, String content, HttpServletResponse response) {
        response.setContentType("text/plain");
        try {
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.info(e.toString());
        }
        ServletOutputStream outputStream = null;
        BufferedOutputStream buffer = null;
        try {
            outputStream = response.getOutputStream();
            buffer = new BufferedOutputStream(outputStream);
            buffer.write(content.getBytes(StandardCharsets.UTF_8));
            buffer.flush();
            buffer.close();
            outputStream.close();
        } catch (IOException e) {
            log.info(e.toString());
        }
    }
}
