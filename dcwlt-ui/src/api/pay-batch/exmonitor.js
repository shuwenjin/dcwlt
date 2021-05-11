import request from '@/utils/request'

// 查询异常信息列表
export function list(query) {
  return request({
    url: '/pay-batch/monitor/exmonitor/list',
    method: 'get',
    params: query
  })
}
