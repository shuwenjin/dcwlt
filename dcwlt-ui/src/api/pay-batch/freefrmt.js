import request from '@/utils/request'

// 查询自由格式列表
export function listNonf(query) {
  return request({
    url: '/pay-batch/freefrmt/list',
    method: 'get',
    params: query
  })
}


// 查询机构编码列表
export function queryPartyList() {
  return request({
    url: '/pay-batch/party/list',
    method: 'get'
    // params: query
  })
}


// 新增定时任务调度
export function addNonf(data) {
  console.info(data);
  return request({
    url: '/pay-batch/freefrmt/sendFreeFrmt',
    method: 'post',
    data: data
  })
}

// 查询自由格式详细
export function getNonf(msgid) {
  return request({
    url: '/pay-batch/freefrmt/' + msgid,
    method: 'get'
  })
}
// 导出自由格式
export function exportNonf(data) {
  return request({
    url: '/pay-batch/freefrmt/export',
    method: 'post',
    data: data
  })
}

//自由格式发送的报文体
function reqData(tlrNO, msgContext,instdDrctPty) {
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
    "instdDrctPty":instdDrctPty,
    "msgContext": msgContext,
    "tlrNO": tlrNO
  };
  var requestData = {
    "head": head,
    "ecnyHead": ecnyHead,
    "body": body
  }
  return requestData;
}



//发送自由格式（从pay-online 直接访问）
export function pymtFrdmFmtMsgSnd(tlrNO, msgContext,instdDrctPty) {
  let requestData = reqData(tlrNO, msgContext,instdDrctPty);
  console.info(requestData);
  return request({
    url: '/dcwlt-pay-online/dcwlt/pymtFrdmFmtMsgSnd',
    method: 'post',
    data: requestData
  })
}
