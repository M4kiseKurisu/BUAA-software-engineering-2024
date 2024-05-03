<template>
    <div style="width: 100%; border: 1px solid #e5e6eb; border-radius: 2px;" class="flex-layout">
        <div style="width: 36%; aspect-ratio: 1/1;">
            <el-image style="width: 80%; height: 80%; margin-top: 10%; margin-left: 10%;" :src="this.school_avatar" fit="fit" />
        </div>

        <div style="margin-left: 5%;">
            <div class="school-font" style="margin-top: 26%;">{{ this.school_name }}</div>
            <button style="border: none; background-color: white; margin-top: 5%;" class="goto-font">
                点击查看学校详情
            </button>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            school_avatar: "./src/Images/buaa-avatar.jpg",
            school_name: "北京航空航天大学",
        }
    }
}
</script>

<style scoped>
.flex-layout {
    display: flex;
}

.school-font {
    font-size: 28px;
    color: #101010;
}

.goto-font {
    font-size: 18px;
    color: #165dff;
}
</style>