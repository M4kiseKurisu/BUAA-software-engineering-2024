package com.hxt.backend;

import com.hxt.backend.mapper.AdminMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;

//  定时任务
@Configuration
@Slf4j
@EnableScheduling
public class ScheduleTaskConfig {

    @Resource
    private AdminMapper adminMapper;

    @Scheduled(fixedDelay = 12 * 60 * 60 * 1000)   //  定时时长(ms)
    private void clearLog() {
        log.info("清理12小时前的用户操作记录...");
        adminMapper.deleteOldLogs(new Timestamp(System.currentTimeMillis() - 12 * 60 * 60 * 1000));
    }
}
