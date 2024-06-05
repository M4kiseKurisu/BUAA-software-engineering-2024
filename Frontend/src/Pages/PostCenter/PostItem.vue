<template>
    <div style="width: 100%;min-width: 720px;max-width: 100%;;height: 145px;background-color:white;display: flex;border-bottom: 1px solid darkgray;">
        <div v-if="this.cover != '' && noCover == 0"
            style="display: flex;height: 100%;width: 145px; justify-content: center;align-items: center; background-color: white;">
            <el-avatar :size='140' :src="cover" shape="square" fit="cover" />
        </div>
        <div class="widthSelect" :class="{ active: this.cover != '' && noCover == 0}">
            <div style="width: 100%;height: 40%;display: flex;align-items: center;justify-content: space-between;">
                <span style="font-size:1.6em;font-weight: bold; margin-left: 20px;">{{ title }}</span>
                <div style="display: flex;">
                    <span style="margin-right: 10px;" v-if = "this.authority == 'assistant'">
                        <el-tag type="warning" size="large" style="font-size: 1em; font-weight: bold;">助教</el-tag>
                    </span>
                    <span style="margin-right: 10px;" v-if = "this.authority == 'teacher'">
                        <el-tag type='danger' size="large" style="font-size: 1em; font-weight: bold;">教师</el-tag>
                    </span>
                    <span v-for="item in this.tags" style="margin-right: 10px;"><el-tag type="primary" size="large"
                            style="font-size: 1em; font-weight: bold;">{{ item }}</el-tag></span>
                </div>
            </div>
            <div
                style="width: 60%;height: 30%;margin-left: 20px;max-width: 60%;color: darkgrey;word-wrap: break-word;overflow: hidden;">
                <span>{{ introduction }}</span>
            </div>
            <div
                style="width: 100%;height: 30%; display: flex;justify-content: space-between;align-items: center;margin-left: 20px;">
                <div style="display: flex;align-items: center;">
                    <img src="../../Images/作者.png" alt="">
                    <span style="color: darkgrey;padding-left: 10px;">{{ authorName }}</span>
                    <span style="color: darkgrey;padding-left: 10px;">{{ time }}</span>
                    <svg style="padding-left: 10px; width: 20px;height: 20px;" t="1715676470652" class="icon"
                        viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5152" width="8"
                        height="8">
                        <path
                            d="M1004.125 382.534c-4.282-13.103-17.943-36.003-61.58-42.357l-173.66-25.227c-42.683-6.214-94.93-44.149-114.06-82.874l-77.661-157.37c-19.526-39.564-45.522-45.475-59.3-45.475s-39.772 5.91-59.298 45.474l-77.685 157.37c-19.106 38.703-71.33 76.661-114.036 82.875L93.184 340.177c-43.66 6.33-57.32 29.254-61.58 42.357-4.235 13.102-6.679 39.657 24.925 70.446l125.673 122.485c30.906 30.138 50.851 91.531 43.543 134.074l-29.649 172.963c-5.748 33.466 3.514 52.922 12.311 63.371 17.874 21.179 49.897 24.088 83.642 6.377l155.3-81.664c36.258-19.014 104.773-19.037 141.032 0l155.346 81.664c14.987 7.866 29.277 11.87 42.519 11.87 16.547 0 31.139-6.47 41.076-18.247 8.797-10.426 18.06-29.905 12.335-63.348l-29.65-172.986c-7.284-42.52 12.637-103.913 43.544-134.051l125.672-122.484c31.581-30.813 29.138-57.368 24.902-70.47z"
                            fill="#FF9C00" p-id="5153"></path>
                    </svg>
                    <span>&ensp;{{ starNum }}</span>
                    <!-- <span style="padding-left: 10px; display: flex;align-items: center;"><img src="../../Images/收藏.png"
                            alt="">&ensp;{{ starNum }}</span> -->
                    <svg style="padding-left: 10px; width: 21px;height: 21px;" t="1715676791674" class="icon"
                        viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8407" width="8"
                        height="8">
                        <path
                            d="M883.1 179.3C780.6 76.8 614.5 76.8 512 179.3c-102.5-102.5-268.6-102.5-371.1 0s-102.5 268.6 0 371.1L512 921.6l371.1-371.1c102.5-102.6 102.5-268.7 0-371.2z"
                            fill="#FF0000" p-id="8408"></path>
                    </svg>
                    <span>&ensp;{{ likeNum }}</span>
                    <!-- <span style="padding-left: 10px; display: flex;align-items: center;"><img src="../../Images/点赞.png"
                            alt="">&ensp;{{ likeNum }}</span> -->
                </div>
                <div style="display: flex;align-items: center; flex-grow: 1;justify-content: end;">
                    <span style="margin-right: 30px;"><el-button type="primary" @click="goToPost()">查看详情</el-button></span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { result } from 'lodash';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    props: {
        getPostId: {
            type: Number,
            default: 1,
        },
        postInfo: {
            type: Object,
            default: null,
        },
        getSectionId: {
            type: Number,
            default: -1,
        },
        noCover:{
            type: Number,
            default: 0,
        }
    },
    data() {
        return {
            title: "这是标题",
            tags: '',
            cover: '',
            introduction: '我要玩原神',
            authorName: '博酱',
            time: '2022.22.2.2',
            starNum: 999,
            likeNum: 999,
            postId: 1,
            authorId: 1,
            sectionId: 0,
            authority: '',
        }
    },
    methods: {
        GetInfomation() {
            //console.log(starNum);
            axios({
                method: "GET",
                url: "/api/posts/specify",
                params: { post_id: this.postId, },
            }).then((result) => {
                console.log(result);
                this.postId = result.data.post_id;
                this.title = result.data.post_title;
                this.authorId = result.data.author_id;
                this.authorName = result.data.author_name;
                this.introduction = result.data.post_intro;
                this.time = result.data.post_time;
                this.tags = result.data.tag_list;
                this.likeNum = result.data.post_likes;
                this.starNum = result.data.post_favorites;
                if (result.data.post_photo != null) {
                    this.cover = result.data.post_photo;
                }
                this.sectionId = this.getSectionId;
            });
        },
        getAuthority() {
            axios({
                method: "GET",
                url: 'api/user/authority',
                params: {
                    id: this.authorId,
                    section: this.sectionId,
                }
            }).then((result) => {
                this.authority = result.data.info;
                console.log(this.authorId);
                console.log(this.sectionId);
                console.log(this.authority);
            })
        },
        goToPost() {
            let authority = "none";
            //console.log(this.sectionId);
            axios({
                method: "GET",
                url: "/api/user/authority",
                params: { section: this.sectionId, },
            }).then((result) => {
                //console.log(result);
                if (result.data.success) {
                    authority = result.data.info;
                }
                this.$router.push({ path: '/MainPage/Course_Center/PostPage/' + this.postId });
            });
        },
    },
    created() {
        //console.log(this.postInfo);
        if (this.postInfo != null) {
            //console.log(this.postInfo);
            this.postId = this.postInfo.post_id;
            this.title = this.postInfo.post_title;
            this.authorId = this.postInfo.author_id;
            this.authorName = this.postInfo.author_name;
            this.introduction = this.postInfo.post_intro;
            this.time = this.postInfo.post_time;
            this.tags = this.postInfo.tag_list;
            this.likeNum = this.postInfo.post_likes;
            this.starNum = this.postInfo.post_favorites;
            if (this.postInfo.post_photo != null) {
                this.cover = this.postInfo.post_photo;
            }
            this.sectionId = this.getSectionId;
            this.getAuthority();
        } else {
            this.postId = this.getPostId;
            this.sectionId = this.getSectionId;
            this.GetInfomation();
            this.getAuthority();
        }
    }
}
</script>
<style>
.widthSelect.active {
    width: calc(100% - 145px);
    background-color: white;
    height: 100%;
}

.widthSelect {
    width: 100%;
    background-color: white;
    height: 100%;
}</style>