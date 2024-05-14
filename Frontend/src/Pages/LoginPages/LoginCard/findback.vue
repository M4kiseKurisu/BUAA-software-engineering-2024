<template>
    <div class="title-container center-container flex-layout">
        <div class="title-font">
            找回密码
        </div>
    </div>

    <form>
        <information_input :input_title="'输入用户名'" :input_type="'username'" ref="username"/>
        <information_input :input_title="'输入邮箱'" :input_type="'email'" ref="email"/>
        <information_input :input_title="'新设密码'" :input_type="'password'" ref="password_1"/>
        <information_input :input_title="'确认密码'" :input_type="'password'" ref="password_2"/>
    </form>

    <div class="login-button-container center-container flex-layout">
        <button class="login-button" @click="this.change_click">申请更改</button>
        <div class="change-page-link" @click="to_login">返回登录界面</div>
    </div>
</template>

<script>
import information_input from "./input.vue"
import { change } from './login_api.js';
export default {
    components: {
        information_input,
    },
    methods: {
        change_click() {
            change(this.$refs.username, this.$refs.email, this.$refs.password_1, this.$refs.password_2);
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