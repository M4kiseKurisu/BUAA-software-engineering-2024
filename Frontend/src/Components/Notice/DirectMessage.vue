<template>
    <div class="container">
        <div class="headContainer"><el-avatar :size="70" src="./src/Images/私信.png" />
        </div>
        <div style="margin-left: 10px;">
            <div class="senderContainer">
                <span style="font-size: large;font-weight: bolder;color: black;">{{ senderName }}</span>
                <span style="margin-left: 5px;">给你发送了私信</span>
                <span style="margin-left: 10px;">{{ time }}</span>
            </div>
            <div class="contentContainer">
                <span style="font-size: x-large;">
                    {{ content }}
                </span>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            headImg: '',
            senderId: '',
            senderName: 'huazhi',
            content: 'hello hxt!',
            time: '2022.2.2.2'
        }
    },
    methods: {

    }
}
</script>

<style>
.container {
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
    display: flex;
    /* align-items: center; */
    height: 50%;
}
</style>