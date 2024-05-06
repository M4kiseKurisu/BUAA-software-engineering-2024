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
                        <img src="../../Images/loginImage.png" alt="" style="width: 90%;height: 90%;border-radius: 10%;">
                    </div>
                    <div style="height: 100px;width: calc(100% - 100px);">
                        <div style="height: 60%;width: 100%;display: flex;align-items: center;">
                            <span style="font-size: 1.5em;font-weight: bold;">{{ selfName }}</span>
                        </div>
                        <div style="height: 40%;width: 100%;display: flex;align-items: center;">
                            <span style="font-size: 1em;color:dimgray;">已加入团体数:{{ selfGroupCount }}</span>
                        </div>
                    </div>
                </div>
                <div class="study_group_center_list">
                    <el-scrollbar>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>

                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>

                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>

                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
                        <GroupItem></GroupItem>
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
                        <el-input v-model="inputTag" style="width: 180px;margin-right: 5px;" placeholder="输入标签" />
                        <el-input v-model="inputTitle" style="width: 180px;margin-right: 5px;" placeholder="输入标题" />
                        <el-button style="margin-right: 5px;" type="primary" plain>搜索</el-button>
                        <el-button style="margin-right: 10px;" type="primary" @click = "goToCreatGroup">创建团体</el-button>
                    </div> 
                </div>
                <div class="study_group_center_groupcontainer">
                    <div style="display: grid;place-items: center;">
                        <GroupCard></GroupCard>
                    </div>
                    <div style="display: grid;place-items: center;">
                        <GroupCard></GroupCard>
                    </div>
                    <div style="display: grid;place-items: center;">
                        <GroupCard></GroupCard>
                    </div>
                    <div style="display: grid;place-items: center;">
                        <GroupCard></GroupCard>
                    </div>
                    <div style="display: grid;place-items: center;">
                        <GroupCard></GroupCard>
                    </div>
                    <div style="display: grid;place-items: center;">
                        <GroupCard></GroupCard>
                    </div>
                    
                </div>
                <div style="width: 100%;height: 40px;display: flex;align-items: center;background-color: white;justify-content: end;">
                    <el-pagination background layout="prev, pager, next" :total="1000" style="margin-right: 20px;"/>
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
import { createDOMCompilerError } from "@vue/compiler-dom";
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue";
import GroupItem from "../Chat/GroupItem.vue";
import GroupCard from "./GroupCard.vue";
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
            userAvatar: '',
            selfName: 'hhhhhhhhhhhh',
            selfGroupCount: 10,
            inputTag: '',
            inputTitle: '',
            groupInfoList: [],
        }
    },
    methods:{
        goToCreatGroup(){
            this.$router.push({ path: "/MainPage/CreateGroup"});
        }
    },
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
    min-width: 1300px;
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
    background-color:rgb(238, 241, 244);
    display: flex;
}

.study_group_center_list {
    width: 100%;
    height: calc(100% - 100px);

}

.study_group_center_grouplist {
    width: 77%;
    height: 100%;
    background-color: antiquewhite;
    min-width: 900px;
}

.study_group_center_groupcontainer {
    width: 100%;
    height: calc(100% - 140px);
    background-color: white;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(calc(50% - 10px), 1fr));
    gap: 20px;
}
</style>