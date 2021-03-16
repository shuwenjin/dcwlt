package com.dcits.dcwlt.pay.online.flow.receive;

import com.dcits.dcwlt.common.pay.channel.bankcore.dto.BankCore996666.BankCore996666Rsp;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.tradeflow.TradeFlow;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPRspDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.txstsqryreq.*;
import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeFlowBuilder;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlInfoMapper;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfoService;
import com.dcits.dcwlt.pay.online.service.impl.BankCoreAccTxnServiceImpl;
import com.dcits.dcwlt.pay.online.service.impl.BankCoreImplDubboService;
import com.dcits.dcwlt.pay.online.service.impl.CoreEventServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 平台回查银行-交易状态查询
 * @author chenyanchun
 */
@Configuration
public class TxStsQry411RTradeFlow {
    private static final Logger logger = LoggerFactory.getLogger(TxStsQry411RTradeFlow.class);

    private static final String TXSTSQRY_TRADE_FLOW = "TxStsQry411RTradeFlow";

    @Autowired
    private BankCoreImplDubboService bankCoreImplDubboService;

    @Autowired
    private BankCoreAccTxnServiceImpl bankCoreAccTxnService;

    @Autowired
    private PayTransDtlInfoMapper payTransDtlInfoMapper;

    @Autowired
    private ICoreProcessService bankCoreProcessService;

    @Autowired
    private CoreEventServiceImpl coreEventService;

    @Autowired
    private IPayTransDtlInfoService payTransDtlInfoService;


    @Bean(name = TXSTSQRY_TRADE_FLOW)
    public TradeFlow txStsQry411RTradeFlow() {
        return EcnyTradeFlowBuilder.get()
                .initRspMsg(this::initRspMsg) //初始化响应报文
                .checkMsg(this::checkMsg)//校验数据
                .initTxn(this::initTxn) //加载原交易
                .process(this::txnStatusHandle)//原交易状态处理
                .response(this::packRspMsg) //响应保存封装
                .errHandler(this::txStsQryErrHandler)//异常处理
                .build();
    }


    /**
     * 校验数据
     * @param context
     */
    private void checkMsg(TradeContext<?,?> context) {
        DCEPReqDTO<TxStsQryReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        TxStsQryReq reqDTO = reqMsg.getBody().getTxStsQryReq();
        TxStsQryOrgnlGrpHdr orgnlGrpHdr = reqDTO.getOrgnlGrpHdr();
        String orgnlMT = orgnlGrpHdr.getOrgnlMT();

        //原查询业务为”兑出“或”汇款兑出“，原收款钱包ID必填
        if((MsgTpEnum.DCEP225.getCode().equals(orgnlMT)
                || MsgTpEnum.DCEP227.getCode().equals(orgnlMT))
                && StringUtils.isBlank(orgnlGrpHdr.getOrgnlCdtrWltId())){
            logger.error("原收款人钱包ID为空");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED, EcnyTransError.C_WALLET_ID_EMPTY);
        }

        //兑回时，原付款钱包ID必填
        if(MsgTpEnum.DCEP221.getCode().equals(orgnlMT)
                && StringUtils.isBlank(orgnlGrpHdr.getOrgnlDbtrWltId())){
            logger.error("原付款人钱包ID为空");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED,EcnyTransError.D_WALLET_ID_EMPTY);
        }

    }

    /**
     * 初始化响应报文
     * @param context
     */
    private void initRspMsg(TradeContext<?,?> context) {
        DCEPReqDTO<TxStsQryReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        TxStsQryReq reqDTO = reqMsg.getBody().getTxStsQryReq();

        TxStsQryOrgnlGrpHdr orgnlGrpHdr = reqDTO.getOrgnlGrpHdr();
        BizQryRef bizQryRef = new BizQryRef();
        bizQryRef.setQryRef(orgnlGrpHdr.getOrgnlMsgId());
        bizQryRef.setQryNm(orgnlGrpHdr.getOrgnlInstgPty());

        TxStsQryRspDTO rspDTO = new TxStsQryRspDTO();
        TxStsQryRsp txStsQryRsp = new TxStsQryRsp();
        txStsQryRsp.setGrpHdr(reqDTO.getGrpHdr());
        txStsQryRsp.setBizQryRef(bizQryRef);
        rspDTO.setTxStsQryRsp(txStsQryRsp);

        //封装响应报文
        DCEPRspDTO<TxStsQryRspDTO> dcepRspDTO = DCEPRspDTO.newInstance(reqMsg, MsgTpEnum.DCEP412.getCode(), rspDTO);
        EcnyTradeContext.setRspMsg(context, dcepRspDTO);
    }

    /**
     * 加载原交易
     * @param context
     */
    private void initTxn(TradeContext<?,?> context) {
        DCEPReqDTO<TxStsQryReqDTO> reqMsg = EcnyTradeContext.getReqMsg(context);
        TxStsQryReq reqDTO = reqMsg.getBody().getTxStsQryReq();
        TxStsQryOrgnlGrpHdr orgnlGrpHdr = reqDTO.getOrgnlGrpHdr();
        String orgnlMsgId = orgnlGrpHdr.getOrgnlMsgId();

        PayTransDtlInfoDO payTransDtlInfoDO = payTransDtlInfoMapper.queryByMsgId(orgnlMsgId);
        if(payTransDtlInfoDO == null){
            logger.error("交易不存在,返回交易处理中");
            throw new EcnyTransException(AppConstant.TRXSTATUS_FAILED,EcnyTransError.PAY_TRANS_DTL_INFO_NOT_EXIST);
        }
        EcnyTradeContext.setTxn(context,payTransDtlInfoDO);
    }

    /**
     * 原交易状态处理
     * @param context
     */
    private void txnStatusHandle(TradeContext<?,?> context) {
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO)EcnyTradeContext.getTxn(context);
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();
        String msgType = payTransDtlInfoDO.getMsgType();

        //兑出、兑回、差错贷记调整来帐
        boolean rTxnFlag = (MsgTpEnum.DCEP225.getCode().equals(msgType)
                || MsgTpEnum.DCEP221.getCode().equals(msgType)
                || MsgTpEnum.DCEP801.getCode().equals(msgType));

        //通道状态，0、1为终态，直接响应平台
        if(AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus)
                || AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus)){
            logger.info("通道状态已达终态，直接响应平台");
            return;
        }

        if(rTxnFlag){
            receiveStatusHandle(payTransDtlInfoDO);
        }

        //拿到最新的交易登记簿信息
        payTransDtlInfoDO = payTransDtlInfoMapper.queryByPayInfo(payTransDtlInfoDO.getPayDate(),payTransDtlInfoDO.getPaySerno());

        EcnyTradeContext.setTxn(context,payTransDtlInfoDO);
    }

    /**
     * 状态机处理
     * @param payTransDtlInfoDO
     */
    private void receiveStatusHandle(PayTransDtlInfoDO payTransDtlInfoDO) {
        logger.info("交易状态查询状态机处理开始，平台日期：{}，平台流水：{}",
                payTransDtlInfoDO.getPayDate(),payTransDtlInfoDO.getPaySerno());

        String trxStatus = payTransDtlInfoDO.getTrxStatus();
        String coreProcStatus = payTransDtlInfoDO.getCoreProcStatus();
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();


        boolean pathEndStatusFlag = (AppConstant.PAYPATHSTATUS_FAILED.equals(pathProcStatus)
                || AppConstant.PAYPATHSTATUS_SUCCESS.equals(pathProcStatus));

        //通道非终态时，核心异常时为2，回查核心，状态机映射，响应平台
        if(!pathEndStatusFlag && AppConstant.CORESTATUS_ABEND.equals(coreProcStatus)){
            //上核心回查
            coreProcStatus = checkBackCore(payTransDtlInfoDO);
            if(!AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus) && !AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)){
                return;
            }
        }

        boolean coreEndStatusFlag =  (AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)
                || AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)
                || AppConstant.CORESTATUS_REVERSED.equals(coreProcStatus));

        //通道非终态时，核心终态0、1、3时，状态机映射，响应平台
        if(!pathEndStatusFlag && coreEndStatusFlag){

            boolean status217 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                    && AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus)
                    && AppConstant.PAYPATHSTATUS_RECIPE.equals(pathProcStatus));

            boolean status207 = (AppConstant.TRXSTATUS_ABEND.equals(trxStatus)
                    && AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)
                    && AppConstant.PAYPATHSTATUS_RECIPE.equals(pathProcStatus));

            //217更新为111
            if(status217){
                payTransDtlInfoService.updateFinalStatus217To111(payTransDtlInfoDO);
            }

            //207更新为000
            if(status207){
                payTransDtlInfoService.updateFinalStatus207To000(payTransDtlInfoDO);
            }

        }
    }

    private String checkBackCore(PayTransDtlInfoDO payTransDtlInfoDO) {

        logger.info("核心状态为异常，回查核心，核心请求日期:{},核心请求流水：{}",payTransDtlInfoDO.getCoreReqDate(), payTransDtlInfoDO.getCoreReqSerno());

        //先主动回查核心，核心回查异常，再登记核心回查事件
        BankCore996666Rsp bankCore996666Rsp = bankCoreImplDubboService.queryCoreStatus(payTransDtlInfoDO.getCoreReqDate(), payTransDtlInfoDO.getCoreReqSerno());
        //核心回查后处理
        bankCoreProcessService.qryCoreStsRetDone(payTransDtlInfoDO,bankCore996666Rsp,new StateMachine());

        //获取最新核心状态
        String coreProcStatus = bankCoreAccTxnService.getCoreStatusMap(bankCore996666Rsp.getTxnSts());

        if(!AppConstant.CORESTATUS_SUCCESS.equals(coreProcStatus) && !AppConstant.CORESTATUS_FAILED.equals(coreProcStatus)){
            logger.info("回查核心异常,登记核心回查事件");
//            coreEventService.registerCoreQry(payTransDtlInfoDO.getCoreReqDate(), payTransDtlInfoDO.getCoreReqSerno(), payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno(), TxStsQryCoreQryCallBack.class);
        }
        return coreProcStatus;
    }

    /**
     * 拼装响应报文
     * @param context
     */
    private void packRspMsg(TradeContext<?,?> context) {
        DCEPRspDTO<TxStsQryRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(context);
        PayTransDtlInfoDO payTransDtlInfoDO = (PayTransDtlInfoDO)EcnyTradeContext.getTxn(context);
        String pathProcStatus = payTransDtlInfoDO.getPathProcStatus();
        TxStsQryRspDTO rspDTO =  dcepRspDTO.getBody();
        BizQryRef bizQryRef = rspDTO.getTxStsQryRsp().getBizQryRef();

        BizRpt bizRpt = new BizRpt();
        OrgnlTxInf orgnlTxInf = new OrgnlTxInf();
        Rsn rsn = new Rsn();

        //应答的原业务信息
        bizQryRef.setQryRs(ProcessStsCdEnum.PR00.getCode());
        bizRpt.setTrnRs(payTransDtlInfoDO.getPayPathRspStatus());

        //通道状态不为终态0,1 响应处理中
        if(!org.apache.commons.lang3.StringUtils.equalsAny(pathProcStatus,AppConstant.PAYPATHSTATUS_SUCCESS,AppConstant.PAYPATHSTATUS_FAILED)){
            bizRpt.setTrnRs(ProcessStsCdEnum.PR02.getCode());
        }

        //原业务信息
        orgnlTxInf.setOrgnlMsgId(payTransDtlInfoDO.getPayPathSerno());
        orgnlTxInf.setOrgnlInstgPty(payTransDtlInfoDO.getInstgPty());
        orgnlTxInf.setOrgnlMT(payTransDtlInfoDO.getMsgType());
        orgnlTxInf.setOrgnlBizTp(payTransDtlInfoDO.getBusiType());
        orgnlTxInf.setOrgnlBizKind(payTransDtlInfoDO.getBusiKind());
        orgnlTxInf.setOrgnlBatchId(payTransDtlInfoDO.getBatchId());
        if(MsgTpEnum.DCEP221.getCode().equals(payTransDtlInfoDO.getMsgType())){
            orgnlTxInf.setDbtrBankId(payTransDtlInfoDO.getPayPathSerno());
        }else{
            orgnlTxInf.setCdtrBankId(payTransDtlInfoDO.getPayPathSerno());
        }

        //原交易原因，当原业务状态为“PR01”时必填
        if(!(ProcessStsCdEnum.PR00.getCode().equals(bizRpt.getTrnRs())
                ||ProcessStsCdEnum.PR02.getCode().equals(bizRpt.getTrnRs()))){
            rsn.setRjctCd(payTransDtlInfoDO.getPayPathRetCode());
            rsn.setRjctInf(payTransDtlInfoDO.getPayPathRetMsg());
        }

        bizRpt.setOrgnlTxInf(orgnlTxInf);
        bizRpt.setRsn(rsn);
        rspDTO.getTxStsQryRsp().setBizRpt(bizRpt);

        logger.info("交易状态查询412响应状态:{},原交易状态：{}",bizQryRef.getQryRs(),bizRpt.getTrnRs());
    }

    /**
     * 异常处理
     * @param context
     * @param exception
     */
    private void txStsQryErrHandler(TradeContext<?,?> context, Throwable exception) {
        logger.error("交易状态查询异常处理");

        RspCodeMapDO rspCodeMapDO = new RspCodeMapDO();
        rspCodeMapDO.setDestRspCode(EcnyTransError.OTHER_TECH_ERROR.getErrorCode());
        rspCodeMapDO.setRspCodeDsp(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());

        if (exception instanceof EcnyTransException) {
            rspCodeMapDO = EcnyTransException.convertRspCode(exception);
            logger.info("映射后错误码错误信息：" + rspCodeMapDO);
        }

        // 响应报文
        DCEPRspDTO<TxStsQryRspDTO> dcepRspDTO = EcnyTradeContext.getRspMsg(context);
        TxStsQryRspDTO txStsQryRspDTO = dcepRspDTO.getBody();
        BizQryRef bizQryRef = txStsQryRspDTO.getTxStsQryRsp().getBizQryRef();

        //查询失败返回失败原因
        bizQryRef.setQryRs(ProcessStsCdEnum.PR01.getCode());
        OprlErr oprlErr = new OprlErr();
        Err err = new Err();
        err.setRjctCd(rspCodeMapDO.getDestRspCode());
        oprlErr.setErr(err);
        oprlErr.setRjctInf(rspCodeMapDO.getRspCodeDsp());
        txStsQryRspDTO.getTxStsQryRsp().setOprlErr(oprlErr);
        logger.info("交易状态查询412应答报文的业务回执状态:{},错误码:{},错误信息:{}",
                bizQryRef.getQryRs(), err.getRjctCd(), oprlErr.getRjctInf());
    }

}
