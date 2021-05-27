package com.dcits.dcwlt.pay.batch.service;

import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.pay.api.model.PayCommCashboxProcess;

import java.util.List;
import java.util.Map;

/**
 * 钱柜出库入库异常处理Service接口
 *
 * @author dcwlt
 * @date 2021-05-11
 */
public interface IPayCommCashboxProcessService {
    /**
     * @param 1、 出入库重发，2、核心状态同步 3、手工冲账
     * @param id 业务编号
     * @return
     */
    public AjaxResult changeApprovalStuts(int appStuts,Long id);


    /**
     * 查询钱柜出库入库异常处理
     *
     * @param id 钱柜出库入库异常处理ID
     * @return 钱柜出库入库异常处理
     */
    public PayCommCashboxProcess selectPayCommCashboxProcessById(Long id);

    /**
     * map.put("OprTp","");//入库类型
     * map.put("AmtCcy", "CNY");//币种
     * map.put("AmtValue","");//入库金额
     * map.put("CshBoxInstnId","");//钱柜所属运营机构
     *
     * @return
     */
    public AjaxResult sendCashbox(Map<String, String> map);

    /**
     * 查询钱柜出库入库异常处理列表
     *
     * @param payCommCashboxProcess 钱柜出库入库异常处理
     * @return 钱柜出库入库异常处理集合
     */
    public List<PayCommCashboxProcess> selectPayCommCashboxProcessList(PayCommCashboxProcess payCommCashboxProcess);

    /**
     * 新增钱柜出库入库异常处理
     *
     * @param payCommCashboxProcess 钱柜出库入库异常处理
     * @return 结果
     */
    public int insertPayCommCashboxProcess(PayCommCashboxProcess payCommCashboxProcess);

    /**
     * 修改钱柜出库入库异常处理
     *
     * @param payCommCashboxProcess 钱柜出库入库异常处理
     * @return 结果
     */
    public int updatePayCommCashboxProcess(PayCommCashboxProcess payCommCashboxProcess);

    /**
     * 批量删除钱柜出库入库异常处理
     *
     * @param ids 需要删除的钱柜出库入库异常处理ID
     * @return 结果
     */
    public int deletePayCommCashboxProcessByIds(Long[] ids);

    /**
     * 删除钱柜出库入库异常处理信息
     *
     * @param id 钱柜出库入库异常处理ID
     * @return 结果
     */
    public int deletePayCommCashboxProcessById(Long id);
}
