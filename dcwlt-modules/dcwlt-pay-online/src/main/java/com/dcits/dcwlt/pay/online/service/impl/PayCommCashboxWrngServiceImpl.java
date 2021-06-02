package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.PayCashboxPartyDO;
import com.dcits.dcwlt.pay.api.model.PayCommCashboxWrng;
import com.dcits.dcwlt.pay.online.mapper.PayCommCashboxWrngMapper;
import com.dcits.dcwlt.pay.online.service.IPayCashboxPartyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 钱柜余额告警Service业务层处理
 *
 * @author dcwlt
 * @date 2021-06-01
 */
@Service
public class PayCommCashboxWrngServiceImpl {
    @Autowired
    private PayCommCashboxWrngMapper payCommCashboxWrngMapper;
    @Autowired
    IPayCashboxPartyService payCashboxPartyService;
    private static final Logger logger = LoggerFactory.getLogger(PayCommCashboxWrngServiceImpl.class);
    /**
     * 钱柜余额告警处理
     *
     * @param freeFmtCnt 自由格式告警报文
     *                   信息内容填写格式为：ALERT/合作银行金融机构编码/运营机构金融机构编码/钱柜ID/当前钱柜余额/钱柜余额预警值/预警内容
     *                   其中预警内容格式如下：XX银行，你行在XX运营机构开立的钱柜余额低于预警值，请你行及时进行钱柜入库
     * @return 结果
     */
    public void freeFmtCashboxWrng(String freeFmtCnt,String creDtTm) {
        String[] fields = freeFmtCnt.split("/");
        if(fields.length>=7){
            PayCommCashboxWrng payCommCashboxWrng = new PayCommCashboxWrng();
            payCommCashboxWrng.setCoopbankInstnid(fields[1]);
            payCommCashboxWrng.setCshboxInstnid(fields[2]);
            payCommCashboxWrng.setCoopbankWltid(fields[3]);
            payCommCashboxWrng.setAmtValue(fields[4]);
            payCommCashboxWrng.setWrngVal(fields[5]);
            payCommCashboxWrng.setMsgCnt(fields[6]);
            payCommCashboxWrng.setWrngTm(creDtTm);
            payCommCashboxWrngMapper.insertPayCommCashboxWrng(payCommCashboxWrng);
            //注册一个告警事件

        }else{
            logger.error("告警报文格式错误！{}", freeFmtCnt);
        }
    }

    /**
     * 查询钱柜余额告警
     *
     * @param coopbankWltid 钱柜余额告警ID
     * @return 钱柜余额告警
     */
    public PayCommCashboxWrng selectPayCommCashboxWrngById(String coopbankWltid) {
        return payCommCashboxWrngMapper.selectPayCommCashboxWrngById(coopbankWltid);
    }

    /**
     * 查询钱柜余额告警列表
     *
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 钱柜余额告警
     */
    public List<PayCommCashboxWrng> selectPayCommCashboxWrngList(PayCommCashboxWrng payCommCashboxWrng) {
        return payCommCashboxWrngMapper.selectPayCommCashboxWrngList(payCommCashboxWrng);
    }

    /**
     * 新增钱柜余额告警
     *
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 结果
     */
    public int insertPayCommCashboxWrng(PayCommCashboxWrng payCommCashboxWrng) {
        return payCommCashboxWrngMapper.insertPayCommCashboxWrng(payCommCashboxWrng);
    }

    /**
     * 修改钱柜余额告警
     *
     * @param payCommCashboxWrng 钱柜余额告警
     * @return 结果
     */
    public int updatePayCommCashboxWrng(PayCommCashboxWrng payCommCashboxWrng) {
        return payCommCashboxWrngMapper.updatePayCommCashboxWrng(payCommCashboxWrng);
    }

    /**
     * 批量删除钱柜余额告警
     *
     * @param coopbankWltids 需要删除的钱柜余额告警ID
     * @return 结果
     */
    public int deletePayCommCashboxWrngByIds(String[] coopbankWltids) {
        return payCommCashboxWrngMapper.deletePayCommCashboxWrngByIds(coopbankWltids);
    }

    /**
     * 删除钱柜余额告警信息
     *
     * @param coopbankWltid 钱柜余额告警ID
     * @return 结果
     */
    public int deletePayCommCashboxWrngById(String coopbankWltid) {
        return payCommCashboxWrngMapper.deletePayCommCashboxWrngById(coopbankWltid);
    }
}
