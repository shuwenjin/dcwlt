package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.PayCertInfoDO;
import com.dcits.dcwlt.pay.online.mapper.PayCertInfoMapper;
import com.dcits.dcwlt.pay.online.service.IPayCertInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 证书信息数据库处理组件
 *
 * @author
 * @date 2021/04/28
 */
@Service
public class PayCertInfoServiceImpl implements IPayCertInfoService {
    private static final Logger logger = LoggerFactory.getLogger(PayCertInfoServiceImpl.class);

    @Autowired
    private PayCertInfoMapper payCertInfoMapper;


    /**
     * 添加证书信息
     *
     * @param payCertInfoDO
     * @return
     */
    @Override
    public int addCert(PayCertInfoDO payCertInfoDO) {
        payCertInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        payCertInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        return insertPayCertInfo(payCertInfoDO);
    }

    /**
     * 添加或更新
     *
     * @param payCertInfoDO
     * @return
     */
    @Override
    public int addOrUpdateCert(PayCertInfoDO payCertInfoDO) {
        PayCertInfoDO oldPayCert = queryPayCertInfoByCerttype(payCertInfoDO.getCerttype(), payCertInfoDO.getSn(), payCertInfoDO.getDn());

        payCertInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        payCertInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        if (oldPayCert != null) {
            return updatePayCertInfo(payCertInfoDO);
        } else {
            return insertPayCertInfo(payCertInfoDO);
        }
    }

    /**
     * 更新证书信息
     *
     * @param payCertInfoDO
     * @return
     */
    @Override
    public int updateCert(PayCertInfoDO payCertInfoDO) {
        payCertInfoDO.setLastUpTime(DateUtil.getDefaultTime());
        payCertInfoDO.setLastUpDate(DateUtil.getDefaultDate());
        return updatePayCertInfo(payCertInfoDO);
    }

    /**
     * 查询证书信息
     *
     * @param payCertInfoDO
     * @return
     */
    @Override
    public List<PayCertInfoDO> queryCert(PayCertInfoDO payCertInfoDO) {
        return queryPayCertInfo(payCertInfoDO);
    }
    
    /**
     * 插入证书信息，对库操作
     *
     * @param payCertInfoDO
     * @return
     */
    private int insertPayCertInfo(PayCertInfoDO payCertInfoDO) {
        return payCertInfoMapper.insert(payCertInfoDO);
    }

    /**
     * 更新证书信息，对库操作
     *
     * @param payCertInfoDO
     * @return
     */
    private int updatePayCertInfo(PayCertInfoDO payCertInfoDO) {
        return payCertInfoMapper.update(payCertInfoDO);
    }

    /**
     * 查询证书信息，对库操作
     *
     * @param payCertInfoDO
     * @return
     */
    private List<PayCertInfoDO> queryPayCertInfo(PayCertInfoDO payCertInfoDO) {
        return payCertInfoMapper.query(payCertInfoDO);
    }

    /**
     * 根据证书类型查询
     *
     * @param certtype
     * @return
     */
    public PayCertInfoDO queryPayCertInfoByCerttype(String certtype, String sn, String dn) {
        PayCertInfoDO PayCertInfoDO = new PayCertInfoDO();
        PayCertInfoDO.setCerttype(certtype);
        PayCertInfoDO.setSn(sn);
        PayCertInfoDO.setDn(dn);
        List<com.dcits.dcwlt.pay.api.model.PayCertInfoDO> PayCertInfoDOS = queryPayCertInfo(PayCertInfoDO);

        if (PayCertInfoDOS == null || PayCertInfoDOS.isEmpty()) {
            return null;
        }
        return PayCertInfoDOS.get(0);
    }
}
