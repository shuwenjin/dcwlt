package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.domain.dcep.config.PayCommonParam;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 参数配置Mapper接口
 *
 * @author dcwlt
 * @date 2021-04-14
 */
public interface PayCommonParamMapper {
    /**
     * 查询参数配置列表
     *
     * @return 参数配置集合
     */
    @Select("select id, param_type as paramType, param_key as paramKey, param_value as paramValue, param_desc as paramDesc, param_status as paramStatus from pay_comm_param")
    public List<PayCommonParam> selectPayCommonParamList();
}
