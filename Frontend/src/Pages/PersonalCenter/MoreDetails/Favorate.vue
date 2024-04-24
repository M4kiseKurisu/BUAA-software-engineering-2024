<template>
  <div class="favorates-container-page">
      <div class="favorates-header-container-page">
        <div class="favorates-header-1-page">收藏帖子</div>
        <div class="favorates-header-2-page">

          <div class="search-and-avatar-page">
            <!-- Tag 选择框 -->
            <el-select v-model="selectedTag" placeholder="选择标签" class="el-select">
              <el-option
                  v-for="item in tagOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
              </el-option>
            </el-select>
            <!-- 搜索框 -->
            <el-input placeholder="搜索帖子" class="search-box"></el-input>
            <!-- 图标 -->
            <div class="circle-bound">
              <el-icon color="#bbbbbb" :size="20"><Search /></el-icon>
            </div>
          </div>

        </div>
      </div>
      <!-- 收藏板块正文信息 -->
      <div class="favorates-cards-container">
        <!-- 循环展示帖子 -->

        <FavorateShow class="singe-show"
                      v-for="(item, index) in favorateList"
                      :key="index"
                      :title="item.title"
                      :content="item.content"
                      :image="item.image"
                      :avatarSrc="item.avatarSrc"
                      :writerName="item.writerName"
                      :tags="item.tags"
        />
        <el-pagination background layout="prev, pager, next" :total="100" page-size="10" style="justify-content: center"></el-pagination>


      </div>
    <!-- 收藏板块头部信息 -->

    <!-- 分页 -->
  </div>
</template>


<script>

import FavorateShow from "@/Components/Group/FavorateShow.vue";
import {Search} from "@element-plus/icons-vue";
import axios from "axios";
export default {
  components:{
    Search,
    FavorateShow
  },
  data(){
    return{
      selectedTag: '', // 存储选择的标签值
      tagOptions: [    // 标签选项
        { value: 'tag1', label: '标签1' },
        { value: 'tag2', label: '标签2' },
        // 其他标签选项...
      ],
      favorateList: [
        {
          //收藏帖子1
          title: "收藏帖子A标题",
          image :"./src/images/postImage.jpg",
          content: "收藏帖子A简介：ABCDABCDABCDABCD",
          avatarSrc: "./src/Images/testAvatar.jpg",
          writerName: "MakiseKurisuA",
          tags:["标签一","标签二"]
        },
        {
          //收藏帖子2
          title: "收藏帖子B标题",
          image :"./src/images/postImage.jpg",
          content: "收藏帖子B简介：ABCDABCDABCDABCD",
          avatarSrc: "./src/Images/testAvatar.jpg",
          writerName: "MakiseKurisuB",
          tags:["标签一","标签二"]
        },
        {
          //收藏帖子3
          title: "收藏帖子C标题",
          image :"./src/images/postImage.jpg",
          content: "收藏帖子C简介：ABCDABCDABCDABCD",
          avatarSrc: "./src/Images/testAvatar.jpg",
          writerName: "MakiseKurisuC",
          tags:["标签一","标签二"]
        },
        {
          //收藏帖子4
          title: "收藏帖子D标题",
          image :"./src/images/postImage.jpg",
          content: "收藏帖子D简介：ABCDABCDABCDABCD",
          avatarSrc: "./src/Images/testAvatar.jpg",
          writerName: "MakiseKurisuD",
          tags:["标签一","标签二"]
        }
      ]
    }
  },
  mounted() {
    axios({
      method: "GET",
      url: "/api/user/favorites"
    }).then((result) => {
      console.log(result);
      this.favorateList = result.data.posts;
    })
  },
  computed: {

  },
  methods: {

  }
}
</script>

<style>
/* 上边栏最右侧搜索框和头像的容器 */
.search-and-avatar-page {
  display: flex;
  align-items: center;
}

/* 标签选择框样式 */
.el-select {
  margin-right: 8px; /* 调整标签选择框和搜索框之间的间距 */
}
.favorates-container-page {
  //display: flex;
  /* 设置子元素可选择靠左还是靠右排列 */
  //justify-content: space-between;
  //align-items: center;
  //margin-left: -16px;
  //margin-right: -16px;
  //width: 100%;
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


.favorates-header-2-page {
  margin-top: 10px;
  margin-right: 15px;
}

/* 收藏帖子模块位置 */
.favorates-container {
  //width: 800px;
  display: block;
  //width: 69%;
  justify-content: center;
}
/* 收藏帖子模块标题位置 */
.favorates-header-container-page {

  height: 88px;

  margin-left: -16px;
  margin-right: -16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 0.5px solid #000; /* 黑色边框线 */
}

/* 收藏帖子模块标题样式 */
.favorates-header-1-page {
  font-size: 24px;
  color: #101010;
  font-weight: bold;
  height: 21px;
  //margin-top: 15px;
  margin-left: 45px;
}


/* 收藏帖子正文部分样式 */
.favorates-cards-container {
  width: 100%;
  //margin-left: 40px;
}

.no-favorate-card-tip-page {
  margin-top: 30px;
  font-size: 22px;
}
</style>
