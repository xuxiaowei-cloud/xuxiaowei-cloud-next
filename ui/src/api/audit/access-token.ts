import request from '../../utils/request'

/**
 * 分页查询授权Token
 * @param data
 */
export const page = function (data: any) {
  return request.post('/audit/oauth-access-token/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 授权Token主键 删除
 * @param oauthAccessTokenId 授权Token主键
 */
export const removeById = function (oauthAccessTokenId: number) {
  return request.post('/audit/oauth-access-token/removeById/' + oauthAccessTokenId).then(response => {
    return response.data
  })
}

/**
 * 根据 授权Token主键 删除
 * @param oauthAccessTokenIds 授权Token主键
 */
export const removeByIds = function (oauthAccessTokenIds: number[]) {
  return request.post('/audit/oauth-access-token/removeByIds', oauthAccessTokenIds).then(response => {
    return response.data
  })
}
