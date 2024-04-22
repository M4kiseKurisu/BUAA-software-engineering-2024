<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>
    <div style="width: calc(100vw - 205px); background-color: aliceblue;height: calc(100vh - 115px);">
        <div style="width: 100%;height: 30%; display: flex;align-items: center;justify-content: center;">
            <div style="width: 95%;height: 90%;background-color: white;display: flex;">
                <div
                    style="height: 100%; aspect-ratio: 1/1 ; background-color: white;display: flex;justify-content: center;align-items: center;">
                    <img :size='200' src="../../Images/testAvatar.jpg"
                        style="max-width: 80%;aspect-ratio: 1/1 ; border: 1px solid darkgray;border-radius: 50%;">
                </div>
                <div style="flex-grow: 1;height: 100%;background-color:white;">
                    <div style="width: 100%;height: 25%; display: flex;align-items: center;">
                        <span style="font-size: 2em;font-weight: bold;">{{ userName }}</span>
                        <span style="margin-left: 20px;font-size: large;color: darkgrey;">入学年份：{{ jieshu }}</span>
                        <span style="margin-left: 20px;font-size: large;color: darkgrey;">学院：{{ academy }}</span>
                    </div>
                    <div style="height: 25%;width: 70%;font-size: large;
                            border-bottom: 1px solid darkgray;display: flex;align-items: center;">
                        <span>个性签名：
                            {{ sign }}
                        </span>
                    </div>
                    <div style="height: 25%;width: 100%;display: flex;align-items: center;">
                        <span class="shumuWord">
                            帖子数：{{ postCount }}
                        </span>
                        <span class="shumuWord">
                            粉丝数：{{ followerCount }}
                        </span>
                        <span class="shumuWord">
                            关注数：{{ followCount }}
                        </span>
                        <span class="shumuWord">
                            收到的赞：{{ likeCount }}
                        </span>
                    </div>
                    <div
                        style="height: 25%;width: 100%;display: flex; align-items: center;margin-left: 20px;justify-content: space-between;">
                        <div style="width: 25%;display: flex;">
                            <el-button v-if="!isFollow" type="primary" size="large" plain @click="followOther"><span
                                    style="font-size: large;">关注</span></el-button>
                            <el-button v-if="isFollow" type="primary" size="large" plain disabled><span
                                    style="font-size: large;">已关注</span></el-button>
                            <el-button type="primary" size="large" plain style="margin-left: 50px;"><span
                                    style="font-size: large;">私信</span></el-button>
                        </div>
                        <div style="margin-right: 50px;">
                            <el-button type="warning" size="large" plain><span
                                    style="font-size: large;">举报</span></el-button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="height: 70%;background-color: aliceblue;width: 100%;display: flex;">
            <div style="width: 30%;height: 100%;display: flex;align-items: center;justify-content: center;margin-left: 2%;">
                <div style="width: 80%;height: 95%; background-color: white;">
                    <div style="width: 100%;height: 10%;display: flex;justify-content: center;align-items: center;">
                        <el-button plain size="large" style="width: 90%;" type="primary"><span style="font-size: large;">ta
                                的 帖
                                子</span></el-button>
                    </div>
                    <div style="width: 100%;height: 10%;display: flex;justify-content: center;align-items: center;">
                        <el-button plain size="large" style="width: 90%;" type="primary"><span style="font-size: large;">ta
                                的 收
                                藏</span></el-button>
                    </div>
                </div>
            </div>
            <div style="width: 65%;height: 100%;background-color: white;">
                <div style="width: 100%;height: 100%;">
                    <el-scrollbar style="height: 100%;width: 100%;">
                        <div style="width: 98.1%;height: 100%;">
                            <PostItem></PostItem>
                            <PostItem></PostItem>
                            <PostItem></PostItem>
                            <PostItem></PostItem>
                            <PostItem></PostItem>
                            <PostItem></PostItem>
                        </div>

                    </el-scrollbar>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
import { ElMessage } from 'element-plus'
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue";
import PostItem from "../PostCenter/PostItem.vue";
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props: {

    },
    components: {
        BreadcrumbLabel,
        PostItem,
    },
    data() {
        return {
            userName: '博酱',
            userId: 1,
            userAvatar: "",
            sign: '一顿能吃三碗饭一顿能吃三碗饭一顿能吃三碗饭一顿能吃三碗饭一顿能吃三碗饭一',
            followCount: 5,
            postCount: 1,
            likeCount: 20,
            followerCount: 15,
            route: ['他人信息'],
            jieshu: '2021',
            academy: '计算机学院',
            isFollow: false,
        }
    },
    methods: {
        GetInfomation() {
            axios({
                method: "GET",
                url: "api/user/social/others",
                data: userId,
            }).then((result) => {
                this.userName = result.name;
                this.userAvatar = result.user_avatar;
                this.followCount = result.following_count;
                this.followerCount = result.follower_count;
                this.postCount = result.post_count;
                this.likeCount = result.like_count;
                this.sign = result.sign;
                this.isFollow = result.is_follow;
            });
        },
        followOther() {
            this.isFollow = true;
        },
    }
}
</script>

<style scoped>
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}

.shumuWord {
    font-size: x-large;
    margin-left: 20px;
}
</style>