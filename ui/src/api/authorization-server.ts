import request from '../utils/request'
import store from '../store'
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

/**
 * 分页查询客户
 * @param data
 */
export const page = function (data: any) {
  return request.post('/authorization-server/oauth-client-details/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 客户主键 删除
 * @param clientId 客户主键
 */
export const removeById = function (clientId: number) {
  return request.post('/authorization-server/oauth-client-details/removeById/' + clientId).then(response => {
    return response.data
  })
}

/**
 * 根据 客户主键 删除
 * @param clientIds 客户主键
 */
export const removeByIds = function (clientIds: number[]) {
  return request.post('/authorization-server/oauth-client-details/removeByIds', clientIds).then(response => {
    return response.data
  })
}

/**
 * 根据 客户主键 查询
 * @param usersId 客户主键
 */
export const getById = function (usersId: number) {
  return request.post('/authorization-server/oauth-client-details/getById/' + usersId).then(response => {
    return response.data
  })
}

/**
 * 保存客户
 * @param data 客户
 */
export const save = function (data: any) {
  return request.post('/authorization-server/oauth-client-details/save', data).then(response => {
    return response.data
  })
}

/**
 * 更新客户
 * @param data 客户
 */
export const updateById = function (data: any) {
  return request.post('/authorization-server/oauth-client-details/updateById', data).then(response => {
    return response.data
  })
}

/**
 * 根据 用户名 删除Token
 * @param usernames 用户名
 */
export const removeByUsernames = function (usernames: string[]) {
  return request.post('/authorization-server/oauth-token/removeByUsernames', usernames).then(response => {
    return response.data
  })
}

/**
 * 根据 客户ID 删除Token
 * @param clientIds 客户ID
 */
export const removeByClientIds = function (clientIds: string[]) {
  return request.post('/authorization-server/oauth-token/removeByClientIds', clientIds).then(response => {
    return response.data
  })
}
