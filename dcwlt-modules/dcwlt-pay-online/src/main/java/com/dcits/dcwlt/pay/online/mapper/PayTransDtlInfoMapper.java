package com.dcits.dcwlt.pay.online.mapper;

import com.dcits.dcwlt.pay.api.model.PayTransDtlInfoDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PayTransDtlInfoMapper {


    PayTransDtlInfoDO queryByPayInfo(Map<String, String> param);

    PayTransDtlInfoDO queryByBusiSysSerno(String busiSysSerno);

    List<PayTransDtlInfoDO> queryByOrigSerno(String origPayPathSerno);

    PayTransDtlInfoDO queryByMsgId(String msgId);

    int updateDirect(PayTransDtlInfoDO payTransDtlInfoDO);

    int update(Map<String, String> param);

    int insert(PayTransDtlInfoDO payTransDtlInfoDO);

    PayTransDtlInfoDO queryByPayInfo(@Param("payDate") String payDate, @Param("paySerno")String paySerno);

}




