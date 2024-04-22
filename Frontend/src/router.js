import { createRouter, createWebHashHistory } from "vue-router";

import LoginPage from "./Pages/LoginPages/LoginPage.vue"

import MainPage from "./Pages/MainPageContainer.vue"

import PersonalInformation from "./Pages/PersonalCenter/PersonalInformation.vue"

import NoticeCenter from "./Components/Notice/NoticeCenter.vue"
import PersonalChat from "./Components/Chat/PersonalChat.vue"
import PersonalInformationChange from "./Pages/PersonalCenter/PersonalInformationChange.vue"
import Favorate from "./Pages/PersonalCenter/MoreDetails/Favorate.vue";

import PersonalCourse from "./Pages/CourseCenter/PersonalCourseCenter.vue"

import PostPage from "./Pages/PostPages/MainPostPage.vue"
import PostCenter from "./Pages/PostCenter/PostCenter.vue"
import CourseSection from "@/Pages/CourseCenter/CourseSection.vue"

import CreateCourseSection from "@/Pages/CourseCenter/CreateCourseSection.vue";


import ShowPersonalInformation from "./Pages/PersonalCenter/ShowPersonalInformation.vue"
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '',
            redirect: '/LoginPage'
        },
        {
            path: '/LoginPage',
            component:LoginPage,
        },
        {
            path:'/NoticeCenter',
            component:NoticeCenter,

        },
        {
            path: '/ChatCenter',
            component: PersonalChat,
        },
        {
            path: '/MainPage',
            component: MainPage,
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
                            path: 'PostPage',
                            component: PostPage,
                        },
                        {
                            path: 'PostCenter',
                            component: PostCenter,
                        },
                        {
                            path:'/CourseSection',
                            component:CourseSection,
                        },
                        {
                            path: '/CreateCourseSection',
                            component:CreateCourseSection,
                        },
                        {

                            path: '/ShowPersonalInformation',
                            component : ShowPersonalInformation,
                        },

                    ]
                },

            ]
        }
    ]
})

export default router;
