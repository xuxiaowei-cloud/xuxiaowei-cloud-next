import request from '../../utils/request'

export interface Oauth2AuthorizationConsentPrimaryKey {
  registeredClientId: string,
  principalName: string
}

/**
 * 分页查询授权同意书
 * @param data
 */
export const page = function (data: any) {
  return request.post('/passport/oauth2-authorization-consent/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 联合主键 删除
 * @param data
 */
export const removeById = function (data: Oauth2AuthorizationConsentPrimaryKey) {
  return request.post('/passport/oauth2-authorization-consent/removeById', data).then(response => {
    return response.data
  })
}

/**
 * 根据 联合主键 删除
 * @param data
 */
export const removeByIds = function (data: Oauth2AuthorizationConsentPrimaryKey[]) {
  return request.post('/passport/oauth2-authorization-consent/removeByIds', data).then(response => {
    return response.data
  })
}
