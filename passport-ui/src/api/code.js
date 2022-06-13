import request from '@/utils/request'

/**
 * 使用授权码获取Token
 * @param code 授权码
 * @param state 状态码
 * @returns {Promise<AxiosResponse<any>>}
 */
export const authorizationCode = function (code, state) {
  return request.post('/code', {
    code: code,
    state: state
  }).then(response => {
    return response.data
  })
}
