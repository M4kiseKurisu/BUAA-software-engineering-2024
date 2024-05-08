<template>
    <el-button type="danger" plain size="small" @click="this.visible = true">举报</el-button>
    <el-dialog v-model="this.visible" title="举报内容" width="400">
        <el-input
            v-model="this.content"
            style="width: 100%"
            :autosize="{ minRows: 3, maxRows: 6}"
            type="textarea"
            placeholder="请输入举报信息"
        />
        <div class="right-layout">
            <el-button type="danger" style="margin-top: 12px;" @click="this.sendReport">发送举报</el-button>
        </div>
        
    </el-dialog>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

export default {
    data() {
        return {
            visible: false,
            content: "",
        }
    },
    props: ["type", "id"],
    methods: {
        sendReport() {
            if (this.type === 0) {  // 举报用户
                let content = {
                    id: this.id,
                    detail: this.content,
                }
                axios({
                    method: "POST",
                    url: "/api/user/report",
                    data: content,
                }).then((result) => {
                    console.log(result);
                    if(result.data.success) {
                        this.$message({
                            showClose: true,
                            message: '用户举报信息上传成功！',
                            type: 'success',
                        });
                    }
                })
            }
            else if (this.type === 1) {  // 举报帖子
                let content = {
                    id: this.id,
                    detail: this.content,
                }
                axios({
                    method: "POST",
                    url: "/api/posts/report",
                    data: content,
                }).then((result) => {
                    console.log(result);
                    if(result.data.success) {
                        this.$message({
                            showClose: true,
                            message: '帖子举报信息上传成功！',
                            type: 'success',
                        });
                    }
                })
            }
            else if (this.type === 2) {  // 举报评论
                let content = {
                    id: this.id,
                    detail: this.content,
                }
                axios({
                    method: "POST",
                    url: "/api/comment/report",
                    data: content,
                }).then((result) => {
                    console.log(result);
                    if(result.data.success) {
                        this.$message({
                            showClose: true,
                            message: '评论举报信息上传成功！',
                            type: 'success',
                        });
                    }
                })
            }
            else if (this.type === 3) {  // 举报回复
                let content = {
                    id: this.id,
                    detail: this.content,
                }
                axios({
                    method: "POST",
                    url: "/api/reply/report",
                    data: content,
                }).then((result) => {
                    console.log(result);
                    if(result.data.success) {
                        this.$message({
                            showClose: true,
                            message: '回复举报信息上传成功！',
                            type: 'success',
                        });
                    }
                })
            }
        }
    }
}
</script>

<style scoped>
.right-layout {
    display: flex;
    justify-content: flex-end;
}
</style>