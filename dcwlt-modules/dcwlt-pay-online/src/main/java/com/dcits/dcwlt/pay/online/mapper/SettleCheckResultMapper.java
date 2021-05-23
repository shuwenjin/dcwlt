package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.CheckResultDO;
import org.apache.ibatis.annotations.Mapper;


/**
 * @description: some desc
 * @author: zhangp
 * @date: 2021/3/11 19:06
 */

@Mapper
public interface SettleCheckResultMapper {

    /**
     * 对账状态更新
     * @param checkResultDO
     * @return
     */
    public int updateCheckResult(CheckResultDO checkResultDO);
}
