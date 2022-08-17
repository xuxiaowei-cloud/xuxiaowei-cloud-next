import request from '../utils/request'

/**
 * 当前时间戳
 */
export const currentTimeMillis = () => {
  return request.get('/currentTimeMillis').then((response: any) => {
    return response.data?.data?.currentTimeMillis
  })
}
