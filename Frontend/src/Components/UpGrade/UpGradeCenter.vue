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
                    <span style="padding-left:3%;">
                        <el-button-group class="ml-4">
                            <el-button type="primary" plain v-if="kindSelect2 != 1" @click="selectKindOne">保研</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 1" @click="selectKindOne">保研</el-button>
                            <el-button type="primary" plain v-if="kindSelect2 != 2" @click="selectKindTwo">考研</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 2" @click="selectKindTwo">考研</el-button>
                            <el-button type="primary" plain v-if="kindSelect2 != 3" @click="selectKindThree">出国</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 3" @click="selectKindThree">出国</el-button>
                            <el-button type="primary" plain v-if="kindSelect2 != 4" @click="selectKindFour">其他</el-button>
                            <el-button type="primary" v-if="kindSelect2 == 4" @click="selectKindFour">其他</el-button>
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
                                style="font-size: large;">查看院校信息</el-button></span>
                    </div>
                </div>
                <div style="width: 100%;height: 45%;display: flex;align-items: center;">
                    <div style="width: 100%;height: fit-content;display: flex;justify-content: end;">
                        <span style="padding-right: 3% ;"><el-input v-model="tagKind" style="width: 150px"
                                placeholder="输入标签" /></span>
                        <span style="padding-right: 3% ;"><el-input v-model="searchWord" style="width: 150px"
                                placeholder="输入关键词" /></span>
                        <span style="padding-right: 7%;"><el-button type="primary" plain>板块内搜索</el-button></span>
                    </div>
                </div>
            </div>
        </div>
        <div style="width: 100%;height: 785px;background-color: white;display: flex;">
            <div style="width: 75%;height: 100%;border-right: 1px solid darkgray;">
                <div style="width: 100%;height: 735px;">
                    <PostItem v-for="item in selectPostList" :postInfo="item" :key="item.post_id"></PostItem>
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
                        热门作者:
                    </div>
                    <div v-if="this.popAuthorIdList == ''" style="font-size: larger;">无</div>
                    <div v-else style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
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
            if (this.sortKindStr == '' || this.sortKind == '最新') {
                sort = 0;
            } else if (this.sortKindStr == '热度') {
                sort = 1;
            }
            return sort;
        }
    },
    data() {
        return {
            show_Creator: false,
            route: ["升学板块"],  //本界面要显示的面包屑信息
            courseName: "软件工程",
            courseId: 1,
            postNum: '',
            kindSelect: 1,
            kindSelect2: 1,
            searchWord: "",
            total: 20,
            currentPage: 1,
            updateTime: "2077.7.7.77",
            sectionId: 0,
            postList: "",
            sortKind: [{
                value: '热度',
                label: '热度',
            },
            {
                value: '最新',
                label: '最新',
            },],
            sortKindStr: '',
            tagKind: '',
            isFollow: false,
            popAuthorIdList: '',

        }
    },
    watch: {
        sortKindStr(newValue, oldValue) {
            console.log(newValue);
            this.getPostList(this.sortIndex, this.kindSelect - 1, this.tagKind);
        }
    },
    methods: {
        selectOne() {
            this.kindSelect = 1;
            this.getPostList(this.sortIndex, this.kindSelect - 1, this.tagKind);
        },
        selectTwo() {
            this.kindSelect = 2;
            this.getPostList(this.sortIndex, this.kindSelect - 1, this.tagKind);
        },
        selectThree() {
            this.kindSelect = 3;
            this.getPostList(this.sortIndex, this.kindSelect - 1, this.tagKind);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        selectKindOne() {
            this.kindSelect2 = 1;
        },
        selectKindTwo() {
            this.kindSelect2 = 2;
        },
        selectKindThree() {
            this.kindSelect2 = 3;
        },
        selectKindFour() {
            this.kindSelect2 = 4;
        },
    },
}
</script>

<style scoped>
.main-postpage-container {
    width: calc(100vw - 205px);
    min-width: 1280px;
    background-color: rgba(247, 248, 250, 0.7);
}

.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}
</style>