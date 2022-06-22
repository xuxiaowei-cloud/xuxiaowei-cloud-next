import request from '../utils/request'
import store from '../store'
import settings from '../settings'
import { ElMessage } from 'element-plus'

/**
 * 用户信息
 */
export const info = function () {
  return request.post('/user/info').then(response => {
    console.log('用户信息', response)
    const responseData = response.data
    if (responseData.code === settings.state.okCode) {
      const data = responseData.data
      const usersId = data.usersId
      const username = data.username
      const nickname = data.nickname

      store.commit('setUsersId', usersId)
      store.commit('setUsername', username)
      store.commit('setNickname', nickname)
    } else {
      ElMessage({
        message: responseData.msg,
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 3000,
        type: 'error'
      })
    }
  })
}

/**
 * 分页查询用户
 * @param data
 */
export const page = function (data: any) {
  return request.post('/user/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 用户主键 删除
 * @param usersId 用户主键
 */
export const removeById = function (usersId: number) {
  return request.post('/user/removeById/' + usersId).then(response => {
    return response.data
  })
}

/**
 * 根据 用户主键 删除
 * @param usersIds 用户主键
 */
export const removeByIds = function (usersIds: number[]) {
  return request.post('/user/removeByIds', usersIds).then(response => {
    return response.data
  })
}

/**
 * 根据 用户主键 查询
 * @param usersId 用户主键
 */
export const getById = function (usersId: number) {
  return request.post('/user/getById/' + usersId).then(response => {
    return response.data
  })
}

/**
 * 保存用户
 * @param data 用户
 */
export const save = function (data: any) {
  return request.post('/user/save', data).then(response => {
    return response.data
  })
}

/**
 * 更新用户
 * @param data 用户
 */
export const updateById = function (data: any) {
  return request.post('/user/updateById', data).then(response => {
    return response.data
  })
}

/**
 * 获取 权限与权限说明 字典
 */
export const authorityList = function () {
  return request.post('/user/authority/list', {}).then(response => {
    return response.data
  })
}

/**
 * 保存 权限表
 * @param data 权限表
 */
export const saveAuthorities = function (data: any) {
  return request.post('/user/authorities/save', data).then(response => {
    return response.data
  })
}

/**
 * 获取用户识别码
 */
export const codeRsa = function () {
  return request.post('/user/code/rsa').then(response => {
    return response.data
  })
}
