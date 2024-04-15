<template>
    <div class="personal-course-page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

        <!-- 此处为界面标题 -->
        <div class="personal-course-title">板块广场</div>

        <!-- 此处为界面上方走马灯
        <el-image :src="this.poster" :fit="fit" class="personal-course-carousel"/> -->
        <el-divider/>

        <div class="personal-course-content-container">

            <!-- 左侧信息栏位内容 -->
            <div class="personal-course-left-container">

                <div class="left-first-row-container">
                    <el-avatar shape="square" :size="120" :src="this.avatarPicture" />

                    <div class="left-first-row-right-container">
                        <div class="personal-course-username">{{ this.username }}</div>
                        <div class="personal-course-courseNumber">关注板块数：{{ this.courseNumber }}</div>
                    </div>
                </div>

                <div class="left-second-row-container">关注板块：</div>

                <!-- 第一行关注模块信息 -->
                <div class="left-content-row-container">
                    <div v-for="item in group1">
                        <div class="my-following-course-card">{{ item }}</div>
                    </div>
                </div>

                <!-- 第二行关注模块信息 -->
                <div class="left-content-row-container">
                    <div v-for="item in group2">
                        <div class="my-following-course-card">{{ item }}</div>
                    </div>
                </div>

                <!-- 第三行关注模块信息 -->
                <div class="left-content-row-container">
                    <div v-for="item in group3">
                        <div class="my-following-course-card">{{ item }}</div>
                    </div>
                </div>

                <!-- 第四行关注模块信息 -->
                <div class="left-content-row-container">
                    <div v-for="item in group4">
                        <div class="my-following-course-card">{{ item }}</div>
                    </div>
                </div>

                <!-- 第五行关注模块信息 -->
                <div class="left-content-row-container">
                    <div v-for="item in group5">
                        <div class="my-following-course-card">{{ item }}</div>
                    </div>
                </div>

                <!-- 左侧分页器  -->
                <!-- <div class="left-pagination-container">
                    <el-pagination small background layout="prev, pager, next" :total="10" class="pagination-left-location"/>
                </div>   -->
            </div>

            <!-- 右侧信息栏位内容 -->
            <div class="personal-course-right-container">

                <div class="right-first-line-container">
                    <div class="left-start-items"> 
                        <div class="right-header">热门板块</div>
                        <div class="right-selector">
                            <el-select v-model="sortValue" placeholder="排序方式" style="width: 85px" size="small">
                                <el-option
                                    v-for="item in sortOptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                />
                            </el-select>
                        </div>
                    </div>

                    <div class="right-start-items"> 
                        <div class="right-selector2">
                            <el-select v-model="sortValue" placeholder="排序方式" style="width: 85px" size="small">
                                <el-option
                                    v-for="item in sortOptions"
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
                            <button class="right-search-button-css">搜索板块</button>
                        </div>
                    </div>
                </div>

                <!-- 单行关注课程信息 -->
                <div v-for="item in sectionFor" class="course-card-row">
                    <el-row :gutter="54">
                        <!-- 单个关注课程信息 -->
                        <el-col :span="12" ><CourseCard 
                            :sectionId="this.sectionCardTest.sectionId"
                            :sectionName="this.sectionCardTest.sectionName"
                            :sectionFollowerCount="this.sectionCardTest.sectionFollowerCount"
                            :sectionIntroduction="this.sectionCardTest.sectionIntroduction"
                            :sectionAcademy="this.sectionCardTest.sectionAcademy"
                            :sectionType="this.sectionCardTest.sectionType"
                        /></el-col>
                        <!-- 单个关注课程信息 -->
                        <el-col :span="12" ><CourseCard 
                            :sectionId="this.sectionCardTest.sectionId"
                            :sectionName="this.sectionCardTest.sectionName"
                            :sectionFollowerCount="this.sectionCardTest.sectionFollowerCount"
                            :sectionIntroduction="this.sectionCardTest.sectionIntroduction"
                            :sectionAcademy="this.sectionCardTest.sectionAcademy"
                            :sectionType="this.sectionCardTest.sectionType"
                        /></el-col>
                    </el-row>
                </div>

                <div class="pagination-in-right-course-center">
                    <el-pagination :pager-count="6" layout="prev, pager, next" :total="100" />
                </div>
                

            </div>

        </div>
    </div>
</template>

<script>
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
            poster: "./src/Images/buaaPoster1.jpg", //本页面要展示的图片
            // carouselList: ["./src/Images/testPoster.jpg", 
            //                "./src/Images/testPoster.jpg", 
            //                "./src/Images/testPoster.jpg", 
            //                "./src/Images/testPoster.jpg", 
            //                "./src/Images/testPoster.jpg", 
            //                "./src/Images/testPoster.jpg"],  //本界面要展示的走马灯图片测试
            avatarPicture: "./src/Images/testAvatar.jpg",  //本界面要展示的头像图片测试
            username: "M4kiseKurisu",  //本界面要展示的昵称信息
            courseNumber: 7,  //本用户关注板块数
            followingCourseList: [
                "关注模块A", "关注模块B", "关注模块C", "关注模块D", "关注模块E", "关注模块F", "关注模块G", "关注模块H", "关注模块I", "关注模块J"
            ],
            sortOptions: [
                {
                    value: '1',
                    label: '按热度排列',
                },
                {
                    value: '2',
                    label: '按首字母排列',
                },
                {
                    value: '3',
                    label: '按院系排列',
                }
            ],
            sortValue: "",
            tagInput: "",
            sectionCardTest: {  //测试热门板块卡片
                sectionId: 1, 
                sectionName: "软件工程",
                sectionFollowerCount: "186", 
                sectionIntroduction: "软件工程真的是一门好课！软件工程真的是一门好课！", 
                sectionAcademy: "计算机学院",
                sectionType: "一般专业课",
            },
            sectionFor: [1, 2, 3, 4],
        }
    },
    computed: {
        group1() {  //分离个人关注的一二个板块
            return (this.followingCourseList.length === 0) ? null : this.followingCourseList.slice(0, 2);
        },
        group2() {  //分离个人关注的三四个板块
            return (this.followingCourseList.length <= 2) ? null : this.followingCourseList.slice(2, 4);
        },
        group3() {  //分离个人关注的五六个板块
            return (this.followingCourseList.length <= 4) ? null : this.followingCourseList.slice(4, 6);
        },
        group4() {  //分离个人关注的七八个板块
            return (this.followingCourseList.length <= 6) ? null : this.followingCourseList.slice(6, 8);
        },
        group5() {  //分离个人关注的九十个板块
            return (this.followingCourseList.length <= 8) ? null : this.followingCourseList.slice(8, 10);
        },
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
    height: 34px;
    font-size: 24px;
    overflow: hidden;
    color: #101010;
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

</style>