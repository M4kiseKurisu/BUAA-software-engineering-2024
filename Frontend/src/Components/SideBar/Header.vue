<template>
    <div class="header">

        <!-- 上边栏左侧为网页标题 -->
        <div class="title">航学通——北航学习互助平台</div>

        <!-- 以下模块包含上边栏右侧全部信息 -->
        <div class="header-right">

            <el-popover placement="bottom" :width="400" style="margin-left: -20%;" trigger="click">
                <template #reference>
                    <button class="circle-bound">
                        <svg t="1714920508420" style="margin-left: -2px;" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2322" width="200" height="200"><path d="M679.039688 749.379825a29.25665 29.25665 0 0 1 46.225506-35.927166l204.796548 263.309847a29.25665 29.25665 0 0 1-46.225507 35.927166l-204.796547-263.309847zM482.727568 789.929541C264.589988 789.929541 87.762798 613.102351 87.762798 394.964771S264.589988 0 482.727568 0 877.692339 176.827191 877.692339 394.964771 700.865148 789.929541 482.727568 789.929541z m0-58.513299C668.53655 731.416242 819.17904 580.773753 819.17904 394.964771S668.53655 58.513299 482.727568 58.513299 146.276097 209.155789 146.276097 394.964771 296.918586 731.416242 482.727568 731.416242z" fill="#86909c" p-id="2323"></path></svg>
                    </button>
                </template>

                <!-- 以下为搜索框内容 -->
                <div class="flex-layout">
                    <el-input size="small" v-model="this.name_input" style="width: 40%" placeholder="输入搜索用户名" />
                    <el-select v-model="this.sort" placeholder="排列方式" size="small" style="width: 32%; margin-left: 5%;" >
                        <el-option v-for="item in sort_options" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                    <el-button size="small" style="width: 20%; margin-left: 3%;" @click="searchPerson">搜索用户</el-button>
                </div>

                <div v-for="item in this.user_list">
                    <button class="flex-layout" style="margin-top: 6px; width: 100%; margin-bottom: 6px; background-color: white; border: none;"
                        @click="toProfile(item.user_id)">
                        <div style="width: 16%; aspect-ratio: 1/1; margin-left: 3%; margin-top: 2%;">
                            <el-avatar style="width: 100%; height: 100%" :src="item.user_avatar" />
                        </div>
                        
                        <div style="width: 70%; margin-left: 4%;" class="flex-layout-column">
                            <div style="margin-top: 7%;" class="user-id-font">{{ item.name }}</div>
                            <div style="text-align: left; width: 100%;" class="user-sign-font">{{ item.sign }}</div>
                        </div>
                    </button>

                    <el-divider/>
                </div>
                
            </el-popover>
            

            <button class="circle-bound" @click="logout">
                <svg t="1713926820556" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4186" width="200" height="200"><path d="M952.7 492.1c-1.4-1.8-3.1-3.4-4.8-4.9l-179-178.9c-12.5-12.5-32.9-12.5-45.4 0s-12.5 32.9 0 45.4l126 126H421.3h-0.1c-18.2 0-32.9 14.8-32.9 33s14.7 33 32.9 33c0.3 0.1 0.5 0 0.7 0h427.8l-126 126c-12.3 12.3-12.3 32.4 0 44.7l0.7 0.7c12.3 12.3 32.4 12.3 44.7 0l182-182c11.7-11.7 12.3-30.6 1.6-43z" fill="#86909c" p-id="4187"></path><path d="M562.3 799c-18 0-32.7 14.7-32.7 32.7v63.8H129.2V128.7h400.4v63.1c0 18 14.7 32.7 32.7 32.7s32.7-14.7 32.7-32.7V96.3c0-3.5-0.6-6.8-1.6-10-4.2-13.3-16.6-23-31.2-23H96.6c-18 0-32.7 14.7-32.7 32.7v831.9c0 14.2 9.2 26.3 21.8 30.8 3.6 1.4 7.5 2.1 11.5 2.1h463.2c0.6 0 1.3 0.1 1.9 0.1 18 0 32.7-14.7 32.7-32.7v-96.5c0-18-14.7-32.7-32.7-32.7z" fill="#86909c" p-id="4188"></path><path d="M256.8 512.7a32.9 33 0 1 0 65.8 0 32.9 33 0 1 0-65.8 0Z" fill="#86909c" p-id="4189"></path></svg>
            </button>

            <NoticeCenter :type="1"/>

            <!-- 上边栏最右侧为头像 -->
            <div class="avatar">
                <el-avatar :size="32" :src="avatarPicture"/>
            </div>

        </div>

    </div>
    
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

import { Search } from '@element-plus/icons-vue'
import NoticeCenter from '../Notice/NoticeCenter.vue';
export default {
    components: {
        Search,
        NoticeCenter,
    },
    data() {
        return {
            avatarPicture: "",  //测试的头像信息
            //showNotice : 0,
            name_input: "",  //要搜索的用户名
            sort: 0,
            sort_options: [
                {
                    value: 0,
                    label: '关联度排序',
                },
                {
                    value: 1,
                    label: '粉丝数排序',
                },
                {
                    value: 2,
                    label: '获赞数排序',
                },
                {
                    value: 3,
                    label: '回复数排序',
                },
            ],
            user_list: [],
        }
    },
    mounted() {
        // 获取头像信息
        axios({
            method: "GET",
            url: "/api/user/head",
            params: {
                user_id: JSON.parse(sessionStorage.getItem("id"))
            }
        }).then((result) => {
            this.avatarPicture = result.data.info;
        })
    },
    methods: {
        logout() {
            // 用户登出
            let content = {
                id: JSON.parse(sessionStorage.getItem("id")),
            }
            axios({
                method: "POST",
                url: "/api/user/logout",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '登出成功！',
                        type: 'success',
                    });
                    this.$store.commit("userLogout");
                    this.$router.push({ path: "/LoginPage" });
                }
            })
        },
        searchPerson() {
            axios({
                method: "GET",
                url: "/api/user/search",
                params: {
                    keyword: this.name_input,
                    sort: this.sort
                }
            }).then((result) => {
                console.log(result);
                this.user_list = result.data.user;
            })
        },
        toProfile(id) {
            this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + id }).then(() => {
                this.$nextTick(() => {
                    // 强制重新加载当前页面
                    location.reload();
                });
            });
        }
    }
}

</script>

<style scoped>
/* 设置上边栏内元素横向排列 */
.header {
    display: flex;
    /* 设置子元素可选择靠左还是靠右排列 */
    justify-content: space-between;
}

/* 设置网站标题的位置和样式 */
.title {
    margin-left: 27px;
    margin-top: 16px;
    font-size: 20px;
    justify-content: flex-start;
}

/* 上边栏右侧内容全部右对齐 */
.header-right {
    justify-content: flex-end;
    display: flex;
}

/* 搜索按钮的圆形边框样式 */
.circle-bound {
    width: 32px;
    height: 32px;
    border: 1px solid #f2f3f5;
    border-radius: 16px;
    margin-top: 14px;
    margin-right: 16px;
    justify-content: center;
    align-items: center;
    display: flex;
    background-color: white;
}

/* 设置头像的位置 */
.avatar {
    margin-right: 17px;
    margin-top: 14px;
}

.icon {
    width: 20px;
    height: 20px;
    margin-left: 4px;
}

.flex-layout {
    display: flex;
}

.user-id-font {
    font-size: 18px;
    color: #165dff;
}

.user-sign-font {
    overflow: hidden;
    word-wrap: break-word;
    font-size: 15px;
    color: #86909c;
    height: 18px;
}

.el-divider--horizontal {
    margin: 0px !important;
}

.flex-layout-column {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
}
</style>