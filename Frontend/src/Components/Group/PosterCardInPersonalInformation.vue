<template>
    <!-- 个人打卡单元 -->
    <div class="poster-container">
        <!-- 打卡日期显示 -->
        <div class="poster-time-font">{{ this.month }}月</div>

        <!-- 打卡照片 -->
        <div class="poster-pictures"> 
            <!-- 第一行打卡照片 -->
            <div class="pictures-row" >
                <el-image v-for="item in pictures1"
                    style="width: 74px; height: 74px; margin-right: 10px" :src="item" :fit="fit" />
            </div>

            <!-- 第二行打卡照片 -->
            <div class="pictures-row" >
                <el-image v-for="item in pictures2"
                    style="width: 74px; height: 74px; margin-right: 10px" :src="item" :fit="fit" />
            </div>


            <!-- 第三行打卡照片 -->
            <div class="pictures-row" >
                <el-image v-for="item in pictures3"
                    style="width: 74px; height: 74px; margin-right: 10px" :src="item" :fit="fit" />
            </div>

        </div>
        
    </div>

    
</template>

<script>
export default {
    props: ["month", "pictures"],
    computed: {
        pictures1() {
            //分离个人打卡中的前三个照片
            return (this.pictures.length === 0) ? null : this.pictures.slice(0, 3);
        },
        pictures2() {
            //分离收藏帖子列表的四五六个照片
            return (this.pictures.length <= 3) ? null : this.pictures.slice(3, 6);
        },
        pictures3() {
            //分离收藏帖子列表的七八九个照片
            return (this.pictures.length <= 6) ? null : this.pictures.slice(6, 9);
        },
    }
}
</script>

<style scoped>
.poster-container {
    display: flex;
    margin-bottom: 15px;
}

.poster-time-font {
    font-size: 20px;
    color: #000000;
    width: 52px;
}

.pictures-row {
    margin-bottom: 9px;
    display: flex;
}
</style>