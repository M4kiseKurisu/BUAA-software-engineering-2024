<template>
    <div v-if="senderId != meId" class="container-o">
        <div class="" style="padding-right: 5px;padding-top: 5px;" @click = "goToShowPersonInfomation">
            <el-avatar :size="55" :src="headImg" />
        </div>
        <div style="width: 100%;">
            <div style="font-size: medium;font-weight: bold;color:dimgray;">{{ senderName }}</div>
            <div class="message-bubble-o">
                <div class="message-content-o">
                    {{ content }}
                </div>
            </div>
        </div>
    </div>
    <div v-if="senderId == meId" class="container-m">
        <div class="" style="padding-left: 5px;padding-top: 5px;">
            <el-avatar :size="55" :src="headImg" />
        </div>
        <div style="width: 100%;display: flex;flex-direction: column;align-items: end;">
            <div style="font-size: medium;font-weight: bold;color:dimgray;">{{ meName }}</div>
            <div class="message-bubble-m">
                <div class="message-content-m">{{ content }}</div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { result } from 'lodash';

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
            senderName: '也是中文名',
            senderId: 0,
            content: 'hello hxt!',
            time: '2022.2.2.2',
            meId: JSON.parse(sessionStorage.getItem("id")),
            meName: '中文名',
            time: '',
        }
    },
    methods: {
        getMessageInfomation() {
            this.senderId = this.messageInfomation.sender_id;
            this.content = this.messageInfomation.content;
            //this.time = this.messageInfomation.time;
            this.meId = JSON.parse(sessionStorage.getItem("id"));
            if (this.meId == this.senderId) {
                axios({
                    method: "GET",
                    url: "api/user/social/simple",
                    params: {
                        id: this.meId,
                    }
                }).then((result) => {
                    this.headImg = result.data.user_avatar;
                    this.meName = result.data.name;
                })
            } else {
                axios({
                    method: "GET",
                    url: "api/user/social/simple",
                    params: {
                        id: this.senderId,
                    }
                }).then((result) => {
                    this.headImg = result.data.user_avatar;
                    this.senderName = result.data.name;
                })
            }
        },
        goToShowPersonInfomation(){
            //this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.memberId});
            let routeUrl = this.$router.resolve({
                path: "/MainPage/Course_Center/ShowPersonalInformation/"+ this.senderId,
            });
            window.open(routeUrl.href, '_blank');
        },
    },
    created() {
        if(this.messageInfomation != null){
            this.getMessageInfomation();
        }
    },
}
</script>

<style scoped>
.message-bubble-o {
    display: flex;
    background-color: #e0e0e0;
    border-radius: 10px;
    padding: 10px;
    margin-bottom: 10px;
    width: fit-content;
    max-width: 70%;
}

.message-content-o {
    font-size: large;
    color: black;
}

.message-bubble-m {
    display: flex;
    background-color: #10ea72;
    border-radius: 10px;
    padding: 10px;
    margin-bottom: 10px;
    width: fit-content;
    max-width: 70%;
    margin-left: 5px;
}

.message-content-m {
    font-size: large;
    color: black;
}

.container-o {
    display: flex;
    margin-left: 10px;
}

.container-m {
    display: flex;
    flex-direction: row-reverse;
    margin-right: 10px;
}

.headContainer {
    display: flex;
    flex-direction: column;
    justify-content: center;
}
</style>