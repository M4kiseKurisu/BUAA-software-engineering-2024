<template>
    <div class="personal-course-page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>
        <div class="title-button">
            <div class="personal-course-title">板块广场</div>
            <div style="display: flex;">
                <button class="submit-button" @click="goToCreateCourseSection">创建课程板块</button>
                <button class="submit-button" style="margin-right: 120px;" @click="teacher_dialog = true">申请教师权限</button>

                <el-dialog v-model="teacher_dialog" title="申请教师权限" width="500">
                    <div style="font-size: 16px; font-weight: bold; margin-bottom: 12px;">填写申请信息</div>
                    <el-input
                        v-model="apply_content"
                        :autosize="{ minRows: 2, maxRows: 4 }"
                        type="textarea"
                        placeholder="请输入申请信息"
                    />

                    <div style="margin-top: 12px; color: #86909c">注：只能上传一个证明文件，已经选择后再次选择文件会覆盖之前的文件</div>
                    <div class="flex-layout" style="margin-top: 12px;">
                        <el-upload
                            :limit="1"
                            :auto-upload="false"
                            v-model:file-list="this.file"
                        >
                            <template #trigger>
                                <el-button type="primary" plain >选择文件</el-button>
                            </template>
                            <el-button type="primary" @click="submitUpload" style="margin-left: 16px;">
                                上传文件
                            </el-button>
                        </el-upload>
                    </div>
                    <el-button style="margin-top: 4px;" type="primary" @click="sendApply">发送申请</el-button>
                    <div v-if="content_warning.length > 0" class="warning-css" style="margin-top: 2px;">{{ content_warning }}</div>
                </el-dialog>
            </div>
        </div>
        <!-- 此处为界面标题 -->

        <!-- 此处为界面上方走马灯
        <el-image :src="this.poster" :fit="fit" class="personal-course-carousel"/> -->
        <el-divider/>

        <div class="personal-course-content-container">

            <!-- 左侧信息栏位内容 -->
            <div class="personal-course-left-container">

                <div class="left-first-row-container">
                    <div class="avatar-container-120" @click="returnUserInfo">
                        <el-avatar shape="square" :size="120" :src="this.avatarPicture" />
                    </div>


                    <div class="left-first-row-right-container">
                        <div class="personal-course-username" @click="returnUserInfo">{{ this.username }}</div>
                        <div class="personal-course-courseNumber">关注板块数：{{ this.courseNumber }}</div>
                    </div>
                </div>

                <div class="left-second-row-container">关注板块：</div>

                <!-- 用户关注模块信息 -->
                <div v-for="item in showGroups" class="left-content-row-container">
                    <div v-for="item2 in item">
                        <button class="my-following-course-card" @click="toFollowingSection(item2.section_id)">{{ item2.section_name }}</button>
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
                    <el-pagination :page-size="8" layout="prev, pager, next" :total="(this.totalPages - 1) * 8" @current-change="changePage"/>
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
            route: [{name: "学业板块", route: ""}, {name: "课程论坛", route: ""}],  //本界面要显示的面包屑信息
            avatarPicture: "",  //本界面要展示的头像图片测试
            username: "",  //本界面要展示的昵称信息
            courseNumber: 0,  //本用户关注板块数
            followingCourseList: [],
            sortOptions: [
                {
                    value: '',
                    label: '按热度排列',
                },
                {
                    value: '1',
                    label: '按关注数排列',
                }
            ],
            sortOptions2: [
                {
                    value: '',
                    label: '所有课程',
                },
                {
                    value: '1',
                    label: '一般专业课',
                },
                {
                    value: '2',
                    label: '核心专业课',
                },
                {
                    value: '3',
                    label: '一般通识课',
                },
                {
                    value: '4',
                    label: '核心通识课',
                },
                {
                    value: '5',
                    label: '基础类课程',
                },
                {
                    value: '6',
                    label: '体育课',
                },
                {
                    value: '7',
                    label: '其它课程',
                },
                {
                    value: '8',
                    label: '版块通知',
                },
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
            teacher_dialog: false,
            apply_content: "",
            file: [],
            url: "",
            is_loading: false,
            content_warning: "",
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
        },
        toFollowingSection(id) {
            this.$router.push({ path: "/MainPage/Course_Center/PostCenter/" + id});
        },
        changePage(val) {
            this.currentPage = val;
        },
        async submitUpload() {
            try {
                console.log(this.file);
                const loadingMessage = this.$message({
                    showClose: true,
                    message: '正在上传资源',
                    type: 'info',
                    duration: 0, // 设置持续时间为 0，表示不自动关闭
                });
                this.is_loading = true;

                const file_upload = this.file[0].raw;
                const formData = new FormData();
                formData.append('file', file_upload);
                formData.append('name', "");
                formData.append('type', "");
                formData.append('publisher_id', JSON.parse(sessionStorage.getItem("id")))

                let response = await axios.post("/api/posts/write/uploadResource", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                console.log(response.data);
                this.url = response.data.url;
                console.log(this.url);

                loadingMessage.close();
                this.is_loading = false;
                this.$message({
                    showClose: true,
                    message: '资源上传成功！',
                    type: 'success',
                });
            } catch (error) {
                console.log("error")
            }
        },
        sendApply() {
            if (this.is_loading) {
                this.content_warning = "请等待资源上传完成后再发布帖子"
                return;
            } else if (this.url.length == 0 || this.apply_content.length == 0) {
                this.content_warning = "不能上传空申请"
                return;
            } else if (this.apply_content.length > 600) {
                this.content_warning = "申请文字过长"
                return;
            }

            let content = {
                detail: this.apply_content,
                file: this.url,
            }
            console.log(content);
            axios({
                method: "POST",
                url: "/api/user/apply/global",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '权限申请信息上传成功！',
                        type: 'success',
                    });
                }
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
            console.log(i)
            return showCurrentPage;
        },
        showGroups() {
            let showMyCourses = [];
            for (let i = 0; i < this.followingCourseList.length; i += 2) {
                showMyCourses.push(this.followingCourseList.slice(i, i + 2));
            }
            return showMyCourses;
        },
        returnUserInfo() {
            this.$router.push("/MainPage/Personal_Center/Personal_Information")
        }
    },
    created() {
        this.currentPage = 1;

        // 获取所有学院用于选择
        axios({
            method: "GET",
            url: "/api/section/academy",
        }).then((result) => {
            console.log(result.data)
            var list = new Array();
            list[0] = {value: "", label: "全部院系"}
            var count = 1;
            for (var i = 0; i < result.data.academy.length; i++) {
                var string = result.data.academy[i];
                if (string != "") {
                    list[count++] = {value: result.data.academy[i], label: result.data.academy[i]};
                }
            }
            console.log(list)
            this.sortOptions3 = list;
        })

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
    width: calc(100vw - 220px);
    min-width: 1174px;
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
  margin-right: 30px;
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

.warning-css {
    color: red;
    font-size: 12px;
}
</style>
