import request from '../../utils/request'

/**
 * 查询字典代码列表
 */
export const list = function () {
  return request.get('/master-data/dict/list').then(response => {
    return response.data
  })
}

/**
 * 分页查询字典
 * @param data
 */
export const page = function (data: any) {
  return request.post('/master-data/dict/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 字典代码 删除
 * @param dictCode 字典代码
 */
export const removeById = function (dictCode: string) {
  return request.post('/master-data/dict/removeById/' + dictCode).then(response => {
    return response.data
  })
}

/**
 * 根据 字典代码 删除
 * @param dictCodes 字典代码
 */
export const removeByIds = function (dictCodes: string[]) {
  return request.post('/master-data/dict/removeByIds', dictCodes).then(response => {
    return response.data
  })
}

/**
 * 根据 字典代码 查询
 * @param dictCode 字典代码
 */
export const getById = function (dictCode: string) {
  return request.post('/master-data/dict/getById/' + dictCode).then(response => {
    return response.data
  })
}

/**
 * 保存字典
 * @param data 字典
 */
export const save = function (data: any) {
  return request.post('/master-data/dict/save', data).then(response => {
    return response.data
  })
}

/**
 * 更新字典
 * @param data 字典
 */
export const updateById = function (data: any) {
  return request.post('/master-data/dict/updateById', data).then(response => {
    return response.data
  })
}
