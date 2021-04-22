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

//登入登出的报文体
function reqData(tlrNo, opterationType) {
  
  var body = {
    "opterationType": opterationType,
    "tlrNo": tlrNo
  };
  return body;

}



//登入操作（从pay-online 直接访问）
export function loginoutFmtMsgSnd(tlrNo, opterationType) {

  let requestData = reqData(tlrNo, opterationType);
  console.info(requestData);

  return request({
    url: '/dcwlt-pay-online/login/loginoutFmtMsgSnd',
    method: 'post',
    data: requestData
  })
}
