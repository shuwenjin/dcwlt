import request from '@/utils/request'

// 查询调度日志列表
export function listJobLog(query, isRetryJob) {
  if (isRetryJob) {
    return request({
      url: '/schedule/job/log/retryList',
      method: 'get',
      params: query
    })
  }
  return request({
    url: '/schedule/job/log/list',
    method: 'get',
    params: query
  })
}

// 删除调度日志
export function delJobLog(jobLogId) {
  return request({
    url: '/schedule/job/log/' + jobLogId,
    method: 'delete'
  })
}

// 清空调度日志
export function cleanJobLog(isRetryJobLog) {
  if (isRetryJobLog) {
    return request({
      url: '/schedule/job/log/cleanRetry',
      method: 'delete'
    })
  }
  return request({
    url: '/schedule/job/log/clean',
    method: 'delete'
  })
}
