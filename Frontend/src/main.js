import { createApp } from 'vue'
import store from './store' 
import ElementPlus from "element-plus"
import router from "./router.js"
import App from './App.vue'

import 'element-plus/dist/index.css'

const app = createApp(App)

app.use(ElementPlus)
app.use(router)
app.use(store)
const vue = app.mount('#app');

export default vue;
