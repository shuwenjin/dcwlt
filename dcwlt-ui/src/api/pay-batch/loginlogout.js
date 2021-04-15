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

// 登入
export function handerLogin(msgid) {
  console.info("123123");
  return request({
    url: '/pay-batch/login/login' + msgid,
    method: 'get'
  })
}


// 登出
export function handerLoginout(msgid) {
  return request({
    url: '/pay-batch/login/loginout' + msgid,
    method: 'get'
  })
}
