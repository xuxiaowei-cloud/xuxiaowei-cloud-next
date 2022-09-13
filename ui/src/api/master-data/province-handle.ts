import request from '../../utils/request'

/**
 * 分页查询省份
 * @param data
 */
export const page = function (data: any) {
  return request.post('/master-data/province-handle/page', data).then(response => {
    return response.data
  })
}
