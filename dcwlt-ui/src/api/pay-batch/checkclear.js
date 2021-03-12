import request from '@/utils/request'

// 查询资金调整汇总核对列表
export function listCheckclear(query) {
  return request({
    url: '/pay-batch/checkclear/list',
    method: 'get',
    params: query
  })
}

// 查询资金调整汇总核对详细
export function getCheckclear(msgid) {
  return request({
    url: '/pay-batch/checkclear/' + msgid,
    method: 'get'
  })
}

