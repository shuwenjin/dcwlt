package com.dcits.dcwlt.pay.online.exception;


import com.dcits.dcwlt.common.pay.exception.TransError;

/**
 * 五羊支付错误码类
 * 样例：ECNY+2位标识位+4位数字
 * 注：000000 标识交易成功。
 * （1）兑出：标识位为“P1”
 * （2）兑回：标识位为“R1”
 * （3）汇款兑出：标识位为“P2”
 * （4）签约：标识位为“S2”
 * （5）解约：标识位为“S2”
 * （6）查询：标识位为“Q1”
 * （7）差错：标识位为“E1”
 * （8）公共管理：标识位为“C1”
 * （9）系统错误码：标识位“S0”
 */
public class EcnyTransError extends TransError {




    public EcnyTransError(String errorCode, String errorMsg) {
        super(errorCode, errorMsg);
    }

    public static final EcnyTransError SUCCESS = new EcnyTransError("000000", "交易成功");

    /**
     * 系统错误码
     */
    public static final EcnyTransError INPARAMS_INVALID               = new EcnyTransError("ECNYS01001", "请求报文格式有误");
    public static final EcnyTransError PARAM_NOT_INIT_ERROR           = new EcnyTransError("ECNYS01002", "参数未初始化");
    public static final EcnyTransError DUPLICATE_KEY_ERROR            = new EcnyTransError("ECNYS01003", "重复交易");
    public static final EcnyTransError OTHER_TECH_ERROR               = new EcnyTransError("ECNYS09999", "其他错误");
    public static final EcnyTransError DATABASE_ERROR                 = new EcnyTransError("ECNYS01004", "数据库错误");
    public static final EcnyTransError GATEWAY_REQUEST_ERROR          = new EcnyTransError("ECNYS01005", "网关请求错误");
    public static final EcnyTransError PARAMS_INVALID                 = new EcnyTransError("ECNYS01006", "入参非法");
    public static final EcnyTransError PARSE_RSP_ERROR                = new EcnyTransError("ECNYS01007","解析响应报文错误");



    /**
     * 协议签约
     */
    public static final EcnyTransError SIGN_INFO_UN_FOUND             = new EcnyTransError("ECNYS20001", "协议信息不存在");
    public static final EcnyTransError UN_SUPPORTED_WALLET_TP         = new EcnyTransError("ECNYS20002","暂不支持该钱包类型");
    public static final EcnyTransError UN_SUPPORTED_MANAGEMENT_TP     = new EcnyTransError("ECNYS20003","账户挂接管理暂不支持该管理类型");
    public static final EcnyTransError TRADE_NOT_FOUND                = new EcnyTransError("ECNYS20004","原身份认证交易不存在");
    public static final EcnyTransError ORI_TRADE_INVALID              = new EcnyTransError("ECNYS20005","原身份认证交易非成功状态");
    public static final EcnyTransError ORIGIN_SIGN_INFO_NOT_FOUND     = new EcnyTransError("ECNYS20006","原签约交易不存在");
    public static final EcnyTransError SEND_SMS_ERROR                 = new EcnyTransError("ECNYS20007","短信发送失败");
    public static final EcnyTransError AUTH_MSG_ERROR                 = new EcnyTransError("ECNYS20008","短信验证码错误");
    public static final EcnyTransError AUTH_MSG_TIMEOUT               = new EcnyTransError("ECNYS20009","短信验证码超时");
    public static final EcnyTransError UN_SUPPORTED_BIZ_TP            = new EcnyTransError("ECNYS20010","暂不支持该业务");
    public static final EcnyTransError INVALID_ACCT_STATUS            = new EcnyTransError("ECNYS20011","客户状态异常");
    public static final EcnyTransError UN_SUPPORTED_ACC_TP            = new EcnyTransError("ECNYS20012","不支持当前客户类型");
    public static final EcnyTransError UN_SUPPORTED_ACC_CLASS_TP      = new EcnyTransError("ECNYS20013","不支持当前账户分类");
    public static final EcnyTransError INVALID_CUSTOMER_STATUS        = new EcnyTransError("ECNYS20014","客户状态异常");
    public static final EcnyTransError FROZEN_ACCOUNT                 = new EcnyTransError("ECNYS20015","账户已冻结");
    public static final EcnyTransError SLEEP_ACCOUNT                  = new EcnyTransError("ECNYS20016","该账户为睡眠户");
    public static final EcnyTransError PASSWORD_NEED_TO_RESET         = new EcnyTransError("ECNYS20017","账户密码待重置");
    public static final EcnyTransError PASSWORD_BLOCKED               = new EcnyTransError("ECNYS20018","账户密码锁定");
    public static final EcnyTransError REPORT_LOSS                    = new EcnyTransError("ECNYS20019","账户处于挂失状态");
    public static final EcnyTransError CANCELLED_ACCOUNT              = new EcnyTransError("ECNYS20020","账户处于销户状态");
    public static final EcnyTransError FORBIDDEN_OR_FROZEN            = new EcnyTransError("ECNYS20021","账户暂禁或冻结");
    public static final EcnyTransError ACCT_CERT_EXPIRED              = new EcnyTransError("ECNYS20022","账户证件到期");
    public static final EcnyTransError PTC_ID_EMPTY                   = new EcnyTransError("ECNYS20023","解约时挂接协议号不能为空");
    public static final EcnyTransError ACC_AUTH_ERROR                 = new EcnyTransError("ECNYS20024","身份认证失败");
    public static final EcnyTransError SIGN_ECIF_FAIL                 = new EcnyTransError("ECNYS20025","查询客户信息返回失败");
    public static final EcnyTransError SIGN_ECIF_RESULT_EMPTY         = new EcnyTransError("ECNYS20026","没有查询到符合条件的客户信息");
    public static final EcnyTransError SIGN_ECIF_NETWORK_FAIL         = new EcnyTransError("ECNYS20027","网络错误");
    public static final EcnyTransError SIGN_ECIF_LACK                 = new EcnyTransError("ECNYS20028","查无客户信息");
    public static final EcnyTransError SIGN_BANK_CORE_FAIL            = new EcnyTransError("ECNYS20029","卡核心查询账户失败" );
    public static final EcnyTransError SIGN_INNER_ACCT_FAIL           = new EcnyTransError("ECNYS20030","内部户不支持签约" );
    public static final EcnyTransError SIGN_CERT_EXPIRED 			  = new EcnyTransError("ECNYS20031","证件过期超90天");
    public static final EcnyTransError SIGN_CON_TEL_SHARED 		      = new EcnyTransError("ECNYS20032","联络手机号码共用");
    public static final EcnyTransError SIGN_SAFE_TEL_SHARED           = new EcnyTransError("ECNYS20033","安全手机号码共用");
    public static final EcnyTransError SIGN_CERT_TYPE_LACK            = new EcnyTransError("ECNYS20034","证件类型缺失");
    public static final EcnyTransError SIGN_CERT_NO_LACK              = new EcnyTransError("ECNYS20035","证件号码缺失");
    public static final EcnyTransError SIGN_CERT_NAME_LACK            = new EcnyTransError("ECNYS20036","证件名称缺失");
    public static final EcnyTransError SIGN_CERT_EXPIRY_DATE_LACK     = new EcnyTransError("ECNYS20037","证件有效期缺失");
    public static final EcnyTransError SIGN_NATIONALITY_LACK          = new EcnyTransError("ECNYS20038","国籍缺失");
    public static final EcnyTransError SIGN_SEX_LACK                  = new EcnyTransError("ECNYS20039","性别缺失");
    public static final EcnyTransError SIGN_PROFESSION_LACK           = new EcnyTransError("ECNYS20040","职业缺失");
    public static final EcnyTransError SIGN_CORE_TEL_LACK             = new EcnyTransError("ECNYS20041","国内核心电话缺失");
    public static final EcnyTransError SIGN_CORE_ADDRESS_LACK         = new EcnyTransError("ECNYS20042","国内核心地址缺失");
    public static final EcnyTransError UN_MATCHED_SIGN_INFO           = new EcnyTransError("ECNYS20043","客户信息不一致");
    public static final EcnyTransError MSG_SND_CD_EMPTY               = new EcnyTransError("ECNYS20044","身份确认时动态关联码不能为空");
    public static final EcnyTransError AUTH_MSG_EMPTY                 = new EcnyTransError("ECNYS20045","身份确认时短信验证码不能为空");
    public static final EcnyTransError INSTD_PTY_ERROR                = new EcnyTransError("ECNYS20046","接收机构错误");
    public static final EcnyTransError IDTP_ERROR                     = new EcnyTransError("ECNYS20047","证件类型枚举值不包含");
    public static final EcnyTransError UN_SUPPORTED_CARD_TYPE         = new EcnyTransError("ECNYS20048", "不支持的卡类型");





    /**
     * 交易状态查询
     *
     */
    public static final EcnyTransError C_WALLET_ID_EMPTY              = new EcnyTransError("ECNYQ10001", "原收款人钱包ID为空");
    public static final EcnyTransError D_WALLET_ID_EMPTY              = new EcnyTransError("ECNYQ10002", "原付款人钱包ID为空");
    public static final EcnyTransError PAY_TRANS_DTL_INFO_NOT_EXIST   = new EcnyTransError("ECNYQ10003", "交易不存在");


    /**
     * 协议解约
     */
    public static final EcnyTransError SIGN_STATUS_UNVALID            = new EcnyTransError("ECNYS40001", "协议已失效");

    /**
     * 兑出
     */
    public static final EcnyTransError PAYER_INFO_UNEQUAL             = new EcnyTransError("ECNYP10001", "付款人信息不匹配");
    public static final EcnyTransError WALLET_TYPE_ERROR              = new EcnyTransError("ECNYP10003", "钱包类型不支持");
    public static final EcnyTransError WALLET_LEVEL_ERROR             = new EcnyTransError("ECNYP10004", "钱包等级不支持");

    /**
     * 汇款兑出
     */
    public static final EcnyTransError AMOUNT_LIMIT_OUT               = new EcnyTransError("ECNYP20001", "交易金额超过限额");
    public static final EcnyTransError PARTY_INFO_UNFOUND             = new EcnyTransError("ECNYP20002", "获取机构信息失败");
    public static final EcnyTransError PARTY_INSTG_ERROR              = new EcnyTransError("ECNYP20003", "发起机构传输有误");
    public static final EcnyTransError PARTY_INSTG_STATUS_UNSUPPORT   = new EcnyTransError("ECNYP20004", "发起机构状态不支持");
    public static final EcnyTransError PARTY_INSTD_STATUS_UNSUPPORT   = new EcnyTransError("ECNYP20005", "接收机构状态不支持");
    public static final EcnyTransError PARTY_INSTD_POWER_UNSUPPORT    = new EcnyTransError("ECNYP20006", "接收机构权限不足");
    public static final EcnyTransError VERIFY_FAILED                  = new EcnyTransError("ECNYP20007", "验签失败");
    public static final EcnyTransError DECRYPT_FAILED                 = new EcnyTransError("ECNYP20008", "解密失败");
    public static final EcnyTransError SEND_DCEP_ERROR                = new EcnyTransError("ECNYP20009", "发送互联互通平台异常");
    public static final EcnyTransError ACCT_TYPE_TRANS_ERROR          = new EcnyTransError("ECNYP20010", "账户类型转换异常");
    /**
     * 差错贷记调账
     */
    public static final EcnyTransError OLD_PAY_INFO_UNFOUND             = new EcnyTransError("ECNYE10001", "原交易不存在");
    public static final EcnyTransError UPDATE_INFO_ERROR                = new EcnyTransError("ECNYE10002", "差错贷记调账失败");
    public static final EcnyTransError NO_DSPT_ALLOW                    = new EcnyTransError("ECNYE10003", "当前状态不允许此操作");
    public static final EcnyTransError ORGAN_STATUS_ERROR               = new EcnyTransError("ECNYE10004", "机构状态异常");
    public static final EcnyTransError ORGAN_POWER_ERROR                = new EcnyTransError("ECNYE10005", "机构权限不足");
    public static final EcnyTransError ECNY_DSPT_INSTG_ERROR            = new EcnyTransError("ECNYE10006", "发起机构传输有误");
    public static final EcnyTransError PAY_TIME_ERROR                   = new EcnyTransError("ECNYE10007", "交易超时，请稍候再查询该交易");
    public static final EcnyTransError PAY_TIME_OUT                     = new EcnyTransError("ECNYE10008", "核心超时或异常");
    public static final EcnyTransError ECNY_DSPT_RESPOSE_ERROR          = new EcnyTransError("ECNYE10009", "互联互通响应失败");
    public static final EcnyTransError ECNY_DSPT_BIZ_ERROR              = new EcnyTransError("ECNYE10010", "差错业务类型错误");
    public static final EcnyTransError ECNY_DSPT_CD_ERROR               = new EcnyTransError("ECNYE10011", "差错业务种类错误");
    public static final EcnyTransError ECNY_DSPT_ORGI_ERROR             = new EcnyTransError("ECNYE10012", "原交易已经做过差错，请勿重复提交");
    public static final EcnyTransError OPER_TYPE_ERROR                  = new EcnyTransError("ECNYE10012", "差错类型错误");
    public static final EcnyTransError CORE_NOT_ABEND                   = new EcnyTransError("ECNYE10013", "核心状态非异常");
    public static final EcnyTransError ECNY_BUSINESS_TYPE_INVALID       = new EcnyTransError("ECNYE10014", "业务类型与业务种类不匹配");
    public static final EcnyTransError NOT_SUPPORTED_REVERSED           = new EcnyTransError("ECNYE10014", "该交易不支持冲正");
    public static final EcnyTransError NOT_SUPPORTED_RECREDIT           = new EcnyTransError("ECNYE10015", "该交易不支持补入账");
    public static final EcnyTransError CREDIT_SUCC_ERROR                = new EcnyTransError("ECNYE10016", "该笔交易核心入账成功，但平台失败");
    public static final EcnyTransError NOT_SURE_END_STATUS              = new EcnyTransError("ECNYE10017", "该笔交易无法确定人行终态");
    public static final EcnyTransError MSG_TYPE_ERROR                   = new EcnyTransError("ECNYE10018", "该报文类型不需要进行差错处理");


    /**
     *公共管理：标识位为“C1”
     */
    public static final EcnyTransError ECNY_TRANS_BODY_ERROR               = new EcnyTransError("ECNYC13001", "开放平台响应结果无body");
    public static final EcnyTransError ECNY_CHECK_HEAD_ERROR               = new EcnyTransError("ECNYC13002", "业务检查HEAD不通过");
    public static final EcnyTransError ECNY_OTHER_ERROR                    = new EcnyTransError("ECNYC13003", "其他类型错误");
    public static final EcnyTransError ECNY_PARAM_ERROR                    = new EcnyTransError("ECNYC13004", "请求参数格式错误");
    public static final EcnyTransError ECNY_NOPARTY_ERROR                  = new EcnyTransError("ECNYC13005", "广发银行机构不在库，请先初始化数据");
    public static final EcnyTransError ECNY_CHNG_NUM_ERROR                 = new EcnyTransError("ECNYC13006", "变更期数低于已有变更期数");
    public static final EcnyTransError ECNY_PARTY_TOBE_EFFECTIVE_ERROR     = new EcnyTransError("ECNYC13007", "待生效机构转生效机构出现错误");
    public static final EcnyTransError ECNY_SEND_REQUEST_ERROR             = new EcnyTransError("ECNYC13008", "调用开放平台服务失败");
    public static final EcnyTransError ECNY_PARSE_RESULT_ERROR             = new EcnyTransError("ECNYC13009", "无法解析开放平台响应结果");
    public static final EcnyTransError ECNY_RETCODE_RESULT_ERROR           = new EcnyTransError("ECNYC13010", "开放平台响应retCode非000000");
    public static final EcnyTransError ECNY_RESULT_ERROR                   = new EcnyTransError("ECNYC13011", "互联互通响应结果失败");
    public static final EcnyTransError ECNY_UPDATE_RESULT_STATUS_ERROR     = new EcnyTransError("ECNYC13012", "业务操作成功，更新状态失败");
    public static final EcnyTransError ECNY_CHECK_DCEPHEAD_ERROR           = new EcnyTransError("ECNYC13013", "业务检查DCEPHEAD不通过");
    public static final EcnyTransError ECNY_AHTUINFO_TOBE_EFFECTIVE_ERROR  = new EcnyTransError("ECNYC13014", "待生效机构转生效机构出现错误");
    public static final EcnyTransError ECNY_REPEAT_LOGIN_ERROR             = new EcnyTransError("ECNYC13015", "机构状态变更错误，当前机构状态与操作类型不匹配：当前状态ST02，操作类型OT00");
    public static final EcnyTransError ECNY_REPEAT_LOGOUT_ERROR            = new EcnyTransError("ECNYC13016", "机构状态变更错误，当前机构状态与操作类型不匹配：当前状态ST03，操作类型OT01");
    public static final EcnyTransError ECNY_LOGIN_TPEY_MISMATCH_ERROR      = new EcnyTransError("ECNYC13019", "登录登出操作类型不匹配，无法执行操作");

    /**
     * 重发申请
     */
    public static final EcnyTransError GET_RSP_INFO_ERROR                  = new EcnyTransError("ECNYS40001","获取互联互通响应信息异常");

    /**
     * 兑回
     */
    public static final EcnyTransError BUSINESS_TYPE_INVALID              = new EcnyTransError("ECNYR10001", "业务类型与业务种类不匹配");
    public static final EcnyTransError PARTY_STATUS_ERROR                 = new EcnyTransError("ECNYR10002", "机构状态异常");
    public static final EcnyTransError PARTY_POWER_ERROR                  = new EcnyTransError("ECNYR10003", "机构权限不足");
    public static final EcnyTransError PARTY_INSTD_ERROR                  = new EcnyTransError("ECNYR10004", "接收机构传输有误");
    public static final EcnyTransError TRANSACTION_FUNDS_SOURCE_EMPTY     = new EcnyTransError("ECNYR10005", "交易资金来源不能为空");

    /**
     * 自由格式报文
     */
    public static final EcnyTransError SEND_PARTY_AUTH_ERROR              =  new EcnyTransError("ECNYR10011","发起机构无发送权限");
    public static final EcnyTransError RECV_PARTY_ERROR                   =  new EcnyTransError("ECNYR10012","接收机构为空");
    public static final EcnyTransError RECV_PARTY_STATUS_ERROR            =  new EcnyTransError("ECNYR10013","接收机构状态异常");
    public static final EcnyTransError RECV_PARTY_AUTH_ERROR              =  new EcnyTransError("ECNYR10014","接收机构无接收权限");
    public static final EcnyTransError CONTEXT_ILLEGAL_ERROR              =  new EcnyTransError("ECNYR10015","msgContext为空或超出限制");
    public static final EcnyTransError INSERT_DATABASE_ERROR              =  new EcnyTransError("ECNYR10016","数据入库失败");
    public static final EcnyTransError RESPOSE_ERROR                      =  new EcnyTransError("ECNYR10017","互联互通响应失败");

    /**
     * 重发申请
     */
    public static final EcnyTransError BATCH_ID_AND_CHECK_DATE_EMPTY     =  new EcnyTransError("ECNYR10001","交易批次号和对账日期不能同时为空");
    public static final EcnyTransError CLEAR_DATE_EMPTY                  =  new EcnyTransError("ECNYR10002","清算日期不能为空");
    public static final EcnyTransError DATE_FORMAT_ERROR                 =  new EcnyTransError("ECNYR10003", "日期格式错误");


    /**
     * 交易明细查询
     */
    public static final EcnyTransError CALL_COP_ERROR                    =  new EcnyTransError("ECNYQ10021", "调用金融开放平台异常");
    public static final EcnyTransError CALL_DCEP_ERROR                   =  new EcnyTransError("ECNYQ10021", "调用互联互通异常");
    public static final EcnyTransError BADRSP_ERROR                      =  new EcnyTransError("ECNYQ10022", "响应报文非法");
}
