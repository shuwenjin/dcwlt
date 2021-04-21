import request from '@/utils/request'

// 查询对账明细
export function listCheckpathdtl(query) {
  return request({
    url: '/pay-batch/checkpathdtl/list',
    method: 'get',
    params: query
  })
}


// //send801
// export function send801(data){
//   console.info(data)
//   return request({
//     url:'/pay-batch/checkpath/executeSend801',
//     method:'post',
//     data:JSON.stringify(data),
//     dataType:"json"
//   })
//  }




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


//登入登出的报文体
function reqData(data) {
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

  /**
   *    Map<String, String> body = new HashMap<>();
        body.put("tranDate", dsptChnlReqDTO.getString("payDate"));
        body.put("paySerno", dsptChnlReqDTO.getString("paySerno"));
        body.put("msgId",dsptChnlReqDTO.getString("msgId"));
        body.put("checkStatus",dsptChnlReqDTO.getString("checkStatus"));
        body.put("operType",dsptChnlReqDTO.getString("operType"));
        body.put("disputeReasonCode",dsptChnlReqDTO.getString("disputeReasonCode"));
        body.put("disputeReason",dsptChnlReqDTO.getString("disputeReason"));
        body.put("instgPty",dsptChnlReqDTO.getString("batchId"));
        body.put("msgTp",dsptChnlReqDTO.getString("msgTp"));
   */
  var body = {
    "tranDate":form.payDate,
    "paySerno": form.paySerno,
    "msgId": form.msgId,
    "checkStatus": form.checkStatus,
    "operType":form.operType,
    "disputeReasonCode":form.disputeReasonCode,
    "disputeReason":form.disputeReason,
    "instgPty":form.batchId,
    "msgTp":form.msgTp


  };
  var requestData = {
    "head": head,
    "ecnyHead": ecnyHead,
    "body": body
  }
  return requestData;
}

//send801
export function send801(form){
  let reqData=reqData(form);
  return request({
    url:'/dcwlt-pay-online/dcwlt/changeAccount',
    method:'post',
    data:reqData
  })
 }
