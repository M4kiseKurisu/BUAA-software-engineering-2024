package com.hxt.backend.controller;

import com.hxt.backend.response.BasicInfoResponse;
import com.hxt.backend.response.LoginResponse;
import com.hxt.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
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
            @RequestParam(name = "password", required = false) String password
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
            return new LoginResponse(true, id, "", userService.setToken(id));
        } else {
            return new LoginResponse(false, id, "用户名或密码错误！", "");
        }
    }

    @RequestMapping("/user/logout")
    public BasicInfoResponse logout(@RequestParam(name = "id") Integer id) {
        if (id == null) {
            return new BasicInfoResponse(false, "用户id为空！");
        }
        userService.resetToken(id);
        return new BasicInfoResponse(true, "");
    }

    @RequestMapping("/user/check")
    public BasicInfoResponse tokenCheck(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "token", required = false) String token
    ) {
        if (id == null || token == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        }
        int i = userService.checkToken(id, token);
        boolean success = (i == 1);
        String info = (i == 1)? "" :
                (i == -1)? "token过期！" :
                        (i == -2)? "token不正确！" : "服务器错误！";
        return new BasicInfoResponse(success, info);
    }

    @RequestMapping("/user/password/update")
    public BasicInfoResponse setPassword(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "old_password", required = false) String op,
            @RequestParam(name = "new_password", required = false) String np
    ) {
        if (id == null || op == null || np == null) {
            return new BasicInfoResponse(false, hasEmptyResponse);
        } else if (!userService.checkPassword(id, op)) {
            return new BasicInfoResponse(false, "旧密码错误！");
        } else if (!userService.lengthCheck(np, 6, 18)) {
            return new BasicInfoResponse(false, "密码过长或过短！");
        } else {
            userService.resetPassword(id, np);
            return new BasicInfoResponse(true, "");
        }
    }

    @RequestMapping("/user/update")
    public BasicInfoResponse setUserInfo(
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "major", required = false) String major,
            @RequestParam(name = "enrollment_year", required = false) Integer year,
            @RequestParam(name = "sign", required = false) String sign,
            @RequestParam(name = "phone", required = false) String phone
    ) {
        if (id == null) {
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
        boolean res = userService.setUserInfo(id, name, major, year, sign, phone);
        String info = res? "" : "修改发生错误，请稍后再试！";
        return new BasicInfoResponse(res, info);
    }
}
