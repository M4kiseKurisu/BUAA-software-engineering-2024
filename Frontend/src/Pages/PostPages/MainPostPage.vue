<template>
    <div class="breadcrumb"><BreadcrumbLabel :routeNames="route" /></div>

    <div class="main-postpage-container">
        <!-- 帖子正文部分 -->
        <div class="post-main-content-container">
            <div class="post-page-header-container">

                <!-- 帖子头部左侧 -->
                <div class="post-page-header-left">
                    <div class="post-main-title">{{ this.title }}</div>

                    <button class="post-main-delete-button" v-if="this.author_id === userId" @click="deletePost">
                        <el-icon :size="16" color="#86909C"><Delete /></el-icon>
                    </button>
                </div>

                <!-- 帖子头部右侧 -->
                <div class="post-page-header-right">
                    <div v-for="item in this.tags" class="post-page-tag-css">{{ item }}</div>

                    <div v-if="this.isLikePost" class="icon-and-content">
                        <!-- 喜欢图标 -->
                        <button class="icon-button-font" @click="likePost">
                            <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#d81e06"></path></svg>
                        </button>
                        <div class="like-icon-after-contents">{{ this.like_count }}人点赞</div>
                    </div>
                    <div v-else class="icon-and-content">
                        <!-- 喜欢图标（未点击的样式） -->
                        <button class="icon-button-font" @click="likePost">
                            <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#86909c"></path></svg>
                        </button>
                        <div class="like-icon-after-contents-2">{{ this.like_count }}人点赞</div>
                    </div>

                    <div v-if="this.isCollectPost" class="icon-and-content">
                        <!-- 收藏图标 -->
                        <button class="icon-button-font" @click="discollectPost">
                            <svg t="1713272491744" class="starfill-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8600" width="200" height="200"><path d="M908.1 353.1l-253.9-36.9L540.7 86.1c-3.1-6.3-8.2-11.4-14.5-14.5-15.8-7.8-35-1.3-42.9 14.5L369.8 316.2l-253.9 36.9c-7 1-13.4 4.3-18.3 9.3-12.3 12.7-12.1 32.9 0.6 45.3l183.7 179.1-43.4 252.9c-1.2 6.9-0.1 14.1 3.2 20.3 8.2 15.6 27.6 21.7 43.2 13.4L512 754l227.1 119.4c6.2 3.3 13.4 4.4 20.3 3.2 17.4-3 29.1-19.5 26.1-36.9l-43.4-252.9 183.7-179.1c5-4.9 8.3-11.3 9.3-18.3 2.7-17.5-9.5-33.7-27-36.3z" p-id="8601" fill="#f4ea2a"></path></svg>
                        </button>
                        <div class="starfill-icon-after-contents">{{ this.collect_count }}人收藏</div>
                    </div>
                    <div v-else class="icon-and-content">
                        <!-- 收藏图标（未点击的样式） -->
                        <button class="icon-button-font" @click="collectPost">
                            <svg t="1713272491744" class="starfill-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8600" width="200" height="200"><path d="M908.1 353.1l-253.9-36.9L540.7 86.1c-3.1-6.3-8.2-11.4-14.5-14.5-15.8-7.8-35-1.3-42.9 14.5L369.8 316.2l-253.9 36.9c-7 1-13.4 4.3-18.3 9.3-12.3 12.7-12.1 32.9 0.6 45.3l183.7 179.1-43.4 252.9c-1.2 6.9-0.1 14.1 3.2 20.3 8.2 15.6 27.6 21.7 43.2 13.4L512 754l227.1 119.4c6.2 3.3 13.4 4.4 20.3 3.2 17.4-3 29.1-19.5 26.1-36.9l-43.4-252.9 183.7-179.1c5-4.9 8.3-11.3 9.3-18.3 2.7-17.5-9.5-33.7-27-36.3z" p-id="8601" fill="#86909c"></path></svg>
                        </button>
                        <div class="like-icon-after-contents-2">{{ this.collect_count }}人收藏</div>
                    </div>
                </div>

            </div>

            <el-divider/>

            <div class="post-more-information-line">

                <div class="post-more-information-left">
                    <!-- 作者头像 -->
                    <button class="avatar-button" @click="toInformationShow(this.author_id)">
                        <el-avatar shape="square" :size="60" :src="this.author_head" />
                    </button>
                    

                    <div class="post-writer-information">
                        <div class="post-writer-username">{{ this.author_name }}</div>
                        <div class="post-writer-signature">{{ this.author_sign }}</div>
                    </div>
                </div>

                <div v-if="this.author_id != this.userId">
                    <div v-if="!this.isFollowingWriter" class="post-more-information-right">
                        <button class="post-page-tag-css" @click="followingWriter">关注作者</button>
                    </div>
                    <div v-else class="post-more-information-right">
                        <button class="post-page-tag-css" @click="notFollowingWriter">取消关注</button>
                    </div>
                </div>


            </div>
            <div v-html="this.content" class="post-main-content"/>

            <div class="post-main-end-line" v-for="(item, index) in this.resources">
                <div class="post-time-show-font">附加资源{{ index + 1 }}：</div>
                <button class="url-show-blue" @click="download(item)">{{ item }}</button>
            </div>

            <div class="post-main-end-line" style="margin-top: 6px">
                <button class="post-grey-button-below" @click="openComments">展开共{{ this.comments.length }}条评论</button>
                <button class="post-grey-button-below" @click="openCommentEditor">去评论</button>
                <div class="post-time-show-font">发帖时间：{{ this.create_time }}</div>
            </div>
        </div>

        <!-- 评论帖子发布位置 -->
        <div class="post-reply-writing-container" v-if="this.isCommentEditorOpen">

            <!-- 评论者头像及评论内容输入框 -->
            <div class="post-reply-first-line">
                <!-- 回复者头像 -->
                <div class="reply-avatar-container">
                    <el-avatar shape="square" :size="50" :src="this.avatarPicture" />
                </div>


                <!-- 回复编辑框 -->
                <!-- <el-input
                    v-model="this.ReplyTextarea"
                    style="margin-left: 18px"
                    :rows="5"
                    type="textarea"
                    placeholder="请输入您的评论"
                /> -->
                <div class="reply-editor-container">
                    <WangEditor :isComment="true" :post_id="this.post_id" :author_id="this.userId"/>
                </div>

            </div>

            <!-- <button class="send-reply-button-css">发布评论</button> -->
        </div>

        <!-- 评论帖子发布位置 -->
        <div class="post-reply-container" v-if="this.isCommentsOpen">
            <div class="post-reply-content-first-line">

                <!-- 回复栏位开头左侧 -->
                <div class="post-reply-content-first-line-left">
                    <div class="reply-title-font">{{ this.comments.length }}条评论</div>

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

                    <el-button type="primary" size="small" style="margin-top: 5px" @click="changesort">改变排序方式</el-button>
                </div>

                <!-- 回复栏位开头右侧 -->
                <div class="post-reply-content-first-line-right">
                    <div class="check-more-reply-font">查看更多评论：</div>

                    <div class="post-reply-content-first-line-right-pagination">
                        <el-pagination :pager-count="6" layout="prev, pager, next" :total="this.commentTotalPages * 10" v-model:current-page="currentCommentPage" />
                    </div>
                </div>

            </div>

            <el-divider/>

            <!-- 一条回复的全部内容 -->
            <div v-for="(item, index) in commentsArray" class="reply-main-content-container">
                <div class="reply-first-line">

                    <!-- 左侧信息：头像，昵称，tag，删除 -->
                    <div class="reply-first-line-left-content">
                        <!-- 回复者头像 -->
                        <button class="avatar-button" @click="toInformationShow(item.comment_author_id)">
                            <el-avatar shape="square" :size="50" :src="item.comment_author_head" />
                        </button>
                        
                        <div class="replyer-username">{{ item.comment_author_name }}</div>

                        <div class="replyer-tag">
                            <div v-if="this.author_id === item.comment_author_id" class="post-page-tag-css-for-replyer">作者</div>
                        </div>
                        <div class="reply-delete-button" v-if="item.comment_author_id === this.userId">
                            <button class="post-main-delete-button" @click="deleteComment(item.comment_id)">
                                <el-icon :size="16" color="#86909C"><Delete /></el-icon>
                            </button>
                        </div>
                    </div>

                    <!-- 右侧信息：评论时间，去评论，点赞 -->
                    <div class="reply-first-line-right-content">
                        <div class="reply-time-information">{{ item.comment_create_time }}</div>

                        <button @click="openReplyEditor(index)" class="icon-and-content-2">
                            <!-- 评论图标 -->
                            <svg t="1713327588804" class="comment-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3255" width="200" height="200"><path d="M896 128H128a32 32 0 0 0-32 32v576a32 32 0 0 0 32 32h288v-64H160V192h704v512h-256c-8.832 0-16.832 3.584-22.656 9.376l-159.968 160 45.248 45.248L621.248 768H896a32 32 0 0 0 32-32V160a32 32 0 0 0-32-32" fill="#86909C" p-id="3256"></path><path d="M560 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 560 448M240 448a48 48 0 1 0 95.968 0.032A48 48 0 0 0 240 448M784 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 784 448" fill="#86909C" p-id="3257"></path></svg>
                            <div class="comment-icon-after-contents">去评论</div>
                        </button>


                        <button @click="likeComment(item.comment_id, index)" v-if="this.isReplyLiked[index]" class="icon-and-content-2">
                            <!-- 喜欢图标 -->
                            <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#d81e06"></path></svg>
                            <div class="like-icon-after-contents">{{ this.replyLikesCount[index] }}人点赞</div>
                        </button>
                        <button @click="likeComment(item.comment_id, index)" v-else class="icon-and-content-2">
                            <!-- 喜欢图标（点赞前） -->
                            <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#86909c"></path></svg>
                            <div class="like-icon-after-contents-2">{{ this.replyLikesCount[index] }}人点赞</div>
                        </button>
                    </div>

                </div>

                <!-- 回复正文 -->
                <div class="post-main-content" v-html="item.comment_content"/>

                <!-- 评论回复 -->
                <div class="write-replys-reply" v-if="this.isReplysOpen[index]">
                    <!-- 回复回复编辑框 -->
                    <!-- <el-input
                        v-model="this.ReplysReplyTextarea"
                        :rows="5"
                        type="textarea"
                        placeholder="请输入您的评论"
                    /> -->

                    <div class="reply-editor-container">
                        <WangEditor2 :isComment="false" :comment_id="item.comment_id" :replied_id="item.comment_author_id" :author_id="this.userId" :post_id="this.post_id" :replied_author_id="item.comment_author_id"/>
                    </div>
                </div>

                <!-- 评论评论的全部内容 -->
                <div v-for="(item2, index2) in repliesArray(item.replies, index)"  class="replys-reply-container">
                    <div class="replys-reply-first-line-container">

                        <!-- 左侧信息：头像，昵称，tag，删除 -->
                        <div class="reply-first-line-left-content">
                            <!-- 回复者头像 -->
                            <button class="avatar-button" @click="toInformationShow(item2.reply_author_id)">
                                <div class="replys-reply-avatar">
                                    <el-avatar shape="square" :size="40" :src="item2.reply_author_head" />
                                </div>
                            </button>
                            
                            <div class="replyer-username">{{ item2.reply_author_name }}</div>

                            <div class="replyer-tag" v-if="this.author_id === item2.reply_author_id">
                                <div class="post-page-tag-css-for-replyer">作者</div>
                            </div>

                            <div class="replys-reply-middle-font">回复</div>

                            <div class="replyer-username">{{ item2.replied_author_name }}</div>
                            <div class="replys-reply-delete-button" v-if="item2.reply_author_id === this.userId">
                                <button class="post-main-delete-button">
                                    <el-icon :size="16" color="#86909C"><Delete /></el-icon>
                                </button>
                            </div>
                        </div>

                        <!-- 右侧信息：评论时间，去评论，点赞 -->
                        <div class="reply-first-line-right-content">
                            <div class="reply-time-information">{{ item2.reply_create_time }}</div>

                            <button @click="openReplyEditor2(index, index2)" class="icon-and-content-2">
                                <!-- 评论图标 -->
                                <svg t="1713327588804" class="comment-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3255" width="200" height="200"><path d="M896 128H128a32 32 0 0 0-32 32v576a32 32 0 0 0 32 32h288v-64H160V192h704v512h-256c-8.832 0-16.832 3.584-22.656 9.376l-159.968 160 45.248 45.248L621.248 768H896a32 32 0 0 0 32-32V160a32 32 0 0 0-32-32" fill="#86909C" p-id="3256"></path><path d="M560 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 560 448M240 448a48 48 0 1 0 95.968 0.032A48 48 0 0 0 240 448M784 448a48 48 0 1 0-95.968-0.032A48 48 0 0 0 784 448" fill="#86909C" p-id="3257"></path></svg>
                                <div class="comment-icon-after-contents">去评论</div>
                            </button>


                            <button v-if="this.isReplyLiked2[index][index2]" @click="likeReply(item2.reply_id, index, index2)" class="icon-and-content-2">
                                <!-- 喜欢图标 -->
                                <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#d81e06"></path></svg>
                                <div class="like-icon-after-contents">{{ this.replyLikes[index][index2] }}人点赞</div>
                            </button>
                            <button v-else class="icon-and-content-2" @click="likeReply(item2.reply_id, index, index2)">
                                <!-- 喜欢图标(未点击) -->
                                <svg t="1713272002795" class="like-icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8419" width="200" height="200"><path d="M923 283.6c-13.4-31.1-32.6-58.9-56.9-82.8-24.3-23.8-52.5-42.4-84-55.5-32.5-13.5-66.9-20.3-102.4-20.3-49.3 0-97.4 13.5-139.2 39-10 6.1-19.5 12.8-28.5 20.1-9-7.3-18.5-14-28.5-20.1-41.8-25.5-89.9-39-139.2-39-35.5 0-69.9 6.8-102.4 20.3-31.4 13-59.7 31.7-84 55.5-24.4 23.9-43.5 51.7-56.9 82.8-13.9 32.3-21 66.6-21 101.9 0 33.3 6.8 68 20.3 103.3 11.3 29.5 27.5 60.1 48.2 91 32.8 48.9 77.9 99.9 133.9 151.6 92.8 85.7 184.7 144.9 188.6 147.3l23.7 15.2c10.5 6.7 24 6.7 34.5 0l23.7-15.2c3.9-2.5 95.7-61.6 188.6-147.3 56-51.7 101.1-102.7 133.9-151.6 20.7-30.9 37-61.5 48.2-91 13.5-35.3 20.3-70 20.3-103.3 0.1-35.3-7-69.6-20.9-101.9z" p-id="8420" fill="#86909c"></path></svg>
                                <div class="like-icon-after-contents-2">{{ this.replyLikes[index][index2] }}人点赞</div>
                            </button>
                        </div>

                    </div>

                    <!-- 评论评论正文 -->
                    <div class="replys-reply-content" v-html="item2.reply_content"/>


                    <div class="write-replys-reply" v-if="this.isReplysOpen2[index][index2]">
                        <div class="reply-editor-container">
                            <WangEditor2 :isComment="false" :comment_id="item.comment_id" :replied_id="item2.replied_author_id" :author_id="this.userId" :post_id="this.post_id" :replied_author_id="item2.replied_author_id"/>
                        </div>
                    </div>
                </div>

                <div class="check-more-replys-reply-line">
                    <div class="check-more-reply-font">查看更多评论：</div>
                    <div class="check-replys-reply-pagination">
                        <el-pagination :pager-count="6" layout="prev, pager, next" :total="this.repliesTotalPages[index] * 10" v-model:current-page="this.repliesCurrentPage[index]" />
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

import WangEditor from "./WangEditor.vue"
import WangEditor2 from "./WangEditor2.vue"

import { Delete } from '@element-plus/icons-vue'

export default {
    components: {
        BreadcrumbLabel,
        Delete,
        WangEditor,
        WangEditor2,
    },
    data() {
        return {
            userId: JSON.parse(sessionStorage.getItem("id")),
            route: ["学业板块", "课程论坛"],  //本界面要显示的面包屑信息
            avatarPicture: "",  //作者头像
            ReplyTextarea: "",  //回复内容监听
            sortOptions: [  //评论排序方式
                {
                    value: '0',
                    label: '时间正序',
                },
                {
                    value: '1',
                    label: '热度排序',
                },
                {
                    value: '2',
                    label: '时间倒序',
                }
            ],
            sortValue: "",
            ReplysReplyTextarea: "",  //回复回复内容监听
            post_id: 21,
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
            like_count: 0,
            collect_count: 0,
            comment_count: 0,
            view_count: 0,

            isLikePost: false,
            isCollectPost: false,
            author_sign: "",
            isFollowingWriter: false,

            isCommentsOpen: false,
            isCommentEditorOpen: false,

            commentTotalPages: 1,
            currentCommentPage: 1,

            isReplysOpen:[false, false, false],
            isReplyLiked:[false, false, false],
            replyLikesCount:[],

            repliesTotalPages: [1, 1, 1],
            repliesCurrentPage: [1, 1, 1],

            isReplysOpen2: [[false, false, false], [false, false, false], [false, false, false]],
            isReplyLiked2: [[false, false, false], [false, false, false], [false, false, false]],
            isReplyLiked2_1: [false, false, false],
            replyLikes: [[0, 0, 0], [0, 0, 0], [0, 0, 0]],
        }
    },
    computed: {
        commentsArray() {
            let showComments = [];

            if (this.comments.length === 0) {
                return showComments;
            }

            let i = this.currentCommentPage;
            //console.log(this.currentCommentPage);
            showComments = this.comments.slice((i - 1) * 3, i * 3);
            //console.log(showComments);

            // 更改楼中楼信息
            for (let j = 0; j < showComments.length; j++) {
                const count = (showComments[j].replies.length % 3 === 0) ? 
                    showComments[j].replies.length / 3 : Math.ceil(showComments[j].replies.length / 3);
                this.repliesTotalPages[j] = count;
                this.repliesCurrentPage[j] = 1;

                //点赞状态信息
                this.isReplyLiked[j] = showComments[j].comment_isLike;
                this.replyLikesCount[j] = showComments[j].comment_like_count;

                for (let k = 0; k < (showComments[j].replies.length >= 3 ? 3 : showComments[j].replies.length); k++) {
                    this.isReplyLiked2[j][k] = showComments[j].replies[k].reply_isLike;
                    this.replyLikes[j][k] = showComments[j].replies[k].reply_like_count;
                }
                console.log(this.isReplyLiked2);
            }

            return showComments;
        },
        repliesArray() {
            // let showReplies = [];

            // if (this.replies.length === 0) {
            //     return replies;
            // }

            // let i = this.repliesCurrentPage[commentIndex];
            // showReplies.push(this.replies.slice((i - 1) * 3, i * 3));
            // return showReplies;
            return (replies, commentIndex) => {
                let showReplies = [];

                if (!replies || replies.length === 0) {
                    return replies;
                }
                // 测试用
                // if (!replies) {
                //     replies = [];
                // }

                let i = this.repliesCurrentPage[commentIndex];
                showReplies = replies.slice((i - 1) * 3, i * 3);
                for (let j = 0; j < showReplies.length; j++) {
                    this.isReplyLiked2[commentIndex][j] = showReplies[j].reply_isLike;
                    this.replyLikes[commentIndex][j] = showReplies[j].reply_like_count;
                }
                return showReplies;
            };
        }
    },
    created() {
        this.post_id = this.$route.params.postId;
    },
    mounted() {
        axios({
            method: "GET",
            url: "/api/posts/post",
            params: {
                post_id: this.post_id,
                comment_sort: 1,  //0：时间（正序）；1：热度；2：时间倒序（最新优先）
                user_id: JSON.parse(sessionStorage.getItem("id")),
            }
        }).then((result) => {
            console.log(result)
            this.title = result.data.title;
            this.content = result.data.content;
            this.author_id = result.data.author_id;
            this.author_head = result.data.author_head;
            this.author_name = result.data.author_name;
            this.create_time = result.data.create_time;
            this.tags = result.data.tags;
            this.images = result.data.images;
            this.resources = result.data.resources;
            this.comments = result.data.comments;
            this.like_count = result.data.likeCount;
            this.collect_count = result.data.collectCount;
            this.comment_count = result.data.comment_count;
            this.view_count = result.data.view_count;

            const count = (result.data.comments.length % 3 === 0) ? 
                result.data.comments.length / 3 : Math.ceil(result.data.comments.length / 3);
            this.commentTotalPages = count;
            // 更改楼中楼信息
            for (let j = 0; j < (this.comments.length >= 3 ? 3 : this.comments.length); j++) {
                const count = (this.comments[j].replies.length % 3 === 0) ? 
                    this.comments[j].replies.length / 3 : Math.ceil(this.comments[j].replies.length / 3);
                this.repliesTotalPages[j] = count;
                this.repliesCurrentPage[j] = 1;

                //点赞状态信息
                this.replyLikesCount.push(this.comments[j].comment_like_count);
                this.isReplyLiked[j] = this.comments[j].comment_isLike;

                //回复状态信息
                for (let k = 0; k < (this.comments[j].replies.length >= 3 ? 3 : this.comments[j].replies.length); k++) {
                    this.isReplyLiked2[j][k] = this.comments[j].replies[k].reply_isLike;
                    this.replyLikes[j][k] = this.comments[j].replies[k].reply_like_count;
                }
            }
            //console.log(this.repliesTotalPages);
            //console.log(this.commentTotalPages);
            //console.log(this.replyLikesCount);
            console.log(this.resources);
            //console.log(this.isReplyLiked2);
            this.createInformation();
        })
    },
    methods: {
        createInformation() {
            //获得用户是否点赞、收藏信息
            axios({
                method: "GET",
                url: "/api/posts/isLike",
                params: {
                    post_id: this.post_id,
                    user_id: this.userId,
                }
            }).then((result) => {
                this.isLikePost = result.data.like;
            })

            axios({
                method: "GET",
                url: "/api/posts/isFavorite",
                params: {
                    post_id: this.post_id,
                    user_id: this.userId,
                }
            }).then((result) => {
                console.log(result)
                this.isCollectPost = result.data.like;
            })


            // 如果作者非本人，获取关注，签名信息
            if (this.author_id != JSON.parse(sessionStorage.getItem("id"))) {
                axios({
                    method: "GET",
                    url: "/api/user/social/others",
                    params: {
                        id: this.author_id,
                    }
                }).then((result) => {
                    console.log(result);
                    this.isFollowingWriter = result.data.flag_follow;
                    this.author_sign = result.data.sign;
                })
            } else {
                axios({
                    method: "GET",
                    url: "/api/user/info",
                }).then((result) => {
                    this.author_sign = result.data.sign;
                })
            }

            // 获取作者头像信息
            console.log( this.author_id);
            axios({
                method: "GET",
                url: "/api/user/head",
                params: {
                    user_id: this.author_id
                }
            }).then((result) => {
                console.log(result)
                this.avatarPicture = result.data.info;
            })
        },
        deletePost() {
            // 作者删除帖子
            let content = {
                post_id: this.post_id,
            }
            axios({
                method: "POST",
                url: "/api/posts/delete",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '帖子删除成功！',
                        type: 'success',
                    });
                }
            })
        },
        likePost() {
            let content = {
                post_id: this.post_id,
                user_id: JSON.parse(sessionStorage.getItem("id"))
            }
            axios({
                method: "POST",
                url: "/api/posts/like",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    // 这里的status定义还要确认
                    // 点赞帖子
                    if (result.data.status === 1) {
                        this.$message({
                            showClose: true,
                            message: '帖子点赞成功！',
                            type: 'success',
                        });
                        this.isLikePost = true;
                        this.like_count++;
                    }
                    // 取消点赞
                    if (result.data.status === 0) {
                        this.$message({
                            showClose: true,
                            message: '取消帖子点赞成功！',
                            type: 'success',
                        });
                        this.isLikePost = false;
                        this.like_count--;
                    }
                }
            })
        },
        collectPost() {
            // 帖子收藏
            let content = {
                post_id: this.post_id,
                user_id: this.userId,
            }
            console.log(content);
            axios({
                method: "POST",
                url: "/api/posts/favorite",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '帖子收藏成功！',
                        type: 'success',
                    });
                    this.isCollectPost = true;
                    this.collect_count++;
                }
            })
        },
        discollectPost() {
            // 帖子取消收藏
            let content = {
                post_id: this.post_id,
                user_id: this.userId,
            }
            axios({
                method: "POST",
                url: "/api/posts/unfavorite",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '帖子取消收藏成功！',
                        type: 'success',
                    });
                    this.isCollectPost = false;
                    this.collect_count--;
                }
            })
        },
        followingWriter() {
            // 关注帖子作者
            let content = {
                follow_id: this.author_id,
            }
            axios({
                method: "POST",
                url: "/api/user/follow",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '作者关注成功！',
                        type: 'success',
                    });
                    this.isFollowingWriter = true;
                }
            })
        },
        notFollowingWriter() {
            // 取消关注作者
            let content = {
                unfollow_id: this.author_id,
            }
            axios({
                method: "POST",
                url: "/api/user/unfollow",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '作者取消关注成功！',
                        type: 'success',
                    });
                    this.isFollowingWriter = false;
                }
            })
        },
        openComments() {
            //开关评论
            this.isCommentsOpen = !this.isCommentsOpen
        },
        openCommentEditor() {
            //开关评论编辑器
            this.isCommentEditorOpen = !this.isCommentEditorOpen
            console.log(123);
        },
        changesort() {
            //更换排序方式
            console.log(this.sortValue);
            axios({
                method: "GET",
                url: "/api/posts/post",
                params: {
                    post_id: this.post_id,
                    comment_sort: this.sortValue,  //0：时间（正序）；1：热度；2：时间倒序（最新优先）
                }
            }).then((result) => {
                this.comments = result.data.comments;

                const count = (result.data.comments.length % 3 === 0) ? 
                    result.data.comments.length / 3 : Math.ceil(result.data.comments.length / 3);
                this.commentTotalPages = count;

                //console.log(this.comments);
                //console.log(this.commentTotalPages);
                this.currentCommentPage = 1;
            })
        },
        deleteComment(comment_id) {
            // 作者删除评论
            let content = {
                comment_id: comment_id,
            }
            axios({
                method: "POST",
                url: "/api/posts/comment/delete",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    this.$message({
                        showClose: true,
                        message: '评论删除成功！',
                        type: 'success',
                    });
                    changesort();
                }
            })
        },
        openReplyEditor(index) {
            this.isReplysOpen[index] = !this.isReplysOpen[index];
        },
        openReplyEditor2(index1, index2) {
            //console.log(index1, index2, this.isReplysOpen2)
            this.isReplysOpen2[index1][index2] = !this.isReplysOpen2[index1][index2];
        },
        likeComment(comment_id, index) {
            let content = {
                comment_id: comment_id,
                user_id: JSON.parse(sessionStorage.getItem("id"))
            }
            axios({
                method: "POST",
                url: "/api/posts/comment/like",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    // 这里的status定义还要确认
                    // 点赞评论
                    if (result.data.status === 1) {
                        this.$message({
                            showClose: true,
                            message: '评论点赞成功！',
                            type: 'success',
                        });
                        this.isReplyLiked[index] = true;
                        this.replyLikesCount[index]++;
                    }
                    // 取消点赞
                    if (result.data.status === 0) {
                        this.$message({
                            showClose: true,
                            message: '取消评论点赞成功！',
                            type: 'success',
                        });
                        this.isReplyLiked[index] = false;
                        this.replyLikesCount[index]--;
                    }
                }
            })
        },
        likeReply(reply_id, index, index2) {
            let content = {
                reply_id: reply_id,
                user_id: JSON.parse(sessionStorage.getItem("id"))
            }
            console.log(content);
            axios({
                method: "POST",
                url: "/api/posts/reply/like",
                data: content,
            }).then((result) => {
                console.log(result);
                if(result.data.success) {
                    // 这里的status定义还要确认
                    //this.isReplyLiked2[index][index2] = !this.isReplyLiked2[index][index2];
                    // let getarray = this.isReplyLiked2[index];
                    // console.log(getarray);
                    // getarray[index2] = !getarray[index2];
                    // console.log(getarray);
                    //this.isReplyLiked2[index] = getarray;
                    //this.isReplyLiked2.splice(index,1,getarray);
                    // let newarray = [];
                    // for (let i = 0; i < 3; i++) {
                    //     newarray.push(getarray);
                    // }
                    // this.isReplyLiked2 = newarray;
                    // console.log(this.isReplyLiked2);
                    // 点赞回复
                    if (result.data.status === 1) {
                        this.$message({
                            showClose: true,
                            message: '回复点赞成功！',
                            type: 'success',
                        });

                        //this.replyLikes[index][index2]++;

                        //?????
                        // console.log(this.isReplyLiked2);
                        // console.log(this.isReplyLiked2[index][index2]);
                        //this.isReplyLiked2[index][index2] = true;
                        //this.$set(this.isReplyLiked2[index], index2, true);
                        // console.log(index, index2);
                        // this.$nextTick(function() {
                        //     console.log(this.isReplyLiked2[index]);
                        //     console.log(this.isReplyLiked2);
                        // })
                        // console.log(this.isReplyLiked2);
                        //console.log(this.isReplyLiked2[index][index2]);
                        //this.isReplyLiked2_1[index2] = true;
                        //console.log(this.isReplyLiked2_1);
                    }
                    // 取消点赞
                    if (result.data.status === 0) {
                        this.$message({
                            showClose: true,
                            message: '取消回复点赞成功！',
                            type: 'success',
                        });
                        //this.isReplyLiked2[index][index2] = false;
                        //this.replyLikes[index][index2]--;
                    }
                    location.reload();
                }
            })
        },
        download(item) {
            window.open(item);
        },
        toInformationShow(id) {
            this.$router.push({ path: "/MainPage/Course_Center/ShowPersonalInformation/" + id});
        }
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
    padding-left: 3px;
    padding-right: 3px;
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

.url-show-blue {
    font-size: 16px;
    color: #165DFF;
    margin-top: 8px;
    width: 80%;
    overflow: hidden;
    background-color: white;
    border: none;
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
    border: none;
    background-color: white;
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
    width: 81%;
    margin-left: 12%;
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

.like-icon-after-contents-2 {
    height: 24px;
    margin-left: 11px;
    margin-top: 7px;
    color: #86909C;
    font-size: 14px;
}

.icon-button-font {
    background-color: white;
    border: none;
    height: 20px;
    width: 20px;
}

.reply-editor-container {
    margin-left: 20px;
}

.reply-avatar-container {
    height: 55px;
    width: 55px;
}

.like-icon-after-contents-2 {
    height: 24px;
    margin-left: 11px;
    margin-top: 7px;
    color: #86909c;
    font-size: 14px;
}

.post-page-tag-css-for-replyer {
    width: 40px;
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

.avatar-button {
    background-color: white;
    border: none;
}
</style>
