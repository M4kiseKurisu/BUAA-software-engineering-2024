<template>
    <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

    <div class="main-postpage-container">
        <!-- 帖子正文部分 -->
        <div class="post-main-content-container">
            <div class="post-main-title">本文的标题ABCDABCDABCD</div>
            <el-divider/>

            <div>12312312312312312312312312</div>









        </div>
    </div>
</template>

<script>
// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"

export default {
    components: {
        BreadcrumbLabel, 
    },
    data() {
        return {
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
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

.post-main-title {
    font-size: 24px;
    color: #101010;
    height: 34px;
    margin-left: 60px;
    margin-bottom: 30px;
}

.el-divider--horizontal {
    border-top: 1px solid #e5e6eb !important;
    margin: 0px !important;
}
</style>