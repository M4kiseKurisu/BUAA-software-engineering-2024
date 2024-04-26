<template>
  <div class="creator-of-post-center-container">
    <el-dialog v-model="show_dialog" :visible.sync="show_dialog" title="提示" width="20%" top="20%">
      <div style="display: flex;justify-content: center">
        <span style="font-size: 20px">暂未完成</span>
      </div>
    </el-dialog>
    <div class="creator-container">
      <div style="width: 15%;font-size: larger;">
        创建者:
      </div>
      <div style="width: 85%; display: grid; grid-template-columns: repeat(5, 1fr);">
        <ManagerPage></ManagerPage>
      </div>
    </div>
    <div style="display: flex;margin-left: 2%;margin-top: 20px;">
    <div style="width: 15%;font-size: larger;">
      相关教师:
    </div>
    <div style="width:85%; display: grid; grid-template-columns: repeat(5, 1fr);">
      <ManagerPage></ManagerPage>
    </div>
  </div>
    <div style="display: flex;margin-left: 2%;margin-top: 20px;">
      <div style="width: 15%;font-size: larger;">
        相关助教:
      </div>
      <div style="width:85%; display: grid; grid-template-columns: repeat(5, 1fr);">
        <ManagerPage></ManagerPage>
        <ManagerPage></ManagerPage>
      </div>
    </div>
    <div style="display: flex;margin-left: 2%;margin-top: 20px;">
      <div style="width: 15%;font-size: larger;">
        热门作者:
      </div>
      <div style="width:85%; display: grid; grid-template-columns: repeat(5, 1fr);">
        <ManagerPage></ManagerPage>
        <ManagerPage></ManagerPage>
        <ManagerPage></ManagerPage>
        <ManagerPage></ManagerPage>
        <ManagerPage></ManagerPage>
        <ManagerPage></ManagerPage>
      </div>
    </div>
    <div class="button-of-it-container">
      <button class="apply-for-assistant" @click="show_dialog=true">申请成为助教</button>
    </div>
  </div>


</template>
<script>

import ManagerItem from "@/Pages/PostCenter/ManagerItem.vue";
import MainPostPage from "@/Pages/PostPages/MainPostPage.vue";
import ManagerPage from "@/Pages/PostCenter/ManagerPage.vue";
export default {
  components:{ManagerPage, MainPostPage, ManagerItem},
  data(){
    return{
      show_dialog:false
    }
  },
  methods:{

  }
}
</script>
<style>
.creator-of-post-center-container{

}
.creator-container{
  display: flex;
  margin-left: 2%;
}
.button-of-it-container{
  display: flex;
  justify-content: center;
}
.apply-for-assistant {
  background-color: #007bff; /* 蓝色背景 */
  color: #ffffff; /* 白色文字 */
  border: none; /* 移除边框 */
  border-radius: 5px; /* 圆角 */
  padding: 8px 16px; /* 添加内边距 */
  font-size: 16px; /* 字体大小 */
  cursor: pointer; /* 光标样式为指针 */
  height: 60%;
  margin-top: 7.5%;
}

.apply-for-assistant:hover {
  background-color: #0056b3; /* 鼠标悬停时变暗一点 */
}
</style>
