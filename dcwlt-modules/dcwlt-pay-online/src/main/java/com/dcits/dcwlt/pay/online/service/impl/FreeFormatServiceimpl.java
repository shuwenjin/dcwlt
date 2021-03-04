package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.MsgTpEnum;
import com.dcits.dcwlt.common.pay.enums.ProcessStsCdEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.common.pay.util.GenerateCodeUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.common.GrpHdr;
import com.dcits.dcwlt.pay.api.domain.dcep.freefrmt.FreeFrmt;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlNonfMapper;
import com.dcits.dcwlt.pay.online.service.IFreeFormatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @desc 自由报文格式服务
 */

@Service
public class FreeFormatServiceimpl implements IFreeFormatService {

    private static final Logger logger = LoggerFactory.getLogger(FreeFormatServiceimpl.class);


    @Autowired
    private PayTransDtlNonfMapper payTransDtlNonfmapper;

    /*
     * 保存自由格式
     * @param freeFrmt 自由格式报文
     * @Param direct 往来方向
     * @Param tlrNo 柜员号(非必输)
     * @Param flag 状态标识号
     * */
    public int insertOrUpdateFreeFormat(FreeFrmt freeFrmt, String direct, String tlrNo, String flag) {
        logger.debug("开始执行自由格式报文入库方法,开始时间为：{}", DateUtil.getSysTime());
        PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
        GrpHdr grpHdr = freeFrmt.getGrpHdr();
        //平台日期
        payTransDtlNonfDO.setPayDate(DateUtil.getDefaultDate());
        //平台时间
        payTransDtlNonfDO.setPayTime(DateUtil.getDefaultTime());
        //平台流水
        payTransDtlNonfDO.setPaySerNo(GenerateCodeUtil.generatePlatformFlowNo());
        //报文标识号
        payTransDtlNonfDO.setMsgId(grpHdr.getMsgId());
        //报文发送时间(原格式为19位2021-01-09T11:15:05,转为14位20210109111505)
        payTransDtlNonfDO.setSenderDateTime(grpHdr.getCreDtTm().replaceAll("\\D", ""));
        //报文方向
        payTransDtlNonfDO.setDrct(direct);
        //信息内容
        payTransDtlNonfDO.setMessageContext(freeFrmt.getFreeFrmtInf().getMsgCnt());
        //发起机构
        payTransDtlNonfDO.setInstgDrctPty(grpHdr.getInstgPty().getInstgDrctPty());
        //接收机构
        payTransDtlNonfDO.setInstdDrctPty(grpHdr.getInstdPty().getInstdDrctPty());
        //最后更新日期
        payTransDtlNonfDO.setLastUpDate(DateUtil.getDefaultDate());
        //最后更新时间
        payTransDtlNonfDO.setLastUpTime(DateUtil.getDefaultTime());
        //报文编号
        payTransDtlNonfDO.setPkgNo(MsgTpEnum.DCEP401.getCode());
        //柜员号
        payTransDtlNonfDO.setTlrNo(tlrNo);
        //初始化状态
        if (AppConstant.TRXSTATUS_INIT.equals(flag)) {
            //交易状态
            payTransDtlNonfDO.setTradeStatus(AppConstant.TRXSTATUS_INIT);
            //业务处理状态
            payTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR02.getCode());
            //新增入库
            try {
                //执行入库
                return payTransDtlNonfmapper.insertPayTransDtlNonf(payTransDtlNonfDO);
            } catch (Exception e) {
                logger.error("自由报文入库异常:{}-{}", e.getMessage(),e);
                throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
            }
        } else if (AppConstant.TRXSTATUS_SUCCESS.equals(flag)) {
            //交易状态
            payTransDtlNonfDO.setTradeStatus(AppConstant.TRXSTATUS_SUCCESS);
            //业务处理状态
            payTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR00.getCode());
        } else if (AppConstant.TRXSTATUS_FAILED.equals(flag)) {
            //交易状态
            payTransDtlNonfDO.setTradeStatus(AppConstant.TRXSTATUS_FAILED);
            //业务处理状态
            payTransDtlNonfDO.setProcStatus(ProcessStsCdEnum.PR01.getCode());
        }
        //更新入库
        try {
            return payTransDtlNonfmapper.updatePayTransDtlNonf(payTransDtlNonfDO);
        } catch (Exception e) {
            logger.error("自由报文入库异常:{}-{}", e.getMessage(),e);
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

}
