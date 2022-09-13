import request from '../../utils/request'

/**
 * 分页查询镇
 * @param data
 */
export const page = function (data: any) {
  return request.post('/master-data/town-handle/page', data).then(response => {
    return response.data
  })
}
