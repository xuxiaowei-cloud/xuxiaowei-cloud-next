import request from '../../utils/request'

/**
 * 分页查询授权码
 * @param data
 */
export const page = function (data: any) {
  return request.post('/audit/oauth-code/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 授权码Code主键 删除
 * @param codeId 授权码Code主键
 */
export const removeById = function (codeId: number) {
  return request.post('/audit/oauth-code/removeById/' + codeId).then(response => {
    return response.data
  })
}

/**
 * 根据 授权码Code主键 删除
 * @param codeIds 授权码Code主键
 */
export const removeByIds = function (codeIds: number[]) {
  return request.post('/audit/oauth-code/removeByIds', codeIds).then(response => {
    return response.data
  })
}
