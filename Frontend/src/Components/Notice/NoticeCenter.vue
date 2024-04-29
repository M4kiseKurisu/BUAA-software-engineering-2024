<template>
    <!-- <el-button plain @click="centerDialogVisible = true">
            Click to open the Dialog
        </el-button> -->

    <!-- 上边栏右侧第一个图标负责搜索（未实现） -->
    <div class="notice-circle-bound" @click="centerDialogVisible = true; this.haveNotice = 0;">
        <img src="../../Images/无通知.png" alt="" v-if="haveNotice == 0">
        <img src="../../Images/有通知.png" alt="" v-if="haveNotice == 1">
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
            <DirectMessage v-for="item in selectDirectMessage" :messageInfomation="item" ></DirectMessage>
        </div>
        <div class="noticeList" v-if="noticeChoice == 2">
            <ReplyNotice v-for="item in selectReplyMessage" :replyInformation="item" :key="item.reply_id"></ReplyNotice>
        </div>
        <div class="noticeList" v-if="noticeChoice == 3">

        </div>
        <div class="noticeList" v-if="noticeChoice == 4">

        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 1">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total1"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange1" />
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 2">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total2"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange2" />
            <span>{{ currentPage2 }}</span>
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 3">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total3"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange3" />
            <span>{{ currentPage3 }}</span>
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 4">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total="total4"
                style="position: absolute; right: 0;" @current-change="handleCurrentChange4" />
            <span>{{ currentPage4 }}</span>
        </div>
    </el-dialog>
</template>

<script>
import ReplyNotice from './ReplyNotice.vue';
import DirectMessage from './DirectMessage.vue';
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
            haveNotice: 1,
            allDirectMessage: '',
            allReplyMessage: '',
            allCheckMessage: '',
            allSystemMessage: '',
        };
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
        }
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
                //url: "/api/message/chats",
                url: 'http://127.0.0.1:4523/m1/4272722-0-default/message/chats',
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
                // url: "/api/message/reply",
                url: "http://127.0.0.1:4523/m1/4272722-0-default/message/reply",
                //data: {receiver_id:this.meId,},
            }).then((result) => {
                //console.log(result);
                this.total2 = result.data.reply_count;
                this.allReplyMessage = result.data.reply_list;
                this.noticeChoice = 2;
            })
        },
        getCheckMessage(){

        },
        getSystemMessage(){

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
            this.noticeChoice = 3;
        },
        changeNoticeFour() {
            this.noticeChoice = 4;
        },
    },
    created() {
        this.getDirectMessage();
    },
    components: {
        DirectMessage,
        ReplyNotice,
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
</style>
