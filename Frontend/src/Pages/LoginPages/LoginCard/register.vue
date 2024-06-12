<template>
    <div class="center-container flex-layout title-container">
        <div class="title-font">
            注册
        </div>
    </div>

    <form @keydown.enter="this.register_click">
        <information_input :input_title="'输入用户名'" :input_type="'username_create'" ref="username"/>
        <information_input :input_title="'输入邮箱'" :input_type="'email'" ref="email"/>
        <information_input :input_title="'输入手机号'" :input_type="'tel'" ref="tel"/>
        <information_input :input_title="'输入密码'" :input_type="'password'" ref="password"/>
        <el-checkbox v-model="agreed" style="left: 25%;">我同意<b>《航学通用户守则》</b></el-checkbox>
    </form>


    <div class="login-button-container center-container flex-layout">
        <div class="change-page-link" @click="show">航学通用户守则</div>
        <button class="login-button" @click="this.register_click">注册</button>
        <div class="change-page-link" @click="to_login">返回登录界面</div>
    </div>

    <el-dialog title="航学通用户守则" v-model="visible">
        <div style="justify-content: center;">
            <p>1.在注册过程中点击“我同意《航学通用户守则》”并注册航学通账号，即代表您已阅读过航学通用户守则，并知悉您在航学通平台中所拥有的权利及义务。</p>
            <p>2.航学通——北航学习互助平台（以下简称“航学通”）是一个兼具论坛、打卡、交流等功能的平台</p>
        </div>
        <div style="display:flex;justify-content: center;margin-top: 20px">
          <el-button type="primary" @click="hide">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>
import information_input from "./input.vue"
import { register } from './login_api.js';
export default {
    components: {
        information_input,
    },
    data() {
        return {
            visible: false
        }
    },
    methods: {
        register_click() {
            register(this.$refs.username, this.$refs.email, this.$refs.tel, this.$refs.password, this.agreed);
        },
        to_login() {
            this.$emit('click-link', 1);
        },
        show() {
            this.visible = true;
        },
        hide() {
            this.visible = false;
        }
    }
}
</script>

<style>
@import './login-card.css';
</style>