<template>
    <div class="flex-layout">

        <!-- 最上方用户头像、名称、签名 -->
        <div style="margin-left: 5%">
            <el-avatar shape="square" :size="76" :src="this.avatar_pic"/>
        </div>

        <div class="flex-column-layout" style="margin-left: 16px; margin-top: 20px; width: 60%">
            <div class="username-font-style">{{ this.user_name }}</div>
            <div class="flex-layout-for-sign">
                <div class="usersign-font-style" style="max-width: 94%">{{ this.user_sign }}</div>
                <div class="usersign-font-style-ellipsis">...</div>
            </div>   
        </div>
        
    </div>

    <div style="margin-top: 24px; margin-left: 5%; color: #86909c" v-if="this.divided_posts.length === 0">您当前还没有打卡信息~</div>

    <div v-else v-for="item1 in this.divided_posts">
        <!--  一个年份的打卡图像-->
        <div class="post-time-font-style" style="margin-top: 24px; margin-left: 5%">{{item1.year}}年</div>

        <!-- 一个月份的打卡图像 -->
        <div v-for="item2 in item1.posts" class="flex-layout" style="margin-top: 12px; margin-left: 5%">

            <div class="post-time-font-style" style="margin-right: 10%; width: 24%;">{{item2.month}}月</div>

            <div style="flex-grow: 1; margin-right: 5%">
                <el-row v-for="item3 in item2.posts" :gutter="20" style="margin-bottom: 6px;">
                    <el-col v-for="item4 in item3" :span="6">
                        <button style="border: none; background-color: white; width: 100%; aspect-ratio: 1/1;" @click="getDetail(item4.id)">
                            <el-image style="width: 100%; aspect-ratio: 1/1;" :src="item4.url"/>
                        </button>
                    </el-col>
                </el-row>
            </div>
        </div>
    </div>

</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export default {
    data() {
        return {
            user_id: JSON.parse(sessionStorage.getItem("id")),
            avatar_pic: "",
            user_name: "",
            user_sign: "",
            avatar_list: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
            get_posts: [],
            divided_posts: [],
        }
    },
    computed: {
        showPic() {
            let pic_show_list = [];
            for (let i = 0; i < this.avatar_list.length; i += 4) {
                pic_show_list.push(this.avatar_list.slice(i, i + 4));
            }
            //console.log(pic_show_list);
            return pic_show_list;
        },       
    },
    mounted() {
        // 获取用户基本信息
        axios({
            method: "GET",
            url: "/api/user/info"
        }).then((result) => {
            this.user_name = result.data.name;
            this.user_sign = result.data.sign;
        })

        // 获取用户头像信息
        axios({
            method: "GET",
            url: "/api/user/head",
            params: {
                user_id: this.user_id
            }
        }).then((result) => {
            this.avatar_pic = result.data.info;
        })

        // 确定用户签名是否需要隐藏
        const container = document.querySelector('.flex-layout-for-sign');
        const content = document.querySelector('.usersign-font-style');
        const ellipsis = document.querySelector('.usersign-font-style-ellipsis');

        if (content.scrollWidth > container.clientWidth) {
            ellipsis.style.visibility = 'visible'; // 显示省略号
        } else {
            ellipsis.style.visibility = 'hidden'; // 隐藏省略号
        }

        // 获得打卡信息
        axios({
            method: "GET",
            url: "/api/pyq/userInfo"
        }).then((result) => {
            console.log(result);
            this.get_posts = result.data.social_post;
            this.dividePosts(result.data.social_post);
            this.finalDivide();
        })
    },
    methods: {
        getDetail(id) {
            this.$emit('childMethod', id);
        },
        dividePosts(posts) {
            this.divided_posts = [];
            let now_year = 0;
            let now_month = 0;
            let month_posts = [];
            let date_posts = [];

            for (let i = 0; i < posts.length; i++) {
                if (posts[i].year != now_year) {
                    if (month_posts.length != 0) {
                        let element1 = {
                            year: posts[i].year,
                            posts: month_posts,
                        }
                        this.divided_posts.push(element1);
                    }  //切分年份
                    now_year = posts[i].year;
                    month_posts = [];
                }

                if (posts[i].month != now_month) {  //这个判断可能有bug
                    if (date_posts.length != 0) {
                        // console.log("month change");
                        // console.log(posts[i].month);
                        // console.log(now_month);
                        let element2 = {
                            month: posts[i].month,
                            posts: date_posts,
                        }
                        month_posts.push(element2);
                    }  //切分月份
                    now_month = posts[i].month;
                    date_posts = [];
                }

                for (let j = 0; j < posts[i].image_urls.length; j++) {
                    let element3 = {
                        url: posts[i].image_urls[j],
                        id: posts[i].post_id,
                    }
                    date_posts.push(element3);
                } //将所有图片,id放入对应日期
                console.log(date_posts);

                if (i === posts.length - 1) {  //切最后一段
                    let element2 = {
                        month: posts[i].month,
                        posts: date_posts,
                    }
                    month_posts.push(element2);
                    let element1 = {
                        year: posts[i].year,
                        posts: month_posts,
                    }
                    this.divided_posts.push(element1);
                }
            }

            console.log(this.divided_posts); // 测评
        },
        finalDivide() {
            // 将每个月的信息以4分组
            for (let i = 0; i < this.divided_posts.length; i++) {
                for (let j = 0; j < this.divided_posts[i].posts.length; j++) {
                    let new_post = [];
                    for (let k = 0; k < this.divided_posts[i].posts[j].posts.length; k += 4) {
                        new_post.push(this.divided_posts[i].posts[j].posts.slice(k, k + 4));
                    }
                    this.divided_posts[i].posts[j].posts = new_post;
                }
            }

            console.log(this.divided_posts); // 测评
        }
    }
}
</script>

<style scoped>
.flex-layout {
    display: flex;
}

.username-font-style {
    height: 28px;
    font-size: 20px;
    overflow: hidden;
    color: #165dff;
}

.flex-layout-for-sign {
    display: flex;
    position: relative;
    overflow: hidden;
    max-width: 100%;
}

.usersign-font-style {
    height: 24px;
    overflow: hidden;
    word-wrap: break-word;
    font-size: 16px;
    color: #86909c;


    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis; /* 超出最大宽度时显示省略号 */
}

.usersign-font-style-ellipsis {
    height: 24px;
    overflow: hidden;
    word-wrap: break-word;
    font-size: 16px;
    color: #86909c;

    
    position: absolute;
    bottom: 0;
    right: 0;
    visibility: hidden; /* 初始状态下隐藏省略号 */
}

.post-time-font-style {
    height: 24px;
    font-size: 18px;
    color: #101010;
    /*font-weight: bold;*/
}

.flex-column-layout {
    display: flex;
    flex-direction: column;
}
</style>