package com.dcits.dcwlt.pay.batch.service.impl;

import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.cashboxWarning.EcnyCashboxWarningRspDTO;
import com.dcits.dcwlt.pay.api.model.PayCommCashboxParty;
import com.dcits.dcwlt.pay.batch.fegin.PayBatchCheckStatisticsFegin;
import com.dcits.dcwlt.pay.batch.mapper.PayCommCashboxPartyMapper;
import com.dcits.dcwlt.pay.batch.service.IPayCommCashboxPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运营机构钱柜参数Service业务层处理
 * 
 * @author dcwlt
 * @date 2021-05-12
 */
@Service
public class PayCommCashboxPartyServiceImpl implements IPayCommCashboxPartyService
{
    @Autowired
    private PayCommCashboxPartyMapper payCommCashboxPartyMapper;

    @Autowired
    private PayBatchCheckStatisticsFegin payBatchCheckStatisticsFegin;
    private static final String RES_STS_FAILED = "failed";
    /**
     * 查询运营机构钱柜参数
     * 
     * @param id 运营机构钱柜参数ID
     * @return 运营机构钱柜参数
     */
    @Override
    public PayCommCashboxParty selectPayCommCashboxPartyById(Long id)
    {
        return payCommCashboxPartyMapper.selectPayCommCashboxPartyById(id);
    }

    /**
     * 查询运营机构钱柜参数列表
     * 
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 运营机构钱柜参数
     */
    @Override
    public List<PayCommCashboxParty> selectPayCommCashboxPartyList(PayCommCashboxParty payCommCashboxParty)
    {
        return payCommCashboxPartyMapper.selectPayCommCashboxPartyList(payCommCashboxParty);
    }


    /**
     * 新增运营机构钱柜参数
     *
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 结果
     */
    @Override
    public int insertPayCommCashboxParty(PayCommCashboxParty payCommCashboxParty)
    {
        int i = payCommCashboxPartyMapper.insertPayCommCashboxParty(payCommCashboxParty);
        return 1;
    }

    /**
     * 修改运营机构钱柜参数
     * 
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 结果
     */
    @Override
    public int updatePayCommCashboxParty(PayCommCashboxParty payCommCashboxParty)
    {
        return payCommCashboxPartyMapper.updatePayCommCashboxParty(payCommCashboxParty);
    }
    /**
     * 修改运营机构钱柜预警余额
     *
     * @param payCommCashboxParty 运营机构钱柜参数
     * @return 结果
     */
    @Override
    public int updatePayCommCashboxPartyWarn(PayCommCashboxParty payCommCashboxParty)
    {
        payCommCashboxParty.setUpdateTime(new Date());
                Map<String,String> map=new HashMap<>();
        map.put("WrngValCcy","CNY");//预警值钱币
        map.put("WrngValValue",payCommCashboxParty.getEarlywarningamount());//预警值
        map.put("CshBoxInstnId",payCommCashboxParty.getCashboxid());//钱柜所属运营机构
        ECNYRspDTO<EcnyCashboxWarningRspDTO> respons = payBatchCheckStatisticsFegin.sendCashboxWarning(map);
        EcnyCashboxWarningRspDTO body = respons.getBody();
        //申请成功，更新数据库
        if (RES_STS_FAILED.equals(body.getPrcSts())){
            return payCommCashboxPartyMapper.updatePayCommCashboxParty(payCommCashboxParty);
        }else{
            return 0;
        }
    }
    /**
     * 批量删除运营机构钱柜参数
     * 
     * @param ids 需要删除的运营机构钱柜参数ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxPartyByIds(Long[] ids)
    {
        return payCommCashboxPartyMapper.deletePayCommCashboxPartyByIds(ids);
    }

    /**
     * 删除运营机构钱柜参数信息
     * 
     * @param id 运营机构钱柜参数ID
     * @return 结果
     */
    @Override
    public int deletePayCommCashboxPartyById(Long id)
    {
        return payCommCashboxPartyMapper.deletePayCommCashboxPartyById(id);
    }
}
