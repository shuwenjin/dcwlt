package com.dcits.dcwlt.pay.batch.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import com.dcits.dcwlt.common.pay.enums.TaskGroupEnum;
import com.dcits.dcwlt.common.pay.sequence.service.IGenerateCodeService;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.model.SettleTaskGroupExecDO;
import com.dcits.dcwlt.pay.batch.service.ISettleTaskGroupExecService;
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
     * 调用联机系统机构生效事件
     */
    private String partyToEffective() {
        String result = SUCC;
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