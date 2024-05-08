<template>
    <div class="detail-container">
        <div style="width: 36%; margin-left: 3%">
            <div class="flex-layout" style="margin-top: 24px;">
                <div style="width: 30%; aspect-ratio: 1/1;">
                    <el-image :src="this.school_avatar" fit="fit" />
                </div>
                <div style="margin-left: 5%;">
                    <div class="school-font" style="margin-top: 8%;">{{ this.school_name }}</div>
                    <div class="website-font" style="margin-top: 2%;">相关网址：{{ this.school_website }}</div>
                </div>
            </div>

            <div style="border-radius: 2px; background-color: #f7f8fa; padding: 3%; margin-top: 24px;">
                <div class="title-font">学校简介</div>

                <div style="margin-top: 16px;" class="information-font">
                    {{ this.school_information }}
                </div>
            </div>
            
        </div>

        <el-divider direction="vertical"/>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export default {
    data() {
        return {
            school_id: 0,
            school_avatar: "",
            school_name: "",
            school_website: "",
            school_information: "",
            school_posts: [],
        }
    },
    created() {
        this.school_id = this.$route.params.school_id;
        axios({
            method: "GET",
            url: "/api/progression/schoolInfo",
            params: {
                school_id: this.school_id
            }
        }).then((result) => {
            //console.log(result);
            this.school_avatar = result.data.school_badge;
            this.school_name = result.data.school_name;
            this.school_information = result.data.school_intro;
            this.school_website = result.data.school_web;
            this.school_posts = result.data.posts;
        })
    }
}
</script>

<style scoped>
.detail-container {
    width: calc(100vw - 205px);
    display: flex;
}

.flex-layout {
    display: flex;
}

.school-font {
    font-size: 25px;
    color: #101010;
}

.website-font {
    font-size: 20px;
    color: #86909c;
}

.information-font {
    font-size: 16px;
    color: #101010;
    line-height: 1.5;
}

.title-font {
    font-size: 18px;
    font-weight: bold;
    color: #101010;
}

.el-divider--vertical {
    border-left: 1px solid #e5e6eb !important;
    margin-left: 3% !important;
    margin-right: 3% !important;
    margin-top: 5%;
    height: auto !important;
}
</style>