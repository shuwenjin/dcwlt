import request from '@/utils/request'

// 查询参数配置列表
export function listParam(query) {
  return request({
    url: '/pay-batch/param/list',
    method: 'get',
    params: query
  })
}

// 查询参数配置详细
export function getParam(id) {
  return request({
    url: '/pay-batch/param/' + id,
    method: 'get'
  })
}

// 新增参数配置
export function addParam(data) {
  return request({
    url: '/pay-batch/param',
    method: 'post',
    data: data
  })
}

// 修改参数配置
export function updateParam(data) {
  return request({
    url: '/pay-batch/param',
    method: 'put',
    data: data
  })
}

// 删除参数配置
export function delParam(id) {
  return request({
    url: '/pay-batch/param/' + id,
    method: 'delete'
  })
}
