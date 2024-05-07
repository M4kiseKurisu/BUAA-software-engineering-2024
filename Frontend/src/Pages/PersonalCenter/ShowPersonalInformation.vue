<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>
    <div style="width: calc(100vw - 205px); background-color: aliceblue;height: calc(100vh - 115px);">
        <div style="width: 100%;height: 30%;min-height: 252px; display: flex;align-items: center;justify-content: center;">
            <div style="width: 95%;height: 90%;background-color: white;display: flex;">
                <div
                    style="height: 100%; aspect-ratio: 1/1 ; background-color: white;display: flex;justify-content: center;align-items: center;">
                    <img :src="userAvatar"
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
                            <el-button v-if="isFollow" type="primary" size="large" plain @click = "cancleFollow"><span
                                    style="font-size: large;">取消关注</span></el-button>
                            <el-button type="primary" size="large" plain style="margin-left: 50px;"><span
                                    style="font-size: large;">私信</span></el-button>
                        </div>
                        <div style="margin-right: 50px;">
                            <Report  :type="0" :id="this.userId"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="height: 70%;background-color: aliceblue;width: 100%;display: flex;">
            <div style="width: 30%;height: 100%;display: flex;align-items: center;justify-content: center;margin-left: 2%;">
                <div style="min-height: 470px;width: 80%;height: 95%; background-color: white;box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);">
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
import Report from "../../Components/Tool/reportButton.vue"
import PostItem from "../PostCenter/PostItem.vue";
import axios from 'axios';
import { result } from 'lodash';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props: {

    },
    components: {
        BreadcrumbLabel,
        PostItem,
        Report,
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
            isFollow: true,
        }
    },
    methods: {
        GetInfomation() {
            axios({
                method: "GET",
                url: "/api/user/social/others",
                params: { id: this.userId}
            }).then((result) => {
                console.log(result);
                this.userName = result.data.name;
                this.userAvatar = result.data.user_avatar;
                this.followCount = result.data.following_count;
                this.followerCount = result.data.follower_count;
                this.postCount = result.data.post_count;
                this.likeCount = result.data.like_count;
                this.sign = result.data.sign;
                this.isFollow = result.data.flag_follow;
            });
        },
        followOther() {
            axios({
                method: "POST",
                url: "/api/user/follow",
                data: {
                    follow_id : this.userId,
                }
            }).then((result) => {
                console.log(result);
                if(result.data.success){
                     this.isFollow = true;
                }
            })
            
        },
        cancleFollow() {
            axios({
                method: "POST",
                url: "/api/user/unfollow",
                data: {
                    unfollow_id : this.userId,
                }
            }).then((result) => {
                console.log(result);
                if(result.data.success){
                     this.isFollow = false;
                }
            })
        },

    },
    created() {
        this.userId = this.$route.params.userId;
        this.GetInfomation();
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
    font-size: 1.3em;
    margin-left: 20px;
}
</style>