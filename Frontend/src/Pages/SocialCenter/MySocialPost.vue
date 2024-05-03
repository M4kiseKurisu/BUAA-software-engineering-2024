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


    <div class="post-time-font-style" style="margin-top: 24px; margin-left: 5%">2024年</div>

    <!-- 一个月份的打卡图像 -->
    <div class="flex-layout" style="margin-top: 12px; margin-left: 5%">

        <div class="post-time-font-style" style="margin-right: 10%">5月</div>

        <div style="flex-grow: 1; margin-right: 5%">
            <el-row v-for="item in showPic" :gutter="20" style="margin-bottom: 6px;">
                <el-col v-for="item2 in item" :span="6">
                    <button style="border: none; background-color: white; width: 100%; aspect-ratio: 1/1;" @click="getDetail(1)">
                        <el-image style="width: 100%; aspect-ratio: 1/1;" :src="url" :fit="fit" />
                    </button>
                </el-col>
            </el-row>
        </div>

    </div>

    <!-- 一个月份的打卡图像（测试用，后续删除） -->
    <div class="flex-layout" style="margin-top: 12px; margin-left: 5%">

        <div class="post-time-font-style" style="margin-right: 10%">4月</div>

        <div style="flex-grow: 1; margin-right: 5%">
            <el-row v-for="item in showPic" :gutter="20" style="margin-bottom: 6px;">
                <el-col v-for="item2 in item" :span="6">
                    <button style="border: none; background-color: white; width: 100%; aspect-ratio: 1/1;" @click="getDetail(1)">
                        <el-image style="width: 100%; aspect-ratio: 1/1;" :src="url" :fit="fit" />
                    </button>
                </el-col>
            </el-row>
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
    },
    methods: {
        getDetail(id) {
            this.$emit('childMethod', id);
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