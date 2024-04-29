import { createRouter, createWebHashHistory } from "vue-router";

import LoginPage from "./Pages/LoginPages/LoginPage.vue"

import MainPage from "./Pages/MainPageContainer.vue"

import PersonalInformation from "./Pages/PersonalCenter/PersonalInformation.vue"

import NoticeCenter from "./Components/Notice/NoticeCenter.vue"
import ChatCenter from "./Components/Chat/ChatCenter.vue"
import PersonalInformationChange from "./Pages/PersonalCenter/PersonalInformationChange.vue"
import Favorate from "./Pages/PersonalCenter/MoreDetails/Favorate.vue";

import PersonalCourse from "./Pages/CourseCenter/PersonalCourseCenter.vue"

import PostPage from "./Pages/PostPages/MainPostPage.vue"
import PostCenter from "./Pages/PostCenter/PostCenter.vue"
import CourseSection from "@/Pages/CourseCenter/CourseSection.vue"
import CreatePost from "./Pages/PostPages/CreatePost.vue"

import CreateCourseSection from "@/Pages/CourseCenter/CreateCourseSection.vue";


import ShowPersonalInformation from "./Pages/PersonalCenter/ShowPersonalInformation.vue"
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '',
            redirect: '/LoginPage',
            meta: { 
                requireAuth: false,
            }   
        },
        {
            path: '/LoginPage',
            component:LoginPage,
            meta: { 
                requireAuth: false,
            }  
        },
        {
            path:'/NoticeCenter',
            component:NoticeCenter,
            meta: { 
                requireAuth: true,
            }  
        },
        {
            path: '/MainPage',
            component: MainPage,
            meta: { 
                requireAuth: true,
            },
            children: [
                {
                    path: 'Personal_Center',
                    redirect: '/MainPage/Personal_Center/Personal_Information',
                    children: [
                        {
                            path: 'Personal_Information',
                            component: PersonalInformation,
                        },
                        {
                            path: 'Personal_Information_Change',
                            component: PersonalInformationChange,
                        }
                    ]
                },
                {
                    path: 'Course_Center',
                    redirect: '/MainPage/Course_Center/Personal_Course',
                    children: [
                        {
                            path: 'Personal_Course',
                            component: PersonalCourse,
                        },
                        {
                            path: 'PostPage/:postId',
                            component: PostPage,
                        },
                        {
                            path: 'PostCenter/:sectionId',
                            component: PostCenter,
                        },
                        {
                            path:'/CourseSection/:section_id',
                            component:CourseSection,
                        },
                        {
                            path: '/CreateCourseSection',
                            component: CreateCourseSection,
                        },
                        {
                            path: '/ShowPersonalInformation/:userId',
                            component: ShowPersonalInformation,
                        },
                        {
                            path: 'CreatePost/:sectionId',
                            component: CreatePost,
                        },
                        {
                            path: '/ChatCenter',
                            component: ChatCenter,
                        },
                    ]
                },

            ]
        }
    ]
})

// 添加全局前置守卫
router.beforeEach((to, from, next) => {
    // 检查是否需要登录才能访问
    if (to.matched.some(record => record.meta.requireAuth)) {
        // 通过对 store.state.user 有无值的检查来判断用户是否登录
        if (JSON.parse(sessionStorage.getItem('id')) == null) {
            next({
                path: '',
                query: { redirect: to.fullPath }
                // 将希望导航到的路由的路径传给登录界面，登录成功后可能需要基于此进行重定向。
            })
        } else {
            next()  // 如果用户已经登录，就正常进入该路由。
        }
    } else {
        next()  // 如果不需要登录，则直接放行。
    }
})

export default router;
