import request from '@/utils/request'

// 查询运营机构钱柜参数列表
export function listParty(query) {
  return request({
    url: '/pay-batch/cashparty/list',
    method: 'get',
    params: query
  })
}

// 查询运营机构钱柜参数详细
export function getParty(id) {
  return request({
    url: '/pay-batch/cashparty/' + id,
    method: 'get'
  })
}

// 新增运营机构钱柜参数
export function addParty(data) {
  return request({
    url: '/pay-batch/cashparty',
    method: 'post',
    data: data
  })
}

// 修改运营机构钱柜参数
export function updateParty(data) {
  return request({
    url: '/pay-batch/cashparty',
    method: 'put',
    data: data
  })
}
// 修改运营机构钱柜预警余额
export function updatePartyWarn(data) {
  return request({
    url: '/pay-batch/cashparty/warn',
    method: 'put',
    data: data
  })
}
// 删除运营机构钱柜参数
export function delParty(id) {
  return request({
    url: '/pay-batch/cashparty/' + id,
    method: 'delete'
  })
}
