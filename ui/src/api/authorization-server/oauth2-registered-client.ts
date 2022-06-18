import request from '../../utils/request'

/**
 * 分页查询客户
 * @param data
 */
export const page = function (data: any) {
  return request.post('/authorization-server/oauth2-registered-client/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 客户主键 删除
 * @param clientId 客户主键
 */
export const removeById = function (clientId: number) {
  return request.post('/authorization-server/oauth2-registered-client/removeById/' + clientId).then(response => {
    return response.data
  })
}

/**
 * 根据 客户主键 删除
 * @param clientIds 客户主键
 */
export const removeByIds = function (clientIds: number[]) {
  return request.post('/authorization-server/oauth2-registered-client/removeByIds', clientIds).then(response => {
    return response.data
  })
}

/**
 * 根据 客户主键 查询
 * @param usersId 客户主键
 */
export const getById = function (usersId: string) {
  return request.post('/authorization-server/oauth2-registered-client/getById/' + usersId).then(response => {
    return response.data
  })
}

/**
 * 保存客户
 * @param data 客户
 */
export const save = function (data: any) {
  return request.post('/authorization-server/oauth2-registered-client/save', data).then(response => {
    return response.data
  })
}

/**
 * 更新客户
 * @param data 客户
 */
export const updateById = function (data: any) {
  return request.post('/authorization-server/oauth2-registered-client/updateById', data).then(response => {
    return response.data
  })
}
