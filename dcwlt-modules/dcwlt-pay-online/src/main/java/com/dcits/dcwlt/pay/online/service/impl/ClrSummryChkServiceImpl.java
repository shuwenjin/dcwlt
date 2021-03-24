package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk.BatchList;
import com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk.ClrList;
import com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk.ClrSummryChk;
import com.dcits.dcwlt.pay.api.domain.dcep.clrsummrychk.SummryChkInf;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstdPty;
import com.dcits.dcwlt.pay.api.domain.dcep.common.InstgPty;
import com.dcits.dcwlt.pay.api.model.BatchCheckClearDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.flow.receive.CapitalCheckClear713RTradeFlow;
import com.dcits.dcwlt.pay.online.mapper.BatchCheckClearMapper;
import com.dcits.dcwlt.pay.online.service.IClrSummryChkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wanyangwei
 * @desc 资金调整汇总核对服务
 */
@Service
public class ClrSummryChkServiceImpl implements IClrSummryChkService {
    private static final Logger logger = LoggerFactory.getLogger(CapitalCheckClear713RTradeFlow.class);
    private static final BigDecimal coefficient = new BigDecimal(100);

    @Autowired
    private BatchCheckClearMapper batchCheckClearMapper;

    /*
     * 查询资金调整汇总核对信息
     * @Param :msgId 报文标识号
     * @Param :clearNetNum :场次号
     * @Param :batchId 批次号
     * *//*
    @Override
    public ClrSummryChk findByCondition(String msgId, String clearNetNum, String batchId) {
        logger.info("开始执行findByCondition方法,开始时间为->{}", DateTimeUtil.getCurrentDateTimeStr());
        //封装查询条件
        BatchCheckClearDO batchCheckClearDO = new BatchCheckClearDO();
        batchCheckClearDO.setMsgId(msgId);
        batchCheckClearDO.setClearNetNum(clearNetNum);
        batchCheckClearDO.setBatchId(batchId);
        //接收查询结果
        try {
            batchCheckClearDO = batchCheckClearRepository.findBatchCheckClear(batchCheckClearDO);
        } catch (Exception e) {
            logger.error("数据库操作异常,{}", e.getMessage());
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
        //资金调整报文实体
        ClrSummryChk clrSummryChk = new ClrSummryChk();
        //汇总核对信息实体
        SummryChkInf summryChkInf = clrSummryChk.getSummryChkInf();
        //清算日期
        summryChkInf.setClrDt(batchCheckClearDO.getClearDate());
        //调整总笔数
        summryChkInf.setClrCntNb(batchCheckClearDO.getClearCountNum());
        TrxAmt trxAmt = new TrxAmt();
        trxAmt.setCcy("ccy");
        //调整借方金额
        trxAmt.setValue(batchCheckClearDO.getClearDbtTotAmt());
        summryChkInf.setDbtCntAmt(trxAmt);
        //调整贷方金额
        trxAmt.setValue(batchCheckClearDO.getClearCbtTotAmt());
        summryChkInf.setCdtCntAmt(trxAmt);

        //调整场次列表
        List<ClrList> clrList = new ArrayList<>();

        //调整场次编号
        clrInf.setClrNetgRnd(batchCheckClearDO.getClearNetNum());
        //报文标识号
        clrInf.setClrReptFlg(batchCheckClearDO.getClearMsgId());
        //调整借贷标识
        clrInf.setCdtDbtInd(CreditDebitCodeEnum.valueOf(batchCheckClearDO.getClearDrct()));
        //调整金额
        trxAmt.setValue(batchCheckClearDO.getClearAmt());
        clrInf.setClrAmt(trxAmt);
        //批次列表
        BatchList batchList = new BatchList();
        List<BatchInf> batchInfList = new ArrayList<>();
        //批次信息
        BatchInf batchInf = new BatchInf();
        //批次号
        batchInf.setBatchId(batchCheckClearDO.getBatchId());
        //批次借贷标识
        batchInf.setCdtDbtInd(CreditDebitCodeEnum.valueOf(batchCheckClearDO.getBatchDrct()));
        //轧差净额
        trxAmt.setValue(batchCheckClearDO.getBatchNetAmt());
        batchInf.setNetgAmt(trxAmt);

        batchInfList.add(batchInf);
        batchList.setBatchInf(batchInfList);
        clrInf.setBatchList(batchList);

        clrInfList.add(clrInf);
        clrList.setClrInf(clrInfList);
        summryChkInf.setClrList(clrList);

        clrSummryChk.setSummryChkInf(summryChkInf);
        return clrSummryChk;
    }*/

    /*
     * 新增资金调整汇总核对信息
     * */
    @Override
    public int addClrSummryChk(ClrSummryChk clrSummryChk) {
        logger.info("开始执行addClrSummryChk方法,开始时间为: ->{}", DateUtil.getISODateTime());
        GrpHdr grpHdr = clrSummryChk.getGrpHdr();
        InstgPty instgPty = grpHdr.getInstgPty();
        InstdPty instdPty = grpHdr.getInstdPty();
        //汇总核对信息
        SummryChkInf summryChkInf = clrSummryChk.getSummryChkInf();


        //清算场次列表
        List<ClrList> clrList = summryChkInf.getClrList();
        //清算场次信息
        List<BatchCheckClearDO> list = new ArrayList<>();
        if (clrList != null && clrList.size() > 0) {
            for (ClrList clrInf : clrList) {
                List<BatchList> batchList = clrInf.getBatchList();
                if (batchList != null && batchList.size() > 0) {
                    for (BatchList batchInf : batchList) {
                        //封装实体
                        BatchCheckClearDO batchCheckClearDO = new BatchCheckClearDO();
                        //报文标识号
                        batchCheckClearDO.setMsgId(grpHdr.getMsgId());
                        //报文发送时间(原格式为19位2021-01-09T11:15:05,转为14位20210109111505)
                        batchCheckClearDO.setSenderDateTime(grpHdr.getCreDtTm().replaceAll("\\D", ""));
                        //发起机构
                        batchCheckClearDO.setInstgDrctPty(instgPty.getInstgDrctPty());
                        //接收机构
                        batchCheckClearDO.setInstdDrctPty(instdPty.getInstdDrctPty());
                        //备注
                        batchCheckClearDO.setRemark(grpHdr.getRmk());
                        //清算日期
                        batchCheckClearDO.setClearDate(summryChkInf.getClrDt());
                        //清算总笔数
                        batchCheckClearDO.setClearCountNum(summryChkInf.getClrCntNb());
                        /*
                         *  金额转换:传过来数据是以元为单位 格式为x.xx
                         * 需要转换为以分为单位,格式为xxx
                         * */
                        //清算借方总金额
                        BigDecimal clearDbtTotAmt = new BigDecimal(Double.valueOf(summryChkInf.getDbtCntAmt().getValue()));
                        batchCheckClearDO.setClearDbtTotAmt(String.valueOf(clearDbtTotAmt.multiply(coefficient).intValue()));
                        logger.info("清算借方总金额: {}", batchCheckClearDO.getClearDbtTotAmt());
                        //清算贷方总金额
                        BigDecimal clearCbtTotAmt = new BigDecimal(Double.valueOf(summryChkInf.getDbtCntAmt().getValue()));
                        batchCheckClearDO.setClearCbtTotAmt(String.valueOf(clearCbtTotAmt.multiply(coefficient).intValue()));
                        logger.info("清算贷方总金额: {}", batchCheckClearDO.getClearCbtTotAmt());
                        //清算场次编号
                        batchCheckClearDO.setClearNetNum(clrInf.getClrNetgRnd());
                        //清算报文标识号
                        batchCheckClearDO.setClearMsgId(clrInf.getClrReptFlg());
                        //清算借贷标识
                        batchCheckClearDO.setClearDrct(clrInf.getCdtDbtInd());
                        //清算金额
                        BigDecimal clearAmt = new BigDecimal(Double.valueOf(summryChkInf.getDbtCntAmt().getValue()));
                        batchCheckClearDO.setClearAmt(String.valueOf(clearAmt.multiply(coefficient).intValue()));
                        logger.info("清算金额为:{}", batchCheckClearDO.getClearAmt());
                        //批次号
                        batchCheckClearDO.setBatchId(batchInf.getBatchId());
                        //批次借贷标识
                        batchCheckClearDO.setBatchDrct(String.valueOf(batchInf.getCdtDbtInd()));
                        //批次扎差净额
                        BigDecimal batchNetAmt = new BigDecimal(Double.valueOf(summryChkInf.getDbtCntAmt().getValue()));
                        batchCheckClearDO.setBatchNetAmt(String.valueOf(batchNetAmt.multiply(coefficient).intValue()));
                        logger.info("扎差净额:{}", batchCheckClearDO.getBatchNetAmt());
                        //最后更新日期
                        batchCheckClearDO.setLastUpDate(DateUtil.getDefaultDate());
                        //最后更新时间
                        batchCheckClearDO.setLastUpTime(DateUtil.getDefaultTime());
                        list.add(batchCheckClearDO);
                    }
                }
            }
        }
        try {
            list.forEach(batchCheckClearDO -> {batchCheckClearMapper.addBatchCheckClear(batchCheckClearDO);});
            logger.info("新增资金调整汇总核对信息成功,->{}", list);
        } catch (Exception e) {
            logger.error("资金调整汇总核对数据库操作异常");
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
        logger.info("执行addClrSummryChk方法结束,结束时间为: ->{}", DateUtil.getISODateTime());
        return Integer.parseInt(AppConstant.TRXSTATUS_FAILED);
    }
}
