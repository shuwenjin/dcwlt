import request from '@/utils/request'

// 查询机构列表
export function listParty(query) {
  return request({
    url: '/pay-batch/party/list',
    method: 'get',
    params: query
  })
}

// 查询机构详细
export function getParty(partyid) {
  return request({
    url: '/pay-batch/party/' + partyid,
    method: 'get'
  })
}
// 查询机构信息
export function getPartyInfo() {
  return request({
    url: '/pay-batch/party/info',
    method: 'get'
  })
}



