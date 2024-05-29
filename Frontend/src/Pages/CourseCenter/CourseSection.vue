<template>
  <div class="course-section-container">
    <div class="breadcrumb">
      <BreadcrumbLabel :routeNames="route" />
    </div>

    <div class="course-container">
      <div class="first-card-container">
        <div class="first-card">
          <div class="first-card-left">
            <div class="inf-tags">
              <div class="course-inf">
                <span class="course-name">{{ course.course_name }}</span>
              </div>
              <div class="course-tags">
                <span class="tag" v-for="(tag, index) in course.course_tags" :key="index">{{ tag }}</span>
              </div>
            </div>
            <div class="other-inf">
              <span class="other-info">课程类型:{{ course.course_type }}</span>
              <span class="other-info">开课院系:{{ course.course_college }}</span>
              <span class="other-info">学分:{{ course.course_credit }}</span>
            </div>
          </div>
          <div class="first-card-right">
            <div class="first-right-container">
              <span class="another-info">课程容量:{{ course.course_capacity }}</span>
            </div>
            <div class="first-right-container">
              <span class="another-info">课程关注:{{ course.course_follows }}</span>
            </div>
            <div class="first-right-container">
              <button class="go-to-plate" @click="goToCourseSectionPostCenter">前往对应板块</button>
            </div>



          </div>
        </div>
      </div>

      <div class="second-card">
        <div class="course-description-container">
          <div class="course-description">
            <span class="cs-title">
              课程介绍
            </span>
            <span class="description-of-course">
              {{ course.course_info }}
            </span>
          </div>
        </div>
        <div class="course-teacher-container">
          <div class="course-teacher">

            <span class="cs-title">
              教师介绍
            </span>
            <div v-if="teachers.length !== 0">
              <div class="pic-name">
                <div class="teacher-pic-container">
                  <img :src="selectedTeacher.teacher_picture" style="width: auto; height: 100px;" alt="">
                </div>
                <div class="teacher-name-container">
                  <span class="teacher-name">{{ selectedTeacher.teacher_name }}</span>
                </div>
              </div>
              <span class="introduction-of-">{{ selectedTeacher.teacher_introduction }}</span>
              <div class="pagination-container">
                <el-pagination v-model="currentPage" :page-size="pageSize" layout="prev, pager, next"
                  :total="teachers.length" @current-change="handleCurrentChange"></el-pagination>
              </div>
            </div>
            <div v-else class="pic-name">
              <span class="description-of-course">
                暂无教师信息
              </span>

            </div>

          </div>
        </div>

      </div>
      <!--
      <div class="third-card">
        <div class="course-content">
          <span class="cs-title">
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
                :total="course.course_content.length"
                @current-change="handleCurrentContentChange"
            ></el-pagination>
          </div>
        </div>
        <div class="course-outline">
          <span class="cs-title">
            课程大纲
          </span>
          <el-collapse>
            <div class="outline-container" v-for="(outline, index) in course.course_outlines" :key="index">
              <el-collapse-item :title=outline.name>
                <span>
                  {{outline.content}}
                </span>
              </el-collapse-item>
            </div>

          </el-collapse>
        </div>
      </div>
      -->
    </div>
  </div>
</template>
<script>

import { defineComponent } from "vue";
import BreadcrumbLabel from "@/Components/Tool/BreadcrumbLabel.vue";
import axios from "axios";



export default defineComponent({

  components: { BreadcrumbLabel },
  data() {
    return {
      route: [{name: "学业板块", route: "/MainPage/Course_Center/Personal_Course"},
            {name: "课程论坛", route: "/MainPage/Course_Center/Personal_Course"}],
      pageSize: 1,
      currentPage: 1,
      currentContent: 1,
      course: {
        course_name: "软件工程",
        course_type: "核心专业课",
        course_tags: ["标签一", "标签二"],
        course_college: "计算机学院",
        course_credit: "114.514",
        course_capacity: "1919",
        course_follows: "810",
        course_info: "太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太没意思了傻软😫👎太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍太有意思了软工😁👍",
        course_content: [
          {
            pic: "./src/Images/course-content-1.png"
          },
          {
            pic: "./src/Images/course-content-2.png"
          }
        ],
        course_outlines: [
          {
            name: "课程内容A",
            content: "课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容"
          },
          {
            name: "课程内容A",
            content: "课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容"
          },
          {
            name: "课程内容A",
            content: "课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容课程内容A的详细内容"
          },
        ]
      },
      teachers: [
        {
          teacher_name: "武计",
          teacher_introduction: "男，博士，副教授，博导，计算机学院软件工程研究所副所长。1974年12月出生，安徽肥西人。主要研究领域是安全关键软件与系统的建模、验证与测试，采用自然语言处理、深度学习、数据挖掘与逻辑建模相结合的方法来研究其中的需求、架构设计、测试自动化、安全性与可靠性等问题。",
          teacher_picture: "./src/Images/teacher.png"
        },
        {
          teacher_name: "韩夏喑",
          teacher_introduction: "教授，博士，博士生导师。大数据科学与脑机智能北京市高精尖创新中心副主任。中国计算机学会系统软件专委会常务委员，计算机科学普及工委主任，中国电子学会云计算、大数据专家委员会副秘书长，国际万维网联盟（W3C）副理事长。主讲：编译技术、云计算技术原理、电子商务（高并发Web系统设计）。",
          teacher_picture: "./src/Images/teacher.png"
        }
      ],
    }
  },
  mounted() {
    const sectionId = this.$route.params.section_id;
    this.getCourseInfo(sectionId);
    //console.log(this.data())
  },
  computed: {

    /*async getCourseInfo(sectionId) {
      try {
        const response = await axios.get("/section/info", { params: { section_id: sectionId } });
        this.course = response.data.course;
        this.teachers = response.data.teachers;
      } catch (error) {
        console.error('Error fetching course information:', error);
      }
    },*/

    selectedTeacher() {
      // 根据当前页码获取对应的老师信息
      return this.teachers[this.currentPage - 1];
    },
    selectedContent() {
      // 根据当前页码获取对应的老师信息
      return this.course.course_content[this.currentContent - 1];
    }
  },
  methods: {
    goToCourseSectionPostCenter() {
      this.$router.push({ path: "/MainPage/Course_Center/PostCenter/" + this.$route.params.section_id});
    },
    getCourseInfo(sectionId) {
      axios({
        method: "GET",
        url: "api/section/info",
        params: { section_id: sectionId },

      })
        //axios.get(`/section/info/${sectionId}`).
        .then(response => {
          console.log(response.data)
          // 请求成功，将返回的课程信息赋值给组件的 course 对象
          this.course.course_info = response.data.course_info;
          this.course.course_name = response.data.course_name;
          this.course.course_college = response.data.course_college;
          this.course.course_credit = response.data.course_credit;
          this.course.course_posts = response.data.course_posts;
          this.course.course_type = response.data.course_type;
          this.course.course_capacity = response.data.course_capacity;
          this.course.course_follows = response.data.course_follows;
          this.teachers = response.data.teachers;
          this.course.course_tags = [this.course.course_college, this.course.course_type];
          this.route[2] = {
            name: response.data.course_name,
            route: "/MainPage/Course_Center/PostCenter/" + sectionId
          }
          /*if (this.teachers.length === 0) {
            this.teachers = [{

              teacher_name: "暂无信息",
              teacher_introduction: "暂无信息",
              teacher_picture: ""
            }];
          }*/
          console.log(this.course)
          console.log(this.teachers)
        })
        .catch(error => {
          // 请求失败，处理错误
          console.error('Error fetching course information:', error);
        });
    },
    handleCurrentChange(val) {
      // 页面改变时触发的方法
      this.currentPage = val;
    },
    handleCurrentContentChange(val) {
      // 页面改变时触发的方法
      this.currentContent = val;
    }
  }
})
</script>
<style>
.course-section-container {
  width: calc(99vw - 205px);

  //height: calc(100vh - 85px);
}

.course-container {
  display: flex;
  //justify-content: center; /* 居中 */
  flex-direction: column;
  align-items: center;
  //margin-left: -500px;
  //background: #dbdbdb;
  background: #dcdcdc;
}

.breadcrumb {
  margin-top: 19px;
  margin-left: 20px;
  margin-bottom: 17px;
}

.first-card-container {
  width: 100%;
  height: 260px;
  //background: #00b9b8;
  //background: #b8dafe;
  background: #c3e0ff;
  //background-color: rgba(0, 150, 255, 0.3); /* 使用淡蓝色，透明度为 0.3 */
  display: flex;
  justify-content: center;
  border-radius: 5px;

}

.first-card {
  display: flex;
  justify-content: space-between;
  width: 55%;
  margin-top: 30px;
  height: 200px;
  background: white;
  //border-top-left-radius: 10px; /* 左上角圆角半径为 10px */
  //border-bottom-right-radius: 10px; /* 右下角圆角半径为 10px */
  //border-radius: 13px;
}

.first-card-left {
  display: flex;
  flex-direction: column;
  margin-left: 15%;
  margin-top: 20px;
}

.inf-tags {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}

.course-name {
  font-size: 32px;
}

.course-tags {
  margin-left: 30px;
  display: flex;
  flex-wrap: wrap;
}

.tag {
  font-size: 12px;
  margin-right: 10px;
  /* 添加标签之间的间距 */
  padding: 5px;
  /* 添加内边距 */
  color: #007bff;
  /* 设置蓝色 */
  border: 1px solid #007bff;
  /* 添加边框 */
  border-radius: 5px;
  /* 圆角边框 */
}


.other-inf {
  display: flex;
  flex-direction: column;
  /* 每个span单独一行 */
  margin-top: 12px;
}

.other-info {
  margin-top: 8px;
  font-size: 14px;
  /* 调整字体大小 */
  color: #888888
    /* 调整颜色为灰色 */
}

.first-card-right {
  display: flex;
  flex-direction: column;
  width: 25%;
  //margin-right: 15%;
  //margin-top: 20px;
}

.first-right-container {
  background: #dcdcdc;
  height: 33%;
  border: 1px solid rgba(0, 0, 0, 0.05);
  display: flex;
  justify-content: center;
  //margin-top: 18px;
}

.another-info {
  margin-top: 18px;
  font-size: 18px;
  /* 调整字体大小 */
  color: #4e5969;
}

.go-to-plate {
  background-color: #007bff;
  /* 蓝色背景 */
  color: #ffffff;
  /* 白色文字 */
  border: none;
  /* 移除边框 */
  border-radius: 5px;
  /* 圆角 */
  padding: 8px 16px;
  /* 添加内边距 */
  font-size: 16px;
  /* 字体大小 */
  cursor: pointer;
  /* 光标样式为指针 */
  height: 60%;
  margin-top: 7.5%;
}

.go-to-plate:hover {
  background-color: #0056b3;
  /* 鼠标悬停时变暗一点 */
}

.second-card {
  margin-top: 40px;
  display: flex;
  justify-content: space-between;
  width: 85%;
  margin-bottom: 35px;
}

.cs-title {
  font-size: 22px;
}

.course-description-container {
  background: white;
  width: 65%;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  border-radius: 1px;
}

.course-description {
  display: flex;
  flex-direction: column;
  /* 每个span单独一行 */
  margin-top: 5%;
  width: 90%;
  margin-left: 5%;

  //margin-left: -100px;
}

.description-of-course {
  margin-top: 30px;
  margin-bottom: 30px;
}

.course-teacher-container {
  width: 30%;
  background: white;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  border-radius: 1px;
}

.course-teacher {
  //margin-left: 60px;
  margin-top: 5%;
  width: 80%;
  margin-left: 10%;
  margin-right: 10%;

}

.pic-name {
  display: flex;
  margin-top: 12px;
  justify-content: space-between;
  //align-items: center;
}

.teacher-name-container {
  //height: 100px;
  align-self: center;
  //margin-right: -30px;
}

.teacher-name {
  font-size: 33px;
  color: #007bff;
  /* 蓝色 */
  //margin-left: 235px;
  //margin-top: 33.5px;
  margin-right: 50px;
}

.pagination-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
  /* 调整上边距 */
}

.third-card {
  display: flex;
  justify-content: space-between;
  margin-top: 60px;
}

.course-content {
  //margin-left: -100px;
  width: 500px;
}

.content-pic {
  margin-top: 30px;
}

.course-outline {
  margin-left: 60px;
  width: 250px;
}</style>
