import request from '@/utils/request'

// 查询在线用户列表
export function list(query) {
  return request({
    url: '/pay-online/monitor/exmonitor/list',
    method: 'POST',
    data: query
  })
}
