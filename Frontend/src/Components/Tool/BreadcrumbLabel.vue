<template>
    <!-- 由于网站只有最后一级面包屑有对应的网页，因此就直接由使用自制的面包屑的网页传路径，不从router.js调用 -->
    <div class="breadcrumbLabel">

        <el-icon :size="16" color="#86909C"><House /></el-icon>

        <!-- 将父元素传入的路径数组循环展示成为面包屑 -->
        <div v-for="(item, index) in routeNames" class="link-loop">
            <div class="seperator">/</div>
            <div class="link" :class="{ 'last-item': index === routeNames.length - 1 }">{{ item }}</div>
        </div>
        
    </div>
</template>

<script>
import { House } from '@element-plus/icons-vue'

export default {
    components: {
        House,
    },
    props: ["routeNames"]
}
</script>

<style>
/* 控制整个面包屑组建的高度 */
.breadcrumbLabel {
    display: flex;
    height: 17px;
}

/* 使每个路径节都横向排列 */
.link-loop {
    display: flex;
}

/* 设定分割路径的分号的样式 */
.seperator {
    color: #bbbbbb;
    margin-left: 5px;
    margin-right: 5px;
    font-size: 12px;
}

/* 设定路径文字样式 */
.link {
    font-size: 12px;
    color: #4e5969;
}

/* 将最后一段路径（本界面位置）文字颜色加深 */
.last-item {
    color: #101010;
}
</style>
