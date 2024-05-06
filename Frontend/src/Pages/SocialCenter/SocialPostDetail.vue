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

    <div class="flex-layout" style="margin-top: 20px;">

        <div style="width: 44%; margin-left: 3%; margin-right: 3%;">
            <!-- 展示打卡中的所有图片 -->
            <el-carousel style="width: 100%; aspect-ratio: 1/1;" autoplay="false">
                <el-carousel-item v-for="item in this.post_pics" :key="item">
                    <div style="width: 100%; height: 100%; background-color: #f7f8fa;">
                        <el-image style="width: 100%; height: 100%" :src="item" fit="contain" />
                    </div>
                </el-carousel-item>
            </el-carousel>
        </div>

        <el-divider direction="vertical"/>

        <div style=" margin-left: 3%; margin-right: 6%; width: 41%; line-height: 1.5;" class="content-font-style">
            {{ this.content }}
        </div>
    </div>

    <div class="flex-layout" style="margin-top: 10px;">
        <!-- 点赞按钮 -->
        <button class="flex-layout" style="background-color: white; border: none; margin-left: 2%; margin-right: 2%;">
            <svg t="1714568960613" style="height: 22px; width: 22px;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1497" width="200" height="200"><path d="M512 901.746939c-13.583673 0-26.122449-4.179592-37.093878-13.061225-8.881633-7.314286-225.697959-175.020408-312.424489-311.379592C133.746939 532.37551 94.040816 471.24898 94.040816 384.522449c0-144.718367 108.146939-262.269388 240.326531-262.269388 67.395918 0 131.657143 30.82449 177.632653 84.636735 45.453061-54.334694 109.191837-84.636735 177.110204-84.636735 132.702041 0 240.326531 117.55102 240.326531 262.269388 0 85.159184-37.093878 143.673469-67.395919 191.216327l-1.044898 1.567346c-86.726531 136.359184-303.542857 304.587755-312.424489 311.379592-10.44898 8.359184-22.987755 13.061224-36.571429 13.061225zM334.367347 164.04898c-109.714286 0-198.530612 98.742857-198.530612 220.473469 0 74.187755 33.959184 127.477551 61.648979 170.318367 83.069388 130.089796 294.138776 294.138776 303.020408 300.930613 3.657143 2.612245 7.314286 4.179592 11.493878 4.179591s7.836735-1.567347 11.493878-4.179591c8.881633-6.791837 219.95102-170.840816 303.020408-300.930613l1.044898-1.567347c28.212245-44.930612 60.604082-95.608163 60.604081-168.75102 0-121.208163-89.338776-220.473469-198.530612-220.473469-60.081633 0-115.983673 29.257143-153.6 80.979591l-6.269388 8.881633c-4.179592 5.22449-10.44898 8.359184-16.718367 8.359184s-13.061224-3.134694-16.718367-8.359184l-6.269388-8.881633C450.35102 193.828571 393.926531 164.04898 334.367347 164.04898z" fill="#86909c" p-id="1498"></path></svg>
            <div style="height: 22px; margin-left: 6px; color: #86909c; font-size: 14px;">点赞</div>
        </button>

        <el-divider direction="vertical"/>

        <!-- 回复按钮 -->
        <button class="flex-layout" style="background-color: white; border: none; margin-left: 2%; margin-right: 2%;">
            <svg t="1714569288805" style="height: 21px; width: 21px;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2556" width="200" height="200"><path d="M881.1 393.1c-17.6 0-31.7 14.3-31.7 31.7v379.9c0 26.3-21.4 47.7-47.7 47.7H213.5c-26.3 0-47.7-21.4-47.7-47.7V216.5c0-26.3 21.4-47.7 47.7-47.7h379.9c17.6 0 31.7-14.3 31.7-31.7 0-17.6-14.3-31.7-31.7-31.7H213.5c-61.2 0-111.1 49.9-111.1 111.1v588.1c0 61.3 49.9 111.2 111.1 111.2h588.2c61.3 0 111.1-49.9 111.1-111.1V424.9c0.1-17.6-14.2-31.8-31.7-31.8z m-395.9 140c6.2 6.2 14.4 9.3 22.5 9.3s16.2-3.1 22.5-9.3l373.5-373.5c12.4-12.4 12.4-32.5 0-44.9-12.4-12.4-32.5-12.4-44.9 0L485.2 488.2c-12.5 12.4-12.5 32.5 0 44.9z m0 0" p-id="2557" fill="#86909c"></path></svg>
            <div style="height: 22px; margin-left: 6px; color: #86909c; font-size: 14px;">评论</div>
        </button>

        <!-- 删除按钮（只有此打卡为本人发布才存在） -->

        <el-divider v-if="this.poster_id === this.my_id" direction="vertical"/>

        <button v-if="this.poster_id === this.my_id" class="flex-layout" style="background-color: white; border: none; margin-left: 2%; margin-right: 2%;" @click="deletePost">
            <svg t="1714569453159" style="height: 19px; width: 19px;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3570" width="200" height="200"><path d="M959.04 192.256h-35.84V958.72a64 64 0 0 1-63.936 63.872H160.64a63.872 63.872 0 0 1-63.872-63.872V192.256h-31.872a31.872 31.872 0 1 1 0-63.872h223.552V32.64c0-17.664 14.272-31.936 31.936-31.936h383.168c17.664 0 32 14.272 32 32v95.68h223.488a32 32 0 0 1 0 64z m-287.424-127.744h-319.36v63.872h319.36v-63.872z m191.68 191.616v-63.872H160.64v734.592a32 32 0 0 0 32 31.936h638.72a32 32 0 0 0 31.936-32V256.192z m-191.68 143.744h63.936v407.168h-63.936V399.872zM480.064 336H544v470.976h-63.872v-471.04z m-191.616 63.872h63.872v407.168h-63.872V399.872z" fill="#86909c" p-id="3571"></path></svg>
            <div style="height: 22px; margin-left: 6px; color: #86909c; font-size: 14px;">删除</div>
        </button>
    </div>

    <div style="margin-top: 15px; margin-left: 2%; margin-right: 3%; border-radius: 2px;
        background-color: #f7f8fa; padding-left: 1%; padding-right: 2%; padding-top: 12px; padding-bottom: 12px;">

        <div class="flex-layout" style="margin-left: 3%;">
            <div style="margin-top: 10px;">
                <svg t="1714554846788" style="height: 27px; width: 27px;" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4166" width="200" height="200"><path d="M516.809433 921.67163S-142.389927 480.534026 144.538823 164.134706c18.112122-19.954032 40.522035-35.712601 65.592485-45.84311C335.278905 67.843709 429.932647 119.417208 478.026981 157.995003c20.568002 16.577196 50.038573 16.577196 70.606576 0C597.648846 118.700909 694.963126 65.797142 823.794544 121.259119c19.851704 8.595583 38.066154 20.670331 53.517737 35.814929C1183.78575 456.691516 516.809433 921.67163 516.809433 921.67163" fill="#86909c" p-id="4167"></path></svg>
            </div>

            <!-- 展示点赞者头像 -->
            <div class="flex-column-layout" style="width: 92%; margin-left: 3%;">
                <div v-for="item in favorGroup" style="width: 100%; margin-top: 3px; margin-bottom: 6px;" class="flex-layout">
                    <div v-for="item2 in item1" style="width: 6%; aspect-ratio: 1/1; margin-right: 2%;">
                        <button style="width: 100%; height: 100%; border: none; background-color: white;">
                            <el-avatar style="width: 100%; height: 100%" :src="item2.favor_id" />
                        </button>
                    </div>
                </div>
            </div>
            
        </div>

        <el-divider/>

        <div v-for="item in this.comment_list">
            <div class="flex-layout" style="width: 100%; margin-bottom: 14px;">
                <!-- 展示所有回复 -->
                <button style="width: 4%; aspect-ratio: 1/1; margin-left: 2%; margin-top: 9px; border: none; background-color: white;">
                    <el-avatar shape="square" :src="item.writer_avatar"/>
                </button>

                <div style="margin-left: 4%;">
                    <div style="margin-top: 9px;" class="reply-username-font-style">{{ item.writer_name }}</div>
                    <div style="margin-top: 4px; width: 100%" class="content-font-style">{{ item.comment_content }}</div>
                </div>
                
            </div>

            <el-divider/>
        </div>
        

    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { Edit, Delete } from '@element-plus/icons-vue'

export default {
    data() {
        return {
            my_id: JSON.parse(sessionStorage.getItem("id")),
            poster_id: 0,
            user_name: "发布打卡者昵称",
            user_sign: "发布打卡者的签名ABCDABCDABCDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD",
            avatar_pic: "发布打卡者的头像信息",
            post_pics: [
                "./src/Images/testAvatar.jpg", "./src/Images/buaaPoster1.jpg", "./src/Images/testPoster.jpg",
            ],
            content: "",
            favor_list: [],
            comment_list: [],
        }
    },
    props: ["social_post_id"],
    components: {
    },
    mounted() {
        // 获取打卡详细信息
        axios({
            method: "GET",
            url: "/api/pyq/detail",
            params: {
                post_id: social_post_id
            }
        }).then((result) => {
            this.poster_id = result.data.poster_id;
            this.user_name = result.data.poster_name;
            this.user_sign = result.data.poster_sign;
            this.avatar_pic = result.data.poster_avatar;
            this.post_pics = result.data.image_urls;
            this.content = result.data.poster_content;
            this.favor_list = result.data.favors_list;
            this.comment_list = result.data.comments_list;
        })
    },
    computed: {
        favorGroup() {
            let output = [];
            if (this.favor_list.length === 0) {
                return output;
            }
            for (let i = 0; i < this.favor_list.length; i += 12) {
                output.push(this.favor_list.slice(i, i + 12));
            }
            return output;
        }
    },
    methods: {
        deletePost() {
            let content = {
                user_id: JSON.parse(sessionStorage.getItem("id")),
                post_id: this.social_post_id,
            }
            console.log(content);
            axios({
                method: "POST",
                url: "/api/pyq/delete",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '删除打卡成功！',
                        type: 'success',
                    });
                    location.reload();
                }
            })
        }
    }
}

</script>

<style scoped>
.flex-layout {
    display: flex;
}

.flex-column-layout {
    display: flex;
    flex-direction: column;
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

.content-font-style {
    font-size: 16px;
    color: #101010;
}

.el-divider--vertical {
    border-left: 1px solid #e5e6eb !important;
    margin: 0px !important;
    height: auto !important;
}

.el-divider--horizontal {
    border-top: 1px solid #e5e6eb !important;
    margin: 6px !important;
}

.reply-username-font-style {
    font-size: 16px;
    overflow: hidden;
    color: #165dff;
}
</style>