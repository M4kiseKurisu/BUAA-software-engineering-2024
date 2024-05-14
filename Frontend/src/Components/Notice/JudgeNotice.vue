<template>
    <div class="judgeNoticeContainer" v-if="!isFeedBack">
        <div class="headContainer"><el-avatar :size="60" :src="userAvatar" /></div>
        <div style="margin-left: 10px;width: 90%;">
            <div class="senderContainer">
                <span style="font-size: large;font-weight: bolder;color: black;">{{ userName }}</span>
                <span style="margin-left: 5px;">申请加入你的学习团体</span>
                <span style="margin-left: 5px;font-size: large;font-weight: bolder;color: black;">{{ groupName }}</span>
            </div>
            <div class="contentContainer">
                <div style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis; width: 80%;font-size: large;">{{
                    applyContent }}</div>
                <div style="height: 100%;display: flex;width: 20%;justify-content: end;">
                    <el-button type="primary" plain @click="showDetail = true">查看详情</el-button>
                </div>
            </div>
        </div>
        <el-dialog v-model="showDetail" title="申请详情" width="650">
            <div style="width: 100%;display: flex;margin-top: 10px;align-items: center;">
                <span class="create_group_itemtext">申请人：</span>
                <el-link  class="create_group_itemtext" @click = "goToPersonCenter">{{ userName }}</el-link>
            </div>
            <div style="width: 100%;display: flex;margin-top: 10px;align-items: center;">
                <span class="create_group_itemtext">申请团体：{{ groupName }}</span>
                <!-- <el-link  class="create_group_itemtext" >{{  }}</el-link> -->
            </div>
            <div style="width: 100%;display: flex;margin-top: 10px;">
                <div class="create_group_itemtext" style="width: 35%;">申请信息：</div>
                <div  style="flex-grow: 1;font-size: 1.3em;">
                    {{ applyContent }}
                </div>
                <!-- <el-link  class="create_group_itemtext" >{{  }}</el-link> -->
            </div>
            <div style="width: 100%;display: flex;margin-top: 20px;">
                <div style="width: 50%;display: flex;align-items: center;justify-content: center;">
                    <el-button type = "primary" plain>通过</el-button>
                </div>
                <div style="width: 50%;display: flex;align-items: center;justify-content: center;">
                    <el-button type = "primary" plain>不通过</el-button>
                </div>
            </div>
        </el-dialog>
    </div>
    <div v-else style=" width: 100%;height: 100px;">
        <div style="width: 100%;height: 40px;display: flex;">
            <span style="margin-left: 20px;font-size: 1.2em;font-weight: bold;margin-top: 25px;">申请反馈</span>
            <el-tag effect="dark" type= 'success' style="margin-top: 25px;margin-left: 10px;font-size: 1em;" v-if = "applyFeedBack">通过</el-tag>
            <el-tag effect="dark" type= 'danger' style="margin-top: 25px;margin-left: 10px;font-size: 1em;" v-if = "!applyFeedBack">未通过</el-tag>
        </div>
        <div style="width: 100%;height: 60px;display: flex;align-items: center;">
            <span style="margin-left: 20px;font-size: 1.5em;" v-if = "applyFeedBack">
                您加入学习团体<span style="font-weight: bold;">{{groupName}}</span>的申请已通过
            </span>
            <span style="margin-left: 20px;font-size: 1.5em;" v-if = "!applyFeedBack">
                您加入学习团体<span style="font-weight: bold;">{{groupName}}</span>的申请未通过
            </span>
        </div>
    </div>
</template>

<script>
import axios from "axios";
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props:{
        noticeInfo:{
            type:Object,
            default: null,
        }
    },
    data() {
        return {
            applyId: 1,
            userId: 1,
            groupId: 1,
            groupLeaderId: 1,
            groupName: '元神讨论组',
            applyTitle: '',
            applyFeedBack: false,
            isFeedBack: true,
            userName: '博酱',
            userAvatar: '',
            applyContent: '我元神六十级我元神六十级我元神六十级我元我元我元神六十级我元神六十级我元神六十级我元我元我元神六十级我元神六十级我元神六十级我元我元',
            showDetail: false,
        }
    },
    methods:{
        goToPersonCenter(){
            // this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.userId}).then(() => {
            //     this.$nextTick(() => {
            //         // 强制重新加载当前页面
            //         location.reload();
            //     });
            // });
            let routeUrl = this.$router.resolve({
                path: "/MainPage/Course_Center/ShowPersonalInformation/"+ this.userId ,
            });
            window.open(routeUrl.href, '_blank');
        }
    },
    created(){
        if(this.noticeInfo !=  null){
            this.applyId = this.noticeInfo.apply_id;
            this.userId = this.noticeInfo.user_id;
            this.groupId = this.noticeInfo.group_id;
            this.groupLeaderId = this.noticeInfo.group_leader_id;
            this.applyTitle = this.noticeInfo.apply_title;
            this.applyFeedBack = this.noticeInfo.apply_feedback_info;
            this.isFeedBack = this.noticeInfo.is_apply_feedback;
            this.groupName = this.noticeInfo.group_name ;
            this.userName = this.noticeInfo.user_name;
            this.userAvatar = this.noticeInfo.user_avatar;
            this.applyContent = this.noticeInfo.apply_content;
        }
    },
}
</script>

<style scoped>
.judgeNoticeContainer {
    width: 100%;
    height: 100px;
    display: flex;

}

.headContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}

.senderContainer {
    display: flex;
    align-items: center;
    height: 50%;
}

.contentContainer {
    display: flex;
    width: 100%;
    /* align-items: center; */
    height: 50%;
}

.create_group_itemtext {
    font-size: 1.3em;
    font-weight: bold;
    color: dimgray;
}
</style>