package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import com.dcits.dcwlt.pay.api.model.StateMachine;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerReq;
import com.dcits.dcwlt.common.pay.channel.bankcore.dto.bankcore351100.BankCore351100InnerRsp;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.ICoreProcessService;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlInfo1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("bankCoreProcessService")
public class BankCoreProcessServiceImpl implements ICoreProcessService {
    private static final Logger logger = LoggerFactory.getLogger(BankCoreProcessServiceImpl.class);

    @Autowired
    private IPayTransDtlInfo1Service payTransDtlInfoRepository;

    @Autowired
    private BankCoreAccTxnService bankCoreAccTxnService;

//    @Autowired
//    private BankCoreImplDubboService bankCoreImplDubboService;



//    public BankCore351100InnerRsp sendToCore(BankCore351100InnerReq bankCore351100InnerReq) {
//
//        logger.info("上核心入账开始，核心日期：{}，核心请求流水：{}",
//                bankCore351100InnerReq.getCoreReqDate(),bankCore351100InnerReq.getCoreReqSerno());
//        BankCore351100InnerRsp bankCore351100InnerRsp;
//
//        try {
////            bankCore351100InnerRsp = bankCoreImplDubboService.coreServer(bankCore351100InnerReq);
//        } catch (Exception e) {
//            logger.error("核心通讯异常：{}-{}", e.getMessage(), e);
//            throw new EcnyTransException(AppConstant.TRXSTATUS_ABEND, EcnyTransError.GATEWAY_REQUEST_ERROR);
//        }
//
//        return bankCore351100InnerRsp;
//    }




    /**
     * 核心前处理，入账务流水表，更新登记簿为状态为处理中,插入操作流水信息
     *
     * @param payTransDtlInfoDO
     * @param bankCore351100InnerReq
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendCorePre(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerReq bankCore351100InnerReq, StateMachine stateMachine) {
        logger.info("sendCorePre: 入账务流水表，更新登记簿为状态为处理中，平台日期：{}，平台流水：{} ",
                payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());

        try {
            bankCoreAccTxnService.insertCoreFlow(bankCore351100InnerReq, bankCore351100InnerReq.getCoreReqSerno(), bankCore351100InnerReq.getCoreReqDate());
            int updateNum = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("更新交易登记簿失败");
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }

            logger.info("sendCorePre: 入账务流水表，更新登记簿为状态为处理中成功,平台日期：{}，平台流水：{} ",
                    payTransDtlInfoDO.getPayDate(), payTransDtlInfoDO.getPaySerno());
        } catch (Exception e) {
            logger.error("sendCorePre: 核心前处理异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sendCoreDone(PayTransDtlInfoDO payTransDtlInfoDO, BankCore351100InnerRsp bankCore351100InnerRsp, StateMachine stateMachine) {
        try {

            int updateNum = bankCoreAccTxnService.updateCoreAccFlow(bankCore351100InnerRsp);
            if (updateNum != 1) {
                logger.error("更新核心流水表失败");
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }

            updateNum = payTransDtlInfoRepository.update(payTransDtlInfoDO, stateMachine);
            if (updateNum != 1) {
                logger.error("更新交易登记簿失败");
                throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
            }
        } catch (Exception e) {
            logger.error("sendCoreDone：接收核心结果，更新对应状态异常：{}-{}", e.getMessage(), e);
            throw new EcnyTransException(EcnyTransError.OTHER_TECH_ERROR);
        }
    }

}
