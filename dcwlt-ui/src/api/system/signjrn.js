import request from '@/utils/request'

// 查询signjrn列表
export function listSignjrn(query) {
  return request({
    url: '/pay-online/sign/signjrn/list',
    method: 'POST',
    data: query
  })
}
