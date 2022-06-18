import request from '../../utils/request'
import store from '../../store'
import { ElMessage } from 'element-plus'

/**
 * 检查 Token
 */
export const checkToken = function () {
  return request.post('/authorization-server/oauth2/check_token').then(response => {
    const responseData = response.data
    if (responseData && responseData.active === true) {
      const authorities = responseData.authorities
      store.commit('setAuthorities', authorities)
      if (authorities === undefined) {
        ElMessage({
          message: '用户无任何权限',
          // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
          duration: 3000,
          type: 'error',
          onClose: () => {

          }
        })
      }
    }
    return responseData
  })
}
