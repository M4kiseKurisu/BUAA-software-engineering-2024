<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>
    <div class="main-postpage-container">
        <div style="display: flex;width: 100%;height: 150px;background-color: white;border-bottom: 1px solid darkgray;">
            <div style="width: 80%;height: 100%;">
                <div style="width: 100%;height: 55%;display: flex;align-items: center;margin-left: 7%;">
                    <span style="font-size: xx-large;font-weight: bold;">升学模块讨论区</span>
                    <span style="padding-left: 3%;font-size: large;color: darkgrey;">帖子数: {{ postNum }}</span>
                </div>
                <div style="width: 100%;height: 45%;display: flex;align-items: center;margin-left: 7%">
                    <span><el-button type="primary" @click="toPost">去发帖</el-button></span>
                    <span style="padding-left: 20px;">
                        <el-button-group class="ml-4">
                            <el-button type="primary" plain v-if="kindSelect != 1" @click="selectOne">讨论帖</el-button>
                            <el-button type="primary" v-if="kindSelect == 1" @click="selectOne">讨论帖</el-button>
                            <el-button type="primary" plain v-if="kindSelect != 2" @click="selectTwo">资源帖</el-button>
                            <el-button type="primary" v-if="kindSelect == 2" @click="selectTwo">资源帖</el-button>
                            <el-button type="primary" plain v-if="kindSelect != 3" @click="selectThree">查看全部</el-button>
                            <el-button type="primary" v-if="kindSelect == 3" @click="selectThree">查看全部</el-button>
                        </el-button-group>
                    </span>
                    <span style="padding-left:20px;">
                        <!-- <el-button-group class="ml-4">
                            <el-button type="primary" plain v-if="kindSelect2 != 1" @click="selectKindOne">保研</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 1" @click="selectKindOne">保研</el-button>
                            <el-button type="primary" plain v-if="kindSelect2 != 2" @click="selectKindTwo">考研</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 2" @click="selectKindTwo">考研</el-button>
                            <el-button type="primary" plain v-if="kindSelect2 != 3" @click="selectKindThree">出国</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 3" @click="selectKindThree">出国</el-button>
                            <el-button type="primary" plain v-if="kindSelect2 != 4" @click="selectKindFour">其他</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 4" @click="selectKindFour">其他</el-button>
                        </el-button-group> -->
                        <el-select v-model="target" style="width: 100px" placeholder="帖子类型">
                            <el-option v-for="item in this.targetKind" :value="item.value" :label="item.label" />
                        </el-select>
                    </span>
                    <span style="padding-left: 10px;">
                        <el-select v-model="sortKindStr" style="width: 100px" placeholder="排序方式">
                            <el-option v-for="item in this.sortKind" :value="item.value" :label="item.label" />
                        </el-select>
                    </span>
                </div>
            </div>
            <div style="width: 40%; height: 100%;">
                <div style="width: 100%;height: 55%;display: flex;align-items: center;">
                    <div style="width: 100%;height: fit-content;display: flex;justify-content: end;">
                        <!-- <span style="padding-right: 4%;"><el-button  type="primary"
                                style="font-size: large;" plain @click = "postRecommend">智能推荐</el-button></span> -->
                        <span style="padding-right: 7%;"><el-button text type="primary"
                                style="font-size: large;" @click = "goToSchoolInfomation">查看院校信息</el-button></span>
                    </div>
                </div>
                <div style="width: 100%;height: 45%;display: flex;align-items: center;">
                    <div style="width: 100%;height: fit-content;display: flex;justify-content: end;">
                        <!-- <span style="padding-right: 3% ;"><el-input v-model="tagKind" style="width: 120px"
                                placeholder="输入标签" /></span> -->
                        <span style="padding-right: 3% ;"><el-input v-model="searchWord" style="width: 120px"
                                placeholder="输入关键词" /></span>
                        <span style="padding-right: 3%;"><el-button type="primary" plain
                                @click="this.getSortPostList();">板块内搜索</el-button></span>
                        <span style="padding-right: 7%;"><el-button type="primary"
                                @click="postRecommend">智能推荐</el-button></span>
                    </div>
                </div>
            </div>
        </div>
        <div style="width: 100%;height: 785px;background-color: white;display: flex;">
            <div style="width: 80%;height: 100%;border-right: 1px solid darkgray;">
                <div style="width: 100%;height: 735px;">
                    <PostItem v-for="item in selectPostList" :postInfo="item" :key="item.post_id"></PostItem>
                </div>
                <div style="width: 100%; position: relative; height: 40px;display: flex;align-items: center;">
                    <el-pagination background layout="prev, pager, next" :page-size="5" :total="total"
                        style="position: absolute; right: 0;margin-right: 10px;" @current-change="handleCurrentChange" />
                </div>
            </div>
            <div style="width: 20%; min-width: 200px;">
                <!-- <div style="margin-left: 5%;margin-top: 20px;font-size: 1.5em;font-weight: bold;color: darkgrey;">
                    板块更新时间:&ensp;{{ updateTime }}
                </div> -->
                <div style="margin-left: 5%;margin-top: 20px;" v-if="this.popAuthorIdList.length != 0">
                    <div style="font-size: larger;font-weight: bold;">
                        热门作者
                    </div>
                    <div v-if="this.popAuthorIdList.length === 0" style="font-size: larger;">无</div>
                    <div v-else style="width:100%; display: grid; grid-template-columns: repeat(3, 1fr);">
                        <ManagerItem v-for="item in this.popAuthorIdList" :personId="item"></ManagerItem>
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
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue";
import PostItem from "../../Pages/PostCenter/PostItem.vue";
import ManagerItem from "../../Pages/PostCenter/ManagerItem.vue";
import CreatorOfPostCenter from "@/Pages/PostCenter/CreatorOfPostCenter.vue";
import axios from 'axios';
import { result } from "lodash";
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    components: {
        BreadcrumbLabel,
        PostItem,
        ManagerItem,
        CreatorOfPostCenter
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
        sortIndex() {
            var sort = 0;
            if (this.sortKindStr == '' || this.sortKind == '热度') {
                sort = 0;
            } else if (this.sortKindStr == '最新') {
                sort = 1;
            }
            return sort;
        }
    },
    data() {
        return {
            show_Creator: false,
            route: [{name: "升学板块", route: ""}],  //本界面要显示的面包屑信息
            courseName: "软件工程",
            courseId: 1,
            postNum: '',
            kindSelect: 3,
            kindSelect2: 1,
            searchWord: "",
            total: 20,
            currentPage: 1,
            updateTime: "2077.7.7.77",
            sectionId: 0,
            postList: [],
            sortKind: [{
                value: '热度',
                label: '热度',
            },
            {
                value: '最新',
                label: '最新',
            },],
            targetKind: [{
                value: '保研',
                label: '保研',
            },
            {
                value: '考研',
                label: '考研',
            },
            {
                value: '出国',
                label: '出国',
            },
            {
                value: '其他',
                label: '其他',
            },
            {
                value: '全部',
                label: '全部',
            },],
            sortKindStr: '',
            tagKind: '',
            isFollow: false,
            popAuthorIdList: [],
            target: '',
            recommend: false,
        }
    },
    watch: {
        sortKindStr(newValue, oldValue) {
            //console.log(newValue);
            this.getSortPostList();
        },
        target(newValue, oldValue){
            this.getSortPostList();
        }
    },
    methods: {
        selectOne() {
            this.kindSelect = 1;
            this.getSortPostList();
        },
        selectTwo() {
            this.kindSelect = 2;
            this.getSortPostList();
        },
        selectThree() {
            this.kindSelect = 3;
            this.getSortPostList();
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        selectKindOne() {
            this.kindSelect2 = 1;
            this.target = '保研';
            this.getSortPostList();
        },
        selectKindTwo() {
            this.kindSelect2 = 2;
            this.target = '考研';
            this.getSortPostList();
        },
        selectKindThree() {
            this.kindSelect2 = 3;
            this.target = '出国';
            this.getSortPostList();
        },
        selectKindFour() {
            this.kindSelect2 = 4;
            this.target = '其他';
            this.getSortPostList();
        },
        getPostList() {
            axios({
                method: "GET",
                url: "api/progression/totalPosts",
                //params: { section_id: this.sectionId,sort: sort,post_type:post_type,tag_name:tag_name,keyword:keyword},
            }).then((result) => {
                //console.log(result);
                this.postList = result.data.posts;
                this.total = result.data.posts.length;
                this.postNum = this.total;
                //console.log(this.postList[0].post_id);
            })
        },
        getPopAuthor() {
            axios({
                method: "GET",
                url: 'api/progression/discussion',
                params: {
                    section_id: this.sectionId,
                }
            }).then((result) => {
                console.log(result);
                this.popAuthorIdList = result.data.author_id;
                this.postNum = result.data.totalPosts;
            })
        },
        getSortPostList() {
            var target;
            if(this.target == ''){
                target = '全部';
            } else {
                target = this.target;
            }
            var packet = {
                target: target,
                type: this.kindSelect,
                keyword: this.searchWord,
                sort: this.sortIndex,
                recommend: this.recommend,
            }
            console.log(packet);
            axios({
                method: "GET",
                url: 'api/progression/filter',
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/progression/filter',
                params: packet,
            }).then((result) => {
                console.log(result);
                this.postList = result.data.posts;
                this.total = this.postList.length;
            })
        },
        postRecommend() {
            this.recommend = true;
            this.getSortPostList();
            this.recommend = false;
        },
        toPost() {
            this.$router.push({ path: "/MainPage/Course_Center/CreatePost/" + 0 });
        },
        goToSchoolInfomation(){
            this.$router.push({ path: "/MainPage/SchoolInformation/Main"});
        }
    },
    created() {
        this.getPostList();
        //this.getSortPostList();
        this.getPopAuthor();
    }
}
</script>

<style scoped>
.main-postpage-container {
    width: calc(100vw - 220px);
    min-width: 1080px;
    background-color: rgba(247, 248, 250, 0.7);
}

.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}
</style>