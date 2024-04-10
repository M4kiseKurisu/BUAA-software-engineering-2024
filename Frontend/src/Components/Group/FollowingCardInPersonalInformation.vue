<template>
    <!-- 关注用户单元 -->
    <div class="following-container">
        <div class="following-group-container">
            <div class="following-left-container">
                
                <!-- 关注用户图片 -->
                <div class="following-avatar">
                    <el-avatar :size="48" :src="avatar"/>
                </div>  

                <!-- 关注用户信息 -->
                <div class="following-information-container">
                    <div class="following-name">{{ this.username }}</div>
                    <div class="following-signature">{{ this.signature }}</div>
                </div>
                
            </div>

            <!-- 取消关注按钮 -->
            <button class="cancel-following-button">
                <div class="button-font">取消关注</div>
            </button>

        </div>

        <div class="following-seperator" />
    </div>
</template>

<script>
export default {
    props: ["username", "signature", "avatar"],
}
</script>

<style scoped>
.following-container {
    margin-bottom: 20px;
}

.following-group-container {
    display: flex;
    justify-content: space-between;
}

.following-left-container {
    display: flex;
}

.following-avatar {
    margin-left: 4px;
}

.following-information-container {
    margin-left: 10px;
}

.following-name {
    font-size: 14px;
    height: 21px;
    margin-top: 3px;
    overflow: hidden;
    font-weight: bold;
    color: #101010;
}

.following-signature {
    font-size: 12px;
    height: 21px;
    overflow: hidden;
    color: #86909c;
}

.following-seperator {
    border-top: 1px solid #bbbbbb !important;
    margin-top: 7px !important;
}

.cancel-following-button {
    border: 0px;
    width: 80px;
    height: 30px;
    border-radius: 4px;
    color: #efefef;
    margin-top: 12px;
    margin-right: 17px;
}

.button-font {
    font-size: 13px;
    color: #86909c;
}
</style>