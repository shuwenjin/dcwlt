package com.dcits.dcwlt.pay.online.service.impl;

import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.enums.ChangeCdEnum;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import com.dcits.dcwlt.pay.api.domain.dcep.common.NbInf;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYReqDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspBody;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspDTO;
import com.dcits.dcwlt.pay.api.domain.ecny.ECNYRspHead;
import com.dcits.dcwlt.pay.api.model.AuthInfoDO;
import com.dcits.dcwlt.pay.api.model.AuthInfoToBeEffectiveDO;
import com.dcits.dcwlt.pay.api.model.PartyInfoDO;
import com.dcits.dcwlt.pay.api.model.PartyToBeEffectiveDO;
import com.dcits.dcwlt.pay.online.exception.EcnyTransError;
import com.dcits.dcwlt.pay.online.exception.EcnyTransException;
import com.dcits.dcwlt.pay.online.service.*;
import com.dcits.dcwlt.pay.online.util.DateCompareUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 机构定时临时数据生效转移
 *
 * @author majun
 * @date 2021/1/3
 */
//@FunService(namespace = "party")
    @Component
public class PartyService implements IPartyService {
    private static final Logger logger = LoggerFactory.getLogger(PartyService.class);

    @Autowired
    private IPartyToBeEffectiveRepository partyInfoTmpRepository;
    @Autowired
    private IPartyInfoservice partyInfoRepository;
    @Autowired
    private IAuthInfoRepository iAuthInfoRepository;
    @Autowired
    private IAuthInfoToBeEffectiveRepository authInfoToBeEffectiveRepository;

    /**
     * 定时移动生效数据
     */
//    @FunMethod(name = ApiConstant.INVOKE_PARTY_TO_EFFECTIVE, session = false)
    public ECNYRspDTO effectiveParty(ECNYReqDTO reqDTO) {
        logger.debug("开始进行临时机构生效定时任务");

        //响应结果初始化
        ECNYRspDTO rspDTO = init(reqDTO);
        try {
            //执行移动
            move();

            //执行成功，响应结果处理
            response(rspDTO);
        } catch (Throwable ex) {
            errorHandler(ex, rspDTO);
        }
        return rspDTO;
    }

    /**
     * 定时移动生效数据
     */
//    @FunMethod(name = ApiConstant.INVOKE_RIGHT_CHANGE, session = false)
    public ECNYRspDTO rightChanged(ECNYReqDTO reqDTO) {
        logger.debug("开始进行临时业务权限生效定时任务");

        //响应结果初始化
        ECNYRspDTO rspDTO = init(reqDTO);
        try {
            //执行移动
            transformAuthInfo();
            //执行成功，响应结果处理
            response(rspDTO);
        } catch (Throwable ex) {
            errorHandler(ex, rspDTO);
        }
        return rspDTO;
    }

    /*
     * 业务权限临时表信息插入业务权限表
     * @author wanyangwei
     * */
    public void transformAuthInfo() {
        //失败条数
        int faultCount = 0;
        AuthInfoToBeEffectiveDO authInfoToBeEffectiveDO = new AuthInfoToBeEffectiveDO();
        //设置当前时间为生效/失效时间

        String currentDateStr = DateUtil.getSysDate();
        authInfoToBeEffectiveDO.setEffectDate(currentDateStr);
        authInfoToBeEffectiveDO.setInEffectiveDate(currentDateStr);

        //根据当前时间查询符合条件的权限信息
        List<AuthInfoToBeEffectiveDO> authInfoList = authInfoToBeEffectiveRepository.queryAuthInfo(authInfoToBeEffectiveDO);
        if (authInfoList.isEmpty() || null == authInfoList) {
            logger.info("无符合条件的业务权限信息:authInfoList {}", authInfoList);
        }
        //执行转存业务权限临时信息到业务权限表操作
        for (AuthInfoToBeEffectiveDO infoToBeEffectiveDO : authInfoList) {
            String changeType = infoToBeEffectiveDO.getChangeType();
            //封装实体
            AuthInfoDO authInfoDO = new AuthInfoDO();
            authInfoDO.setPartyId(infoToBeEffectiveDO.getPartyId());
            authInfoDO.setMsgType(infoToBeEffectiveDO.getMsgType());
            authInfoDO.setTradeCtgyCode(infoToBeEffectiveDO.getTradeCtgyCode());
            authInfoDO.setSendAuth(infoToBeEffectiveDO.getSendAuth());
            authInfoDO.setRecvAuth(infoToBeEffectiveDO.getRecvAuth());
            authInfoDO.setEffectDate(infoToBeEffectiveDO.getEffectDate());
            authInfoDO.setInEffectiveDate(infoToBeEffectiveDO.getInEffectiveDate());
            authInfoDO.setLastUpDate(DateUtil.getSysDate());
            authInfoDO.setLastUpTime(DateUtil.getCurTime());
            //变更类型为新增或修改
            if (ChangeCdEnum.CC00.getCode().equals(changeType) || ChangeCdEnum.CC01.getCode().equals(changeType)) {
                //设置状态为生效中
                authInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_EFFECTIVE);
            } else { //更变类型为撤销
                //设置状态为已撤销
                authInfoDO.setStatus(AppConstant.EFFECTIVE_STATUS_REVOKE);
            }
            try {
                //转存业务权限信息,转存完成无需删除源数据
                iAuthInfoRepository.replaceAuthInfo(authInfoDO);
                //转存业务权限信息成功后 删除该条信息
                authInfoToBeEffectiveRepository.deleteAuthInfo(infoToBeEffectiveDO);
                logger.info("转存该条业务权限信息插入业务权限表成功,删除临时表数据成功:authInfoDO {}", authInfoDO);
            } catch (Exception e) {
                faultCount += 1;
                logger.warn("转存该条业务权限失败,partyId:{},msgType:{},tradeCtgyCode:{}", infoToBeEffectiveDO.getPartyId(), infoToBeEffectiveDO.getMsgType(), infoToBeEffectiveDO.getTradeCtgyCode());
                logger.warn("转存异常信息为:{}", e.getMessage());
            }
        }
        //失败日志记录
        if (faultCount != 0) {
            logger.warn("移动业务权限临时表数据到业务权限表完成，移动总数：{}， 移动失败条数：{}", authInfoList.size(), faultCount);
            throw new EcnyTransException(EcnyTransError.ECNY_AHTUINFO_TOBE_EFFECTIVE_ERROR);
        }
    }

    private void move() {
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
    }

    private ECNYRspDTO init(ECNYReqDTO reqDTO) {
        //ECNY头初始化，初始为异常
        ECNYRspHead ecnyRspHead = new ECNYRspHead();
        ecnyRspHead.setTrxStatus(AppConstant.TRXSTATUS_ABEND);

        //初始化响应报文
        ECNYRspDTO<ECNYRspBody> result = ECNYRspDTO.newInstance(reqDTO, ecnyRspHead, null);

//        result.getHead().setRetCode(EcnyTransError.OTHER_TECH_ERROR.getErrorCode());
//        result.getHead().setRetInfo(EcnyTransError.OTHER_TECH_ERROR.getErrorCode());

        return result;
    }

    private void response(ECNYRspDTO rspDTO) {
//        rspDTO.getHead().setRetCode(Head.RET_CODE_SUCCESS);
//        rspDTO.getHead().setRetInfo(PlatformError.SUCCESS.getErrorMsg());

        rspDTO.getEcnyRspHead().setTrxStatus(AppConstant.TRXSTATUS_SUCCESS);
    }

    private void errorHandler(Throwable ex, ECNYRspDTO rspDTO) {
        logger.warn("执行移动失败,错误原因", ex);
        // 错误码映射
//        RspCodeMapDO rspCodeMapDO = EcnyTransException.convertRspCode(ex);

        if (ex instanceof EcnyTransException) {
//            rspDTO.getHead().setRetCode(((EcnyTransException) ex).getErrorCode());
//            rspDTO.getHead().setRetInfo(((EcnyTransException) ex).getErrorMsg());
        } else {
//            rspDTO.getHead().setRetCode(EcnyTransError.OTHER_TECH_ERROR.getErrorCode());
//            rspDTO.getHead().setRetInfo(EcnyTransError.OTHER_TECH_ERROR.getErrorMsg());
        }

        rspDTO.getEcnyRspHead().setTrxStatus(AppConstant.TRXSTATUS_ABEND);
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
        PartyInfoDO partyInfoDO = partyInfoRepository.queryPartyInfoByPartyId(partyToBeEffectiveDO.getPartyID());

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
                partyInfoRepository.addParty(partyInfoDO);
            } else {
                logger.info("机构ID{}, 执行更新生效表操作", partyInfoDO.getPartyID());
                partyInfoRepository.updateParty(partyInfoDO);
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
     * 判断机构是否可以发送或接收报文
     *
     * @param partyId
     * @return
     */
    public boolean sendReceiveAble(String partyId) {
        return partyInfoRepository.sendReceiveAble(partyId);
    }

    /**
     * 判断机构是否可以发送或接收报文
     *
     * @param partyInfoDO
     * @return
     */
    public boolean sendReceiveAble(PartyInfoDO partyInfoDO) {
        return partyInfoRepository.sendReceiveAble(partyInfoDO);
    }

    /**
     * 机构是否可用
     *
     * @param partyInfoDO
     * @return
     */
    public boolean isAvailable(PartyInfoDO partyInfoDO) {
        return partyInfoRepository.isAvailable(partyInfoDO);
    }

    /**
     * 机构是否可用
     *
     * @param partyId
     * @return
     */
    public boolean isAvailable(String partyId) {
        return partyInfoRepository.isAvailable(partyId);
    }
}
