<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>
    <div class="study_group_center_container">
        <!-- <div class="study_group_center_header">
            <span style="font-size: 2em;margin-left: 30px;">
                团体广场
            </span>
            <span style="margin-left: 30px;color: darkgray;font-size: large;">
                可加入团体数:{{ groupCount }}
            </span>
        </div> -->
        <div class="study_group_center_content">
            <div class="study_group_center_mygroup">
                <div class="study_group_center_selfinfomation">
                    <div style="height: 100px;width: 100px; display: flex;align-items: center;justify-content: center;">
                        <img :src="selfAvatar" alt="" style="width: 90%;height: 90%;border-radius: 10%;">
                    </div>
                    <div style="height: 100px;width: calc(100% - 100px);">
                        <div style="height: 60%;width: 100%;display: flex;align-items: center;">
                            <span style="font-size: 1.5em;font-weight: bold;">{{ selfName }}</span>
                        </div>
                        <div style="height: 40%;width: 100%;display: flex;">
                            <span style="font-size: 1em;color:dimgray;margin-bottom: 10px;">已加入团体数:{{ selfGroupCount
                            }}</span>
                        </div>
                    </div>
                </div>
                <div class="study_group_center_list">
                    <el-scrollbar v-if="myGroupList != []">
                        <GroupItem v-for="item in this.myGroupList" :groupInfo="item" class="hover-div"></GroupItem>
                    </el-scrollbar>
                </div>
            </div>
            <div class="study_group_center_grouplist">
                <div class="study_group_center_header">
                    <span style="font-size: 2em;margin-left: 30px;">
                        团体广场
                    </span>
                    <span style="margin-left: 30px;color: dimgray;font-size: large;">
                        可加入团体数:{{ groupCount }}
                    </span>
                    <div style="margin-left: auto;">
                        <el-input v-model="inputTag" style="width: 120px;margin-right: 5px;" placeholder="输入标签" />
                        <el-input v-model="inputKeyWord" style="width: 120px;margin-right: 5px;" placeholder="输入关键字" />
                        <el-button style="margin-right: 5px;" type="primary" plain @click='searchGroup'>搜索</el-button>
                        <el-button style="margin-right: 10px;" type="primary" @click="goToCreatGroup">创建团体</el-button>
                    </div>
                </div>

                <div class="study_group_center_groupcontainer" v-if="groupInfoList != []">
                    <div v-for="item in selectGroupList" style="display: grid;place-items: center;">
                        <GroupCard :groupInfo="item" :key='item.group_id'></GroupCard>
                    </div>
                </div>
                <div
                    style="width: 100%;height: 40px;display: flex;align-items: center;background-color: white;justify-content: end;">
                    <el-pagination background layout="prev, pager, next" :page-size="6" :total="totalGroup"
                        @current-change="handleCurrentChange" style="margin-right: 20px;" />
                </div>
                <!-- <div style="width: 100%; position: relative; height: 20px;" v-if="noticeChoice == 1">
                    <el-pagination background layout="prev, pager, next" :page-size="6" :total="total1"
                        style="position: absolute; right: 0;" @current-change="handleCurrentChange1" />
                </div> -->
            </div>
        </div>
    </div>
</template>

<script>
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue";
import GroupItem from "../Chat/GroupItem.vue";
import GroupCard from "./GroupCard.vue";
import axios from "axios";
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { result } from "lodash";
export default {
    components: {
        BreadcrumbLabel,
        GroupItem,
        GroupCard,
    },
    data() {
        return {
            route: ["学业团体", "团体广场"],
            groupCount: 20,
            selfAvatar: '',
            selfName: 'hhhhhhhhhhhh',
            selfGroupCount: 10,
            inputTag: '',
            inputKeyWord: '',
            groupInfoList: [],
            totalGroup: 20,
            currentPage: 1,
            myGroupList: [],
            selfId: JSON.parse(sessionStorage.getItem("id")),
        }
    },
    computed: {
        selectGroupList() {
            var begin, end;
            begin = this.currentPage * 6 - 6;
            end = this.currentPage * 6;
            if (end > this.total) {
                end = this.total;
            }
            return this.groupInfoList.slice(begin, end);
        }
    },
    methods: {
        goToCreatGroup() {
            this.$router.push({ path: "/MainPage/CreateGroup" });
        },
        handleCurrentChange(val) {
            this.currentPage = val;
        },
        getGroupList() {
            axios({
                method: "GET",
                url: 'api/group/list',
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/group/list',
                // data: {

                // }
            }).then((result) => {
                console.log(result);
                this.groupCount = result.data.group_count;
                this.totalGroup = result.data.group_count;
                this.groupInfoList = result.data.group;
                //console.log(this.groupInfoList);
            })
        },
        getMyGroupList() {
            axios({
                method: "GET",
                url: 'api/group/joined',
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/group/list',
            }).then((result) => {
                this.myGroupList = result.data.group;
                this.selfGroupCount = result.data.group_count;
            })
        },
        getSelfInfo() {
            axios({
                method: "GET",
                url: 'api/user/social/simple',
                params: {
                    id: this.selfId,
                }
            }).then((result) => {
                //console.log(this.selfId)
                console.log(result);
                this.selfName = result.data.name;
                this.selfAvatar = result.data.user_avatar;
            })
        },
        searchGroup() {
            axios({
                method: 'GET',
                url: 'api/group/search',
                params: {
                    tag: this.inputTag,
                    keyword: this.inputKeyWord,
                }
            }).then((result) => {
                this.groupInfoList = result.data.group;
            })
        },
    },
    created() {
        this.getGroupList();
        this.getMyGroupList();
        this.getSelfInfo();
    }
}
</script>

<style scoped>
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}

.study_group_center_container {
    width: calc(100vw - 220px);
    /* min-width: 1080px; */
    min-width: 1080px;
    /* background-color: rgba(247, 248, 250, 0.7); */
    background-color: white;
    height: calc(100vh - 120px);
    /* min-height: 820px; */
    min-height: 700px;
}

.study_group_center_header {
    width: 100%;
    height: 100px;
    background-color: aliceblue;
    display: flex;
    align-items: center;
}

.study_group_center_content {
    width: 100%;
    background-color: rgb(181, 181, 166);
    height: 100%;
    display: flex;
}

.study_group_center_mygroup {
    width: 23%;
    height: 100%;
    background-color: white;
    min-width: 300px;
}

.study_group_center_selfinfomation {
    width: 100%;
    height: 100px;
    background-color: rgb(238, 241, 244);
    display: flex;
}

.study_group_center_list {
    width: 100%;
    height: calc(100% - 100px);

}

.study_group_center_grouplist {
    width: 77%;
    height: 100%;
    background-color: white;
    min-width: 800px;
}

.study_group_center_groupcontainer {
    width: 100%;
    height: calc(100% - 140px);
    background-color: white;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(calc(50% - 10px), 1fr));
    gap: 10px;
}

.hover-div:hover {
    background-color: #f4f5f7;
}
</style>