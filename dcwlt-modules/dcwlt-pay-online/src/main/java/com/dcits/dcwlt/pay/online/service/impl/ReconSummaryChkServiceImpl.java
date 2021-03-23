package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.sequence.service.impl.GenerateCodeServiceImpl;
import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.common.pay.util.AmountUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.summarychk.*;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.api.model.ReconSummaryChkDO;
import com.dcits.dcwlt.pay.api.model.SummaryInfoDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.builder.EcnyTradeContext;
import com.dcits.dcwlt.pay.online.service.IReconSummaryChkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author weimeiyuan
 * @Time 2021/2/4 15:24
 * @Version 1.0
 * Description:机构对账汇总事务同时处理入库
 */
@Service
public class ReconSummaryChkServiceImpl implements IReconSummaryChkService {
    private static final Logger logger = LoggerFactory.getLogger(AuthInfoServiceimpl.class);
    @Autowired
    private ReconSummaryChkimpl reconSummaryChkRepository;
    @Autowired
    private DtlFileInfServiceimpl dtlFileInfRepository;
    @Autowired
    private GenerateCodeServiceImpl generateCodeService;
    @Autowired
    private SummaryInfoServiceImpl summaryInfoRepository;


    /**
     * 接收机构对账汇总数据入库处理
     * @param tradeContext
     * @return
     * @Transactional 将方法里的所有方法作为一个事务，要么都成功要么都失败
     */
    @Override
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)

    public int saveSummary(TradeContext<?, ?> tradeContext) {
        logger.info("机构对账汇总流程开始-{}");
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ReconSummaryChkDTO reconSummaryChkDTO = reqMsg.getBody();
        //返回标识
        int count = 0;

        /********step 1:汇总总记录信息入库***********************/
        ReconSummaryChkDO reconSummaryChkDO = saveReconSummaryChkDTO(tradeContext);
        int saveReconSummaryChkDTO = 0;
        if (null != reconSummaryChkDO && reconSummaryChkDO != null) {
            try {
                saveReconSummaryChkDTO = reconSummaryChkRepository.replaceReconSummaryChkDO(reconSummaryChkDO);
                logger.info("对总信息入库成功，汇总信息：{}", saveReconSummaryChkDTO);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("汇总信息入库失败,报文标识号为：{}", reconSummaryChkDO.getMsgId());
                throw new EcnyTransException(e.getMessage(), EcnyTransError.DATABASE_ERROR.getErrorCode(),EcnyTransError.DATABASE_ERROR.getErrorMsg());
            }
        }
        /********step2: 业务对账清单信息    入库前先检查是否存在相同标识，存在则删除，否则入库***************/
        List<SummaryInfoDO> summaryInfoDOS = saveSummaryInfoDO(tradeContext);
        int summaryInfo = 0;
        if (!summaryInfoDOS.isEmpty()) {
            for (SummaryInfoDO summaryInfoDO : summaryInfoDOS) {
                try {
                    summaryInfo = summaryInfoRepository.replaceSummaryInfoDO(summaryInfoDO);
                    logger.info("对账汇总业务对账清单信息入库成功，对账明细信息为：{}", summaryInfo);
                } catch (Exception e) {
                    logger.error("对账清单信息入库失败,对账明细信息为：{}", summaryInfoDOS);
                    throw new EcnyTransException(e.getMessage(), EcnyTransError.DATABASE_ERROR.getErrorCode(),EcnyTransError.DATABASE_ERROR.getErrorMsg());
                }
            }
        }

        /*************step3:对账明细文件信息入库******************/
        List<DtlFileInfDO> dtlFileInfDOS = saveDtlFileInfDO(tradeContext);
        int dtlFileInfo = 0;
        if (dtlFileInfDOS != null && !dtlFileInfDOS.isEmpty()) {
            for (DtlFileInfDO dtlFileInf : dtlFileInfDOS) {
                try {
                    dtlFileInfo = dtlFileInfRepository.replaceDtlFileInfDO(dtlFileInf);
                    logger.info("对账文件明细入库成功，明细文件信息为：{}", dtlFileInf);
                } catch (Exception e) {
                    logger.error("对账明细文件入库失败，明细文件信息为：{}", dtlFileInf);
                    throw new EcnyTransException(e.getMessage(), EcnyTransError.DATABASE_ERROR.getErrorCode(),EcnyTransError.DATABASE_ERROR.getErrorMsg());
                }
            }
        }
        if (saveReconSummaryChkDTO > 0 && summaryInfo > 0 && dtlFileInfo > 0) {
            count = count + 1;
        }
        logger.info("机构对账汇总711报文入库流程完成{}", DateUtil.getDefaultTime(), reconSummaryChkDTO.getReconSummaryChk().getGrpHdr().getMsgId());
        return count;
    }
    /**
     * step 1:汇总总记录信息入库
     * @param tradeContext
     * @return
     */
    private ReconSummaryChkDO saveReconSummaryChkDTO(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ReconSummaryChkDTO summaryChkDTO = reqMsg.getBody();
        ReconSummaryChkDO summaryChkDO = new ReconSummaryChkDO();
        ReconSummaryChk reconSummaryChk = summaryChkDTO.getReconSummaryChk();
        //业务组件
        GrpHdr grpHdr = reconSummaryChk.getGrpHdr();
        //对账汇总核对信息
        SummaryChkInf summaryChkInf = reconSummaryChk.getSummaryChkInf();
        //对账汇总消息头
        SummaryHdr summaryHdr = summaryChkInf.getSummaryHdr();
        //区块链对账数据索引
        summaryChkDO.setReconIndex(summaryChkDTO.getReconSummaryChk().getSummaryChkInf().getReconIndex());
        //平台日期
        summaryChkDO.setPayDate(DateUtil.getDefaultDate());
        //平台流水
        summaryChkDO.setPaySerNo(generateCodeService.generatePlatformFlowNo());
        //平台日期
        summaryChkDO.setPayTime(DateUtil.getDefaultTime());
        //数字信封
        summaryChkDO.setDigitalEnvelope(reqMsg.getDcepHead().getDgtlEnvlp());
        //最后更新日期
        summaryChkDO.setLastUpDate(DateUtil.getDefaultDate());
        //最后更新时间
        summaryChkDO.setLastUpTime(DateUtil.getDefaultTime());
        /********************GrpHdr业务组件部分*****************************/
        //报文标识号
        summaryChkDO.setMsgId(grpHdr.getMsgId());
        //报文发送时间   时间：2021-01-14T09:24:01转 20210114092401
        summaryChkDO.setSenderDateTime(grpHdr.getCreDtTm());
        //机构发起方
        summaryChkDO.setInstgDrctPty(grpHdr.getInstgPty().getInstgDrctPty());
        //机构接收方
        summaryChkDO.setInstdDrctPty(grpHdr.getInstdPty().getInstdDrctPty());
        //备注
        summaryChkDO.setRemark(grpHdr.getRmk());

        /************************SummaryChkInf对账汇总信息部分*****************************/
        //交易批次号
        summaryChkDO.setBatchId(summaryHdr.getBatchId());
        //批次日期
        summaryChkDO.setBatchDate(reconSummaryChk.getSummaryChkInf().getSummaryHdr().getBatchId().substring(1, 9));
        //汇总记录总笔数
        summaryChkDO.setCountNum(summaryHdr.getCntNb());
        //汇总记录总金额
        summaryChkDO.setCountAmt(AmountUtil.yuanToFen(summaryHdr.getCntAmt().getValue()));
        //汇款记录总笔数
        summaryChkDO.setCcY(summaryHdr.getCntAmt().getCcy());
        //汇总记录付款总笔数
        summaryChkDO.setDbitCountNum(summaryHdr.getDbtCntNb());
        //汇总记录付款总金额
        summaryChkDO.setDbitCountAmt(AmountUtil.yuanToFen(summaryHdr.getDbtCntAmt().getValue()));
        //汇总记录收款笔数总数
        summaryChkDO.setCrdtCountNum(summaryHdr.getCdtCntNb());
        //汇总记录收款金额总数
        summaryChkDO.setCrdtCountAmt(AmountUtil.yuanToFen(summaryHdr.getCdtCntAmt().getValue()));
        return summaryChkDO;
    }

    /**
     * step2: 业务对账清单信息
     * @param tradeContext
     * @return
     */
    private List<SummaryInfoDO> saveSummaryInfoDO(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ReconSummaryChkDTO summaryChkDTO = reqMsg.getBody();
        ReconSummaryChk reconSummaryChk = summaryChkDTO.getReconSummaryChk();
        SummaryBody summaryBody = reconSummaryChk.getSummaryChkInf().getSummaryBody();
        List<SummaryInfoDO> arrayList = new ArrayList<>();
        if (null != summaryBody && !summaryBody.getSummaryGrp().isEmpty()) {
            for (SummaryGrp summaryGrp : summaryBody.getSummaryGrp()) {
                //业务信息
                ChkPayList chkPayList = summaryGrp.getChkPayList();
                List<ChkPayInf> chkPayInf = null;
                if (null != chkPayList) {
                    chkPayInf = chkPayList.getChkPayInf();
                }
                if (null != chkPayInf && !chkPayInf.isEmpty()) {
                    for (ChkPayInf chkPay : chkPayInf) {
                        SummaryInfoDO summaryInfoDO = new SummaryInfoDO();
                        //报文标识
                        summaryInfoDO.setMsgId(reconSummaryChk.getGrpHdr().getMsgId());
                        //批次日期
                        summaryInfoDO.setBatchDate(reconSummaryChk.getSummaryChkInf().getSummaryHdr().getBatchId().substring(1, 9));
                        //交易批次号
                        summaryInfoDO.setBatchId(reconSummaryChk.getSummaryChkInf().getSummaryHdr().getBatchId());
                        //最后更新日期
                        summaryInfoDO.setLastUpDate(DateUtil.getDefaultDate());
                        //最后更新时间
                        summaryInfoDO.setLastUpTime(DateUtil.getDefaultTime());
                        /***************SummaryBody<SummaryGrp>分片信息*************/
                        //分片编号
                        summaryInfoDO.setSplitNum(summaryGrp.getNb());
                        //分片总笔数
                        summaryInfoDO.setSplitCountNum(summaryGrp.getCntNb());
                        //分片总金额
                        summaryInfoDO.setSplitCountAmt(AmountUtil.yuanToFen(summaryGrp.getCntAmt().getValue()));
                        //分片记录付款笔数总数
                        summaryInfoDO.setSplitDbitCountNum(summaryGrp.getDbtCntNb());
                        //分片记录付款金额总数
                        summaryInfoDO.setSplitDbitCountAmt(AmountUtil.yuanToFen(summaryGrp.getDbtCntAmt().getValue()));
                        //汇总记录收款笔数总
                        summaryInfoDO.setSplitCrdtCountNum(summaryGrp.getCdtCntNb());
                        //汇总记录收款金额总数
                        summaryInfoDO.setSplitCrdtCountAmt(AmountUtil.yuanToFen(summaryGrp.getCdtCntAmt().getValue()));

                        /******************ChkPayList<ChkPayInf>业务清单信息************/
                        //报文编号
                        summaryInfoDO.setMsgType(chkPay.getMsgTp());
                        //业务状态
                        summaryInfoDO.setMsgBizStatus(chkPay.getBizSts());
                        //总笔数
                        summaryInfoDO.setMsgCountNum(chkPay.getCntNb());
                        //总金额
                        summaryInfoDO.setMsgCountAmt(AmountUtil.yuanToFen(chkPay.getCntAmt().getValue()));
                        //付款笔数
                        summaryInfoDO.setMsgDbitCountNum(chkPay.getDbtCntNb());
                        //付款金额
                        summaryInfoDO.setMsgDbitCountAmt(AmountUtil.yuanToFen(chkPay.getDbtCntAmt().getValue()));
                        //收款笔数
                        summaryInfoDO.setMsgCrdtCountNum(chkPay.getCdtCntNb());
                        //收款金额
                        summaryInfoDO.setMsgCrdtCountAmt(AmountUtil.yuanToFen(chkPay.getCdtCntAmt().getValue()));
                        arrayList.add(summaryInfoDO);
                    }
                }
            }
        }
        return arrayList;
    }

    /**
     * step3:对账明细文件信息入库
     * @param tradeContext
     * @return
     */
    private List<DtlFileInfDO> saveDtlFileInfDO(TradeContext<?, ?> tradeContext) {
        DCEPReqDTO<ReconSummaryChkDTO> reqMsg = EcnyTradeContext.getReqMsg(tradeContext);
        ReconSummaryChkDTO summaryChkDTO = reqMsg.getBody();
        ReconSummaryChk reconSummaryChk = summaryChkDTO.getReconSummaryChk();
        SummaryChkInf summaryChkInf = reconSummaryChk.getSummaryChkInf();

        //对账汇总消息头
        SummaryHdr summaryHdr = summaryChkInf.getSummaryHdr();
        DtlFileInf dtlFileInf = summaryChkInf.getDtlFileInf();
        List<FileInf> fileInfs = null;
        if (null != dtlFileInf) {
            FileInfList fileInfList = dtlFileInf.getFileInfList();
            if (null != fileInfList) {
                fileInfs = fileInfList.getFileInf();
            }
        }
        ArrayList<DtlFileInfDO> arrayList = new ArrayList<>();
        if (null != fileInfs && !fileInfs.isEmpty()) {
            for (FileInf fileInf : fileInfs) {
                FileNameList fileNameList = fileInf.getFileNameList();
                List<FileName> fileName = null;
                if (null != fileNameList) {
                    fileName = fileNameList.getFileName();
                }
                if (null != fileName && !fileName.isEmpty()) {
                    for (FileName fileNameList1 : fileName) {
                        DtlFileInfDO dtlFileInfDO = new DtlFileInfDO();
                        //报文标识
                        dtlFileInfDO.setMsgId(reconSummaryChk.getGrpHdr().getMsgId());
                        //批次号
                        dtlFileInfDO.setBatchId(summaryHdr.getBatchId());
                        //最后更新日期
                        dtlFileInfDO.setLastUpDate(DateUtil.getDefaultDate());
                        //最后更新时间
                        dtlFileInfDO.setLastUpTime(DateUtil.getDefaultTime());
                        //文件路径
                        dtlFileInfDO.setSrcFilePath(fileInf.getFilePath());
                        //文件名称
                        dtlFileInfDO.setFileName(fileNameList1.getFileName());
                        arrayList.add(dtlFileInfDO);
                    }
                }
            }
        }
        return arrayList;
    }
}
