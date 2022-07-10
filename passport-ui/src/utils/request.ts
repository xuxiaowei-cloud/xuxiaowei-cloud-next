import axios, { AxiosError } from 'axios'
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
    return config
  },
  error => {
    return error
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    return response
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
