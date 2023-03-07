import axios, { AxiosError, InternalAxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import CryptoJS from 'crypto-js'
import settings from '../settings'

// create an axios instance
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API, // BASE URL
  withCredentials: true, // 携带 Cookie
  timeout: 60000 // 请求超时
})

// request interceptor
service.interceptors.request.use((config: InternalAxiosRequestConfig) => {
  return config
},
error => {
  return error
})

// response interceptor
service.interceptors.response.use((response: any) => {
  // 如果响应头中指定了加密类型（版本）为 v1，将进行数据解密
  if (response.headers.encrypt === 'v1') {
    const key = CryptoJS.enc.Utf8.parse(settings.key)
    const iv = CryptoJS.enc.Latin1.parse(settings.iv)
    const decrypt = CryptoJS.AES.decrypt(response.data.ciphertext, key, {
      iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7
    })
    // 解密完成，将解密后的数据放入响应数据的位置
    response.data = JSON.parse(decrypt.toString(CryptoJS.enc.Utf8))
  }

  return response
},
error => {
  const message = error.message
  if (error instanceof AxiosError) {
    if (message === 'timeout of ' + error.config?.timeout + 'ms exceeded') {
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
})

export default service
