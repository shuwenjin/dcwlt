import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listBanlance(query) {
  return request({
    url: '/pay-batch/banlance/list',
    method: 'get',
    params: query
  })
}

// 查询【请填写功能名称】详细
export function getBanlance(id) {
  return request({
    url: '/pay-batch/banlance/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addBanlance(data) {
  return request({
    url: '/pay-batch/banlance',
    method: 'post',
    data: data
  })
}

// 修改【请填写功能名称】
export function updateBanlance(data) {
  return request({
    url: '/pay-batch/banlance',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delBanlance(id) {
  return request({
    url: '/pay-batch/banlance/' + id,
    method: 'delete'
  })
}


// 查询运营机构钱柜参数列表
export function listPartyfor(query) {
  // 查询参数
  query= {pageNum: 1,pageSize: 100};
  return request({
    url: '/pay-batch/cashparty/list',
    method: 'get',
    params: query
  })
}

