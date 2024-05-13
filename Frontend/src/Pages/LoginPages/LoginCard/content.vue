<template>
    <div class="title-container center-container flex-layout">
        <div class="title-font">
            登录
        </div>
    </div>

    <form>
        <information_input :input_title="'输入用户名'" :input_type="'username'" ref="username"/>
        <information_input :input_title="'输入密码'" :input_type="'password'" ref="password"/>
    </form>

    <div class="login-button-container center-container flex-layout">
        <button class="login-button" @click="this.login_click">登录</button>
    </div>

    <div class="center-container flex-layout change-page-link-container">
        <div class="change-page-link" @click="to_register">还没有账号？去注册</div>
        <div class="change-page-link" @click="to_findback">找回密码</div>
    </div>
</template>

<script>
import information_input from "./input.vue"
import { login } from './login_api.js';
export default {
    components: {
        information_input,
    },
    methods: {
        login_click() {
            login(this.$refs.username, this.$refs.password);
        },
        to_register() {
            this.$emit('click-link', 2);
        },
        to_findback() {
            this.$emit('click-link', 3);
        }
    }
}
</script>

<style>
@import './login-card.css';
</style>