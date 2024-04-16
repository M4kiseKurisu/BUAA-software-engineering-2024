<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>

    <div class="main-postpage-container">
        <!-- 帖子正文部分 -->
        <div style="display: flex;width: 100%;height: 150px;background-color: antiquewhite;">
            <div style="width: 60%;height: 100%;">
                <div style="width: 100%;height: 55%;display: flex;align-items: center;margin-left: 7%;">
                    <span style="font-size: xx-large;font-weight: bold;">{{ courseName }}讨论区</span>
                    <span style="padding-left: 3%;font-size: large;color: darkgrey;">帖子数: {{ postNum }}</span>
                    <span style="padding-left: 3%;font-size: large;color: darkgrey;">关注数: {{ subscripNum }}</span>
                    <span style="padding-left: 3%;"><el-button type="primary" plain>关注板块</el-button></span>
                </div>
                <div style="width: 100%;height: 45%;display: flex;align-items: center;margin-left: 7%">
                    <span><el-button type="primary">去发帖</el-button></span>
                    <span style="padding-left: 3%;">
                        <el-button-group class="ml-4">
                            <el-button type="primary" plain v-if="kindSelect != 1" @click="selectOne">讨论帖</el-button>
                            <el-button type="primary" v-if="kindSelect == 1" @click="selectOne">讨论帖</el-button>
                            <el-button type="primary" plain v-if="kindSelect != 2" @click="selectTwo">资源帖</el-button>
                            <el-button type="primary" v-if="kindSelect == 2" @click="selectTwo">资源帖</el-button>
                            <el-button type="primary" plain v-if="kindSelect != 3" @click="selectThree">查看全部</el-button>
                            <el-button type="primary" v-if="kindSelect == 3" @click="selectThree">查看全部</el-button>
                        </el-button-group>
                    </span>
                    <span style="padding-left: 3%;">
                        <el-select v-model="sortKind" style="width: 150px" >
                            <el-option  key="热门" >热门</el-option>
                            <el-option  key="最新" >最新</el-option>
                        </el-select>
                    </span>
                </div>
            </div>
            <div style="width: 40%; height: 100%;display: flex; align-items: row-reverse;">
                <div style="width: 100%;height: 55%;display: flex;align-items: center;">
                    <div style="width: 100%;height: fit-content;display: flex;align-items: row-reverse;">
                        <el-button text>查看相关课程界面</el-button>
                    </div>
                </div>
            </div>
        </div>
        <div>

        </div>
    </div>
</template>

<script>
// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"

export default {
    components: {
        BreadcrumbLabel,
    },
    data() {
        return {
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
            courseName: "软件工程",
            courseId: 1,
            postNum: 20,
            subscripNum: 30,
            kindSelect: 1,
            sortKind: "热门",
        }
    },
    methods: {
        selectOne() {
            this.kindSelect = 1;
        },
        selectTwo() {
            this.kindSelect = 2;
        },
        selectThree() {
            this.kindSelect = 3;
        }
    }
}
</script>

<style scoped>
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}

.main-postpage-container {
    width: calc(100vw - 205px);
    background-color: rgba(247, 248, 250, 0.7);
}

.post-main-content-container {
    margin-left: 80px;
    margin-right: 80px;
    padding-top: 36px;
    background-color: white;
    border-radius: 2px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
    ;
}

.post-main-title {
    font-size: 24px;
    color: #101010;
    height: 34px;
    margin-left: 60px;
    margin-bottom: 30px;
}

.el-divider--horizontal {
    border-top: 1px solid #e5e6eb !important;
    margin: 0px !important;
}
</style>