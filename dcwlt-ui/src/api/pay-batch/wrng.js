import request from '@/utils/request'

// 查询钱柜余额告警列表
export function listWrng(query) {
  return request({
    url: '/pay-batch/wrng/list',
    method: 'get',
    params: query
  })
}

// 查询钱柜余额告警详细
export function getWrng(coopbankWltid) {
  return request({
    url: '/pay-batch/wrng/' + coopbankWltid,
    method: 'get'
  })
}
