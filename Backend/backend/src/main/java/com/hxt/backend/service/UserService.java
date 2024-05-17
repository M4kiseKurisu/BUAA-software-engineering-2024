package com.hxt.backend.service;

import com.hxt.backend.entity.User;
import com.hxt.backend.entity.post.Post;
import com.hxt.backend.mapper.*;
import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.UserInfoResponse;
import com.hxt.backend.response.list.PostListResponse;
import com.hxt.backend.response.list.SectionListResponse;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.singleInfo.PostResponse;
import com.hxt.backend.response.singleInfo.UserAuthorityInfo;
import com.hxt.backend.response.singleInfo.UserSocialInfoResponse;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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
    @Resource
    private AdminMapper adminMapper;
    private final String defaultHeadUrl = "https://hxt-2024.obs.cn-north-4.myhuaweicloud.com:443/6059d059-907d-4b80-a351-4549cdaf6ce6-R-C.jpg";

    public Integer register(String name, String email, String phone,
                            String major, Integer year, String password) {
        if (userMapper.selectUserByAccount(name) != null || userMapper.selectUserByName(name) != null) {
            return -1;
        }
        //String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        return userMapper.insertUser(name, email, phone, major, year, password);
    }

    public Integer checkPassword(String name, String password) {
        //String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        User user = userMapper.selectUserByAccount(name);
        if (user != null && password.equals(user.getPassword())) {
            return user.getUserId();
        }
        return -1;
    }

    public boolean checkPassword(Integer id, String password) {
        //  String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        User user = userMapper.selectUserById(id);
        return password.equals(user.getPassword());
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
    
    public void setUserHead(Integer user_id, Integer head_id) {
        User user = userMapper.selectUserById(user_id);
        if (user.getUserId() == null) {
            return;
        }
        userMapper.updateHead(user_id, head_id);
    }

    public UserSocialInfoResponse getUserSocialInfo(Integer searcher, Integer id) {
        User user = userMapper.selectUserById(id);
        if (user == null) return new UserSocialInfoResponse();
        if (searcher == -1) {
            return new UserSocialInfoResponse(
                    user.getName(), null,
                    (user.getHeadId() == null) ? defaultHeadUrl : imageMapper.getImage(user.getHeadId()),
                    null, null, null, null, null,
                    null, null, null, null
            );
        }
        Integer postLike = postMapper.getUserPostLikeNum(id);
        if (postLike == null) {
            postLike = 0;
        }
        Integer commentLike = postMapper.getUserCommentLikeNum(id);
        if (commentLike == null) {
            commentLike = 0;
        }
        Integer replyLike = postMapper.getUserReplyLikeNum(id);
        if (replyLike == null) {
            replyLike = 0;
        }
        List<UserAuthorityInfo> authorityInfo = new ArrayList<>();
        Integer check = adminMapper.checkGlobalAuthority(id);
        if (check != null && check > 0) {
            authorityInfo.add(new UserAuthorityInfo(0, "全局管理员"));
        } else {
            authorityInfo = adminMapper.getUserAuthorities(id);
        }
        return new UserSocialInfoResponse(
                user.getName(), id,
                (user.getHeadId() == null) ? defaultHeadUrl : imageMapper.getImage(user.getHeadId()),
                userMapper.getFollowCount(id),
                userMapper.getFollowerCount(id),
                postMapper.getUserPostNum(id),
                postMapper.getUserCommentNum(id) + postMapper.getUserReplyNum(id),
                postLike + commentLike + replyLike,
                user.getSign(),
                userMapper.isFollow(searcher, id) > 0,
                checkGlobalBlocked(id),
                authorityInfo
        );
    }

    public UserListResponse searchUser(String keyword, Integer searcher, Integer sort) {
        List<Integer> ids = userMapper.searchUserByName(keyword);
        UserListResponse response = new UserListResponse(ids.size(), new ArrayList<>());
        for (Integer id : ids) {
            response.getUser().add(getUserSocialInfo(searcher, id));
        }
        if (sort == 1) {
            response.getUser().sort((o1, o2) -> o2.getFollower_count() - o1.getFollower_count());
        } else if (sort == 2) {
            response.getUser().sort((o1, o2) -> o2.getLike_count() - o1.getLike_count());
        } else if (sort == 3) {
            response.getUser().sort((o1, o2) -> o2.getPost_count() + o2.getComment_count()
                    - o1.getPost_count() - o1.getComment_count());
        }
        return response;
    }

    public UserListResponse getFollow(Integer id) {
        List<Integer> followIds = userMapper.getFollow(id);
        UserListResponse userListResponse = new UserListResponse(followIds.size(), new ArrayList<>());
        for (Integer followId : followIds) {
            UserSocialInfoResponse response = getUserSocialInfo(id, followId);
            if (response != null) {
                userListResponse.getUser().add(response);
            }
        }
        return userListResponse;
    }

    public PostListResponse getFavorite(Integer id, boolean flag) {
        if (flag && (userMapper.checkUserShowFavorite(id) == 0)) {
            return new PostListResponse(-1, new ArrayList<>());
        }
        List<Integer> favorites = userMapper.getCollect(id);
        PostListResponse postListResponse = new PostListResponse(favorites.size(), new ArrayList<>());
        for (Integer favorite : favorites) {
            PostResponse postResponse = getSinglePostInfo(favorite);
            if (postResponse != null) {
                postListResponse.getPosts().add(postResponse);
            }
        }
        return postListResponse;
    }

    public PostListResponse getPost(Integer id, boolean flag) {
        if (flag && (userMapper.checkUserShowPost(id) == 0)) {
            return new PostListResponse(-1, new ArrayList<>());
        }
        List<Integer> posts = userMapper.getPosts(id);
        PostListResponse postListResponse = new PostListResponse(posts.size(), new ArrayList<>());
        for (Integer post : posts) {
            PostResponse postResponse = getSinglePostInfo(post);
            if (postResponse != null) {
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

    public BasicInfoResponse getSectionAuthority(Integer userid, Integer sectionId) {
        String info = adminMapper.checkAuthorityType(userid, sectionId);
        boolean res = (info != null);
        info = info == null? "" : info;
        return new BasicInfoResponse(res, info);
    }

    public void resetPassword(Integer id, String password) {
        //String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        userMapper.resetPassword(id, password);
    }

    public BasicInfoResponse resetForgottenPassword
            (String account, String email, String np) {
        User user = userMapper.selectUserByAccount(account);
        if (!user.getEmail().equals(email)) {
            return new BasicInfoResponse(false, "信息验证不通过！");
        }
        resetPassword(user.getUserId(), np);
        return new BasicInfoResponse(true, "");
    }

    public String setToken(Integer id) {
        String token = RandomStringUtils.randomAlphanumeric(64);
        Integer timestamp = Math.toIntExact(System.currentTimeMillis() / 1000);
        userMapper.setToken(id, token, timestamp);
        return token;
    }

    public String setUserInfo(Integer id, String name, String major, Integer year, String sign, String phone,
                              Boolean showPost, Boolean showFavorite) {
        if (userMapper.selectUserByName(name) != null) {
            return "用户昵称重名！";
        }
        boolean nameSuccess = name == null || userMapper.updateName(id, name) > 0;
        boolean majorSuccess = major == null || userMapper.updateMajor(id, major) > 0;
        boolean yearSuccess = year == null || userMapper.updateYear(id, year) > 0;
        boolean signSuccess = sign == null || userMapper.updateSign(id, sign) > 0;
        boolean phoneSuccess = phone == null || userMapper.updatePhone(id, phone) > 0;
        boolean postSuccess = showPost == null || userMapper.updateShowPost(id, (showPost? 1 : 0)) > 0;
        boolean favoriteSuccess = showFavorite == null || userMapper.updateShowFavorite(id, (showFavorite? 1 : 0)) > 0;
        return (nameSuccess && majorSuccess && yearSuccess && signSuccess
                && phoneSuccess && postSuccess && favoriteSuccess)? "" : "服务器错误！";
    }

    public boolean followUser(Integer userId, Integer followId) {
        return userMapper.followUser(userId, followId) > 0;
    }

    public boolean unfollowUser(Integer userId, Integer followId) {
        return userMapper.unfollowUser(userId, followId) > 0;
    }

    public boolean reportUser(Integer userId, Integer reportId, String detail) {
        if (adminMapper.checkSameReport(3, reportId, userId) > 0
            ||  (userMapper.isGlobalBlocked(reportId) != null && userMapper.isGlobalBlocked(reportId) > 0)) {
            //  同一人已经发起举报或该用户已被全局封禁，则不提交举报
            return true;
        }
        return adminMapper.insertReport(userId, 3, reportId, detail, null) > 0;
    }

    public boolean applyForAuthority(Integer user, Integer section, Integer type, String detail, String resource) {
        if (adminMapper.checkSameReport(4, section * 3 + type, user) > 0
                || adminMapper.checkAuthority(user, section) != null
                || (section == 0 && type == 0 && adminMapper.checkGlobalAuthority(user) > 0) ) {
            return true;
        }
        return adminMapper.insertReport(user, 4, section * 3 + type, detail, resource) > 0;
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

    public boolean checkGlobalBlocked(Integer id) {
        Integer tmp = userMapper.isGlobalBlocked(id);
        return  (tmp != null && tmp != 0);
    }

    public boolean checkSectionBlocked(Integer user, Integer section) {
        Integer tmp = userMapper.isSectionBlocked(user, section);
        return  (tmp != null && tmp != 0);
    }

    public boolean lengthCheck(String s, int min, int max) {
        return (s.length() >= min) && (s.length() <= max);
    }

    public void setUserCookie(String type, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(type, value);
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private PostResponse getSinglePostInfo(Integer id) {
        Post post = postMapper.getPost(id);
        if (post != null) {
            return new PostResponse(
                    post.getPost_id(),
                    post.getTitle(),
                    post.getIntro(),
                    post.getContent(),
                    userMapper.getUserNameById(post.getAuthor_id()),
                    post.getAuthor_id(),
                    sectionMapper.getSectionNameById(post.getSection_id()),
                    postMapper.getTagNameByPost(post.getPost_id())
            );
        }
        return null;
    }
}
