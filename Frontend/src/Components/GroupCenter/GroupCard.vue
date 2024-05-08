<template>
    <div class="group_card_container" @click = "showSubmitForm = true">
        <!-- <div style="width: 100%;height: 40%;display: flex;">
            <div style="height: 100%;aspect-ratio: 1/1 ;display: flex;align-items: center;justify-content: center;">
                <img src="../../Images/buaaLogo.png" alt="" style="height: 90%;aspect-ratio: 1/1 ;border-radius: 5%;">
            </div>
            <div style="width: 280px;height: 100%;">
                <div
                    style="max-width: 90%;height: 25%;display: flex;align-items: center;margin-left: 10px;margin-top: 10px;">
                    <span style="font-size: 1.3em;font-weight: bold;">{{ groupName }}</span>
                </div>
                <div
                    style="max-width: calc(100% - 5px);height: 50%;display: flex;flex-wrap: wrap;margin-left: 5px;margin-top: 1px;">
                    <el-tag type="primary"
                        style="margin-right: 8px;margin-top: 2px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                    <el-tag type="primary"
                        style="margin-right: 8px;margin-top: 2px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                    <el-tag type="primary"
                        style="margin-right: 8px;margin-top: 2px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                    <el-tag type="primary"
                        style="margin-right: 8px;margin-top: 2px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                    <el-tag type="primary"
                        style="margin-right: 8px;margin-top: 2px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                </div>

            </div>
        </div>
        <div style="width: 100%;height: 37%;">
            <div style="width: 100%;height: 20%;">
                <span style="margin-left: 10px;">团体简介</span>
            </div>
            <div style="width: calc(100% - 10px);height: 80%;margin-left: 10px;">
                <div style="max-width: 100%;max-height: 100%;color: dimgray;font-size: 0.90em;">{{
                    briefIntro }}</div>
            </div>
        </div>
        <div style="width:100%;height: 23%;">
            <div style="width: 100%;margin-left: 10px;margin-top: 5px;">
                <span>团体成员 {{ groupMemberNum }}/{{ groupCapacity }}</span>
            </div>
            <div style="height: 48px;width: 100%;display: flex;align-items: center;margin-left: 10px;">
                <img src="../../Images/testAvatar.jpg" alt=""
                    style="height: 85%;aspect-ratio: 1/1 ;border-radius: 50%;margin-right: 5px;">
                <img src="../../Images/testAvatar.jpg" alt=""
                    style="height: 85%;aspect-ratio: 1/1 ;border-radius: 50%;margin-right: 5px;">
                <span>...</span>
                <el-button type="primary" style="margin-left: auto;margin-right: 20px;"
                    @click="showSubmitForm = true">申请加入</el-button>
            </div>
        </div> -->
        <!-- 新尝试卡片 -->
        <div style="height: 100%;aspect-ratio: 1/1 ;display: flex;align-items: center;justify-content: center;">
            <img src="../../Images/buaaLogo.png" alt="" style="width: 90%;aspect-ratio: 1/1 ;">
        </div>
        <div style="flex-grow: 1;">
            <div style="max-width: 90%;height: 25%;display: flex;align-items: center;margin-left: 10px;margin-top: 10px;">
                <span style="font-size: 1.3em;font-weight: bold;">{{ groupName }}</span>
            </div>
            <div style="max-width: calc(100% - 5px);height: 25%;display: flex;flex-wrap: wrap;margin-left: 10px;margin-top: 1px;align-items: center;">
                <span style="font-size: 1.2em;font-weight: bold;color: dimgray;">团体人数：{{ this.groupMemberNum }}/{{ this.groupCapacity }}</span>
            </div>
            <div style="max-width: calc(100% - 5px);height: 25%;display: flex;flex-wrap: wrap;margin-left: 10px;margin-top: 1px;align-items: center;">
                <span style="font-size: 1.2em;font-weight: bold;color: dimgray;">是否需要审核：<span v-if = "this.isExamine">是</span> <span v-else>否</span></span>
            </div>
        </div>
        <!-- 新尝试卡片 -->
        <el-dialog v-model="showSubmitForm" title="团体申请" width="900">
            <div style="width: 100%;display: flex;">
                <div style="width: 420px;border-right: 1px solid darkgray;">
                    <div style="width: 100%;display: flex;">
                        <div
                            style="height: 120px;aspect-ratio: 1/1 ;display: flex;align-items: center;justify-content: center;">
                            <img src="../../Images/buaaLogo.png" alt=""
                                style="height: 90%;aspect-ratio: 1/1 ;border-radius: 5%;">
                        </div>
                        <div style="width: 280px;height: 100%;">
                            <div
                                style="max-width: 90%;height: 25%;display: flex;align-items: center;margin-left: 10px;margin-top: 10px;">
                                <span style="font-size: 1.5em;font-weight: bold;">{{ groupName }}</span>
                            </div>
                            <div
                                style="max-width: calc(100% - 5px);height: 50%;display: flex;flex-wrap: wrap;margin-left: 5px;margin-top: 1px;">
                                <el-tag type="primary"
                                    style="margin-right: 8px;margin-top: 5px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                                <el-tag type="primary"
                                    style="margin-right: 8px;margin-top: 5px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                                <el-tag type="primary"
                                    style="margin-right: 8px;margin-top: 5px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                                <el-tag type="primary"
                                    style="margin-right: 8px;margin-top: 5px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                                <el-tag type="primary"
                                    style="margin-right: 8px;margin-top: 5px;font-weight: bold;font-size: 1em;">tag2</el-tag>
                            </div>
                        </div>
                    </div>
                    <div style="margin-top: 10px;">
                        <span style="margin-left: 10px;font-size: 1.3em;font-weight: bold;">创建人：{{ groupCreaterName
                        }}</span>
                    </div>
                    <div style="margin-top: 10px;">
                        <span v-if="isExamine" style="margin-left: 10px;font-size: 1.3em;font-weight: bold;">是否需要审核：是</span>
                        <span v-if="!isExamine"
                            style="margin-left: 10px;font-size: 1.3em;font-weight: bold;">是否需要审核：否</span>
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
                        <div
                            style="width: 100%;display: flex;align-items: center;margin-left: 10px;margin-top: 10px;flex-wrap: wrap;">
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <GroupMemberItem></GroupMemberItem>
                            <!-- <img src="../../Images/testAvatar.jpg" alt=""
                                style="height: 45px;aspect-ratio: 1/1 ;border-radius: 50%;margin-right: 5px;margin-bottom: 5px;">-->
                        </div>
                    </div>
                </div>
                <div style="width: calc(100% - 420px);">
                    <div style="width: 100%;height: 30px;margin-left: 20px;">
                        <span style="font-size: 1.3em;font-weight: bold;">申请信息</span>
                    </div>
                    <div style="margin-left: 20px;">
                        <textarea id="submitText" type="text" class="custom-input" placeholder="请输入申请信息"
                            v-model="inputSubmitText"></textarea>
                    </div>
                    <div style="display: flex;justify-content: end;margin-top: 5px;">
                        <el-button style="margin-left: auto;margin-right: 4px;" type="primary"
                            @click="pushSubmitText">提交申请</el-button>
                    </div>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import GroupMemberItem from '../Chat/GroupMemberItem.vue';

export default {
    props: {
        groupInfo: {
            type: Object,
            default: null,
        }
    },
    data() {
        return {
            groupId: 1,
            groupAvatar: '../../Images/buaaLogo.png',
            groupName: '元神讨论中心',
            groupCreaterName: '博酱',
            groupBriefIntor: "O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！O神60级以上的进！",
            groupCapacity: 100,
            groupMemberNum: 50,
            groupMemberAvatar: [],
            groupCreaterId: '',
            showSubmitForm: false,
            isExamine: false,
            inputSubmitText: '',
        }
    },
    components:{
        GroupMemberItem,
    },
    computed: {
        briefIntro() {
            if (this.groupBriefIntor.length > 140) {
                var brief = this.groupBriefIntor.substring(0, 140);
                brief = brief + "...";
                return brief;
            } else {
                return this.groupBriefIntor;
            }
        }
    },
    methods: {
        pushSubmitText() {
            //var textarea = document.getElementById("submitText");
            //this.inputSubmitText = textarea.value;
        },

    },
    created() {
        if (this.groupInfo != null) {
            this.groupId = this.groupInfo.group_id;
            this.groupName = this.groupInfo.name;
            this.groupCreaterId = this.groupInfo.creater_id;
            this.groupMemberNum = this.groupInfo.member_count;
            this.groupBriefIntor = this.groupInfo.content;
            this.groupCapacity = this.groupInfo.permitted_num;
            this.isExamine = this.groupInfo.is_examine;
            this.groupAvatar = this.groupInfo.image;
        }

    }
}
</script>

<style scoped>
/* .group_card_container {
    width: 420px;
    height: 320px;
    background-color: rgb(244, 250, 250);
    border-radius: 2%;
    border: 1px solid rgb(242, 237, 237);
} */
.group_card_container {
    width: 440px;
    height: 160px;
    background-color: rgb(244, 250, 250);
    border-radius: 2%;
    border: 1px solid rgb(242, 237, 237);
    display: flex;
}


.group_card_container:hover {
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
}

.custom-input {
    width: 400px;
    height: 200px;
    padding: 10px;
    border: 2px solid #ccc;
    border-radius: 5px;
    font-size: 16px;
    color: #333;
    outline: none;
    transition: border-color 0.3s ease;
    resize: none;
}

.custom-input:focus {
    border-color: #5e9cd3;
}
</style>