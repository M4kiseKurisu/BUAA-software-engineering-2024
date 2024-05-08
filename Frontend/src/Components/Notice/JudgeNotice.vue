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
                    <el-button type="primary" plain @click="goToPost">查看详情</el-button>
                </div>
            </div>
        </div>
    </div>
    <div v-else style=" width: 100%;height: 100px;">
        <div style="width: 100%;height: 40px;display: flex;align-items: center;">

        </div>
        <div>

        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            applyId: 1,
            userId: 1,
            groupId: 1,
            groupLeaderId: 1,
            groupName: '元神讨论组',
            applyTitle: '',
            applyFeedBack: true,
            isFeedBack: '',
            userName: '博酱',
            userAvatar: '',
            applyContent: '我元神六十级我元神六十级我元神六十级我元我元'
        }
    }
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
</style>