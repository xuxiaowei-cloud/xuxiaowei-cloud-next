import request from '../../utils/request'
import settings from '../../settings'
import { ElMessage } from 'element-plus/es'
import { useStore } from '../../store'

/**
 * 退出登录
 */
export const signout = function () {
  return request.get('/passport/signout?accessToken=' + useStore.getAccessToken, {
    headers: {}
  }).then(response => {
    console.log('退出登录', response)

    // 清空当前域名的 Cookie
    document.cookie.split(';').forEach(cookie => {
      document.cookie = cookie.replace(/^ +/, '').replace(/=.*/, `=;expires=${new Date(0).toUTCString()};path=/`)
    })

    // 清空本地储存
    window.localStorage.clear()

    // 清空会话储存
    window.sessionStorage.clear()

    const responseData = response.data
    if (responseData.code === settings.okCode) {
      ElMessage({
        message: responseData.msg,
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 3000,
        type: 'success',
        onClose: () => {
          location.href = settings.loginPage + '?homePage=' + encodeURIComponent(location.href.replace('non-authority', ''))
        }
      })
    } else {
      ElMessage({
        message: responseData.msg,
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 3000,
        type: 'error',
        onClose: () => {
          location.reload()
        }
      })
    }
  })
}
