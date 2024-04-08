<template>
    <div class="header">

        <!-- 上边栏左侧为网页标题 -->
        <div class="title">航学通——北航学习互助平台</div>

        <!-- 以下模块包含上边栏右侧全部信息 -->
        <div class="header-right">

            <!-- 上边栏右侧第一个图标负责搜索（未实现） -->
            <div class="circle-bound">
                <el-icon color="#bbbbbb" :size="21"><Search /></el-icon>
            </div>

            <!-- 上边栏最右侧为头像 -->
            <div class="avatar">
                <el-avatar :size="32" :src="avatarPicture"/>
            </div>

        </div>

    </div>
    
</template>

<script>
import { Search } from '@element-plus/icons-vue'

export default {
    components: {
        Search,
    },
    data() {
        return {
            avatarPicture: "./src/Images/testAvatar.jpg",  //测试的头像信息
        }
    }
}

</script>

<style scoped>
/* 设置上边栏内元素横向排列 */
.header {
    display: flex;
    /* 设置子元素可选择靠左还是靠右排列 */
    justify-content: space-between;
}

/* 设置网站标题的位置和样式 */
.title {
    margin-left: 27px;
    margin-top: 16px;
    font-size: 20px;
    justify-content: flex-start;
}

/* 上边栏右侧内容全部右对齐 */
.header-right {
    justify-content: flex-end;
    display: flex;
}

/* 搜索按钮的圆形边框样式 */
.circle-bound {
    width: 32px;
    height: 32px;
    border: 1px solid #f2f3f5;
    border-radius: 16px;
    margin-top: 14px;
    margin-right: 16px;
    justify-content: center;
    align-items: center;
    display: flex;
}

/* 设置头像的位置 */
.avatar {
    margin-right: 17px;
    margin-top: 14px;
}
</style>