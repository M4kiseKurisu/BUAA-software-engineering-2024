import { createApp } from 'vue'
import ElementPlus from "element-plus"
import router from "./router.js"
import App from './App.vue'
import axios from 'axios';

import 'element-plus/dist/index.css'

axios.defaults.timeout=3000;
axios.defaults.baseURL = 'http://127.0.0.1:8080/';

const app = createApp(App)

app.use(ElementPlus)
app.use(router)
app.mount('#app')
