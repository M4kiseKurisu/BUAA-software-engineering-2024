<template>
    <div style="width: 100%;">
        <div style="width: 100%;display: flex;">
            <div style="height: 120px;aspect-ratio: 1/1 ;display: flex;align-items: center;justify-content: center;">
                <img :src="groupAvatar" alt="" style="height: 90%;aspect-ratio: 1/1 ;border-radius: 5%;">
            </div>
            <div style="width: 280px;height: 100%;">
                <div
                    style="max-width: 90%;height: 25%;display: flex;align-items: center;margin-left: 10px;margin-top: 10px;">
                    <span style="font-size: 1.5em;font-weight: bold;">{{ groupName }}</span>
                </div>
                <div
                    style="max-width: calc(100% - 5px);height: 50%;display: flex;flex-wrap: wrap;margin-left: 5px;margin-top: 1px;">
                    <el-tag v-for="item in tags" type="primary"
                        style="margin-right: 8px;margin-top: 5px;font-weight: bold;font-size: 1em;">{{ item
                        }}</el-tag>
                </div>
            </div>
        </div>
        <div style="margin-top: 10px;">
            <span style="margin-left: 10px;font-size: 1.3em;font-weight: bold;">创建人：{{ groupCreaterName }}</span>
        </div>
        <div style="margin-top: 10px;">
            <span v-if="isExamine" style="margin-left: 10px;font-size: 1.3em;font-weight: bold;">是否需要审核：是</span>
            <span v-if="!isExamine" style="margin-left: 10px;font-size: 1.3em;font-weight: bold;">是否需要审核：否</span>
        </div>
        <div style="width: 100%;margin-top: 15px;">
            <div style="width: 100%;height: 20%;">
                <span style="margin-left: 10px;font-size: 1.3em;font-weight: bold;">团体简介</span>
            </div>
            <div style="width: calc(100% - 10px);height: 80%;margin-left: 10px;margin-top: 10px;">
                <div style="max-width: 100%;max-height: 100%;color: dimgray;font-size: 1.1em;">{{
                    groupBriefIntor }}</div>
            </div>
        </div>
        <div style="width:100%;margin-top: 10px;">
            <div style="width: 100%;margin-left: 10px;">
                <span style="font-size: 1.3em;font-weight: bold;">团体成员 {{ groupMemberNum }}/{{ groupCapacity
                }}</span>
            </div>
            <div v-if="this.groupMemberList.length != 0"
                style="width: 100%;display: flex;align-items: center;margin-left: 10px;margin-top: 10px;flex-wrap: wrap;">
                <GroupMemberItem v-for="item in groupMemberList" :memberInfo="item"></GroupMemberItem>
                <!-- <img src="../../Images/testAvatar.jpg" alt=""
                                style="height: 45px;aspect-ratio: 1/1 ;border-radius: 50%;margin-right: 5px;margin-bottom: 5px;">-->
            </div>
        </div>
        <div style="width: 100%;height: 20px;display: flex;margin-top: 40px;">
            <div style="width: 50%;height: 100%;display: flex;align-items: center;justify-content: center;">
                <el-button v-if="this.selfId != this.groupCreaterId" @click="this.showQuit = true" type="primary"
                    plain>退出团体</el-button>
            </div>
            <div style="width: 50%;height: 100%;display: flex;align-items: center;justify-content: center;">
                <el-button type="warning" plain v-if="this.selfId != this.groupCreaterId"
                    @click="this.showReport = true">举报团体</el-button>
                <el-button type="warning" plain v-else @click="showDelect = true">解散团体</el-button>
            </div>
        </div>
    </div>
    <el-dialog v-model="showQuit" title="退出提醒" width="600">
        <div style="width: 100%;height: 100px;align-items: center;justify-content: center;display: flex;">
            <span style="font-size: x-large;color:coral;">是否确定退出团体{{ groupName }}</span>
        </div>
        <div style="width: 100%;display: flex;">
            <div style="width: 50%;align-items: center;justify-content: center;display: flex;">
                <el-button type="primary" @click="showQuit = flase">返回</el-button>
            </div>
            <div style="width: 50%;align-items: center;justify-content: center;display: flex;">
                <el-button type="primary" @click="exitGroup">确定</el-button>
            </div>
        </div>
    </el-dialog>
    <el-dialog v-model="showDelect" title="解散提醒" width="600">
        <div style="width: 100%;height: 100px;align-items: center;justify-content: center;display: flex;">
            <span style="font-size: x-large;color:coral;">是否确定解散团体{{ groupName }}</span>
        </div>
        <div style="width: 100%;display: flex;">
            <div style="width: 50%;align-items: center;justify-content: center;display: flex;">
                <el-button type="primary" @click="showDelect = flase">返回</el-button>
            </div>
            <div style="width: 50%;align-items: center;justify-content: center;display: flex;">
                <el-button type="primary" @click="delectGroup">确定</el-button>
            </div>
        </div>
    </el-dialog>
    <el-dialog v-model="this.showReport" title="举报内容" width="400">
        <el-input v-model="reportInput" style="width: 100%" :autosize="{ minRows: 3, maxRows: 6 }" type="textarea"
            placeholder="请输入举报信息" />
        <div style="display: flex;justify-content: flex-end;">
            <el-button type="danger" style="margin-top: 12px;" @click="this.reportGroup">发送举报</el-button>
        </div>
    </el-dialog>
</template>

<script>
import axios from 'axios';
import GroupMemberItem from './GroupMemberItem.vue';
import { result } from 'lodash';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { ElMessage } from 'element-plus';
export default {
    props: {
        groupId: {
            type: Number,
            default: 1,
        }
    },
    data() {
        return {
            groupAvatar: '',
            groupName: '元神讨论中心',
            groupCreaterName: '博酱',
            groupBriefIntor: "O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！",
            groupCapacity: 100,
            groupMemberNum: 50,
            groupMemberList: [],
            groupCreaterId: 1,
            tags: [],
            selfId: JSON.parse(sessionStorage.getItem("id")),
            isExamine: false,
            showQuit: false,
            showDelect: false,
            showReport: false,
            reportInput: '',
        }
    },
    components: {
        GroupMemberItem,
    },
    methods: {
        delectGroup() {
            axios({
                method: "POST",
                url: "api/group/delete",
                data: {
                    group_id: this.groupId,
                }
            }).then((result) => {
                if (result.data.success) {
                    ElMessage({
                        message: '团体解散成功',
                        type: 'success',
                        plain: true,
                    })
                    this.$nextTick(() => {
                        this.$router.go(0);
                    })
                }
                //console.log(result);
            })
        },
        getGroupInfo() {
            axios({
                method: "GET",
                url: "api/group/info",
                params: {
                    group_id: this.groupId,
                }
            }).then((result) => {
                console.log(result);
                this.groupName = result.data.group[0].name;
                this.groupCreaterId = result.data.group[0].creater_id;
                this.groupMemberNum = result.data.group[0].member_count;
                this.groupBriefIntor = result.data.group[0].content;
                this.groupCapacity = result.data.group[0].permitted_num
                this.isExamine = result.data.group[0].is_examine;
                this.tags = result.data.group[0].tags;
                this.groupAvatar = result.data.group[0].image;
                this.getCreaterInfo();
            })
        },
        getCreaterInfo() {
            axios({
                method: "GET",
                url: 'api/user/social/simple',
                params: {
                    id: this.groupCreaterId,
                }
            }).then((result) => {
                this.groupCreaterName = result.data.name;
            })
        },
        getGroupMemberItem() {
            axios({
                method: "GET",
                url: 'api/group/memberList',
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/group/memberList',
                params: {
                    group_id: this.groupId,
                }
            }).then((result) => {
                this.groupMemberList = result.data.member;
            })
        },
        reportGroup() {
            axios({
                method: "POST",
                url: 'api/group/report',
                data: {
                    id: this.groupId,
                    detail: this.reportInput,
                }
            }).then((result) => {
                if (result.data.success) {
                    this.showReport = false;
                    ElMessage({
                        message: '举报发送成功',
                        type: 'success',
                        plain: true,
                    })
                } else {
                    ElMessage({
                        message: '举报发送失败',
                        type: 'danger',
                        plain: true,
                    })
                }
            })
        },
        exitGroup() {
            axios({
                method: "POST",
                url: 'api/group/exit',
                data: {
                    group_id: this.groupId,
                }
            }).then((result) => {
                if (result.data.success) {
                    ElMessage({
                        message: '退出团体成功',
                        type: 'success',
                        plain: true,
                    })
                } else {
                    ElMessage({
                        message: '退出团体失败',
                        type: 'danger',
                        plain: true,
                    })
                }
                this.$router.go(0);
            })
        },
    },
    created() {
        this.getGroupInfo();
        this.getGroupMemberItem();
        //this.getCreaterInfo();
        //console.log(this.groupCreaterId);
    }
}
</script>

<style scoped></style>