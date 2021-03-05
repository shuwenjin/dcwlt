package com.dcits.dcwlt.pay.online.base;

public class Constant {
    public static final String SM3 = "SM3";

    public static final String ENCODE_UFT8 = "utf-8";

    /**
     * 分页常量
     */
    public static final String QUERYPAGEFLAG_FIRST 								=  "0"; //首页
    public static final String QUERYPAGEFLAG_LAST 								=  "3"; //尾页
    public static final String QUERYPAGEFLAG_UP 								=  "1"; //上翻
    public static final String QUERYPAGEFLAG_DOWN 							    =  "2"; //下翻

    public static final String JUDGE_YES 								        =  "Y"; //yes
    public static final String JUDGE_NO 								        =  "N"; //no


    /**
     * 币种
     */
    public static final String CURRENCY_CODE_156 = "156";    //人民币

    public static final String ROOT_TAG = "root"; 

    /**
     * 服务化变量
     */
    public static final String SERVER_CODE = "srvcCode";         //服务代码
    public static final String PROVIDER_SYS_ID = "prvdSysId";     //提供方系统标识
    public static final String CONSUMER_SYS_ID = "cnsmrSysId";     //消费方系统标识
    public static final String PROVIDER_ADDR = "prvdAddr";         //提供方目标地址
    public static final String SERVER_SUCC_RSPCODE = "000000";             //服务化成功响应码

    public static final String HEAD_TAG = "head";
    public static final String BODY_TAG = "body";

    /**
     * 核心报文头
     */
    public static final String REQ_TL_ID = "AAAE0443";
    public static final String REQ_TR_BRANCH = "199999";
    public static final String REQ_TR_BANK = "001";
    public static final String REQ_CHNL = "ECN";
    public static final String REQ_TERMID = "ECN";

    /**
     * 校验标识
     */
    public static final String CHECK_FLAG_Y = "Y";                                             //校验标识,Y为校验
    public static final String CHECK_FLAG_N = "N";                                             //校验标识，N为不校验

    /**
     * 核心接口服务码
     */
    public static final String SRVCCODE_358040 = "prod.dpst.n.acctAndDebtCardIdntVrfyAndInfoQry"; // 358404-账户身份校验及信息查询接口服务码
    public static final String SRVCCODE_351100 = "bsns.bank.f.oneDbOneCrCmprhAcctng"; // 351100- 一借一贷综合记账服务码
    public static final String SRVCCODE_996666 = "bsns.bank.c.coreTxnStQry"; // 996666- 查询核心交易状态服务码
    public static final String SRVCCODE_998889 = "bsns.bank.f.coreTxnChnlRvrs"; // 998889- 上核心冲正服务码
    public static final String SRVCCODE_267550 = "prod.dpst.f.curntAcctFrz"; //267550 - 圈存
    public static final String SRVCCODE_267510 = "prod.dpst.f.curntAcctUnfrz"; //267510 - 解冻
    public static final String SRVCCODE_351140 = "prod.dpst.f.oneDbAndOneCrCmprhAcctng"; //351140- 一借一贷综合记账（支持控制记账时间）
    public static final String SRVCCODE_991198 = ""; //991198- 核心费用工厂单独收费

    /**
     * 核心接口码
     */
    public static final String BANKCORE_QUERYACC_CODE = "358040";
    public static final String BANKCORE_BANKREV_CODE = "998889";
    public static final String BANKCORE_QUERYSTATUS_CODE = "996666";
    public static final String BANKCORE_CHARGE = "991198";
    public static final String BANKCORE_CRCMPRHACCTNG_CODE = "351100";
    public static final String BANKCORE_FREEZE_CODE = "267550";
    public static final String BANKCORE_THAW_CODE = "267510";

    /**
     * 记账方式
     */
    public static final String COREACCMODE_ACCT = "A"; // 账号
    public static final String COREACCMODE_CASH = "C"; // 现金
    public static final String COREACCMODE_PROD = "E"; // 产品事件
    public static final String COREACCMODE_AUTO = "G"; // 核心根据代码自动取内部户
    
    public static final String COREACCBRNOTYPE_TABLE = "0"; // 从表里取核算机构
    public static final String COREACCBRNOTYPE_CHANL = "1"; // 渠道上送或从核心查询
    
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
     * 系统标识
     */
    public static final String ECNY_SYS_ID = "ECNY";    //五羊支付子应用
    public static final String CORE_SYS_ID = "CRPM";    //银行核心系统
    public static final String DCEP_SYS_ID = "DCEP";    //互联互通子应用
    public static final String CARD_CORE_SYS_ID = "CCS";        //信用卡核心子应用
    public static final String EXGS_SYS_ID = "EXGS";    //卡交换系统
    public static final String SMS_SYS_ID = "SMS";        //短信系统
    public static final String UPPS_SYS_ID = "UPPS";        //统一支付
    public static final String COP_SYS_ID = "COPS";        //金融开放平台
    public static final String CUSS_SYS_ID = "CUSS";        //银联清算平台

    public static final String MASTERBANK                   = "173001"; //总行的行所号
    public static final String SZBANK                       = "102087"; //总行的行所号

    public static final String CONSTANT_ERRORCODE = "errorCode";
    public static final String CONSTANT_ERRORMSG = "errorMsg";

    public static final String EVENT_SUCC = "00000000";

    /**
     * 系统标识
     */
    public static final String DEFAULT_BANKSYSID                   = "IN0000";  //核心系统标识
    public static final String CRFAULT_BANKSYSID                   = "IN0002"; //贷记卡核心系统标识
    public static final String E_BANKSYSID                         = "FVA";  //E帐通核心系统标识

    /**
     * 反交易标识
     */
    public static final String REVTRANFLAG_POSITIVE                = "1";       //正交易
    public static final String REVTRANFLAG_REVERSE                 = "0";       //反交易

    public static final String BANKTRXTYPE_TRANSFER                = "00";      //双边转账
    public static final String BANKTRXTYPE_DEBIT                   = "01";      //实时扣款
    public static final String BANKTRXTYPE_CREDIT                  = "02";      //实时入账
    public static final String BANKTRXTYPE_BATCH_DEBIT             = "03";      //批量扣款
    public static final String BANKTRXTYPE_BATCH_CREDIT            = "04";      //批量入账
    public static final String BANKTRXTYPE_BATCH_CHECK             = "05";      //对账文件

    /**
     * 核心记账类型
     */
    public static final String BANKCORE_CREDIT = "BANKCREDIT";       //核心收款
    public static final String BANKCORE_DEBIT = "BANKDEBIT";       //核心付款

    /**
     * 核心服务方式
     */
    public static final String P_BANKCORE_CREDIT = "P_BANKCREDIT";       //核心收款
    public static final String P_BANKCORE_DEBIT = "P_BANKDEBIT";       //核心付款

    /**
     * 借方介质标识
     * 0-卡 1-折 2-捷算通 暂时写死
     */
    public static final String PAYER_MEDIA_TYPE_CARD = "0";
    public static final String PAYER_MEDIA_TYPE_BANKBOOK = "1";

    /**
     * 报文标识
     */
    public static final String DCEP_221 = "dcep.221.001.01";         //兑回业务请求报文
    public static final String DCEP_222 = "dcep.222.001.01";         //兑回业务应答报文
    public static final String DCEP_225 = "dcep.225.001.01";         //兑出业务请求报文
    public static final String DCEP_226 = "dcep.226.001.01";         //兑出业务应答报文
    public static final String DCEP_227 = "dcep.227.001.01";         //汇款兑出请求报文
    public static final String DCEP_228 = "dcep.228.001.01";         //汇款兑出应答报文
    public static final String DCEP_401 = "dcep.401.001.01";         //自由格式报文
    public static final String DCEP_411 = "dcep.411.001.01";         //交易状态查询请求报文
    public static final String DCEP_412 = "dcep.412.001.01";         //交易状态查询应答报文
    public static final String DCEP_417 = "dcep.417.001.01";         //交易明细查询应答报文
    public static final String DCEP_418 = "dcep.418.001.01";         //交易明细查询应答报文
    public static final String DCEP_433 = "dcep.433.001.01";         //银行账户挂接管理请求报文
    public static final String DCEP_434 = "dcep.434.001.01";         //银行账户挂接管理应答报文
    public static final String DCEP_711 = "dcep.711.001.01";         //机构对账汇总核对报文
    public static final String DCEP_713 = "dcep.713.001.01";         //资金调整汇总核对报文
    public static final String DCEP_801 = "dcep.801.001.01";         //差错贷记调账请求报文
    public static final String DCEP_802 = "dcep.802.001.01";         //差错贷记调账应答报文
    public static final String DCEP_900 = "dcep.900.001.01";         //通用处理确认报文
    public static final String DCEP_902 = "dcep.902.001.01";         //通信级确认报文
    public static final String DCEP_909 = "dcep.909.001.01";         //交易终态通知报文
    public static final String DCEP_911 = "dcep.911.001.01";         //报文丢弃通知报文
    public static final String DCEP_915 = "dcep.915.001.01";         //业务权限变更通知报文
    public static final String DCEP_917 = "dcep.917.001.01";         //机构变更通知报文
    public static final String DCEP_920 = "dcep.920.001.01";         //重发申请报文
    public static final String DCEP_931 = "dcep.931.001.01";         //机构状态变更通知报文
    public static final String DCEP_933 = "dcep.933.001.01";         //登录、退出请求报文
    public static final String DCEP_934 = "dcep.934.001.01";         //登录、退出应答报文

}
