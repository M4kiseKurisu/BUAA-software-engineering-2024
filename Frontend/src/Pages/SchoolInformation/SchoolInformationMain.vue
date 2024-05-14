<template>
    <!-- 此处为面包屑组件 -->
    <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

    <div class="container-css">
        <el-row style="width: 94%; margin-left: 3%; margin-top: 16px;" v-for="item in groups" :gutter="60">
            <el-col v-for="item2 in item" :span="12">
                <SchoolInformationCard 
                    :school_avatar="item2.school_badge"
                    :school_name="item2.school_name"
                    :school_id="item2.school_id"
                />
            </el-col>
        </el-row>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

import BreadcrumbLabel from "@/Components/Tool/BreadcrumbLabel.vue";
import SchoolInformationCard from "./SchoolInformationCard.vue"

export default {
    data(){
        return {
            route: ["升学中心", "学校信息"],
            school_list: [],
        }
    },
    components: {
        SchoolInformationCard,
        BreadcrumbLabel,
    },
    mounted() {
        // 获取学校基本信息
        axios({
            method: "GET",
            url: "/api/progression/schools",
        }).then((result) => {
            console.log(result);
            this.school_list = result.data.schools;
        })
    },
    computed: {
        groups() {
            let schools = [];

            if (this.school_list.length === 0) {
                return schools;
            }
            for (let i = 0; i < this.school_list.length; i += 2) {
                schools.push(this.school_list.slice(i, i + 2));
            }
            return schools;
        }
    }
}
</script>

<style scoped>
.container-css {
    width: calc(100vw - 205px);
    min-width: 1174px;
}
</style>