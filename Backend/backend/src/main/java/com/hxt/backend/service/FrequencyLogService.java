package com.hxt.backend.service;

import com.hxt.backend.mapper.AdminMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class FrequencyLogService {
    @Resource
    private AdminMapper adminMapper;

    private final int time20Sec = 20 * 1000;
    private final int limit20Sec = 5;
    private final int time60Sec = 60 * 1000;
    private final int limit60Sec = 10;
    private final int time60Min = 60 * 60 * 1000;
    private final int limit60Min = 150;
    private final int time6Hour = 6 * 60 * 60 * 1000;
    private final int limit6Hour = 500;

    public void setLog(Integer user, Integer type) {
        adminMapper.addLog(user, type);
    }

    public boolean checkFrequency(Integer user) {
        long now = System.currentTimeMillis();
        return  (adminMapper.checkRecentLogNum(user, new Timestamp(now - time20Sec)) > limit20Sec
                || adminMapper.checkRecentLogNum(user, new Timestamp(now - time60Sec)) > limit60Sec
                || adminMapper.checkRecentLogNum(user, new Timestamp(now - time60Min)) > limit60Min
                || adminMapper.checkRecentLogNum(user, new Timestamp(now - time6Hour)) > limit6Hour);
    }
}
