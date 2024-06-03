package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.LoginResponse;
import com.hxt.backend.response.UserInfoResponse;
import com.hxt.backend.response.list.PostListResponse;
import com.hxt.backend.response.list.SectionListResponse;
import com.hxt.backend.response.list.UserListResponse;
import com.hxt.backend.response.sectionResponse.SectionUserAuthorityResponse;
import com.hxt.backend.response.singleInfo.UserSocialInfoResponse;
import com.hxt.backend.service.FrequencyLogService;
import com.hxt.backend.service.ImageService;
import com.hxt.backend.service.ObsService;
import com.hxt.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final FrequencyLogService frequencyLogService;
    private final String emailPattern = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";
    private final String phonePattern = "^\\d+";
    private final String hasEmptyResponse = "信息填写不完整！";
    private final String frequencyResponse = "操作太频繁了，休息一下再来吧！";

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
            userService.setUserCookie("user_id", String.valueOf(id), response);
            return new LoginResponse(true, id, "", userService.setToken(id));
        } else {
            return new LoginResponse(false, id, "用户名或密码错误！", "");
        }
    }


    @RequestMapping("/user/logout")
    public BasicInfoResponse logout(@CookieValue(name = "user_id", defaultValue = "") String user_id) {
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(true, "您已不处于登录状态！");
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

    @RequestMapping("/user/social/simple")
    public UserSocialInfoResponse getUserNameAndHead(
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new UserSocialInfoResponse();
        }
        return userService.getUserSocialInfo(-1, id);
    }

    @RequestMapping("/user/social/others")
    public UserSocialInfoResponse getUserSocialInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (id == null) {
            return new UserSocialInfoResponse();
        } else if (user_id.isEmpty()) {
            return userService.getUserSocialInfo(0, id);
        }
        return userService.getUserSocialInfo(Integer.parseInt(user_id), id);
    }

    @RequestMapping("/user/search")
    public UserListResponse searchUser(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "sort", required = false) Integer sort
    ) {
        Integer id = user_id.isEmpty()? 0 : Integer.parseInt(user_id);
        return userService.searchUser(keyword, id, sort);
    }

    @RequestMapping("/user/head")
    public BasicInfoResponse getUserHead(
            @RequestParam(name = "user_id", required = false) Integer user_id
    ) {
        if (user_id == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        String url = userService.getUserHead(user_id);
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
        String[] res = obsService.uploadFile(file);
        String url = res[0];
        String md5 = res[1];
        Integer response = 1;

        // 将图片的 URL 保存到数据库
        if (md5 != null) {
            response = imageService.uploadImage(url, md5);
        }
        
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
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (user_id.isEmpty()) {
            return new PostListResponse(-1, new ArrayList<>());
        }
        return userService.getFavorite(id == null? Integer.parseInt(user_id) : id,
                (id != null && Integer.parseInt(user_id) != id));
    }

    @RequestMapping("/user/posts")
    public PostListResponse getUserPost(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id
    ) {
        if (user_id.isEmpty()) {
            return new PostListResponse(-1, new ArrayList<>());
        }
        return userService.getPost(id == null? Integer.parseInt(user_id) : id,
                (id != null && Integer.parseInt(user_id) != id));
    }

    @RequestMapping("/user/password/update")
    public BasicInfoResponse setPassword(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "old_password", required = false) String op,
            @RequestParam(name = "new_password", required = false) String np
    ) {
        if (user_id.isEmpty() || op == null || np == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        } else if (!userService.checkPassword(Integer.parseInt(user_id), op)) {
            return new BasicInfoResponse(false, "旧密码错误！");
        } else {
            userService.resetPassword(Integer.parseInt(user_id), np);
            frequencyLogService.setLog(Integer.parseInt(user_id), 8);
            return new BasicInfoResponse(true, "");
        }
    }

    @RequestMapping("/user/password/forget/request")
    public BasicInfoResponse getResetPassword(
            @RequestParam(name = "account", required = false) String account,
            @RequestParam(name = "email", required = false) String email
    ) {
        if (account == null || email == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else {
            return userService.sendCheckEmail(account, email);
        }
    }

    @RequestMapping("/user/password/forget")
    public BasicInfoResponse setForgottenPassword(
            @RequestParam(name = "account", required = false) String account,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "password", required = false) String np
    ) {
        if (account == null || code == null || np == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else {
            return userService.resetForgottenPassword(account, code, np);
        }
    }

    @RequestMapping("/user/update")
    public BasicInfoResponse setUserInfo(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "major", required = false) String major,
            @RequestParam(name = "enrollment_year", required = false) Integer year,
            @RequestParam(name = "sign", required = false) String sign,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "show_post", required = false) Boolean showPost,
            @RequestParam(name = "show_favorite", required = false) Boolean showFavorite
    ) {
        if (user_id.isEmpty()) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
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
        String info = userService.setUserInfo(Integer.parseInt(user_id), name, major, year,
                sign, phone, showPost, showFavorite);
        frequencyLogService.setLog(Integer.parseInt(user_id), 9);
        return new BasicInfoResponse(info.isEmpty(), info);
    }

    @RequestMapping("/user/follow")
    public BasicInfoResponse followUser(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "follow_id", required = false) Integer follow_id
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

    @RequestMapping("/user/authority")
    public SectionUserAuthorityResponse getSectionAuthority(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", defaultValue = "") Integer id,
            @RequestParam(name = "section", required = false) Integer section
    ) {
        if (user_id.isEmpty()) {
            return new SectionUserAuthorityResponse(false, hasEmptyResponse, false);
        }
        if (id == null) {
            id = Integer.parseInt(user_id);
        }
        return userService.getSectionAuthority(id, section);
    }

    @RequestMapping("/user/report")
    public BasicInfoResponse reportUser(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "detail", required = false) String detail
    ) {
        if (user_id.isEmpty() || id == null || detail == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        boolean res = userService.reportUser(Integer.parseInt(user_id), id, detail);
        String info = res? "" : "服务器错误！";
        if (res) {
            frequencyLogService.setLog(Integer.parseInt(user_id), 3);
        }
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/user/apply")
    public BasicInfoResponse ApplyForAuthority(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "section_id", required = false) Integer section_id,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(name = "detail", required = false) String detail,
            @RequestParam(name = "file", required = false) String file
    ) {
        if (user_id.isEmpty() || section_id == null || type == null || detail == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (file == null) {
            file = "";
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        boolean res = userService.applyForAuthority(Integer.parseInt(user_id), section_id, type, detail, file);
        String info = res? "" : "服务器错误！";
        if (res) {
            frequencyLogService.setLog(Integer.parseInt(user_id), 4);
        }
        return new BasicInfoResponse(res, info);
    }

    @RequestMapping("/user/apply/global")
    public BasicInfoResponse ApplyForGlobalAuthority(
            @CookieValue(name = "user_id", defaultValue = "") String user_id,
            @RequestParam(name = "detail", required = false) String detail,
            @RequestParam(name = "file", required = false) String file
    ) {
        if (user_id.isEmpty() || detail == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (file == null) {
            file = "";
        } else if (frequencyLogService.checkFrequency(Integer.parseInt(user_id))) {
            return new BasicInfoResponse(false, frequencyResponse);
        }
        boolean res = userService.applyForAuthority(Integer.parseInt(user_id), 0, 2, detail, file);
        String info = res? "" : "服务器错误！";
        if (res) {
            frequencyLogService.setLog(Integer.parseInt(user_id), 4);
        }
        return new BasicInfoResponse(res, info);
    }
}
