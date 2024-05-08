<template>
    <div style="width: 100%;height: 100px;">
        <div style="width: 100%;height: 40px;display: flex;">
            <span style="margin-left: 20px;font-size: 1.2em;font-weight: bold;margin-top: 20px;"> {{ noticeTitle }}</span>
            <span style="margin-top: 22.5px;margin-left: 10px;font-size: 1em;">{{ noticeTime }} </span>
        </div>
        <div style="width: 100%;height: 60px;margin-left: 20px;display: flex;">
            <div
                style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis; width: 75%;font-size: large;margin-top: 10px;">
                {{ noticeContent }}</div>
            <div style="height: 100%;display: flex;width: 20%;justify-content: end;">
                <el-button type="primary" plain @click="showDetail = true">查看详情</el-button>
            </div>
        </div>
    </div>
    <el-dialog v-model="showDetail" title="系统通知详情" width="600">
        <div style="font-size: 1.3em; font-weight: bold;color: dimgray;width: 100%;">通知时间：{{ noticeTime }}</div>
        <div style="font-size: 1.2em;margin-top: 10px;">{{ noticeContent }}</div>
    </el-dialog>
</template>

<script>
import axios from "axios";
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props:{
        noticeInfo:{
            type: Object,
            default: null,
        }
    },
    data() {
        return {
            noticeId: 1,
            noticeUserName: '',
            noticeTitle: '系统通知',
            noticeContent: '你他妈被举报了你他妈被举报了你他妈被举报了你他妈被举报了你他妈被举报了你他妈被举报了你他妈被举报了',
            noticeTime: '2022.22.2.2',
            showDetail: false,
        }
    },
    methods:{

    },
    created(){
        if(this.noticeInfo != null){
            this.noticeId = this.noticeInfo.notice_id;
            this.noticeUserName = this.noticeInfo.notice_user_name;
            this.noticeTitle = this.noticeInfo.notice_title;
            this.noticeContent = this.noticeInfo.notice_content;
            this.noticeTime = this.noticeInfo.notice_time;
        }
    },
}
</script>

<style scoped></style>