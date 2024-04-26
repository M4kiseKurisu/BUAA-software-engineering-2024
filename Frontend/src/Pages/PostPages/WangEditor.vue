<template>
    <div style="border: 1px solid #ccc">
      <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editorRef"
        :defaultConfig="toolbarConfig"
        :mode="mode"
      />
      <Editor
        style="height: 200px; overflow-y: hidden;"
        v-model="valueHtml"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="handleCreated"
      />
      
    </div>
    <div class="upload-button-container">
        <button class="upload-button" @click="handleUpload">上传</button>
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

        if (this.isComment) {
            //发布帖子回复
            let content = {
                post_id: this.post_id,
                author_id: this.author_id,
                content: contentToUpload,
                //images: this.images,
            }

            console.log(content);

            axios({
                method: "POST",
                url: "/api/posts/comment",
                data: content,
            }).then((result) => {
                console.log(result);
                location.reload();
            })
        }
    }
  },
  props: ["isComment", "post_id", "author_id"],
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
            "insertVideo",  // 取消视频按钮
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
            if(data.success)
                success(data, data.isSuccess)
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
      mode: 'simple', // 或 'simple'
      toolbarConfig,
      editorConfig,
      handleCreated,
      images,
    };
    }
}
</script> 

<style scoped>
.upload-button-container {
    display: flex;
    justify-content: flex-end;
}

.upload-button {
    border-radius: 4px;
    border: none;
    background-color: #165dff;
    color: white;
    font-size: 14px;
    height: 30px;
    width: 80px;
    margin-top: 17px;
    margin-right: 18px;
}
</style>