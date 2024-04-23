<template>
    <div class="directMessageContainer">
        <div class="headContainer"><el-avatar :size="60" src="./src/Images/私信.png" />
        </div>
        <div style="margin-left: 10px;width: 70%;">
            <div class="senderContainer">
                <span style="font-size: large;font-weight: bolder;color: black;">{{ senderName }}</span>
                <span style="margin-left: 5px;">给你发送了私信</span>
                <span style="margin-left: 10px;">{{ time }}</span>
            </div>
            <div class="contentContainer">
                {{ content }}
            </div>
        </div>
        <div style="width: 20%;height: 100%;display: flex;justify-content: end;align-items: center;">
            <el-tag type="danger"  >未读</el-tag>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props: {
        messageInfomation: {
            type: Object,
            default: null,
        },
    },
    data() {
        return {
            headImg: '',
            senderId: 1,
            senderName: 'huazhi',
            content: "hello,hxthello,hxthello,",
            time: "2002020",
            isRead: true,
            receiverId: 2,
        }
    },
    methods: {
        GetSenderInfomation() {
            this.senderId = this.messageInfomation.message_sender_id,
                this.senderName = 'huazhi',
                this.content = this.messageInfomation.message_content,
                this.time = this.messageInfomation.message_time,
                this.isRead = this.messageInfomation.is_read,
                this.receiverId = this.messageInfomation.message_receiver_id,
                axios({
                    method: "GET",
                    url: "api/user/info",
                    data: {message_sender_id : this.senderId,},
                }).then((result) => {
                    this.senderName = result.name;
                });
            axios({
                method: "GET",
                url: "api/user/head",
                data: {message_sender_id : this.senderId,},
            }).then((result) => {
                this.headImg = result.info;
            });
        },
    }
}
</script>

<style scoped>
.directMessageContainer {
    width: 100%;
    height: 100px;
    display: flex;
    
}

.headContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
}

.senderContainer {
    display: flex;
    align-items: center;
    height: 50%;
}

.contentContainer {
    width: 100%;
    /* align-items: center; */
    height: 50%;
    font-size: x-large;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
</style>