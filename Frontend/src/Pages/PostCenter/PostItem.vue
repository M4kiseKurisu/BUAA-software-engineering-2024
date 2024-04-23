<template>
    <div style="width: 100%;height: 145px;background-color:white;display: flex;border-bottom: 1px solid darkgray;">
        <div v-if="cover == ''" style="display: flex;height: 100%;width: 145px; justify-content: center;align-items: center; background-color: white;">
            <el-avatar :size = '140' src="./src/Images/testAvatar.jpg" shape="square" fit="cover"/>
        </div>
        <div style="flex-grow: 1;background-color:white;height: 100%;">
            <div style="width: 100%;height: 40%;display: flex;align-items: center; justify-content: space-between;">
                <span style="font-size:1.6em;font-weight: bold; margin-left: 20px;">{{ title }}</span>
                <div style="display: flex;">

                    <span style="margin-right: 10px;"><el-tag type="primary" size="large" style="font-size: 1em; font-weight: bold;">Tag 1</el-tag></span>
                    <span style="margin-right: 10px;"><el-tag type="primary" size="large" style="font-size: 1em;font-weight: bold">Tag 2</el-tag></span>
                </div>
            </div>
            <div style="width: 60%;height: 30%;margin-left: 20px;color: darkgrey;word-wrap: break-word;overflow: hidden;">
                    {{ introduction }}{{ introduction }}
            </div>
            <div style="width: 100%;height: 30%; display: flex;justify-content: space-between;align-items: center;margin-left: 20px;">
                <div style="display: flex;align-items: center;">
                    <img src="../../Images/作者.png" alt="">
                    <span style="color: darkgrey;padding-left: 10px;">{{ authorName }}</span>
                    <span style="color: darkgrey;padding-left: 10px;">{{ time }}</span>
                    <span style="padding-left: 10px; display: flex;align-items: center;"><img src="../../Images/收藏.png" alt="">&ensp;{{ starNum }}</span>
                    <span style="padding-left: 10px; display: flex;align-items: center;"><img src="../../Images/点赞.png" alt="">&ensp;{{ likeNum }}</span>
                </div>
                <div style="display: flex;align-items: center; flex-grow: 1;justify-content: end;">
                    <span style="margin-right: 5%;"><el-button type="primary">查看详情</el-button></span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default{
    props:{
        // postId :{
        //     type:Number,
        //     default: 1,
        // },
    },
    data(){
        return {
            title: "这是标题",
            tags: '',
            cover: '',
            introduction: '我要玩原神',
            authorName: '博酱',
            time: '2022.22.2.2',
            starNum: 999,
            likeNum: 999,
            postId: 1,
            authorId : 1,
        }
    },
    methods:{
        GetInfomation(){
            //console.log(starNum);
            axios({
                method : "GET",
                url : "/api/posts/post",
                data: {post_id:this.postId,},
            }).then((result) => {
                console.log(result);
                this.title = result.title;
                this.authorId = result.author_id;
                this.authorName = result.author_name;
                this.introduction = result.content;
                this.time = result.create_time;
                this.tags = result.tags;
                this.likeNum = result.like_count;
                this.starNum = result.collect_count;
                //还差一个封面
            });
        }
    },
    created(){
        //this.GetInfomation();
    }
}
</script>
<style>

</style>