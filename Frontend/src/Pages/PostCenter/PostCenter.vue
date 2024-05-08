<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>

    <div class="main-postpage-container">
        <!-- 管理界面弹出窗 -->
        <div class="creator_dialog">
            <el-dialog class="c_dialog" title="管理信息" v-model="show_Creator" :visible.sync="show_Creator" width="40%" top="2%">
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
                    <span style="padding-left: 3%;" v-if="!this.isFollow"><el-button type="primary" plain
                            @click="followSection">关注板块</el-button></span>
                    <span style="padding-left: 3%;" v-if="this.isFollow"><el-button type="primary" plain
                            @click="unFollowSection">取消关注</el-button></span>
                    <span style="padding-left: 3%;"> <Apply :section_id="this.sectionId"/> </span>
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
                        <el-select v-model="sortKindStr" style="width: 100px" placeholder="排序方式">
                            <el-option v-for="item in this.sortKind" :value="item.value" :label="item.label" />
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
                        <span style="padding-right: 3% ;">
                            <!-- <el-select v-model="value" placeholder="选择标签" style="width: 120px">
                                <el-option v-for="item in options" :key="item.value" :label="item.label"
                                    :value="item.value" />
                            </el-select> -->
                            <el-input v-model="tagKind" style="width: 160px"
                                placeholder="输入标签" />
                        </span>
                        <span style="padding-right: 3% ;"><el-input v-model="searchWord" style="width: 160px"
                                placeholder="输入关键词" /></span>
                        <span style="padding-right: 7%;"><el-button type="primary" plain @click = "searchPost">模块内搜索</el-button></span>
                    </div>
                </div>
            </div>
        </div>
        <div style="width: 100%;height: 785px;background-color: white;display: flex;">
            <div style="width: 75%;height: 100%;border-right: 1px solid darkgray;">
                <div style="width: 100%;height: 735px;">
                    <PostItem v-for="item in selectPostList" :postInfo="item" :key="item.post_id" :getSectionId="this.sectionId"></PostItem>
                </div>
                <div style="width: 100%; position: relative; height: 40px;display: flex;align-items: center;">
                    <el-pagination background layout="prev, pager, next" :page-size="5" :total="total"
                        style="position: absolute; right: 0;margin-right: 10px;" @current-change="handleCurrentChange" />
                </div>
            </div>
            <div style="width: 25%; min-width: 360px;">
                <div style="margin-left: 5%;margin-top: 20px;font-size: 1.5em;font-weight: bold;color: darkgrey;">
                    板块更新时间:&ensp;{{ updateTime }}
                </div>
                <div style="display: flex;margin-left: 5%;margin-top: 20px;">
                    <div style="width: 25%;font-size: larger;">
                        相关教师:
                    </div>
                    <div v-if = "this.techerIdList == ''" style="font-size: larger;">无</div>
                    <div v-else style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem v-for = "item in this.techerIdList" :personId = "item"></ManagerItem>
                    </div>
                </div>
                <div style="display: flex;margin-left: 5%;margin-top: 20px;">
                    <div style="width: 25%;font-size: larger;">
                        相关助教:
                    </div>
                    <div v-if = "this.assitantIdList == ''" style="font-size: larger;">无</div>
                    <div v-else style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem v-for = "item in this.assitantIdList" :personId = "item"></ManagerItem>
                        <ManagerItem></ManagerItem>
                    </div>
                </div>
                <div style="display: flex;margin-left: 5%;margin-top: 20px;">
                    <div style="width: 25%;font-size: larger;">
                        热门作者:
                    </div>
                    <div v-if = "this.popAuthorIdList == ''" style="font-size: larger;">无</div>
                    <div v-else style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem v-for = "item in this.popAuthorIdList" :personId = "item"></ManagerItem>
                    </div>
                </div>
                <div style="width: 100%;height: fit-content;display: flex;justify-content: end;">

                    <span style="padding-right: 3%;margin-top: 20px;"><el-button text type="primary"
                            @click="toggle_Creator()" style="font-size: large;">查看详情</el-button></span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue";
import Apply from "../../Components/Tool/ApplyButton.vue";
import PostItem from "./PostItem.vue";
import ManagerItem from "./ManagerItem.vue";
import CreatorOfPostCenter from "@/Pages/PostCenter/CreatorOfPostCenter.vue";
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    components: {
        BreadcrumbLabel,
        PostItem,
        ManagerItem,
        CreatorOfPostCenter,
        Apply,
    },
    computed: {
        selectPostList() {
            var begin, end;
            begin = this.currentPage * 5 - 5;
            end = this.currentPage * 5;
            if (end > this.total) {
                end = this.total;
            }
            return this.postList.slice(begin, end);
        },
        sortIndex(){
            var sort = 0;
            if(this.sortKindStr == '' ||this.sortKind == '最新' ){
                sort = 0;
            } else if(this.sortKindStr == '点赞数') {
                sort = 1;
            } else if(this.sortKindStr == '收藏数') {
                sort = 2;
            }
            return sort;
        }
    },
    data() {
        return {
            show_Creator: false,
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
            courseName: "软件工程",
            courseId: 1,
            postNum: '',
            subscripNum: 30,
            courseType: '',
            kindSelect: 1,
            searchWord: "",
            total: 20,
            currentPage: 1,
            updateTime: "2077.7.7.77",
            sectionId: 1,
            postList: "",
            sortKind: [{
                value: '点赞数',
                label: '点赞数',
            },
            {
                value: '最新',
                label: '最新',
            },
            {
                value: '收藏数',
                label: '收藏数',
            }],
            sortKindStr: '',
            tagKind: '',
            isFollow: false,
            techerIdList: '',
            assitantIdList: '',
            popAuthorIdList: '',
        }
    },
    watch:{
        sortKindStr(newValue,oldValue){
            console.log(newValue);
            this.getPostList(this.sortIndex,this.kindSelect - 1,this.tagKind);
        }
    },
    methods: {
        toggle_Creator() {
            this.show_Creator = true;
        },
        selectOne() {
            this.kindSelect = 1;
            this.getPostList(this.sortIndex,this.kindSelect - 1,this.tagKind);
        },
        selectTwo() {
            this.kindSelect = 2;
            this.getPostList(this.sortIndex,this.kindSelect - 1,this.tagKind);
        },
        selectThree() {
            this.kindSelect = 3;
            this.getPostList(this.sortIndex,this.kindSelect - 1,this.tagKind);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        toPost() {
            this.$router.push({ path: "/MainPage/Course_Center/CreatePost/" + this.sectionId});
        },
        followSection() {
            axios({
                method: "POST",
                url: "api/section/focus",
                data: { section_id: this.sectionId },
            }).then((result) => {
                console.log(result);
            })
        },
        searchPost(){
            this.getPostList(this.sortIndex,this.kindSelect - 1,this.tagKind);
        },
        getPostList(sort,post_type,tag_name) {
            axios({
                method: "GET",
                url: "api/section/posts",
                params: { section_id: this.sectionId,sort: sort,post_type:post_type,tag_name:tag_name},
            }).then((result) => {
                //console.log(result);
                this.postList = result.data.posts;
                this.total = result.data.posts.length;
                //console.log(this.postList[0].post_id);
            })
        },
        getSectionInfomation() {
            axios({
                method: "GET",
                url: "/api/section/info",
                params: { section_id: this.sectionId },
            }).then((result) => {
                //console.log(result);
                this.courseName = result.data.course_name;
                this.courseType = result.data.course_type;
                this.subscripNum = result.data.course_follows;
                this.postNum = result.data.course_posts;
            })
        },
        followSection() {
            axios({
                method: "POST",
                url: "/api/section/focus",
                data: { section_id: this.sectionId },
            }).then((result) => {
                console.log(result);
                if (result.data.success) {
                    this.isFollow = true;
                }
            })
        },
        unFollowSection() {
            axios({
                method: "POST",
                url: "/api/section/unfocus",
                data: { section_id: this.sectionId },
            }).then((result) => {
                console.log(result);
                if (result.data.success) {
                    this.isFollow = false;
                }
            })
        },
    },
    mounted() {

    },
    created() {
        this.sectionId = this.$route.params.sectionId;
        //console.log(this.$route.params.sectionId);
        //console.log(this.sortIndex);
        this.getPostList(this.sortIndex,this.kindSelect - 1,this.tagKind);
        this.getSectionInfomation();
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
    min-width: 1080px;
    background-color: rgba(247, 248, 250, 0.7);
}
</style>
