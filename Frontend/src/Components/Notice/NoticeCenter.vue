<template>
    <!-- <el-button plain @click="centerDialogVisible = true">
            Click to open the Dialog
        </el-button> -->

    <!-- 上边栏右侧第一个图标负责搜索（未实现） -->
    <div v-if="!this.haveNotice" class="notice-circle-bound" @click="centerDialogVisible = true;this.delectNewNotice() ">
        <!-- <img src="../../Images/无通知.png" alt="" v-if="haveNotice == 0">
        <img src="../../Images/有通知.png" alt="" v-if="haveNotice == 1"> -->
        <svg t="1715050698908" style="width: 80%; height: 80%; margin-left: 3%; margin-top: -2%;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2381" width="200" height="200"><path d="M891.851 813.716L814 701.561V411.873c0-61.969-24.132-120.229-67.951-164.048-43.82-43.82-102.079-67.952-164.049-67.952h-38V153c0-17.673-14.327-32-32-32s-32 14.327-32 32v26.873h-38c-61.97 0-120.23 24.132-164.049 67.952C234.132 291.644 210 349.904 210 411.873v289.688l-77.85 112.155a31.999 31.999 0 0 0 26.288 50.247h245.508C416.92 911.461 460.444 946.48 512 946.48s95.08-35.019 108.054-82.518h245.508a32 32 0 0 0 26.289-50.246zM512 882.48c-15.359 0-29.045-7.259-37.837-18.518h75.675c-8.792 11.259-22.479 18.518-37.838 18.518z m-292.396-82.517l48.684-70.138A32.002 32.002 0 0 0 274 711.578V411.873c0-92.636 75.364-168 168-168h140c92.636 0 168 75.364 168 168v299.705a32.002 32.002 0 0 0 5.712 18.247l48.686 70.138H219.604z" fill="#86909c" p-id="2382"></path></svg>
    </div>
    <div v-if="this.haveNotice" class="notice-circle-bound" @click="centerDialogVisible = true;this.delectNewNotice() ">
        <!-- <img src="../../Images/无通知.png" alt="" v-if="haveNotice == 0">
        <img src="../../Images/有通知.png" alt="" v-if="haveNotice == 1"> -->
        <svg t="1716126157955" style="width: 100%; height: 100%; margin-left: 3%; margin-top: -2%;" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5311" width="8" height="8"><path d="M727.744 680.96a18.656 18.656 0 0 1-18.56-18.88v-234.24c1.92-108.16-84.16-197.44-192.64-199.68-108.16-1.92-197.76 84.16-199.68 192.32v241.28c0 5.12-1.92 9.6-5.44 13.44-3.52 3.52-8.32 5.44-13.12 5.44h-37.12v37.76h501.76v-37.76h-35.2z m35.2-37.76c9.92 0 19.2 3.84 26.24 10.88 7.04 7.04 10.88 16.64 10.88 26.56v37.76c0 20.48-16.64 37.44-37.12 37.44H261.184c-20.48 0-37.12-16.96-37.12-37.44v-37.76c0-9.92 3.84-19.52 10.88-26.56 7.04-7.04 16.32-10.88 26.24-10.88h18.56v-215.36c-1.6-128.64 101.76-234.24 230.4-235.84s234.56 101.76 235.84 230.4v220.8h16.96z m-297.6 131.84h111.36c0.64 30.72-23.68 56.32-54.4 56.96-30.72 0.64-56.32-23.68-56.96-54.4v-2.56z" fill="#888888" p-id="5312"></path><path d="M672 320m-128 0a128 128 0 1 0 256 0 128 128 0 1 0-256 0Z" fill="#FF1A1A" p-id="5313"></path></svg>
    </div>
    <el-dialog v-model="this.centerDialogVisible" width="800px" height="1000px" center>
        <div class="bar">
            <div class="buttonContainer" :class="{ active: noticeChoice === 1 }" @click='changeNoticeOne'>
                <img src="../../Images/私信.png">
                <span style="font-weight: bolder;">私信消息</span>
            </div>
            <div class="buttonContainer" :class="{ active: noticeChoice === 2 }" @click='changeNoticeTwo'>
                <img src="../../Images/评论回复.png">
                <span style="font-weight: bolder;">回复我的</span>
            </div>
            <div class="buttonContainer" :class="{ active: noticeChoice === 3 }" @click='changeNoticeThree'>
                <img src="../../Images/审核.png">
                <span style="font-weight: bolder;">申请信息</span>
            </div>
            <div class="buttonContainer" :class="{ active: noticeChoice === 4 }" @click='changeNoticeFour'>
                <img src="../../Images/系统通知.png">
                <span style="font-weight: bolder;">系统通知</span>
            </div>
        </div>

        <!-- <template #footer>
                <div class="dialog-footer">
                    <el-button @click="centerDialogVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="centerDialogVisible = false">
                        Confirm
                    </el-button>
                </div>
            </template> -->
        <div class="noticeList" v-if="noticeChoice == 1">
            <DirectMessage v-for="item in selectDirectMessage" :messageInfomation="item" :key = "item.id"></DirectMessage>
        </div>
        <div class="noticeList" v-if="noticeChoice == 2">
            <ReplyNotice v-for="item in selectReplyMessage" :replyInformation="item" :key="item.reply_id"></ReplyNotice>
        </div>
        <div class="noticeList" v-if="noticeChoice == 3">
            <JudgeNotice v-for = "item in selectJudgeMessage" :noticeInfo = "item" :key = "item.apply_id" @updateNotice = "getJudgeMessage"></JudgeNotice>
        </div>
        <div class="noticeList" v-if="noticeChoice == 4">
            <SystemNotice v-for = "item in selectSystemMessage" :noticeInfo = "item" :key = "item.notice_id"></SystemNotice>
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 1">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total1"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange1" />
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 2">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total2"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange2" />
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 3">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total3"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange3" />
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 4">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total4"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange4" />
        </div>
    </el-dialog>
</template>

<script>
import ReplyNotice from './ReplyNotice.vue';
import DirectMessage from './DirectMessage.vue';
import JudgeNotice from './JudgeNotice.vue';
import SystemNotice from './SystemNotice.vue';
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    data() {
        return {
            meId: 1,
            noticeChoice: 1,
            currentPage1: 1,
            currentPage2: 1,
            currentPage3: 1,
            currentPage4: 1,
            total1: 1,
            total2: 1,
            total3: 1,
            total4: 1,
            centerDialogVisible: false,
            allDirectMessage: [],
            allReplyMessage: [],
            allJudgeMessage: [],
            allSystemMessage: [],
            haveNotice: false,
        };
    },
    props: {
        newNotice:{
            type:Boolean,
            default: false,
        }
    },
    computed: {
        selectDirectMessage() {
            var begin, end;
            begin = this.currentPage1 * 6 - 6;
            end = this.currentPage1 * 6;
            if (end > this.total1) {
                end = this.total1;
            }
            return this.allDirectMessage.slice(begin, end);
        },
        selectReplyMessage() {
            var begin, end;
            begin = this.currentPage2 * 6 - 6;
            end = this.currentPage2 * 6;
            if (end > this.total2) {
                end = this.total2;
            }
            return this.allReplyMessage.slice(begin, end);
        },
        selectJudgeMessage() {
            var begin, end;
            begin = this.currentPage3 * 6 - 6;
            end = this.currentPage3 * 6;
            if (end > this.total3) {
                end = this.total3;
            }
            return this.allJudgeMessage.slice(begin, end);
        },
        selectSystemMessage() {
            var begin, end;
            begin = this.currentPage4 * 6 - 6;
            end = this.currentPage4 * 6;
            if (end > this.total4) {
                end = this.total4;
            }
            return this.allSystemMessage.slice(begin, end);
        },
    },
    methods: {
        handleCurrentChange1(val) {
            this.currentPage1 = val;
        },
        handleCurrentChange2(val) {
            this.currentPage2 = val;
        },
        handleCurrentChange3(val) {
            this.currentPage3 = val;
        },
        handleCurrentChange4(val) {
            this.currentPage4 = val;
        },
        getDirectMessage() {
            axios({
                method: "GET",
                url: "/api/message/chats",
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/message/chats',
                //data: {receiver_id:this.meId,},
            }).then((result) => {
                //console.log(result);
                this.total1 = result.data.chat_count;
                this.allDirectMessage = result.data.chat_list;
                this.noticeChoice = 1;
            })
        },
        getReplyMessage() {
            axios({
                method: "GET",
                url: "/api/message/reply",
                //url: "http://127.0.0.1:4523/m1/4272722-0-default/message/reply",
                //data: {receiver_id:this.meId,},
            }).then((result) => {
                console.log(result);
                this.total2 = result.data.reply_count;
                this.allReplyMessage = result.data.reply_list;
                this.noticeChoice = 2;
            })
        },
        getJudgeMessage(){
            axios({
                method: "GET",
                url: "/api/message/apply",
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/message/chats',
                //data: {receiver_id:this.meId,},
            }).then((result) => {
                //console.log(result);
                this.total3 = result.data.apply_count;
                this.allJudgeMessage = result.data.apply_list;
                this.noticeChoice = 3;
            })
        },
        getSystemMessage(){
            axios({
                method: "GET",
                url: "/api/message/notice",
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/message/chats',
                //data: {receiver_id:this.meId,},
            }).then((result) => {
                //console.log(result);
                this.total4 = result.data.notice_count;
                this.allSystemMessage = result.data.notice_list;
                this.noticeChoice = 4;
            })
        },
        changeNoticeOne() {
            //this.noticeChoice = 1;
            this.getDirectMessage();
        },
        changeNoticeTwo() {
            //this.noticeChoice = 2;
            this.getReplyMessage();
        },
        changeNoticeThree() {
            //this.noticeChoice = 3;
            this.getJudgeMessage();
        },
        changeNoticeFour() {
            //this.noticeChoice = 4;
            this.getSystemMessage();
        },
        delectNewNotice() {
            //console.log(this.groupId);
            //console.log("rudiao")
            this.haveNotice = false;
            this.$emit('delectNewNotice');
            this.getDirectMessage();
            this.noticeChoice = 1;
        },
    },
    created() {
        this.haveNotice = this.newNotice;
        //console.log(this.haveNotice);
    },
    components: {
        DirectMessage,
        ReplyNotice,
        JudgeNotice,
        SystemNotice,
    }
}
</script>

<style scoped>
.notice-circle-bound {
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

.bar {
    width: 100%;
    height: max-content;
    display: flex;
    justify-content: center;
}

.buttonContainer {
    margin-top: 5px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    width: 25%;
}

.overlay {
    width: 100px;
    height: 100px;
    background-color: rgba(0, 0, 0, 0.5);
    /* 半透明黑色背景 */
    z-index: 1;
}

.buttonContainer.active {
    background-color: rgb(226, 240, 251);
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}

.noticeList {
    width: 100%;
    height: 600px;
}

.favorates-header-2 {
    font-size: 14px;
    color: #165dff;
    height: 20px;
    margin-top: 41px;
    border: none;
}
</style>
