<template>
    <button class="card-css" style="width: 100%; aspect-ratio: 4/7; border: none; background-color: white;" @click="toDetail">
        <el-image style="width: 100%; aspect-ratio: 1/1;" :src="this.post_pic" fit="fit"/>

        <div style="width: 88%; margin-left: 6%; margin-top: 10px; height: 30%; display: flex; justify-content: flex-start; " class="content-font">
            {{ this.content }}    
        </div>
        <div class="flex-container" style="margin-left: 6%; margin-top: 5px;">
            <button style="width: 12%; aspect-ratio: 1/1; border: none; background-color: white;" @click="toPerson">
                <el-avatar style="width: 100%; height: 100%" :src="this.avatar_pic"/>
            </button>
            <div class="user-name-font" style="margin-left: 3%; margin-top: 4px; width: 55%; height: 16px;display: flex; justify-content: flex-start; ">{{ this.writer_name }}</div>
            <div style="width: 9%; aspect-ratio: 1/1; margin-left: 3%; margin-top: -6px;">
                <svg t="1714624020741" style="width: 100%; height: 100%" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2329" width="200" height="200"><path d="M461.1 887.1S210.6 784 98.3 552.5C46.6 446 46.3 303.3 147 202.1c100.6-101.1 263.8-101.2 364.4 0C612 101 775.2 101 875.8 202.1 976.5 303.3 979.1 458 924.5 552.5 789 787.1 570.3 885.1 570.3 885.1c-29.8 16.3-78.9 16.8-109.2 2z m20.1-41.4c17.3 8.5 50.7 7.9 66.8-1l3.6-1.7c2.7-1.2 8.5-4 17-8.5 14.6-7.8 31-17.3 48.8-28.6 51-32.6 102.1-73.1 149.7-121.9 45.2-46.3 85-97.7 117.8-154.5 50-86.6 36.9-216.2-41.5-294.9-82.7-83.1-216.6-83.1-299.2 0l-32.6 32.8-32.6-32.8c-82.7-83.1-216.6-83.1-299.3 0-78 78.4-89.5 195.7-40 297.8 33.6 69.1 82.8 130.8 143.9 185 43 38.2 89.2 70.4 135.4 96.7 16.2 9.2 31 16.9 44.3 23.3 7.8 3.7 13 6.1 15.5 7.1l2.4 1.2z" fill="#86909c" p-id="2330"></path></svg>
            </div>
            <div class="user-name-font" style="margin-left: 2%; margin-top: 5px; height: 16px;">{{ this.likes }}</div>
        </div>
    </button>
</template>

<script>
export default {
    data() {
        return {
            post_pic: "",
            avatar_pic: "",
            likes: 0,
            content: "",
            writer_name: "",
            post_id: 0,
            writer_id: 0,
        }
    },
    props: ["post"],
    mounted() {
        this.post_pic = this.post.image_url;
        this.avatar_pic = this.post.author_avatar;
        this.likes = this.post.favors;
        this.content = this.post.content;
        this.writer_name = this.post.author_name;
        this.post_id = this.post.post_id;
        this.writer_id = this.post.author_id;
    },
    methods: {
        toDetail() {
            console.log(this.post_id);
            this.$emit('childMethod', this.post_id);
        },
        toPerson() {
            this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.writer_id});
        }
    }
}
</script>

<style scoped>
.card-css {
    background-color: white;
    border-radius: 2px;
    border: none;
}

.content-font {
    overflow: hidden;
    word-wrap: break-word;
    font-size: 14px;
    color: #101010;
    line-height: 1.4;
}

.flex-container {
    display: flex;
}

.user-name-font {
    font-size: 12px;
    color: #86909c;
    overflow: hidden;
    word-wrap: break-word;
}
</style>