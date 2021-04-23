package com.dcits.dcwlt.common.pay.constant;

import com.dcits.dcwlt.common.pay.enums.EnvironmentEmum;

public class AppConstant {
    public static final String DCEP_HEAD = "dcepHead";
    public static final String DCEP_BODY = "body";
    public static final String MSG_TYPE = "MesgType";
    public static final String SM3 = "SM3";
    /**
     * 发送消息最大长度限制
     */
    public static final Integer MESSAGE_SIZE                 =1024;          //消息大小


    /**
     * 行内系统标识
     */
    public static final String ECNY_SYS_ID                   = "ECNY";       //支付子应用
    public static final String COP_SYS_ID                    = "COP";        //金融开放平台
    public static final String CRPM_SYS_ID                   = "CRPM";       //银行核心前置
    public static final String BDRD_SYS_ID                   = "BDRD";       //大数据零售智能风控子应用
    public static final String ECIF_SYS_ID                   = "ECIF";       //ECIF

    /**
     * 外部系统标识
     */
    public static final String UWAP_SYS_ID                   = "UWAP";       //数字钱包
    public static final String DCEP_SYS_ID                   = "DCEP";       //互联互通
    public static final String DCPS_SYS_ID                   = "DCPS";       //城银清

    /**
     * 金融机构编码                                                   C1030644021075
     */
    public static final String CGB_FINANCIAL_INSTITUTION_CD      = "C1030644021075";    //广发银行
    public static final String DCEP_FINANCIAL_INSTITUTION_CD = "G4001011000013";        //互联互通机构号
    public static final String DCPS_FINANCIAL_INSTITUTION_CD = "G4001011000013";        //城银清机构号

    public static final String ORGCODE = "106";

    /**
     * 交易类别
     */
    public static final String TXN_TYPE_SIGN = "S";     // 签约类
    public static final String TXN_TYPE_COMM = "C";     // 通用类
    public static final String TXN_TYPE_CAPITAL = "F";  // 资金类

    /**
     * 业务状态
     */
    public static final String TRXSTATUS_SUCCESS                   = "1";       //成功
    public static final String TRXSTATUS_FAILED                    = "0";       //失败
    public static final String TRXSTATUS_ABEND                     = "2";       //异常
    public static final String TRXSTATUS_REVERSED                  = "3";       //已冲正
    public static final String TRXSTATUS_RETURNED                  = "5";       //已退回
    public static final String TRXSTATUS_RECIPE                    = "7";       //已收妥
    public static final String TRXSTATUS_UNDO                      = "8";       //已撤销
    public static final String TRXSTATUS_INIT                      = "9";       //未处理
    public static final String TRXSTATUS_PRECREDITSUCCESS          = "A";

    /**
     * 业务处理码
     */
    public static final String TRX_RET_CODE_SUCCESS                = "ECNY000000";       //成功
    public static final String TRX_RET_CODE_FAILED                 = "ECNY000001";       //失败
    public static final String TRX_RET_CODE_PROCESSING             = "ECNY000002";       //处理中

    /**
     * 业务处理信息
     */
    public static final String TRX_RET_MSG_SUCCESS                 = "SUCCESS";          //成功
    public static final String TRX_RET_MSG_FAILED                  = "FAILED";           //失败
    public static final String TRX_RET_MSG_PROCESSING              = "PROCESSING";       //处理中

    /**
     * 核心状态
     */
    public static final String CORESTATUS_INIT                     = "9";       //未处理
    public static final String CORESTATUS_SUCCESS                  = "1";       //成功
    public static final String CORESTATUS_FAILED                   = "0";       //失败
    public static final String CORESTATUS_ABEND                    = "2";       //异常
    public static final String CORESTATUS_REVERSED                 = "3";       //已冲正
    public static final String CORESTATUS_REVERSING                = "4";       //正在冲正
    public static final String CORESTATUS_TRANSFER                 = "5";       //已划转
    public static final String CORESTATUS_TRANSFING                = "6";       //正在划转
    public static final String CORESTATUS_FREEZE                   = "7";       //已冻结
    public static final String CORESTATUS_UNFREEZE                 = "8";       //已解冻
    public static final String CORESTATUS_PRECREDITSUCCESS         = "A";       //预入账成功

    /**
     * 通道状态
     */
    public static final String PAYPATHSTATUS_SUCCESS               = "1";       //成功
    public static final String PAYPATHSTATUS_FAILED                = "0";
    public static final String PAYPATHSTATUS_ABEND                 = "2";      //异常
    public static final String PAYPATHSTATUS_REVERSED              = "3";      //已冲正
    public static final String PAYPATHSTATUS_REVERSING             = "4";      //正在冲正
    public static final String PAYPATHSTATUS_RETURNED              = "5";      //已退回
    public static final String PAYPATHSTATUS_RETURNING             = "6";      //正在退回
    public static final String PAYPATHSTATUS_RECIPE                = "7";      //已收妥
    public static final String PAYPATHSTATUS_RSPFAILD              = "8";      //回执失败
    public static final String PAYPATHSTATUS_INIT                  = "9";      //未处理
    public static final String PAYPATHSTATUS_RSPSUCCESS            = "A";      //回执成功

    /**
     * 往来方向
     */
    public static final String DIRECT_SEND	= "S";
    public static final String DIRECT_RECV	= "R";

    /**
     * 收付标识
     */
    public static final String IDENTIFICATION_PAYEE = "PAYEE";
    public static final String IDENTIFICATION_PAYER = "PAYER";

    /**
     * 协议状态
     */
    public static final String SIGN_STATUS_NORMAL = "N";
    public static final String SIGN_STATUS_CLOSE  = "C";

    /**
     * 异常事件重试标识
     */
    public static final String EVENT_AGAIN_Y                      = "Y";       //重试
    public static final String EVENT_AGAIN_N                      = "N";

     /**
     * 生效状态标识
     */
    public static final String EFFECTIVE_STATUS_EFFECTIVE   = "1";              //生效中
    public static final String EFFECTIVE_STATUS_REVOKE      = "0";              //已撤销

    /**
     * 操作步骤
     */
    public static final String OPERSTEP_INIT                = "INIT";           //入库
    public static final String OPERSTEP_DRDT                = "DRDT";           //扣账
    public static final String OPERSTEP_CRDT                = "CRDT";           //入账
    public static final String OPERSTEP_REVR                = "REVR";           //冲正

    /**
     * 操作状态
     */
    public static final String OPERSTATUS_SUCC              = "SUCC";           //成功
    public static final String OPERSTATUS_FAIL              = "FAIL";           //失败
    public static final String OPERSTATUS_EXPT              = "EXPT";           //异常

    public static final String CURRENCY_SYMBOL              = "CNY";            //币种

    /**
     * 接收超时时间
     */
    public static final long REQUEST_READ_TIMEOUT = 3000L;

    /**
     * 互联互通机构号
     */
    public static final String NET_PARTY_ID = "G4001011000013";

    /**
     * 当前环境
     */
    public static final EnvironmentEmum ENVIRONMENT_EMUM =  EnvironmentEmum.DEV;

    /*
    * 自定义错误信息
    * */
    public static final String FAULT_MSG = "报文处理失败";

    /**
     * TR00100250个人客户基本信息查询交易（定长）
     */

    public static final String ENCODE_GBK = "gbk";

    public static final String REQ_ECIF   = "ECIF";

    public static final String ECIF_SRVCODE_TR00100250_GATEWAY = "EC0004";

    public static final String ECIF_SERV_SUCC = "CII0000000";

    public static final String ECIF_SERV_SUCC1 = "CII0000001";

    public static final String ECIF_SRVCODE_TR00100250 = "TR00100250";

    public static final String ECIF_QUERY_FAIL_MSG = "没有查询到符合条件的信息!";

    public static final String SYSTEMID = "ECNY";

    public static final String EFIC_DEFAULT_CHANNEL_NO = "100200000072";

    /**
     * TR00100300个人客户基本信息查询交易
     */
    public static final String ECIF_SRVCODE_TR00100300 = "TR00100300";
    public static final String ECIF_SRVCODE_TR00100300_GATEWAY = "EC0001";

    /**
     * ECIF 配置的源系统代码
     */
    // TODO: 2021/1/9 和强哥沟通
    public static final String ECIF_SOURCE_BRANCH_NO = "000147";
    public static final String ECIF_DEST_BRANCH_NO = "000042";

    /**
     * 业务类型 兑回、兑出、差错贷记调账
     */
    public static final String BUSINESS_TYPE_RECONVERT = "C201";
    public static final String BUSINESS_TYPE_CONVERT = "D201";
    public static final String BUSINESS_TYPE_DSPT = "E100";

    /**
     * 反洗钱错误码
     */
    public static final String LSFK43_ACCOUNT_ERROR = "CB520012";
    public static final String LSFK43_ACCOUNT_AUTH_ERROR = "CB1010";


}
