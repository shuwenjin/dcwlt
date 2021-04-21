import request from '@/utils/request'

// 查询交易重发申请列表
export function listReSendApy(query) {
  return request({
    url: '/pay-batch/ReSendApy/list',
    method: 'get',
    params: query
  })
}

// 交易重发申请
export function addReSendApy(data) {
  return request({
    url: '/pay-online/ReSendApy/resend',
    method: 'post',
    data: data
  })
}
