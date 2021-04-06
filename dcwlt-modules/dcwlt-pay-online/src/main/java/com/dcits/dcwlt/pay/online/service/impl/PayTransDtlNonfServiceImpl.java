package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import com.dcits.dcwlt.pay.online.mapper.PayTransDtlNonfMapper;
import com.dcits.dcwlt.pay.online.service.IPayTransDtlNonfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 非金融报文登记簿数据库处理组件
 *
 * @author
 * @date 2020/12/29
 */
@Component
public class PayTransDtlNonfServiceImpl implements IPayTransDtlNonfService {

    private static final String INSERT_URL = "payTransDtlNonf.insertPayTransDtlNonf";
    private static final String UPDATE_URL = "payTransDtlNonf.updatePayTransDtlNonf";
    private static final String DELETE_URL = "payTransDtlNonf.deletePayTransDtlNonf";
    private static final String QUERY_URL = "payTransDtlNonf.queryPayTransDtlNonf";


    @Autowired
    private PayTransDtlNonfMapper payTransDtlNonfMapper;
    /**
     * 新增报文登记信息
     * @param ecnyPayTransDtlNonfEntity
     * @return
     */
    @Override
    public int addPayTransDtlNonf(PayTransDtlNonfDO ecnyPayTransDtlNonfEntity) {
        return insertPayTransDtlNonf(ecnyPayTransDtlNonfEntity);
    }

    /**
     * 判断信息登记报文是否存在
     * @param msgId
     * @return
     */
    @Override 
    public boolean payTransDtlNonfExist(String msgId) {
        return queryPayTransDtlNonfByMsgId(msgId) != null;
    }

    /**
     * 查询信息报文
     * @param msgId
     * @return
     */
    @Override
    public List<PayTransDtlNonfDO> queryPayTransDtlNonf(String msgId) {
        PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
        payTransDtlNonfDO.setMsgId(msgId);

        return queryPayTransDtlNonf(payTransDtlNonfDO);
    }

    /**
     * 根据信息id查询报文登记信息
     * @param msgId
     * @return
     */
    @Override
    public PayTransDtlNonfDO queryPayTransDtlNonfByMsgId(String msgId) {
        if(StringUtil.isBlank(msgId)){
            return null;
        }
        PayTransDtlNonfDO payTransDtlNonfDO = new PayTransDtlNonfDO();
        payTransDtlNonfDO.setMsgId(msgId);

        List<PayTransDtlNonfDO> payTransDtlNonfDOS = queryPayTransDtlNonf(payTransDtlNonfDO);
        if(payTransDtlNonfDOS != null && !payTransDtlNonfDOS.isEmpty()){
            return payTransDtlNonfDOS.get(0);
        }
        return null;
    }

    /**
     * 更新报文登记信息
     * @param payTransDtlNonf
     * @return
     */
    @Override
    public int updatePayTransDtlNonf(PayTransDtlNonfDO payTransDtlNonf)  {
        return updatePayTransDtlNonfInfo(payTransDtlNonf);
    }


    /**
     * 新增信息报文登记，入库操作
     * @param payTransDtlNonfDO
     * @return
     */
    private int insertPayTransDtlNonf(PayTransDtlNonfDO payTransDtlNonfDO) {
        return payTransDtlNonfMapper.insertPayTransDtlNonf( payTransDtlNonfDO);
    }

    /**
     * 更新信息报文登记，对库操作
     * @param payTransDtlNonfDO
     * @return
     */
    private int updatePayTransDtlNonfInfo(PayTransDtlNonfDO payTransDtlNonfDO){
        return payTransDtlNonfMapper.updatePayTransDtlNonf( payTransDtlNonfDO);
    }

    /**
     * 查询信息报文登记
     * @param payTransDtlNonfDO
     * @return
     */
    private List<PayTransDtlNonfDO> queryPayTransDtlNonf(PayTransDtlNonfDO payTransDtlNonfDO){
        return payTransDtlNonfMapper.queryPayTransDtlNonf(payTransDtlNonfDO);
    }
}
