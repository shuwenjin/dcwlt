package com.dcits.dcwlt.pay.batch.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.pay.enums.CheckStatusEnum;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import com.dcits.dcwlt.pay.batch.mapper.CheckPathDOMapper;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对账汇总Service业务层处理
 *
 * @author
 * @date 2021-03-09
 */
@Service
public class CheckPathDOServiceImpl implements ICheckPathDOService {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckPathDOMapper checkPathDOMapper;
    @Autowired
    private RestTemplate restTemplate;

    //执行801报文
    private static final String DCEP_PYMT_WRNG_ACCT_DEAL_URL = "http://localhost:9301/dcwlt/dcepPymtWrngAcctDeal";



    /**
     * 查询对账汇总
     *
     * @param paydate 对账汇总ID
     * @return 对账汇总
     */
    @Override
    public CheckPathDO selectCheckPathDOById(String paydate) {
        return checkPathDOMapper.selectCheckPathDOById(paydate);
    }

    /**
     * 查询对账汇总列表
     *
     * @param checkPathDO 对账汇总
     * @return 对账汇总
     */
    @Override
    public List<CheckPathDO> selectCheckPathDOList(CheckPathDO checkPathDO) {
        return checkPathDOMapper.selectCheckPathDOList(checkPathDO);
    }

    @Override
    public AjaxResult execute801(JSONObject dsptChnlReqDTO) {
        JSONObject sendData = this.sendData(dsptChnlReqDTO);
        logger.info("sendData{}",sendData);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(DCEP_PYMT_WRNG_ACCT_DEAL_URL, sendData, String.class);

        JSONObject result = this.checkResult(responseEntity);
        if (result.getBoolean("checkRetCodeStatus")){
            return  AjaxResult.success("手动差错成功");
        }else {
            logger.error("手动差错失败:原因==>{}",result.getString("retInfo"));
            return  AjaxResult.error("手动差错失败:原因==>"+result.getString("retInfo"));
        }

    }

    @Override
    public boolean reconciliation(CheckPathDO checkPathDO) {
        JSONObject sendData = new JSONObject();
        String url="http://10.0.23.169:8888/711";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,  String.class);

        //执行重新对账的操作，更改状态
        checkPathDO.setCheckStatus(CheckStatusEnum.SAME);

        int flag = checkPathDOMapper.updateCheckPathDO(checkPathDO);

        if (flag>0){
            logger.info("responseEntity===>{}",responseEntity);
            return  true;
        }else {
            return false;
        }

    }

    /**
     * 拼接后的json
     * {
     * "head": {
     * "tranDate": "20210317",
     * "tranTime": "103524",
     * "seqNo": "20210113000122532910308590900000"
     * },
     * "ecnyHead": {
     * "busiChnl": "1111",
     * "busiChnl2": "2222",
     * "zoneno": "01",
     * "brno": "01234",
     * "tellerno": "111",
     * "origChnl": "11",
     * "origChnl2": "222",
     * "origChnlDtl": "22"
     * },
     * "body": {
     * "payDate": "20210317",
     * "paySerno": "10352440002"
     * }
     * }
     *
     * @param dsptChnlReqDTO
     * @return
     */
    protected JSONObject sendData(JSONObject dsptChnlReqDTO) {
        Map<String, String> head = new HashMap<>(3);
        head.put("tranDate", "20210317");
        head.put("tranTime", "103524");
        head.put("seqNo", "20210113000122532910308590900000");

        Map<String, String> body = new HashMap<>();
        body.put("tranDate", dsptChnlReqDTO.getString("payDate"));
        body.put("paySerno", dsptChnlReqDTO.getString("paySerno"));
        body.put("msgId",dsptChnlReqDTO.getString("msgId"));
        body.put("checkStatus",dsptChnlReqDTO.getString("checkStatus"));
        body.put("operType",dsptChnlReqDTO.getString("operType"));
        body.put("disputeReasonCode",dsptChnlReqDTO.getString("disputeReasonCode"));
        body.put("disputeReason",dsptChnlReqDTO.getString("disputeReason"));
        body.put("instgPty",dsptChnlReqDTO.getString("batchId"));
        body.put("msgTp",dsptChnlReqDTO.getString("msgTp"));




        Map<String, String> ecnyHead = new HashMap<>();
        ecnyHead.put("busiChnl", "1111");
        ecnyHead.put("busiChnl2", "2222");
        ecnyHead.put("zoneno", "01");
        ecnyHead.put("brno", "01234");
        ecnyHead.put("tellerno", "1234");
        ecnyHead.put("origChnl", "11");
        ecnyHead.put("origChnl2", "22");
        ecnyHead.put("origChnlDtl", "2");

        JSONObject json = new JSONObject();
        json.put("head", head);
        json.put("body", body);
        json.put("ecnyHead", ecnyHead);
        return json;
    }


    /**
     * {"head":{"retCode":"000000","retInfo":"交易成功","tranDate":"20210413","tranTime":"154350","seqNo":"123"},
     * "ecnyRspHead":{"trxStatus":"1"},"body":{"procResult":"登录/退出成功。"}}
     **/
    private JSONObject checkResult(ResponseEntity<String> stringResponseEntity) {
        String body = stringResponseEntity.getBody();

        logger.info("body{}",body);

        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject head = jsonObject.getJSONObject("head");
        String retCode = head.getString("retCode");

      //  logger.info("返回信息===>{}",retInfo);
        head.put("checkRetCodeStatus","000000".equals(retCode));

        return head;
    }


}
