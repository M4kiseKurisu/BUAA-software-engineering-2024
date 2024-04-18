<template>
  <div class="course-section-container">
    <div class="breadcrumb">
      <BreadcrumbLabel :routeNames="route" />
    </div>

    <div class="course-container">
      <div class="first-card">
        <div class="first-card-left">
          <div class="inf-tags">
            <div class="course-inf">
              <span class="course-name">{{course.name}}</span>
            </div>
            <div class="course-tags">
              <span class="tag" v-for="(tag, index) in course.tags" :key="index">{{ tag }}</span>
            </div>
          </div>
          <div class="other-inf">
            <span class="other-info">课程类型:{{ course.type }}</span>
            <span class="other-info">开课院系:{{ course.college }}</span>
            <span class="other-info">学分:{{ course.credits }}</span>
          </div>
        </div>
        <div class="first-card-right">
          <span class="another-info">课程容量:{{course.capacity}}</span>
          <span class="another-info">课程关注:{{course.attention}}</span>
          <button class="go-to-plate">前往对应板块</button>
        </div>
      </div>
      <div class="second-card">
        <div class="course-description">
          <span class="title">
            课程介绍
          </span>
          <span class="description-of-course">
            {{course.description}}
          </span>
        </div>
        <div class="course-teacher">
          <span class="title">
            教师介绍
          </span>

          <div class="pic-name">
            <div class="teacher-pic-container">
              <img :src="selectedTeacher.teacherPicture" style="width: auto; height: 100px;" alt="">
            </div>
            <div class="teacher-name-container">
              <span class="teacher-name">{{ selectedTeacher.name }}</span>
            </div>
          </div>
          <span class="introduction-of-">{{selectedTeacher.introduction }}</span>
          <div class="pagination-container">
            <el-pagination
                v-model="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next"
                :total="course.teachers.length"
                @current-change="handleCurrentChange"
            ></el-pagination>
          </div>
        </div>
      </div>
      <div class="third-card">
        <div class="course-content">
          <span class="title">
            课程内容展示
          </span>
          <div class="content-pic">
            <img :src="selectedContent.pic" style="width: auto; height: 250px;" alt="">
          </div>
          <div class="pagination-container">
            <el-pagination
                v-model="currentContent"
                :page-size="pageSize"
                layout="prev, pager, next"
                :total="course.content.length"
                @current-change="handleCurrentContentChange"
            ></el-pagination>
          </div>
        </div>
        <div class="course-outline">
          <span class="title">
            课程大纲
          </span>
          <el-collapse>
            <div class="outline-container" v-for="(outline, index) in course.outlines" :key="index">
              <el-collapse-item :title=outline.name>
                <span>
                  {{outline.content}}
                </span>
              </el-collapse-item>
            </div>

          </el-collapse>
        </div>
      </div>

    </div>
  </div>
</template>
<script>

import {defineComponent} from "vue";
import BreadcrumbLabel from "@/Components/Tool/BreadcrumbLabel.vue";

export default defineComponent({

  components: {BreadcrumbLabel},
  data(){
    return{
      pageSize:1,
      currentPage: 1,
      currentContent:1,
      course:{
        name:"软件工程",
        type:"核心专业课",
        tags:["标签一","标签二"],
        college:"计算机学院",
        credits:"114.514",
        capacity:"1919",
        attention:"810",
        description:"太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太没意思了傻软😫👎太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍",
        teachers: [
          {
            name: "武计",
            introduction: "男，博士，副教授，博导，计算机学院软件工程研究所副所长。1974年12月出生，安徽肥西人。主要研究领域是安全关键软件与系统的建模、验证与测试，采用自然语言处理、深度学习、数据挖掘与逻辑建模相结合的方法来研究其中的需求、架构设计、测试自动化、安全性与可靠性等问题。多年来一直与国内航空航天研究所、华为公司等开展了密切合作，面向工业部门的挑战性问题开展研究。多项研究成果在工业界等取得了实质性应用。",
            teacherPicture: "./src/Images/teacher.png"
          },
          {
            name: "韩夏喑",
            introduction: "教授，博士，博士生导师。大数据科学与脑机智能北京市高精尖创新中心副主任。中国计算机学会系统软件专委会常务委员，计算机科学普及工委主任，中国电子学会云计算、大数据专家委员会副秘书长，国际万维网联盟（W3C）副理事长。主讲：编译技术、云计算技术原理、电子商务（高并发Web系统设计）。",
            teacherPicture: "./src/Images/teacher.png"
          }
        ],
        content:[
          {
            pic:"./src/Images/course-content-1.png"
          },
          {
            pic:"./src/Images/course-content-2.png"
          }
        ],
        outlines:[
          {
            name: "课程内容A",
            content:"课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容"
          },
          {
            name: "课程内容A",
            content:"课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容"
          },
          {
            name: "课程内容A",
            content:"课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容"
          },
        ]
      }
    }
  },
  computed: {
    selectedTeacher() {
      // 根据当前页码获取对应的老师信息
      return this.course.teachers[this.currentPage - 1];
    },
    selectedContent() {
      // 根据当前页码获取对应的老师信息
      return this.course.content[this.currentContent - 1];
    }
  },
  methods: {
    handleCurrentChange(val) {
      // 页面改变时触发的方法
      this.currentPage = val;
    },
    handleCurrentContentChange(val) {
      // 页面改变时触发的方法
      this.currentContent=val;
    }
  }
})
</script>
<style>
.course-section-container{
  width: calc(100vw - 205px);
}
.course-container{
  display: flex;
  //justify-content: center; /* 居中 */
  flex-direction: column;
  align-items: center;
}
.breadcrumb {
  margin-top: 19px;
  margin-left: 20px;
  margin-bottom: 17px;
}
.first-card{
  display: flex;
  justify-content: space-between;
}
.first-card-left{
  display: flex;
  flex-direction:column ;
  margin-left: -100px;
}
.inf-tags{
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
.course-name{
  font-size: 32px;
}

.course-tags {
  margin-left: 30px;
  display: flex;
  flex-wrap: wrap;
}

.tag {
  font-size: 12px;
  margin-right: 10px; /* 添加标签之间的间距 */
  padding: 5px; /* 添加内边距 */
  color: #007bff; /* 设置蓝色 */
  border: 1px solid #007bff; /* 添加边框 */
  border-radius: 5px; /* 圆角边框 */
}


.other-inf {
  display: flex;
  flex-direction: column; /* 每个span单独一行 */
  margin-top: 12px;
}

.other-info {
  margin-top: 8px;
  font-size: 14px; /* 调整字体大小 */
  color: #888888 /* 调整颜色为灰色 */
}

.first-card-right{
   display: flex;
   flex-direction:column ;
   margin-left: 150px;
 }

.another-info {
  margin-top:18px;
  font-size: 18px; /* 调整字体大小 */
  color: #4e5969;
}

.go-to-plate {
  margin-top: 30px;
  background-color: #007bff; /* 蓝色背景 */
  color: #ffffff; /* 白色文字 */
  border: none; /* 移除边框 */
  border-radius: 5px; /* 圆角 */
  padding: 8px 16px; /* 添加内边距 */
  font-size: 16px; /* 字体大小 */
  cursor: pointer; /* 光标样式为指针 */
}

.go-to-plate:hover {
  background-color: #0056b3; /* 鼠标悬停时变暗一点 */
}

.second-card{
  margin-top: 80px;
  display: flex;
  justify-content: space-between;
}

.title{
  font-size: 22px;
}

.course-description{
  display: flex;
  flex-direction: column; /* 每个span单独一行 */
  margin-top: 12px;
  width: 500px;
  //margin-left: -100px;
}

.description-of-course{
  margin-top: 40px;
}
.course-teacher{
  margin-top: 12px;
  margin-left: 60px;
  width: 250px;

}

.pic-name{
  display: flex;
  margin-top: 12px;
  justify-content: space-between;
  //align-items: center;
}
.teacher-name-container{
  //height: 100px;
  align-self: center;
  margin-right: -30px;
}
.teacher-name{
  font-size: 33px;
  color: #007bff; /* 蓝色 */
  //margin-left: 235px;
  //margin-top: 33.5px;
  margin-right: 50px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px; /* 调整上边距 */
}

.third-card{
  display: flex;
  justify-content: space-between;
  margin-top: 60px;
}
.course-content{
  //margin-left: -100px;
  width: 500px;
}
.content-pic{
  margin-top: 30px;
}
.course-outline{
  margin-left: 60px;
  width: 250px;
}
</style>
