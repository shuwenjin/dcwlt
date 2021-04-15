import request from '@/utils/request'

// 查询对账明细
export function listCheckpathdtl(query) {
  return request({
    url: '/pay-batch/checkpathdtl/list',
    method: 'get',
    params: query
  })
}

