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


function body(tlrNo, opterationType) {
  var head = {
    "tranDate": "20210317",
    "tranTime": "103524",
    "seqNo": "20210113000122532910308590900000"
  };
  var ecnyHead = {
    "busiChnl": "1111",
    "busiChnl2": "2222",
    "zoneno": "01",
    "brno": "01234",
    "tellerno": "111",
    "origChnl": "11",
    "origChnl2": "22",
    "origChnlDtl": "2"
  };
  var body = {
    "opterationType": opterationType,
    "tlrNo": tlrNo
  };
  var requestData = {
    "head": head,
    "ecnyHead": ecnyHead,
    "body": body
  }

  return requestData;
  
}
// 登入
export function handerLogin(msgid) {

  var requestData = body("OT00");
  console.info(requestData);
  return request({
    url: '/pay-batch/login/login/' + msgid,
    method: 'get'
  })
}


// 登出
export function handerLoginout(msgid) {
  return request({
    url: '/pay-batch/login/loginout/' + msgid,
    method: 'get'
  })
}


//登入操作（从pay-online 直接访问）
export function loginoutFmtMsgSnd(tlrNo, opterationType) {
  
  let requestData = body(tlrNo, opterationType);
  console.info(requestData);
 
  return request({
    url: '/dcwlt-pay-online/dcwlt/loginoutFmtMsgSnd',
    method: 'post',
    data: requestData
  })
}
