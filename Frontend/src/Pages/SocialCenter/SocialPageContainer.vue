<template>
    <div class="page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb" style="margin-bottom: 24px"><BreadcrumbLabel :routeNames="route" /></div>

        <div class="flex-container">
            <div class="container-left">
                <div style="margin-left: 5%; margin-bottom: 12px;" class="flex-container-between">
                    <el-radio-group v-model="radio">
                        <el-radio-button label="查看本人打卡" value="0" />
                        <el-radio-button label="查看打卡广场" value="1" />
                    </el-radio-group>

                    <el-button plain @click="this.dialogTableVisible = true" style="margin-right: 5%;">
                        发布新打卡
                    </el-button>
                </div>
                <div v-if="radio === '0'"><MySocialPost @childMethod="showDetail"/></div>
                <div v-if="radio === '1'"><OthersSocialPost /></div>
            </div>
            <el-divider v-if="radio === '0'" direction="vertical"/>
            <div class="container-right">
                <div v-if="this.detail_post_id != 0"><SocialPostDetail :social_post_id="this.detail_post_id"/></div>
            </div>
        </div>    
        
        

        <!-- 以下为弹出的发布打卡框 -->
        <el-dialog v-model="dialogTableVisible" title="编辑新打卡" width="75%">
            <div class="flex-container">
                <div style="margin-left: 4%; margin-right: 4%; width: 49%; padding-bottom: 12px;">
                    <div class="title-for-dialog-font" style="margin-top: 15px;">编辑打卡文字</div>
                    <el-input
                        v-model="this.social_post_text"
                        style="width: 100%; margin-top: 16px;"
                        :autosize="{ minRows: 8, maxRows: 8 }"
                        type="textarea"
                        placeholder="输入打卡文字"
                    />   
                </div>

                <el-divider direction="vertical"/>

                <div style="width: 36%; margin-right: 4%; margin-left: 3%;">
                    <div class="title-for-dialog-font" style="margin-top: 15px;">上传打卡图片</div>

                    <div class="flex-container">
                        <el-upload
                            class="avatar-uploader"
                            :show-file-list="false"
                            style="margin-top: 16px;"
                        >
                            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                        </el-upload>

                        <el-upload
                            class="avatar-uploader"
                            :show-file-list="false"
                            style="margin-top: 16px; margin-left: 16px;"
                        >
                            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                            <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                        </el-upload>
                    </div>
                    
                </div>

            </div>

            <el-button type="primary" style="margin-left: 4%; margin-top: 16px;">上传打卡</el-button>
        </el-dialog>

    </div>
</template>

<script>
import BreadcrumbLabel from '@/Components/Tool/BreadcrumbLabel.vue';

import MySocialPost from './MySocialPost.vue'
import SocialPostDetail from './SocialPostDetail.vue'
import OthersSocialPost from './OthersSocialPost.vue'

export default {
    components: {
        BreadcrumbLabel,
        MySocialPost,
        SocialPostDetail,
        OthersSocialPost,
    },
    data(){
        return {
            route: ["展示模块", "打卡信息"],
            detail_post_id: 0,
            radio: "0",
            dialogTableVisible: false,
            social_post_text: "",
        }
    },
    methods: {
        showDetail(id) {
            this.detail_post_id = id;
            console.log(this.detail_post_id);
        },
    },
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

.flex-container-between {
    display: flex;
    justify-content: space-between;
}

.title-for-dialog-font {
    font-size: 18px;
    color: #101010;
}
</style>