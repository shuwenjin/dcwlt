import request from '@/utils/request'

// 查询对账明细
export function listCheckpathdtl(query) {
  return request({
    url: '/pay-batch/checkpathdtl/list',
    method: 'get',
    params: query
  })
}


//send801
export function send801(data){
  console.info(data)
  return request({
    url:'/pay-batch/checkpath/executeSend801',
    method: 'post',
    params: data
  })
 }




// 查询参数配置详细
export function querySingle(query) {
  return request({
    url: '/pay-batch/checkpathdtl/querySingle',
    method: 'get',
    params:query
    })
 }
 
 
// 查询对账明细(不平对账)
export function listCheckpathdetails(query) {
  return request({
    url: '/pay-batch/checkpathdtl/detailsList',
    method: 'get',
    params: query
  })
}
