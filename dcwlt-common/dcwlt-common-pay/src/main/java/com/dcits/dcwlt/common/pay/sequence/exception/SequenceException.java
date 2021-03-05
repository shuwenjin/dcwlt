package com.dcits.dcwlt.common.pay.sequence.exception;

/**
 * 序列中心异常类
 *
 * @author lanleifang-yfzx
 * @Time 2020年3月20日
 * @Version 1.0
 */
public class SequenceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    //IDCache未初始化成功时的异常码
    public static final int EXCEPTION_ID_IDCACHE_INIT_FALSE = -1;
    //key不存在时的异常码
    public static final int EXCEPTION_ID_KEY_NOT_EXISTS = -2;
    //SegmentBuffer中的两个Segment均未从DB中装载时的异常码
    public static final int EXCEPTION_ID_TWO_SEGMENTS_ARE_NULL = -3;
    //init_id小于1时候的异常码
    public static final int EXCEPTION_INIT_ID_LESS_THAN_ONE = -4;
    //max_total小于0时候的异常码
    public static final int EXCEPTION_MAX_TOTAL_LESS_THAN_ZERO = -5;
    //号段模式服务初始化失败
    public static final int EXCEPTION_SEGMENT_SERVICE_INIT_FAIL = -6;

    //雪花算法中，各区间段位数总和不足64位
    public static final int EXCEPTION_ALLOCATE_NOT_ENOUGH_64_BITS = -7;
    //appNo不正确，应该是在1-1023之间的整数
    public static final int EXCEPTION_APPNO_IS_NOT_CORRECT = -8;
    //workerid不正确，通常应该是在0-1023之间的整数
    public static final int EXCEPTION_WORKERID_IS_NOT_CORRECT = -9;
    //数据源不存在
    public static final int EXCEPTION_DATASOURCE_NOT_EXISTS = -10;


    private int errorCode;

    public SequenceException(Throwable e) {
        super(e);
    }

    public SequenceException(String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
    }

    public SequenceException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SequenceException(String message, int errorCode, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
