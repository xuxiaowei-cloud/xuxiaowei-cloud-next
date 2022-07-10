import request from '../utils/request'

/**
 * 使用授权码获取Token
 * @param code 授权码
 * @param state 状态码
 */
export const authorizationCode = function (code: string, state: string) {
  return request.post('/code', {
    code,
    state
  }).then(response => {
    return response.data
  })
}
