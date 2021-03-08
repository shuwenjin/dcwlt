package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.csp.sentinel.util.StringUtil;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.AuthInfoDrctEnum;
import com.dcits.dcwlt.common.pay.enums.AuthoritySignCodeEnum;
import com.dcits.dcwlt.common.pay.enums.ChangeCdEnum;
import com.dcits.dcwlt.common.pay.enums.EffectiveCdEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyChngNtfctn;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.AuthrtyInf;
import com.dcits.dcwlt.pay.api.domain.dcep.authinfo.BizAuthrtyInf;
import com.dcits.dcwlt.pay.api.domain.dcep.chngctrl.ChngCtrl;
import com.dcits.dcwlt.pay.api.model.AuthInfoDO;
import com.dcits.dcwlt.pay.api.model.AuthInfoToBeEffectiveDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.mapper.AuthInfoMapper;
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
public class AuthInfoServiceimpl implements IAuthInfoService {
    private static final Logger logger = LoggerFactory.getLogger(AuthInfoServiceimpl.class);

    @Autowired
    private AuthInfoMapper authInfoMapper;

    //计数器
    private int count = 0;

    /*
     * 更新/新增业务权限信息
     * */
    @Override
    public int replaceAuthInfo(AuthrtyChngNtfctn authrtyChngNtfctn) {
        logger.info("进入[replaceAuthInfo]方法,进入时间为:{}", DateUtil.getDefaultTime());
        List<AuthrtyInf> authrtyInfList = authrtyChngNtfctn.getAuthrtyInf();
        //遍历参与机构业务权限信息清单
        for (AuthrtyInf authrtyInf : authrtyInfList) {
            //校验数据变更组件
            ChngCtrl chngCtrl = authrtyInf.getChngCtrl();
            //校验数据变更组件
//            chngCtrl.validate();
            //生效类型
            String fctvTp = chngCtrl.getFctvTp();
            //变更类型
            String chngTp = chngCtrl.getChngTp();
            //生效日期
            String fctvDt = chngCtrl.getFctvDt();
            if (StringUtil.isNotBlank(fctvDt)) {
                fctvDt = fctvDt.replaceAll("\\D", "").substring(0, 8);
            }
            //失效日期
            String ifctvDt = chngCtrl.getIfctvDt();
            if (StringUtil.isNotBlank(ifctvDt)) {
                ifctvDt = ifctvDt.replaceAll("\\D", "").substring(0, 8);
            }
            List<BizAuthrtyInf> bizAuthrtyInfList = authrtyInf.getBizAuthrtyInf();
            //循环插入业务权限信息
            if (bizAuthrtyInfList != null && !bizAuthrtyInfList.isEmpty()) {
                for (BizAuthrtyInf bizAuthrtyInf : bizAuthrtyInfList) {
                    try {
                        if (EffectiveCdEnum.EF00.getCode().equals(fctvTp)) { //即时生效
                            //封装实体
                            AuthInfoDO authInfoDO = new AuthInfoDO();
                            authInfoDO.setPartyId(authrtyInf.getPtcpt());
                            authInfoDO.setMsgType(bizAuthrtyInf.getMT());
                            authInfoDO.setTradeCtgyCode(bizAuthrtyInf.getTrdCtgyCd());
                            authInfoDO.setSendAuth(bizAuthrtyInf.getInitAuthrtySgnCd());
                            authInfoDO.setRecvAuth(bizAuthrtyInf.getRcvAuthrtySgnCd());
                            authInfoDO.setEffectDate(fctvDt);
                            authInfoDO.setInEffectiveDate(ifctvDt);
                            authInfoDO.setLastUpDate(DateUtil.getDefaultDate());
                            authInfoDO.setLastUpTime(DateUtil.getDefaultTime());
                            if (ChangeCdEnum.CC00.getCode().equals(chngTp) || ChangeCdEnum.CC01.getCode().equals(chngTp)) {
                                //变更类型为新增或修改,设置状态为生效中
                                authInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_EFFECTIVE);
                            } else { //更变类型为撤销
                                authInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_REVOKE);
                            }
                            //新增或更新业务权限信息
                            authInfoMapper.replaceAuthInfo(authInfoDO);
                            logger.info("该条业务权限插入成功:authInfoDO {}", authInfoDO);
                        } else {     //定时生效
                            //封装实体
                            AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO = new AuthInfoToBeEffectiveDO();
                            authInfoToBeEffectiveDO.setPartyId(authrtyInf.getPtcpt());
                            authInfoToBeEffectiveDO.setMsgType(bizAuthrtyInf.getMT());
                            authInfoToBeEffectiveDO.setTradeCtgyCode(bizAuthrtyInf.getTrdCtgyCd());
                            authInfoToBeEffectiveDO.setSendAuth(bizAuthrtyInf.getInitAuthrtySgnCd());
                            authInfoToBeEffectiveDO.setRecvAuth(bizAuthrtyInf.getRcvAuthrtySgnCd());
                            authInfoToBeEffectiveDO.setEffectiveType(EffectiveCdEnum.EF01.getCode());
                            authInfoToBeEffectiveDO.setEffectDate(fctvDt);
                            authInfoToBeEffectiveDO.setInEffectiveDate(ifctvDt);
                            authInfoToBeEffectiveDO.setLastUpDate(DateUtil.getDefaultDate());
                            authInfoToBeEffectiveDO.setLastUpTime(DateUtil.getDefaultTime());
                            if (ChangeCdEnum.CC00.getCode().equals(chngTp)) { //变更类型为新增
                                authInfoToBeEffectiveDO.setChangeType(ChangeCdEnum.CC00.getCode());
                            } else if (ChangeCdEnum.CC01.getCode().equals(chngTp)) { //更变类型为修改
                                authInfoToBeEffectiveDO.setChangeType(ChangeCdEnum.CC01.getCode());
                            } else {        //更变类型为撤销
                                authInfoToBeEffectiveDO.setChangeType(ChangeCdEnum.CC02.getCode());
                            }
                            //新增或更新业务权限信息到临时表
                            authInfoMapper.replaceAuthInfoToBeEffective(authInfoToBeEffectiveDO);
                            logger.info("该条业务权限临时信息插入成功:authInfoToBeEffectiveDO {}", authInfoToBeEffectiveDO);
                        }
                    } catch (Exception e) {
                        //执行失败,记录该条失败信息
                        count += 1;
                        logger.warn("新增或更新该条业务权限失败,partyId:{},msgType:{},tradeCtgyCode:{}", authrtyInf.getPtcpt(), bizAuthrtyInf.getMT(), bizAuthrtyInf.getTrdCtgyCd());
                        logger.warn("异常信息e:{}", e.getMessage());
                    }
                }
            } else {
                logger.error("业务权限清单为空:{}", bizAuthrtyInfList);
                logger.info("结束执行[replaceAuthInfo]方法,结束时间为:{}", DateUtil.getDefaultDate());
                return authrtyInfList.size();
            }
        }
        logger.info("执行入库失败条数为 faultCount :{} ", count);
        logger.info("结束执行[replaceAuthInfo]方法,结束时间为:{},", DateUtil.getDefaultTime());
        //返回执行失败条数
        return count;
    }

    /*
     * 校验业务权限信息
     * @Param:partyid 机构编码
     * @Param:msgType 报文编号
     * @Param:TradeCtgyCode 业务类型
     * @Param:flag 接收/发送标识: recvAuth/sendAuth
     * */
    @Override
    public Boolean validateAuthInfo(String partyid, String msgType, String tradeCtgyCode, AuthInfoDrctEnum flag) {
        logger.info("进入执行[validateAuthInfo]方法,进入时间为:{}", DateUtil.getDefaultDate());

        //封装查询条件
        AuthInfoDO authInfoDO = new AuthInfoDO();
        authInfoDO.setPartyId(partyid);
        authInfoDO.setMsgType(msgType);
        authInfoDO.setTradeCtgyCode(tradeCtgyCode);

        try {
            //校验业务权限
            List<AuthInfoDO> list = authInfoMapper.validateAuthInfo(authInfoDO);
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
                    logger.info("{}有接收权限->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getRecvAuth(), DateUtil.getDefaultDate());
                    //有接收权限
                    return true;
                }
                logger.info("{}无接收权限->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getRecvAuth(), DateUtil.getDefaultDate());
                //无接收权限
                return false;
            } else {   //查询发送权限
                //发送权限标识为允许/禁止
                if (AuthoritySignCodeEnum.AS00.getCode().equals(authInfo.getSendAuth())) {
                    logger.info("{}有发送权限 ->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getSendAuth(), DateUtil.getDefaultDate());
                    //有发送权限
                    return true;
                }
                logger.info("{}无法送权限->{},结束validateAuthInfo方法,结束时间为:->{}", partyid, authInfo.getSendAuth(), DateUtil.getDefaultDate());
                //无发送权限
                return false;
            }
        } catch (Exception e) {
            logger.error("校验业务权限变更数据库操作异常,异常信息e:{}", e.getMessage());
            throw new EcnyTransException(EcnyTransError.DATABASE_ERROR);
        }
    }
}
