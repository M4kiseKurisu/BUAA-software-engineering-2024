<template>
    <div class="directMessageContainer" @click="goToChatCenter">
        <div class="headContainer"><el-avatar :size="60" :src="this.headImg" />
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
        <div v-if="this.isRead != true"
            style="width: 20%;height: 100%;display: flex;justify-content: end;align-items: center;">
            <el-tag type="danger">未读</el-tag>
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
            selfId : JSON.parse(sessionStorage.getItem("id")),
            personId: 1,
        }
    },
    methods: {
        GetSenderInfomation() {
                this.senderId = this.messageInfomation.sender_id,
                //this.senderName = this.messageInfomation.,
                this.content = this.messageInfomation.last_message_content,
                this.time = this.messageInfomation.last_message_time,
                this.isRead = this.messageInfomation.is_read,
                this.receiverId = this.messageInfomation.receiver_id,
                axios({
                    method: "GET",
                    url: "api/user/social/simple",
                    params: { id: this.personId, },
                }).then((result) => {
                    this.senderName = result.data.name;
                    this.headImg = result.data.user_avatar;
                });
        },
        goToChatCenter() {
            this.$router.push({ name: 'ChatCenter', params: { personId: this.senderId, groupId: -1 } }).then(() => {
                this.$nextTick(() => {
                    // 强制重新加载当前页面
                    location.reload();
                });
            });
        }
    },
    created() {
        if (this.messageInfomation != null) {
            if (this.selfId != this.messageInfomation.sender_id) {
                this.personId = this.messageInfomation.sender_id
            } else {
                this.personId = this.messageInfomation.receiver_id;
            }
            this.GetSenderInfomation();
        }
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