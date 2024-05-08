<template>
  <div>
    <!-- 收藏板块头部信息 -->
    <div class="follow-header-container">
      <div class="follow-header-1">关注用户</div>
      <div class="follow-header-2">
        <div class="search-and-avatar">
          <!-- 搜索框 -->
          <el-input v-model="searchKeyword" placeholder="搜索用户" class="search-box"></el-input>
          <!-- 图标 -->
          <div class="circle-bound-page" @click="searchUser">
            <el-icon color="#bbbbbb" :size="20"><Search /></el-icon>
          </div>
        </div>
      </div>
    </div>
    <div v-if="filteredFollowingList.length === 0" class="no-matching-users">没有匹配的用户</div>
    <div v-else class="follow-cards-container">
      <div class="follow-cards" v-for="item in filteredFollowingList" :key="item.id">
        <FollowingShow
            :username="item.name"
            :signature="item.sign"
            :avatar="item.user_avatar"
            :id="item.user_id"
            :concerns="item.following_count"
            :posts="item.post_count"
        />
      </div>
    </div>
  </div>
</template>

<script>
import FollowingShow from "@/Components/Group/FollowingShow.vue";
import { Search } from "@element-plus/icons-vue";
import axios from "axios";
import FollowingCard from "@/Components/Group/FollowingCardInPersonalInformation.vue";

export default {
  data() {
    return {
      searchKeyword: '', // 搜索关键词
      followingList: [],
      filteredFollowingList: [], // 将 filteredFollowingList 改为普通属性
    }
  },
  components: {
    FollowingCard,
    Search,
    FollowingShow
  },
  computed: {
    followingListSort() {
      //分离关注用户的前四个内容
      return (this.followingList.length === 0) ? null : this.followingList.slice(0, 4);
    }
  },
  methods: {
    // 获取搜索结果
    searchUser() {
      const keyword = this.searchKeyword.trim().toLowerCase(); // 去除首尾空格并转换为小写
      if (!keyword) {
        // 如果搜索关键词为空，则返回全部用户列表
        this.filteredFollowingList = this.followingList;
      } else {
        // 使用 filter 方法根据关键词过滤用户列表
        this.filteredFollowingList = this.followingList.filter(user => {
          // 这里根据需要修改过滤条件，比如搜索用户名、用户ID等
          return user.name?.toLowerCase().includes(keyword);
        });
        console.log(keyword)
        console.log(this.filteredFollowingList)
      }
    },
    // 从后端获取关注用户列表
    getFollowingList() {
      axios({
        method: "GET",
        url: "/api/user/following"
      }).then((result) => {
        console.log(result);
        this.followingList = result.data.user;
        this.filteredFollowingList = result.data.user; // 初始化 filteredFollowingList
      })
    }
  },
  mounted() {
    // 初始化页面时获取关注用户列表
    this.getFollowingList();
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
.circle-bound-page {
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

.circle-bound-page:hover {
  background-color: #cccccc; /* 按钮的背景色变为灰色 */
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

.no-matching-users{
  margin-top: 40px;
  display: flex;
  justify-content: center;
  margin-bottom: 40px ;
  font-size: 20px;
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
