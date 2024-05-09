<template>
    <div class="LoginPage">
        <div class="LoginPageContainer">
            <div class="LoginPageLeft">
                <el-image class="loginPicture" :src="loginPic" :fit="fit" />
            </div>

            <div v-if="this.loginOrRegister === 1" class="LoginPageRight">
                <!-- 登陆界面 -->
                <div class="login-title-font">登录</div>

                <div class="information-change-left-row">
                    <div class="information-sign">输入账号</div>
                    <input type="text" class="input-type-1" v-model="this.loginName">
                </div>

                <div class="information-change-left-row">
                    <div class="information-sign">输入密码</div>
                    <input type="password" class="input-type-1" v-model="this.loginPassword">
                </div>

                <div class="button-container">
                    <button class="button-left" @click="Login">登录</button>
                </div>

                <div class="to-assign-container">
                    <button class="to-assign-font" @click="toRegister">还没有账号？去注册</button>
                    <button class="to-assign-font" style="margin-left: 16px" @click="toFindPassword">找回密码</button>
                </div>

                <el-image class="logoPicture" :src="logoPic" :fit="fit" />

            </div>

            <div v-if="this.loginOrRegister === 2" class="LoginPageRight">
                <!-- 注册界面 -->
                <div class="create-title-font">注册</div>

                <div class="information-change-left-row-2">
                    <div class="information-sign-2">账号</div>
                    <div>
                        <input type="text" class="input-type-2" v-model="this.registerAccount">
                    </div>
                </div>

                <div class="information-change-left-row-2">
                    <div class="information-sign-2">邮箱</div>
                    <div>
                        <input type="text" class="input-type-2" v-model="this.registerEmail">
                    </div>
                </div>

                <div class="information-change-left-row-2">
                    <div class="information-sign-2">(可选)手机</div>
                    <div>
                        <input type="text" class="input-type-2" v-model="this.registerPhone">
                    </div>
                </div>

                <div class="information-change-left-row-2">
                    <div class="information-sign-2">密码</div>
                    <div>
                        <input type="password" class="input-type-2" v-model="this.registerPassword">
                    </div>
                </div>

                <div class="button-container">
                    <button class="button-left" @click="Register">注册</button>
                </div>

                <div class="to-assign-container">
                    <button class="to-assign-font" @click="toLogin">返回登录界面</button>
                </div>

            </div>

            <div v-if="this.loginOrRegister === 3" class="LoginPageRight">
                <!-- 找回密码界面 -->
                <div class="create-title-font">修改密码</div>

                <div class="information-change-left-row">
                    <div class="information-sign">输入账号</div>
                    <input type="text" class="input-type-1" v-model="this.changeAccount">
                </div>

                <div class="information-change-left-row">
                    <div class="information-sign">输入邮箱</div>
                    <input type="text" class="input-type-1" v-model="this.changeEmail">
                </div>


                <div class="information-change-left-row">
                    <div class="information-sign">新设密码</div>
                    <input type="password" class="input-type-1" v-model="this.changePassword">
                </div>

                <div class="information-change-left-row">
                    <div class="information-sign">再次输入</div>
                    <input type="password" class="input-type-1" v-model="this.changePassword2">
                </div>

                <div class="button-container">
                    <button class="button-left-3" @click="change">申请更改</button>
                </div>

                <div class="to-assign-container">
                    <button class="to-assign-font" @click="toLogin">返回登录界面</button>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export default {
    data() {
        return {
            loginOrRegister: 1,  //1代表登录界面，2代表注册界面，3代表找回密码
            loginPic: "./src/Images/LoginImage.png",
            logoPic: "./src/Images/buaaLogo.png",
            loginName: "",
            loginPassword: "",
            registerAccount:"",
            registerEmail:"",
            registerPhone:"",
            registerPassword:"",
            changeAccount: "",
            changeEmail: "",
            changePassword: "",
            changePassword2: "",
        }
    },
    methods: {
        Login() {
            let content = {
                name: this.loginName,
                password: md5(this.loginPassword),
            }

            console.log(content);

            axios({
                method: "POST",
                url: "/api/user/login",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    // 登录成功
                    this.$message({
                        showClose: true,
                        message: "登录成功！",
                        type: 'success',
                    });
                    this.$store.commit("userLogin", result.data);
                    this.$router.push({ path: "MainPage/Personal_Center" });
                } else {
                    // 登录失败
                    this.$message({
                        showClose: true,
                        message: result.data.info,
                        type: 'error',
                    });
                }
            })
        },
        toRegister() {
            this.loginOrRegister = 2;
        },
        toFindPassword() {
            this.loginOrRegister = 3;
        },
        toLogin() {
            this.loginOrRegister = 1;
        },
        Register() {
            //注册账号信息打包
            let content = {
                name: this.registerAccount,
                email: this.registerEmail,
                phone: this.registerPhone,
                major: "",
                year: "",
                password: md5(this.registerPassword),
            }

            console.log(content);

            axios({
                method: "POST",
                url: "/api/user/register",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.loginOrRegister = 1;
                    // 注册成功
                    this.$message({
                        showClose: true,
                        message: "注册成功！",
                        type: 'success'
                    });
                } else {
                    // 注册失败
                    this.$message({
                        showClose: true,
                        message: result.data.info,
                        type: 'error'
                    });
                }
            })
        },
        change() {
            if (this.changePassword === "" || this.changePassword2 === "") {
                return;
            }
            if (this.changePassword != this.changePassword2) {
                this.$message({
                    showClose: true,
                    message: "两次输入的新密码不一致！",
                    type: 'error',
                });
            }

            let content = {
                account: this.changeAccount,
                email: this.changeEmail,
                password: md5(this.changePassword),
            }
            console.log(content);

            axios({
                method: "POST",
                url: "/api/user/password/forget",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: "密码更改成功！",
                        type: 'success',
                    });
                } else {
                    this.$message({
                        showClose: true,
                        message: "密码更改失败！",
                        type: 'error',
                    });
                }
            })
        }
    }
}
</script>

<style scoped>
.LoginPage {
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url("../../../src/Images/loginBackground.png");
    background-size: cover;
    background-position: center;
}

.LoginPageContainer {
    width: 60%;
    height: 60%;
    background-color: white;
    border-radius: 20px;
    display: flex;
}

.LoginPageLeft {
    width: 50%;
}

.loginPicture {
    width: 100%;
    height: 100%;
    border-radius: 20px;
}

.LoginPageRight {
    width: 50%;
    display: flex;
    flex-direction: column;
}

.login-title-font {
    font-size: 32px;
    color: #165DFF;
    font-weight: bold;
    margin-top: 20%;
    align-self: center;
    margin-bottom: 40px;
}

.create-title-font {
    font-size: 32px;
    color: #165DFF;
    font-weight: bold;
    margin-top: 65px;
    align-self: center;
    margin-bottom: 24px;
}


.information-change-left-row-2 {
    display: flex;
    margin-bottom: 20px;
    width: 83%;
    margin-left: -35%;
    justify-content: flex-end;
    align-self: center;
}

.information-sign-2 {
    font-size: 16px;
    font-weight: bold;
    color: #4e5969;
    margin-right: 20px;
    margin-top: 6px;
    align-self: flex-end;
}

.input-type-1 {
    width: 68%;
    height: 32px;
    background-color: #f7f8fa;
    border: 0px;
    padding-left: 10px;
    align-self: flex-end;
}

.input-type-1:focus {
    outline: none;
    border: none;
}

.input-type-2 {
    width: 100%;
    height: 32px;
    background-color: #f7f8fa;
    border: 0px;
    padding-left: 10px;
    align-self: flex-end;
}

.input-type-2:focus {
    outline: none;
    border: none;
}

.button-container {
    display: flex;
    margin-top: 10px;
    justify-content: center;
}

.button-left {
    width: 60px;
    height: 32px;
    border-radius: 4px;
    background-color: #1f63ff;
    color: #ffffff;
    border: none;
}

.to-assign-container {
    display: flex;
    margin-top: 16px;
    justify-content: center;
}

.to-assign-font {
    border-radius: 4px;
    background-color: #ffffff;
    color: #165DFF;
    border: none;
    text-decoration: underline;
}

.logoPicture {
    height: 20%;
    width: 17%;
    align-self: flex-end;
    margin-right: 8px;
}

.information-change-left-row {
    display: flex;
    margin-bottom: 20px;
    width: 83%;
    margin-left: -8%;
    justify-content: flex-end;
    align-self: center;
}

.information-sign {
    font-size: 16px;
    font-weight: bold;
    color: #4e5969;
    margin-right: 20px;
    margin-top: 6px;
    align-self: flex-end;
    width: 65px;
}

.button-left-3 {
    width: 80px;
    height: 32px;
    border-radius: 4px;
    background-color: #1f63ff;
    color: #ffffff;
    border: none;
}
</style>
