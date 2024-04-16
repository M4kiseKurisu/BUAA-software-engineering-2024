<template>
    <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

    <div class="main-postpage-container">
        <!-- 帖子正文部分 -->
        <div class="post-main-content-container">
            <div class="post-page-header-container">

                <!-- 帖子头部左侧 -->
                <div class="post-page-header-left">
                    <div class="post-main-title">本文的标题ABCDABCDABCD</div>

                    <button class="post-main-delete-button">
                        <el-icon :size="16" color="#86909C"><Delete /></el-icon>
                    </button>
                </div>
                
                <!-- 帖子头部右侧 -->
                <div class="post-page-header-right">
                    <div class="post-page-tag-css">本文标签</div>

                    <div class="icon-and-content">
                        <!-- 喜欢图标 -->
                        <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#d81e06"></path></svg>
                        <div class="like-icon-after-contents">XX人点赞</div>
                    </div>

                    <div class="icon-and-content">
                        <!-- 收藏图标 -->
                        <svg t="1713272491744" class="starfill-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8600" width="200" height="200"><path d="M908.1 353.1l-253.9-36.9L540.7 86.1c-3.1-6.3-8.2-11.4-14.5-14.5-15.8-7.8-35-1.3-42.9 14.5L369.8 316.2l-253.9 36.9c-7 1-13.4 4.3-18.3 9.3-12.3 12.7-12.1 32.9 0.6 45.3l183.7 179.1-43.4 252.9c-1.2 6.9-0.1 14.1 3.2 20.3 8.2 15.6 27.6 21.7 43.2 13.4L512 754l227.1 119.4c6.2 3.3 13.4 4.4 20.3 3.2 17.4-3 29.1-19.5 26.1-36.9l-43.4-252.9 183.7-179.1c5-4.9 8.3-11.3 9.3-18.3 2.7-17.5-9.5-33.7-27-36.3z" p-id="8601" fill="#f4ea2a"></path></svg>
                        <div class="starfill-icon-after-contents">YY人收藏</div>
                    </div>
                </div>
                
            </div>
            
            <el-divider/>

            <div class="post-more-information-line">

                <div class="post-more-information-left">
                    <!-- 作者头像 -->
                    <el-avatar shape="square" :size="60" :src="this.avatarPicture" />

                    <div class="post-writer-information">
                        <div class="post-writer-username">文章作者昵称</div>
                        <div class="post-writer-signature">文章作者的个人签名：ABCDABCDABCDABCDABCDABCDABCDABCDABCD</div>
                    </div>
                </div>

                <div class="post-more-information-right">
                    <button class="post-page-tag-css">关注作者</button>
                </div>

            </div>

            <div class="post-main-content">
                12312312312312312
            </div>









        </div>
    </div>
</template>

<script>
// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"

import { Delete } from '@element-plus/icons-vue'

export default {
    components: {
        BreadcrumbLabel, 
        Delete, 
    },
    data() {
        return {
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
            avatarPicture: "./src/Images/testAvatar.jpg",  //作者头像
        }
    }
}
</script>

<style scoped>
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}

.main-postpage-container {
    width: calc(100vw - 205px);
    background-color: rgba(247, 248, 250, 0.7);
    padding-top: 29px;
    padding-bottom: 81px;
}

.post-main-content-container {
    margin-left: 80px;
    margin-right: 80px;
    padding-top: 36px;
    background-color: white;
    border-radius: 2px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);;
}

.post-page-header-container {
    display: flex;
    justify-content: space-between;
}

.post-main-title {
    font-size: 24px;
    color: #101010;
    height: 34px;
    margin-left: 60px;
    margin-bottom: 30px;
}

.post-page-header-left {
    display: flex;
}

.post-main-delete-button {
    border: none;
    background-color: white;
    height: 16px;
    width: 16px;
    margin-left: 9px;
    margin-top: 8px;
}

.post-page-header-right {
    display: flex;
}

.post-page-tag-css {
    width: 72px;
    height: 26px;
    background-color: #e9f3ff;
    border: 1px solid #3894ff;
    border-radius: 3px;
    font-size: 14px;
    color: #3894ff;
    margin-top: 4px;
    margin-right: 16px;
    display: flex;
    align-items: center;
    justify-content: center; 
}

.icon-and-content {
    display: flex;
    margin-right: 16px;
}

.like-icon {
    margin-top: 4px;
    height: 24px;
    width: 24px;
}

.like-icon-after-contents {
    height: 24px;
    margin-left: 11px;
    margin-top: 7px;
    color: #d81e06;
    font-size: 14px;
}

.starfill-icon {
    margin-top: 4px;
    height: 24px;
    width: 24px;
}

.starfill-icon-after-contents {
    height: 24px;
    margin-left: 11px;
    margin-top: 7px;
    color: #f4ea2a;
    font-size: 14px;
}

.el-divider--horizontal {
    border-top: 1px solid #e5e6eb !important;
    margin: 0px !important;
}

.post-more-information-line {
    display: flex;
    justify-content: space-between;
}

.post-more-information-left {
    display: flex;
    margin-left: 61px;
    margin-top: 39px;
}

.post-writer-information {
    margin-left: 18px;
}

.post-writer-username {
    height: 28px;
    font-size: 20px;
    color: #165dff;
    margin-top: 1px;
}

.post-writer-signature {
    height: 24px;
    width: 572px;
    overflow: hidden;
    word-wrap: break-word;
    font-size: 16px;
    color: #86909c;
    margin-top: 6px;
}

.post-more-information-right {
    margin-right: 39px;
    margin-top: 54px;
}

.post-main-content {
    width: 88%;
    height: 200px;
    background-color: #165dff;
    margin-left: 6%;
}

</style>