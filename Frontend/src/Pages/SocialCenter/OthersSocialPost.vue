<template>
    <div style="background-color: #f7f8fa; padding-top: 2px; padding-bottom: 12px;">

        <el-radio-group v-model="followLimit" style="display: flex; margin-bottom: 8px; margin-top: 12px; margin-left: 3%;" @change="getPosts(1)">
            <el-radio style="background-color: white;" :value=0 border>查看所有人</el-radio>
            <el-radio style="background-color: white;" :value=1 border>查看关注的人</el-radio>
        </el-radio-group>
        <div style="width: 94%; margin-left: 3%">
            <el-row v-for="item in showPosts" :gutter="20" style="margin-top: 16px;">
                <el-col v-for="item2 in item" :span="12">

                    <OthersPostCard
                        :post="item2"
                        @childMethod="showDetail"
                        :key="this.refreshKey"
                    />

                </el-col>
            </el-row>
        </div>

        <div style="margin-top: 12px; margin-left: 3%;">
            <el-pagination :pager-count="5" layout="prev, pager, next" :background="is_background"
                :total="this.total_pages * 10" class="msg-pagination-container" @current-change="getPosts" />
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
            pages_all: 0,
            pages_follow_limit: 0,
            current_page: 1,
            is_background: true,
            refreshKey: 0,
            followLimit: 0
        }
    },
    components: {
        OthersPostCard,
    },
    mounted() {
        this.getPosts(this.current_page);
    },
    computed: {
        showPosts() {
            let output = [];
            if (this.post_list.length === 0) {
                return output;
            }
            for (let i = 0; i < this.post_list.length; i += 2) {
                output.push(this.post_list.slice(i, i + 2));
            }
            this.refreshKey++;
            return output;
        }
    },
    methods: {
        getDetail(id) {
            this.$emit('childMethod', id);
        },
        showDetail(id) {
            this.getDetail(id);
        },
        getPosts(v) {
            this.current_page = v;
            if (this.followLimit == 1) {
                axios({
                    method: "GET",
                    url: "/api/pyq/follow",
                    params: { page: (this.current_page - 1) },
                }).then((result) => {
                    this.post_list = result.data.posts;
                    console.log(this.post_list)
                })
                axios({
                    method: "GET",
                    url: "/api/pyq/pages",
                    params: { limit: 1 },
                }).then((result) => {
                    this.total_pages = result.data.pages;
                })
            } else {
                axios({
                    method: "GET",
                    url: "/api/pyq/square",
                    params: { page: (this.current_page - 1) },
                }).then((result) => {
                    this.post_list = result.data.posts;
                    console.log(this.post_list)
                })
                axios({
                    method: "GET",
                    url: "/api/pyq/pages",
                    params: { limit: 0 },
                }).then((result) => {
                    this.total_pages = result.data.pages;
                })
            }
        }
    }
}
</script>

<style scoped>
</style>