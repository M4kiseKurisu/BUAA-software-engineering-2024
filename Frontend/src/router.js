import { createRouter, createWebHashHistory } from "vue-router";

import MainPage from "./Pages/MainPageContainer.vue"

import PersonalInformation from "./Pages/PersonalCenter/PersonalInformation.vue"
import PersonalInformationChange from "./Pages/PersonalCenter/PersonalInformationChange.vue"
import Favorate from "@/Pages/PersonalCenter/MoreDetails/Favorate.vue";

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '',
            redirect: '/MainPage'
        },
        {
            path:'/fa',
            component:Favorate
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
                }
            ]
        }
    ]
})

export default router;
