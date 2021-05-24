import request from '@/utils/request'

// 查询协议列表
export function listSigninfo(query) {
  return request({
    url: '/pay-batch/sign/signinfo/list',
    method: 'POST',
    data: query
  })
}
