import request from '@/utils/request'

// 查询金融交易统计报表列表
export function listFinancereport(query) {
  return request({
    url: '/pay-batch/financereport/list',
    method: 'get',
    params: query
  })
}

// 查询金融交易统计报表详细
export function getFinancereport(reportDate) {
  return request({
    url: '/pay-batch/financereport/' + reportDate,
    method: 'get'
  })
}

// 新增金融交易统计报表
export function addFinancereport(data) {
  return request({
    url: '/pay-batch/financereport',
    method: 'post',
    data: data
  })
}

// 修改金融交易统计报表
export function updateFinancereport(data) {
  return request({
    url: '/pay-batch/financereport',
    method: 'put',
    data: data
  })
}

// 删除金融交易统计报表
export function delFinancereport(reportDate) {
  return request({
    url: '/pay-batch/financereport/' + reportDate,
    method: 'delete'
  })
}
