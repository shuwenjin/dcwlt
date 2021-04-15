package com.dcits.dcwlt.pay.batch.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import org.apache.poi.hssf.record.DVALRecord;
import org.aspectj.weaver.loadtime.Aj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.dcits.dcwlt.pay.batch.mapper.CheckPathDOMapper;
import com.dcits.dcwlt.pay.batch.service.ICheckPathDOService;
import org.springframework.web.client.RestTemplate;

/**
 * 对账汇总Service业务层处理
 *
 * @author
 * @date 2021-03-09
 */
@Service
public class CheckPathDOServiceImpl implements ICheckPathDOService {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CheckPathDOMapper checkPathDOMapper;
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://localhost:8000/dcwlt-pay-online/dcwlt/dcepPymtWrngAcctDeal   ";



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
    public boolean execute801(CheckPathDO checkPathDO) {
        JSONObject sendData = this.sendData(checkPathDO);
        logger.info("sendData{}",sendData);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, sendData, String.class);

        boolean checkResult = this.checkResult(responseEntity);
        return  checkResult;
    }

    @Override
    public boolean reconciliation(CheckPathDO checkPathDO) {
        JSONObject sendData = new JSONObject();
        String url="http://10.7.91.61:8888/711";

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url,  String.class);

        logger.info("responseEntity===>{}",responseEntity);
        return  true;
    }

    /**
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
     * @param checkPathDO
     * @return
     */
    protected JSONObject sendData(CheckPathDO checkPathDO) {
        Map<String, String> head = new HashMap<>();
        head.put("tranDate", "20210317");
        head.put("tranTime", "103524");
        head.put("seqNo", "20210113000122532910308590900000");

        Map<String, String> body = new HashMap<>();
        body.put("tranDate", checkPathDO.getPayDate());
        body.put("paySerno", checkPathDO.getPaySerno());
        //

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
    protected boolean checkResult(ResponseEntity<String> stringResponseEntity) {
        String body = stringResponseEntity.getBody();
        logger.info("body{}",body);
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject head = jsonObject.getJSONObject("head");
        String retCode = head.getString("retCode");
        return "000000".equals(retCode);
    }


}
