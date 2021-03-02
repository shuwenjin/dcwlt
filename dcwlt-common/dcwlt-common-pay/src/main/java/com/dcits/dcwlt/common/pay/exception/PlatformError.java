package com.dcits.dcwlt.common.pay.exception;

/**
 * @Time 2020/9/17 13:55
 * @Version 1.0
 * Description:平台错误码类
 */
public class PlatformError {
    public static PlatformError SUCCESS                     = new PlatformError("00000000", "成功");
    public static PlatformError SYSTEM_ERROR                = new PlatformError("BE999999", "系统异常");
    public static PlatformError OTHER_BUSI_ERROR            = new PlatformError("BE999998", "其他业务异常");
    public static PlatformError DATABASE_EXECUTE_ERROR      = new PlatformError("BE000001", "数据库异常");
    public static PlatformError LOADCLASS_ERROR             = new PlatformError("BE000002", "加载class异常");
    public static PlatformError SM3_ENCRYPT_ERROR           = new PlatformError("BE000003", "获取SM3散列值失败");
    public static PlatformError SOCKET_CONN_ERROR           = new PlatformError("BE000004", "socket连接异常");
    public static PlatformError SOCKET_SEND_ERROR           = new PlatformError("BE000005", "socket发送异常");
    public static PlatformError SOCKET_RECEIVE_ERROR        = new PlatformError("BE000006", "socket接收异常");
    public static PlatformError AMOUNT_ERROR                = new PlatformError("BE000007", "金额非法");
    public static PlatformError IO_CHECK_ERROR              = new PlatformError("BE000008", "IO校验异常");
    public static PlatformError EMPTY_RECORDS               = new PlatformError("BE000009", "无此记录");
    public static PlatformError INPARAMS_INVALID            = new PlatformError("BE000010", "参数配置有误");
    public static PlatformError COMM_ABEND                  = new PlatformError("BE000011", "通讯异常");



    private String errorCode;
    private String errorMsg;

    PlatformError(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
