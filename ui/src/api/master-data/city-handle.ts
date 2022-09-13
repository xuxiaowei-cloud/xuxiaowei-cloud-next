import request from '../../utils/request'

/**
 * 分页查询城市
 * @param data
 */
export const page = function (data: any) {
  return request.post('/master-data/city-handle/page', data).then(response => {
    return response.data
  })
}
