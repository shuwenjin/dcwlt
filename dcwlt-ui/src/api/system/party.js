import request from '@/utils/request'

// 查询机构列表
export function listParty(query) {
  return request({
    url: '/system/party/list',
    method: 'get',
    params: query
  })
}

// 查询机构详细
export function getParty(partyid) {
  return request({
    url: '/system/party/' + partyid,
    method: 'get'
  })
}

// 新增机构
export function addParty(data) {
  return request({
    url: '/system/party',
    method: 'post',
    data: data
  })
}

// 修改机构
export function updateParty(data) {
  return request({
    url: '/system/party',
    method: 'put',
    data: data
  })
}

// 删除机构
export function delParty(partyid) {
  return request({
    url: '/system/party/' + partyid,
    method: 'delete'
  })
}
