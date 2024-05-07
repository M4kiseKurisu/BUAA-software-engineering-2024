<template>
    <!-- 学习团体单元 -->
    <div class="group-container">
        <div class="study-group-container">
            <div class="left-container">
                
                <!-- 学习团体图片 -->
                <div class="group-avatar">
                    <el-avatar :size="48" :src="avatar"/>
                </div>  

                <!-- 学习团体信息 -->
                <div class="group-information-container">
                    <div class="group-name">{{ this.title }}</div>
                    <div class="group-people-amount">共{{ this.amount }}人</div>
                </div>
                
            </div>

            <!-- "点击进入"字样 -->
            <button style="border: none; background-color: white;" class="group-information-link" @click="toGroup">>点击进入</button>
        </div>

        <div class="group-seperator" />
    </div>
</template>

<script>
export default {
    props: ["title", "amount", "avatar", "id"],
    methods: {
        toGroup() {
            this.$router.push({ name: 'ChatCenter', params: { personId: -1, groupId: this.id }});
        }
    }
}
</script>

<style scoped>
/* 学习团体单元位置 */
.group-container {
    margin-bottom: 13px;
}

.study-group-container {
    display: flex;
    justify-content: space-between;
}

/* 左侧元素位置 */
.left-container {
    display: flex;
} 

.group-avatar {
    margin-left: 64px;
}

.group-information-container {
    margin-left: 10px;
}

.group-name {
    font-size: 14px;
    height: 21px;
    margin-top: 3px;
    overflow: hidden;
    font-weight: bold;
    color: #101010;
}

.group-people-amount {
    font-size: 12px;
    height: 21px;
    overflow: hidden;
    color: #86909c;
}

.group-information-link {
    color: #165dff;
    font-size: 12px;
    margin-top: 35px;
    width: 64px;
    height: 20px;
    margin-right: 3px;
}

.group-seperator {
    border-top: 1px solid #bbbbbb !important;
    margin-top: 5px !important;
    margin-left: 52px !important;
}
</style>