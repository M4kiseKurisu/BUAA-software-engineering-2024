<template>
    <div class="detail-container">
        <div style="width: 36%; margin-left: 3%">
            <div class="flex-layout" style="margin-top: 24px;">
                <div style="width: 30%; aspect-ratio: 1/1;">
                    <el-image style="width: 100%; height: 100%;" :src="this.school_avatar" fit="fit" />
                </div>
                <div style="margin-left: 5%;">
                    <div class="school-font" style="margin-top: 18%;">{{ this.school_name }}</div>
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
            school_avatar: "./src/Images/buaa-avatar.jpg",
            school_name: "北京航空航天大学",
            school_website: "jiaowu.buaa.edu.cn",
            school_information: "北京航空航天大学教务处是主管学校本科教学工作和实施教学管理的职能部门。近年来，在学校党政领导下，教务处坚持以教师为本、以学生为本，注重课内与课外结合、教学与研究结合、传授知识与能力培养结合，坚持“厚植情怀、强化基础、突出实践、科教融通”的本科人才培养方针，积极探索新形势下人才培养的新思路，努力培养综合素质高、知识结构合理、基础扎实、知识面宽、具有创新精神和实践能力的一流拔尖创新人才。",
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