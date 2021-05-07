import request from '@/utils/request'

// 查询任务组信息列表
export function taskGroupExecList(query) {
  return request({
    url: '/pay-batch/taskexec/taskgroupexec/list',
    method: 'GET',
    params: query
  })
}



// 查询某个任务组的详细列表信息
export function taskExecList(query) {
  return request({
    url: '/pay-batch/taskexec/taskexec/list',
    method: 'GET',
    params: query
  })
}


