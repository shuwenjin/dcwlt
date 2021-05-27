package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.common.pay.constant.HttpStatus;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.cashbox.EcnyCashboxRspDTO;
import com.dcits.dcwlt.pay.api.model.PayCommCashboxProcess;
import com.dcits.dcwlt.pay.batch.fegin.PayBatchCheckStatisticsFegin;
import com.dcits.dcwlt.pay.batch.mapper.PayCommCashboxProcessMapper;
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 钱柜出库入库异常处理Service业务层处理
 *
 * @author dcwlt
 * @date 2021-05-11
 */
@Service
public class PayCommCashboxProcessServiceImpl implements IPayCommCashboxProcessService {

    @Autowired
    private PayBatchCheckStatisticsFegin payBatchCheckStatisticsFegin;

    @Autowired
    private PayCommCashboxProcessMapper payCommCashboxProcessMapper;


    private static final String RES_STS_FAILED = "failed";

    /**
     * 查询钱柜出库入库异常处理
     *
     * @param id 钱柜出库入库异常处理ID
     * @return 钱柜出库入库异常处理
     */
    @Override
    public PayCommCashboxProcess selectPayCommCashboxProcessById(Long id) {
        return payCommCashboxProcessMapper.selectPayCommCashboxProcessById(id);
    }

    /**
     * 出入库手工调用，出入库重发
     *
     * @param map
     * @return
     */
    @Override
    public AjaxResult sendCashbox(Map<String, String> map) {
        System.out.println("出入库调用成功！");
        //return AjaxResult.success("成功");
        // todo 后续添加
        //ECNYRspDTO<EcnyCashboxRspDTO> ecn = payBatchCheckStatisticsFegin.sendCashbox(map);
        //System.out.println("获取接口报文："+ecn.toString());
        return AjaxResult.success("成功");
//        EcnyCashboxRspDTO body = ecn.getBody();
//        if (body == null) {
//            return AjaxResult.error(HttpStatus.OUT_TIME, "接口调用超时");
//        } else if (RES_STS_FAILED.equals(body.getPrcSts())) {
//            return AjaxResult.error(HttpStatus.DATA_ERROR, body.getPrcDesc());
//        } else {
//            return AjaxResult.success("成功");
//        }
    }

    /**
     * 冲账 todo 后续根据核心接口修改做冲正
     *
     * @param pay
     * @return
     */
    private AjaxResult buildBankCore3041000204Req(PayCommCashboxProcess pay) {
//        BankCore3041000204Req bankCore3041000204Req = new BankCore3041000204Req();
//        bankCore3041000204Req.setPrevDt(pay.getCredttms());//请求核心日期
//        bankCore3041000204Req.setOriPrevFlowNo(pay.getMsgid());
//        bankCore3041000204Req.setTransAmt(AmountUtil.fenToYuan(pay.getAmtvalue()));
//        BankCore3041000204Rsp bankCore3041000204Rsp = payBatchCheckStatisticsFegin.bankRev(bankCore3041000204Req);
//        if (bankCore3041000204Rsp == null) {
//            return AjaxResult.error(HttpStatus.OUT_TIME, "核心接口调用超时");
//        } else if (!AppConstant.TRX_RET_CODE_SUCCESS.equals(bankCore3041000204Rsp.getRetCd())) {
//            return AjaxResult.error(HttpStatus.DATA_ERROR, bankCore3041000204Rsp.getRetMsg());
//        } else {
//            pay.setCorests(AppConstant.CORESTATUS_REVERSED);
//            this.updatePayCommCashboxProcess(pay);
//            return AjaxResult.success("成功");
//        }

        pay.setCorests(AppConstant.CORESTATUS_REVERSED);
        this.updatePayCommCashboxProcess(pay);
        return AjaxResult.success("成功");
    }

    /**
     * 回查核心状态 todo 后续根据核心接口修改做核心回查
     *
     * @param pay
     * @return
     */
    private AjaxResult buildBankCore30430001Req(PayCommCashboxProcess pay) {
//        BankCore30430001Req bankCore30430001Req = new BankCore30430001Req();
//        bankCore30430001Req.setPrevDt(pay.getCredttms());  //前置日期
//        bankCore30430001Req.setPrevFlowNo(pay.getMsgid()); //前置流水号
//        bankCore30430001Req.setPrevSysNo(Constant.CONSUMER_SYS_ID);
//        bankCore30430001Req.setUsrId("90087");          //柜员号
//        BankCore30430001Rsp bankCore30430001Rsp = payBatchCheckStatisticsFegin.queryCoreStatus(bankCore30430001Req);
//        if (bankCore30430001Req == null) {
//            return AjaxResult.error(HttpStatus.OUT_TIME, "核心接口调用超时");
//        } else if (!AppConstant.TRX_RET_CODE_SUCCESS.equals(bankCore30430001Rsp.getRetCd())) {
//            return AjaxResult.error(HttpStatus.DATA_ERROR, bankCore30430001Rsp.getRetMsg());
//        } else {
//            pay.setCorests(bankCore30430001Rsp.getTxnSt());
//            this.updatePayCommCashboxProcess(pay);
//            return AjaxResult.success("成功");
//        }

        this.updatePayCommCashboxProcess(pay);
        return AjaxResult.success("成功");
    }

    /**
     * @param id 业务编号
     * @return
     */
    @Override
    public AjaxResult changeApprovalStuts(int appStuts,Long id) {
        PayCommCashboxProcess pay = this.selectPayCommCashboxProcessById(id);
        AjaxResult ajaxResult = AjaxResult.success("成功");
        if (AppConstant.ONE == appStuts) {
            Map<String, String> map = new HashMap<>();
            map.put("OprTp", pay.getOprtp());
            map.put("AmtCcy", pay.getAmtccy());
            map.put("AmtValue", pay.getAmtvalue());
            map.put("CshBoxInstnId", pay.getCshboxinstnid());
            map.put("MsgId", pay.getMsgid());
            ajaxResult = this.sendCashbox(map);
        } else if (AppConstant.TOW == appStuts) {
            ajaxResult = buildBankCore3041000204Req(pay);
        } else if (AppConstant.THR == appStuts) {
            ajaxResult = buildBankCore30430001Req(pay);
        }
        //pay.setApprovalStuts(0);
        int i = this.updatePayCommCashboxProcess(pay);
        if (i == 0) {
            ajaxResult = AjaxResult.error("数据修改失败！");
        }
        return ajaxResult;
    }


    /**
     * 查询钱柜出库入库异常处理列表
     *
     * @param payCommCashboxProcess 钱柜出库入库异常处理
     * @return 钱柜出库入库异常处理
     */
    @Override
    public List<PayCommCashboxProcess> selectPayCommCashboxProcessList(PayCommCashboxProcess payCommCashboxProcess) {
        return payCommCashboxProcessMapper.selectPayCommCashboxProcessList(payCommCashboxProcess);
    }

    /**
     * 新增钱柜出库入库异常处理
     *
     * @param payCommCashboxProcess 钱柜出库入库异常处理
     * @return 结果
     */
    @Override
    public int insertPayCommCashboxProcess(PayCommCashboxProcess payCommCashboxProcess) {
        return payCommCashboxProcessMapper.insertPayCommCashboxProcess(payCommCashboxProcess);
    }

    /**
     * 修改钱柜出库入库异常处理
     *
     * @param payCommCashboxProcess 钱柜出库入库异常处理
     * @return 结果
     */
    @Override
    public int updatePayCommCashboxProcess(PayCommCashboxProcess payCommCashboxProcess) {
        return payCommCashboxProcessMapper.updatePayCommCashboxProcess(payCommCashboxProcess);
    }

    /**
     * 批量删除钱柜出库入库异常处理
     *
     * @param ids 需要删除的钱柜出库入库异常处理ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxProcessByIds(Long[] ids) {
        return payCommCashboxProcessMapper.deletePayCommCashboxProcessByIds(ids);
    }

    /**
     * 删除钱柜出库入库异常处理信息
     *
     * @param id 钱柜出库入库异常处理ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxProcessById(Long id) {
        return payCommCashboxProcessMapper.deletePayCommCashboxProcessById(id);
    }
}
