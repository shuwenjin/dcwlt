import request from '@/utils/request'

// 查询签约流水列表
export function listJrn(query) {
  return request({
    url: '/pay-batch/jrn/list',
    method: 'get',
    params: query
  })
}

// 查询客户信息
export function queryCustInfo(acctid) {
  return request({
    url: '/pay-batch/signinfo/queryCustInfo/'+acctid,
    method: 'get'
  })
}
// 查询签约流水详细
export function getJrn(paydate) {
  return request({
    url: '/pay-batch/jrn/' + paydate,
    method: 'get'
  })
}

// 新增签约流水
export function addJrn(data) {
  return request({
    url: '/pay-batch/jrn',
    method: 'post',
    data: data
  })
}

// 修改签约流水
export function updateJrn(data) {
  return request({
    url: '/pay-batch/jrn',
    method: 'put',
    data: data
  })
}

// 删除签约流水
export function delJrn(paydate) {
  return request({
    url: '/pay-batch/jrn/' + paydate,
    method: 'delete'
  })
}
