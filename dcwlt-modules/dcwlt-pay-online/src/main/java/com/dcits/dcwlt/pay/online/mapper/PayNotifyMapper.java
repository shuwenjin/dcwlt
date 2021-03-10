package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayNotifyDO;
import org.mapstruct.Mapper;


@Mapper
public interface PayNotifyMapper {
	int insert(String insertPayNotifySql, PayNotifyDO payNotifyDO);

	PayNotifyDO queryByMsgId(String queryPayNotifyByMsgidSql, String msgId);
}
