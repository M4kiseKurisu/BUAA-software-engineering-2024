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

import SocialCenter from "@/Pages/SocialCenter/SocialPageContainer.vue"

import ShowPersonalInformation from "./Pages/PersonalCenter/ShowPersonalInformation.vue"
import StudyGroupCenter from './Components/GroupCenter/StudyGroupCenter.vue'
import CreateGroup from './Components/GroupCenter/CreateGroup.vue'
import UpGradeCenter from './Components/UpGrade/UpGradeCenter.vue'


import SchoolInformationMain from "./Pages/SchoolInformation/SchoolInformationMain.vue"
import SchoolInformationDetail from "./Pages/SchoolInformation/SchoolInformationDetail.vue"
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
                            path: 'ShowPersonalInformation/:userId',
                            component: ShowPersonalInformation,
                        },
                        {
                            path: 'CreatePost/:sectionId',
                            component: CreatePost,
                        },
                    ]
                },
                {
                    path: 'Social_Center',
                    component: SocialCenter,
                },
                {
                    path: 'StudyGroupCenter',
                    component: StudyGroupCenter,
                },
                {
                    path: 'CreateGroup',
                    component: CreateGroup,
                },
                {
                    path: 'ChatCenter/:personId/:groupId',
                    name: 'ChatCenter',
                    component: ChatCenter,
                },
                {
                    path: 'UpGradeCenter',
                    component: UpGradeCenter,
                },
                {
                    
                    path: 'SchoolInformation',
                    redirect: '/MainPage/SchoolInformation/Main',
                    children: [
                        {
                            path: 'Main',
                            component: SchoolInformationMain,
                        },
                        {
                            path: 'Detail/:school_id',
                            component: SchoolInformationDetail,
                        },
                    ]
                }
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
        // 如果不需要登录，则直接放行。
        if (JSON.parse(sessionStorage.getItem('id')) != null) {
          // 如果用户已经登录，并且访问的页面不需要登录，则重定向到指定的首页
            next('/MainPage/Personal_Center/Personal_Information') // 将 '/home' 替换为你想要重定向的首页路径
        } else {
            next() // 否则直接放行
        }
    }
})

export default router;
