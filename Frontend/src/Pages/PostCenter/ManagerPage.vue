<template>
  <div class="img-container">
    <div class="img-container-it">
      <img class="img-of-creator" :src="user_avatar" alt="name">
    </div>
    <div class="name-container">
      <span><a :href="user_http" class="link">{{name}}</a></span>
    </div>
  </div>
</template>
<script>
import axios from "axios";

export default {
  props: ["id"],
  data(){
    return{
      name:"",
      user_avatar:"",
      user_http:""
    }
  },
  mounted() {
    axios({
      method: "GET",
      url: "api/user/social/simple",
      params: { id: this.id },
    }).then((result) => {
      console.log(result);
      this.name=result.data.name;
      this.user_avatar=result.data.user_avatar
      this.user_http="http://122.9.45.57/#/MainPage/Course_Center/ShowPersonalInformation/"+this.id
    })
  }
}
</script>
<style>
.img-container {
  display: block;
  /* justify-content: center; */
  /* background: #8c939d; */
  width: 100%;
}

.img-container-it {
  display: flex;
  justify-content: center;
}

.img-of-creator {
  max-width: 80%;
  aspect-ratio: 1/1;
  border: 1px solid darkgray;
  border-radius: 5%;
}

.name-container {
  display: flex;
  justify-content: center;
  font-size: 16px;
}
.link {
  text-decoration: none; /* 移除下划线 */
  color: blue; /* 设置链接颜色 */
}

.link:hover {
  text-decoration: underline; /* 鼠标悬停时显示下划线 */
}
</style>
