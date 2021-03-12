package com.dcits.dcwlt.common.pay.constant;

import java.util.concurrent.ExecutorService;

public class BusiConst {

    /**
     * 金融机构编码
     */
    public static final String CGB_FINANCIAL_INSTITUTION_CD = "C1030644021075";    //广发银行

    /**
     * 收付标识, 我行作为收款行
     */
    public static final String IDENTIFICATION_PAYEE = "PAYEE";
    /**
     * 收付标识, 我行作为付款行
     */
    public static final String IDENTIFICATION_PAYER = "PAYER";

    /**
     * 线程池配置
     */
    public static final int CORE_THREAD_NUM = 4;
    public static final int MAX_THREAD_NUM = 4;
    public static final int MAX_QUEUE_SIZE = 100;
    public static final String THREAD_NAME = "gwf008_thread";
    /**
     * 交易处理调度池
     */
    public static final ExecutorService COMMON_EXECUTOR = null;

    /**
     * 交易码
     */
    public static final String SERVER_CODE_GWF008 = "bsns.bank.n.rcnclnFileDwldAply";         //文件下载申请请求码
    public static final String SERVICE_CODE_GWF008 = "bsns.bank.n.rcnclnFileDwldAply";

    /**
     * 开放平台对账文件下载状态
     */
    public static final String DTL_MESSAGE_DOWNLOAD_SUCCESS = "01";
    public static final String DTL_MESSAGE_DOWNLOAD_FAIL = "02";

    /**
     * 开放平台文件下载配置项
     */
    public static final String FILE_SERVICE_MERID = "file.service.merId";
    public static final String FILE_SERVICE_APPID = "file.service.appid";
    public static final String FILE_SERVICE_SRCMERID = "file.service.srcMerid";
    public static final String FILE_SERVICE_CHANNEL = "file.service.channel";
    public static final String FILE_SERVICE_FILETYPE = "file.service.fileType";

    /**
     * 行内系统标识
     */
    public static final String ECNY_SYS_ID = "ECNY";       //五羊支付子应用
    public static final String COP_SYS_ID = "COP";       //金融开放平台

    /**
     * 业务状态
     */
    public static final String TRXSTATUS_SUCCESS = "1";       //成功
    public static final String TRXSTATUS_FAILED = "0";       //失败
    public static final String TRXSTATUS_ABEND = "2";       //异常
    public static final String TRXSTATUS_REVERSED = "3";       //已冲正
    public static final String TRXSTATUS_RETURNED = "5";       //已退回
    public static final String TRXSTATUS_RECIPE = "7";       //已收妥
    public static final String TRXSTATUS_UNDO = "8";       //已撤销
    public static final String TRXSTATUS_INIT = "9";       //未处理

    /**
     * 操作步骤
     */
    public static final String OPERSTEP_INIT = "INIT";           //入库
    public static final String OPERSTEP_DRDT = "DRDT";           //扣账
    public static final String OPERSTEP_CRDT = "CRDT";           //入账
    public static final String OPERSTEP_REVR = "REVR";           //冲正


    /**
     * 联机数据库数据源标识
     */
    public static final String DATASOURCE_ONLINE = "ecny_onlie";                       //联机库
    public static final String DATASOURCE_ONLINE_READONLY = "ecny_onlie_readonly";     //联机库


    public static final String COP_RECEIVE_SUCCESS_CODE = "000000";         //开放平台响应的成功码

    /**
     * 查询批量数据最大条数
     */
    public static final int MAX_COUNT = 30;
    /**
     * 查询首页
     */
    public static final int QUERY_PAGE_MODE_FRIST = 0;
    /**
     * 查询上一页
     */
    public static final int QUERY_PAGE_MODE_UP = 1;
    /**
     * 查询下一页
     */
    public static final int QUERY_PAGE_MODE_DOWN = 2;
}
