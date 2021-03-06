package com.dcits.dcwlt.pay.api.mq.event.impl;

import com.alibaba.fastjson.JSON;
import com.dcits.dcwlt.common.mq.EventProducer;
import com.dcits.dcwlt.common.pay.constant.EventConst;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventConfigDO;
import com.dcits.dcwlt.pay.api.domain.dcep.eventBatch.EventDealReqMsg;
import com.dcits.dcwlt.pay.api.mq.event.IEventRegisterAppService;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransError;
import com.dcits.dcwlt.pay.api.mq.event.exception.EcnyTransException;
import com.dcits.dcwlt.pay.api.mapper.EventConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @Time 2020/10/29 11:39
 * @Version 1.0
 * Description:异常事件注册服务
 */
@Service
public class EventRegisterAppServiceimpl implements IEventRegisterAppService {

    private static final Logger logger = LoggerFactory.getLogger(EventRegisterAppServiceimpl.class);

    @Autowired
    private EventConfigMapper eventConfigMapper;

    @Autowired
    private EventProducer eventProducer;

    /**
     * 注册异常事件，将异常信息存放在消息中心
     *
     * @param reqMsg
     */
    @Override
    public void registerEvent(EventDealReqMsg reqMsg) {
        registerEvent(reqMsg, null);
    }

    /**
     * 注册异常事件，将异常信息存放在消息中心
     *
     * @param reqMsg
     * @param msgTag
     */
    @Override
    public void registerEvent(EventDealReqMsg reqMsg, String msgTag) {
        //获取异常配置表信息，
        String eventCode = reqMsg.getExceptEventCode();
        EventConfigDO config = loadEventConfig(eventCode);
        // 延时级别
        int intervalMin = EventConst.EVENT_INTERVAL_TIME.get(config.getExceptEventDealIntervalMin());

        //保存事件的消息标签，用于重试
        reqMsg.setExceptEventMsgTag(msgTag);
        if("BATCH".equals(msgTag)) {
            //发送消息中心
            eventProducer.sendMsg(msgTag, "topic-dcwlt-batch", JSON.toJSON(reqMsg).toString(), intervalMin);
        } else {
            eventProducer.sendMsg(msgTag, "topic-dcwlt", JSON.toJSON(reqMsg).toString(), intervalMin);
        }

    }


    /**
     * 加载异常事件配置
     *
     * @param eventCode
     * @return
     */
    @Override
    public EventConfigDO loadEventConfig(String eventCode) {
        EventConfigDO config = eventConfigMapper.queryEventConfig(eventCode);
        if (null == config) {
            logger.error("异常事件参数未配置！");
            throw new EcnyTransException(EcnyTransError.ECNY_SEND_REQUEST_ERROR);
        }
        return config;
    }


    /**
     * 获取最大处理次数
     *
     * @param eventCode
     * @return
     */
    @Override
    public String getMaxDealCount(String eventCode) {
        EventConfigDO config = loadEventConfig(eventCode);
        return config.getExceptEventDealMaxCount();
    }

}
