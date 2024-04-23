<template>
    <!-- <el-button plain @click="centerDialogVisible = true">
            Click to open the Dialog
        </el-button> -->

    <!-- 上边栏右侧第一个图标负责搜索（未实现） -->
    <div class="notice-circle-bound" @click="centerDialogVisible = true;this.haveNotice = 0;">
        <img src="../../Images/无通知.png" alt="" v-if="haveNotice == 0">
        <img src="../../Images/有通知.png" alt="" v-if="haveNotice == 1">
    </div>

    <el-dialog v-model="this.centerDialogVisible" width="800px" height="1000px" center>
        <div class="bar">
            <div class="buttonContainer" :class="{ active: noticeChoice === 1 }" @click='noticeChoice = 1'>
                <img src="../../Images/私信.png">
                <span style="font-weight: bolder;">私信消息</span>
            </div>
            <div class="buttonContainer" :class="{ active: noticeChoice === 2 }" @click='noticeChoice = 2'>
                <img src="../../Images/评论回复.png">
                <span style="font-weight: bolder;">回复我的</span>
            </div>
            <div class="buttonContainer" :class="{ active: noticeChoice === 3 }" @click='noticeChoice = 3'>
                <img src="../../Images/审核.png">
                <span style="font-weight: bolder;">申请信息</span>
            </div>
            <div class="buttonContainer" :class="{ active: noticeChoice === 4 }" @click='noticeChoice = 4'>
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
            <DirectMessage></DirectMessage>
            <DirectMessage></DirectMessage>
            <DirectMessage></DirectMessage>
            <DirectMessage></DirectMessage>
            <DirectMessage></DirectMessage>
            <DirectMessage></DirectMessage>
        </div>
        <div class="noticeList" v-if="noticeChoice == 2">
            <ReplyNotice></ReplyNotice>
            <ReplyNotice></ReplyNotice>
        </div>
        <div class="noticeList" v-if="noticeChoice == 3">

        </div>
        <div class="noticeList" v-if="noticeChoice == 4">

        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 1">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total = "total1" style="position: absolute; right: 0;"
                @current-change="handleCurrentChange1" />
            <span>{{ currentPage1 }}</span>
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 2">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total = "total2" style="position: absolute; right: 0;"
                @current-change="handleCurrentChange2" />
            <span>{{ currentPage2 }}</span>
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 3">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total = "total3" style="position: absolute; right: 0;"
                @current-change="handleCurrentChange3" />
            <span>{{ currentPage3 }}</span>
        </div>
        <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 4">
            <el-pagination background layout="prev, pager, next" :page-size="6" :total = "total4" style="position: absolute; right: 0;"
                @current-change="handleCurrentChange4" />
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
            total1: 15,
            total2: 1,
            total3: 1,
            total4: 1,
            centerDialogVisible: false,
            haveNotice : 1,
            allDirectMessage : '',
        };
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
        getDirectMessage(){
            axios({
                method:"GET",
                url: "api/message/private",
                data: {receiver_id:this.meId,},
            }).then((result) => {
                this.total1 = result.message_count ;
                this.allDirectMessage = result.message_list;
                this.noticeChoice = 1;
            })
        },
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
}

.noticeList {
    width: 100%;
    height: 600px;
}
</style>
