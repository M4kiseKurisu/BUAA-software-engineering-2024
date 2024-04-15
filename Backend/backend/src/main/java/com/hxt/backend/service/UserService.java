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

    public String setToken(Integer id) {
        String token = RandomStringUtils.randomAlphanumeric(64);
        Integer timestamp = Math.toIntExact(System.currentTimeMillis() / 1000);
        userMapper.setToken(id, token, timestamp);
        return token;
    }

    public void resetToken(Integer id) {
        userMapper.resetToken(id);
    }

    public boolean checkToken() {
        return false;
    }

    public boolean lengthCheck(String s, int min, int max) {
        return (s.length() >= min) && (s.length() <= max);
    }
}
