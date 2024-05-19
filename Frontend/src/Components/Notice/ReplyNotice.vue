<template>
    <div class="replyNoticeContainer">
        <div class="headContainer" @click="goToShowPersonInfomation"><el-avatar :size="60" :src="replyUserHead" />
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
                                                white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                        <span style="margin-left: 5px;"> {{ meName }}: {{ commentContent }}</span>
                    </div>
                    <div style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">回复：{{ replyContent }}</div>
                </div>
                <div style="height: 100%;display: flex;align-items: center;width: 20%;justify-content: end;">
                    <el-button type="primary" plain @click="goToPost">查看详情</el-button>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import { result } from 'lodash';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props: {
        replyInformation: {
            type: Object,
            default: null,
        }
    },
    data() {
        return {
            replyId: 1,
            replyUserId: 1,
            replyUserName: 'bojiang',
            replyUserHead: '',
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
        goToShowPersonInfomation() {
            //this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.memberId});
            let routeUrl = this.$router.resolve({
                path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.replyUserId,
            });
            window.open(routeUrl.href, '_blank');
        },
        getReplyInfomation() {
            this.replyId = this.replyInformation.reply_id;
            this.replyUserId = this.replyInformation.reply_user_id;
            this.replyUserName = this.replyInformation.reply_user_name;
            this.replyToPost = this.replyInformation.reply_to_post;
            this.replyContent = this.replyInformation.reply_content;
            this.replyTime = this.replyInformation.reply_time;
            this.postId = this.replyInformation.post_id;
            this.postTitle = this.replyInformation.post_title;
            this.commentId = this.replyInformation.comment_id;
            this.commentContent = this.replyInformation.comment_content;
            if (!this.replyToPost) {
                axios({
                    method: "GET",
                    url: 'api/user/social/self',
                }).then((result) => {
                    this.meName = result.data.name;
                })
            }
            axios({
                method: "GET",
                url: "api/user/social/others",
                params: { id: this.replyUserId, },
            }).then((result) => {
                //console.log(result);
                this.replyUserName = result.data.name;
                this.replyUserHead = result.data.user_avatar;
            });
        },
        goToPost() {
            this.$router.push({ path: '/MainPage/Course_Center/PostPage/' + this.postId });
        },
    },
    created() {
        if (this.replyInformation != null) {
            this.getReplyInfomation();
        }
        //console.log(this.replyInformation);
    }
}
</script>
<style scoped>
.contentContainer {
    display: flex;
    /* align-items: center; */
    height: 60%;
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
    height: 40%;
    align-items: center;
}

.backTest {
    background-color: rgb(230, 225, 223);
}
</style>