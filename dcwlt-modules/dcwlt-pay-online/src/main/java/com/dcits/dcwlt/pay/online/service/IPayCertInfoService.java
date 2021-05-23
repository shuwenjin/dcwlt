package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.PayCertInfoDO;

import java.util.List;

/**
 * 密钥证书服务
 *
 * @author
 * @date 2020/12/30
 */
public interface IPayCertInfoService {

    int addCert(PayCertInfoDO payCertInfoDO);

    int addOrUpdateCert(PayCertInfoDO payCertInfoDO);

    int updateCert(PayCertInfoDO payCertInfoDO);

    List<PayCertInfoDO> queryCert(PayCertInfoDO payCertInfoDO);

}
