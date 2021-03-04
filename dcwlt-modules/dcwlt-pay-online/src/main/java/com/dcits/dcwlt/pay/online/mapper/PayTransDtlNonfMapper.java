package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自由格式报文
 */
@Mapper
public interface PayTransDtlNonfMapper {

    int insertPayTransDtlNonf(PayTransDtlNonfDO payTransDtlNonfDO);

    int updatePayTransDtlNonf(PayTransDtlNonfDO payTransDtlNonfDO);
}
