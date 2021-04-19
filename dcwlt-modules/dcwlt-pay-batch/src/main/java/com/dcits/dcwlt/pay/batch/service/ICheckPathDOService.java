package com.dcits.dcwlt.pay.batch.service;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.web.domain.AjaxResult;
import com.dcits.dcwlt.pay.api.domain.ecny.dspt.DsptChnlReqDTO;
import com.dcits.dcwlt.pay.api.model.CheckPathDO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * 对账汇总Service接口
 * 
 * @author 
 * @date 2021-03-09
 */
public interface ICheckPathDOService 
{
    /**
     * 查询对账汇总
     * 
     * @param paydate 对账汇总ID
     * @return 对账汇总
     */
    public CheckPathDO selectCheckPathDOById(String paydate);

    /**
     * 查询对账汇总列表
     * 
     * @param checkPathDO 对账汇总
     * @return 对账汇总集合
     */
    public List<CheckPathDO> selectCheckPathDOList(CheckPathDO checkPathDO);

    /**
     * 执行801手动差错
     * @param checkPathDO
     * @return AjaxResult
     */
    boolean execute801(JSONObject dsptChnlReqDTO);

    boolean reconciliation(CheckPathDO checkPathDO);
}
