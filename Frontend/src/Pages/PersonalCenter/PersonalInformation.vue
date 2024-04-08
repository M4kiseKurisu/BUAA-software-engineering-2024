<template>
    <!-- 此容器是为了设定宽度，让内部的所有元素都可以使用百分比宽度 -->
    <div class="page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

        <!-- 此处为页面头部蓝色卡片组件 -->
        <el-card class="top-card" shadow="hover">

            <!-- 蓝色卡片内部信息存储组件 -->
            <div class="blue-card-container">
                <!-- 头像信息 -->
                <el-avatar :size="86" :src="avatarPicture"/>
                <!-- 昵称信息 -->
                <div class="nickname">{{ this.nickName }}</div>

                <!-- 学届，专业，邮箱信息（在同一行） -->
                <div class="information-line2-container">
                    <!-- 学届信息 -->
                    <div class="sub-information-line2-container">
                        <el-icon><Calendar :size="17" color="#101010"/></el-icon>
                        <div class="sub-information-line2-font">{{ this.entryYear }}</div>
                    </div>

                    <!-- 专业信息 -->
                    <div class="sub-information-line2-container">
                        <el-icon><School :size="17" color="#101010"/></el-icon>
                        <div class="sub-information-line2-font">{{ this.majority }}</div>
                    </div>

                    <!-- 邮箱信息 -->
                    <div class="sub-information-line2-container">
                        <el-icon><Message :size="17" color="#101010"/></el-icon>
                        <div class="sub-information-line2-font-end">{{ this.email }}</div>
                    </div>
                </div>
            </div>
            
        </el-card>

    </div>
</template>

<script>
// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"
// 引入图标
import { Calendar, School, Message } from '@element-plus/icons-vue'

export default {
    components: {
        BreadcrumbLabel,
        Calendar,
        School,
        Message
    },
    data() {
        return {
            route: ["个人中心", "用户信息"],  //本界面要显示的面包屑信息
            avatarPicture: "./src/Images/testAvatar.jpg",  //测试的头像信息
            nickName: "M4kiseKurisu", //测试的昵称信息
            entryYear: "2021",  //测试的学届信息
            majority: "计算机学院",  //测试的专业信息
            email: "21373343@buaa.edu.cn",  //测试的邮箱信息
        }
    }
}
</script>

<style scoped>
/* 设定界面宽度真实值 */
.page-container {
    width: calc(100vw - 205px);
}

/* 控制面包屑组件的位置 */
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
}

/* 控制蓝色卡片的样式 */
.top-card {
    margin-top: 20px;
    width: 96%;
    height: 216px;
    background-color: #e8f4ff;
    border: 1px solid white;
    border-radius: 4px;
    display: flex;
    justify-content: center;
}

/* 控制蓝色卡片内部信息的位置 */
.blue-card-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin-top: 14px;
}

/* 控制昵称的样式和位置 */
.nickname {
    font-size: 16px;
    color: #101010;
    font-weight: bold;
    margin-top: 7px;
}

/* 以下为控制第二行三条信息展示的所有样式，包括字体，间距等 */
.information-line2-container {
    display: flex;
    height: 17px;
    margin-top: 12px;
}

.sub-information-line2-container {
    display: flex;
    height: 17px;
}

.sub-information-line2-font {
    font-size: 14px;
    margin-top: -2px;
    margin-left: 6px;
    margin-right: 16px;
}

.sub-information-line2-font-end {
    font-size: 14px;
    margin-top: -2px;
    margin-left: 6px;
}
</style>