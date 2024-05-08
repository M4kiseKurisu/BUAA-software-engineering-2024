<template>
  <!-- 个人信息中的收藏帖子单元 -->
  <el-card class="card-container-page" >
    <div class="card-content">
      <!--
      <div class="image-container">
        <img :src="image" alt="图片" sizes="100vw">
      </div>
    -->

      <div class="content-details">
        <div class="first-line-container">

            <div class="title">{{ title }}</div>
          <div class="tags-star">
            <!-- 动态渲染标签 -->
            <div class="tags">
              <span class="tag" v-for="(tag, index) in tags" :key="index">{{ tag }}</span>
            </div>


            <div class="star" @click="disFavorate">
              <el-icon :size="24" color="#fedf00"><StarFilled/></el-icon>
            </div>
          </div>

        </div>

        <!-- 收藏帖子正文 -->
        <div class="content">{{ content }}</div>

        <!-- 卡片最下面一行的内容 -->
        <div class="last-line-information">
          <!-- 收藏帖子作者头像，昵称 -->
          <div class="avatar-writername">
            <el-avatar :size="24" :src="avatarSrc"/>
            <div class="writername">{{ writerName }}</div>
          </div>

          <!-- 点击跳转至收藏帖子 -->
          <div class="content-link" @click="jump">>点击进入</div>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script>
import { StarFilled } from '@element-plus/icons-vue'
import axios from "axios";

export default {
  components: {
    StarFilled,
  },
  data(){
    return{
      avatarSrc:'',
    }

  },
  props: ["writerId","title","image", "content",  "writerName","tags","post_id"],
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
      this.$router.push({ path: "/MainPage/Course_Center/PostPage/" + this.post_id});
    }
  }
}
</script>

<style scoped>
/* 定义卡片大小和边缘 */
.card-container-page {
  height: auto;
  border: 10px solid #e5e6eb;
  border-radius: 2px;
  overflow: hidden;

}

/* 卡片内容布局 */
.card-content {
  display: flex;
}

/* 左边图片容器样式 */
.image-container {
  margin-right: 12px;
}

/* 图片样式 */
.image-container img {
  width: 80px; /* 自定义图片宽度 */
  height: 80px; /* 自定义图片高度 */
  object-fit: cover; /* 保持图片比例并填充容器 */
}
/* 定义标签区域样式 */
.tags {
  display: flex;
  margin-top: 4px;
}

/* 定义标签样式 */
.tag {
  margin-right: 8px;
  padding: 4px 8px;
  background-color: #f0f0f0;
  border-radius: 4px;
  font-size: 12px;
  color: #165dff; /* 蓝色 */

}
.tag::before {
  content: "#"; /* 在标签前添加 # */
}
/* 收藏帖子详情容器 */
.content-details {
  flex-grow: 1; /* 填充剩余空间 */
  min-width: 0;
  width: 0;
}

/* 卡片第一行样式 */
.first-line-container {
  display: flex;
  justify-content: space-between;
  margin-top: -6px;
  margin-left: -1px;
}
.tags-star{
  display: flex;
  justify-content: space-between;
}
/* 定义文章标题样式，注意抵消el-card自带的20px padding */
.title {
  font-size: 16px;
  height: 24px;
  color: #101010;
}

/* 定义星星收藏按钮的位置和样式 */
.star {
  margin-top: 3px;
  margin-right: -5px;
}

/* 定义文章简介样式 */
.content {
  font-size: 14px;
  color: #86909c;
  width: 300px;
  height: auto;
  line-height: 1.5;
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
  font-size: 14px;
  margin-top: 4px;
}

/* 定义跳转文字的样式 */
.content-link {
  font-size: 14px;
  color: #165dff;
  margin-top: 18px;
  margin-right: -10px;
}
</style>
