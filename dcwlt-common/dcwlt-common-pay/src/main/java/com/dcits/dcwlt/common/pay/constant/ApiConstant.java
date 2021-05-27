package com.dcits.dcwlt.common.pay.constant;

/**
 * 接口名常量
 */
public class ApiConstant {
    public static final String DCEP_SERVICE_NAME       = "/online";

    /**行内系统接口名**/

    //自由格式报文发起服务
    public static final String FREEFRMT_SERVICE_NAME = "pymtFrdmFmtMsgSnd";

    //登录登出报文发起服务
    public static final String LOGINOUT_SERVICE_NAME = "loginoutFmtMsgSnd";

    /**行内系统接口名**/
    //重发申请报文发起服务
    public static final String RESENDAPY_SERVICE_NAME = "pymtRqstNwlySnd";

    //差错贷记调账服务化接口
    public static final String DSPT_SERVICE_NAME = "dcepPymtWrngAcctDeal";

    //差错贷记调账服务化接口
    public static final String PAYCONVERT_SERVICE_NAME = "pymtRmtOtptRqstIntt";

    //发送钱柜入库出库报文
    public static final String SEND_CASHBOX = "sendCashbox";



}
