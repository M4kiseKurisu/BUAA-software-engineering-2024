<template>
  <div class="creator-of-post-center-container">
    <el-dialog v-model="show_dialog" :visible.sync="show_dialog" title="提示" width="20%" top="20%">
      <div style="display: flex;justify-content: center">
        <span style="font-size: 20px">暂未完成</span>
      </div>
    </el-dialog>
    <!--<div class="creator-container">
      <div style="width: 15%;font-size: larger;">
        创建者:
      </div>
      <div style="width: 85%; display: grid; grid-template-columns: repeat(5, 1fr);">
        <ManagerPage></ManagerPage>
      </div>
    </div>-->
    <div style="display: flex;margin-left: 2%;margin-top: 20px;">
    <div style="width: 15%;font-size: larger;">
      相关教师:
    </div>
      <div v-if="teachers.length === 0">
        <span  style="font-size: 16px;">暂无教师信息</span>
      </div>
    <div v-else style="width:85%; display: grid; grid-template-columns: repeat(4, 1fr);">
      <ManagerPage v-for="(teacher, index) in teachers" :key="index" :id="teacher" />
    </div>
  </div>
    <div style="display: flex;margin-left: 2%;margin-top: 20px;">
      <div style="width: 15%;font-size: larger;">
        相关助教:
      </div>
      <div v-if="assistants.length === 0">
        <span  style="font-size: 16px;">暂无助教信息</span>
      </div>
      <div v-else style="width:85%; display: grid; grid-template-columns: repeat(4, 1fr);">
        <ManagerPage v-for="(teacher, index) in assistants" :key="index" :id="teacher" />
      </div>
    </div>
    <!---<div style="display: flex;margin-left: 2%;margin-top: 20px;">
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
    </div>-->

    <div class="button-of-it-container">
      <div class="apply-for-assistant">
        <apply-button :section_id="section_id"></apply-button>
      </div>

    </div>
  </div>


</template>
<script>

import ManagerItem from "@/Pages/PostCenter/ManagerItem.vue";
import MainPostPage from "@/Pages/PostPages/MainPostPage.vue";
import ManagerPage from "@/Pages/PostCenter/ManagerPage.vue";
import axios from "axios";
import applyButton from "@/Components/Tool/ApplyButton.vue";
export default {
  props:['section_id'],
  components:{ManagerPage, MainPostPage, ManagerItem,applyButton},
  data(){
    return{
      show_dialog:false,
      section_data:{},
      teachers:[],
      assistants:[]
    }
  },
  methods:{
    do_it(){
      axios({
        method: "GET",
        url: "api/section/info",
        params: { section_id: this.section_id },
      }).then((result) => {
        console.log(result);
        console.log(this.section_id)
      })
    }
  },
  mounted() {
    axios({
      method: "GET",
      url: "api/section/authority",
      params: {id: this.section_id },
    }).then((result) => {
      this.section_data=result.data;
      this.teachers=result.data.teacher;
      this.assistants=result.data.assistant;
      console.log(this.section_id)
      console.log(result);
    })
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

  margin-top: 4.5%;
}

</style>
