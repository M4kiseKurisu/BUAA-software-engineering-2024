<template>
    <!-- 此容器是为了设定宽度，让内部的所有元素都可以使用百分比宽度 -->
    <div class="page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

        <!-- 此处为上层灰色信息卡片 -->
        <div class="grey-card">

            <!-- 头像信息 -->
            <el-avatar :size="100" :src="avatarPicture" class="avatar-position"/>

            <!-- 第一列信息 -->
            <div class="first-row-container">

                <div class="line-container-1">
                    <div class="grey-card-information">用户昵称：</div>
                    <div class="grey-card-information2">{{ this.username }}</div>
                </div>

                <div class="line-container-1">
                    <div class="grey-card-information">登陆账号：</div>
                    <div class="grey-card-information2">{{ this.loginNumber }}</div>
                </div>

                <div class="line-container-1">
                    <div class="grey-card-information">入学时间：</div>
                    <div class="grey-card-information2">{{ this.signTime }}</div>
                </div>

            </div>

            <!-- 第二列信息 -->
            <div class="second-row-container">

                <!--
                <div class="line-container-1">
                    <div class="grey-card-information">认证邮箱：</div>
                    <div class="grey-card-information2">{{ this.email }}</div>
                    <div class="grey-card-changee-link">修改</div>
                </div>
                -->

                <div class="line-container-1">
                    <div class="grey-card-information">手机号码：</div>
                    <div class="grey-card-information2">{{ this.phoneNumber }}</div>
                    <!-- <div class="grey-card-changee-link">修改</div> -->
                </div>

                <div class="line-container-1" v-if="this.is_blocked">
                    <div class="grey-card-information-red-version">该用户已被封禁</div>
                </div>

            </div>

        </div>

        <div class="down-information-container">
            <!-- 左侧基本信息更改栏位 -->
            <div class="left-container">
                <div class="left-container-header">基础信息更改</div>

                <div class="information-change-left-row">
                    <div class="information-sign">用户昵称</div>
                    <input type="text"
                        :class="{'input-type-1': true, 'red-border': username_warning.length > 0}"
                        v-model="inputUsername" :placeholder=this.username>
                </div>
                <div style="display: flex; justify-content: flex-end;">
                    <div v-if="username_warning.length > 0" class="warning-css"
                    style="margin-top: -18px; margin-bottom: 18px;">{{ username_warning }}</div>
                </div>



                <div class="information-change-left-row">
                    <div class="information-sign">手机号码</div>
                    <input type="text"
                        :class="{'input-type-1': true, 'red-border': phone_warning.length > 0}"
                        v-model="inputPhone" :placeholder=this.phoneNumber>
                </div>
                <div style="display: flex; justify-content: flex-end;">
                    <div v-if="phone_warning.length > 0" class="warning-css"
                    style="margin-top: -18px; margin-bottom: 18px;">{{ phone_warning }}</div>
                </div>

                <div class="information-change-left-row">
                    <div class="information-sign">专业</div>
                    <input type="text" :class="{'input-type-1': true, 'red-border': major_warning.length > 0}"
                        v-model="inputMajority" :placeholder=this.majority>
                </div>
                <div style="display: flex; justify-content: flex-end;">
                    <div v-if="major_warning.length > 0" class="warning-css"
                    style="margin-top: -18px; margin-bottom: 18px;">{{ major_warning }}</div>
                </div>

                <div class="information-change-left-row">
                    <div class="information-sign">入学时间</div>
                    <input type="text" :class="{'input-type-1': true, 'red-border': enroll_warning.length > 0}"
                        v-model="inputEntryTime" :placeholder=this.signTime>
                </div>
                <div style="display: flex; justify-content: flex-end;">
                    <div v-if="enroll_warning.length > 0" class="warning-css"
                    style="margin-top: -18px; margin-bottom: 18px;">{{ enroll_warning }}</div>
                </div>

                <div class="information-change-left-row">
                    <div class="information-sign">个人签名</div>
                    <textarea type="text" :class="{'input-type-2': true, 'red-border': sign_warning.length > 0}"
                        v-model="inputSignature" :placeholder=this.signature />
                </div>
                <div style="display: flex; justify-content: flex-end;">
                    <div v-if="sign_warning.length > 0" class="warning-css"
                    style="margin-top: -18px; margin-bottom: 18px;">{{ sign_warning }}</div>
                </div>

                <div class="button-container">
                    <!-- <el-upload v-model:file-list="this.fileList" :limit="1" :show-file-list="false" :auto-upload="false" action="#">
                        <button class="button-changeavatar">选择头像</button>
                    </el-upload>
                    <button class="button-changeavatar" @click="uploadAvatar">上传头像</button> -->
                    <button class="button-changeavatar" @click="showAvatarUpload">更换头像</button>
                    <button class="button-left" @click="ChangeInformation">保存</button>
                    <button class="button-right" @click="emptyInformation">重置</button>

                    <el-dialog v-model="dialogVisible" title="头像裁剪" width="60%">
                        <ImageCropper :Name="cropperName" ref="child"></ImageCropper>
                    </el-dialog>
                </div>

            </div>

            <!-- 右侧基本信息更改栏位 -->
            <div class="right-container">

                <!--
                <div class="right-container-header">
                    <button class="button-choose">更改密码</button>
                    <button class="button-not-choose-1">邮箱认证</button>
                    <button class="button-not-choose-1">手机号认证</button>
                </div>
                -->

                <div class="left-container-header-2">更改密码</div>

                <div class="right-content-container">

                    <div class="information-change-left-row">
                        <div class="information-sign">旧密码</div>
                        <input type="text" class="input-type-1" v-model="this.inputOldPassword">
                    </div>

                    <div class="information-change-left-row">
                        <div class="information-sign">新密码</div>
                        <input type="password" class="input-type-1" v-model="this.inputNewPassword">
                    </div>

                    <div class="information-change-left-row">
                        <div class="information-sign">再次输入密码</div>
                        <input type="password"
                            :class="{'input-type-1': true, 'red-border': pwd_diff_warning.length > 0}"
                            v-model="this.inputNewPassword2">
                    </div>
                    <div style="display: flex; justify-content: flex-end;">
                        <div v-if="pwd_diff_warning.length > 0" class="warning-css"
                        style="margin-top: -18px; margin-bottom: 18px;">{{ pwd_diff_warning }}</div>
                    </div>

                    <div class="button-container">
                        <button class="button-left" @click="changePassword">保存</button>
                        <button class="button-right" @click="emptyInformation2">重置</button>
                    </div>

                </div>


            </div>

        </div>


    </div>
</template>

<script>
import axios from 'axios';
import md5 from 'js-md5';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"
import ImageCropper from '../../Components/Tool/ImageCropper.vue';

export default {
    components: {
        BreadcrumbLabel,
        ImageCropper
    },
    data() {
        return {
            route: [
                {
                    name: "个人中心",
                    route: "/MainPage/Personal_Center/Personal_Information"
                }, {
                    name: "用户设置",
                    route: ""
                }],  //本界面要显示的面包屑信息
            avatarPicture: "",  //测试的头像信息
            username: "", //测试的昵称信息
            loginNumber: "", //测试的账号信息
            signTime: "", //测试的注册时间信息
            email: "", //测试的邮箱信息
            phoneNumber: "", //测试的手机信息
            majority: "", //测试的学员信息
            signature: "", //测试的个人签名

            username_warning: "",  //更改用户名报错
            phone_warning: "",  //更改电话报错
            major_warning: "", //专业信息报错
            enroll_warning: "",  //入学年份报错
            sign_warning: "",  //更改签名报错
            pwd_diff_warning: "",

            fileList: [],  //上传图片位置

            inputUsername: "",
            inputPhone: "",
            inputMajority: "",
            inputEntryTime: "",
            inputSignature: "",

            inputOldPassword: "",
            inputNewPassword: "",
            inputNewPassword2: "",
            is_blocked: false,

            dialogVisible: false,

            option: {
                img: 'https://avatars2.githubusercontent.com/u/15681693?s=460&v=4',
                size: 1,
                full: false,
                outputType: 'png',
                canMove: true,
                fixedBox: false,
                original: false,
                canMoveBox: true,
                autoCrop: true,
                // 只有自动截图开启 宽度高度才生效
                autoCropWidth: 750,
                autoCropHeight: 340,
                centerBox: true,
                high: true,
                max: 99999
            },
            formValidate: {
                mainImage: '',
            },
            ruleValidate: {
                mainImage: [
                {required: true, message: '请上传封面', trigger: 'blur'}
                ],
            },
            //裁切图片参数
            cropperModel:false,
            cropperName:'',
            imgName: '',
            imgVisible: false
        }
    },
    mounted() {
        axios({
            method: "GET",
            url: "/api/user/info",
        }).then((result) => {
            console.log(result);
            this.username = (result.data.name != "") ? result.data.name : result.data.account;
            this.loginNumber = result.data.account;
            this.signTime = (result.data.enrollment_year != "") ? result.data.enrollment_year : "未设定";
            this.email = result.data.email;
            this.phoneNumber = (result.data.phone_number != "") ? result.data.phone_number : "未设定";
            this.majority = (result.data.major != "") ? result.data.major : "未设定";
            this.signature = (result.data.sign != "") ? result.data.sign : "未设定";
        })

        // 获取头像信息
        axios({
            method: "GET",
            url: "/api/user/head",
            params: {
                user_id: JSON.parse(sessionStorage.getItem("id"))
            }
        }).then((result) => {
            console.log(result)
            this.avatarPicture = result.data.info;
        })

        //获取封禁信息
        axios({
            method: "GET",
            url: "/api/user/social/self",
        }).then((result) => {
            this.is_blocked = result.data.flag_blocked;
        })
    },
    methods: {
        // checkname() {
        //     if (!this.inputUsername || this.inputUsername.length == 0) {
        //         this.username_warning = "用户名不能为空！"
        //     } else if (this.inputUsername.length > 8) {
        //         this.username_warning = "用户名过长！"
        //     } else {
        //         this.username_warning = ""
        //     }
        //     console.log(this.inputUsername.length);
        // },
        // checktel() {
        //     if (!this.phoneNumber || this.phoneNumber.length == 0) {
        //         this.phone_warning = "输入手机号不能为空！"
        //         return;
        //     }
        //     var tel_reg = /^1[3-9][0-9]{9}$/;
        //     if (!tel_reg.test(content)) {
        //         this.phone_warning = "输入的手机号不合法！";
        //         return;
        //     }
        //     this.phone_warning = "";
        // },
        showAvatarUpload() {
            this.dialogVisible = true;
        },

        uploadAvatar() {
            const file = this.fileList[0].raw;
            const data = new FormData();
            data.append("file", file);

            console.log(file);

            axios.post("/api/user/uploadHead", data);
        },

        ChangeInformation() {
            if (this.inputUsername.length > 8) {
                this.username_warning = "用户名过长！";
            } else {
                this.username_warning = "";
            }

            if (this.inputPhone.length > 0) {
                var tel_reg = /^1[3-9][0-9]{9}$/;
                if (!tel_reg.test(this.inputPhone)) {
                    this.phone_warning = "输入的手机号不合法！";
                } else {
                    this.phone_warning = "";
                }
            } else {
                this.phone_warning = "";
            }

            if (isNaN(this.inputEntryTime)) {
                this.enroll_warning = "入学年份必须是数字！";
            } else {
                this.enroll_warning = "";
            }

            if (this.inputSignature.length > 30) {
                this.sign_warning = "个人签名过长！";
            } else {
                this.sign_warning = "";
            }

            if (this.username_warning.length > 0 || this.phone_warning.length > 0
                || this.enroll_warning.length > 0 || this.sign_warning.length > 0) {
                return;
            }

            const content = {};
            if (this.inputUsername != "") {
                content.name = this.inputUsername;
            }
            if (this.inputPhone != "") {
                content.phone = this.inputPhone;
            }
            if (this.inputMajority != "") {
                content.major = this.inputMajority;
            }
            if (this.inputEntryTime != "") {
                content.enrollment_year = parseInt(this.inputEntryTime);
            }
            if (this.inputSignature != "") {
                content.sign = this.inputSignature;
            }

            console.log(content);

            axios({
                method: "POST",
                url: "/api/user/update",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '基础信息更改成功！',
                        type: 'success',
                    });
                    location.reload();
                } else {
                    this.$message({
                        showClose: true,
                        message: "基础信息更改失败！",
                        type: 'error',
                    });
                }
            })
        },

        emptyInformation() {
            this.inputUsername = "";
            this.inputPhone = "";
            this.inputMajority = "";
            this.inputEntryTime = "";
            this.inputSignature = "";
        },

        emptyInformation2() {
            this.inputOldPassword = "";
            this.inputNewPassword = "";
            this.inputNewPassword2 = "";
        },

        changePassword() {
            if (this.inputNewPassword === "" || this.inputNewPassword2 === "") {
                return;
            }
            if (this.inputNewPassword != this.inputNewPassword2) {
                this.pwd_diff_warning = "两次输入的新密码不一致！"
                return;
            } else {
                this.pwd_diff_warning = "";
            }

            let content = {
                id: JSON.parse(sessionStorage.getItem("id")),
                old_password: md5(this.inputOldPassword),
                new_password: md5(this.inputNewPassword),
            }
            console.log(content);

            axios({
                method: "POST",
                url: "/api/user/password/update",
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
.page-container {
    width: calc(100vw - 220px);
    min-width: 1174px;
    height: calc(100vh - 85px);
}

.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
}

.grey-card {
    margin-top: 21px;
    width: 96%;
    height: 162px;
    border-radius: 4px;
    background-color: #f7f8fa;
    display: flex;
}

.avatar-position {
    margin-top: 29px;
    margin-left: 37px;
}

.first-row-container {
    margin-top: 41px;
}

.line-container-1 {
    display: flex;
    margin-left: 60px;
    margin-bottom: 8px;
}

.grey-card-information {
    height: 20px;
    font-size: 14px;
    color: #4e5969;
    margin-right: 14px;
}

.grey-card-information2 {
    height: 20px;
    font-size: 14px;
    color: #4e5969;
}

.grey-card-changee-link {
    height: 20px;
    font-size: 14px;
    color: #165dff;
    margin-left: 14px;
}

.second-row-container {
    margin-top: 41px;
    margin-left: 224px;
}

.down-information-container {
    display: flex;
}

.left-container {
    width: 42%;
}

.left-container-header {
    font-size: 16px;
    color: #101010;
    height: 52px;
    margin-top: 42px;
    margin-left: 96px;
    font-weight: bold;
}

.left-container-header-2 {
    font-size: 16px;
    color: #101010;
    height: 52px;
    margin-top: 42px;
    margin-left: 96px;
    font-weight: bold;
    margin-left: 30%;
}

.information-change-left-row {
    display: flex;
    margin-bottom: 20px;
    justify-content: flex-end;
}

.information-sign {
    font-size: 14px;
    font-weight: bold;
    color: #4e5969;
    margin-right: 11px;
    margin-top: 6px;
}

.input-type-1 {
    width: 366px;
    height: 32px;
    background-color: #f7f8fa;
    border: 0px;
    padding-left: 10px;
}

.input-type-1:focus {
    outline: none;
    border: none;
}

input::placeholder {
    font-size: 14px;
    margin-left: 4px;
	color:#888888;
}

.input-type-2 {
    width: 356px;
    height: 54px;
    background-color: #f7f8fa;
    border: 0px;
    padding-left: 10px;
    padding-right: 10px;
    padding-top: 8px;
    padding-bottom: 8px;
}

.input-type-2:focus {
    outline: none;
    border: none;
}

textarea::placeholder {
    font-size: 14px;
    margin-left: 4px;
	color:#888888;
}

.button-container {
    display: flex;
    margin-top: 32px;
    justify-content: flex-end;
}

.button-left {
    margin-right: 12px;
    width: 60px;
    height: 32px;
    border-radius: 4px;
    background-color: #1f63ff;
    color: #ffffff;
    border: none;
}

.button-right {
    width: 60px;
    height: 32px;
    border-radius: 4px;
    background-color: #f7f8fa;
    color: #4e5969;
    border: none;
}

.button-changeavatar {
    margin-right: 12px;
    height: 32px;
    width: 80px;
    border-radius: 4px;
    background-color: #f7f8fa;
    color: #4e5969;
    border: none;
}

.right-container {
    width: 47%;
}

.right-container-header {
    height: 94px;
    display: flex;
}

.button-choose {
    width: 88px;
    height: 30px;
    border-radius: 15px;
    background-color: #f2f3f8;
    color: #1f63ff;
    font-size: 14px;
    border: none;
    margin-right: 5px;
    margin-top: 42px;
    margin-left: 118px;
}

.button-not-choose-1 {
    width: 88px;
    height: 30px;
    border-radius: 15px;
    background-color: #ffffff;
    color: #4e5969;
    font-size: 14px;
    border: none;
    margin-right: -7px;
    margin-top: 42px;
}

.right-content-container {
    width: 485px;
    margin-left: 103px;
}

.grey-card-information-red-version {
    height: 20px;
    font-size: 14px;
    color: red;
    margin-right: 14px;
}

.cut {
    width: 90%;
}

.red-border {
    border: 1px solid red;
}

.warning-css {
    color: red;
    font-size: 12px;
}
</style>