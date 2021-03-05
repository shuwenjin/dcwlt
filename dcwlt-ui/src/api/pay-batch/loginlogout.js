import request from '@/utils/request'

// 查询非金融登记簿列表
export function listNonf(query) {
  return request({
    url: '/pay-batch/login/list',
    method: 'get',
    params: query
  })
}

// 查询非金融登记簿详细
export function getNonf(msgid) {
  return request({
    url: '/pay-batch/login/' + msgid,
    method: 'get'
  })
}

