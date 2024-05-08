<template>
    <div class="personItemContainer" @click="givePersonId">
        <div style="height: 100%;align-items: center;justify-content: center;display: flex;width: 80px;">
            <el-avatar :size="70" :src="personAvatar" shape="square" />
        </div>
        <div style="height: 100%;width: calc(100% - 80px);">
            <div style="width: 100%;height: 50%;display: flex;align-items: center;">
                <div style="font-size: 1.3em;padding-left: 5px;">
                    {{ personName }}
                </div>
                <div style="margin-left: auto;">
                    <el-button text @click="goToOtherPage" type="primary">查看主页</el-button>
                </div>
            </div>
            <div style="width: 100%;height: 50%;display: flex;align-items: center;">
                <div style="font-size: 1em;padding-left: 5px;padding-bottom: 10px;color:dimgray;
                            white-space: nowrap;overflow: hidden;text-overflow: ellipsis;width: 85%;">
                    {{ this.lastMessageContent }}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props: {
        chatItemInfo: {
            type: Object,
            default: null,
        }
    },
    data() {
        return {
            selfId: JSON.parse(sessionStorage.getItem("id")),
            personId: 1,
            personAvatar: './src/Images/testAvatar.jpg',
            personName: 'cjh',
            lastMessageContent: '上一条消息上一条消息上一条消息',
        }
    },
    methods: {
        getPersonInfo() {
            axios({
                method: "GET",
                url: 'api/user/social/simple',
                params: {
                    id: this.personId,
                }
            }).then((result) => {
                this.personName = result.data.name;
                this.personAvatar = result.data.user_avatar;
            })
        },
        givePersonId() {
            console.log(this.personId);
            this.$emit('getPersonId', this.personId);
        },
        goToOtherPage() {
            this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.personId});
        }
    },
    created() {
        console.log(this.chatItemInfo);
        if (this.chatItemInfo != null) {
            if (this.selfId != this.chatItemInfo.sender_id) {
                this.personId = this.chatItemInfo.sender_id;
            } else {
                this.personId = this.chatItemInfo.receiver_id;
            }
            this.lastMessageContent = this.chatItemInfo.last_message_content;
            this.getPersonInfo();
        }
    }
}
</script>

<style scoped>
.personItemContainer {
    width: 100%;
    height: 85px;
    display: flex;
    align-items: center;
    background-color: rgb(240, 247, 252);
    margin-bottom: 5px;
    min-width: 300px;
}

.personItemContainer:hover {
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
}
</style>