import request from '@/utils/request'

// 查询证书管理列表
export function listCerts(query) {
  return request({
    url: '/pay-batch/certs/list',
    method: 'get',
    params: query
  })
}

// 查询证书管理详细
export function getCerts(partyId) {
  return request({
    url: '/pay-batch/certs/' + partyId,
    method: 'get'
  })
}

// 新增证书管理
export function addCerts(data) {
  return request({
    url: '/pay-batch/certs',
    method: 'post',
    data: data
  })
}

// 修改证书管理
export function updateCerts(data) {
  return request({
    url: '/pay-batch/certs',
    method: 'put',
    data: data
  })
}

// 删除证书管理
export function delCerts(partyId) {
  return request({
    url: '/pay-batch/certs/' + partyId,
    method: 'delete'
  })
}
