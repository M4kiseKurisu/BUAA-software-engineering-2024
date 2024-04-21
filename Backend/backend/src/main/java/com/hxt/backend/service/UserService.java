package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.post.Post;
import com.hxt.backend.mapper.ImageMapper;
import com.hxt.backend.mapper.PostMapper;
import com.hxt.backend.mapper.SectionMapper;
import com.hxt.backend.mapper.UserMapper;
import com.hxt.backend.response.UserInfoResponse;
import com.hxt.backend.response.list.PostListResponse;
import com.hxt.backend.response.list.SectionListResponse;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.singleInfo.PostResponse;
import com.hxt.backend.response.singleInfo.UserSocialInfoResponse;
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
    @Resource
    private PostMapper postMapper;
    @Resource
    private SectionMapper sectionMapper;
    private final String defaultHeadUrl = "";

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

    public UserSocialInfoResponse getUserSocialInfo(Integer id) {
        User user = userMapper.selectUserById(id);
        if (user == null) return new UserSocialInfoResponse();
        return new UserSocialInfoResponse(
                user.getName(),
                user.getUserId(),
                (user.getHeadId() == null) ? defaultHeadUrl : imageMapper.getImage(user.getHeadId()),
                userMapper.getFollowCount(user.getUserId()),
                userMapper.getFollowerCount(user.getUserId()),
                postMapper.getUserPostNum(user.getUserId()),
                postMapper.getUserCommentNum(user.getUserId()),
                user.getSign()
        );
    }

    public UserListResponse getFollow(Integer id) {
        List<Integer> followIds = userMapper.getFollow(id);
        UserListResponse userListResponse = new UserListResponse(followIds.size(), new ArrayList<>());
        for (Integer followId : followIds) {
            UserSocialInfoResponse response = getUserSocialInfo(followId);
            if (response != null) {
                userListResponse.getUser().add(response);
            }
        }
        return userListResponse;
    }

    public PostListResponse getFavorite(Integer id) {
        List<Integer> favorites = userMapper.getCollect(id);
        PostListResponse postListResponse = new PostListResponse(favorites.size(), new ArrayList<>());
        for (Integer favorite : favorites) {
            Post post = postMapper.getPost(favorite);
            if (post != null) {
                PostResponse postResponse = new PostResponse(
                        post.getPostId(),
                        post.getTitle(),
                        post.getContent(),
                        userMapper.getUserNameById(post.getAuthorId()),
                        post.getAuthorId(),
                        sectionMapper.getSectionNameById(post.getSectionId()),
                        postMapper.getTagNameByPost(post.getPostId())
                );
                postListResponse.getPosts().add(postResponse);
            }
        }
        return postListResponse;
    }

    public SectionListResponse getSection(Integer id) {
        List<Integer> focuses = userMapper.getFocus(id);
        SectionListResponse sectionListResponse = new SectionListResponse(focuses.size(), new ArrayList<>());
        for (Integer focus : focuses) {
            String name = sectionMapper.getSectionNameById(focus);
            if (name != null) {
                HashMap<String, Object> sec = new HashMap<>();
                sec.put("section_name", name);
                sec.put("section_id", focus);
                sectionListResponse.getSections().add(sec);
            }
        }
        return sectionListResponse;
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
