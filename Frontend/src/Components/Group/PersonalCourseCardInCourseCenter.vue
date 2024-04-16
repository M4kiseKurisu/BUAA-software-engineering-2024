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
            <button class="course-card-button">关注板块</button>
            <div class="course-card-count">关注人数：{{ this.sectionFollowerCount }}</div>
            <div class="course-page-enter">进入板块</div>
        </div>
    </div>
</template>

<script>
export default {
    props: ["sectionId", "sectionName", "sectionFollowerCount", "sectionIntroduction", "sectionAcademy", "sectionType"],
    data() {
        return {

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
    height: 46px;
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