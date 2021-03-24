package com.dcits.dcwlt.pay.api.mq.event.impl;

import com.dcits.dcwlt.common.pay.constant.EventConst;
import com.dcits.dcwlt.common.pay.exception.EventDealError;
import com.dcits.dcwlt.common.pay.exception.EventDealException;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.pay.api.mq.event.IEventRegisterAppService;
import com.dcits.dcwlt.pay.api.mapper.EventMapper;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventConfigDO;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealRspMsg;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventInfoDO;
import com.dcits.dcwlt.pay.api.fun.FunInvokerFactory;
import com.dcits.dcwlt.pay.api.fun.FunInvoker;
import com.dcits.dcwlt.pay.api.fun.FunExecutor;
import com.dcits.dcwlt.pay.api.mq.event.IEventDealAppService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhanguohai
 * @Time 2020/10/29 11:36
 * @Version 1.0
 * Description:异常事件处理服务
 */
@Service
public class EventDealAppService implements IEventDealAppService {

    private static final Logger logger = LoggerFactory.getLogger(EventDealAppService.class);

    @Autowired
    private IEventRegisterAppService eventRegisterService;

    @Autowired
    private EventMapper eventMapper;

    @Override
    public void eventDeal(EventDealReqMsg eventDealReqMsg) {
        //1、初始化入库信息
        EventInfoDO eventInfoDO = initEventInfo(eventDealReqMsg);

        //2、保存异常事件,MQ-->DB

        saveEvent(eventInfoDO);

        //3、事件处理
        dealEvent(eventDealReqMsg, eventInfoDO);

        //4、更新异常事件处理结果
        eventMapper.updateEventInfo(eventInfoDO);
    }


    /**
     * 保存异常事件信息
     *
     * @param event
     */
    private void saveEvent(EventInfoDO event) {
        try {
            event.setLastUpDate(DateUtil.getDefaultDate());
            event.setLastUpTime(DateUtil.getDefaultTime());
            eventMapper.insertEventInfo(event);
        } catch (DuplicateKeyException e) {
            // 累加重試次數
            String exceptEventCode = event.getExceptEventCode();
            String exceptEventSeqNo = event.getExceptEventSeqNo();
            String maxDealNum = eventRegisterService.getMaxDealCount(event.getExceptEventCode());
            Map<String, Object> param = new HashMap<>();
            param.put("exceptEventCode", exceptEventCode);
            param.put("exceptEventSeqNo", exceptEventSeqNo);
            param.put("maxDealNum", maxDealNum);
            int count = eventMapper.updateEventDealNum(param);
            if (count < 1) {
                logger.info("事件：{}，已达到最大处理次数，不再重试", exceptEventCode);
                event.setExceptEventSysStatus(EventConst.EVENT_DEAL_EXPT);
                throw new EventDealException(EventDealError.REACH_MAX_DEAL);
            }
        }
    }


    /**
     * 初始化事件信息
     *
     * @param eventMsg
     * @return
     */
    private EventInfoDO initEventInfo(EventDealReqMsg eventMsg) {
        EventInfoDO event = new EventInfoDO();
        event.setExceptEventDate(DateUtil.getDefaultDate());
        event.setExceptEventSerno(DateUtil.getSystemTime());
        event.setExceptEventTime(DateUtil.getDefaultTime());
        event.setExceptEventCode(eventMsg.getExceptEventCode());
        event.setExceptEventSeqNo(eventMsg.getExceptEventSeqNo());
        event.setExceptEventDealCount("1");
        event.setExceptEventSysStatus(EventConst.EVENT_DEAL_PROC);
        // todo 后期获取获取动态ip
        event.setExceptEventDealPath("127.0.0.1");
        event.setExceptEventContext(eventMsg.getExceptEventContext());
        return event;
    }

    /**
     * 异常事件处理
     *
     * @param eventMsg
     * @param eventInfo
     */
    public void dealEvent(EventDealReqMsg eventMsg, EventInfoDO eventInfo) {
        EventDealRspMsg dealRspMsg = dealEvent(eventMsg);
        logger.info("异常处理响应信息：" + dealRspMsg.toString());
        eventInfo.setExceptEventErrorCode(dealRspMsg.getRespCode());
        eventInfo.setExceptEventErrorMsg(dealRspMsg.getRespMsg());

        // 返回非0000000都当失败，应用可选择重试，重试的话，公共模块自动再发到消息中心一次
        if (PlatformError.SUCCESS.getErrorCode().equals(dealRspMsg.getRespCode())) {
            eventInfo.setExceptEventSysStatus(EventConst.EVENT_DEAL_SUCC);
        } else {
            eventInfo.setExceptEventSysStatus(EventConst.EVENT_DEAL_FAIL);
            if (!EventConst.EVENT_DEAL_RETRY_N.equals(dealRspMsg.getRetryFlag())) {
                eventRegisterService.registerEvent(eventMsg, eventMsg.getExceptEventMsgTag());
            }
        }

    }

    /**
     * 事件处理
     *
     * @param eventInfo
     * @return
     */
    private EventDealRspMsg dealEvent(EventDealReqMsg eventInfo) {
        logger.info("异常事件处理EventDealReqMsg:" + eventInfo.toString());
        EventDealRspMsg rspMsg = new EventDealRspMsg();
        try {
            EventConfigDO config = eventRegisterService.loadEventConfig(eventInfo.getExceptEventCode());
            logger.info("异常事件处理 Config:" + config);

            //自动处理
            if (EventConst.EVENT_DEAL_TYPE_AUTO.equals(config.getExceptEventDealType())) {
                rspMsg = autoDealEvent(eventInfo, config);
            } else {
                //人工处理暂不实现
                rspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
                rspMsg.setRespCode(EventDealError.MANU_DEAL.getErrorCode());
                rspMsg.setRespMsg(EventDealError.MANU_DEAL.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("异常事件处理失败：{}", e.getMessage(), e);
            //用于自动处理异常的情况下重试，其他情况若设置了不自动重试则落地。
            if (StringUtils.isBlank(rspMsg.getRetryFlag())) {
                rspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_Y);
            }
            rspMsg.setRespCode(EventDealError.SYS_ERR.getErrorCode());
            rspMsg.setRespMsg(EventDealError.SYS_ERR.getErrorMsg());
        }
        return rspMsg;
    }


    /**
     * 自动处理异常事件
     *
     * @param eventInfo
     * @param config
     * @return
     */
    private EventDealRspMsg autoDealEvent(EventDealReqMsg eventInfo, EventConfigDO config) {
        //初始化响应信息
        EventDealRspMsg rspMsg = new EventDealRspMsg();
        rspMsg.setExceptEventCode(eventInfo.getExceptEventCode());
        rspMsg.setExceptEventSeqNo(eventInfo.getExceptEventSeqNo());

        //请求FUN交易初始化
        FunInvoker invoker = FunInvokerFactory.getFunInvoker(config.getExceptEventTrxCode());//根据 FUN Path 获取 FUN 执行器

        //本线程调用
        if (EventConst.EVENT_DEAL_MODE_LOCAL.equals(config.getExceptDealMode())) {
            Object respParam = FunExecutor.execute(invoker, eventInfo);// 执行 FUN，获取返回结果
            return (EventDealRspMsg) respParam;
            //异步线程调用
        } else if (EventConst.EVENT_DEAL_MODE_ASYNC.equals(config.getExceptDealMode())) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                try {
                    FunExecutor.execute(invoker, eventInfo);
                } catch (Exception e) {
                    //异步调用不处理异常
                }
            });
            executorService.shutdown();
            rspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            rspMsg.setRespCode(PlatformError.SUCCESS.getErrorCode());
            rspMsg.setRespMsg(PlatformError.SUCCESS.getErrorMsg());
            return rspMsg;
            //其他暂不开放
        } else {
            logger.info("异常事件暂不支持该处理模式");
            rspMsg.setRetryFlag(EventConst.EVENT_DEAL_RETRY_N);
            throw new EventDealException(EventDealError.SYS_ERR);
        }
    }
}
