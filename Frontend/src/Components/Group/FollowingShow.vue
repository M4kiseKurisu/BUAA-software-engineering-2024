<template>
  <!-- 关注用户单元 -->
  <div class="following-container-page">
    <div class="following-group-container">
      <div class="following-left-container">

        <!-- 关注用户图片 -->
        <div class="following-avatar"  @click="toInformationShow(this.id)">
          <el-avatar :size="48" :src="avatar"/>
        </div>

        <!-- 关注用户信息 -->
        <div class="following-information-container">
          <div class="name-and-counts">
            <div class="following-name">{{ this.username }}</div>
            <div class="following-counts">
              <span>{{ this.concerns }}个关注</span>
              <span>{{ this.posts }}个帖子</span>
            </div>
          </div>

          <div class="following-signature">{{ this.signature }}</div>
        </div>

      </div>

      <!-- 取消关注按钮 -->
      <button class="cancel-following-button-page" @click="notFollow">
        <div class="button-font" >取消关注</div>
      </button>

    </div>

    <div class="following-seperator" />
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: ["username", "signature", "avatar","concerns","posts","id"],
  data(){
    return{
      avatarSrc:" "
    }
  },
  methods: {
    notFollow() {
      // 取消关注作者
      let content = {
        unfollow_id: this.id,
      }
      axios({
        method: "POST",
        url: "/api/user/unfollow",
        data: content,
      }).then((result) => {
        console.log(result);
        if (result.data.success) {
          this.$message({
            showClose: true,
            message: '作者取消关注成功！',
            type: 'success',
          });
          location.reload();
          this.isFollowingWriter = false;
        }
      })

    },
    toInformationShow(id) {
      this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + id});
    }
  }
}
</script>

<style scoped>
.following-container-page {
  width: 100%;
  margin-bottom: 20px;
}

.following-group-container {
  display: flex;
  justify-content: space-between;
}

.following-left-container {
  display: flex;
}

.following-avatar {
  margin-left: 4px;
}
.name-and-counts{
  display: flex;

}
.following-information-container {
  margin-left: 10px;
}
.following-counts {
  font-size: 12px;
  height: 21px;
  color: #86909c;
  margin-top: 4px;
}

.following-counts span {
  margin-left: 10px; /* 调整关注数和贴子数之间的间距 */
}
.following-name {
  font-size: 14px;
  height: 21px;
  margin-top: 3px;
  overflow: hidden;
  font-weight: bold;
  color: #101010;
}

.following-signature {
  font-size: 12px;
  height: 21px;
  overflow: hidden;
  color: #86909c;
}

.following-seperator {
  border-top: 1px solid #bbbbbb !important;
  margin-top: 7px !important;
}

.cancel-following-button-page {
  border: 0px;
  width: 80px;
  height: 30px;
  border-radius: 4px;
  color: #efefef;
  margin-top: 12px;
  margin-right: 17px;
}

.button-font {
  font-size: 13px;
  color: #86909c;
}
/* 鼠标移上去变灰的效果 */
.cancel-following-button-page:hover {
  background-color: #cccccc; /* 按钮的背景色变为灰色 */
}
</style>
