package com.dcits.dcwlt.pay.online.mapper;


import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;

import java.util.List;

public interface LoginMapper {
	int insertPayTransDtlNonf(String insertUrl, PayTransDtlNonfDO payTransDtlNonfDO);

	int updatePayTransDtlNonf(String updateUrl, PayTransDtlNonfDO payTransDtlNonfDO);

	List<PayTransDtlNonfDO> queryPayTransDtlNonf(String queryUrl, PayTransDtlNonfDO payTransDtlNonfDO);

}



