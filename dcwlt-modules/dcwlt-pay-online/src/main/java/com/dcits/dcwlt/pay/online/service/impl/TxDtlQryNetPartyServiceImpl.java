package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstdPty;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstgPty;
import com.dcits.dcwlt.pay.api.domain.dcep.common.OrgnlGrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery.TxDtlQryReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery.TxDtlQryRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.transdetailquery.TxDtlsQryReq;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.ITxDtlQryNetPartyService;
import org.apache.commons.lang3.StringUtils;
import com.dcits.dcwlt.pay.online.base.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * 交易明细查询
 * 交易路径 五羊支付 --》 金融开放平台 --》互联互通
 *
 * @author yangqijun-yfzx
 */
@Service
@Validated
public class TxDtlQryNetPartyServiceImpl implements ITxDtlQryNetPartyService {
    private static final Logger logger = LoggerFactory.getLogger(TxDtlQryNetPartyServiceImpl.class);
    @Autowired
    DcepSendService dcepSendService;

    @Autowired
    IGenerateCodeService generateCodeService;

    /**
     * 交易明细查询
     *
     * @param orgnlGrpHdr 原交易主键组件
     * @return TxDtlQryRspDTO 交易明细响应报文体
     * <p>
     * 查询结果失败 返回为null
     * @throws javax.validation.ConstraintViolationException 字段校验不通过
     * @throws EcnyTransException                            调用异常
     */
    @Override
    public TxDtlQryRspDTO txDtlQryNetParty(@Valid OrgnlGrpHdr orgnlGrpHdr) {
        logger.info("开始执行交易明细查询.");
        //输入校验,可被查询的业务 221、225、227、801
        String msgTpOrg = orgnlGrpHdr.getOrgnlMT();
        if (!StringUtils.equalsAny(msgTpOrg, MsgTpEnum.DCEP221.getCode(), MsgTpEnum.DCEP225.getCode(),
                MsgTpEnum.DCEP227.getCode(), MsgTpEnum.DCEP801.getCode())) {
            //暂不校验
        }
        //生成请求body
        TxDtlQryReqDTO txDtlQryReqDTO = createReqBody(orgnlGrpHdr);
        //报文编号
        String msgTp = MsgTpEnum.DCEP417.getCode();
        //通讯级流水号
        String msgSn = generateCodeService.generateMsgSN(txDtlQryReqDTO.getTxDtlsQryReq().getGrpHdr().getMsgId());
        // 接收方运营机构 即 互联互通
        String receiver = AppConstant.DCEP_FINANCIAL_INSTITUTION_CD;
        //生成开放平台实体
        DCEPReqDTO<TxDtlQryReqDTO> dcepReqDTO = DCEPReqDTO.newInstance(msgTp, msgSn, receiver, txDtlQryReqDTO);
        logger.info("发送交易明细查询到互联互通.");
        //通过金融开放平台 --》互联互通
        JSONObject jsonObject = dcepSendService.sendDcep(dcepReqDTO);
        //解析并响应
        //判断开放平台调用是否成功
        if (null == jsonObject || null == jsonObject.get("head")) {
            logger.error("调用金融开放平台异常！响应报文：{}", jsonObject);
            throw new EcnyTransException(EcnyTransError.CALL_COP_ERROR);
        }
        if (!Constant.SERVER_SUCC_RSPCODE.equals(jsonObject.getJSONObject("head").get("retCode"))) {
            logger.error("调用互联互通异常！响应报文：{}", jsonObject);
            throw new EcnyTransException(EcnyTransError.CALL_DCEP_ERROR);
        }
        //判断ecnyhead,响应的是否为 418 报文
        JSONObject body = jsonObject.getJSONObject("body");
        if (null == jsonObject.get("ecnyHead") ||
                !MsgTpEnum.DCEP418.getCode().equals(jsonObject.getJSONObject("ecnyHead").get("MsgTp")) ||
                null == body) {
            logger.error("互联互通响应报文非法：{}", jsonObject);
            throw new EcnyTransException(EcnyTransError.BADRSP_ERROR);
        }
        TxDtlQryRspDTO txDtlQryRspDTO = JSON.toJavaObject(body, TxDtlQryRspDTO.class);

        logger.info("交易明细查询返回消息体：{}", txDtlQryRspDTO);
        return txDtlQryRspDTO;
    }

    /**
     * 生成请求body
     */
    private TxDtlQryReqDTO createReqBody(OrgnlGrpHdr orgnlGrpHdr) {
        TxDtlQryReqDTO txDtlQryReqDTO = new TxDtlQryReqDTO();
        TxDtlsQryReq txDtlQryReq = new TxDtlsQryReq();
        txDtlQryReq.setOrgnlGrpHdr(orgnlGrpHdr);

        //生成业务头组件
        GrpHdr grpHdr = new GrpHdr();
        //生成报文标识号
        String msgId = generateCodeService.generateMsgId(OutOrgTypeEnum.OutOrg, MsgTpEnum.DCEP417.getCode().substring(5, 8));
        grpHdr.setMsgId(msgId);
        //业务处理时间
        grpHdr.setCreDtTm(DateUtil.getISODateTime());

        //发起机构 即 广发
        InstgPty instgPty = new InstgPty(AppConstant.CGB_FINANCIAL_INSTITUTION_CD);
        grpHdr.setInstgPty(instgPty);

        //接收机构 即 互联互通
        InstdPty instdPty = new InstdPty(AppConstant.DCEP_FINANCIAL_INSTITUTION_CD);
        grpHdr.setInstdPty(instdPty);

        txDtlQryReq.setGrpHdr(grpHdr);
        txDtlQryReqDTO.setTxDtlsQryReq(txDtlQryReq);
        return txDtlQryReqDTO;
    }
}
