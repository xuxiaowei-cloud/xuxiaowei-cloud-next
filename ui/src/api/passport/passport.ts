import request from '../../utils/request'

/**
 * 根据 用户名 删除Token
 * @param usernames 用户名
 */
export const removeByUsernames = function (usernames: string[]) {
  return request.post('/passport/oauth-token/removeByUsernames', usernames).then(response => {
    return response.data
  })
}

/**
 * 根据 客户ID 删除Token
 * @param clientIds 客户ID
 */
export const removeByClientIds = function (clientIds: string[]) {
  return request.post('/passport/oauth-token/removeByClientIds', clientIds).then(response => {
    return response.data
  })
}
