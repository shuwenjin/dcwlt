package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.PayTransDtlNonfDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 非金融报文登记簿服务
 *
 * @author majun
 * @date 2020/12/29
 */
@Service
public interface IPayTransDtlNonfService {

    int addPayTransDtlNonf(PayTransDtlNonfDO payTransDtlNonfDO);

    boolean payTransDtlNonfExist(String msgId);

    List<PayTransDtlNonfDO> queryPayTransDtlNonf(String msgId);

    PayTransDtlNonfDO queryPayTransDtlNonfByMsgId(String msgId);

    int updatePayTransDtlNonf(PayTransDtlNonfDO payTransDtlNonfDO);
}
