package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.pay.api.model.SignInfoDO;
import org.springframework.stereotype.Service;

/**
 * @author zhangwang
 * @date  2020/12/31
 * @version 1.0.0
 * <p>协议表DAO</p>
 */

public interface ISignInfoRepository {

	int insert(SignInfoDO signInfoDO);

	SignInfoDO queryBySignNo(String signNo);
}
