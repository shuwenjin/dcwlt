import request from '@/utils/request'

// 查询非金融交易统计报表列表
export function listNonfinancereport(query) {
  return request({
    url: '/pay-batch/nonfinancereport/list',
    method: 'get',
    params: query
  })
}

// 查询非金融交易统计报表详细
export function getNonfinancereport(reportDate) {
  return request({
    url: '/pay-batch/nonfinancereport/' + reportDate,
    method: 'get'
  })
}

// 新增非金融交易统计报表
export function addNonfinancereport(data) {
  return request({
    url: '/pay-batch/nonfinancereport',
    method: 'post',
    data: data
  })
}

// 修改非金融交易统计报表
export function updateNonfinancereport(data) {
  return request({
    url: '/pay-batch/nonfinancereport',
    method: 'put',
    data: data
  })
}

// 删除非金融交易统计报表
export function delNonfinancereport(reportDate) {
  return request({
    url: '/pay-batch/nonfinancereport/' + reportDate,
    method: 'delete'
  })
}
