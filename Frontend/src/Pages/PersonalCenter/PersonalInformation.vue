<template>
    <!-- 此容器是为了设定宽度，让内部的所有元素都可以使用百分比宽度 -->
    <div class="page-container">
      <div>
        <div class="favorate_dialog" v-if="favorateList.length!==0">
          <el-dialog v-model="show_favorate" :visible.sync="show_favorate" width="56%" top="5%" center>
            <Favorate class="favorate"></Favorate>
          </el-dialog>
        </div>
        <div v-else>
          <el-dialog v-model="show_favorate" :visible.sync="show_favorate" title="提示" width="20%" top="20%">
            <div style="display: flex;justify-content: center">
              <span style="font-size: 20px">暂无收藏信息</span>
            </div>
          </el-dialog>
        </div>
      </div>
      <div>
        <div class="following_user_dialog" v-if="followingList.length!==0">
          <el-dialog v-model="show_userfollowing" :visible.sync="show_userfollowing" width="40%" center top="5%">
            <Following_user class="user-dialog"></Following_user>

          </el-dialog>
        </div>
        <div v-else>
          <el-dialog v-model="show_userfollowing" :visible.sync="show_userfollowing" title="提示" width="20%" top="20%">
            <div style="display: flex;justify-content: center">
              <span style="font-size: 20px">暂无关注信息</span>
            </div>

          </el-dialog>
        </div>
      </div>

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

        <!-- 此处为页面头部蓝色卡片组件 -->
        <el-card class="top-card" shadow="hover">

            <!-- 蓝色卡片内部信息存储组件 -->
            <div class="blue-card-container">
                <!-- 头像信息 -->
                <el-avatar :size="86" :src="avatarPicture" @click="jump"/>
                <!-- 昵称信息 -->
                <div class="nickname">{{ this.username }}</div>

                <!-- 学届，专业，邮箱信息（在同一行） -->
                <div class="information-line2-container">
                    <!-- 学届信息 -->
                    <div class="sub-information-line2-container">
                        <el-icon><Calendar :size="17" color="#101010"/></el-icon>
                        <div class="sub-information-line2-font">{{ this.signTime }}</div>
                    </div>

                    <!-- 专业信息 -->
                    <div class="sub-information-line2-container">
                        <el-icon><School :size="17" color="#101010"/></el-icon>
                        <div class="sub-information-line2-font">{{ this.majority }}</div>
                    </div>

                    <!-- 邮箱信息 -->
                    <div class="sub-information-line2-container">
                        <el-icon><Message :size="17" color="#101010"/></el-icon>
                        <div class="sub-information-line2-font-end">{{ this.email }}</div>
                    </div>
                </div>

                <!-- 个人签名信息 -->
                <div class="personal-sign-container">
                    <el-icon><User :size="17" color="#101010"/></el-icon>
                    <div class="personal-sign-font">{{ this.signature }}</div>
                </div>
            </div>

        </el-card>

        <div class="favorate-and-study-group-container">
            <!-- 收藏文章展示模块 -->
            <div class="favorates-container">
                <!-- 收藏板块头部信息 -->
                <div class="favorates-header-container">
                    <div style="display: flex;">
                        <div class="favorates-header-1">收藏帖子</div>
                        <el-popover
                            placement="top"
                            trigger="click"
                            width="300"
                        >
                            <div style="color: #101010; font-size: 16px; margin-bottom: 8px; font-weight: bold;">我的收藏</div>
                            <!-- <div style="display: flex; margin-bottom: 6px;">
                                <div @click="changeView" style="margin-right: 8px; text-decoration: underline;">所有人可见</div>
                                <div v-if="true">（已选择）</div>
                            </div>
                            <div style="display: flex; margin-bottom: 8px;">
                                <div @click="changeView" style="margin-right: 8px; text-decoration: underline;">仅自己可见</div>
                                <div v-if="false">（已选择）</div>
                            </div> -->
                            <el-radio-group v-model="view_radio1" style="display: flex; margin-bottom: 8px;">
                                <el-radio :value=0 border>所有人可见</el-radio>
                                <el-radio :value=1 border>仅自己可见</el-radio>
                            </el-radio-group>
                            <div style="color: #101010; font-size: 16px; margin-bottom: 8px; font-weight: bold;">我的发布</div>
                            <!-- <div style="display: flex; margin-bottom: 6px;">
                                <div @click="changeView" style="margin-right: 8px; text-decoration: underline;">所有人可见</div>
                                <div v-if="true">（已选择）</div>
                            </div>
                            <div style="display: flex; margin-bottom: 6px;">
                                <div @click="changeView" style="margin-right: 8px; text-decoration: underline;">仅自己可见</div>
                                <div v-if="false">（已选择）</div>
                            </div> -->
                            <el-radio-group v-model="view_radio2" style="display: flex; margin-bottom: 8px;">
                                <el-radio :value=0 border>所有人可见</el-radio>
                                <el-radio :value=1 border>仅自己可见</el-radio>
                            </el-radio-group>
                            <el-button style="margin-top: 8px;" type="primary" @click="changeView">确认选择</el-button>
                            <template #reference>
                                <div style="margin-left: 8px; margin-top: 32px; color: #86909c; font-size: 14px;">设置可见性</div>
                            </template>
                        </el-popover>
                    </div>
                    <el-button @click="toggle_favorate()" class="favorates-header-2">查看更多</el-button>

                </div>


                <!-- 收藏板块正文信息 -->
                <div class="favorates-cards-container">

                    <div v-if="favorateListLength !== 0">
                        <!-- 按顺序输出前三个用户收藏文章卡片 -->
                        <el-row :gutter="26">
                            <el-col :span="8" v-for="item in group1">
                                <FavorateCard
                                    :postId="item.post_id"
                                    :title="item.post_title"
                                    :content="item.post_intro"
                                    :writerId="item.author_id"
                                    :writerName="item.author_name"
                                />
                            </el-col>
                        </el-row>

                        <!-- 按顺序输出四五六个用户收藏文章卡片 -->
                        <el-row v-if="this.group2 != null" :gutter="26" class="second-card-row">
                            <el-col :span="8" v-for="item in group2">
                                <FavorateCard
                                    :postId="item.post_id"
                                    :title="item.post_title"
                                    :content="item.post_intro"
                                    :writerId="item.author_id"
                                    :writerName="item.author_name"
                                />
                            </el-col>
                        </el-row>
                    </div>

                    <div v-else>
                        <div class="no-favorate-card-tip">目前您还没有收藏的文章~</div>
                    </div>

                </div>
            </div>

            <!-- 学习小组信息模块 -->
            <div class="study-group-container">
                <!-- 学习小组板块头部信息 -->
                <div class="study-group-header-container">
                    <div class="favorates-header-1">学习团体/成员</div>
                    <button class="favorates-header-2" style="border: none; background-color: white;" @click="toGroupCenter">查看更多</button>
                </div>

                <!-- 学习小组团体单元 -->
                <div v-for="item in groupListSort">
                    <StudyGroupCard
                        :title="item.name"
                        :amount="item.member_count"
                        :avatar="item.image"
                        :id="item.group_id"
                    />
                </div>

                <div v-if="!groupListSort" class="no-favorate-card-tip" style="margin-left: 55px;">目前您还没有加入的小组~</div>
            </div>

        </div>

        <!-- 第二行的三个板块的信息 -->
        <div class="other-information-group-container">

            <!-- 关注用户信息 -->
            <div class="following-user-container">
                <!-- 关注用户头部信息 -->
                <div class="following-user-header-container">
                    <div class="favorates-header-1">关注用户</div>
                    <el-button class="favorates-header-2" @click="toggle_following_user()">查看更多</el-button>
                </div>

                <div v-for="item in followingListSort">
                    <FollowingCard
                        :username="item.name"
                        :signature="item.sign"
                        :avatar="item.user_avatar"
                        :id="item.user_id"
                    />
                </div>

                <div v-if="followingList.length === 0" class="no-favorate-card-tip">您目前还没有关注的用户~</div>
            </div>

            <!-- 我的打卡信息 -->
            <div class="my-poster-container">
                <!-- 我的打卡头部信息 -->
                <div class="my-poster-header-container">
                    <div class="favorates-header-1">我的打卡</div>
                    <button class="favorates-header-2" style="border: none; background-color: white;" @click="toSocialPost">查看更多</button>
                </div>

                <div v-if="this.divided_posts.length != 0" class="no-favorate-card-tip" style="margin-bottom: 12px; margin-top: -5px;">您已经连续打卡{{ continue_post }}天！</div>
                <PosterCard v-if="this.divided_posts.length != 0" :item="this.divided_posts[0].posts[0]"/>
                <div v-else class="no-favorate-card-tip">您目前还没有打卡信息~</div>
            </div>

            <!-- 站内通知信息 -->
            <div class="notices-container">
                <!-- 站内通知头部信息 -->
                <div class="notices-header-container">
                    <div class="favorates-header-1">系统通知</div>
                    <!-- <div class="favorates-header-2">查看更多</div> -->
                    <!-- <NoticeCenter :type="2" /> -->
                </div>

                <div v-for="item in noticeListSort">
                    <NoticeCard :notice="item.notice_content"/>
                </div>

                <div v-if="this.noticeList.length === 0" class="no-favorate-card-tip">目前您还没有收到的系统通知~</div>
            </div>

        </div>

    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { useStore } from 'vuex'

// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"
// 引入图标
import {Calendar, School, Message, User, Search} from '@element-plus/icons-vue'
//引入收藏帖子单元
import FavorateCard from "../../Components/Group/FavorateCardInPersonalInformation.vue"
//引入学习团体单元
import StudyGroupCard from "../../Components/Group/StudyGroupCardInPersonalInformation.vue"
//引入关注用户单元
import FollowingCard from "../../Components/Group/FollowingCardInPersonalInformation.vue"
//引入个人打卡单元
import PosterCard from "../../Components/Group/PosterCardInPersonalInformation.vue"
//引入站内消息单元
import NoticeCard from "../../Components/Group/NoticeCardInPersonalInformation.vue"
import NoticeCenter from '../../Components/Notice/NoticeCenter.vue';
import Favorate from "@/Pages/PersonalCenter/MoreDetails/Favorate.vue"
import Following_user from "@/Pages/PersonalCenter/MoreDetails/Following_user.vue";
import FavorateShow from "@/Components/Group/FavorateShow.vue";
export default {
    components: {
      Search, FavorateShow,
        BreadcrumbLabel,
        Calendar,
        School,
        Message,
        User,
        FavorateCard,
        StudyGroupCard,
        FollowingCard,
        PosterCard,
        NoticeCard,
        Favorate,
        Following_user,
        NoticeCenter,
    },
    data() {
        return {
            route: [{name: "个人中心", route: ""},
            {name: "用户信息", route: ""}],  //本界面要显示的面包屑信息
            avatarPicture: "",  //头像信息
            username: "", //昵称信息
            signTime: "",  //测试的学届信息
            majority: "",  //专业信息
            email: "",  //邮箱信息
            signature: "", //个人签名信息

            //收藏界面可视化
            show_favorate:false,
            show_userfollowing:false,

            //以下是用来测试收藏帖子板块的数组
            favorateList: [],

            //以下是用来测试学习小组板块的数组
            groupList: [],

            //以下是用来测试关注用户板块的数组
            followingList: [],

            //以下是用来测试个人打卡板块的元素
            poster: {
                month: 2,
                pictures: ["./src/Images/testAvatar.jpg", "./src/Images/testAvatar.jpg", "./src/Images/testAvatar.jpg",
                           "./src/Images/testAvatar.jpg", "./src/Images/testAvatar.jpg", "./src/Images/testAvatar.jpg",
                           "./src/Images/testAvatar.jpg"],
            },

            //以下是用来测试站内通知板块的数组
            noticeList: [],
            get_posts: [],
            divided_posts: [],

            continue_post: 0,

            view_radio1: 0,
            view_radio2: 0,
        }
    },
    methods: {
        changeView() {
            const content = {};
            content.show_post = (this.view_radio2 == 0) ? true : false;
            content.show_favorite = (this.view_radio1 == 0) ? true : false;
            console.log(content);

            axios({
                method: "POST",
                url: "/api/user/update",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '帖子可见性更改成功！',
                        type: 'success',
                    });
                    location.reload();
                } else {
                    this.$message({
                        showClose: true,
                        message: "帖子可见性更改失败！",
                        type: 'error',
                    });
                }
            })
        },
        toggle_favorate() {
            this.show_favorate = true;
        },
        toggle_following_user() {
            this.show_userfollowing = true;
        },
        toGroupCenter() {
            this.$router.push("/MainPage/StudyGroupCenter");
        },
        toSocialPost() {
            this.$router.push("/MainPage/Social_Center");
        },
        dividePosts(posts) {
            this.divided_posts = [];
            let now_year = 0;
            let now_month = 0;
            let month_posts = [];
            let date_posts = [];

            for (let i = 0; i < posts.length; i++) {
                if (posts[i].year != now_year) {
                    if (month_posts.length != 0) {
                        let element1 = {
                            year: posts[i - 1].year,
                            posts: month_posts,
                        }
                        this.divided_posts.push(element1);
                    }  //切分年份
                    now_year = posts[i].year;
                    month_posts = [];
                }

                if (posts[i].month != now_month) {  //这个判断可能有bug
                    if (date_posts.length != 0) {
                        // console.log("month change");
                        // console.log(posts[i].month);
                        // console.log(now_month);
                        let element2 = {
                            month: posts[i - 1].month,
                            posts: date_posts,
                        }
                        month_posts.push(element2);
                    }  //切分月份
                    now_month = posts[i].month;
                    date_posts = [];
                }

                for (let j = 0; j < posts[i].image_urls.length; j++) {
                    let element3 = {
                        url: posts[i].image_urls[j],
                        id: posts[i].post_id,
                    }
                    date_posts.push(element3);
                } //将所有图片,id放入对应日期
                console.log(date_posts);

                if (i === posts.length - 1) {  //切最后一段
                    let element2 = {
                        month: posts[i].month,
                        posts: date_posts,
                    }
                    month_posts.push(element2);
                    let element1 = {
                        year: posts[i].year,
                        posts: month_posts,
                    }
                    this.divided_posts.push(element1);
                }
            }

            console.log(this.divided_posts); // 测评
        },
        finalDivide() {
            // 将每个月的信息以4分组
            for (let i = 0; i < this.divided_posts.length; i++) {
                for (let j = 0; j < this.divided_posts[i].posts.length; j++) {
                    let new_post = [];
                    for (let k = 0; k < this.divided_posts[i].posts[j].posts.length; k += 4) {
                        new_post.push(this.divided_posts[i].posts[j].posts.slice(k, k + 4));
                    }
                    this.divided_posts[i].posts[j].posts = new_post;
                }
            }

            console.log(this.divided_posts); // 测评
            console.log(this.divided_posts[0].posts[0].posts.length);
        },
        choose_visible() {

        },
        jump() {
            this.$router.push("/MainPage/Course_Center/ShowPersonalInformation/" + JSON.parse(sessionStorage.getItem('id')))
        }
    },
    computed: {
        group1() {
            //分离收藏帖子列表的前三个帖子
            return (this.favorateList.length === 0) ? null : this.favorateList.slice(0, 3);
        },
        group2() {
            //分离收藏帖子列表的四五六个帖子
            return (this.favorateList.length <= 3) ? null : this.favorateList.slice(3, 6);
        },
        groupListSort() {
            //分离学习群体列表的前四个内容
            return (this.groupList.length === 0) ? null : this.groupList.slice(0, 4);
        },
        followingListSort() {
            //分离关注用户的前四个内容
            return (this.followingList.length === 0) ? null : this.followingList.slice(0, 4);
        },
        noticeListSort() {
            //分离站内通知的前四个内容
            return (this.noticeList.length === 0) ? null : this.noticeList.slice(0, 4);
        },
        favorateListLength() {
            return this.favorateList.length;
        }
    },
    mounted() {
        // 获取基本信息
        axios({
            method: "GET",
            url: "/api/user/info",
        }).then((result) => {
            console.log(result);
            this.username = (result.data.name != "") ? result.data.name : result.data.account;
            this.signTime = (result.data.enrollment_year != "") ? result.data.enrollment_year : "未设定";
            this.email = result.data.email;
            this.majority = (result.data.major != "") ? result.data.major : "未设定";
            this.signature = (result.data.sign != "") ? result.data.sign : "未设定";
        })

        // 获取头像信息
        axios({
            method: "GET",
            url: "/api/user/head",
            params: {
                user_id: JSON.parse(sessionStorage.getItem("id"))
            }
        }).then((result) => {
            console.log("getuserhead")
            console.log(result)
            this.avatarPicture = result.data.info;
        })

        // 获取收藏帖子信息
        axios({
            method: "GET",
            url: "/api/user/favorites"
        }).then((result) => {
            console.log(result);
            this.favorateList = result.data.posts;
        })

        // 获取关注用户信息
        axios({
            method: "GET",
            url: "/api/user/following"
        }).then((result) => {
            console.log(result);
            this.followingList = result.data.user;
        })

        // 获取系统信息
        axios({
            method: "GET",
            url: "/api/message/notice"
        }).then((result) => {
            console.log(result);
            this.noticeList = result.data.notice_list;
        })

        //获取学习团体信息
        axios({
            method: "GET",
            url: "/api/group/joined"
        }).then((result) => {
            console.log(result);
            this.groupList = result.data.group;
            if (!result.data.group) {
                this.groupList = [];
            }
        })

        //获取打卡信息
        axios({
            method: "GET",
            url: "/api/pyq/userInfo"
        }).then((result) => {
            console.log(result);
            this.get_posts = result.data.social_post;
            this.dividePosts(result.data.social_post);
            //this.finalDivide();
        })

        //获取连续打卡信息
        axios({
            method: "GET",
            url: "api/pyq/days"
        }).then((result) => {
            this.continue_post = result.data.days;
        })
    }
}
</script>

<style scoped>

.favorate_dialog {
  width: 60%;
  display: flex;
  justify-content: center; /* 水平居中 */
}


.favorate{
  width: 100%;
  margin-top: -3%;
  display: inline-block;
  justify-content: center; /* 水平居中 */
  text-align: center;
  left:auto;

}

.favorate_container{
  width: 100%;
  height: 100%;
  margin-left: 100px;
  justify-content: center;
  text-align: center;
  left:auto;
}

.fa_dialog .el-dialog___body {
  padding: 0; /* 取消 el-dialog 的内边距 */
}
.fa_dialog .el-dialog__body {
  background: #1f63ff;
  font-size: 22px;
  position: absolute;
  left: 20px;
  top: 54px;
  bottom: 72px;
  right: 0;
  padding: 0;
  z-index: 1;
  overflow: hidden;
  overflow-y: auto;
}

.user-dialog{
  width: 100%;
  margin-top: -5%;
}
/* 设定界面宽度真实值 */
.page-container {
    width: calc(100vw - 220px);
    min-width: 1174px;
}

/* 控制面包屑组件的位置 */
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
}

/* 控制蓝色卡片的样式 */
.top-card {
    margin-top: 20px;
    width: 96%;
    height: 216px;
    background-color: #e8f4ff;
    border: 1px solid white;
    border-radius: 4px;
    display: flex;
    justify-content: center;
}

/* 控制蓝色卡片内部信息的位置 */
.blue-card-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    margin-top: 14px;
}

/* 控制昵称的样式和位置 */
.nickname {
    font-size: 16px;
    color: #101010;
    font-weight: bold;
    margin-top: 7px;
}

/* 以下为控制第二行三条信息展示的所有样式，包括字体，间距等 */
.information-line2-container {
    display: flex;
    height: 17px;
    margin-top: 12px;
}

.sub-information-line2-container {
    display: flex;
    height: 17px;
}

/* 设置文字的margin-top是为了大致和图标对齐 */
.sub-information-line2-font {
    font-size: 14px;
    margin-top: -2px;
    margin-left: 6px;
    margin-right: 16px;
}

.sub-information-line2-font-end {
    font-size: 14px;
    margin-top: -2px;
    margin-left: 6px;
}

/* 个人签名位置样式 */
.personal-sign-container {
    height: 17px;
    margin-top: 9px;
    display: flex;
}

/* 个人签名字体样式 */
.personal-sign-font {
    height: 17px;
    font-size: 14px;
    margin-left: 6px;
    margin-top: -2px;
    color: #101010;
}

.favorate-and-study-group-container {
    display: flex;
    height: 360px;
}

/* 收藏帖子模块位置 */
.favorates-container {
    width: 69%;
}

/* 收藏帖子模块标题位置 */
.favorates-header-container {
    height: 77px;
    margin-left: 40px;
    justify-content: space-between;
    display: flex;
    border: none;
}

/* 收藏帖子模块标题样式 */
.favorates-header-1 {
    font-size: 16px;
    color: #101010;
    font-weight: bold;
    height: 21px;
    margin-top: 30px;
}

.favorates-header-2 {
    font-size: 14px;
    color: #165dff;
    height: 20px;
    margin-top: 41px;
    border: none;
}

/* 收藏帖子正文部分样式 */
.favorates-cards-container {
    margin-left: 40px;
    width: 95%;
}

/* 两行收藏文章卡片中间的间距设置 */
.second-card-row {
    margin-top: 29px;
}

/* 学习小组信息展示位置 */
.study-group-container {
    height: 200px;
    width: 27%;
}

.study-group-header-container {
    height: 77px;
    margin-left: 55px;
    justify-content: space-between;
    display: flex;
}

/* 第二行的关注用户，打卡和消息通知 */
.other-information-group-container {
    display: flex;
}

/* 关注用户模块位置 */
.following-user-container {
    width: 38%;
    margin-left: 38px;
}

.following-user-header-container {
    height: 80px;
    justify-content: space-between;
    display: flex;
    margin-right: 29px;
}

/* 我的打卡模块位置 */
.my-poster-container {
    width: 26%;
    margin-left: 23px;
}

.my-poster-header-container {
    height: 90px;
    justify-content: space-between;
    display: flex;
}

/* 站内通知模块位置 */
.notices-container {
    width: 23%;
    margin-left: 53px;
}

.notices-header-container {
    height: 87px;
    justify-content: space-between;
    display: flex;
}

.no-favorate-card-tip {
    font-size: 15px;
    color: #86909c;
}
</style>
