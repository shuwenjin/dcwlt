import request from '@/utils/request'

// 重发711申请报文
export function resend711Apply(batchId) {
  let data = {
    "head": {
      "seqNo": "1",
      "retCode": "0",
      "retInfo": "",
      "tranDate": "20210308",
      "tranTime": "183056",
    },
    "ecnyHead": {
        "Ver": "01",
        "SndDtTm": "2021-01-13T11:45:54",
        "MsgTp": "dcep.711.001.01",
        "MsgSN": "202101130001917941057896199000000991",
        "Sender": "G4001011000013",
        "Receiver": "C1030644021075",
        "SignSN": "4",
        "origChnl": "ccm",
        "brno": "123456",
        "busiChnl": "vbss"
    },
    "body": {
        "msgType": "dcep.711.001.01",
        "checkDate":batchId.substring(1, 9),//"20210308",
        "batchId": batchId,//"B202103081600",
    }
  };
  return request({
    url: '/dcep/dcwlt/pymtRqstNwlySnd',
    method: 'post',
    data: data
  })
}

// 重发713申请报文
export function resend713Apply(clearDate) {
  let data = {
    "head": {
      "seqNo": "1",
      "retCode": "0",
      "retInfo": "",
      "tranDate": "20210308",
      "tranTime": "183056",
    },
    "ecnyHead": {
        "Ver": "01",
        "SndDtTm": "2021-01-13T11:45:54",
        "MsgTp": "dcep.713.001.01",
        "MsgSN": "202101130001917941057896199000000991",
        "Sender": "G4001011000013",
        "Receiver": "C1030644021075",
        "SignSN": "4",
        "origChnl": "ccm",
        "brno": "123456",
        "busiChnl": "vbss"
    },
    "body": {
        "msgType": "dcep.713.001.01",
        "clearDate":clearDate
    }
  };
  return request({
    url: '/dcep/dcwlt/pymtRqstNwlySnd',
    method: 'post',
    data: data
  })
}
