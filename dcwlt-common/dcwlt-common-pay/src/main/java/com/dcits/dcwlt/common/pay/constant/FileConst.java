package com.dcits.dcwlt.common.pay.constant;

public class FileConst {
    //运行状态
    public static final int RUNNINGSTATRTUS_INIT = 0;//扫描服务闲置中
    public static final int RUNNINGSTATRTUS_RUNNING = 1;//扫描服务运行中
    //路径配置转义字段
    public static final String REX = "%\\(|\\)s"; //format配置类型
    //文件处理步骤
    public static final String FILES_STEP_INIT = "INIT"; //初始化
    public static final String FILES_STEP_MOVE = "MOVE"; //移动
    public static final String FILES_STEP_DEAL = "DEAL"; //处理
    public static final String FILES_STEP_SEND = "SEND"; //发送
    //文件处理状态
    public static final String FILES_STATUS_INIT = "INIT"; //初始化
    public static final String FILES_STATUS_SUCC = "SUCC"; //成功
    public static final String FILES_STATUS_FAIL = "FAIL"; //失败
    public static final String FILES_STATUS_ABEND = "ABEND"; //异常、处理中
    public static final String FILES_STATUS_UNFOUND_BUSICONFIG = "UN_FOUND_CONFIG"; //异常、处理中
    //文件结束标志
    public static final String FILE_TRAN_FINISH_ENDTAG = ".end"; // end文件标志
    //文件目录配置
    public static final String CONFIG_PROPERTY_SCANINGPATH = "file.service.receivePath"; //文件接收目录
    public static final String CONFIG_PROPERTY_SAVEBAKPATH = "file.service.backupPath"; //扫描备份目录
    public static final String CONFIG_PROPERTY_FROMATFAILPATH = "file.service.decodeFailPath"; //不能识别的文件名
    public static final String CONFIG_PROPERTY_REPEATFILEPATH = "file.service.duplicatePath"; //重复的文件名
    public static final String CONFIG_PROPERTY_APPDATASAVEPATH = "file.service.localSavePath"; //本地处理目录-接收
    public static final String CONFIG_PROPERTY_APPDATASENDPATH = "file.service.localSendPath"; //本体处理目录-发送
    public static final String CONFIG_PROPERTY_SENDBAKPATH = "file.service.sendPath"; //发送文件目录
    //文服端口配置
    public static final String FTS_IP = "file.service.UFSM.ip"; //文服绑定IP
    public static final String FTS_PORT = "file.service.UFSM.port"; //文服绑定端口
    //文服超时配置
    public static final String CONFIG_PROPERTY_CONNTIMEOUT = "file.service.UFSM.connectTimeOut"; //文件连接超时
    public static final String CONFIG_PROPERTY_TRANSTIMEOUT = "file.service.UFSM.transTimeOut"; //文件连接超时
    //FTSTaskClient
    public static final String FTSCLIENT_SUCC = "0000";
    public static final String FTSCLIENT_SUCC_MSG = "成功";
    //文件获取状态
    public static final int FILES_UFS_MODE_PUSH = 0; // 推
    public static final int FILES_UFS_MODE_PULL = 1; // 拉 未实现
    //文件获取状态
    public static final String FILES_SHELL_STATUS_SUCC = "SUCC"; // 成功
    public static final String FILES_SHELL_STATUS_FAIL = "FAIL"; // 失败
}
