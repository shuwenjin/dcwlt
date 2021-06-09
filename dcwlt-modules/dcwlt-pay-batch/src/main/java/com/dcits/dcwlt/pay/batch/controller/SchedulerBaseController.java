package com.dcits.dcwlt.pay.batch.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.ChangeCdEnum;
import com.dcits.dcwlt.common.pay.enums.TaskGroupEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.util.DateCompareUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.common.NbInf;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.batch.mapper.PartyInfoMapper;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupExecService;
import com.dcits.dcwlt.pay.batch.service.impl.IPartyServiceImpl;
import com.dcits.dcwlt.pay.batch.task.TaskExecRunnable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务同一入口 serviceName 必须传入服务名称
 **/
@RestController
public class SchedulerBaseController {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerBaseController.class);
    //处理成功的返回结果
    private static final String SUCC = "0";
    //抽数定时任务
    private static final String SCHEDULE_CHK_DATA = "ImportDataService";
    //检查异常任务
    private static final String SCHEDULE_CHK_FAIL_TASK = "CheckFailureService";
    //机构定时生效任务
    private static final String SCHEDULE_PARTY_TO_EFFECTIVE_TASK = "PartyToEffectiveService";
    //业务权限变更 定时任务
    private static final String SCHEDULE_RIGHT_CHANGE_TASK = "RightChangeService";
    //机构定时生效任务请求地址
    private static final String PART_TO_EFFECTIVE_URL = "/ecny/party/effectiveParty.fun";
    //业务权限变更请求地址
    private static final String RIGHT_CHANGE_URL = "/ecny/party/rightChanged.fun";

    private static final String TMP_BRO_NO = "000800";
    private static final String TMP_SYS_ID = "UPP";
    @Autowired
    private IPartyServiceImpl partyInfoTmpRepository;
//    @Autowired
//    private PartyInfoMapper partyInfoRepository;
    @Autowired
    private ISettleTaskGroupExecService taskGroupRepository;

    @Autowired
    private IGenerateCodeService codeService;

    @Value("${ency.batch.task.execute.threads}")
    private String threadNum;

    @RequestMapping("/schedulerController")
    public String schedulerController(HttpServletRequest request) {
        logger.info("ecny scheduler controller start.");
        // 请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        // 转Map<String,String>
        Map<String, String> paramMap = new HashMap<>();
        parameterMap.forEach((key, value) -> paramMap.put(key, value[0]));
        logger.info("请求参数：{}", paramMap.toString());
        String result = SUCC;
        String settleDate = paramMap.get("settleDate");
        if (StringUtils.isEmpty(settleDate)) {
            settleDate = DateUtil.getDefaultDate();
        }
        // 接口名称
        String serviceName = paramMap.get("serviceName");
        switch (serviceName) {
            case SCHEDULE_CHK_DATA:
                startCheckDataTask(paramMap, settleDate);
                break;
            case SCHEDULE_CHK_FAIL_TASK:
                execTask(settleDate);
                break;
            case SCHEDULE_PARTY_TO_EFFECTIVE_TASK:
                result = partyToEffective();
                break;
            case SCHEDULE_RIGHT_CHANGE_TASK:
                result = rightChange();
                break;
            default:
                logger.info("invalid service name, cannot found execute task.");
                break;
        }
        logger.info("ecny scheduler controller end.");
        return result;
    }

    /**
     * 执行抽数任务
     */
    private void startCheckDataTask(Map<String, String> paramMap, String settleDate) {
        String taskGroup = TaskGroupEnum.CHK_DATA.getCode();
        String batchId = paramMap.get("batchId");
        if (StringUtils.isEmpty(batchId)) {
            //取交易批次号，当前时间取上一个小时的批次号
            Date date = DateUtil.addHours(new Date(), -1);
            logger.info("batch date =" + DateUtil.getDefaultTimeFromDate(date));
            batchId = codeService.getBachNo(date);
        }
        Thread t = new Thread(new TaskExecRunnable(settleDate, batchId, taskGroup));
        t.start(); //异步执行
    }

    /**
     * 机构生效事件
     */
    private String partyToEffective() {
        String result = SUCC;
        logger.debug("开始进行临时机构生效定时任务");
        PartyToBeEffectiveDO partyToBeEffective = new PartyToBeEffectiveDO();
        //设置当前日期为生失效日期
        partyToBeEffective.setEffectDate(DateUtil.getSysDate());
        partyToBeEffective.setInEffectiveDate(DateUtil.getSysDate());

        //1.查询当前日期生失效的所有临时表数据
        List<PartyToBeEffectiveDO> partyToBeEffectiveDOS = partyInfoTmpRepository.queryPartyTmpByEffectiveDate(partyToBeEffective);

        //2.判断是否当前天生效，或者撤销
        if (partyToBeEffectiveDOS != null && !partyToBeEffectiveDOS.isEmpty()) {
            logger.info("开始移动机构临时表数据到生效表，移动总数：{}", partyToBeEffectiveDOS.size());
            AtomicInteger moveFailedCount = new AtomicInteger();
            partyToBeEffectiveDOS.stream().forEach(partyInfoTmpDO -> {

                //2.1判断当前天是否会发生状态变更
                try {
                    //执行移动操作
                    doMove(partyInfoTmpDO);
                } catch (Exception e) {
                    //异常处理
                    logger.warn("移动临时机构id:{} 机构名:{}, 机构信息{} 失败",
                            partyInfoTmpDO.getPartyID(),
                            partyInfoTmpDO.getPartyName(),
                            partyInfoTmpDO, e);
                    moveFailedCount.getAndIncrement();
                }
            });
            //失败日志记录
            if (moveFailedCount.get() != 0) {
                logger.warn("移动机构临时表数据到生效表完成，移动总数：{}， 移动失败条数：{}", partyToBeEffectiveDOS.size(), moveFailedCount.get());
                throw new EcnyTransException(EcnyTransError.ECNY_PARTY_TOBE_EFFECTIVE_ERROR);
            }
        }
//        logger.info("start process party to effective task.");
//        String dsptUrl = RtpUtil.getInstance().getProperty("ecny.online.server.url");
//        if (StringUtils.isEmpty(dsptUrl)) {
//            logger.error("Cannot found online server url.");
//            return "找不到联机服务器url地址";
//        }
//        try {
//            ECNYReqDTO ecnyReqDTO = new ECNYReqDTO();
//
//            //封装请求头
//            Head head = new Head();
//            head.setRetCode(Head.RET_CODE_SUCCESS);
//            head.setRetInfo(Head.RET_CODE_SUCCESS);
//            head.setResdFlag("N");
//            head.setTranDate(DateUtil.getDefaultDate());
//            head.setTranTime(DateUtil.getDefaultTime());
//            head.setSeqNo(generateCodeService.generateCoreReqSerno());
//            ecnyReqDTO.setHead(head);
//
//            //封装ECNY头
//            ECNYReqHead ecnyReqHead = new ECNYReqHead();
//            ecnyReqHead.setBusiChnl(BusiConst.ECNY_SYS_ID);
//            ecnyReqHead.setBrno(TMP_BRO_NO);
//            ecnyReqHead.setOrigChnl(TMP_SYS_ID);
//            ecnyReqDTO.setEcnyHead(ecnyReqHead);
//
//            //执行请求
//            String url = dsptUrl + PART_TO_EFFECTIVE_URL;
//            logger.info("发送待生效机构转生效机构请求任务到联机服务器");
//            String rsp = HttpClientUtil.doJsonPost(BusiConst.ECNY_SYS_ID, ApiConstant.INVOKE_PARTY_TO_EFFECTIVE,
//                    url, JSONObject.toJSON(ecnyReqDTO).toString(), "utf-8");
//            logger.info("待生效机构转生效机构任务执行完成，联机服务器响应记过{}", rsp);
//
//            //解析结果
//            JSONObject jsonObject = JSONObject.parseObject(rsp);
//            ECNYRspDTO ecnyRspDTO = JSONObject.toJavaObject(jsonObject, ECNYRspDTO.class);
//
//            //结果判断，head.retCode 000000为成功
//            if (!Head.RET_CODE_SUCCESS.equals(ecnyRspDTO.getHead().getRetCode())) {
//                logger.error("待生效机构移动到生效机构表任务执行出现错误");
//                result = ecnyRspDTO.getHead().getRetInfo();
//            }
//        } catch (Exception e) {
//            logger.error("请求网络错误{}", e.getMessage(), e);
//            result = "网络请求错误";
//        }
//        logger.info("end process part to effective task.");
        return result;
    }
    /**
     * 执行移动操作
     *
     * @param partyToBeEffectiveDO
     */
    private void doMove(PartyToBeEffectiveDO partyToBeEffectiveDO) {
        logger.info("开始移动临时机构{}", partyToBeEffectiveDO);

        //是否新插入判断， 根据生效表判断该机构是否已经存在
        boolean newInsert = false;
        //是否需要更新到生效表
        boolean needUpdate = false;
        PartyInfoDO partyInfoDO = partyInfoTmpRepository.queryParty(partyToBeEffectiveDO.getPartyID());

        //生效判断, 非CC02撤销类型，
        if (!ChangeCdEnum.CC02.getCode().equals(partyToBeEffectiveDO.getChangeType())) {
            logger.info("机构ID:{}为{}类型", partyToBeEffectiveDO.getPartyID(), partyToBeEffectiveDO.getChangeType());
            if (partyInfoDO == null) {
                //生效表中没有数据， 新增并且需要更新待生效数据到生效表中
                partyInfoDO = new PartyInfoDO();
                protertiesChange(partyToBeEffectiveDO, partyInfoDO);
                newInsert = true;
                logger.info("机构ID{}在生效表中为空，需要新增", partyToBeEffectiveDO.getPartyID());
            } else {
                logger.info("机构ID{}在生效表中不为空，可能变更", partyToBeEffectiveDO.getPartyID());
                protertiesChange(partyToBeEffectiveDO, partyInfoDO);
            }
            //判断变更期数,生效表变更期数效待生效表
            if (partyInfoDO.getChangeNumber() < partyToBeEffectiveDO.getChangeNumber()
                    || partyInfoDO.getChangeNumber() >= NbInf.MAX_CHNG_NB) {
                //生效日期小于等当前日期，更新为生效
                if (partyToBeEffectiveDO.getEffectDate() != null && DateCompareUtil.nowGTEDateTime(partyToBeEffectiveDO.getEffectDate())) {
                    partyInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_EFFECTIVE);
                    needUpdate = true;
                    logger.info("机构ID{},变更期数正常，需要执行更新,生效表变更期数{}，待生效表期数{}",
                            partyToBeEffectiveDO.getPartyID(), partyInfoDO.getChangeNumber(), partyToBeEffectiveDO.getChangeNumber());
                }

                //失效日期小于等当前日期，更新为已失效
                if (partyToBeEffectiveDO.getInEffectiveDate() != null && DateCompareUtil.nowGTEDateTime(partyToBeEffectiveDO.getInEffectiveDate())) {
                    partyInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_REVOKE);
                    needUpdate = true;
                    logger.info("机构ID{},失效日期小于等于当前日期，需要执行失效处理, 失效日期{}",
                            partyToBeEffectiveDO.getPartyID(), partyToBeEffectiveDO.getInEffectiveDate());
                }
            }
        } else {
            //失效 当前日期等于时效日期
            if (partyInfoDO == null) {
                //失效， 并且生效表中不存在该数据， 直接返回，表示当前处理成功
                logger.info("机构ID{},撤销处理，生效表中不存在，不处理", partyToBeEffectiveDO.getPartyID());
                return;
            } else {
                protertiesChange(partyToBeEffectiveDO, partyInfoDO);
            }

            //变更期数判断， 如果生效表中变更期数小于待生效表中变更期数，进行更新操作
            if (partyInfoDO.getChangeNumber() < partyToBeEffectiveDO.getChangeNumber()
                    || partyInfoDO.getChangeNumber() >= NbInf.MAX_CHNG_NB) {
                logger.info("机构ID{}, 变更期数正常，可能需要执行撤销操作, 生效表变更期数{}， 待生效表变更期数{}",
                        partyToBeEffectiveDO.getPartyID(), partyInfoDO.getChangeNumber(), partyToBeEffectiveDO.getChangeNumber());
                //失效日期小于等当前日期，更新为已失效
                if (partyToBeEffectiveDO.getInEffectiveDate() != null && DateCompareUtil.nowGTEDateTime(partyToBeEffectiveDO.getInEffectiveDate())) {
                    partyInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_REVOKE);
                    needUpdate = true;
                    logger.info("机构ID{}, 失效日期小于等于当前日期，需要执行撤销操作, 待生效表失效日期{}",
                            partyToBeEffectiveDO.getPartyID(), partyToBeEffectiveDO.getInEffectiveDate());
                }
            }
        }

        //新增判断， 减少再次查询
        if (needUpdate) {
            partyInfoDO.setLastUpDate(DateUtil.getDefaultDate());
            partyInfoDO.setLastUpTime(DateUtil.getDefaultTime());
            partyInfoDO.setChangeCircleTimes(partyToBeEffectiveDO.getChangeCircleTimes());
            partyInfoDO.setChangeNumber(partyToBeEffectiveDO.getChangeNumber());
            if (newInsert) {
                logger.info("机构ID{}, 执行插入生效表操作", partyInfoDO.getPartyID());
                partyInfoTmpRepository.insertParty(partyInfoDO);
            } else {
                logger.info("机构ID{}, 执行更新生效表操作", partyInfoDO.getPartyID());
                partyInfoTmpRepository.updateParty(partyInfoDO);
            }
        }
    }
    private void protertiesChange(PartyToBeEffectiveDO partyToBeEffectiveDO, PartyInfoDO partyInfoDO) {
        //属性变更判断， 可能原有数据为空
        if (partyToBeEffectiveDO.getPartyID() != null) {
            partyInfoDO.setPartyID(partyToBeEffectiveDO.getPartyID());
        }
        if (partyToBeEffectiveDO.getTelephone() != null) {
            partyInfoDO.setTelephone(partyToBeEffectiveDO.getTelephone());
        }
        if (partyToBeEffectiveDO.getContact() != null) {
            partyInfoDO.setContact(partyToBeEffectiveDO.getContact());
        }
        if (partyToBeEffectiveDO.getFax() != null) {
            partyInfoDO.setFax(partyToBeEffectiveDO.getFax());
        }
        if (partyToBeEffectiveDO.getMail() != null) {
            partyInfoDO.setMail(partyToBeEffectiveDO.getMail());
        }
        if (partyToBeEffectiveDO.getPartyAlias() != null) {
            partyInfoDO.setPartyAlias(partyToBeEffectiveDO.getPartyAlias());
        }
        if (partyToBeEffectiveDO.getPartyName() != null) {
            partyInfoDO.setPartyName(partyToBeEffectiveDO.getPartyName());
        }
        if (partyToBeEffectiveDO.getPartyStatus() != null) {
            partyInfoDO.setPartyStatus(partyToBeEffectiveDO.getPartyStatus());
        }
        if (partyToBeEffectiveDO.getChangeType() != null) {
            partyInfoDO.setPartyType(partyToBeEffectiveDO.getPartyType());
        }
        if (partyToBeEffectiveDO.getEffectDate() != null) {
            partyInfoDO.setEffectDate(partyToBeEffectiveDO.getEffectDate());
        }
        if (partyToBeEffectiveDO.getInEffectiveDate() != null) {
            partyInfoDO.setInEffectiveDate(partyToBeEffectiveDO.getInEffectiveDate());
        }
    }

    /**
     * 业务权限变更事件
     *
     * @AUth :wanyangwei
     */
    private String rightChange() {
        String result = SUCC;
//        logger.info("start process right change task.");
//        String dsptUrl = RtpUtil.getInstance().getProperty("ecny.online.server.url");
//        if (StringUtils.isEmpty(dsptUrl)) {
//            logger.error("Cannot found online server url.");
//            return "找不到联机服务器url地址";
//        }
//        try {
//            ECNYReqDTO ecnyReqDTO = new ECNYReqDTO();
//
//            //封装请求头
//            Head head = new Head();
//            head.setRetCode(Head.RET_CODE_SUCCESS);
//            head.setRetInfo(Head.RET_CODE_SUCCESS);
//            head.setResdFlag("N");
//            head.setTranDate(DateUtil.getDefaultDate());
//            head.setTranTime(DateUtil.getDefaultTime());
//            head.setSeqNo(generateCodeService.generateCoreReqSerno());
//            ecnyReqDTO.setHead(head);
//
//            //封装ECNY头
//            ECNYReqHead ecnyReqHead = new ECNYReqHead();
//            ecnyReqHead.setBusiChnl(BusiConst.ECNY_SYS_ID);
//            ecnyReqHead.setBrno(TMP_BRO_NO);
//            ecnyReqHead.setOrigChnl(TMP_SYS_ID);
//            ecnyReqDTO.setEcnyHead(ecnyReqHead);
//
//            //执行请求
//            String url = dsptUrl + RIGHT_CHANGE_URL;
//            logger.info("发送业务权限变更请求任务到联机服务器");
//            String rsp = HttpClientUtil.doJsonPost(BusiConst.ECNY_SYS_ID, ApiConstant.INVOKE_RIGHT_CHANGE,
//                    url, JSONObject.toJSON(ecnyReqDTO).toString(), "utf-8");
//            logger.info("待业务权限变更任务执行完成，联机服务器响应记过{}", rsp);
//
//            //解析结果
//            JSONObject jsonObject = JSONObject.parseObject(rsp);
//            ECNYRspDTO ecnyRspDTO = JSONObject.toJavaObject(jsonObject, ECNYRspDTO.class);
//
//            //结果判断，head.retCode 000000为成功
//            if (!Head.RET_CODE_SUCCESS.equals(ecnyRspDTO.getHead().getRetCode())) {
//                logger.error("待业务权限变更任务执行出现错误");
//                result = ecnyRspDTO.getHead().getRetInfo();
//            }
//        } catch (Exception e) {
//            logger.error("请求网络错误{}", e.getMessage(), e);
//            result = "网络请求错误";
//        }
//        logger.info("end process right change  task.");
        return result;
    }

    public void execTask(String settleDate) {
        logger.info("start process execute task fialure case.");
        List<SettleTaskGroupExecDO> failureList = taskGroupRepository.queryTaskExecFailure(settleDate);
        if (failureList == null || failureList.isEmpty()) {
            logger.info("cannot found execute task fialure case, process done.");
            return;
        }
        int threads = 0;
        try {
            threads = Integer.parseInt(threadNum);
        } catch (Exception e) {
            threads = 5;
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        for (SettleTaskGroupExecDO task : failureList) {
            executorService.submit(new TaskExecRunnable(settleDate, task.getBatchId(), task.getTaskGroupCode()));
        }
        executorService.shutdown();
    }
}