<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>
    <div style="width: calc(100vw - 220px); background-color: aliceblue;height: calc(100vh - 115px);min-width: 780px;">
        <div style="width: 100%;height: 30%;min-height: 200px; display: flex;align-items: center;justify-content: center;">
            <div style="width: 95%;height: 90%;background-color: white;display: flex;">
                <div
                    style="height: 100%; aspect-ratio: 1/1 ; background-color: white;display: flex;justify-content: center;align-items: center;">
                    <img :src="userAvatar"
                        style="width: 80%;aspect-ratio: 1/1 ; border: 1px solid darkgray;border-radius: 50%;">
                </div>
                <div style="flex-grow: 1;height: 100%;background-color:white;">
                    <div style="width: 100%;height: 25%; display: flex;align-items: center;">
                        <span style="font-size: 1.8em;font-weight: bold;">{{ userName }}</span>
                        <!-- <span style="margin-left: 20px;font-size: large;color: darkgrey;">入学年份：{{ jieshu }}</span>
                        <span style="margin-left: 20px;font-size: large;color: darkgrey;">学院：{{ academy }}</span> -->
                        <el-tag v-if="isBlock" type="danger" effect="dark" style="margin-left: 20px;" size="large">
                            <span style="font-size: large;font-weight: bold;">账号封禁中</span>
                        </el-tag>
                    </div>
                    <div
                        style="height: 25%;width: 70%;font-size: large;border-bottom: 1px solid darkgray;display: flex;align-items: center;">
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
                            <el-button v-if="!isFollow && !isBlock && !isSelf" type="primary" plain @click="followOther"><span
                                    style="font-size: 1.2em;">关注</span></el-button>
                            <el-button v-if="!isFollow && (isBlock || isSelf)" type="primary" plain disabled><span
                                    style="font-size: 1.2em;">关注</span></el-button>
                            <el-button v-if="isFollow" type="primary" plain @click="cancleFollow"><span
                                    style="font-size: 1.2em;">取消关注</span></el-button>
                            <el-button v-if="!isBlock && !isSelf" type="primary" plain style="margin-left: 50px;"
                                @click="goToChatCenter"><span style="font-size: 1.2em;">私信</span></el-button>
                            <el-button v-if="isBlock || isSelf" type="primary" plain style="margin-left: 50px;" disabled><span
                                    style="font-size: 1.2em;">私信</span></el-button>
                        </div>
                        <div style="margin-right: 50px;">
                            <Report v-if="!isSelf" :type="0" :id="this.userId" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="height: 70%;background-color: aliceblue;width: 100%;display: flex;">
            <div style="width: 30%;height: 100%;display: flex;align-items: center;justify-content: center;margin-left: 2%;">
                <div style="width: 80%;height: 95%; background-color: white;box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);">
                    <div style="width: 100%;display: flex;justify-content: center;align-items: center;margin-top: 20px;">
                        <el-button plain size="large" style="width: 90%;" type="primary"><span style="font-size: large;"
                                @click="getPersonalPostList">ta
                                的 帖
                                子</span></el-button>
                    </div>
                    <div style="width: 100%;display: flex;justify-content: center;align-items: center;margin-top: 20px;">
                        <el-button plain size="large" style="width: 90%;" type="primary"><span style="font-size: large;" @click = "getPersonFavoritesPost">ta
                                的 收
                                藏</span></el-button>
                    </div>
                </div>
            </div>
            <div style="width: 65%;height: 100%;background-color: white;">
                <div style="width: 100%;height: 100%;">
                    <div style="height: 100%;width: 100%; overflow-y: auto;overflow-x: hidden;">
                        <div v-if="this.postItemList.length != 0" style="width: 100%;height: 100%;">
                            <PostItem v-for="item in this.postItemList" :getPostId="item.post_id" :key="item.post_id">
                            </PostItem>
                        </div>
                        <div v-if = "this.postItemList.length == 0" class="no-posts-container">
                            <div style="font-size: 1.5em; font-weight: 200;">没有相应帖子或用户设置不可见</div>
                        </div>
                    </div>
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
            route: [{ name: "个人信息", route: "" }],
            jieshu: '2021',
            academy: '计算机学院',
            isFollow: true,
            isBlock: true,
            postItemList: [],
            showNum: 0,
            isSelf: true,
            selfId: JSON.parse(sessionStorage.getItem("id")),
        }
    },
    methods: {
        GetInfomation() {
            axios({
                method: "GET",
                url: "/api/user/social/others",
                params: { id: this.userId }
            }).then((result) => {
                //console.log(result);
                this.userName = result.data.name;
                this.userAvatar = result.data.user_avatar;
                this.followCount = result.data.following_count;
                this.followerCount = result.data.follower_count;
                this.postCount = result.data.post_count;
                this.likeCount = result.data.like_count;
                this.sign = result.data.sign;
                this.isFollow = result.data.flag_follow;
                this.isBlock = result.data.flag_blocked;
                this.route[1] = {name: this.userName, route: ""};
                if (this.userName == undefined) {
                    this.$message({showClose: false, message: '该用户不存在！', type: 'error'});
                }
            });
        },
        followOther() {
            if(this.selfId == this.userId){
                ElMessage({
                    message: '无法关注自己！',
                    type: 'warning',
                })
                return;
            }
            axios({
                method: "POST",
                url: "/api/user/follow",
                data: {
                    follow_id: this.userId,
                }
            }).then((result) => {
                console.log(result);
                if (result.data.success) {
                    this.isFollow = true;
                }
            })

        },
        cancleFollow() {
            axios({
                method: "POST",
                url: "/api/user/unfollow",
                data: {
                    unfollow_id: this.userId,
                }
            }).then((result) => {
                console.log(result);
                if (result.data.success) {
                    this.isFollow = false;
                }
            })
        },
        goToChatCenter() {
            if (this.isFollow == true) {
                this.$router.push({ name: 'ChatCenter', params: { personId: this.userId, groupId: -1 } });
            } else {
                ElMessage({
                    message: '请先关注用户！',
                    type: 'warning',
                })
            }
        },
        getPersonalPostList() {
            axios({
                method: "GET",
                url: "api/user/posts",
                params: {
                    id: this.userId,
                }
            }).then((result) => {
                this.showNum = result.data.count;
                this.postItemList = result.data.posts;
            })
        },
        getPersonFavoritesPost() {
            axios({
                method: "GET",
                url: "api/user/favorites",
                params: {
                    id: this.userId,
                }
            }).then((result) => {
                console.log(result);
                this.showNum = result.data.count;
                this.postItemList = result.data.posts;
            })
        }
    },
    created() {
        this.userId = this.$route.params.userId;
        this.isSelf = (this.userId == JSON.parse(sessionStorage.getItem('id')));
        this.GetInfomation();
        this.getPersonalPostList();
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
    font-size: 1.2em;
    margin-left: 20px;
}

.no-posts-container {
  width: 100%;
  height: 100%; /* 根据需要调整高度 */
  display: flex;
  justify-content: center;
  align-items: center; /* 垂直居中 */
  background-color: rgba(241, 241, 241, 0.5); /* 灰色背景 */
  backdrop-filter: blur(20px); /* 20px 模糊半径 */
  flex-direction: column;
}

</style>