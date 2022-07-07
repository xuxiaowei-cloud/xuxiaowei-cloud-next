import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { ipv4, ipv6 } from './api/ipify'
import request from './utils/request'
// 防止 ElMessage 样式无效
import 'element-plus/theme-chalk/el-message.css'
// 防止 ElMessageBox 样式无效
import 'element-plus/theme-chalk/el-message-box.css'

const app = createApp(App)

app.use(store)

// 需要放在 mount 之前
app.use(router)

app.mount('#app')

app.config.globalProperties.$request = request
app.config.globalProperties.$ipv4 = ipv4
app.config.globalProperties.$ipv6 = ipv6
