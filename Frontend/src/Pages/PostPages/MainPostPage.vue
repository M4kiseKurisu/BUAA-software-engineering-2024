<template>
    <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

    <div class="main-postpage-container">
        <!-- 帖子正文部分 -->
        <div class="post-main-content-container">
            <div class="post-page-header-container">

                <!-- 帖子头部左侧 -->
                <div class="post-page-header-left">
                    <div class="post-main-title">本文的标题ABCDABCDABCD</div>

                    <button class="post-main-delete-button">
                        <el-icon :size="16" color="#86909C"><Delete /></el-icon>
                    </button>
                </div>
                
                <!-- 帖子头部右侧 -->
                <div class="post-page-header-right">
                    <div class="post-page-tag-css">本文标签</div>

                    <div class="icon-and-content">
                        <!-- 喜欢图标 -->
                        <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#d81e06"></path></svg>
                        <div class="like-icon-after-contents">XX人点赞</div>
                    </div>

                    <div class="icon-and-content">
                        <!-- 收藏图标 -->
                        <svg t="1713272491744" class="starfill-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8600" width="200" height="200"><path d="M908.1 353.1l-253.9-36.9L540.7 86.1c-3.1-6.3-8.2-11.4-14.5-14.5-15.8-7.8-35-1.3-42.9 14.5L369.8 316.2l-253.9 36.9c-7 1-13.4 4.3-18.3 9.3-12.3 12.7-12.1 32.9 0.6 45.3l183.7 179.1-43.4 252.9c-1.2 6.9-0.1 14.1 3.2 20.3 8.2 15.6 27.6 21.7 43.2 13.4L512 754l227.1 119.4c6.2 3.3 13.4 4.4 20.3 3.2 17.4-3 29.1-19.5 26.1-36.9l-43.4-252.9 183.7-179.1c5-4.9 8.3-11.3 9.3-18.3 2.7-17.5-9.5-33.7-27-36.3z" p-id="8601" fill="#f4ea2a"></path></svg>
                        <div class="starfill-icon-after-contents">YY人收藏</div>
                    </div>
                </div>
                
            </div>
            
            <el-divider/>

            <div class="post-more-information-line">

                <div class="post-more-information-left">
                    <!-- 作者头像 -->
                    <el-avatar shape="square" :size="60" :src="this.avatarPicture" />

                    <div class="post-writer-information">
                        <div class="post-writer-username">文章作者昵称</div>
                        <div class="post-writer-signature">文章作者的个人签名：ABCDABCDABCDABCDABCDABCDABCDABCDABCD</div>
                    </div>
                </div>

                <div class="post-more-information-right">
                    <button class="post-page-tag-css">关注作者</button>
                </div>

            </div>

            <!-- <div class="post-main-content">
                此处为正文，存在额外的段间距。以下为随机文字：<br>
                计算机视觉领域也探索了基础模型，尽管程度较小。也许最突出的例子是从网络中对齐配对的文本和图像。例如，CLIP和ALIGN使用对比学习来训练文本和图像编码器，以对齐两种模态。一旦训练完成，工程化的文本提示就可以将零样本泛化到新的视觉概念和数据分布。这些编码器也可以与其他模块有效组合，以实现下游任务，例如图像生成（例如，DALL·E）。虽然在视觉和语言编码器方面取得了很大进展，但计算机视觉领域包含了远远超出这个范围的问题，对于其中的许多问题，都不存在充足的训练数据。
            </div>

            <div class="post-main-content">
                注意，如果存在图片，图片和文字之间应该存在间隔：<br>
                Task任务。我们首先将prompt提示的思想将NLP迁移到分割，其中一个prompt提示可以是一组前景/背景点，粗略的框或掩码，自由形式的文本，或者，一般来说，任何指示在图像中分割什么的信息。然后，promptable segmentation task可提示分割任务是给定任何提示返回有效的分割掩码。<br>
                Related tasks 相关任务。 分割是一个广泛的领域：有interactive segmentation交互式分割，edge detection边缘检测，super pixelization超像素化，object proposal generation对象建议生成，foreground segmentation前景分割，semantic segmentation语义分割，instance segmentation实例分割，panoptic segmentation全景分割等。我们的可提示分割任务的目标是产生一个广泛的模型。
            </div> -->
            <div class="post-main-content">
                {{ this.content }}
            </div>

            <div class="post-main-end-line">
                <button class="post-grey-button-below">展开共ZZ条评论</button>
                <button class="post-grey-button-below">去评论</button>
                <div class="post-time-show-font">发帖时间：YY-MM-DD hh-mm</div>
            </div>
        </div>

        <!-- 评论帖子发布位置 -->
        <div class="post-reply-writing-container">

            <!-- 评论者头像及评论内容输入框 -->
            <div class="post-reply-first-line">
                <!-- 回复者头像 -->
                <el-avatar shape="square" :size="50" :src="this.avatarPicture" />

                <!-- 回复编辑框 -->
                <el-input
                    v-model="this.ReplyTextarea"
                    style="margin-left: 18px"
                    :rows="5"
                    type="textarea"
                    placeholder="请输入您的评论"
                />
            </div>

            <button class="send-reply-button-css">发布评论</button>
        </div>

        <!-- 评论帖子发布位置 -->
        <div class="post-reply-container">
            <div class="post-reply-content-first-line">

                <!-- 回复栏位开头左侧 -->
                <div class="post-reply-content-first-line-left">
                    <div class="reply-title-font">ZZ条评论</div>

                    <div class="right-selector">
                        <el-select v-model="sortValue" placeholder="排序方式" style="width: 85px" size="small">
                            <el-option
                                v-for="item in sortOptions"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </div>
                </div>

                <!-- 回复栏位开头右侧 -->
                <div class="post-reply-content-first-line-right">
                    <div class="check-more-reply-font">查看更多评论：</div>

                    <div class="post-reply-content-first-line-right-pagination">
                        <el-pagination :pager-count="6" layout="prev, pager, next" :total="100" />
                    </div>
                </div>
                
            </div>

            <el-divider/>

            <!-- 一条回复的全部内容 -->
            <div class="reply-main-content-container">
                <div class="reply-first-line">

                    <!-- 左侧信息：头像，昵称，tag，删除 -->
                    <div class="reply-first-line-left-content">
                        <!-- 回复者头像 -->
                        <el-avatar shape="square" :size="50" :src="this.avatarPicture" />
                        <div class="replyer-username">评论者昵称</div>

                        <div class="replyer-tag">
                            <div class="post-page-tag-css">作者标签</div>
                        </div>
                        <div class="reply-delete-button">
                            <button class="post-main-delete-button">
                                <el-icon :size="16" color="#86909C"><Delete /></el-icon>
                            </button>
                        </div>
                    </div>

                    <!-- 右侧信息：评论时间，去评论，点赞 -->
                    <div class="reply-first-line-right-content">
                        <div class="reply-time-information">评论时间：YY-MM-DD hh-mm</div>

                        <div class="icon-and-content-2">
                            <!-- 评论图标 -->
                            <svg t="1713327588804" class="comment-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3255" width="200" height="200"><path d="M896 128H128a32 32 0 0 0-32 32v576a32 32 0 0 0 32 32h288v-64H160V192h704v512h-256c-8.832 0-16.832 3.584-22.656 9.376l-159.968 160 45.248 45.248L621.248 768H896a32 32 0 0 0 32-32V160a32 32 0 0 0-32-32" fill="#86909C" p-id="3256"></path><path d="M560 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 560 448M240 448a48 48 0 1 0 95.968 0.032A48 48 0 0 0 240 448M784 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 784 448" fill="#86909C" p-id="3257"></path></svg>
                            <div class="comment-icon-after-contents">去评论</div>
                        </div>


                        <div class="icon-and-content-2">
                            <!-- 喜欢图标 -->
                            <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#d81e06"></path></svg>
                            <div class="like-icon-after-contents">XX人点赞</div>
                        </div>
                    </div>

                </div>

                <!-- 回复正文 -->
                <div class="post-main-content">
                    此处为正文，存在额外的段间距。以下为随机文字：<br>
                    计算机视觉领域也探索了基础模型，尽管程度较小。也许最突出的例子是从网络中对齐配对的文本和图像。例如，CLIP和ALIGN使用对比学习来训练文本和图像编码器，以对齐两种模态。一旦训练完成，工程化的文本提示就可以将零样本泛化到新的视觉概念和数据分布。这些编码器也可以与其他模块有效组合，以实现下游任务，例如图像生成（例如，DALL·E）。虽然在视觉和语言编码器方面取得了很大进展，但计算机视觉领域包含了远远超出这个范围的问题，对于其中的许多问题，都不存在充足的训练数据。
                </div>

                <!-- 评论评论的全部内容 -->
                <div class="replys-reply-container">
                    <div class="replys-reply-first-line-container">

                        <!-- 左侧信息：头像，昵称，tag，删除 -->
                        <div class="reply-first-line-left-content">
                            <!-- 回复者头像 -->
                            <div class="replys-reply-avatar">
                                <el-avatar shape="square" :size="40" :src="this.avatarPicture" />
                            </div>
                            <div class="replyer-username">评论者昵称</div>

                            <!-- <div class="replyer-tag">
                                <div class="post-page-tag-css">作者标签</div>
                            </div> -->

                            <div class="replys-reply-middle-font">回复</div>

                            <div class="replyer-username">评论对象昵称</div>
                            <div class="replys-reply-delete-button">
                                <button class="post-main-delete-button">
                                    <el-icon :size="16" color="#86909C"><Delete /></el-icon>
                                </button>
                            </div>
                        </div>

                        <!-- 右侧信息：评论时间，去评论，点赞 -->
                        <div class="reply-first-line-right-content">
                            <div class="reply-time-information">评论时间：YY-MM-DD hh-mm</div>

                            <div class="icon-and-content-2">
                                <!-- 评论图标 -->
                                <svg t="1713327588804" class="comment-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3255" width="200" height="200"><path d="M896 128H128a32 32 0 0 0-32 32v576a32 32 0 0 0 32 32h288v-64H160V192h704v512h-256c-8.832 0-16.832 3.584-22.656 9.376l-159.968 160 45.248 45.248L621.248 768H896a32 32 0 0 0 32-32V160a32 32 0 0 0-32-32" fill="#86909C" p-id="3256"></path><path d="M560 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 560 448M240 448a48 48 0 1 0 95.968 0.032A48 48 0 0 0 240 448M784 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 784 448" fill="#86909C" p-id="3257"></path></svg>
                                <div class="comment-icon-after-contents">去评论</div>
                            </div>


                            <div class="icon-and-content-2">
                                <!-- 喜欢图标 -->
                                <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#d81e06"></path></svg>
                                <div class="like-icon-after-contents">XX人点赞</div>
                            </div>
                        </div>

                    </div>

                    <!-- 评论评论正文 -->
                    <div class="replys-reply-content">
                        此处为评论，存在额外的段间距。以下为随机文字：<br>
                        计算机视觉领域也探索了基础模型，尽管程度较小。也许最突出的例子是从网络中对齐配对的文本和图像。例如，CLIP和ALIGN使用对比学习来训练文本和图像编码器，以对齐两种模态。一旦训练完成，工程化的文本提示就可以将零样本泛化到新的视觉概念和数据分布。<br>
                        加入@功能尝试：@AAAA，@BBBB，@CCCC
                    </div>
                </div>

                <div class="write-replys-reply">
                    <!-- 回复回复编辑框 -->
                    <el-input
                        v-model="this.ReplysReplyTextarea"
                        :rows="5"
                        type="textarea"
                        placeholder="请输入您的评论"
                    />
                </div>

                <div class="send-replys-reply-button-location">
                    <button class="send-reply-button-css">发布评论</button>
                </div>

                <div class="check-more-replys-reply-line">
                    <div class="check-more-reply-font">查看更多评论：</div>
                    <div class="check-replys-reply-pagination">
                        <el-pagination :pager-count="6" layout="prev, pager, next" :total="50" />
                    </div>
                </div>
                

            </div>

        </div>
    </div>
</template>

<script>
import axios from 'axios';
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// 引入面包屑组件
import BreadcrumbLabel from "../../Components/Tool/BreadcrumbLabel.vue"

import { Delete } from '@element-plus/icons-vue'

export default {
    components: {
        BreadcrumbLabel, 
        Delete, 
    },
    data() {
        return {
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
            avatarPicture: "./src/Images/testAvatar.jpg",  //作者头像
            ReplyTextarea: "",  //回复内容监听
            sortOptions: [  //评论排序方式
                {
                    value: '1',
                    label: '按热度排列',
                },
                {
                    value: '2',
                    label: '按时间排列',
                }
            ],
            ReplysReplyTextarea: "",  //回复回复内容监听
            title: "",
            content: "",
            author_id: "",
            author_head: "",
            author_name: "",
            cerate_time: "",
            tags: [],
            images: [],
            resources: [],
            comments: [],
        }
    },
    mounted() {
        axios({
            method: "GET",
            url: "/api/posts/post",
            params: {
                post_id: 21,
                comment_sort: 1,  //0：时间（正序）；1：热度；2：时间倒序（最新优先）
            }
        }).then((result) => {
            console.log(result)
            this.title = result.data.title;
            this.content = result.data.content;
            this.author_id = result.data.author_id;
            this.author_head = result.data.author_head;
            this.author_name = result.data.author_name;
            this.cerate_time = result.data.cerate_time;
            this.tags = result.data.tags;
            this.images = result.data.images;
            this.resources = result.data.resources;
            this.comments = result.data.comments;
        })
    }
}
</script>

<style scoped>
.breadcrumb {
    margin-top: 19px;
    margin-left: 20px;
    margin-bottom: 17px;
}

.main-postpage-container {
    width: calc(100vw - 205px);
    background-color: rgba(247, 248, 250, 0.7);
    padding-top: 29px;
    padding-bottom: 81px;
}

.post-main-content-container {
    margin-left: 80px;
    margin-right: 80px;
    padding-top: 36px;
    padding-bottom: 36px;
    background-color: white;
    border-radius: 2px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);;
}

.post-page-header-container {
    display: flex;
    justify-content: space-between;
}

.post-main-title {
    font-size: 24px;
    color: #101010;
    height: 34px;
    margin-left: 60px;
    margin-bottom: 30px;
}

.post-page-header-left {
    display: flex;
}

.post-main-delete-button {
    border: none;
    background-color: white;
    height: 16px;
    width: 16px;
    margin-left: 9px;
    margin-top: 8px;
}

.post-page-header-right {
    display: flex;
}

.post-page-tag-css {
    width: 72px;
    height: 26px;
    background-color: #e9f3ff;
    border: 1px solid #3894ff;
    border-radius: 3px;
    font-size: 14px;
    color: #3894ff;
    margin-top: 4px;
    margin-right: 16px;
    display: flex;
    align-items: center;
    justify-content: center; 
}

.icon-and-content {
    display: flex;
    margin-right: 16px;
}

.like-icon {
    margin-top: 4px;
    height: 24px;
    width: 24px;
}

.like-icon-after-contents {
    height: 24px;
    margin-left: 11px;
    margin-top: 7px;
    color: #d81e06;
    font-size: 14px;
}

.starfill-icon {
    margin-top: 4px;
    height: 24px;
    width: 24px;
}

.starfill-icon-after-contents {
    height: 24px;
    margin-left: 11px;
    margin-top: 7px;
    color: #f4ea2a;
    font-size: 14px;
}

.el-divider--horizontal {
    border-top: 1px solid #e5e6eb !important;
    margin: 0px !important;
}

.post-more-information-line {
    display: flex;
    justify-content: space-between;
    margin-bottom: 46px;
}

.post-more-information-left {
    display: flex;
    margin-left: 61px;
    margin-top: 39px;
}

.post-writer-information {
    margin-left: 18px;
}

.post-writer-username {
    height: 28px;
    font-size: 20px;
    color: #165dff;
    margin-top: 1px;
}

.post-writer-signature {
    height: 24px;
    width: 572px;
    overflow: hidden;
    word-wrap: break-word;
    font-size: 16px;
    color: #86909c;
    margin-top: 6px;
}

.post-more-information-right {
    margin-right: 39px;
    margin-top: 54px;
}

.post-main-content {
    width: 88%;
    overflow: hidden;
    word-wrap: break-word;
    color: #101010;
    margin-left: 6%;
    margin-bottom: 20px;
    line-height: 32px;
}

.post-main-end-line {
    display: flex;
    margin-left: 91px;
}

.post-grey-button-below {
    height: 36px;
    padding-left: 8px;
    padding-right: 8px;
    border: none;
    border-radius: 0px;
    background-color: #e5e6eb;
    font-size: 16px;
    color: #86909c;
    margin-right: 16px;
}

.post-time-show-font {
    height: 25px;
    font-size: 16px;
    color: #86909c;
    margin-top: 8px;
}

.post-reply-writing-container {
    margin-top: 39px;
    margin-left: 80px;
    margin-right: 80px;
    padding-top: 30px;
    padding-bottom: 17px;
    background-color: white;
    border-radius: 2px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
}

.post-reply-first-line {
    display: flex;
    margin-left: 61px;
    margin-right: 61px;
}

.send-reply-button-css {
    border-radius: 4px;
    border: none;
    background-color: #165dff;
    color: white;
    font-size: 14px;
    height: 30px;
    width: 80px;
    align-self: flex-end;
    margin-top: 17px;
    margin-right: 81px;
}

.post-reply-container {
    margin-top: 30px;
    margin-left: 80px;
    margin-right: 80px;
    padding-top: 36px;
    padding-bottom: 17px;
    background-color: white;
    border-radius: 2px;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
    display: flex;
    flex-direction: column;
}

.post-reply-content-first-line {
    display: flex;
    justify-content: space-between;
    margin-left: 61px;
    margin-right: 61px;
}

.post-reply-content-first-line-left {
    display: flex;
}

.reply-title-font {
    height: 34px;
    font-size: 24px;
    color: #101010;
    margin-right: 8px;
    margin-bottom: 22px;
}

.right-selector {
    margin-top: 4px;
}

.post-reply-content-first-line-right {
    display: flex;
}

.check-more-reply-font {
    height: 25px;
    font-size: 16px;
    color: #86909c;
    margin-top: 8px;
    margin-right: 8px;
}

.post-reply-content-first-line-right-pagination {
    margin-top: 3px;
}

.reply-first-line {
    display: flex;
    justify-content: space-between;
    margin-left: 61px;
    margin-right: 61px;
    margin-top: 23px;
    margin-bottom: 15px;
}

.reply-first-line-left-content {
    display: flex;
}

.replyer-username {
    margin-top: 11px;
    height: 28px;
    font-size: 20px;
    color: #165dff;
    margin-left: 18px;
}

.replyer-tag {
    margin-left: 11px;
    margin-top: 7px;
}

.reply-delete-button {
    margin-top: 8px;
    margin-left: -12px;
}

.reply-first-line-right-content {
    display: flex;
}

.reply-time-information {
    height: 23px;
    font-size: 16px;
    color: #86909c;
    margin-top: 14px;
    margin-right: 16px;
}

.icon-and-content-2 {
    display: flex;
    margin-right: 12px;
    margin-top: 7px;
}

.comment-icon {
    margin-top: 6px;
    height: 24px;
    width: 24px;
}

.comment-icon-after-contents {
    height: 24px;
    margin-left: 11px;
    margin-top: 7px;
    color: #86909c;
    font-size: 14px;
}

.replys-reply-container {
    margin-top: 2px;
}

.replys-reply-first-line-container {
    display: flex;
    justify-content: space-between;
    margin-left: 130px;
    margin-right: 61px;
    margin-bottom: 15px;
}

.replys-reply-avatar {
    margin-top: 5px;
}

.replys-reply-middle-font {
    margin-top: 13px;
    height: 28px;
    font-size: 16px;
    color: #101010;
    margin-left: 8px;
    margin-right: -10px;
}

.replys-reply-delete-button {
    border: none;
    background-color: white;
    height: 16px;
    width: 16px;
    margin-left: 0px;
    margin-top: 8px;
}

.replys-reply-content {
    width: 82%;
    overflow: hidden;
    word-wrap: break-word;
    color: #101010;
    margin-left: 12%;
    margin-bottom: 20px;
    line-height: 32px;
}

.write-replys-reply {
    width: 77%;
    margin-left: 17%;
    margin-top: -5px;
}

.send-replys-reply-button-location {
    display: flex;
    justify-content: flex-end;
}

.check-more-replys-reply-line {
    display: flex;
    justify-content: flex-end;
    margin-top: 12px;
    margin-right: 6%;
}

.check-replys-reply-pagination {
    margin-top: 3px;
}
</style>
