package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.mapper.ImageMapper;
import com.hxt.backend.mapper.PostMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.FollowResponse;
import com.hxt.backend.response.UserInfoResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private ImageMapper imageMapper;
    private PostMapper postMapper;
    private String defaultHeadUrl = "";

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

    public UserInfoResponse getUserInfo(Integer id) {
        User user = userMapper.selectUserById(id);
        return new UserInfoResponse(user);
    }

    public String getUserHead(Integer id) {
        User user = userMapper.selectUserById(id);
        if (user.getUserId() == null) {
            return null;
        }
        return imageMapper.getImage(user.getHeadId());
    }

    public FollowResponse getFollow(Integer id) {
        List<Integer> followIds = userMapper.getFollow(id);
        FollowResponse followResponse = new FollowResponse(followIds.size(), new ArrayList<>());
        for (Integer followId : followIds) {
            User user = userMapper.selectUserById(followId);
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", user.getName());
            map.put("user_id", user.getUserId());
            if (user.getHeadId() == null) {
                map.put("user_avatar", defaultHeadUrl);
            } else {
                map.put("user_avatar", imageMapper.getImage(user.getHeadId()));
            }
            map.put("following_count", userMapper.getFollowCount(user.getUserId()));
            map.put("post_count", postMapper.getPostNum(user.getUserId()));
            map.put("sign", user.getSign());
            followResponse.getFollowing().add(map);
        }
        return followResponse;
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

    public boolean unfollowUser(Integer userId, Integer followId) {
        return userMapper.unfollowUser(userId, followId) > 0;
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
