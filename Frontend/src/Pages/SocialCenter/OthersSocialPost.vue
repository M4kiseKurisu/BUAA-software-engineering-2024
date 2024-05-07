<template>
    <div style="background-color: #f7f8fa; padding-top: 2px; padding-bottom: 12px;">
        <div style="width: 94%; margin-left: 3%">
            <el-row v-for="item in showPosts" :gutter="20" style="margin-top: 16px;">
                <el-col v-for="item2 in item" :span="12">
                    
                    <OthersPostCard 
                        :post="item2"
                    />

                </el-col>
            </el-row>
        </div>

        <div style="margin-top: 12px; margin-left: 3%;">
            <el-pagination :pager-count="5" layout="prev, pager, next" :background="is_background"
                :total="this.total_pages * 10" v-model:current-page="this.current_page" class="msg-pagination-container" />
        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

import OthersPostCard from './OthersPostCard.vue'

export default {
    data() {
        return {
            post_list: [],
            total_pages: 0,
            current_page: 1,
            is_background: true,
        }
    },
    components: {
        OthersPostCard,
    },
    mounted() {
        axios({
            method: "GET",
            url: "/api/pyq/square"
        }).then((result) => {
            this.post_list = result.data.posts;
            this.total_pages = (result.data.posts.length % 6 === 0) ? 
                result.data.posts.length / 6 : Math.ceil(result.data.posts.length / 6);
        })
    },
    computed: {
        showPosts() {
            let slice = this.post_list.slice((this.current_page - 1) * 6, this.current_page * 6);
            let output = [];
            if (slice.length === 0) {
                return output;
            }
            for (let i = 0; i < slice.length; i += 2) {
                output.push(slice.slice(i, i + 2));
            }
            return output;
        }
    }
}
</script>

<style scoped>
</style>