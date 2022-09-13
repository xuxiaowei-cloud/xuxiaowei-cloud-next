import request from '../../utils/request'

/**
 * 分页查询居委会
 * @param data
 */
export const page = function (data: any) {
  return request.post('/master-data/village-handle/page', data).then(response => {
    return response.data
  })
}
