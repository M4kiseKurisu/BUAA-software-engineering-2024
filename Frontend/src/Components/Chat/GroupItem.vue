<template>
    <div class="personContainer">
        <el-avatar :size="70" :src="groupHead" shape="square" />
        <span style="font-size: x-large;font-weight: bold;padding-left: 10px;">
            {{ groupName }}
        </span>
        <div style="margin-left: auto;">
            <el-button text @click="giveGroupId" style="font-size: 1em;"> 去聊天</el-button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    data() {
        return {
            groupId: 1,
            groupHead: './src/Images/testAvatar.jpg',
            groupName: 'fyb',
        }
    },
    methods:{
        giveGroupId(){
            console.log(this.groupId);
            this.$emit('getGroupId',this.groupId);
        }
    }
}
</script>

<style scoped>
.personContainer {
    width: 100%;
    display: flex;
    align-items: center;
    background-color: rgb(240, 247, 252);
    margin-bottom: 5px;
}
</style>