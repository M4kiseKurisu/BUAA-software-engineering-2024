<template>
    <!-- 个人信息中的收藏帖子单元 -->
    <el-card class="card-container" shadow="never">

        <div class="first-line-container">
            <!-- 收藏帖子标题 -->
            <div class="title">{{ title }}</div>

            <!-- 收藏按钮 -->
            <div class="star">
                <el-icon :size="24" color="#fedf00"><StarFilled/></el-icon>
            </div>   
        </div>
        
        <!-- 收藏帖子正文 -->
        <div class="content">{{ content }}</div>

        <!-- 卡片最下面一行的内容 -->
        <div class="last-line-information">
            <!-- 收藏帖子作者头像，昵称 -->
            <div class="avatar-writername">
                <el-avatar :size="24" :src="avatarSrc"/>
                <div class="writername">{{ writerName }}</div>
            </div>

            <!-- 点击跳转至收藏帖子 -->
            <div class="content-link">>点击进入</div>
        </div>
        
    </el-card>
</template>

<script>
import { StarFilled } from '@element-plus/icons-vue'

export default {
    components: {
        StarFilled, 
    },
    props: ["title", "content", "avatarSrc", "writerName"],
}
</script>

<style sccoped>
/* 定义卡片大小和边缘 */
.card-container {
    height: 126px;
    border: 1px solid #e5e6eb;
    border-radius: 2px;
}

/* 卡片第一行样式 */
.first-line-container {
    display: flex;
    justify-content: space-between;
    margin-top: -6px;
    margin-left: -1px;
}

/* 定义文章标题样式，注意抵消el-card自带的20px padding */
.title {
    font-size: 14px !important;
    height: 20px;
    color: #101010;
}

/* 定义星星收藏按钮的位置和样式 */
.star {
    margin-top: 3px;
    margin-right: -5px;
}

/* 定义文章简介样式 */
.content {
    font-size: 14px;
    color: #86909c;
    width: 200px;
    height: 40px;
    line-height: 1.42;
    overflow: hidden;
}

/* 定义最后一行信息位置和排列 */
.last-line-information {
    display: flex;
    justify-content: space-between;
}

/* 定义作者头像样式 */
.avatar-writername {
    margin-top: 8px;
    display: flex;
}

/* 定义作者昵称信息样式 */
.writername {
    margin-left: 10px;
    color: #86909c;
    font-size: 12px;
    margin-top: 4px;
}

/* 定义跳转文字的样式 */
.content-link {
    font-size: 12px;
    color: #165dff;
    margin-top: 18px;
    margin-right: -10px;
}
</style>