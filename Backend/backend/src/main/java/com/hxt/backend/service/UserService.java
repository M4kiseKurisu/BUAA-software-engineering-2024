package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UserService {
    @Resource
    private UserMapper userMapper;

    public Integer register(String name, String email, String phone,
                            String major, Integer year, String password) {
        if (userMapper.selectUserByName(name) != null) {
            return -1;
        }
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        return userMapper.insertUser(name, email, phone, major, year, md5);
    }

    public Integer checkPassword(String name, String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        User user = userMapper.selectUserByName(name);
        System.out.println(user);
        if (md5.equals(user.getPassword())) {
            return user.getUserId();
        }
        return -1;
    }

    public boolean checkPassword(Integer id, String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        User user = userMapper.selectUserById(id);
        return md5.equals(user.getPassword());
    }

    public void resetPassword(Integer id, String password) {
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        userMapper.resetPassword(id, md5);
    }

    public String setToken(Integer id) {
        String token = RandomStringUtils.randomAlphanumeric(64);
        Integer timestamp = Math.toIntExact(System.currentTimeMillis() / 1000);
        userMapper.setToken(id, token, timestamp);
        return token;
    }

    public boolean setUserInfo(Integer id, String name, String major, Integer year, String sign, String phone) {
        boolean nameSuccess = name == null || userMapper.updateName(id, name) > 0;
        boolean majorSuccess = major == null || userMapper.updateMajor(id, major) > 0;
        boolean yearSuccess = year == null || userMapper.updateYear(id, year) > 0;
        boolean signSuccess = sign == null || userMapper.updateSign(id, sign) > 0;
        boolean phoneSuccess = phone == null || userMapper.updatePhone(id, phone) > 0;
        return nameSuccess && majorSuccess && yearSuccess && signSuccess && phoneSuccess;
    }

    public void resetToken(Integer id) {
        userMapper.resetToken(id);
    }

    public int checkToken(Integer id, String token) {
        User user = userMapper.selectUserById(id);
        if (token.equals(user.getToken())) {
            Integer timestamp = Math.toIntExact(System.currentTimeMillis() / 1000);
            int time = user.getTokenTime() - timestamp;
            if (time >= 86400) {    //  token过期时间为86400s，即24小时
                resetToken(id);
                return -1;
            }
            return 1;
        }
        return -2;
    }

    public boolean lengthCheck(String s, int min, int max) {
        return (s.length() >= min) && (s.length() <= max);
    }
}
