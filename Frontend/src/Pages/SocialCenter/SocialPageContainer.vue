<template>
    <div class="page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb" style="margin-bottom: 24px"><BreadcrumbLabel :routeNames="route" /></div>

        <div class="flex-container">
            <div class="container-left"><MySocialPost @childMethod="showDetail"/></div>
            <el-divider direction="vertical"/>
            <div class="container-right">
                <div v-if="this.detail_post_id != 0"><SocialPostDetail :social_post_id="this.detail_post_id"/></div>
            </div>
        </div>        

    </div>
</template>

<script>
import BreadcrumbLabel from '@/Components/Tool/BreadcrumbLabel.vue';

import MySocialPost from './MySocialPost.vue'
import SocialPostDetail from './SocialPostDetail.vue'

export default {
    components: {
        BreadcrumbLabel,
        MySocialPost,
        SocialPostDetail,
    },
    data(){
        return {
            route: ["展示模块", "打卡信息"],
            detail_post_id: 0,
        }
    },
    methods: {
        showDetail(id) {
            this.detail_post_id = id;
            console.log(this.detail_post_id);
        },
    }
}
</script>

<style scoped>
.page-container {
    width: calc(100vw - 205px);
}

.flex-container {
    display: flex;
}

.container-left {
    width: 40%;
}

.container-right {
    width: 60%;
}

.el-divider--vertical {
    border-left: 1px solid #e5e6eb !important;
    margin: 0px !important;
    height: auto !important;
}
</style>