import request, { AjaxResponse } from '../utils/request'
import { useStore } from '../store'
import settings from '../settings'
import { ElMessage } from 'element-plus'
import { AxiosResponse } from 'axios'

/**
 * 权限
 */
export interface AuthorityVo {
  authority: String;
  explain: String;
}

/**
 * 用户信息
 */
export interface UsersVo {
  usersId: number; // 用户主键
  username: string; // 用户名
  email: string; // 邮箱
  emailValid: Boolean; // 邮箱是否验证
  nickname: string; // 昵称
  sex: string;
  sexLabel: string;
  sexExplain: string;
  birthday: string;
  provinceCode: number | string;
  provinceName: string;
  cityCode: number | string;
  cityName: string;
  countyCode: number | string;
  countyName: string;
  townCode: number | string;
  townName: string;
  villageCode: number | string;
  villageName: string;
  detailAddress: string;
  enabled: Boolean;
  accountNonExpired: Boolean;
  credentialsNonExpired: Boolean;
  accountNonLocked: Boolean;
  createDate: string;
  updateDate: string;
  authorityList: Array<AuthorityVo>;
}

/**
 * 用户信息
 */
export const info = function () {
  return request.post('/user/info').then((response: AxiosResponse<AjaxResponse<UsersVo>>) : AjaxResponse<UsersVo> => {
    console.log('用户信息', response)
    const responseData = response.data
    if (responseData.code === settings.okCode) {
      const data = responseData.data
      const usersId = data.usersId
      const username = data.username
      const nickname = data.nickname

      useStore.setUsersId(usersId)
      useStore.setUsername(username)
      useStore.setNickname(nickname)
    } else {
      ElMessage({
        message: responseData.msg,
        // 显示时间，单位为毫秒。设为 0 则不会自动关闭，类型：number，默认值：3000
        duration: 3000,
        type: 'error'
      })
    }
    return response.data
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
export const update = function (data: any) {
  return request.post('/user/update', data).then(response => {
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
