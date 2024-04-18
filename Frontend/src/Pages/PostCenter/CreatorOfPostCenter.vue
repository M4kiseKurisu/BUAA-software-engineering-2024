<template>
  <div style="display: flex;margin-left: 5%;margin-top: 20px;">
    <div style="width: 25%;font-size: larger;">
      创建者:
    </div>
    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
      <ManagerItem></ManagerItem>
    </div>
  </div>
  <div style="display: flex;margin-left: 5%;margin-top: 20px;">
    <div style="width: 25%;font-size: larger;">
      相关教师:
    </div>
    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
      <ManagerItem></ManagerItem>
    </div>
  </div>
  <div style="display: flex;margin-left: 5%;margin-top: 20px;">
    <div style="width: 25%;font-size: larger;">
      相关助教:
    </div>
    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
      <ManagerItem></ManagerItem>
      <ManagerItem></ManagerItem>
    </div>
  </div>
  <div style="display: flex;margin-left: 5%;margin-top: 20px;">
    <div style="width: 25%;font-size: larger;">
      热门作者:
    </div>
    <div style="width:75%; display: grid; grid-template-columns: repeat(3, 1fr);">
      <ManagerItem></ManagerItem>
      <ManagerItem></ManagerItem>
      <ManagerItem></ManagerItem>
      <ManagerItem></ManagerItem>
      <ManagerItem></ManagerItem>
    </div>
  </div>
</template>
<script>

import ManagerItem from "@/Pages/PostCenter/ManagerItem.vue";
export default {
  components:{ManagerItem}
}
</script>
<style>

</style>
