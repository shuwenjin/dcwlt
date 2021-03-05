import request from '@/utils/request'

// 查询自由格式列表
export function listNonf(query) {
  return request({
    url: '/pay-batch/freefrmt/list',
    method: 'get',
    params: query
  })
}

// 查询自由格式详细
export function getNonf(msgid) {
  return request({
    url: '/pay-batch/freefrmt/' + msgid,
    method: 'get'
  })
}
// 导出自由格式
export function exportNonf(data) {
  return request({
    url: '/pay-batch/freefrmt/export',
    method: 'post',
    data: data
  })
}
