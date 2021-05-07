import request from '@/utils/request'

// 查询任务组信息列表
export function taskGroupInfoList(query) {
  return request({
    url: '/pay-batch/taskinfo/taskgroupinfo/list',
    method: 'GET',
    params: query
  })
}

// 添加任务组
export function addTaskGroup(data) {
  return request({
    url: '/pay-batch/taskinfo/taskgroupinfo',
    method: 'POST',
    data: data
  })
}

// 修改任务组
export function updateTaskGroup(data) {
  return request({
    url: '/pay-batch/taskinfo/taskgroupinfo',
    method: 'PUT',
    data: data
  })
}

// 删除任务组
export function delTaskGroup(taskGroupCodes) {
  return request({
    url: '/pay-batch/taskinfo/taskgroupinfo/' + taskGroupCodes,
    method: 'DELETE'
  })
}


// 查询某个任务组的详细列表信息
export function taskInfoList(query) {
  return request({
    url: '/pay-batch/taskinfo/taskinfo/list',
    method: 'GET',
    params: query
  })
}


// 添加任务信息
export function addTaskInfo(data) {
  return request({
    url: '/pay-batch/taskinfo/taskinfo',
    method: 'POST',
    data: data
  })
}

// 修改任务信息
export function updateTaskInfo(data) {
  return request({
    url: '/pay-batch/taskinfo/taskinfo',
    method: 'PUT',
    data: data
  })
}

// 删除任务信息
export function delTaskInfo(taskCodes) {
  return request({
    url: '/pay-batch/taskinfo/taskinfo/' + taskCodes,
    method: 'DELETE'
  })
}