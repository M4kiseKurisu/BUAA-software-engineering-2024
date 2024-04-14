<template>
    <div class="chatContainer">
        <div class="aside">
            <div class="chatBar">

            </div>
            <div style="height: 94%;">
                <div style="display: flex; width: 100%;align-items: center;height: 5%;">
                    <el-input v-model="nameKeyWord" style="width: 100%; margin-top: 5px;" placeholder="Please input"
                        clearable />
                    <div style="background-color: aliceblue;margin-top: 5px;">
                        <img src="../../Images/搜索.png">
                    </div>
                </div>
                <el-scrollbar style="height: 95%;">
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                    <PersonItem></PersonItem>
                </el-scrollbar>
            </div>
        </div>
        <div style="width: 82%;height: 100%;">
            <div class="header">
                {{ senderName }}
            </div>
            <el-scrollbar style="height: 88%;">
                <ChatMessage :senderId="0"></ChatMessage>
                <ChatMessage :senderId="999"></ChatMessage>
                <ChatMessage :senderId="0"></ChatMessage>
                <ChatMessage :senderId="999"></ChatMessage>
                <ChatMessage :senderId="0"></ChatMessage>
                <ChatMessage :senderId="999"></ChatMessage>
                <ChatMessage :senderId="0"></ChatMessage>
                <ChatMessage :senderId="999"></ChatMessage>
            </el-scrollbar>
            <div class="footer">
                <el-input v-model="textinput" style="width: 100%;margin-left: 5px;" :autosize="{ minRows: 1, maxRows: 10 }" type="textarea"
                    placeholder="Please input" @keyup.enter.native="send" />
                    <el-button type="primary" style="margin-left: 5px;margin-right: 5px;">发送 &#x2708;</el-button>
            </div>
        </div>
    </div>
</template>
<script>
import ChatMessage from './ChatMessage.vue';
import PersonItem from './PersonItem.vue';
export default {
    data() {
        return {
            headImg: '',
            senderId: '',
            senderName: 'huazhi',
            content: 'hello hxt!',
            time: '2022.2.2.2',
            textinput: '',
            nameKeyWord: '',
        }
    },
    methods: {
        send() {
            /* 发送消息 */
            console.log(this.textinput);
            /* 清空输入框 */
            this.textinput = '';
        },
    },
    components: {
        ChatMessage,
        PersonItem,
    }
}
</script>
<style scoped>
.chatContainer {
    width: 100%;
    height: 1000px;
    display: flex;
}

.aside {
    width: 18%;
    height: 100%;
    background-color: rgb(196, 224, 248);
}

.chatBar {
    width: 100%;
    height: 6%;
    display: flex;
    justify-content: center;
    background-color: beige;
}

.header {
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: xx-large;
    font-weight: bolder;
    width: 100%;
    height: 6%;
    background-color: beige;
}

.footer {
    width: 100%;
    background-color: rgb(66, 79, 91);
    height: 6%;
    display: flex;
    align-items: center;
}
</style>