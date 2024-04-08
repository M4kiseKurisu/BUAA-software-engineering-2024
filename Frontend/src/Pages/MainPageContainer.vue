
<template>
    <!-- 本页面作为主页面的容器，包含上边栏、侧边栏和右下角的正文部分 -->
    <!-- 正文部分使用router-view，根据路由路径不同显示不同的内容 -->
    <div class="common-layout">
        <el-container>

            <!-- 此处为上边栏对应模块（页面设置为上边栏处于上层） -->
            <el-header height="57px">
                <Header />
            </el-header>
            <el-divider/>

            <el-container>
                <!-- 此处为侧边栏对应模块 -->
                <el-aside width="200px">
                    <Sidebar />
                </el-aside>

                <!-- 此处为页面正文对应模块 -->
                <el-main>
                    <div class="main">
                        <!-- 使分割线和主界面同高 -->
                        <el-divider direction="vertical"/>
                        <div>
                            <!-- 此处为主界面的路由 -->
                            <router-view></router-view>
                        </div>
                    </div>
                </el-main>
            </el-container>

        </el-container>
    </div>
</template>

<script>
//引入侧边栏组件
import Sidebar from "../Components/SideBar/Sidebar.vue";
//引入上边栏组件
import Header from "../Components/SideBar/Header.vue";

export default {
    components: {
        Sidebar,
        Header,
    }
}
</script>

<style scoped>
/* 设置上边栏边界距离为0 */
.el-header {
    --el-header-padding: 0px !important;
}

/* 设置上边栏下方的分割线样式 */
.el-divider--horizontal {
    border-top: 1px solid #bbbbbb !important;
    margin: 0px !important;
}

/* 取消主界面的边界距离 */
.el-main {
    --el-main-padding: 0px !important;
}

/* 设置横向分界线和主界面内容横向排布 */
.main {
    display: flex;
}

/* 设置侧边栏右侧的分割线样式，让其和界面大小相同 */
.el-divider--vertical {
    border-left: 1px solid #e5e6eb !important;
    margin: 0px !important;
    height: auto !important;
}
</style>