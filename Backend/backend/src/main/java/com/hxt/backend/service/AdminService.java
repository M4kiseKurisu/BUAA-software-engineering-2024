package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.mapper.AdminMapper;
import com.hxt.backend.mapper.PostMapper;
import com.hxt.backend.mapper.SectionMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.singleInfo.TotalInfoResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AdminService {
    @Resource
    SectionMapper sectionMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    PostMapper postMapper;
    @Resource
    AdminMapper adminMapper;

    public Integer checkPassword(String name, String password) {
        Timestamp lock = adminMapper.checkLock(name);
        if (lock != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (now.getTime() - lock.getTime() < 1000 * 60 * 60 * 24) {
                return -4;
            }
        }
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        Integer id = adminMapper.tryLogin(name, md5);
        if (id != null) {
            adminMapper.setFail(name, 0);
            return id;
        }
        Integer fail = adminMapper.checkFail(name);
        if (fail == null) {
            return -5;
        } else if (fail < 3) {
            adminMapper.setFail(name, fail + 1);
            return -(fail + 1);
        } else {
            adminMapper.setFail(name, 0);
            adminMapper.setLock(name, new Timestamp(System.currentTimeMillis()));
            return -4;
        }
    }

    public TotalInfoResponse getTotalInfo() {
        long tmp = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
        Integer recent24int = Math.toIntExact(tmp / 1000);
        Timestamp recent24 = new Timestamp(tmp);
        Integer post24 = postMapper.getPostNumRecent(recent24);
        Integer postAll = postMapper.getPostNum();
        return new TotalInfoResponse(
                userMapper.getLoginNumRecent(recent24int),
                post24,
                post24 + postMapper.getCommentNumRecent(recent24) + postMapper.getReplyNumRecent(recent24),
                userMapper.getUserNum(),
                sectionMapper.getCourseNum(),
                sectionMapper.getSchoolNum(),
                postAll,
                postAll + postMapper.getCommentNum() + postMapper.getReplyNum()
        );
    }

    public boolean checkPassword(Integer id, String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        return adminMapper.checkPassword(id, md5) > 0;
    }

    public void resetPassword(Integer id, String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        adminMapper.resetPassword(id, md5);
    }

    public boolean addCourse(String name, String intro, String type,
                             String academy, Integer credit, Integer capacity) {
        return sectionMapper.insertCourse(name, intro, type, academy, credit, capacity) > 0;
    }

    public boolean addSchool(String name, String intro, String category, String web) {
        return sectionMapper.insertSchool(name, intro, category, web) > 0;
    }

    public boolean lengthCheck(String s, int min, int max) {
        return (s.length() >= min) && (s.length() <= max);
    }
}
