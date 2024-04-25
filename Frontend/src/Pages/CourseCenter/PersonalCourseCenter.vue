<template>
    <div class="personal-course-page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>
        <div class="title-button">
          <div class="personal-course-title">板块广场</div>
          <button class="submit-button" @click="goToCreateCourseSection">创建课程板块</button>
        </div>
        <!-- 此处为界面标题 -->

        <!-- 此处为界面上方走马灯
        <el-image :src="this.poster" :fit="fit" class="personal-course-carousel"/> -->
        <el-divider/>

        <div class="personal-course-content-container">

            <!-- 左侧信息栏位内容 -->
            <div class="personal-course-left-container">

                <div class="left-first-row-container">
                    <div class="avatar-container-120">
                        <el-avatar shape="square" :size="120" :src="this.avatarPicture" />
                    </div>
                    

                    <div class="left-first-row-right-container">
                        <div class="personal-course-username">{{ this.username }}</div>
                        <div class="personal-course-courseNumber">关注板块数：{{ this.courseNumber }}</div>
                    </div>
                </div>

                <div class="left-second-row-container">关注板块：</div>

                <!-- 用户关注模块信息 -->
                <div v-for="item in showGroups" class="left-content-row-container">
                    <div v-for="item2 in item">
                        <div class="my-following-course-card">{{ item2.section_name }}</div>
                    </div>
                </div>

                <div v-if="this.courseNumber === 0" class="noFollowingCourses">您当前还没有关注课程板块~</div>

            </div>

            <!-- 右侧信息栏位内容 -->
            <div class="personal-course-right-container">

                <div class="right-first-line-container">
                    <div class="left-start-items">
                        <div class="right-header">热门板块</div>
                    </div>

                    <div class="right-start-items">
                        <div class="right-selector2">
                            <el-select v-model="sortValue1" placeholder="排序方式" style="width: 85px" size="small">
                                <el-option
                                    v-for="item in sortOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </div>

                        <div class="right-selector2">
                            <el-select v-model="sortValue2" placeholder="课程类型" style="width: 85px" size="small">
                                <el-option
                                    v-for="item in sortOptions2"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </div>

                        <div class="right-selector2">
                            <el-select v-model="sortValue3" placeholder="开课院系" style="width: 85px" size="small">
                                <el-option
                                    v-for="item in sortOptions3"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </div>

                        <div class="right-input">
                            <el-input v-model="tagInput" style="width: 160px" placeholder="搜索板块关键字" size="small"/>
                        </div>

                        <div class="right-search-button">
                            <button class="right-search-button-css" @click="searchSession">搜索板块</button>
                        </div>
                    </div>
                </div>

                <div v-for="item in showCourses" class="course-card-row">
                    <el-row :gutter="54">
                        <!-- 单个关注课程信息 -->
                        <el-col v-for="item2 in item" :span="12" >
                            <CourseCard
                                :sectionId="item2.section_id"
                                :sectionName="item2.section_name"
                                :sectionFollowerCount="item2.section_follower_count"
                                :sectionIntroduction="item2.section_introduction"
                                :sectionAcademy="item2.section_academy"
                                :sectionType="item2.section_type"
                                :sectionIsFollowing="item2.section_is_following"
                            />
                        </el-col>
                    </el-row>
                </div>

                <div class="pagination-in-right-course-center">
                    <el-pagination :pager-count="6" layout="prev, pager, next" :total="this.totalPages" v-model="this.currentPage"/>
                </div>


            </div>

        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"

//引入关注课程组件
import CourseCard from "../../Components/Group/PersonalCourseCardInCourseCenter.vue"

export default {
    components: {
        BreadcrumbLabel,
        CourseCard,
    },
    data() {
        return {
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
            avatarPicture: "",  //本界面要展示的头像图片测试
            username: "",  //本界面要展示的昵称信息
            courseNumber: 0,  //本用户关注板块数
            followingCourseList: [],
            sortOptions: [
                {
                    value: '0',
                    label: '按热度排列',
                },
                {
                    value: '1',
                    label: '按关注数排列',
                }
            ],
            sortOptions2: [
                {
                    value: '0',
                    label: '所有课程',
                },
                {
                    value: '1',
                    label: '一般专业课',
                },
                {
                    value: '2',
                    label: '核心专业课',
                }
            ],
            sortOptions3: [
                {
                    value: '计算机学院',
                    label: '计算机学院',
                },
                {
                    value: '软件工程学院',
                    label: '软件工程学院',
                },
            ],
            sortValue1: "",
            sortValue2: "",
            sortValue3: "",
            tagInput: "",
            sectionFor: [1, 2, 3, 4],
            totalPages: 1,  // 热门板块页码数
            currentPage: 1,  //当前页码
            getSections: [],  // 当前获取课程
        }
    },
    methods: {
        goToCreateCourseSection() {
            this.$router.push({ path: '/CreateCourseSection' });
        },
        searchSession() {
            // 搜索板块
            console.log({
                keyword: this.tagInput,
                sort: this.sortValue1,
                type: this.sortValue2,
                academy: this.sortValue3,
            })
            axios({
                method: "GET",
                url: "/api/section/search",
                params: {
                    keyword: this.tagInput,
                    sort: this.sortValue1,
                    type: this.sortValue2,
                    academy: this.sortValue3,
                }
            }).then((result) => {
                console.log(result)
                this.totalPages = result.data.section_count / 8 + 1;
                this.getSections = result.data.sections;
            })
        }
    },
    computed: {
        showCourses() {
            let showCurrentPage = [];
            let i = this.currentPage;
            showCurrentPage.push(this.getSections.slice((i - 1) * 8, (i - 1) * 8 + 2));
            showCurrentPage.push(this.getSections.slice((i - 1) * 8 + 2, (i - 1) * 8 + 4));
            showCurrentPage.push(this.getSections.slice((i - 1) * 8 + 4, (i - 1) * 8 + 6));
            showCurrentPage.push(this.getSections.slice((i - 1) * 8 + 6, i * 8));
            return showCurrentPage;
        },
        showGroups() {
            let showMyCourses = [];
            for (let i = 0; i < this.followingCourseList.length; i += 2) {
                showMyCourses.push(this.followingCourseList.slice(i, i + 2));
            }
            return showMyCourses;
        }
    },
    created() {
        this.currentPage = 1;

        // 默认获取热门板块
        axios({
            method: "GET",
            url: "/api/section/hots",
        }).then((result) => {
            console.log(result)
            this.totalPages = result.data.section_count / 8 + 1;
            this.getSections = result.data.sections;
        })

        // 获取用户关注板块信息
        axios({
            method: "GET",
            url: "/api/user/focus",
        }).then((result) => {
            this.courseNumber = result.data.count;
            this.followingCourseList = result.data.sections;
        })

        // 获取用户基本信息
        axios({
            method: "GET",
            url: "/api/user/info",
        }).then((result) => {
            this.username = result.data.name;
        })

        // 获取用户头像信息
        axios({
            method: "GET",
            url: "/api/user/head",
            params: {
                user_id: JSON.parse(sessionStorage.getItem('id')),
            }
        }).then((result) => {
            this.avatarPicture = result.data.info;
        })
    }
}
</script>

<style scoped>
.personal-course-page-container {
    width: calc(100vw - 205px);
}

.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
}

.personal-course-title {
    height: 34px;
    font-size: 30px;
    color: #101010;
    margin-left: 38px;
    margin-top: 29px;
}
.title-button{
  display: flex;
  justify-content: space-between;
}

.submit-button {
  display: flex;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  width: 100px;
  height: 35px;
  margin-right: 200px;
  margin-top: 32px;
  justify-content: center;
  align-items: center;
}
.personal-course-carousel {
    width: 82%;
    margin-left: 9%;
    margin-top: 8px;
    height: 188px;
}

.el-divider--horizontal {
    border-top: 1px solid #e5e6eb !important;
    margin: 15px !important;
    width: 90%;
}

.personal-course-content-container {
    display: flex;
    margin-top: 16px;
}

.personal-course-left-container {
    width: 30%;
    height: 739px;
    background-color: #f7f8fa;
}

.left-first-row-container {
    display: flex;
    margin-top: 32px;
    margin-left: 27px;
    margin-right: 27px;
}

.left-first-row-right-container {
    margin-left: 26px;
    margin-top: 21px;
}

.personal-course-username {
    width: 90%;
    font-size: 24px;
    color: #101010;
    margin-top: -10px;
}

.personal-course-courseNumber {
    height: 25px;
    margin-top: 12px;
    font-size: 20px;
    overflow: hidden;
    color: #4e5969;
}

.left-second-row-container {
    margin-top: 18px;
    margin-left: 27px;
    margin-bottom: 10px;
    height: 25px;
    font-size: 20px;
    color: #4e5989;
}

.left-content-row-container {
    margin-top: 8px;
    margin-left: 27px;
    height: 36px;
    display: flex;
}

.my-following-course-card {
    width: 140px;
    height: 36px;
    margin-right: 17px;
    border-radius: 4px;
    border: 1px solid #e5e6eb;
    background-color: #ffffff;
    color: #4e5969;
    font-size: 16px;
    overflow: hidden;
    display: flex;
    justify-content: center;
    align-items: center;
}

.left-pagination-container {
    display: flex;
    justify-content: flex-end;
}

.pagination-left-location {
    margin-top: 15px;
    margin-right: 30px;
}

.personal-course-right-container {
    width: 63%;
    display: flex;
    flex-direction: column;
}

.right-first-line-container {
    height: 81px;
    display: flex;
    justify-content: space-between;
}

.left-start-items {
    margin-left: 26px;
    display: flex;
}

.right-header {
    height: 34px;
    font-size: 25px;
    color: #101010;
    margin-top: 19px;
}

.right-selector {
    margin-left: 12px;
    margin-top: 25px;
}

.right-start-items {
    display: flex;
}

.right-selector2 {
    margin-top: 25px;
}

.right-input {
    margin-top: 25px;
}

.right-search-button {
    margin-top: 25px;
    margin-left: 10px;
}

.right-search-button-css {
    border-radius: 4px;
    background-color: #e5e6eb;
    border: none;
    font-size: 12px;
    color: #4e5969;
    width: 70px;
    height: 24px;
}

.course-card-row {
    margin-left: 37px;
    margin-bottom: 22px;
}

.pagination-in-right-course-center {
    align-self: flex-end;
}

.noFollowingCourses {
    margin-left: 27px;
    font-size: 15px;
    color: #86909c;
}

.avatar-container-120 {
    height: 120px;
    width: 120px;
}
</style>
