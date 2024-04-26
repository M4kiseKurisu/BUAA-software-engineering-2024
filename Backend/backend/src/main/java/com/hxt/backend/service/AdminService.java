package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.singleInfo.TotalInfoResponse;
import com.hxt.backend.response.singleInfo.UserSocialInfoResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    ImageMapper imageMapper;

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

    public boolean blockUser(Integer id, Integer days) {
        Integer realtime = days == null? Integer.MAX_VALUE : days;
        return userMapper.blockUser(id, realtime) > 0;
    }

    public boolean unblockUser(Integer id) {
        return userMapper.unblockUser(id) > 0;
    }

    public UserListResponse getUserList() {
        List<Integer> ids = userMapper.selectAllUserId();
        UserListResponse userInfoResponse = new UserListResponse(ids.size(), new ArrayList<>());
        for (Integer id : ids) {
            Integer tmp = userMapper.isBlocked(id);
            boolean isBlocked = (tmp != null && tmp != 0);
            User user = userMapper.selectUserById(id);
            userInfoResponse.getUser().add(new UserSocialInfoResponse(
                    user.getName(), id,
                    (user.getHeadId() == null) ? "" : imageMapper.getImage(user.getHeadId()),
                    0, 0, 0, 0, 0, user.getSign(), false, isBlocked
            ));
        }
        return userInfoResponse;
    }

    public boolean setAuthority(Integer id, Integer section, String type) {
        if (checkAuthority(id, section)) {
            return true;
        }
        return adminMapper.setAuthority(id, section, type) > 0;
    }

    public boolean deleteAuthority(Integer id, Integer section) {
        return adminMapper.deleteAuthority(id, section) > 0;
    }

    public boolean checkAuthority(Integer id, Integer section) {
        if (checkGlobalAuthority(id)) {
            return true;
        }
        return adminMapper.checkAuthority(id, section) != null;
    }

    public boolean setGlobalAuthority(Integer id) {
        return adminMapper.setGlobalAuthority(id) > 0;
    }

    public boolean deleteGlobalAuthority(Integer id) {
        return adminMapper.deleteGlobalAuthority(id) > 0;
    }

    public boolean checkGlobalAuthority(Integer id) {
        return adminMapper.checkGlobalAuthority(id) > 0;
    }

    public void setUserCookie(String type, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(type, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public boolean lengthCheck(String s, int min, int max) {
        return (s.length() >= min) && (s.length() <= max);
    }
}
