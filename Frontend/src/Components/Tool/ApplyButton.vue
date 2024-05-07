<template>
    <el-button type="primary" plain @click="this.visible = true">申请权限</el-button>
    <el-dialog v-model="this.visible" title="申请信息填写" width="500">
        <el-radio-group v-model="this.apply_type">
            <el-radio-button label="申请教师" :value=0 />
            <el-radio-button label="申请助教" :value=1 />
        </el-radio-group>

        <el-input
            v-model="this.content"
            style="width: 100%; margin-top: 12px;"
            :autosize="{ minRows: 3, maxRows: 6}"
            type="textarea"
            placeholder="请输入申请信息"
        />

        <div style="margin-top: 12px; color: #86909c">注：只能上传一个证明文件，已经选择后再次选择文件会覆盖之前的文件</div>
        <div class="flex-layout" style="margin-top: 12px;">
            <el-upload
                :limit="1"
                :auto-upload="false"
                v-model:file-list="this.file"
            >
                <template #trigger>
                    <el-button type="primary" plain >选择文件</el-button>
                </template>
                <el-button type="primary" @click="submitUpload" style="margin-left: 16px;"> 
                    上传文件  
                </el-button>
            </el-upload>
        </div>

        <el-button type="primary" style="margin-top: 4px;" @click="sendApply">发送申请</el-button>
        
    </el-dialog>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export default {
    data() {
        return {
            visible: false,
            file: [],
            url: "",
            apply_type: 0,
            content: "",
        }
    },
    props: ["section_id"],
    methods: {
        async submitUpload() {
            try {
                console.log(this.file);
                const file_upload = this.file[0].raw;
                const formData = new FormData();
                formData.append('file', file_upload);
                formData.append('name', "");
                formData.append('type', "");
                formData.append('publisher_id', JSON.parse(sessionStorage.getItem("id")))

                let response = await axios.post("/api/posts/write/uploadResource", formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                console.log(response.data);
                this.url = response.data.url;
                console.log(this.url);
            } catch (error) {
                console.log("error")
            }
        },
        sendApply() {
            let content = {
                user_id: JSON.parse(sessionStorage.getItem("id")),
                section_id: parseInt(this.section_id),
                type: this.apply_type,
                detail: this.content,
                file: this.url,
            }
            console.log(content);
            axios({
                method: "POST",
                url: "/api/user/apply",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '权限申请信息上传成功！',
                        type: 'success',
                    });
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
</style>
