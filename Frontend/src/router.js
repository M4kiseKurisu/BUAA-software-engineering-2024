import { createRouter, createWebHashHistory } from "vue-router";

import MainPage from "./Pages/MainPageContainer.vue"

import PersonalInformation from "./Pages/PersonalCenter/PersonalInformation.vue"
<<<<<<< HEAD
import NoticeCenter from "./Components/Notice/NoticeCenter.vue"
import PersonalChat from "./Components/Chat/PersonalChat.vue"
=======
import PersonalInformationChange from "./Pages/PersonalCenter/PersonalInformationChange.vue"
import Favorate from "./Pages/PersonalCenter/MoreDetails/Favorate.vue";

import PersonalCourse from "./Pages/CourseCenter/PersonalCourseCenter.vue"

>>>>>>> 1a50d7ad92a81534583df5e1691e847abab08182
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '',
            redirect: '/MainPage'
        },
        {
<<<<<<< HEAD
            path:'/NoticeCenter',
            component:NoticeCenter,

        },
        {
            path: '/ChatCenter',
            component: PersonalChat,
=======
            path:'/fa',
            component:Favorate
>>>>>>> 1a50d7ad92a81534583df5e1691e847abab08182
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
                        }
                    ]
                }
            ]
        }
    ]
})

export default router;
