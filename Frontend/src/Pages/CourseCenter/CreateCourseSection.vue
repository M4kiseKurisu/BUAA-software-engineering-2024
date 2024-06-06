<template>
  <div>
    <div class="breadcrumb">
      <BreadcrumbLabel :routeNames="route" />
    </div>
    <div class="content-container">
      <div class="create-cs-container">
        <div class="write-in-cs-inf">
          <div class="create-title-container">
            <span class="create-title">新建课程</span>
          </div>
          <div class="input-container">
            <div class="inf-input">
              <div class="form-group">
                <span class="prompt-before-input">课程名称:</span>
                <div class="name-input-container">
                  <el-input v-model="course.name" placeholder="输入课程名称"></el-input>
                </div>
              </div>
              <div class="form-group">
                <span class="prompt-before-input">课程类型:</span>
                <div class="name-input-container">
                  <el-input v-model="course.type" placeholder="输入课程类型"></el-input>
                </div>
              </div>
              <div class="form-group">
                <span class="prompt-before-input">学分:</span>
                <div style="max-width: 80px">
                  <el-input-number v-model="course.credits" :min="0" :max="99" placeholder="输入学分"></el-input-number>
                </div>
              </div>
              <div class="form-group">
                <span class="prompt-before-input">开课院系:</span>
                <div class="name-input-container">
                  <el-input v-model="course.college" placeholder="输入开课院系"></el-input>
                </div>
              </div>
              <div class="form-group">
                <span class="prompt-before-input">课程容量:</span>
                <el-input-number v-model="course.capacity" :min="0" :max="9999" placeholder="输入容量"></el-input-number>
              </div>

              <div class="form-group">
                <span class="prompt-before-input">课程描述:</span>
                <el-input
                    type="textarea"
                    :rows="3" :autosize="{ minRows: 3 }" v-model="course.description">
                </el-input>
              </div>
              <!--<div class="form-group">
                <span class="prompt-before-input">标签:</span>
                <div class="tag-in-container">
                  <el-tag
                      v-for="tag in dynamicTags"
                      :key="tag"
                      closable
                      :disable-transitions="false"
                      @close="handleClose(tag)"
                  >
                    {{ tag }}
                  </el-tag>
                  <el-input
                      v-if="inputVisible"
                      ref="InputRef"
                      v-model="inputValue"
                      class="w-20"
                      @keyup.enter="handleInputConfirm"
                      @blur="handleInputConfirm"
                      style="width: 100px"
                  />
                  <el-button v-else class="button-new-tag" @click="showInput">
                    + New Tag
                  </el-button>
                </div>
              </div>-->
            </div>
            <!--<div class="png-input">
              <el-upload
                  v-model:file-list="fileList"
                  action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
                  list-type="picture-card"
                  :on-preview="handlePictureCardPreview"
                  :on-remove="handleRemove"
              >
                <el-icon><Plus /></el-icon>
              </el-upload>

              <el-dialog v-model="dialogVisible">
                <img w-full :src="dialogImageUrl" alt="Preview Image" />
              </el-dialog>
            </div>-->
          </div>
          <div style="display: flex;justify-content: center">
            <button @click="handleCreateSection" class="create-submit-button">提交</button>
          </div>

        </div>
      </div>
    </div>

  </div>
</template>
<script>

import {defineComponent} from "vue";
import BreadcrumbLabel from "@/Components/Tool/BreadcrumbLabel.vue";
import { Plus } from '@element-plus/icons-vue'
import axios from "axios";
export default defineComponent({
  components: {BreadcrumbLabel,Plus},

  data() {
    return {
      route: [{name: "学业板块", route: "/MainPage/Course_Center/Personal_Course"},
            {name: "创建课程", route: ""}],
      inputValue: '',
      dynamicTags: ['Tag 1', 'Tag 2', 'Tag 3'],
      inputVisible: false,
      before: 114,
      course:{
        name:"",
        type:"",
        college:"",
        capacity:"114",
        credits: "",
        description:""
      },
      fileList: [
        {
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
        },


      ],
      dialogImageUrl: '',
      dialogVisible: false,
    }
  },
  methods: {
    handleClose(tag) {
      this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
    },
    showInput() {
      this.inputVisible = true;
      nextTick(() => {
        this.$refs.InputRef.input.focus();
      });
    },
    handleInputConfirm() {
      console.log(this.dynamicTags);
      if (this.inputValue) {
        this.dynamicTags.push(this.inputValue);
      }
      this.inputVisible = false;
      this.inputValue = '';
    },
    handleRemove(uploadFile, uploadFiles) {
      console.log(uploadFile, uploadFiles)
    },
    handlePictureCardPreview(uploadFile) {
      this.dialogImageUrl = uploadFile.url;
      this.dialogVisible = true
    },
    handleCreateSection() {
      var str = "" + this.course.capacity;
      var flag = false;
      if (this.course.name == null || this.course.name.length > 20 || this.course.name == '') {
        this.$message({showClose: true, message: '请正确输入课程名称！', type: 'error',});
        flag = true;
      }
      if (!str.match('^[0-9]+$')) {
        this.$message({showClose: true, message: '请输入正确格式的课程容量！', type: 'error',});
        flag = true;
      }
      str = "" + this.course.credits;
      if (!str.match('^[0-9]+$')) {
        this.$message({showClose: true, message: '请输入正确格式的学分！', type: 'error',});
        flag = true;
      }
      if (flag) {
        return;
      }

      let content = {
        name:this.course.name,
        intro:this.course.description,
        type:this.course.type,
        academy:this.course.college,
        credit:this.course.credits,
        capacity:this.course.capacity
      }

      console.log(content);

      axios({
        method: "POST",
        url: "/api/section/add/course",
        data: content,
      }).then((result) => {
        if(result.data.success) {
          this.$message({
            showClose: true,
            message: '申请创建成功！',
            type: 'success',
          });
          this.$router.go(-1);
        } else {
          this.$message({
            showClose: true,
            message: result.data.info,
            type: 'error',
          });
        }
      })
    },
  }

})
</script>
<style>
.breadcrumb {
  margin-top: 19px;
  margin-left: 20px;
  margin-bottom: 17px;
}
.content-container{
  display: flex;
  justify-content: center;
  //flex-direction: column; /* 将子元素垂直排列 */
  //align-items: center; /* 水平居中对齐 */
  //margin-left: -200px;
  width: calc(100vw - 205px);
  background: #dcdcdc;
}
.create-cs-container{
  display: flex;
  justify-content: center;
  //width: calc(100vw - 205px);
  width: 85%;
  margin-top: 30px;
  margin-bottom: 30px;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  border-radius: 1px;
}
.write-in-cs-inf{
  display: flex;
  justify-content: center;
  flex-direction: column;
  width: 800px;
}

.create-title-container{
  display: flex;
  justify-content: center;
  //font-size: 42px;
  margin-bottom: 30px;
  margin-top: 30px;
}

.create-title{
  font-size: 32px;
}


.inf-input{
  width: 1700px;
}
.input-container{
  display: flex;
  justify-content: space-between;
  //width: 100vw;
}

.form-group {
  display: flex;
  //justify-content: space-between;
  margin-bottom: 25px;
}
.name-input-container{
  max-width: 200px;
}
.prompt-before-input{
  display: flex;
  align-items: center;
  font-size: 15px;
  flex-shrink: 0; /* 禁止标签收缩 */
  margin-right: 10px; /* 调整标签和输入框之间的间距 */
}

.tag-in-container{
  display: inline;
}

.png-input{
  width: 800px;
  margin-left: 100px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.create-submit-button {

  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 100px;
  margin-bottom: 30px;
}

.create-submit-button:hover {
  background-color: #0056b3;
}



</style>
