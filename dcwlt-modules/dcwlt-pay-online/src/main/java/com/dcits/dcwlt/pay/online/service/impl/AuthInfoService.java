package com.dcits.dcwlt.pay.online.service.impl;


import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.AuthInfoDrctEnum;
import com.dcits.dcwlt.common.pay.enums.AuthoritySignCodeEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyChngNtfctn;
import com.dcits.dcwlt.pay.api.model.AuthInfoDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.IAuthInfoRepository;
import com.dcits.dcwlt.pay.online.service.IAuthInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanyangwei
 * @desc 业务权限变更
 */
@Service
public class AuthInfoService implements IAuthInfoService {
    private static final Logger logger = LoggerFactory.getLogger(AuthInfoService.class);
    @Autowired
    private IAuthInfoRepository iAuthInfoRepository;

    //计数器
    private int count = 0;
    
    @Override
    public Boolean validateAuthInfo(String partyid, String msgType, String tradeCtgyCode, AuthInfoDrctEnum flag) {
        logger.info("进入执行[validateAuthInfo]方法,进入时间为:{}", DateUtil.getDefaultTime());

        //封装查询条件
        AuthInfoDO authInfoDO = new AuthInfoDO();
        authInfoDO.setPartyId(partyid);
        authInfoDO.setMsgType(msgType);
        authInfoDO.setTradeCtgyCode(tradeCtgyCode);

        try {
            //校验业务权限
            List<AuthInfoDO> list = iAuthInfoRepository.validateAuthInfo(authInfoDO);
            if (list.isEmpty()) {
                logger.info("无法查询到该业务信息，partyId：{}， msgType：{},TradeCtgyCode：{}", partyid, msgType, tradeCtgyCode);
                logger.info("无业务权限信息,结束validateAuthInfo方法,结束时间为:{}", DateUtil.getDefaultTime());
                //无权限
                return false;
            }
            //如果有两条权限信息时(TradeCtgyCode有具体值和有null时,以TradeCtgyCode有具体值的权限信息为准)
            AuthInfoDO authInfo = new AuthInfoDO();
            if (list.size() > 1) {
                for (AuthInfoDO infoDO : list) {
                    if (!StringUtils.equals("null", infoDO.getTradeCtgyCode())) {
                        BeanUtils.copyProperties(infoDO, authInfo);
                    }
                }
            } else {
                BeanUtils.copyProperties(list.get(0), authInfo);
            }
            //撤销状态
            String status = authInfo.getStatus();
            if (AppConstant.EFFECTIVE_STATUS_REVOKE.equals(status)) { //已撤销
                logger.info("业务权限状态为:{},已撤销", status);
                return false;
            }
            //判断当前日期下 业务权限是否已生效/失效
            String currentDateStr = DateUtil.getDefaultDate();
            //失效日期
            String inEffectiveDate = authInfo.getInEffectiveDate();
            if (null != inEffectiveDate && !"".equals(inEffectiveDate) && currentDateStr.compareTo(inEffectiveDate) >= 0) {
                logger.info("已过业务权限失效日期:{}", inEffectiveDate);
                return false;
            }
            //生效日期
            String effectDate = authInfo.getEffectDate();
            if (null != effectDate && !"".equals(effectDate) && currentDateStr.compareTo(effectDate) < 0) {//还未生效
                logger.info("未到业务权限生效日期:{}", effectDate);
                return false;
            }
            //查询接收权限
            if (AuthInfoDrctEnum.recvAuth.getCode().equals(flag.getCode())) {
                //接收权限标识为允许/禁止
                if (AuthoritySignCodeEnum.AS00.getCode().equals(authInfo.getRecvAuth())) {
                    logger.info("{}有接收权限->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getRecvAuth(), DateUtil.getDefaultTime());
                    //有接收权限
                    return true;
                }
                logger.info("{}无接收权限->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getRecvAuth(), DateUtil.getDefaultTime());
                //无接收权限
                return false;
            } else {   //查询发送权限
                //发送权限标识为允许/禁止
                if (AuthoritySignCodeEnum.AS00.getCode().equals(authInfo.getSendAuth())) {
                    logger.info("{}有发送权限 ->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getSendAuth(), DateUtil.getDefaultTime());
                    //有发送权限
                    return true;
                }
                logger.info("{}无法送权限->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getSendAuth(), DateUtil.getDefaultTime());
                //无发送权限
                return false;
            }
        } catch (Exception e) {
            logger.error("校验业务权限变更数据库操作异常,异常信息e:{}", e.getMessage());
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }

    @Override
    public int replaceAuthInfo(AuthrtyChngNtfctn authrtyChngNtfctn) {
        return 0;
    }
}
