import request from '@/utils/request'

// 查询对账汇总列表
export function listCheckpath(query) {
  return request({
    url: '/pay-batch/checkpath/list',
    method: 'get',
    params: query
  })
}

// 查询对账汇总详细
export function getCheckpath(paydate) {
  return request({
    url: '/pay-batch/checkpath/' + paydate,
    method: 'get'
  })
}

//send801
export function send801(data){
  return request({
    url:'/pay-batch/checkpath/executeSend801',
    method: 'post',
    params: data
  })
 }




  //重新调账
  export function executeReconciliation(data){
    return request({
      url:'/pay-batch/checkpath/reconciliation',
      method: 'post',
      params: data
    })
  }
