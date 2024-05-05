<template>
    <div class="breadcrumb">
        <BreadcrumbLabel :routeNames="route" />
    </div>
    <div class="create_group_container">
        <div
            style="width: 700px;height: 700px;background-color: white;border: 1px solid #ccc;border-radius: 2%;display: flex;flex-direction: column;">
            <div style="width: 100%;height: 50px;display: flex;align-items: center;justify-content: center;">
                <span style="font-size: 1.5em;font-weight: bold;">创建团体</span>
            </div>
            <!-- <el-upload v-model:file-list="this.coverList" :limit="1" :show-file-list="true" :auto-upload="false" action="#">
                <button class="button-changeavatar">选择封面</button>
            </el-upload>
            <el-button @click = "this.coverList = []">删除选择</el-button>
            <img :src="this.coverList[0]">
            <img :src="getImageUrl(coverList[0].raw)" v-if="coverList[0] && coverList[0].status === 'ready'">
            {{ coverList[0] }} -->
            <div style="margin-left: 20px;width: 100%;display: flex;align-items: center;margin-top: 20px;">
                <span class="create_group_itemtext">标题：</span>
                <el-input v-model="inputTitle" style="width: 240px" placeholder="输入标题" />
            </div>
            <div style="margin-left: 20px;width: 100%;display: flex;margin-top: 20px;">
                <span class="create_group_itemtext">简介：</span>
                <textarea id="submitText" type="text" class="group_create_custom-input" placeholder="输入团体简介"
                    v-model="inputContent"></textarea>
            </div>
            <div style="margin-left: 20px;width: 100%;display: flex;margin-top: 20px;align-items: center;">
                <span class="create_group_itemtext">标签：</span>
                <div class="tag-in-container">
                    <el-input v-if="inputVisible" ref="InputRef" v-model="inputValue" class="w-20"
                        @keyup.enter="handleInputConfirm" @blur="handleInputConfirm" style="width: 100px" />
                    <el-button v-else class="button-new-tag" @click="showInput">
                        添加新标签
                    </el-button>

                    <el-tag class="tag-in-container-content" v-for="tag in inputDynamicTags" :key="tag" closable
                        :disable-transitions="false" @close="handleClose(tag)">
                        {{ tag }}
                    </el-tag>
                </div>
            </div>
            <!-- 选择图片以及展示操作 -->
            <div style="margin-left: 20px;width: 100%;display: flex;align-items: center;margin-top: 20px;">
                <span class="create_group_itemtext">封面：</span>
                <el-upload v-model:file-list="coverList" :limit="1" :show-file-list="false" :auto-upload="false" action="#">
                    <el-button @click="coverList = []" plain>选择图片</el-button>
                </el-upload>
            </div>
            <div style="width: 100%;margin-top: 20px;margin-left: 20px; display: flex;">
                <span class="create_group_itemtext"> 预览封面：</span>
                <img :src="coverUrl" v-if="coverList[0] && coverList[0].status === 'ready'"
                    style="height: 150px;width: 150px;">
            </div>
            <!-- {{ coverList[0] }} -->
            <!--选图结束-->
            <div style="margin-left: 20px;width: 100%;display: flex;margin-top: 20px;align-items: center;">
                <span class="create_group_itemtext">限制人数：</span>
                <el-input-number v-model="inputPersonNum" :min="3" :max="99" />
            </div>
            <div style="margin-left: 20px;width: 100%;display: flex;margin-top: 20px;align-items: center;">
                <span class="create_group_itemtext">是否需要审核：</span>
                <el-radio-group v-model="inputIsExamine" class="ml-4">
                    <el-radio :value="true" size="large">是</el-radio>
                    <el-radio :value="false" size="large">否</el-radio>
                </el-radio-group>
            </div>
            <div style="width: 100%;margin-top: auto;height: 140px;display: flex;">
                <div style="width: 50%;height: 100%;display: flex;align-items: center;justify-content: center;">
                    <el-button type="primary" plain @click="this.$router.go(-1);">返回广场</el-button>
                </div>
                <div style="width: 50%;height: 100%;display: flex;align-items: center;justify-content: center;">
                    <el-button type="primary" plain @click="postCreatedGroup">发布团体</el-button>
                </div>
            </div>

        </div>
    </div>
</template>

<script>
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue";
import { ElMessage } from 'element-plus';
import axios from 'axios';
import { result } from "lodash";
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
export default {
    data() {
        return {
            route: ['学习团体', '创建团体'],
            coverList: [],
            inputTitle: '',
            inputContent: '',
            inputIsExamine: null,
            inputPersonNum: 3,
            inputValue: '',
            inputVisible: false,
            inputDynamicTags: [],
        }
    },
    components: {
        BreadcrumbLabel,
    },
    methods: {
        getImageUrl(file) {
            return URL.createObjectURL(file);
        },
        handlePersonNumChange(val) {
            //this.inputPersonNum = val;
        },
        handleClose(tag) {
            this.inputDynamicTags.splice(this.inputDynamicTags.indexOf(tag), 1);
        },
        showInput() {
            this.inputVisible = true;
            nextTick(() => {
                this.$refs.InputRef.input.focus();
            });
        },
        handleInputConfirm() {
            //console.log(this.inputDynamicTags);
            if (this.inputValue) {
                this.inputDynamicTags.push(this.inputValue);
            }
            this.inputVisible = false;
            this.inputValue = '';
        },
        postCreatedGroup() {
            if (this.inputTitle == '') {
                ElMessage({
                    message: '信息不完善，请填写标题',
                    type: 'warning',
                    plain: true,
                })
                return;
            }
            if (this.inputContent == '') {
                ElMessage({
                    message: '信息不完善，请填写简介',
                    type: 'warning',
                    plain: true,
                })
                return;
            }
            //console.log(this.inputDynamicTags);
            if (this.inputIsExamine == null) {
                ElMessage({
                    message: '信息不完善，请选择是否需要入群审核',
                    type: 'warning',
                    plain: true,
                })
                return;
            }
            if (this.coverList.length == 0) {
                ElMessage({
                    message: '信息不完善，请选择团体封面',
                    type: 'warning',
                    plain: true,
                })
                return;
            }
            var url = this.getImageUrl(this.coverList[0].raw);
            axios({
                method: "POST",
                url: "/api/group/create",
                data: {
                    name: this.inputTitle,
                    permitted_num: this.inputPersonNum,
                    content: this.inputContent,
                    is_examine: this.inputIsExamine,
                    tags: this.inputDynamicTags,
                    image: url,
                }
            }).then((result) => {
                console.log(result);
                ElMessage({
                    message: '团体发布成功',
                    type: 'success',
                    plain: true,
                })
            })
        }
    },
    computed: {
        coverUrl() {
            if (this.coverList.length > 0 && this.coverList[0].status === 'ready') {
                return this.getImageUrl(this.coverList[0].raw);
            }
            return ''; // 没有封面或封面未准备好时返回空字符串
        }
    },
}
</script>

<style scoped>
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}

.create_group_container {
    width: calc(100vw - 220px);
    height: calc(100vh - 120px);
    min-height: 750px;
    min-width: 800px;
    background-color: aliceblue;
    display: flex;
    justify-content: center;
    align-items: center;
}

.create_group_itemtext {
    font-size: 1.2em;
    font-weight: bold;
    color: dimgray;
}

.group_create_custom-input {
    width: 350px;
    height: 35px;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 1.2em;
    color: dimgray;
    outline: none;
    transition: border-color 0.3s ease;
    resize: none;
}

.group_create_custom-input:focus {
    border-color: #5e9cd3;
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
</style>