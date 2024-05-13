<template>
    <div class="center-container flex-layout title-container">
        <div class="title-font">
            注册
        </div>
    </div>

    <form>
        <information_input :input_title="'输入用户名'" :input_type="'username_create'" ref="username"/>
        <information_input :input_title="'输入邮箱'" :input_type="'email'" ref="email"/>
        <information_input :input_title="'输入手机号'" :input_type="'tel'" ref="tel"/>
        <information_input :input_title="'输入密码'" :input_type="'password'" ref="password"/>
    </form>

    <div class="login-button-container center-container flex-layout">
        <button class="login-button" @click="this.register_click">注册</button>
        <div class="change-page-link" @click="to_login">返回登录界面</div>
    </div>
</template>

<script>
import information_input from "./input.vue"
import { register } from './login_api.js';
export default {
    components: {
        information_input,
    },
    methods: {
        register_click() {
            register(this.$refs.username, this.$refs.email, this.$refs.tel, this.$refs.password);
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