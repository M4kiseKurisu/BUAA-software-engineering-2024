<template>
    <div class="replyNoticeContainer">
        <div class="headContainer"><el-avatar :size="60" src="./src/Images/私信.png" />
        </div>
        <div style="margin-left: 10px;width: 90%;">
            <div class="senderContainer">
                <span style="font-size: large;font-weight: bolder;color: black;">{{ replyUserName }}</span>
                <span style="margin-left: 5px;" v-if="replyToPost">回复了你的帖子</span>
                <span style="font-size: large;font-weight: bolder;color: black;margin-left: 5px;" v-if="replyToPost">{{
                    postTitle }}</span>
                <span style="margin-left: 5px;" v-if="!replyToPost">回复了你的评论</span>
                <span style="font-size: large;font-weight: bolder;color: black;margin-left: 5px;" v-if="replyToPost">{{
                    postTitle }}</span>
                <span style="margin-left: 10px;">{{ time }}</span>
            </div>
            <div class="contentContainer">
                <div style="height: 100%;width: 80%;max-width: 80%;;font-size: 1.2em;">
                    <div v-if="!replyToPost" style="border-left: 3px solid darkgray;background-color: rgb(230, 225, 223);margin-bottom: 5px;max-width: 100%;
                        white-space: nowrap;overflow: hidden;text-overflow: ellipsis;" >
                        <span style="margin-left: 5px;"> {{ meName }}: {{ commentContent }}</span>
                    </div>
                    <div style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">回复：{{ replyContent }}</div>
                </div>
                <div style="height: 100%;display: flex;align-items: center;width: 20%;justify-content: end;">
                    <el-button type="primary" plain>查看详情</el-button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            replyId: 1,
            replyUserId: 1,
            replyUserName: 'bojiang',
            meName: 'wodes',
            replyToPost: false,
            replyContent: "喜欢吗~喜欢吗~喜欢吗~喜欢吗~喜欢吗",
            replyTime: "20020200",
            postId: 1,
            postTitle: "hhhh",
            commentId: 1,
            commentContent: "你个吊毛你个吊毛你个吊毛你个吊毛你个吊毛你个吊毛你个吊毛你个吊毛你个吊毛你个吊毛",
        }
    },
    methods: {

    }
}
</script>
<style scoped>
.contentContainer {
    display: flex;
    /* align-items: center; */
    height: 50%;
    justify-content: space-between;
    align-items: center;
}

.replyNoticeContainer {
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
    height: 50%;
    align-items: center;
}

.backTest {
    background-color: rgb(230, 225, 223);
}
</style>