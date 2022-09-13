import axios, { AxiosError, AxiosRequestConfig, AxiosResponse } from 'axios'
import { ElMessage } from 'element-plus'
import { JSEncrypt } from 'jsencrypt'
import CryptoJS from 'crypto-js'
import { useStore } from '../store'
import settings from '../settings'
import { decrypt, encrypt } from './aes'

// create an axios instance
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API, // BASE URL
  withCredentials: true, // 携带 Cookie
  timeout: 5000 // 请求超时
})

// request interceptor
service.interceptors.request.use((config: AxiosRequestConfig) => {
  if (!config.headers) {
    config.headers = {}
  }
  config.headers.authorization = 'Bearer ' + useStore.getAccessToken

  if (config.method === 'post' || config.method === 'POST') { // POST 请求加密
    const data = config.data
    if (data !== undefined) { // 请求体不为空时，进行加密
      const currentTimeMillis = useStore.currentTimeMillis || new Date().getTime() // 请求体中添加时间戳变量
      config.data = { ciphertext: encrypt(settings.key, settings.iv, currentTimeMillis + JSON.stringify(data)) } // 加密数据
      config.headers.encrypt = 'v1' // 加密数据方式（版本）
      // 未提供客户ID，使用默认AES秘钥与偏移量进行加解密
    }
  }

  return config
},
error => {
  return error
})

// response interceptor
service.interceptors.response.use((response: AxiosResponse) => {
  const data = response.data
  // 如果响应头中指定了加密类型（版本）为 v1，将进行数据解密
  if (response.headers.encrypt === 'v1') {
    // 解密完成，将解密后的数据放入响应数据的位置
    response.data = JSON.parse(decrypt(settings.key, settings.iv, data.ciphertext))
  }
  if (response.headers.sign) {
    const jsEncrypt = new JSEncrypt()
    jsEncrypt.setPublicKey(settings.publicKey)
    // @ts-ignore
    const verify = jsEncrypt.verify(JSON.stringify(data), response.headers.sign, CryptoJS.MD5)
    if (!verify) {
      ElMessage({
        message: '验证响应数据签名失败，返回数据不可信',
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 3000,
        type: 'error'
      })
    }
  }

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
})

export default service
