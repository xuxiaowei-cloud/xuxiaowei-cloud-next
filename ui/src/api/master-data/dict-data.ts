import request from '../../utils/request'

export interface PrimaryKey {
  dictCode: string;
  dictDataCode: string;
}

/**
 * 根据字典代码查询字典列表
 * @param dictCode 字典代码
 */
export const listByDictCode = function (dictCode: string) {
  return request.get('/master-data/dict-data/' + dictCode).then(response => {
    return response.data
  })
}

/**
 * 分页查询字典数据
 * @param data
 */
export const page = function (data: any) {
  return request.post('/master-data/dict-data/page', data).then(response => {
    return response.data
  })
}

/**
 * 根据 字典代码 删除
 * @param data
 */
export const removeById = function (data: PrimaryKey) {
  return request.post('/master-data/dict-data/removeById', data).then(response => {
    return response.data
  })
}

/**
 * 根据 字典代码 删除
 * @param data
 */
export const removeByIds = function (data: PrimaryKey[]) {
  return request.post('/master-data/dict-data/removeByIds', data).then(response => {
    return response.data
  })
}

/**
 * 根据 字典代码、字典数据代码（联合主键） 查询
 * @param data
 */
export const getById = function (data: PrimaryKey) {
  return request.post('/master-data/dict-data/getById', data).then(response => {
    return response.data
  })
}

/**
 * 保存字典
 * @param data 字典
 */
export const save = function (data: any) {
  return request.post('/master-data/dict-data/save', data).then(response => {
    return response.data
  })
}

/**
 * 更新字典
 * @param data 字典
 */
export const updateById = function (data: any) {
  return request.post('/master-data/dict-data/updateById', data).then(response => {
    return response.data
  })
}
