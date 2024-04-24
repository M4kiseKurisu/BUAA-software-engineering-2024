<template>
  <div>
    <!-- 收藏板块头部信息 -->
    <div class="follow-header-container">
      <div class="follow-header-1">关注用户</div>
      <div class="follow-header-2">

        <div class="search-and-avatar">
          <!-- 搜索框 -->
          <el-input placeholder="搜索用户" class="search-box"></el-input>
          <!-- 图标 -->
          <div class="circle-bound">
            <el-icon color="#bbbbbb" :size="20"><Search /></el-icon>
          </div>
        </div>

      </div>
    </div>
    <div class="follow-cards-container">
      <div class="follow-cards" v-for="item in followingListSort">
        <FollowingShow
            :username="item.username"
            :signature="item.signature"
            :avatar="item.avatar"
            :concerns="item.concerns"
            :posts="item.posts"
        />
      </div>
    </div>

  </div>

</template>


<script>

import FollowingShow from "@/Components/Group/FollowingShow.vue";
import {Search} from "@element-plus/icons-vue";
import axios from "axios";
export default {
  data(){
    return{
      followingList: [
        {
          //关注用户1
          username: "M4kiseKurisu1",
          signature: "好好做好软工作业是命运石之门的选择！",
          avatar: "./src/Images/testAvatar.jpg",
          concerns:"a",
          posts:"b",
        },
        {
          //关注用户2
          username: "M4kiseKurisu2",
          signature: "好好做好软工作业是命运石之门的选择！",
          avatar: "./src/Images/testAvatar.jpg",
          concerns:"a",
          posts:"b",
        },
        {
          //关注用户3
          username: "M4kiseKurisu3",
          signature: "好好做好软工作业是命运石之门的选择！",
          avatar: "./src/Images/testAvatar.jpg",
          concerns:"a",
          posts:"b",
        },
        {
          //关注用户4
          username: "M4kiseKurisu4",
          signature: "好好做好软工作业是命运石之门的选择！",
          avatar: "./src/Images/testAvatar.jpg",
          concerns:"a",
          posts:"b",
        },
      ],
    }
  },
  components:{
    Search,
    FollowingShow
  },
  computed:{
    followingListSort() {
      //分离关注用户的前四个内容
      return (this.followingList.length === 0) ? null : this.followingList.slice(0, 4);
    },
  },
  mounted() {
    axios({
      method: "GET",
      url: "/api/user/following"
    }).then((result) => {
      console.log(result);
      this.followingList = result.data.user;
    })
  }
}
</script>
<style scoped>
/* 上边栏最右侧搜索框和头像的容器 */
.search-and-avatar {
  display: flex;
  align-items: center;
  margin-top: 15px;
}
.follow-cards-container{
  display: flex;
  justify-content: center;
  flex-wrap: wrap; /* 换行 */
}
.follow-cards{
  margin-top: 30px;
  width: 500px;
  justify-content: center;
  display: block;
}

/* 搜索按钮的圆形边框样式 */
/* 图标容器样式 */
.circle-bound {
  width: 32px;
  height: 32px;
  border: 1px solid #f2f3f5;
  border-radius: 16px;
//margin-top: 14px;
  margin-right: 16px;
  justify-content: center;
  align-items: center;
  display: flex;
}


.follow-header-2 {
  margin-right: 0px;
}


.follow-header-container {
  height: 88px;
  margin-left: -17px;
  margin-right: -17px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 0.5px solid #000; /* 黑色边框线 */
}

/* 模块标题样式 */
.follow-header-1 {
  font-size: 24px;
  color: #101010;
  font-weight: bold;
  height: 21px;
  margin-left: 30px;
//margin-top: 15px;

}



</style>
