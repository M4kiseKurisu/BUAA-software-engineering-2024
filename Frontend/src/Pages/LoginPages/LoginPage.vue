<template>
    <div class="LoginPage">
        <div class="LoginPageContainer">
            <div class="LoginPageLeft">
                <el-image class="loginPicture" :src="loginPic" :fit="fit" />
            </div>

            <div class="LoginPageRight">
                <div class="login-title-font">登录</div>

                <div class="information-change-left-row">
                    <div class="information-sign">输入账号</div>
                    <input type="text" class="input-type-1" v-model="inputLoginNumber" :placeholder=this.loginNumber>
                </div>

                <div class="information-change-left-row">
                    <div class="information-sign">输入密码</div>
                    <input type="text" class="input-type-1" v-model="inputLoginNumber" :placeholder=this.loginNumber>
                </div>

                <div class="button-container">
                    <button class="button-left">登录</button>
                </div>

                <div class="to-assign-container">
                    <button class="to-assign-font">还没有账号？去注册</button>
                </div>

                <el-image class="logoPicture" :src="logoPic" :fit="fit" />

            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            loginPic: "./src/Images/LoginImage.png",
            logoPic: "./src/Images/buaaLogo.png",
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
    background-image: url("../../src/Images/loginBackground.png");
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
    margin-top: 90px;
    align-self: center;
    margin-bottom: 40px;
}


.information-change-left-row {
    display: flex;
    margin-bottom: 20px;
    justify-content: flex-end;
    align-self: center;
}

.information-sign {
    font-size: 16px;
    font-weight: bold;
    color: #4e5969;
    margin-right: 30px;
    margin-top: 6px;
}

.input-type-1 {
    width: 240px;
    height: 32px;
    background-color: #f7f8fa;
    border: 0px;
    padding-left: 10px;
}

.input-type-1:focus {
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
    height: 120px;
    width: 90px;
    align-self: flex-end;
    margin-right: 8px;
}
</style>