package com.dcits.dcwlt.pay.online.exception;


import com.dcits.dcwlt.common.pay.exception.TransError;
import com.dcits.dcwlt.common.pay.exception.TransException;
import com.dcits.dcwlt.pay.api.model.RspCodeMapDO;

public class EcnyTransException extends TransException {

    private String status;    //交易状态，详细见AppConstant.java的业务状态常量，一般只用成功、失败、异常三种
    private String srcId;     //源系统标识

    private static final long serialVersionUID = 1L;

    public EcnyTransException(TransError error) {
        super(error.getErrorCode(), error.getErrorMsg());
    }

    public EcnyTransException(RspCodeMapDO error) {
        super(error.getDestRspCode(), error.getRspCodeDsp());
    }


    public EcnyTransException(String status, EcnyTransError error) {
        super(error.getErrorCode(), error.getErrorMsg());
        this.status = status;
    }

    public EcnyTransException(String srcId, String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
        this.srcId = srcId;
    }

    public EcnyTransException(String status, String srcId, String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
        this.srcId = srcId;
        this.status = status;
    }

    public EcnyTransException(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }


//
//    /**
//     * 错误码转换，默认不使用txnType，如有该场景再扩展（txnType应答码分类,C为通用类,S为签约类，F为金融类，为了解决不同业务场景返回不通错误码的问题）
//     *
//     * @param e 异常类
//     * @return
//     */
    public static RspCodeMapDO convertRspCode(Throwable e) {
        return convertRspCode(e);
    }

    public static RspCodeMapDO convertRspCode(String coreSysId, String dcepSysId, String errorCode, String errorMsg) {
     return  convertRspCode(coreSysId,dcepSysId,errorCode,errorMsg);
    }
//
//
//    /**
//     * 错误码转换
//     *
//     * @param srcId     源系统
//     * @param destId    目标系统
//     * @param errorCode 错误码
//     * @return
//     */
//    public static RspCodeMapDO convertRspCode(String srcId, String destId, String errorCode, String errorMsg) {
//        return convertRspCode(srcId, destId, AppConstant.TXN_TYPE_COMM, errorCode, errorMsg);
//    }

//    /**
//     * 错误码转换
//     *
//     * @param srcId     源系统
//     * @param destId    目标系统
//     * @param trxType   交易类别
//     * @param errorCode 错误码
//     * @return
//     */
//    public static RspCodeMapDO convertRspCode(String srcId, String destId, String trxType, String errorCode, String errorMsg) {
//        ResponseCdService responseCdService = RtpUtil.getInstance().getBean(ResponseCdService.class);
//
//        // 返回码实体
//        RspCodeMapDO codeMap = responseCdService.getErrorCodeMap(AppConstant.ECNY_SYS_ID, srcId, destId, trxType, errorCode, "*");
//
//
//        // 通用匹配
//        if (codeMap == null && !trxType.equals(AppConstant.TXN_TYPE_COMM)) {
//            codeMap = responseCdService.getErrorCodeMap(AppConstant.ECNY_SYS_ID, srcId, destId, AppConstant.TXN_TYPE_COMM, errorCode, "*");
//        }
//
//        // 交易类型兜底错误映射
//        if (codeMap == null) {
//            codeMap = responseCdService.getErrorCodeMap(AppConstant.ECNY_SYS_ID, srcId, destId, trxType, "*", "*");
//        }
//
//
//        //匹配兜底错误码
//        if (codeMap == null) {
//            codeMap = responseCdService.getErrorCodeMap(AppConstant.ECNY_SYS_ID, AppConstant.ECNY_SYS_ID, destId, AppConstant.TXN_TYPE_COMM, EcnyTransError.OTHER_TECH_ERROR.getErrorCode(), "*");
//            codeMap.setRspCodeDsp(errorMsg);
//        }
//        return codeMap;
//    }

//    /**
//     * 错误码转换
//     *
//     * @param e       异常类
//     * @param trxType 交易类别
//     * @param destId  目标系统
//     * @return
//     */
//    public static RspCodeMapDO convertRspCode(Throwable e, String trxType, String destId) {
//        //捕获异常不为EcnyTransException，返回兜底错误码
//        if (!(e instanceof EcnyTransException)) {
//            return convertRspCode(AppConstant.ECNY_SYS_ID, destId, AppConstant.TXN_TYPE_COMM, EcnyTransError.OTHER_TECH_ERROR.getErrorCode(), e.getMessage());
//        }
//        //源系统为上送，则默认为系统自己抛出的异常
//        String srcId = ((EcnyTransException) e).getSrcId();
//        String errorCode = ((EcnyTransException) e).getErrorCode();
//        String errorMsg = ((EcnyTransException) e).getErrorMsg();
//        if (StringUtil.isBlank(srcId)) {
//            srcId = AppConstant.ECNY_SYS_ID;
//        }
//
//        return convertRspCode(srcId, destId, trxType, errorCode, errorMsg);
//    }


    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
