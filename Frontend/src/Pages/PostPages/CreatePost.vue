<template>
<div class="personal-course-page-container">
    <div class="personal-course-title">创建帖子</div>

    <div class="first-line">
        <div class="first-line-1">
            <div class="post-title-font">帖子标题：</div>
            <div>
                <el-input :class="{'red-border': title_warning.length > 0}" v-model="this.inputTitle" style="width: 450px" placeholder="输入标题内容" />
                <div v-if="title_warning.length > 0" class="warning-css" style="margin-top: 2px;">{{ title_warning }}</div>
            </div>
        </div>
        
        <div class="first-line-1">
            <div class="post-title-font">帖子类型：</div>
            <div>
                <el-select :class="{'red-border': post_category_warning.length > 0}"
                    v-model="this.PostcategoryValue" placeholder="选择帖子类型" style="width: 200px">
                    <el-option v-for="item in this.Postcategory" :key="item.value" :label="item.label" :value="item.value" />
                </el-select>
                <div v-if="post_category_warning.length > 0" class="warning-css" style="margin-top: 2px;">{{ post_category_warning }}</div>
            </div>
        </div>

    </div>

    <div class="first-line">

        <div class="post-title-font">添加标签：</div>
        <div class="tag-in-container">
            <el-input
                v-if="inputVisible"
                ref="InputRef"
                v-model="inputValue"
                class="w-20"
                @keyup.enter="handleInputConfirm"
                @blur="handleInputConfirm"
                style="width: 100px"
            />
            <el-button v-else :class="{'button-new-tag': true, 'red-border': tag_warning.length > 0}" @click="showInput">
                添加新标签
            </el-button>

            <el-tag class="tag-in-container-content"
                v-for="tag in dynamicTags"
                :key="tag"
                closable
                :disable-transitions="false"
                @close="handleClose(tag)"
            >
                {{ tag }}
            </el-tag>
            <div v-if="tag_warning.length > 0" class="warning-css" style="margin-top: 2px;">{{ tag_warning }}</div>
        </div>

        <div style="margin-left: 20px; margin-right: -10px;" class="post-title-font">附加资源：</div>
        <el-upload v-model:file-list="this.fileList" :show-file-list="true" :auto-upload="false" 
            action="/api/posts/write/uploadResource" :data="getUploadData" @change="handleFileChange">
            <el-button v-if="parseInt(this.PostcategoryValue) === 1" class="button-upload-file">选择资源</el-button>
            <el-button v-else disabled class="button-upload-file">选择资源</el-button>
        </el-upload>

        <div>
            <el-button v-if="parseInt(this.PostcategoryValue) === 1" 
                :class="{'button-upload-file': true, 'red-border': upload_warning.length > 0}" @click="uploadFile">上传资源</el-button>
            <el-button v-else disabled class="button-upload-file">上传资源</el-button>
            <div v-if="upload_warning.length > 0" class="warning-css" style="margin-top: 2px; margin-left: 20px;">{{ upload_warning }}</div>
        </div>
    </div>

    <div class="first-line">
        <!-- <div class="tag-in-container">
            <el-input
                v-if="inputVisible"
                ref="InputRef"
                v-model="inputValue"
                class="w-20"
                @keyup.enter="handleInputConfirm"
                @blur="handleInputConfirm"
                style="width: 100px"
            />
            <el-button v-else class="button-new-tag" @click="showInput">
                添加新标签
            </el-button>

            <el-tag class="tag-in-container-content"
                v-for="tag in dynamicTags"
                :key="tag"
                closable
                :disable-transitions="false"
                @close="handleClose(tag)"
            >
                {{ tag }}
            </el-tag>
        </div>

        <el-upload v-model:file-list="this.fileList" :show-file-list="false" :auto-upload="false" action="#">
            <el-button v-if="parseInt(this.PostcategoryValue) === 1" class="button-upload-file">上传资源</el-button>
            <el-button v-else disabled class="button-upload-file">上传资源</el-button>
        </el-upload> -->

        <div class="post-title-font">帖子摘要：</div>
        <div>
            <el-input v-model="this.inputContent" style="width: 600px" 
                :autosize="{ minRows: 1, maxRows: 2 }" type="textarea" placeholder="输入摘要内容" 
                :class="{'red-border': content_warning.length > 0}"/>
            <div v-if="content_warning.length > 0" class="warning-css" style="margin-top: 2px;">{{ content_warning }}</div>
        </div>
    </div>

    <div class="first-line" v-if="this.sectionId == 0">
        <div class="post-title-font">学校信息：</div>
        <div>
            <el-select v-model="this.school_choice" placeholder="选择学校" style="width: 240px">
                <el-option
                    v-for="item in school_options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.label"
                />
            </el-select>
        </div>

        <div class="post-title-font" style="margin-left: 10px;">升学分享类型：</div>
        <div>
            <el-select v-model="this.graduate_choice" placeholder="选择类型" style="width: 240px">
                <el-option
                    v-for="item in graduate_options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.label"
                />
            </el-select>
        </div>
    </div>

    <div class="inputPostContainer" style="border: 1px solid #e5e6eb">
        <Toolbar
            style="border-bottom: 1px #e5e6eb"
            :editor="editorRef"
            :defaultConfig="toolbarConfig"
            :mode="mode"
        />
        <Editor
            style="height: 500px; overflow-y: hidden;"
            v-model="valueHtml"
            :defaultConfig="editorConfig"
            :mode="mode"
            @onCreated="handleCreated"
        />
        <el-button class="button-upload-post" @click="handleUpload">发布帖子</el-button>
    </div>

    <!-- 此处为界面标题 -->
</div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

import '@wangeditor/editor/dist/css/style.css' // 引入 css

import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

export default {
    components: { Editor, Toolbar },
    methods: {
        handleUpload() {
            const contentToUpload = this.valueHtml; // 获取编辑器中的内容
            console.log(contentToUpload);

            //上传资源处理
            // for (let i = 0; i < this.fileList.length; i++) {
            //     let input = {
            //         name: "Try",
            //         publisher_id: JSON.parse(sessionStorage.getItem("id")),
            //         file: this.fileList[i],
            //         type: "",
            //     }
            //     console.log(input);
            //     axios({
            //         method: "POST",
            //         url: "/api/posts/write/uploadResource",
            //         data: input,
            //     }).then((result) => {
            //         console.log(result);
            //     })
            // }

            //注册账号信息打包

            if (this.inputTitle.length > 30) {
                this.title_warning = "标题内容不能大于30个字";
            } else if (!this.inputTitle || this.inputTitle.length == 0){
                this.title_warning = "标题内容不能为空";
            } else {
                this.title_warning = "";
            }

            if (!this.PostcategoryValue || this.PostcategoryValue.length == 0) {
                this.post_category_warning = "需要选择帖子类型";
            } else if (this.filelistUrl.length == 0 && this.PostcategoryValue == "1") {
                this.post_category_warning = "若不上传资源请选择帖子类型为普通帖子";
            } else {
                this.post_category_warning = "";
            }

            if (!this.dynamicTags || this.dynamicTags.length == 0) {
                this.tag_warning = "需要插入至少一个标签";
            } else if (this.dynamicTags.length > 3) {
                this.tag_warning = "最多只能插入三个标签";
            } else {
                this.tag_warning = "";
                for (let i = 0; i < this.dynamicTags.length; i++) {
                    if (this.dynamicTags[i].length == 0) {
                        this.tag_warning = "标签内容不能为空";
                        break;
                    }
                }
            }

            if (!this.inputContent || this.inputContent.length == 0) {
                this.content_warning = "帖子摘要不能为空";
            } else if (this.inputContent.length > 100) {
                this.content_warning = "帖子摘要不能超过100字";
            }  else {
                this.content_warning = "";
            }

            if (this.is_loading) {
                this.upload_warning = "请等待资源上传完成后再发布帖子"
            } else {
                this.upload_warning = "";
            }
            
            if (this.title_warning.length > 0 || this.post_category_warning.length > 0
                || this.tag_warning.length > 0 || this.content_warning.lnegth > 0
                || this.upload_warning.length > 0) {
                return;
            }

            if (this.sectionId == 0) {
                if (this.school_choice == this.graduate_choice) {
                    this.dynamicTags.push(this.school_choice);
                }
                else {
                    this.dynamicTags.push(this.school_choice);
                    this.dynamicTags.push(this.graduate_choice);
                }
            }


            let content = {
                section_id: this.sectionId,
                author_id: JSON.parse(sessionStorage.getItem("id")),
                title: this.inputTitle,
                content: contentToUpload,
                category: parseInt(this.PostcategoryValue),
                tags: this.dynamicTags,
                images: this.images,
                resources: this.filelistUrl,
                intro: this.inputContent,
            }

            console.log(content);

            const loadingMessage = this.$message({
                showClose: true,
                message: '正在发送帖子',
                type: 'info',
                duration: 0, // 设置持续时间为 0，表示不自动关闭
            });

            axios({
                method: "POST",
                url: "/api/posts/write",
                data: content,
            }).then((result) => {
                console.log(result);
                console.log('in1');
                loadingMessage.close();
                console.log('in2');
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '发帖成功！',
                        type: 'success',
                    });
                    //location.reload();
                    this.$router.push({ path: "/MainPage/Course_Center/PostCenter/" + this.sectionId});
                } else {
                    this.$message({
                        showClose: true,
                        message: result.data.info,
                        type: 'error',
                    });
                }
            })
        },
        handleClose(tag) {
            this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        },
        showInput() {
            this.inputVisible = true;
            nextTick(() => {
                this.$refs.InputRef.input.focus();
            });
        },
        handleInputConfirm() {
            console.log(this.dynamicTags);
            if (this.inputValue) {
                this.dynamicTags.push(this.inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
        },
        handleRemove(uploadFile, uploadFiles) {
            console.log(uploadFile, uploadFiles)
        },
        handleFileChange(file) {
            this.fileList = [file];
        },
        getUploadData() {
            return {
                // 传递其他参数，如 name、publisher_id、type
                name: "Try",
                publisher_id: JSON.parse(sessionStorage.getItem("id")),
                type: ""
            };
        },
        async uploadFile() {
            try {
                if (this.fileList.length === 0) {
                    // 没有选择文件，进行处理
                    return;
                }
                const loadingMessage = this.$message({
                    showClose: true,
                    message: '正在上传资源',
                    type: 'info',
                    duration: 0, // 设置持续时间为 0，表示不自动关闭
                });
                this.is_loading = true;

                //console.log(this.fileList);
                for (let i = 0; i < this.fileList.length; i++) {
                    const formData = new FormData();
                    formData.append('file', this.fileList[i].raw);
                    formData.append('name', "");
                    formData.append('type', "");
                    formData.append('publisher_id', JSON.parse(sessionStorage.getItem("id")))

                    let response = await axios.post("/api/posts/write/uploadResource", formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        }
                    });

                    console.log(response.data);
                    this.filelistUrl.push(response.data.url);
                }

                loadingMessage.close();
                this.is_loading = false;
                this.$message({
                    showClose: true,
                    message: '资源上传成功！',
                    type: 'success',
                });
            } catch (error) {
                console.log("error")
            }
        }
    },
    created() {
        this.sectionId = this.$route.params.sectionId;
        console.log(this.sectionId);
    },
    data() {
        return {
            inputTitle: "",
            Postcategory: [
                {
                    value: '0',
                    label: '普通帖子',
                },
                {
                    value: '1',
                    label: '资源帖子',
                },
            ],
            PostcategoryValue: "",
            inputValue: '',
            inputVisible: false,
            dynamicTags: [],
            fileList: [],  //上传资源列表
            sectionId: 1,
            inputContent: "",
            filelistUrl: [],
            school_choice: "其他",
            school_options: [
                {
                    value: '0',
                    label: '北京航空航天大学',
                },
                {
                    value: '1',
                    label: '清华大学',
                },
                {
                    value: '2',
                    label: '北京大学',
                },
                {
                    value: '3',
                    label: '复旦大学',
                },
                {
                    value: '4',
                    label: '上海交通大学',
                },
                {
                    value: '5',
                    label: '浙江大学',
                },
                {
                    value: '6',
                    label: '南京大学',
                },
                {
                    value: '7',
                    label: '中国人民大学',
                },
                {
                    value: '8',
                    label: '中国科学技术大学',
                },
                {
                    value: '9',
                    label: '其他',
                }
            ],
            graduate_choice: "其他",
            graduate_options: [
                {
                    value: '0',
                    label: '考研升学',
                },
                {
                    value: '1',
                    label: '保研升学',
                },
                {
                    value: '2',
                    label: '出国升学',
                },
                {
                    value: '3',
                    label: '其他',
                },
            ],

            title_warning: "",
            post_category_warning: "",
            tag_warning: "",
            content_warning: "",
            upload_warning: "",
            is_loading: false,
        }
    },
    setup() {
        // 编辑器实例，必须用 shallowRef
        const editorRef = shallowRef()

        // 内容 HTML
        const valueHtml = ref('<p></p>')

        // 模拟 ajax 异步获取内容
        onMounted(() => {
            setTimeout(() => {
                valueHtml.value = '<p></p>'
            }, 1500)
        })

        const toolbarConfig = {
            excludeKeys: [
                "group-video",  // 取消视频按钮
            ]
        }

        const images = ref([]);

        const editorConfig = { 
            placeholder: '请输入内容', 
            MENU_CONF: {
                uploadImage: {
                // 自定义上传
                    async customUpload(file, insertFn) {
                        const formData = new FormData();
                        formData.append('file', file);
                        upload(
                            '/api/posts/write/uploadImage',  //url
                            formData,  //data
                            (message)=>{
                                console.log(message.url);
                                images.value.push(message.url);
                                insertFn(message.url, message.alt, message.href)  //success
                            }
                        )
                    }     
                }
            }
        }

        function upload(url, data, success){
            console.log("start uploading pic!")
            console.log(url);
            console.log(data);
            axios.post(url, data).then(({data})=> {
                console.log(data);
                if(data.success) {
                    success(data, data.isSuccess)
                }
            })
        }   

        // 组件销毁时，也及时销毁编辑器
        onBeforeUnmount(() => {
            const editor = editorRef.value
            if (editor == null) return
            editor.destroy()
        })

        const handleCreated = (editor) => {
            editorRef.value = editor // 记录 editor 实例，重要！
        }

        return {
            editorRef,
            valueHtml,
            mode: 'default', // 或 'simple'
            toolbarConfig,
            editorConfig,
            handleCreated,
            images,
        };
    }
}
</script>

<style scoped>
.personal-course-page-container {
    width: calc(100vw - 205px);
    min-width: 1174px;
}

.personal-course-title {
    height: 34px;
    font-size: 30px;
    color: #101010;
    margin-left: 38px;
    margin-top: 29px;
    margin-bottom: 40px;
}

.first-line {
    display: flex;
    margin-bottom: 20px;
    padding-left: 2%;
}

.first-line-1 {
    display: flex;
    margin-right: 20px;
}

.post-title-font {
    color: #101010;
    margin-top: 3px;
    margin-right: 8px;
}

.inputPostContainer {
    width: 95%;
    margin-left: 2%;
}

.tag-in-container {
    display: inline;
    
}

.tag-in-container-content {
    margin-right: 4px;
}

.button-new-tag {
    margin-right: 8px;
}

.button-upload-file {
    margin-left: 20px;
}

.button-upload-post {
    margin-left: 20px;
    margin-bottom: 10px;
}

.red-border {
    border: 1px solid red;
}

.warning-css {
    color: red;
    font-size: 12px;
}
</style>