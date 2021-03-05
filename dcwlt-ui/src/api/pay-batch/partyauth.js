import request from '@/utils/request'

// 查询业务权限变更信息列表
export function listPartyauth(query) {
  return request({
    url: '/pay-batch/partyauth/list',
    method: 'get',
    params: query
  })
}

// 查询业务权限变更信息详细
export function getPartyauth(partyid) {
  return request({
    url: '/pay-batch/partyauth/' + partyid,
    method: 'get'
  })
}

// 新增业务权限变更信息
export function addPartyauth(data) {
  return request({
    url: '/pay-batch/partyauth',
    method: 'post',
    data: data
  })
}

// 修改业务权限变更信息
export function updatePartyauth(data) {
  return request({
    url: '/pay-batch/partyauth',
    method: 'put',
    data: data
  })
}

// 删除业务权限变更信息
export function delPartyauth(partyid) {
  return request({
    url: '/pay-batch/partyauth/' + partyid,
    method: 'delete'
  })
}
