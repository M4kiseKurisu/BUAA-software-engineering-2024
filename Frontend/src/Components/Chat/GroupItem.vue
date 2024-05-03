<template>
    <div class="personContainer">
        <div style="height: 100%;align-items: center;justify-content: center;display: flex;width: 80px;">
            <el-avatar :size="70" :src="groupHead" shape="square" />
        </div>

        <div style="height: 100%;width: calc(100% - 80px);">
            <div style="width: 100%;height: 50%;display: flex;align-items: center;">
                <div style="font-size: 1.3em;padding-left: 5px;">
                    {{ groupName }}
                </div>
                <div style="margin-left: auto;">
                    <el-button text @click = "showQuit = true">退出团体</el-button>
                </div>
            </div>
            <div style="width: 100%;height: 50%;display: flex;align-items: center;">
                <div style="font-size: 1em;padding-left: 5px;color:dimgray;">
                    团体人数:{{ groupPersonNum }}/{{ groupCapacity }}
                </div>
                <div style="margin-left: auto;">
                    <el-button  type = "primary" plain style="margin-right: 5px;">去聊天</el-button>
                </div>
            </div>
        </div>
        <el-dialog v-model="showQuit" title="退出提醒" width="600">
            <div style="width: 100%;height: 100px;align-items: center;justify-content: center;display: flex;">
                <span style="font-size: x-large;color:coral;">是否确定退出团体{{groupName}}</span>
            </div>
            <div style="width: 100%;display: flex;">
                <div style="width: 50%;align-items: center;justify-content: center;display: flex;">
                    <el-button  type = "primary" @click = "showQuit = flase">返回</el-button>
                </div>
                <div style="width: 50%;align-items: center;justify-content: center;display: flex;">
                    <el-button type = "primary" >确定</el-button>
                </div>
            </div>
        </el-dialog>
        <!-- <div style="margin-left: auto;">
            <el-button text @click="giveGroupId" style="font-size: 1em;"> 去聊天</el-button>
        </div> -->
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
            groupPersonNum : 50,
            groupCapacity : 200,
            showQuit: false,
        }
    },
    methods: {
        giveGroupId() {
            console.log(this.groupId);
            this.$emit('getGroupId', this.groupId);
        }
    }
}
</script>

<style scoped>
.personContainer {
    width: 100%;
    height: 85px;
    display: flex;
    align-items: center;
    background-color: rgb(240, 247, 252);
    margin-bottom: 5px;
    min-width: 300px;
}
.personContainer:hover {
    box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
}
</style>