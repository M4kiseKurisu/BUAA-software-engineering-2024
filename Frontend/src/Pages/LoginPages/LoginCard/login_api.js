import Vue from '@/main.js';
import axios from 'axios';
import md5 from 'js-md5';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export function login(username_e, password_e) {
    username_e.checkContent();
    password_e.checkContent();

    if (username_e.is_warning || password_e.is_warning) {
        return;
    }

    let content = {
        name: username_e.content,
        password: md5(password_e.content),
    }
    console.log(content);
    axios({
        method: "POST",
        url: "/api/user/login",
        data: content,
    }).then((result) => {
        if (result.data.success) {
            Vue.$message({ showClose: true, message: "登录成功！", type: 'success' });
            sessionStorage.setItem("token", result.data.token)
            Vue.$store.commit("userLogin", result.data);
            Vue.$router.push({ path: "MainPage/Personal_Center" });
        } else {
            Vue.$message({ showClose: true, message: result.data.info, type: 'error' });
        }
    })

}

export function register(username_e, email_e, tel_e, password_e, agreed_e) {
    username_e.checkContent();
    email_e.checkContent();
    tel_e.checkContent();
    password_e.checkContent();

    if (username_e.is_warning || email_e.is_warning || tel_e.is_warning || password_e.is_warning) {
        return;
    }

    if (agreed_e != true) {
        Vue.$message({ showClose: true, message: '清先同意《航学通用户守则》！', type: 'error' });
        return;
    }

    let content = {
        name: username_e.content,
        email: email_e.content,
        phone: tel_e.content,
        major: "",
        year: "",
        password: md5(password_e.content),
    }
    axios({
        method: "POST",
        url: "/api/user/register",
        data: content,
    }).then((result) => {
        if (result.data.success) {
            Vue.$message({ showClose: true, message: "注册成功！即将自动刷新...", type: 'success' });
            setTimeout(() => {
                location.reload();
            }, 1000);
        } else {
            Vue.$message({ showClose: true, message: result.data.info, type: 'error' });
        }
    })
}

export function sendEmail(username_e, email_e) {
    username_e.checkContent();
    email_e.checkContent();
    let content = {
        account: username_e.content,
        email: email_e.content,
    }
    axios({
        method: "POST",
        url: "/api/user/password/forget/request",
        data: content,
    }).then((result) => {
        if (result.data.success) {
            Vue.$message({ showClose: true, message: "验证码已发送至邮箱！", type: 'success' });
        } else {
            Vue.$message({ showClose: true, message: result.data.info, type: 'error' });
        }
        return result.data.success
    })
}

export function change(username_e, code_e, password_1_e, password_2_e) {
    username_e.checkContent();
    code_e.checkContent();
    password_1_e.checkContent();
    password_2_e.checkContent();

    if (password_2_e.content != password_1_e.content) {
        password_2_e.showWarning("两次输入的密码不一致！");
    }

    let content = {
        account: username_e.content,
        code: code_e.content,
        password: md5(password_1_e.content),
    }
    axios({
        method: "POST",
        url: "/api/user/password/forget",
        data: content,
    }).then((result) => {
        if (result.data.success) {
            Vue.$message({ showClose: true, message: "密码更改成功！", type: 'success' });
        } else {
            Vue.$message({ showClose: true, message: result.data.info, type: 'error' });
        }
    })

}