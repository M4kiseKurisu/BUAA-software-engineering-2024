<template>
    <!-- 个人信息中的收藏帖子单元 -->
    <el-card class="card-container" shadow="never">

        <div class="first-line-container">
            <!-- 收藏帖子标题 -->
            <div class="title">{{ title }}</div>

            <!-- 收藏按钮 -->
            <button class="star" @click="disFavorate">
                <el-icon :size="24" color="#fedf00"><StarFilled/></el-icon>
            </button>   
        </div>
        
        <!-- 收藏帖子正文 -->
        <div class="content">{{ content }}</div>

        <!-- 卡片最下面一行的内容 -->
        <div class="last-line-information">
            <!-- 收藏帖子作者头像，昵称 -->
            <button class="avatar-writername" style="border: none; background-color: white;" @click="toDetail">
                <el-avatar :size="24" :src="avatarSrc"/>
                <div class="writername">{{ writerName }}</div>
            </button>

            <!-- 点击跳转至收藏帖子 -->
            <button class="content-link" @click="jump">>点击进入</button>
        </div>
        
    </el-card>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { StarFilled } from '@element-plus/icons-vue'

export default {
    data() {
        return {
            avatarSrc: "",
        }
    },
    components: {
        StarFilled, 
    },
    props: ["postId", "title", "content", "writerId", "writerName"],
    mounted() {
        // 获取作者头像信息
        //console.log(this.writerId);
        axios({
            method: "GET",
            url: "/api/user/head",
            params: {
                user_id: this.writerId
            }
        }).then((result) => {
            //console.log(result.data.info);
            this.avatarSrc = result.data.info;
        })
    },
    methods: {
        disFavorate() {
            // 帖子取消收藏
            let content = {
                post_id: this.postId,
                user_id: JSON.parse(sessionStorage.getItem("id")),
            }
            axios({
                method: "POST",
                url: "/api/posts/unfavorite",
                data: content,
            }).then((result) => {
                //console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '帖子取消收藏成功！',
                        type: 'success',
                    });
                    location.reload();
                }
            })
        },
        jump() {
            this.$router.push({ path: "/MainPage/Course_Center/PostPage/" + this.postId});
        },
        toDetail() {
            this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.writerId});
        }
    }
}
</script>

<style sccoped>
/* 定义卡片大小和边缘 */
.card-container {
    height: 126px;
    border: 1px solid #e5e6eb;
    border-radius: 2px;
}

/* 卡片第一行样式 */
.first-line-container {
    display: flex;
    justify-content: space-between;
    margin-top: -6px;
    margin-left: -1px;
}

/* 定义文章标题样式，注意抵消el-card自带的20px padding */
.title {
    font-size: 14px !important;
    height: 20px;
    color: #101010;
}

/* 定义星星收藏按钮的位置和样式 */
.star {
    margin-top: 3px;
    margin-right: -5px;
    border: none;
    background-color: white;
}

/* 定义文章简介样式 */
.content {
    font-size: 14px;
    color: #86909c;
    width: 200px;
    height: 40px;
    line-height: 1.42;
    overflow: hidden;
}

/* 定义最后一行信息位置和排列 */
.last-line-information {
    display: flex;
    justify-content: space-between;
}

/* 定义作者头像样式 */
.avatar-writername {
    margin-top: 8px;
    display: flex;
}

/* 定义作者昵称信息样式 */
.writername {
    margin-left: 10px;
    color: #86909c;
    font-size: 12px;
    margin-top: 4px;
}

/* 定义跳转文字的样式 */
.content-link {
    font-size: 12px;
    color: #165dff;
    margin-top: 18px;
    margin-right: -10px;
    border: none;
    background-color: white;
}
</style>