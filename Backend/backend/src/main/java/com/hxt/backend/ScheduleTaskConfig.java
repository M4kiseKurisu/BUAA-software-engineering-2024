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

    @Scheduled(cron = "0 0 0,8,16 * * *")   //  每天0/8/16时
    private void clearLog() {
        log.info("清理8小时前的用户操作记录...");
        adminMapper.deleteOldLogs(new Timestamp(System.currentTimeMillis() - 8 * 60 * 60 * 1000));
    }

    @Scheduled(cron = "0 0 0 * * *")   //  每天0时
    private void clearHandled() {
        log.info("清理已处理完毕的举报...");
        adminMapper.deleteHandledReport();
    }
}
