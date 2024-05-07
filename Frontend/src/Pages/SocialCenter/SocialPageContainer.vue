<template>
    <div class="page-container">

        <!-- 此处为面包屑组件 -->
        <div class="breadcrumb" style="margin-bottom: 24px"><BreadcrumbLabel :routeNames="route" /></div>

        <div class="flex-container">
            <div class="container-left">
                <div style="margin-left: 5%; margin-bottom: 12px;" class="flex-container-between">
                    <el-radio-group v-model="radio">
                        <el-radio-button label="查看本人打卡" value="0" />
                        <el-radio-button label="查看打卡广场" value="1" />
                    </el-radio-group>

                    <el-button plain @click="this.dialogTableVisible = true" style="margin-right: 5%;">
                        发布新打卡
                    </el-button>
                </div>
                <div v-if="radio === '0'"><MySocialPost @childMethod="showDetail"/></div>
                <div v-if="radio === '1'"><OthersSocialPost /></div>
            </div>
            <el-divider v-if="radio === '0'" direction="vertical"/>
            <div class="container-right">
                <div v-if="this.detail_post_id != 0"><SocialPostDetail :social_post_id="this.detail_post_id"/></div>
                <!-- <div v-if="true"><SocialPostDetail :social_post_id="1"/></div> -->
            </div>
        </div>    
        
        

        <!-- 以下为弹出的发布打卡框 -->
        <el-dialog v-model="dialogTableVisible" title="编辑新打卡" width="75%">
            <div class="flex-container">
                <div style="margin-left: 4%; margin-right: 4%; width: 49%; padding-bottom: 12px;">
                    <div class="title-for-dialog-font" style="margin-top: 15px;">编辑打卡文字</div>
                    <el-input
                        v-model="this.social_post_text"
                        style="width: 100%; margin-top: 16px;"
                        :autosize="{ minRows: 8, maxRows: 8 }"
                        type="textarea"
                        placeholder="输入打卡文字"
                    />   
                </div>

                <el-divider direction="vertical"/>

                <div style="width: 36%; margin-right: 4%; margin-left: 3%;">
                    

                    <div class="flex-container" style="margin-top: 15px;">
                        <div class="title-for-dialog-font">上传打卡图片</div>
                        <el-upload v-model:file-list="image_list" :show-file-list="false" :auto-upload="false" :on-change="test" style="margin-left: 16px;">
                            <el-button type="primary" plain size="small">选择图片</el-button>
                        </el-upload>
                    </div>

                    <el-row v-for="(item, index1) in images" :gutter="20" style="margin-top: 16px;">
                        <el-col v-for="(item2, index2) in item" :key="index2" :span="8">
                            <div style="width: 100%; aspect-ratio: 1/1; border: 1px solid #e5e6eb;">
                                <el-popover
                                    placement="top-start"
                                    trigger="hover"
                                >
                                    <template #reference>
                                        <el-image :src="item2" style="width: 100%; height: 100%" />
                                    </template>
                                    <div style="width: 100%" class="flex-container-center">
                                        <el-button type="danger" plain @click="deleteImage(index1, index2)">删除图片</el-button>
                                    </div>
                                </el-popover>
                            </div>
                        </el-col>
                    </el-row>
                    
                    
                </div>

            </div>

            <el-button type="primary" style="margin-left: 4%; margin-top: 16px;" @click="uploadPost">上传打卡</el-button>
        </el-dialog>

    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

import BreadcrumbLabel from '@/Components/Tool/BreadcrumbLabel.vue';

import MySocialPost from './MySocialPost.vue'
import SocialPostDetail from './SocialPostDetail.vue'
import OthersSocialPost from './OthersSocialPost.vue'

export default {
    components: {
        BreadcrumbLabel,
        MySocialPost,
        SocialPostDetail,
        OthersSocialPost,
    },
    data(){
        return {
            route: ["展示模块", "打卡信息"],
            detail_post_id: 0,
            radio: "0",
            dialogTableVisible: false,
            social_post_text: "",
            image_list: [],
            preview_urls: [],
            image_urls: [],
        }
    },
    computed: {
        images() {
            let show_images = [];
            if (this.preview_urls.length === 0) {
                return show_images;
            }

            for (let i = 0; i < this.preview_urls.length; i += 3) {
                show_images.push(this.preview_urls.slice(i, i + 3));
            }

            return show_images;
        },
    },
    methods: {
        showDetail(id) {
            this.detail_post_id = id;
            console.log(this.detail_post_id);
        },
        test(file, fileList) {
            //console.log(file.raw instanceof Blob);
            console.log(file.raw);
            const newfile = file.raw;
            const formData = new FormData();
            formData.append("file", newfile);
            axios.post("/api/posts/write/uploadImage", formData).then(({data})=> {
                console.log(data);
                if(data.success) {
                    this.image_urls.push(data.url);
                }
            })

            this.image_list = fileList;
            //console.log(this.image_list);
            this.presee(this.image_list);
            
            //console.log(this.preview_urls);
        },
        presee(image_list) {
            this.preview_urls = [];
            // 遍历文件列表
            for (let i = 0; i < image_list.length; i++) {
                const file = image_list[i];
                
                if (file.raw instanceof Blob) {
                    // 使用FileReader对象读取文件
                    const reader = new FileReader();

                    // 在读取完成时触发的回调函数
                    reader.onload = () => {
                        const previewUrl = reader.result; // 获取预览链接
                        this.preview_urls.push(previewUrl); // 将预览链接添加到数组中
                    };

                    // 读取文件
                    reader.readAsDataURL(file.raw);
                }
            }
        },
        deleteImage(index1, index2) {
            let removeIndex = index1 * 3 + index2;
            this.image_list.splice(removeIndex, 1);
            this.image_urls.splice(removeIndex, 1);
            this.presee(this.image_list);
        },
        uploadPost() {
            console.log(this.image_urls);

            let content = {
                user_id: JSON.parse(sessionStorage.getItem("id")),
                image_urls: this.image_urls,
                content: this.social_post_text,
            }
            console.log(content);
            axios({
                method: "POST",
                url: "/api/pyq/send",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '发表打卡成功！',
                        type: 'success',
                    });
                    location.reload();
                }
            })
        }
    },
}
</script>

<style scoped>
.page-container {
    width: calc(100vw - 205px);
}

.flex-container {
    display: flex;
}

.container-left {
    width: 40%;
}

.container-right {
    width: 60%;
}

.el-divider--vertical {
    border-left: 1px solid #e5e6eb !important;
    margin: 0px !important;
    height: auto !important;
}

.flex-container-between {
    display: flex;
    justify-content: space-between;
}

.title-for-dialog-font {
    font-size: 18px;
    color: #101010;
}

.flex-container-center {
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}
</style>