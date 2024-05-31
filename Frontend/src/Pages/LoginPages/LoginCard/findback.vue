<template>
    <div class="title-container center-container flex-layout">
        <div class="title-font">
            找回密码
        </div>
    </div>

    <form @keydown.enter="this.change_click">
        <information_input :input_title="'输入用户名'" :input_type="'username'" ref="username"/>
        <information_input :input_title="'输入邮箱'" :input_type="'email'" ref="email"/>
        <information_input :input_title="'输入验证码'" :input_type="'code'" ref="code"/>
        <information_input :input_title="'新设密码'" :input_type="'password'" ref="password_1"/>
        <information_input :input_title="'确认密码'" :input_type="'password'" ref="password_2"/>
    </form>

    <div class="login-button-container center-container flex-layout">
        <button :class="send_class" :disabled="isDisable" @click="this.send_click">{{ word }}</button>
        <button class="login-button" @click="this.change_click">申请更改</button>
        <div class="change-page-link" @click="to_login">返回登录界面</div>
    </div>
</template>

<script>
import information_input from "./input.vue"
import { sendEmail, change } from './login_api.js';
export default {
    components: {
        information_input,
    },
    data() {
        return {
            isDisable: false,
            word: "获取邮箱验证码",
            timer: "",
            send_class: "login-button"
        }
    },
    methods: {
        time_clock() {
            var time = 60;
            this.word = "重新获取 (" + time + ")"
            this.timer = setInterval(() => {
                time = time - 1;
                this.word = "重新获取 (" + time + ")"
                if (time <= 0) {
                    this.isDisable = false;
                    this.word = "重新获取验证码";
                    this.send_class = "login-button";
                    clearInterval(this.timer);
                }
            }, 1000)
        },
        send_click() {
            this.isDisable = true;
            this.word = "请求中...";
            this.send_class = "disabled-login-button";
            sendEmail(this.$refs.username, this.$refs.email);
            this.time_clock();
        },
        change_click() {
            change(this.$refs.username, this.$refs.code, this.$refs.password_1, this.$refs.password_2);
            this.$emit('click-link', 1);
        },
        to_login() {
            this.$emit('click-link', 1);
        }
    }
}
</script>

<style>
@import './login-card.css';
</style>