package com.dcits.dcwlt.pay.batch.mapper;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayPayTransdtlMapper {

    List<PayTransDtlInfoDO> selectPayPayTransdtlList(PayTransDtlInfoDO payPayTransdtl);
}
