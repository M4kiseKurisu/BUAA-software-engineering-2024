<template>
    <div style="background-color: #f7f8fa; padding-top: 2px; padding-bottom: 12px;">
        <div style="width: 94%; margin-left: 3%">
            <el-row v-for="item in 3" :gutter="20" style="margin-top: 16px;">
                <el-col v-for="item in 2" :span="12">
                    
                    <OthersPostCard />

                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import OthersPostCard from './OthersPostCard.vue'

export default {
    data() {
        return {

        }
    },
    components: {
        OthersPostCard,
    }
}
</script>

<style scoped>

</style>