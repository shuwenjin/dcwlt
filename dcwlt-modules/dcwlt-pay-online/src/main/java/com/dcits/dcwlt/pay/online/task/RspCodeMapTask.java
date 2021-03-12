package com.dcits.dcwlt.pay.online.task;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.mapper.RspCodeMapMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableScheduling
public class RspCodeMapTask {
    private static final Logger logger = LoggerFactory.getLogger(RspCodeMapTask.class);
    @Autowired
    private RspCodeMapMapper rspCodeMapRepository;

    private static Map<String, RspCodeMapDO> dictDataMap = new HashMap<>();

    @PostConstruct
    @Scheduled(cron = "0 0/1 * * * ?")
    public void cacheData() {
        List<RspCodeMapDO> rspCodeMapDOList = rspCodeMapRepository.getAllRspCodeMap();
        logger.info("RspCodeMapDO init size:{}",rspCodeMapDOList.size());
        if (rspCodeMapDOList == null) {
            return;
        }
        for (RspCodeMapDO codeMap : rspCodeMapDOList) {
            //key组成规则：业务类型_源的编号_目的的编号_应答码分类_源应答码
            String key = String.format("%s_%s_%s_%s_%s_%s", codeMap.getPayPath(), codeMap.getSrcId(), codeMap.getDestId(), codeMap.getTxnType(), codeMap.getSrcRspCode(), codeMap.getSrcRspCode2());
            dictDataMap.put(key, codeMap);
        }
    }

    /**
     * @param srcId      源系统标识
     * @param destId     目标系统标识
     * @param trxType    应答码分类
     * @param srcRspCode 源应答码大类
     * @return
     */
    public static RspCodeMapDO getErrorCodeMap(String srcId, String destId, String trxType, String srcRspCode) {
        return getErrorCodeMap(AppConstant.ECNY_SYS_ID, srcId, destId, trxType, srcRspCode, "*");
    }

    /**
     * @param payPath     通道代码
     * @param srcId       源系统标识
     * @param destId      目标系统标识
     * @param trxType     应答码分类
     * @param srcRspCode  源应答码大类
     * @param srcRspCode2 源应答码小类
     * @return
     */
    public static RspCodeMapDO getErrorCodeMap(String payPath, String srcId, String destId, String trxType, String srcRspCode, String srcRspCode2) {
        //判断入参是否为空或null值
        if (StringUtils.isAnyBlank(payPath, srcId, destId, trxType, srcRspCode, srcRspCode2)) {
            logger.error("请求参数存在空或nulL值");
            throw new EcnyTransException(EcnyTransError.PARAM_NOT_INIT_ERROR);
        }
        if (null == dictDataMap) {
            logger.error("ErrorCodeCacheMap缓存容器未初始化");
            throw new EcnyTransException(EcnyTransError.PARAM_NOT_INIT_ERROR);
        }

        RspCodeMapDO errorMapDO = null;
        //判断错误码缓存是否为空
        if (!dictDataMap.isEmpty()) {
            errorMapDO = dictDataMap.get(String.format("%s_%s_%s_%s_%s_%s", payPath, srcId, destId, trxType, srcRspCode, srcRspCode2));
        }
        return errorMapDO;
    }

}
