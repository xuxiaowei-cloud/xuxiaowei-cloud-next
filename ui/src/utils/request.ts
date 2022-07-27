import axios, { AxiosError } from 'axios'
import { useStore } from '../store'
import settings from '../settings'
import { ElMessage } from 'element-plus'

// create an axios instance
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API, // BASE URL
  withCredentials: true, // 携带 Cookie
  timeout: 5000 // 请求超时
})

// request interceptor
service.interceptors.request.use(
  config => {
    if (!config.headers) {
      config.headers = {}
    }
    config.headers.authorization = 'Bearer ' + useStore.getAccessToken
    return config
  },
  error => {
    return error
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    if (settings.loginRequiredCode.indexOf(response.data.code) === -1) {
      return response
    } else {
      ElMessage({
        message: response.data.msg,
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 3000,
        type: 'error',
        onClose: () => {
          // 授权成功后重定向页面
          // 重定向页面不包括无权限页面（non-authority）
          location.href = settings.loginPage + '?homePage=' + encodeURIComponent(location.href.replace('non-authority', ''))
        }
      })
    }
  },
  error => {
    const message = error.message
    if (error instanceof AxiosError) {
      if (message === 'timeout of ' + error.config.timeout + 'ms exceeded') {
        ElMessage({
          message: '请求超时，请重试',
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 3000,
          type: 'error'
        })
      } else if (message === 'Network Error') {
        ElMessage({
          message: '网络错误，请稍后再试',
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 3000,
          type: 'error'
        })
      } else {
        ElMessage({
          message,
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 3000,
          type: 'error'
        })
      }
    } else if (message === 'Network Error') {
      ElMessage({
        message: '网络错误，请稍后再试',
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 3000,
        type: 'error'
      })
    } else {
      console.log(error)
    }
    return error
  }
)

export default service
