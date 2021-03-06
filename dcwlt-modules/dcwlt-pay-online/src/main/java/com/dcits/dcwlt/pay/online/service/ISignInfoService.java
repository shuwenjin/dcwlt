package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.pay.api.model.SignInfoDO;

import java.util.List;

/**
 * @author
 * @date  2020/12/31
 * @version 1.0.0
 * <p>协议表DAO</p>
 */
public interface ISignInfoService {

	SignInfoDO queryBySignNo(String signNo);

	int insert(SignInfoDO signInfoDO);

	int updateBySignNo(SignInfoDO signNo);

	SignInfoDO selectByWltIdAndAcctId(SignInfoDO signInfoDO);

	int updateByWltIdAndAcctId(SignInfoDO signInfoDO);

	List<SignInfoDO> selectPartSignInfo(SignInfoDO signInfoDO);
}
