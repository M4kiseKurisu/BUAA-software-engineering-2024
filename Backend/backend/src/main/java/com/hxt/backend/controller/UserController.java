package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.LoginResponse;
import com.hxt.backend.response.UploadResponse;
import com.hxt.backend.response.UserInfoResponse;
import com.hxt.backend.response.list.PostListResponse;
import com.hxt.backend.response.list.SectionListResponse;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.singleInfo.UserSocialInfoResponse;
import com.hxt.backend.service.ImageService;
import com.hxt.backend.service.ObsService;
import com.hxt.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    
    private final ObsService obsService;
    
    private final ImageService imageService;
    private String emailPattern = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";
    private String phonePattern = "^\\d+";
    private String hasEmptyResponse = "信息填写不完整！";

    @RequestMapping("/user/register")
    public BasicInfoResponse register(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "major", required = false) String major,
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "password", required = false) String password
    ) {
        //System.out.println(name + " " + email + " " + phone + " " + major + " " + year + " " + password);
        if (name == null || email == null || password == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        String p = (phone == null)? "" : phone;
        String m = (major == null)? "" : major;
        int y;
        if ((year == null)) {
            Calendar c = Calendar.getInstance();
            y = c.get(Calendar.YEAR);
        } else {
            y = year;
        }
        if (!userService.lengthCheck(name, 1, 16)
                || !userService.lengthCheck(email, 1, 96)
                || !userService.lengthCheck(p, 0, 11)
                || !userService.lengthCheck(m, 0, 64)
                || !userService.lengthCheck(password, 6, 18)
                || !(y >= 2000 && y <= 2040)) {
            return new BasicInfoResponse(false, "注册信息输入不合法！");
        }
        Pattern pat = Pattern.compile(emailPattern);
        Matcher matcher = pat.matcher(email);
        if (!matcher.find()) {
            return new BasicInfoResponse(false, "邮箱格式不正确！");
        }
        pat = Pattern.compile(phonePattern);
        matcher = pat.matcher(p);
        if (!p.isEmpty() && !matcher.find()) {
            return new BasicInfoResponse(false, "手机号格式不正确！");
        }
        Integer res = userService.register(name, email, p, m, y, password);
        String info = res == 1? "" :
                    res == -1? "用户名重名！" : "服务器错误！";
        return new BasicInfoResponse(res == 1, info);
    }

    @RequestMapping("/user/login")
    public LoginResponse login(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "password", required = false) String password,
            HttpServletResponse response
    ) {
        if (name == null)  {
            return new LoginResponse(false, -1, "未填写用户名！", "");
        } else if (password == null) {
            return new LoginResponse(false, -1, "未填写密码！", "");
        } else if (!userService.lengthCheck(name, 1, 16)) {
            return new LoginResponse(false, -1, "用户名不合法！", "");
        }
        int id = userService.checkPassword(name, password);
        if (id > 0) {
            Cookie cookie = new Cookie("user_id",String.valueOf(id));
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new LoginResponse(true, id, "", userService.setToken(id));
        } else {
            return new LoginResponse(false, id, "用户名或密码错误！", "");
        }
    }


    @RequestMapping("/user/logout")
    public BasicInfoResponse logout(@CookieValue(name = "user_id", defaultValue = "") String user_id) {
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false, "用户未登录！");
        }
        int id = Integer.parseInt(user_id);
        userService.resetToken(id);
        return new BasicInfoResponse(true, "");
    }

    //无需token验证，改为通过cookie获取用户id，但函数先保留，等待组会时再商议
    @RequestMapping("/user/check")
    public BasicInfoResponse tokenCheck(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "token", required = false) String token
    ) {
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        Integer id = Integer.parseInt(user_id);
        if (token == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        int i = userService.checkToken(id, token);
        boolean success = (i == 1);
        String info = (i == 1)? "" :
                (i == -1)? "token过期！" :
                        (i == -2)? "token不正确！" : "服务器错误！";
        return new BasicInfoResponse(success, info);
    }

    @RequestMapping("/user/info")
    public UserInfoResponse getUserInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.isEmpty()) {
            return new UserInfoResponse(null);
        }
        return userService.getUserInfo(Integer.parseInt(user_id));
    }

    @RequestMapping("/user/social/self")
    public UserSocialInfoResponse getUserSocialInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.isEmpty()) {
            return new UserSocialInfoResponse();
        }
        return userService.getUserSocialInfo(Integer.parseInt(user_id), Integer.parseInt(user_id));
    }

    @RequestMapping("/user/social/others")
    public UserSocialInfoResponse getUserSocialInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        System.out.println(user_id + " " + id);
        if (id == null) {
            return new UserSocialInfoResponse();
        } else if (user_id.isEmpty()) {
            return userService.getUserSocialInfo(0, id);
        }
        return userService.getUserSocialInfo(Integer.parseInt(user_id), id);
    }

    @RequestMapping("/user/head")
    public BasicInfoResponse getUserHead(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        String url = userService.getUserHead(Integer.parseInt(user_id));
        if (url == null) {
            return new BasicInfoResponse(false, "该用户未设置头像！");
        }
        return new BasicInfoResponse(true, url);
    }
    
    @RequestMapping (value="/user/uploadHead")
    public BasicInfoResponse uploadUserHead(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "file", required = false) MultipartFile file
    ) {
        
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        
        if (file == null) {
            return new BasicInfoResponse(false, "图片为空");
        }
        // 上传文件到云服务器并返回图片在云服务器上的 URL
        String url = obsService.uploadFile(file);
        
        // 将图片的 URL 保存到数据库
        Integer response = imageService.uploadImage(url);
        
        // 将图片id存入 user 表
        Integer headId = imageService.getImageIdByUrl(url);
        userService.setUserHead(Integer.parseInt(user_id), headId);
        
        // 返回
        boolean isSuccess = (response == 1);
        String info = isSuccess ? "上传成功！" :
                response == -1? "上传出错，请重新上传！" : "服务器错误！";
        
        return new BasicInfoResponse(isSuccess, info);
    }

    @RequestMapping("/user/favorites")
    public PostListResponse getUserFavorite(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.isEmpty()) {
            return new PostListResponse(-1, new ArrayList<>());
        }
        return userService.getFavorite(Integer.parseInt(user_id));
    }

    @RequestMapping("/user/password/update")
    public BasicInfoResponse setPassword(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "old_password", required = false) String op,
            @RequestParam(name = "new_password", required = false) String np
    ) {
        if (user_id.isEmpty() || op == null || np == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!userService.checkPassword(Integer.parseInt(user_id), op)) {
            return new BasicInfoResponse(false, "旧密码错误！");
        } else if (!userService.lengthCheck(np, 6, 18)) {
            return new BasicInfoResponse(false, "密码过长或过短！");
        } else {
            userService.resetPassword(Integer.parseInt(user_id), np);
            return new BasicInfoResponse(true, "");
        }
    }

    @RequestMapping("/user/password/forget")
    public BasicInfoResponse setForgottenPassword(
            @RequestParam(name = "account", required = false) String account,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "password", required = false) String np
    ) {
        if (account == null || email == null || np == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!userService.lengthCheck(np, 6, 18)) {
            return new BasicInfoResponse(false, "密码过长或过短！");
        } else {
            return userService.resetForgottenPassword(account, email, np);
        }
    }

    @RequestMapping("/user/update")
    public BasicInfoResponse setUserInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "major", required = false) String major,
            @RequestParam(name = "enrollment_year", required = false) Integer year,
            @RequestParam(name = "sign", required = false) String sign,
            @RequestParam(name = "phone", required = false) String phone
    ) {
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        if (!(name == null || userService.lengthCheck(name, 0, 16))
                || !(major == null || userService.lengthCheck(major, 0, 64))
                || !(year == null || (year >= 2000 && year <= 2040))
                || !(sign == null || userService.lengthCheck(sign, 0, 256))
                || !(phone == null || userService.lengthCheck(phone, 0, 11))) {
            return new BasicInfoResponse(false, "信息输入不合法！");
        }
        if (phone != null) {
            Pattern pat = Pattern.compile(phonePattern);
            Matcher matcher = pat.matcher(phone);
            if (!phone.isEmpty() && !matcher.find()) {
                return new BasicInfoResponse(false, "手机号格式不正确！");
            }
        }
        String info = userService.setUserInfo(Integer.parseInt(user_id), name, major, year, sign, phone);
        return new BasicInfoResponse(info.isEmpty(), info);
    }

    @RequestMapping("/user/follow")
    public BasicInfoResponse followUser(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "unfollow_id", required = false) Integer follow_id
    ) {
        if (user_id.isEmpty() || follow_id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        boolean success = userService.followUser(Integer.parseInt(user_id), follow_id);
        String info = success? "" : "发生错误，未进行任何修改！";
        return new BasicInfoResponse(success, info);
    }

    @RequestMapping("/user/unfollow")
    public BasicInfoResponse unfollowUser(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "unfollow_id", required = false) Integer unfollow_id
    ) {
        if (user_id.isEmpty() || unfollow_id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        boolean success = userService.unfollowUser(Integer.parseInt(user_id), unfollow_id);
        String info = success? "" : "发生错误，未进行任何修改！";
        return new BasicInfoResponse(success, info);
    }

    @RequestMapping("/user/following")
    public UserListResponse getFollowInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.isEmpty()) {
            return new UserListResponse(-1, new ArrayList<>());
        }
        return userService.getFollow(Integer.parseInt(user_id));
    }

    @RequestMapping("/user/focus")
    public SectionListResponse getFocusInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id
    ) {
        if (user_id.isEmpty()) {
            return new SectionListResponse(-1, new ArrayList<>());
        }
        return userService.getSection(Integer.parseInt(user_id));
    }
}
