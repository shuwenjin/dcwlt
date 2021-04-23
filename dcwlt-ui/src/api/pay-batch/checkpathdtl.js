import request from '@/utils/request'

// 查询对账明细
export function listCheckpathdtl(query) {
  return request({
    url: '/pay-batch/checkpathdtl/list',
    method: 'get',
    params: query
  })
}




// 查询参数配置详细
export function querySingle(query) {
  return request({
    url: '/pay-batch/checkpathdtl/querySingle',
    method: 'get',
    params: query
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


//手动差错的报文体
function reqData(data) {

  var body = {
    "tranDate": form.workdate,
    "paySerno": form.amount,
    "msgId": form.msgId,
    "checkStatus": form.checkStatus,
    "operType": form.operType,
    "disputeReasonCode": form.disputeReasonCode,
    "disputeReason": form.disputeReason,
    "instgPty": form.batchId,
    "msgTp": form.msgTp
  };
  return body;
}

//send801
export function send801(form) {
 // let body = reqData(form);
  return request({
    url: '/pay-online/checkpath/changeAccount',
    method: 'post',
    data: form
  })
}
