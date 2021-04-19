import request from '@/utils/request'

// 查询对账明细
export function listCheckpathdtl(query) {
  return request({
    url: '/pay-batch/checkpathdtl/list',
    method: 'get',
    params: query
  })
}
// 查询对账明细(不平对账)
export function listCheckpathdetails(query) {
  return request({
    url: '/pay-batch/checkpathdtl/detailsList',
    method: 'get',
    params: query
  })
}
