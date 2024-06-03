<template>
    <div style="width: (100/3)%; margin-top : 5px" @click="goToShowPersonInfomation()"
        @contextmenu.prevent="handleRightClick">
        <div style="width: 100%;aspect-ratio: 1/1 ;display: flex;align-items: center;justify-content: center;flex-direction: column;">
            <img :src="headImg" style="width: 80%;aspect-ratio: 1/1 ; border: 1px solid darkgray;border-radius: 10%;">
            <div style="color: dimgray;font-size: 0.9em;">
                {{ partName }}
            </div>
        </div>
    </div>
    <el-dialog v-model="dialog_visible" title="进行权限操作" width="360" v-if="this.authority != 'blocked'">
        <el-button v-if="this.authority != 'assistant'" type="primary" @click="giveAssistantAuthority">给予用户助教权限</el-button>
        <el-button v-else type="warning" @click="delectAssistantAuthority">撤销用户助教权限</el-button>
        <div style="display: flex; margin-top: 12px;">
            <el-button v-if="this.authority != 'teacher'" type="danger" @click="blockPerson">板块内封禁该用户</el-button>
            <el-input v-model="cancel_days" style="width: 80px; margin-left: 16px;" placeholder="输入天数" />
        </div>
        <div style="font-size: 14px; color: #86909c; margin-top: 12px;">注：若不填写封禁天数，则默认永久板块内封禁</div>
    </el-dialog>
</template>

<script>
import axios from 'axios';
import { result } from 'lodash';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
import { ElMessage } from 'element-plus';
export default {
    props: {
        personId: {
            type: Number,
            default: 1,
        },
        sectionId: {
            type: Number,
            default: 1,
        },
    },
    computed:{
        partName(){
            if(this.personName.length > 3){
                return this.personName.substring(0,4) + '...';
            } else {
                return this.personName;
            }
        }
    },
    data() {
        return {
            headImg: '',
            //personId: '',
            personName: '',
            dialog_visible: false,
            //sectionId: 1,
            authority: null,
            cancel_days: '',
        }
    },
    methods: {
        GetInfomation() {
            axios({
                method: "GET",
                url: "/api/user/social/others",
                params: { id: this.personId }
            }).then((result) => {
                //console.log(result);
                this.headImg = result.data.user_avatar;
                this.personName = result.data.name;
            });
        },
        goToShowPersonInfomation() {
            //this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" });
            let routeUrl = this.$router.resolve({
                path: "/MainPage/Course_Center/ShowPersonalInformation/" + this.personId,
            });
            window.open(routeUrl.href, '_blank');
        },
        handleRightClick(event) {
            // 处理右键点击事件的逻辑
            //console.log("鼠标右键点击事件触发了", event);
            axios({
                method: "GET",
                url: 'api/user/authority',
                params: {
                    section: this.sectionId,
                }
            }).then((result) => {
                var selfAuthorith = result.data.info;
                //console.log(result.data.info);
                // if(this.authority != 'teacher'){
                //     if(selfAuthorith == 'teacher'){
                //         this.dialog_visible = true;
                //     }
                // }
                if (this.authority != "teacher" && selfAuthorith == 'teacher') {
                    this.dialog_visible = true;
                }
            })
        },
        giveAssistantAuthority() {
            axios({
                method: "POST",
                url: 'api/section/add/assistant',
                data: {
                    section: this.sectionId,
                    assistant: this.personId,
                }
            }).then((result) => {
                console.log(result);
                if (result.data.success) {
                    ElMessage({
                        message: '操作成功!',
                        type: 'success',
                        plain: true,
                    })
                    this.$router.go(0);
                } else {
                    ElMessage({
                        message: '操作失败!',
                        type: 'error',
                        plain: true,
                    })
                }
            })
        },
        delectAssistantAuthority() {
            axios({
                method: "POST",
                url: 'api/section/delete/assistant',
                data: {
                    section: this.sectionId,
                    assistant: this.personId,
                }
            }).then((result) => {
                console.log(result);
                if (result.data.success) {
                    ElMessage({
                        message: '操作成功!',
                        type: 'success',
                        plain: true,
                    })
                    this.$router.go(0);
                } else {
                    ElMessage({
                        message: '操作失败!',
                        type: 'error',
                        plain: true,
                    })
                }
            })
        },
        blockPerson() {
            var day = this.cancel_days;
            if (day == '') {
                day = 100000000000;
            } else {
                day = parseInt(this.cancel_days);
            }
            axios({
                method: "POST",
                url: 'api/section/block',
                data: {
                    section: this.sectionId,
                    id: this.personId,
                    days: day,
                }
            }).then((result) => {
                console.log(result);
                if (result.data.success) {
                    ElMessage({
                        message: '操作成功!',
                        type: 'success',
                        plain: true,
                    })
                    this.$router.go(0);
                } else {
                    ElMessage({
                        message: '操作失败!',
                        type: 'error',
                        plain: true,
                    })
                }
            })
        },
        getAuthority() {
            axios({
                method: "GET",
                url: 'api/user/authority',
                params: {
                    id: this.personId,
                    section: this.sectionId,
                }
            }).then((result) => {
                //console.log(result.data.info);
                this.authority = result.data.info;
            })
        }
    },

    created() {
        //console.log(this.personId);
        this.GetInfomation();
        this.getAuthority();
    }
}
</script>

<style></style>