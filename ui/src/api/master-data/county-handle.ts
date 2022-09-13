import request from '../../utils/request'

/**
 * 分页查询县
 * @param data
 */
export const page = function (data: any) {
  return request.post('/master-data/county-handle/page', data).then(response => {
    return response.data
  })
}
