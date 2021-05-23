package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayCertInfoDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayCertInfoMapper {


    int update(PayCertInfoDO payCertInfoDO);

    int insert(PayCertInfoDO payCertInfoDO);

    List<PayCertInfoDO> query(PayCertInfoDO payCertInfoDO);

}




