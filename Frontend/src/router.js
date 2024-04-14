import { createRouter, createWebHashHistory } from "vue-router";

import MainPage from "./Pages/MainPageContainer.vue"

import PersonalInformation from "./Pages/PersonalCenter/PersonalInformation.vue"
import NoticeCenter from "./Components/Notice/NoticeCenter.vue"
import PersonalChat from "./Components/Chat/PersonalChat.vue"
const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '',
            redirect: '/MainPage'
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
                        }
                    ]
                }
            ]
        }
    ]
})  

export default router;