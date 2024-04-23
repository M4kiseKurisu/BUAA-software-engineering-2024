<template>
    <div class="course-card-container">
        <div class="course-card-left">
            <div class="course-name-font">{{ this.sectionName }}</div>
            <div class="course-introduction-font">{{ this.sectionIntroduction }}</div>
            <div class="course-tag-row">
                <div class="course-card-tag-css">{{ this.sectionAcademy }}</div>
                <div class="course-card-tag-css">{{ this.sectionType }}</div>
            </div>
        </div>

        <div class="course-card-right">
            <div v-if="!sectionIsFollowing">
                <button class="course-card-button" @click="followCourse">关注板块</button>
            </div>
            <div v-else>
                <button class="course-card-button-2" @click="notfollowCourse">取消关注</button>
            </div>
            
            <div class="course-card-count">关注人数：{{ this.sectionFollowerCount }}</div>
            <div class="course-page-enter">进入板块</div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export default {
    props: ["sectionId", "sectionName", "sectionFollowerCount", "sectionIntroduction", "sectionAcademy", "sectionType", "sectionIsFollowing"],
    data() {
        return {

        }
    },
    methods: {
        followCourse() {
            let content = {
                section_id: this.sectionId
            }

            axios({
                method: "POST",
                url: "/api/section/focus",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '关注板块成功！',
                        type: 'success',
                    });
                }
            })
        },
        notfollowCourse() {
            let content = {
                section_id: this.sectionId
            }

            axios({
                method: "POST",
                url: "/api/section/unfocus",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '取消关注成功！',
                        type: 'success',
                    });
                }
            })
        }
    }
}
</script>

<style scoped>
.course-card-container{ 
    border-radius: 2px;
    border: 1px solid #e5e6eb;
    height: 131px;
    display: flex;
    justify-content: space-between;
}

.course-card-left {
    margin-left: 25px;
}

.course-name-font {
    height: 23px;
    font-size: 16px;
    color: #101010;
    margin-top: 14px;
}

.course-introduction-font {
    height: 40px;
    width: 200px;
    margin-top: 4px;
    font-size: 14px;
    color: #86909c;
    overflow: hidden;
    word-wrap: break-word;
}

.course-tag-row {
    margin-top: 4px;
    display: flex;
}

.course-card-tag-css {
    height: 24px;
    border-radius: 3px;
    border: 1px solid #3894ff;
    color: #3894ff;
    background-color: #e9f3ff;
    font-size: 13px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding-left: 6px;
    padding-right: 6px;
    margin-right: 6px;
}

.course-card-right {
    margin-right: 16px;
    display: flex;
    flex-direction: column;
}

.course-card-button {
    height: 24px;
    border-radius: 3px;
    border: 1px solid #3894ff;
    color: #3894ff;
    background-color: #e9f3ff;
    font-size: 13px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 13px;
    padding-left: 6px;
    padding-right: 6px;
    align-self: flex-end;
}

.course-card-button-2 {
    height: 24px;
    border-radius: 3px;
    border: none;
    color: #86909C;
    background-color: #E5E6EB;
    font-size: 13px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 13px;
    padding-left: 6px;
    padding-right: 6px;
    align-self: flex-end;
}

.course-card-count {
    height: 18px;
    font-size: 12px;
    color: #86909c;
    margin-top: 8px;
    align-self: flex-end;
}

.course-page-enter {
    height: 23px;
    font-size: 14px;
    color: #165dff;
    margin-top: 30px;
    align-self: flex-end;
}
</style>