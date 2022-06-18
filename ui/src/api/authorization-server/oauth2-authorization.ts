import request from '../../utils/request'

/**
 * 分页查询授权
 * @param data
 */
export const page = function (data: any) {
  return request.post('/authorization-server/oauth2-authorization/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 主键 删除
 * @param id 主键
 */
export const removeById = function (id: string) {
  return request.post('/authorization-server/oauth2-authorization/removeById/' + id).then(response => {
    return response.data
  })
}

/**
 * 根据 主键 删除
 * @param ids 主键
 */
export const removeByIds = function (ids: string[]) {
  return request.post('/authorization-server/oauth2-authorization/removeByIds', ids).then(response => {
    return response.data
  })
}
