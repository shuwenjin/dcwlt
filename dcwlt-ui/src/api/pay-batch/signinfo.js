import request from '@/utils/request'

// 查询协议列表
export function listSigninfo(query) {
  return request({
    url: '/pay-batch/signinfo/list',
    method: 'get',
    params: query
  })
}

// 查询协议详细
export function getSigninfo(signno) {
  return request({
    url: '/pay-batch/signinfo/' + signno,
    method: 'get'
  })
}

// 查询客户信息
export function queryCustInfo(acctid) {
  return request({
    url: '/pay-batch/signinfo/queryCustInfo/'+acctid,
    method: 'get'
  })
}
// 新增协议
export function addSigninfo(data) {
  return request({
    url: '/pay-batch/signinfo',
    method: 'post',
    data: data
  })
}

// 修改协议
export function updateSigninfo(data) {
  return request({
    url: '/pay-batch/signinfo',
    method: 'put',
    data: data
  })
}

// 删除协议
export function delSigninfo(signno) {
  return request({
    url: '/pay-batch/signinfo/' + signno,
    method: 'delete'
  })
}
