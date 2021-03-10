package com.dcits.dcwlt.common.pay.exception;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author maozewu
 * 批量构架系统中所有的错误码定义
 */
public enum SettleTaskErrorEnum {
    BC0000("ENCYBC0000", "执行成功"),
    BC0001("ENCYBC0001", "任务组状态已完成，无法执行任务"),
    BC0002("ENCYBC0002", "更新任务组状态失败"),
    BC0003("ENCYBC0003", "没有该任务组配置"),
    BC0004("ENCYBC0004", "没有该任务列表配置"),
    BC0005("ENCYBC0005", "生成当日任务组失败"),
    BC0006("ENCYBC0006", "系统异常"),
    BC0007("ENCYBC0007", "没有该任务执行信息"),
    BC0008("ENCYBC0008", "没有该任务的执行类"),
    BC0009("ENCYBC0009", "任务正在执行，不能启动"),
    BC0010("ENCYBC0010", "任务执行成功，不能重复"),
    BC0011("ENCYBC0011", "任务执行失败，无法加载类"),
    BC0012("ENCYBC0012", "更新任务状态为执行中失败"),
    BC0013("ENCYBC0013", "任务执行失败"),
    BC0014("ENCYBC0014", "汇总数据没有找到"),
    BC0015("ENCYBC0015", "抽取数据没有找到"),
    BC0016("ENCYBC0016", "抽取数据任务还没有执行"),
    BC0017("ENCYBC0017", "抽取数据任务还没有执行完成"),
    BC0018("ENCYBC0018", "汇总数据中没有找到人民币统计数"),

    //文件类
    BC0100("ENCYBC0100", "执行对账明细文件转存失败"),
    BC0101("ENCYBC0101", "执行向开放平台发送文件下载申请失败"),
    BC0102("ENCYBC0102", "执行文件移动，检查文件是否收齐任务失败"),
    BC0103("ENCYBC0103", "从明细文件中导数据到数据库表失败"),
    BC0104("ENCYBC0104", "参数中缺少必要的参数"),
    BC0105("ENCYBC0105", "开放平台响应无head头"),
    BC0106("ENCYBC0106", "开放平台head头提示retCode提示交易失败"),
    BC0107("ENCYBC0107", "开放平台响应无body"),
    BC0108("ENCYBC0108", "调用文件下载申请接口失败"),
    BC0109("ENCYBC0109", "对账明细文件数据移库失败"),
    BC0110("ENCYBC0110", "部分对账明细文件下载申请失败"),
    BC0111("ENCYBC0111", "对账明细文件格式不对，无法解析"),
    BC0112("ENCYBC0112", "对账明细数据批量入库失败"),
    BC0113("ENCYBC0113", "对账明细文件后缀不合法"),
    BC0114("ENCYBC0114", "执行从联机库导报文文件数据到批量库任务失败"),
    BC0115("ENCYBC0115", "解析明细对账文件失败"),
    BC0116("ENCYBC0116", "对账明细文件检查不通过，end文件不存在"),
    BC0117("ENCYBC0116", "文件不存在"),
    BC0118("ENCYBC0118", "数据从联机库导入文件失败"),
    
    //查询接口错误
    SUCCESS("000000", "交易成功"),
    PAYDATE_NULL("ENCYQ10001", "查询失败，参数：平台日期[payDate]为空"),
    PAYSERNO_NULL("ENCYQ10002", "查询失败，参数：平台流水号[paySerno]为空"),
    COUNT_NULL("ENCYQ10003", "查询失败，参数：查询条数[count]为空"),
    COUNT_INVALID("ENCYQ10004", "查询失败，参数：查询条数[count]不是合法的数字"),
    COUNT_INVALID_NUM("ENCYQ10005", "查询失败，参数：查询条数[count]必须在0到30之间"),
    QUERYPAGEFLAG_NULL("ENCYQ10006", "查询失败，参数：翻页参数[queryPageFlag]为空"),
    QUERYPAGEFLAG_INVALID_NUM("ENCYQ10007", "查询失败，参数：翻页参数[queryPageFlag]不是合法的数字"),
    QUERYPAGEFLAG_INVALID("ENCYQ10008", "查询失败，参数：翻页参数[queryPageFlag]必须是0, 1, 2之间的任意一个"),
    MSGID_INVALID("ENCYQ10009", "查询失败，翻页模式下参数：报文标识号[msgId]为空"),
    CLEARMSGID_NULL("ENCYQ10010", "查询失败，参数：清算报文标识号[clearMsgId]为空"),
    BATCHID_NULL("ECNYQ10011", "查询失败，参数：批次号[batchId]为空"),
    NODATA("ENCYQ10012", "没有查询到数据"),
    SYS_ERROR("ENCYQ10013", "发生系统错误"),
    BODY_NULL("ENCYQ10014", "请求报文非法，报文体为空"),
    DATABASE_ERROR("ENCYQ10015", "数据库错误"),
    PARAMETER_ERROR("ENCYQ10016", "参数错误"),
    QUERY_PAGE_KEY_INVALID("ENCYQ10017", "查询失败，翻页模式下参数：[queryPageKey]不能为空"),
    SUMMARY_QUERY_NULL("ENCYQ10019", "对账汇总查询结果为空"),
    // 差错公共接口
    DSPT_PATH_ERROR("ENCYE10001", "请求路径错误"),
    DSPT_SYS_ERROR("ENCYE10002", "发生系统错误"),
    DSPT_SERVER_ERROR("ENCYE10003", "请求服务错误"),
    DSPT_RESULT_ERROR("ENCYE10004", "原交易不存在"),
    DSPT_RESULT_OVER_ERROR("ENCYE10005", "已经在处理，请勿重复处理"),
    DSPT_CODE_ERROR("ENCYE10006", "差错原因码错误"),
    DSPT_CHECKRESULT_ERROR("ENCYE10007", "更新对账结果表失败"),
    DSPT_START_ERROR("ENCYE10008", "此状态不允许对账"),
    
	//对账
    CHECK_BATCHID_INVALID("ENCYC10001", "交易检查错误，系统不支持的处理类型"),
	CHECK_BATCHID_EMPTY("ENCYC10002", "交易检查错误，交易批次号为空"),
	CHECK_BATCHID_LEN("ENCYC10003", "交易批次号长度大于13位字符"),
	CHECK_CHECKDAY_LEN("ENCYC10004", "交易检查错误，对账日期长度大于8位字符"),
	CHECK_TASK_EXIST("ENCYC10005", "交易检查错误，当前已经有正在执行重对账作务"),
	CHECK_DETAIL_TASK_EXIST("ENCYC10006", "交易检查错误，当前正在执行明细对账作务"),
	;
	

    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(SettleTaskErrorEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code) {
        return enumSet.contains(code);
    }

    private String code;

    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    SettleTaskErrorEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
