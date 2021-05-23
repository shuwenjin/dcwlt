package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cashboxBanlance.CashboxBanlanceReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.cashboxBanlance.ChckngInf;
import com.dcits.dcwlt.pay.api.domain.dcep.cashboxBanlance.CshBoxAcctChckngNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.comconf.ComConfDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.model.PayCashboxBanlanceDO;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.PayCashboxBanlanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CashboxBanlance453RTradeFlow {

    private static final Logger logger = LoggerFactory.getLogger(CashboxBanlance453RTradeFlow.class);

    private static final String CASHBOX_TRADE_FLOW = "CashboxBanlance453RTradeFlow";

    private static final String ORIGINAL_MESSAGE_TYPE = "msgTp";

    @Autowired
    private PayCashboxBanlanceMapper payCashboxBanlanceMapper;

    @Bean(name = CASHBOX_TRADE_FLOW)
    public TradeFlow cashboxTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg)      //初始化响应报文
                .process(this::process)    //处理入库
                .errHandler(this::errHandler)
                .build();
    }

    /**
     * 初始化返回报文
     *
     * @param context
     */
    public void initRspMsg(TradeContext<?, ?> context) {

        DCEPReqDTO<CashboxBanlanceReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);

        //初始化返回报文
        CashboxBanlanceReqDTO reqBody = reqMsg.getBody();
        CshBoxAcctChckngNtfctn reqDTO = reqBody.getCshBoxAcctChckngNtfctn();

        //设置原始报文编号，响应结果中需要添加该参数
        String msgId = reqMsg.getBody().getCshBoxAcctChckngNtfctn().getGrpHdr().getMsgId();
        context.getTempCtx().put(ORIGINAL_MESSAGE_TYPE, msgId);

        //业务头组件
        GrpHdr grpHdr = GrpHdr.getInstance(reqMsg.getBody().getCshBoxAcctChckngNtfctn().getGrpHdr());

        //响应信息
        ComConfDTO comConfDTO = ComConfDTO.newInstance(grpHdr, reqMsg.getDcepHead(), "");

        //封装响应报文
        DCEPRspDTO<ComConfDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP902.getCode(), comConfDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * 处理 入库
     *
     * @param context
     */
    private void process(TradeContext<?, ?> context) {

        //获取上下文中的报文数据
        DCEPReqDTO<CashboxBanlanceReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        logger.info("开始处理钱柜入库出库通知，报文标识号：{}", context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE));
        try {
            CashboxBanlanceReqDTO cashboxBanlanceReqDTO = reqMsg.getBody();
            CshBoxAcctChckngNtfctn cshBoxAcctChckngNtfctn = cashboxBanlanceReqDTO.getCshBoxAcctChckngNtfctn();

            ChckngInf chckngInf = cshBoxAcctChckngNtfctn.getChckngInf();

            PayCashboxBanlanceDO payCashboxBanlanceDO = new PayCashboxBanlanceDO();
            payCashboxBanlanceDO.setChckngDt(chckngInf.getChckngDt());
            payCashboxBanlanceDO.setCoopBankInstnId(chckngInf.getCoopBankInstnId());
            payCashboxBanlanceDO.setCoopBankWltId(chckngInf.getCoopBankWltId());
            payCashboxBanlanceDO.setCshBoxInstnId(chckngInf.getCshBoxInstnId());
            payCashboxBanlanceDO.setInitlAmtCcy(chckngInf.getInitlAmt() != null ? chckngInf.getInitlAmt().getCcy() : null);
            payCashboxBanlanceDO.setInitlAmtValue(chckngInf.getInitlAmt() != null ? chckngInf.getInitlAmt().getValue() : null);
            payCashboxBanlanceDO.setCdtDbtInd(chckngInf.getCdtDbtInd());
            payCashboxBanlanceDO.setDbtCntAmtCcy(chckngInf.getDbtCntAmt() != null ? chckngInf.getDbtCntAmt().getCcy() : null);
            payCashboxBanlanceDO.setDbtCntAmtValue(chckngInf.getDbtCntAmt() != null ? chckngInf.getDbtCntAmt().getValue() : null);
            payCashboxBanlanceDO.setCdtCntAmtCcy(chckngInf.getCdtCntAmt() != null ? chckngInf.getCdtCntAmt().getCcy() : null);
            payCashboxBanlanceDO.setCdtCntAmtValue(chckngInf.getCdtCntAmt() != null ? chckngInf.getCdtCntAmt().getValue() : null);
            payCashboxBanlanceDO.setFnlAmtCcy(chckngInf.getFnlAmt() != null ? chckngInf.getFnlAmt().getCcy() : null);
            payCashboxBanlanceDO.setFnlAmtValue(chckngInf.getFnlAmt() != null ? chckngInf.getFnlAmt().getValue() : null);
            payCashboxBanlanceMapper.insert(payCashboxBanlanceDO);
        } catch (Throwable e) {
            logger.warn("报文标识号：{}钱柜入库出库通知处理失败,失败原因:{}",
                    context.getTempCtx().get(ORIGINAL_MESSAGE_TYPE),
                    e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 异常处理
     *
     * @param context
     * @param e
     */
    private void errHandler(TradeContext<?, ?> context, Throwable e) {
        DCEPReqDTO<CashboxBanlanceReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        CashboxBanlanceReqDTO cashboxBanlanceReqDTO = reqMsg.getBody();
        CshBoxAcctChckngNtfctn cshBoxAcctChckngNtfctn = cashboxBanlanceReqDTO.getCshBoxAcctChckngNtfctn();
    }

}
