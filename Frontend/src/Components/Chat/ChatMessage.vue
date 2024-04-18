<template>
    <div v-if="senderId != meId" class="container-o">
        <div class="" style="padding-right: 5px;padding-top: 5px;">
            <el-avatar :size="55" src="./src/Images/私信.png" />
        </div>
        <div style="width: 100%;">
            <div style="font-size: medium;font-weight: bold;color:darkgray;">{{ senderName }}</div>
            <div class="message-bubble-o">
                <div class="message-content-o">这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息
                    这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息
                </div>
            </div>
        </div>
    </div>
    <div v-if="senderId == meId" class="container-m">
        <div class="" style="padding-left: 5px;padding-top: 5px;">
            <el-avatar :size="55" src="./src/Images/私信.png" />
        </div>
        <div style="width: 100%;display: flex;flex-direction: column;align-items: end;">
            <div style="font-size: medium;font-weight: bold;color:darkgray;">{{ meName }}</div>
            <div class="message-bubble-m">
                <div class="message-content-m">这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息</div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
    props: {
        senderId: {
            type: Number,
            default: 0,
        },
    },
    data() {
        return {
            headImg: '',
            senderName: '也是中文名',
            content: 'hello hxt!',
            time: '2022.2.2.2',
            meId: 999,
            meName: '中文名',
        }
    },
    methods: {
    }
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