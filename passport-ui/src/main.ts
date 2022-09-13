import { createApp } from 'vue'
import './style.css'

// https://github.com/unocss/unocss/#vite
import 'uno.css'

import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import { ipv4, ipv6 } from './api/ipify'
import request from './utils/request'

const pinia = createPinia()

const app = createApp(App)

app.use(pinia)

// 需要放在 mount 之前
app.use(router)

app.mount('#app')

app.config.globalProperties.$request = request
app.config.globalProperties.$ipv4 = ipv4
app.config.globalProperties.$ipv6 = ipv6
