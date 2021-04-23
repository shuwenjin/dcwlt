import request from '@/utils/request'

// 查询金融交易登记列表
export function listTransdtl(query) {
  return request({
    url: 'pay-batch/transdtl/list',
    method: 'get',
    params: query
  })
}

// 查询金融交易登记详细
export function getTransdtl(paydate) {
  return request({
    url: 'transdtl/' + paydate,
    method: 'get'
  })
}
