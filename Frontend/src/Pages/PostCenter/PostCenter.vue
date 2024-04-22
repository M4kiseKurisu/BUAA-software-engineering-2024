<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>

    <div class="main-postpage-container">
      <!-- 管理界面弹出窗 -->
      <div class="creator_dialog">
        <el-dialog class="c_dialog" title="管理信息" v-model="show_Creator" :visible.sync="show_Creator" width="40%">
          <div class="creator_container">
            <CreatorOfPostCenter></CreatorOfPostCenter>
          </div>
        </el-dialog>
      </div>
        <!-- 帖子正文部分 -->
        <div style="display: flex;width: 100%;height: 150px;background-color: white;border-bottom: 1px solid darkgray;">
            <div style="width: 60%;height: 100%;">
                <div style="width: 100%;height: 55%;display: flex;align-items: center;margin-left: 7%;">
                    <span style="font-size: xx-large;font-weight: bold;">{{ courseName }}讨论区</span>
                    <span style="padding-left: 3%;font-size: large;color: darkgrey;">帖子数: {{ postNum }}</span>
                    <span style="padding-left: 3%;font-size: large;color: darkgrey;">关注数: {{ subscripNum }}</span>
                    <span style="padding-left: 3%;"><el-button type="primary" plain>关注板块</el-button></span>
                </div>
                <div style="width: 100%;height: 45%;display: flex;align-items: center;margin-left: 7%">
                    <span><el-button type="primary" @click="toPost">去发帖</el-button></span>
                    <span style="padding-left: 3%;">
                        <el-button-group class="ml-4">
                            <el-button type="primary" plain v-if="kindSelect != 1" @click="selectOne">讨论帖</el-button>
                            <el-button type="primary" v-if="kindSelect == 1" @click="selectOne">讨论帖</el-button>
                            <el-button type="primary" plain v-if="kindSelect != 2" @click="selectTwo">资源帖</el-button>
                            <el-button type="primary" v-if="kindSelect == 2" @click="selectTwo">资源帖</el-button>
                            <el-button type="primary" plain v-if="kindSelect != 3" @click="selectThree">查看全部</el-button>
                            <el-button type="primary" v-if="kindSelect == 3" @click="selectThree">查看全部</el-button>
                        </el-button-group>
                    </span>
                    <span style="padding-left: 3%;">
                        <el-select v-model="sortKind" style="width: 150px">
                            <el-option sortKind="热门">热门</el-option>
                            <el-option sortKind="最新">最新</el-option>
                        </el-select>
                    </span>
                </div>
            </div>
            <div style="width: 40%; height: 100%;">
                <div style="width: 100%;height: 55%;display: flex;align-items: center;">
                    <div style="width: 100%;height: fit-content;display: flex;justify-content: end;">
                        <span style="padding-right: 7%;"><el-button text type="primary"
                                style="font-size: large;">查看相关课程界面</el-button></span>
                    </div>
                </div>
                <div style="width: 100%;height: 45%;display: flex;align-items: center;">
                    <div style="width: 100%;height: fit-content;display: flex;justify-content: end;">
                        <span>
                            <el-select v-model="value" placeholder="选择标签" style="width: 120px">
                                <el-option v-for="item in options" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select>
                        </span>
                        <span style="padding-right: 3% ;"><el-input v-model="searchWord" style="width: 240px"
                                placeholder="输入关键词" /></span>
                        <span style="padding-right: 7%;"><el-button type="primary" plain>模块内搜索</el-button></span>
                    </div>
                </div>
            </div>
        </div>
        <div style="width: 100%;height: fit-content;background-color: white;display: flex;">
            <div style="width: 75%;height: fit-content;border-right: 1px solid darkgray;">
                <PostItem></PostItem>
                <PostItem></PostItem>
                <PostItem></PostItem>
                <PostItem></PostItem>
                <PostItem></PostItem>
                <div style="width: 100%; position: relative; height: 40px;display: flex;align-items: center;">
                    <el-pagination background layout="prev, pager, next" :page-count="total"
                        style="position: absolute; right: 0;margin-right: 10px;" @current-change="handleCurrentChange" />
                </div>
            </div>
            <div style="width: 25%; ">
                <div style="margin-left: 5%;margin-top: 20px;font-size: 1.5em;font-weight: bold;color: darkgrey;">
                    板块更新时间:&ensp;{{ updateTime }}
                </div>
                <div style="display: flex;margin-left: 5%;margin-top: 20px;">
                    <div style="width: 25%;font-size: larger;">
                        创建者:
                    </div>
                    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem></ManagerItem>
                    </div>
                </div>
                <div style="display: flex;margin-left: 5%;margin-top: 20px;">
                    <div style="width: 25%;font-size: larger;">
                        相关教师:
                    </div>
                    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem></ManagerItem>
                    </div>
                </div>
                <div style="display: flex;margin-left: 5%;margin-top: 20px;">
                    <div style="width: 25%;font-size: larger;">
                        相关助教:
                    </div>
                    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem></ManagerItem>
                        <ManagerItem></ManagerItem>
                    </div>
                </div>
                <div style="display: flex;margin-left: 5%;margin-top: 20px;">
                    <div style="width: 25%;font-size: larger;">
                        热门作者:
                    </div>
                    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem></ManagerItem>
                        <ManagerItem></ManagerItem>
                        <ManagerItem></ManagerItem>
                        <ManagerItem></ManagerItem>
                        <ManagerItem></ManagerItem>
                    </div>
                </div>
                <div style="width: 100%;height: fit-content;display: flex;justify-content: end;">

                    <span style="padding-right: 3%;margin-top: 20px;"><el-button text type="primary" @click="toggle_Creator()"
                            style="font-size: large;">查看详情</el-button></span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"
import PostItem from "./PostItem.vue";
import ManagerItem from "./ManagerItem.vue";
import CreatorOfPostCenter from "@/Pages/PostCenter/CreatorOfPostCenter.vue";
export default {
    components: {

        BreadcrumbLabel,
        PostItem,
        ManagerItem,
        CreatorOfPostCenter
    },
    data() {
        return {
            show_Creator:false,
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
            courseName: "软件工程",
            courseId: 1,
            postNum: 20,
            subscripNum: 30,
            kindSelect: 1,
            searchWord: "",
            total: 20,
            currentPage: 1,
            updateTime: "2077.7.7.77",
        }
    },
    methods: {
        toggle_Creator() {
          this.show_Creator = true;
        },
        selectOne() {
            this.kindSelect = 1;
        },
        selectTwo() {
            this.kindSelect = 2;
        },
        selectThree() {
            this.kindSelect = 3;
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        toPost() {
            this.$router.push({ path: "/MainPage/Course_Center/CreatePost" });
        }
    }
}
</script>

<style scoped>
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}

.main-postpage-container {
    width: calc(100vw - 205px);
    background-color: rgba(247, 248, 250, 0.7);
}
</style>
