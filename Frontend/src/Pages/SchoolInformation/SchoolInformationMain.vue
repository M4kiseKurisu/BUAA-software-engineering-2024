<template>
    <!-- 此处为面包屑组件 -->
    <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

    <div class="container-css">
        <el-row style="width: 94%; margin-left: 3%; margin-top: 16px;" v-for="item in 5" :gutter="60">
            <el-col v-for="item2 in 2" :span="12">
                <SchoolInformationCard />
            </el-col>
        </el-row>
    </div>
</template>

<script>
import BreadcrumbLabel from "@/Components/Tool/BreadcrumbLabel.vue";
import SchoolInformationCard from "./SchoolInformationCard.vue"

export default {
    data(){
        return {
            route: ["升学中心", "学校信息"],
        }
    },
    components: {
        SchoolInformationCard,
        BreadcrumbLabel,
    }
}
</script>

<style scoped>
.container-css {
    width: calc(100vw - 205px);
}
</style>