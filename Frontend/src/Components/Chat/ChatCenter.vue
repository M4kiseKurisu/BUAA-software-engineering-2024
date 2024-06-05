<template>
    <div class="chatContainer">
        <!-- <div class="aside"> -->
        <div style="width: 27%; height: 100%; background-color: #efefef;min-width: 315px;">
            <!-- <div class="chatBar">
                <div class="chatKindChose" :class="{ active: chatKindChose === 1 }"
                    style="width: 50%;height: 100%;display: flex;justify-content: center;align-items: center;"
                    @click="chosePersonChat">
                    <img src="../../Images/私聊.png" alt="">
                </div>
                <div class="chatKindChose" :class="{ active: chatKindChose === 2 }" @click="choseGroupChat"
                    style="width: 50%;height: 100%;display: flex;justify-content: center;align-items: center;">
                    <img src="../../Images/群聊.png" alt="">
                </div>
            </div> -->
            <!-- <div style="height: 94%;">
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
                <el-scrollbar v-if="this.chatKindChose == 1" style="height: 95%;"> -->
            <!-- <没加key> -->
            <!-- <PersonItem @getPersonId="getPersonId" v-for="item in personItemList" :chatItemInfo="item" ></PersonItem>
                </el-scrollbar>
            </div> -->
            <div style="width: 100%; background-color: #f7f8fa; padding-top: 12px; padding-bottom: 16px;">
                <div style="display: flex; width: 90%; margin-left: 5%; align-items: center; ">
                    <div style="width: 90%;">
                        <el-input v-model="nameKeyWord" style="width: 100%; " placeholder="搜索用户及群聊" clearable />
                    </div>
                    <div>
                        <el-button
                            style="width: 30px; height: 30px; margin-left: 4px; border: none; background-color: #f7f8fa;">
                            <svg t="1715139963488" style="width: 22px; height: 22px; " viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="2334" width="200" height="200">
                                <path
                                    d="M674.1504 619.9808l1.6896 1.536 235.3152 235.3152a25.6 25.6 0 1 1-36.1984 36.1984l-235.3152-235.3152a26.0608 26.0608 0 0 1-0.2048-0.1536 320 320 0 1 1 9.216-461.4144 25.6 25.6 0 0 1-36.1984 36.1984 268.8 268.8 0 1 0 67.3792 112.5376 25.6 25.6 0 1 1 49.0496-14.6944c10.0864 33.6384 14.592 68.7616 13.312 103.936a318.7712 318.7712 0 0 1-68.0448 185.856z"
                                    fill="#86909c" p-id="2335"></path>
                            </svg>
                        </el-button>
                    </div>
                </div>

                <div style="width: 90%; margin-left: 5%; margin-top: 16px; display: flex;">
                    <div class="chatKindChose" :class="{ active: chatKindChose === 1 }" @click="chosePersonChat"
                        style="width: 50% ; display: flex; justify-content: center;align-items: center;height: 30px;">
                        <button style="width: 26px; height: 26px; border: none; background-color: #f7f8fa;">
                            <svg t="1715140629455" style="width: 100%; height: 100%" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="4751" width="200" height="200">
                                <path
                                    d="M345.571015 461.585797c-27.467585 0-49.694819 22.226211-49.694819 49.694819 0 27.432793 22.227234 49.694819 49.694819 49.694819 27.465538 0 49.690726-22.26305 49.690726-49.694819 0-27.469632-22.225187-49.694819-49.690726-49.694819zM511.216659 461.585797c-27.467585 0-49.694819 22.25691-49.694819 49.694819 0 27.432793 22.228257 49.694819 49.694819 49.694819 27.468608 0 49.690726-22.26305 49.690726-49.694819 0-27.437909-22.222117-49.694819-49.690726-49.694819zM676.86128 461.585797c-27.468608 0-49.694819 22.226211-49.694819 49.694819 0 27.432793 22.226211 49.694819 49.694819 49.694819s49.694819-22.26305 49.694819-49.694819c0.001023-27.469632-22.226211-49.694819-49.694819-49.694819z"
                                    fill="#86909c" p-id="4752"></path>
                                <path
                                    d="M878.772896 765.278517c52.185546-75.1864 79.684854-162.667823 79.684854-254.0286 0-246.626004-200.615087-447.245184-447.241091-447.245184S63.972499 264.654612 63.972499 511.279592c0 246.558465 200.61918 447.208345 447.245183 447.246208 71.855538 0 142.997831-17.569144 206.701713-50.894134l164.76867 49.469691a33.371038 33.371038 0 0 0 9.57917 1.410116c8.66638 0 17.066701-3.41068 23.35594-9.725502a33.148981 33.148981 0 0 0 8.31641-32.93511l-45.166689-150.572344z m-146.141428 93.349062a51.166333 51.166333 0 0 0-38.430264 3.667529c-56.356542 29.482475-119.632681 45.065382-182.977382 45.065382-218.403219-0.032746-396.087037-177.713494-396.087037-396.080898 0-218.415498 177.680748-396.10955 396.079874-396.109549 218.397079 0 396.075781 177.680748 396.075781 396.079874 0 40.241516-6.031367 79.861886-17.925255 117.757984-11.888771 37.880749-29.595039 73.912383-52.627614 107.096156a51.16531 51.16531 0 0 0-6.974855 43.875277l35.539424 118.480438-132.672672-39.832193z"
                                    fill="#86909c" p-id="4753"></path>
                            </svg>
                        </button>
                    </div>

                    <el-divider direction="vertical" />

                    <div class="chatKindChose" :class="{ active: chatKindChose === 2 }" @click="choseGroupChat"
                        style="width: 50%; display: flex; justify-content: center;align-items: center;height: 30px;">
                        <button style="width: 26px; height: 26px; border: none; background-color: #f7f8fa;">
                            <svg t="1715140591634" style="width: 100%; height: 100%" class="icon" viewBox="0 0 1024 1024"
                                version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3300" width="200" height="200">
                                <path
                                    d="M784.344 938.278c-12.192 0-23.923-3.469-33.94-10.041l-32.314-20.83c-1.648-0.879-4.025-2.303-6.403-4.525l-18.779-12.422c-72.405 9.644-143.027-2.402-204.866-35.003-11.388-6.009-15.664-19.959-9.535-31.141 2.93-5.386 7.926-9.4 13.875-11.161a23.77 23.77 0 0 1 17.847 1.782c55.295 29.164 119.103 39.127 184.489 28.76a23.837 23.837 0 0 1 16.805 3.628l28.817 19.034c0.96 0.641 1.899 1.361 2.767 2.133l33.136 21.387c4.369 2.884 9.106 3.175 16.355 0.886 4.138-2.856 6.562-7.232 6.562-12.008v-58.643c0.004-7.657 3.887-14.818 10.36-19.091 65.017-42.872 103.829-110.157 103.829-179.972 0-16.419-1.899-32.42-5.63-47.574-3.359-13.51-8.458-27.183-15.09-40.615-5.648-11.427-0.801-25.192 10.839-30.741 11.664-5.538 25.638-0.783 31.286 10.64 8.096 16.352 14.297 33.118 18.478 49.838 4.62 18.74 6.952 38.394 6.952 58.452 0 81.326-42.444 159.365-114.186 211.01v46.695c0 21.787-12.188 42.072-31.811 52.942a23.049 23.049 0 0 1-3.476 1.58c-9.378 3.36-17.998 5-26.367 5m-36.158-63.157l0.025 0.032-0.025-0.032M281.533 849.288c-10.222 0-20.444-2.569-29.525-7.444-19.37-9.889-32.129-30.624-32.129-52.591v-75.126C121.903 647.898 63.816 545.146 63.816 436.903c0-193.641 181.789-351.181 405.225-351.181 223.46 0 405.246 157.54 405.246 351.181 0 193.638-181.786 351.174-405.246 351.174-22.138 0-44.803-1.697-67.465-5.042l-36.112 23.481c-1.736 1.417-3.27 2.385-4.319 3.033l-46.791 30.287c-8.717 5.973-20.562 9.452-32.821 9.452m187.508-717.565c-197.616 0-358.388 136.908-358.388 305.18 0 96.41 54.313 188.202 145.291 245.541 6.714 4.231 10.775 11.529 10.771 19.363v87.445c0 4.865 3.019 9.793 7.317 12.008 6.041 3.235 12.465 1.548 14.02 0.514l46.586-30.184c0.709-0.436 1.006-0.627 1.052-0.627 0 0 0 0.021-0.025 0.053a20.79 20.79 0 0 1 2.583-1.963l45.058-29.323a23.879 23.879 0 0 1 16.876-3.504c23.165 3.887 46.334 5.85 68.858 5.85 197.638 0 358.412-136.904 358.412-305.173 0.001-168.272-160.773-305.18-358.411-305.18M334.659 771.927c0 0.011-0.025 0.021-0.025 0.032 0-0.011 0.025-0.018 0.025-0.032"
                                    fill="#86909c" p-id="3301"></path>
                                <path
                                    d="M364.833 433.714c0 21.624-17.957 39.265-39.974 39.265-22.017 0-39.978-17.641-39.978-39.265 0-21.62 17.96-39.258 39.978-39.258 22.017 0 39.974 17.637 39.974 39.258z m147.162 7.518c0 19.544-16.238 35.488-36.14 35.488-19.905 0-36.137-15.944-36.137-35.488 0-19.548 16.231-35.492 36.137-35.492 19.901 0 36.14 15.945 36.14 35.492z m133.035 0c0 19.544-16.238 35.488-36.137 35.488-19.906 0-36.14-15.944-36.14-35.488 0-19.548 16.235-35.492 36.14-35.492 19.899 0 36.137 15.945 36.137 35.492z"
                                    fill="#86909c" p-id="3302"></path>
                            </svg>
                        </button>
                    </div>
                </div>
            </div>

            <div
                style="width: 100%; background-color: #efefef; padding-top: 10px; padding-bottom: 10px;height: calc(100% - 128px)">
                <el-scrollbar v-if="this.chatKindChose == 2" style="height: 100%;">
                    <div style="width: 100%; " v-for="item in this.groupItemList" class="hover-div">
                        <!-- <GroupItem v-for = "item in this.groupItemList" :groupInfo = "item" @getGroupId="getGroupId"></GroupItem> -->
                        <div style="width: 90%; margin-left: 5%; padding-top: 5px; padding-bottom: 5px;">
                            <GroupItem :groupInfo="item"></GroupItem>
                        </div>
                    </div>
                </el-scrollbar>
                <el-scrollbar v-if="this.chatKindChose == 1" style="height: 100%;">
                    <!-- <没加key> -->
                    <div style="width: 100%;">
                        <div v-for="item in personItemList" style="width: 100%" class="hover-div">
                            <div style="width: 90%; margin-left: 5%; padding-top: 11px; padding-bottom: 11px;">
                                <PersonItem @getPersonId="getPersonId" :chatItemInfo="item"></PersonItem>
                            </div>
                            <!-- <el-divider /> -->
                        </div>
                    </div>
                </el-scrollbar>
            </div>

        </div>

        <el-divider direction="vertical" />

        <!-- <div style="width: 82%;height: 100%;min-width: 760px;"> -->
        <!-- <div>
            <div class="header" v-if="this.chatKindChose == 1" style="justify-content: center;">
                {{ personName }}
            </div>
            <div class="header" v-if="this.chatKindChose == 2">
                <span style="flex: 1;display: flex;align-items: center;justify-content: center;">{{ groupName }}</span>
                <el-tooltip class="box-item" effect="dark" content="团体详情" placement="bottom-end"><el-button text><img
                            src="../../Images/省略号.png" alt="" @click="this.showGroupInfo = true"></el-button></el-tooltip>
            </div>
            <el-dialog v-model="showGroupInfo" title="团体信息" width="650">
                <GroupInfo v-if="this.showGroupInfo"></GroupInfo>
            </el-dialog>
            <div style="height: 88%;width: 100%;">
                <el-scrollbar style="height: 100%;width: 100%;" v-if="this.messageList.length != 0">
                    <ChatMessage v-for="item in messageList" :messageInfomation="item"></ChatMessage>
                </el-scrollbar>
            </div>
            <div class="footer">
                <el-input v-model="textinput" style="width: 100%;margin-left: 5px;" :autosize="{ minRows: 1, maxRows: 10 }"
                    size="large" placeholder="Please input" @keyup.enter.native="sendMessage" />
                <el-button type="primary" style="margin-left: 5px;margin-right: 5px;" @click="sendMessage">发送
                    &#x2708;</el-button>
            </div>
        </div>  -->
        <div style="width: 73%; height: 100%; background-color: #f7f8fa;min-width: 700px;">
            <div style="height: 10%; width: 100%; display: flex; align-items: center;justify-content: center;">
                <div v-if="this.chatKindChose == 1"
                    style="font-size: 25px; display: flex;align-items: center;justify-content: center;">
                    <span>{{ titleName }}</span>
                </div>
                <div v-if="this.chatKindChose == 2"
                    style="font-size: 25px; display: flex;align-items: center;justify-content: center;">
                    <span>{{ titleName }}</span>
                </div>
            </div>
            <el-divider />
            <div style="height: 60%; width: 100%;">
                <!-- 这里装聊天内容 -->
                <!-- <el-scrollbar style="height: 100%;width: 100%;" v-if="this.messageList.length != 0">
                    <ChatMessage v-for="item in messageList" :messageInfomation="item" :key="item.id"></ChatMessage>
                </el-scrollbar> -->
                <div id="messageContainer" style="height: 100%;width: 100%;overflow-y: auto;overflow-x: hidden;"
                    v-if="this.messageList != []">
                    <ChatMessage v-for="item in messageList" :messageInfomation="item" :key="item.id"></ChatMessage>
                </div>
            </div>
            <el-divider />
            <div style="height: 29%; width: 100%;">
                <textarea v-model="textinput" placeholder="输入聊天内容" style="width: 95%; margin-left: 2%; margin-top: 20px; height: 64%;
                                                                                                border: none;"
                    @keydown.enter="handleKeyPress"></textarea>
                <div style="width: 94%; display: flex; justify-content: flex-end; margin-top: 8px;">
                    <el-button type="success" plain @click="sendMessage">发送信息</el-button>
                </div>
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
            selfId: JSON.parse(sessionStorage.getItem("id")),
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
            personItemList: [],
            groupItemList: [],
            titleName: '',
            ws: null,
            timer: '',
            reopen_timer: '',
            close_sign: false,
        }
    },
    methods: {
        // send() {
        //     /* 发送消息 */
        //     console.log(this.textinput);
        //     /* 清空输入框 */
        //     axios({
        //         method: "POST",
        //         url: "api/message/private",
        //         data: {
        //             receiver_id: this.sendId,
        //             content: this.textinput,
        //         }
        //     }).then((result) => {
        //         console.log(result);
        //     });
        //     this.textinput = '';
        // },
        chosePersonChat() {
            this.chatKindChose = 1;
            this.getPersonItemList();
        },
        choseGroupChat() {
            this.chatKindChose = 2;
            this.getGroupItemList();
        },
        getGroupId(value) {
            this.groupId = value;
            //console.log("diaoshangl")
            this.personId = -1;
            this.chatKindChose = 2;
            this.$router.push({ name: 'ChatCenter', params: { personId: -1, groupId: this.groupId } }).then(() => {
                this.$nextTick(() => {
                    // 强制重新加载当前页面
                    location.reload();
                });
            });
            console.log(this.getGroupId);
        },
        getPersonId(value) {
            this.personId = value;
            this.groupId = -1;
            this.chatKindChose = 1;
            this.$router.push({ name: 'ChatCenter', params: { personId: this.personId, groupId: -1 } }).then(() => {
                this.$nextTick(() => {
                    // 强制重新加载当前页面
                    location.reload();
                });
            });
            console.log(this.personId);
            this.getPersonInfo();
            this.getPersonMessageList();
        },
        getGroupMessageList() {
            axios({
                method: 'GET',
                url: 'api/group/message',
                //url: 'http://127.0.0.1:4523/m2/4272722-0-default/166110437',
                params: {
                    group_id: this.groupId,
                }
            }).then((result) => {
                console.log(result);
                this.messageCount = result.data.message_count;
                this.messageList = result.data.message_list;
            });
        },
        getPersonMessageList() {
            axios({
                method: 'GET',
                url: 'api/message/private',
                //url: 'http://127.0.0.1:4523/m2/4272722-0-default/166110437',
                params: {
                    receiver_id: this.personId,
                }
            }).then((result) => {
                //console.log(result);
                this.messageCount = result.data.message_count;
                this.messageList = result.data.message_list;
            });

        },
        getPersonItemList() {
            axios({
                method: 'GET',
                url: 'api/message/chats',
                params: {
                    type: 1,
                }
            }).then((result) => {
                console.log(result);
                this.personItemList = result.data.chat_list;
            })
        },
        getGroupItemList() {
            axios({
                method: "GET",
                url: 'api/group/joined',
                //url: 'http://127.0.0.1:4523/m1/4272722-0-default/group/list',
            }).then((result) => {
                this.groupItemList = result.data.group;
            })
        },
        handleWsOpen() {
            this.$message({showClose: false, message: '服务连接成功！', type: 'success'});
            console.log("ws 打开了！");
        },
        handleWsClose() {
            this.close_sign = true;
            console.log("ws 关闭了！");
        },
        handleWsError() {
            this.close_sign = true;
            console.log("ws 爆炸了！");
        },
        handleWsMessage(rawData) {
            //console.log(rawData.data);
            if (rawData.data == "pong") {
                console.log("心跳！");
            } else {
                var message = JSON.parse(rawData.data);
                if (this.groupId == message.group_id) {
                    this.messageList.push(message);
                    this.scrollToBottom();
                }
            }
        },
        handleKeyPress(event) {
            if (event.key === 'Enter') {
                this.sendMessage(event);
            }
        },
        sendMessage(event) {
            event.preventDefault();
            var textTest = this.textinput;
            if (textTest.trim() == '') {
                this.textinput = '';
                return;
            }
            if (this.ws && this.ws.readyState === WebSocket.OPEN) {
                var messageToSend;
                // 发送消息
                messageToSend = {
                    sender_id: this.selfId,
                    content: this.textinput,
                    receiver_id: this.personId,
                    group_id: this.groupId,
                };
                let jsonData = JSON.stringify(messageToSend);
                this.ws.send(jsonData);
                //console.log(this.messageList);
                if (!this.messageList) {
                    this.messageList = [];
                }
                //console.log(this.messageList);
                this.messageList.push(messageToSend);
                //console.log(messageToSend);
                // 清空消息输入框
                this.$nextTick(() => {
                    this.textinput = '';
                })
                this.scrollToBottom();
            } else {
                this.$message({showClose: true, message: '服务断线，操作失败！', type: 'error',});
                console.error('WebSocket连接未打开或已关闭');
                this.close_sign = true;
            }
        },
        getPersonInfo() {
            axios({
                method: "GET",
                url: "api/user/social/simple",
                params: {
                    id: this.personId,
                }
            }).then((result) => {
                this.titleName = result.data.name;
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
                this.titleName = result.data.group[0].name;
            })
        },
        scrollToBottom() {
            this.$nextTick(() => {
                var messageContainer = document.getElementById('messageContainer');
                //console.log(messageContainer.scrollTop);
                messageContainer.scrollTop = messageContainer.scrollHeight;
                //console.log(messageContainer.scrollTop);
            })
        },
        tryReload() {

        }
    },
    components: {
        ChatMessage,
        GroupItem,
        PersonItem,
        GroupInfo,
    },
    watch: {
        messageList(newValue, oldValue) {
            this.scrollToBottom();
        },
        // messageList: function scrollToBottom() {
        //     this.$nextTick(() => {
        //         var messageContainer = document.getElementById('messageContainer');
        //         console.log(messageContainer.scrollTop);
        //         messageContainer.scrollTop = messageContainer.scrollHeight;
        //         console.log(messageContainer.scrollTop);
        //     })
        // }
        '$route'() {
            location.reload();
            console.log(this.personId + " " + this.groupId);
        }
    },
    created() {
        //this.ws = new WebSocket('/api/webSocket/' + this.selfId);   //  服务器用这个
        this.ws = new WebSocket('ws://localhost:8080/webSocket/' + this.selfId); //本地调试请用这个
        this.ws.addEventListener('open', this.handleWsOpen.bind(this), false);
        this.ws.addEventListener('close', this.handleWsClose.bind(this), false);
        this.ws.addEventListener('error', this.handleWsError.bind(this), false);
        this.ws.addEventListener('message', this.handleWsMessage.bind(this), false);
        //this.ws.open();
        console.log(this.$route.params);
        this.groupId = this.$route.params.groupId;
        this.personId = this.$route.params.personId;
        if (this.groupId == -1) {
            this.chatKindChose = 1;
            this.getPersonItemList();
            this.getPersonMessageList();
            this.getPersonInfo();
        } else if (this.personId == -1) {
            this.chatKindChose = 2;
            this.getGroupItemList();
            this.getGroupMessageList();
            this.getGroupInfo();
        }
        window.onbeforeunload = function() {
            clearInterval(this.timer);
            clearInterval(this.reopen_timer);
            this.ws.close()
        }
        this.timer = setInterval(() => {
            console.log('发出心跳！');
            this.ws.send('ping');
        }, 30000);
        this.reopen_timer = setInterval(() => {
            if (this.close_sign == true) {
                this.close_sign = false;
                this.$message({showClose: false, message: '服务断线，正在尝试重连...', type: 'error'});
                //this.ws = new WebSocket('/api/webSocket/' + this.selfId);   //  服务器用这个
                this.ws = new WebSocket('ws://localhost:8080/webSocket/' + this.selfId); //本地调试请用这个
                this.ws.addEventListener('open', this.handleWsOpen.bind(this), false);
                this.ws.addEventListener('close', this.handleWsClose.bind(this), false);
                this.ws.addEventListener('error', this.handleWsError.bind(this), false);
                this.ws.addEventListener('message', this.handleWsMessage.bind(this), false);
                if (this.groupId == -1) {
                    this.getPersonItemList();
                    this.getPersonMessageList();
                } else if (this.personId == -1) {
                    this.getGroupItemList();
                    this.getGroupMessageList();
                }
            }
        }, 2000);
        //console.log(this.groupId);
        //console.log(this.personId);
        //this.scrollToBottom();
    },
    beforeDestroy() {
        clearInterval(this.timer);
        clearInterval(this.reopen_timer);
        this.ws.close();
    },
    beforeRouteLeave() {
        clearInterval(this.timer);
        clearInterval(this.reopen_timer);
        this.ws.close();
    },
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
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}

.el-divider--vertical {
    border-left: 1px solid #e5e6eb !important;
    margin: 0px !important;
    height: auto !important;
}

.el-divider--horizontal {
    border-top: 1px solid #e5e6eb !important;
    margin: 0px !important;
}

.hover-div:hover {
    background-color: #e5e6eb;
}

textarea:focus {
    outline: none;
}

textarea {
    background-color: #f7f8fa;
    font-size: 16px;
    color: #101010;
}
</style>