<template>
    <!-- 本模块用来设定侧边栏内容，首先设定侧边栏样式信息 -->
    <el-menu
        active-text-color="#165dff"
        text-color="#4e5969"
        router="true"
    >

        <!-- 侧边栏第一个元素是个人中心 -->
        <el-sub-menu>
            <!-- 设置个人中心子菜单的表头信息 -->
            <template #title>
                <el-icon><User /></el-icon>
                <span>个人中心</span>
            </template>

            <!-- 设置个人中心子菜单内容：用户信息，用户设置 -->
            <el-menu-item index="/MainPage/Personal_Center/Personal_Information">用户信息</el-menu-item>
            <el-menu-item index="/MainPage/Personal_Center/Personal_Information_Change">用户设置</el-menu-item>
        </el-sub-menu>

        <!-- 侧边栏第二个元素是学业模块 -->
        <el-sub-menu>
            <!-- 设置学业模块子菜单的表头信息 -->
            <template #title>
                <el-icon><Reading /></el-icon>
                <span>学业模块</span>
            </template>

            <!-- 设置学业模块子菜单内容：课程信息，课程论坛 -->
            <el-menu-item index="/MainPage/Course_Center/Personal_Course">课程论坛</el-menu-item>
            <!-- <el-menu-item index="/MainPage/Course_Center/PostPage">论坛内容2</el-menu-item>
            <el-menu-item index="/MainPage/Course_Center/PostCenter">测试</el-menu-item>
            <el-menu-item index="/CourseSection">课程详情测试</el-menu-item> -->
            <el-menu-item index="/CourseSection/1">课程详情测试</el-menu-item>
            <!-- <el-menu-item index="/ShowPersonalInformation">个人信息展示测试</el-menu-item> -->
        </el-sub-menu>

        <!-- 侧边栏第三个元素是展示模块 -->
        <el-sub-menu>
            <!-- 设置学业模块子菜单的表头信息 -->
            <template #title>
                <el-icon><View /></el-icon>
                <span>展示模块</span>
            </template>

            <!-- 设置学业模块子菜单内容：学习团体，打卡信息 -->
            <el-menu-item index="/MainPage/Social_Center">打卡信息</el-menu-item>
            <el-menu-item index="/MainPage/StudyGroupCenter">学习团体中心</el-menu-item>
            <el-menu-item index="/MainPage/SchoolInformation/Main">测试1</el-menu-item>
            <el-menu-item index="/MainPage/SchoolInformation/Detail">测试2</el-menu-item>
            <el-menu-item index="/MainPage/UpGradeCenter">升学模块</el-menu-item>
            <!-- <el-menu-item index="/MainPage/SchoolInformation/Detail">测试2</el-menu-item> -->
        </el-sub-menu>

    </el-menu>
</template>

<script>
import { User, Reading, View } from '@element-plus/icons-vue'

export default {
    components: {
        User,
        Reading,
        View,
    },
    data() {
        return {
            activeIndex: '', // 记录当前选中的Menu选项
        };
    },
    methods: {
        handleSelect(index) {
            this.activeIndex = index;  //选择选项时更新活跃选项索引
        }
    }
}
</script>

<style scoped>
/* 设定选中的选项栏的样式 */
.el-menu-item.is-active {
    background-color: #f2f3f5 !important;
}

/* 删除选项栏右侧分界线 */
.el-menu {
    border-right: 1px solid #ffffff;
}
</style>
