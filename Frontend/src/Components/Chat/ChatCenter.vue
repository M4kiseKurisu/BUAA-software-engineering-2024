<template>
    <div class="chatContainer">
        <div class="aside">
            <div class="chatBar">
                <div class="chatKindChose" :class="{ active: chatKindChose === 1 }"
                    style="width: 50%;height: 100%;display: flex;justify-content: center;align-items: center;"
                    @click="chosePersonChat">
                    <img src="../../Images/私聊.png" alt="">
                </div>
                <div class="chatKindChose" :class="{ active: chatKindChose === 2 }" @click="choseGroupChat"
                    style="width: 50%;height: 100%;display: flex;justify-content: center;align-items: center;">
                    <img src="../../Images/群聊.png" alt="">
                </div>
            </div>
            <div style="height: 94%;">
                <div style="display: flex; width: 100%;align-items: center;height: 5%;background-color: azure;">
                    <el-input v-model="nameKeyWord" style="width: 100%; margin-top: 5px;" placeholder="Please input"
                        clearable />
                    <div style="background-color: white;margin-top: 5px;">
                        <el-button text><img src="../../Images/搜索.png"></el-button>
                    </div>
                </div>
                <el-scrollbar v-if="this.chatKindChose == 2" style="height: 95%;">
                    <GroupItem @getGroupId="getGroupId"></GroupItem>
                </el-scrollbar>
                <el-scrollbar v-if="this.chatKindChose == 1" style="height: 95%;">
                    <PersonItem @getPersonId="getPersonId"></PersonItem>
                </el-scrollbar>
            </div>
        </div>
        <div style="width: 82%;height: 100%;min-width: 760px;">
            <div class="header" v-if="this.chatKindChose == 1" style="justify-content: center;">
                {{ personName }}
            </div>
            <div class="header" v-if="this.chatKindChose == 2">
                <span style="flex: 1;display: flex;align-items: center;justify-content: center;">{{ groupName }}</span>
                <el-tooltip class="box-item" effect="dark" content="团体详情" placement="bottom-end"><el-button text><img
                            src="../../Images/省略号.png" alt="" @click = "this.showGroupInfo = true"></el-button></el-tooltip>
            </div>
            <el-dialog v-model="showGroupInfo" title="团体信息" width="650">
                <GroupInfo v-if = "this.showGroupInfo"></GroupInfo>
            </el-dialog>
            <div style="height: 88%;width: 100%;">
                <el-scrollbar style="height: 100%;width: 100%;" v-if="this.messageList.length == 0">
                    <ChatMessage></ChatMessage>
                    <ChatMessage></ChatMessage>
                </el-scrollbar>
            </div>

            <div class="footer">
                <el-input v-model="textinput" style="width: 100%;margin-left: 5px;" :autosize="{ minRows: 1, maxRows: 10 }"
                    size="large" placeholder="Please input" @keyup.enter.native="send" />
                <el-button type="primary" style="margin-left: 5px;margin-right: 5px;">发送 &#x2708;</el-button>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import ChatMessage from './ChatMessage.vue';
import GroupItem from './GroupItem.vue';
import PersonItem from './PersonItem.vue';
import { result } from 'lodash';
import GroupInfo from './GroupInfo.vue';
export default {
    data() {
        return {

            time: '2022.2.2.2',
            textinput: '',
            nameKeyWord: '',
            chatKindChose: 0,
            messageList: [],
            messageCount: '',
            groupId: 1,
            personId: 1,
            personName: 'huazhi',
            groupName: '元神讨论组',
            showGroupInfo: false,
        }
    },
    methods: {
        send() {
            /* 发送消息 */
            console.log(this.textinput);
            /* 清空输入框 */
            axios({
                method: "POST",
                url: "api/message/private",
                data: {
                    receiver_id: this.sendId,
                    content: this.textinput,
                }
            }).then((result) => {
                console.log(result);
            });
            this.textinput = '';
        },
        chosePersonChat() {
            this.chatKindChose = 1;
        },
        choseGroupChat() {
            this.chatKindChose = 2;
        },
        getGroupId(value) {
            this.getGroupId = value;
            //console.log("diaoshangl")
            console.log(this.getGroupId);
        },
        getPersonMessageList() {
            axios({
                method: 'GET',
                url: 'api/message/private',
                params: {
                    receiver_id: this.sendId,
                }
            }).then((result) => {
                console.log(result);
                this.messageCount = result.data.message_count;
                this.messageList = result.data.message_list
            });

        }
    },
    components: {
        ChatMessage,
        GroupItem,
        PersonItem,
        GroupInfo,
    },
    created() {
        console.log(this.$route.params);
        this.groupId = this.$route.params.groupId;
        this.personId = this.$route.params.personId;
        if (this.groupId == -1) {
            this.chatKindChose = 1;
        } else if (this.personId == -1) {
            this.chatKindChose = 2;
        }
        console.log(this.groupId);
        console.log(this.personId);
    }
}
</script>
<style scoped>
.chatContainer {
    width: calc(100vw - 205px);
    height: calc(100vh - 70px);
    display: flex;
}

.aside {
    width: 350px;
    min-width: 300px;
    height: 100%;
    background-color: white;
}

.chatBar {
    width: 100%;
    height: 6%;
    display: flex;
    justify-content: center;
    background-color: white;
}

.header {
    display: flex;
    align-items: center;
    font-size: xx-large;
    font-weight: bolder;
    width: 100%;
    height: 6%;
    background-color: azure;
}

.footer {
    width: 100%;
    background-color: rgb(213, 221, 228);
    height: 6%;
    display: flex;
    align-items: center;
}

.chatKindChose.active {
    background-color: rgb(218, 234, 234);
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}
</style>