import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listProcess(query) {
  return request({
    url: '/pay-batch/process/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getProcess(id) {
  return request({
    url: '/pay-batch/process/' + id,
    method: 'get'
  })
}
// 查询【请填写功能名称】详细
export function bankRev(type,id) {
  return request({
    url: '/pay-batch/process/bankRev?type='+type+'&id='+id,
    method: 'get'
  })
}

export function changeApprovalStuts(type,id) {
  return request({
    url: '/pay-batch/process/changeApprovalStuts?appStuts='+type+'&id='+id,
    method: 'get'
  })
}
// 新增钱柜出库入库异常处理
export function sendCashbox(data) {
  return request({
    url: '/pay-batch/process/sendCashbox',
    headers:{
      'Content-Type':'application/json'
    },
    method: 'post',
    data: data
  })
}

// 新增【请填写功能名称】
export function addProcess(data) {
  return request({
    url: '/pay-batch/process',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateProcess(data) {
  return request({
    url: '/pay-batch/process',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delProcess(id) {
  return request({
    url: '/pay-batch/process/' + id,
    method: 'delete'
  })
}
