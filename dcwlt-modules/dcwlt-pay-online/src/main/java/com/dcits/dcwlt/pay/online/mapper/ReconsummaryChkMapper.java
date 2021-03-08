package com.dcits.dcwlt.pay.online.mapper;


import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventConfigDO;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventInfoDO;
import com.dcits.dcwlt.pay.api.model.DtlFileInfDO;
import com.dcits.dcwlt.pay.api.model.MonitorDO;
import com.dcits.dcwlt.pay.api.model.ReconSummaryChkDO;
import com.dcits.dcwlt.pay.api.model.SummaryInfoDO;

public interface ReconsummaryChkMapper {

    EventConfigDO queryEventConfig(String eventCode);

    int insertEventInfo(EventInfoDO eventInfoDO);

    int updateEventInfo(EventInfoDO eventInfoDO);

    int updateEventDealNum(String exceptEventCode, String exceptEventSeqNo, String maxDealNum);
    /*
     * 新增监控数据
     * */
    int insertMonitorData(MonitorDO monitorDO);

    /*
     * 更新监控数据
     * */
    int updateMonitorData(MonitorDO monitorDO);

    int replaceDtlFileInfDO(DtlFileInfDO dtlFileInfDO) throws Exception;

    int update(String replaceSql, SummaryInfoDO summaryInfoDO);

    int insertReconSummaryChkDO(String replaceSql, ReconSummaryChkDO reconSummaryChkDO);

    int insertDtlFileInfDO(String replaceSql, DtlFileInfDO dtlFileInfDO);
}

