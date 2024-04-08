import { createRouter, createWebHashHistory } from "vue-router";

import MainPage from "./Pages/MainPageContainer.vue"

import PersonalInformation from "./Pages/PersonalCenter/PersonalInformation.vue"

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '',
            redirect: '/MainPage'
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