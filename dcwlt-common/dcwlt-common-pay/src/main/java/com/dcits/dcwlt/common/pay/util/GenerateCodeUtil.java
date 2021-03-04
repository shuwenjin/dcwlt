package com.dcits.dcwlt.common.pay.util;


import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 生成Code工具
 */
public class GenerateCodeUtil {


    private static String environment = "0";


    /**
     * 机构编号
     */
    private static final String ORGCODE = "106";
    /**
     * 系统ID
     */
    public static final String ECNY_SYS_ID = "ECNY";
    /**
     * 预留填充
     */
    private static final String COREZERO_FILL = "00";
    /**
     * 默认控制码
     */
    private static final String DEFAULTBUCKETCODE_FILL = "00";

    /**
     * 批次号后面预留两位
     */
    private static final String DEFAULTBATCHNO_FILL = "00";


    /**
     * 流水前缀
     */
    private static final String PRE_FLOW = "F";
    /**
     * 协议号前缀
     */
    private static final String PRE_AGREE = "A";

    /**
     * 批次号前缀
     */
    private static final String PRE_BATCH = "B";

    /**
     * 通讯流水号序列
     */
    private static AtomicInteger snSequence = new AtomicInteger(0);

    /**
     * 核心请求流水号序列
     */
    private static AtomicInteger coreSequence = new AtomicInteger(0);

    /**
     * 3.报文标识号生成
     *
     * @param outOrgType 跨行标识 {@link OutOrgTypeEnum}
     * @param messageTp  报文编号 为三位
     * @param bucketCode 钱包ID控制位 可以为空，有值必须为两位
     * @return
     */
    public static String generateMsgId(OutOrgTypeEnum outOrgType, String messageTp, String bucketCode) {
        return commonToString(
                //8 位日期
                DateUtil.getDefaultDate(),
                //3 位机构标识
                ORGCODE,
                //1 位跨行标识
                String.valueOf(outOrgType.getType())
                //3 位报文编号
                , messageTp,
                //14位序列
                //-6 位时间
                DateUtil.getDefaultTime(),
                //-8 位分布式序列
                convertNumToString(Long.valueOf(messageTp), 8),
                //2 位控制码
                StringUtils.isBlank(bucketCode) ? DEFAULTBUCKETCODE_FILL : bucketCode,
                //1 位环境标识
                environment);
    }

    public static String generateMsgId(OutOrgTypeEnum outOrgType, String messageTp) {
        return generateMsgId(outOrgType, messageTp, null);
    }

    /**
     * 平台流水
     *
     * @return
     */
    public static String generatePlatformFlowNo() {
        return commonToString(
                //6 位时间
                DateUtil.getDefaultTime(),
                //5 位序列
                convertNumToString((long) (Math.random() * 90000 + 10000), 5));
    }


    /**
     * 生成交易批次号
     *
     * @return
     */
    public static String getBachNo() {
        return bachNo(new Date());
    }

    private static String bachNo(Date date) {
        return commonToString(
                PRE_BATCH,
                DateUtil.getDefaultDate(),
                getBatchHourStr(date),
                DEFAULTBATCHNO_FILL);
    }

    /**
     * 根据给定的时间生成交易批次号
     *
     * @return
     */
    public static String getBachNo(Date date) {
        return bachNo(date);
    }

    public static String generateFlowNo() {
        return commonToString(
                PRE_FLOW,
                //3 位机构
                ORGCODE,
                //14 位时间
                DateUtil.getSystemTime(),
                //8 位分布式序列
                convertNumToString((long) (Math.random() * 90000 + 10000), 8)
        );

    }

    public static String generateMsgSN(String msgId) {
        int seq = snSequence.incrementAndGet();
        //超过4位重置
        if (seq > 9999) {
            snSequence.set(0);
            seq = snSequence.incrementAndGet();
        }
        return commonToString(
                //32 位报文标识
                msgId,
                //4 位序列
                convertNumToString(seq, 4));
    }


    public static String generateAgreementNo(String org) {
        return commonToString(
                PRE_AGREE,
                //3 位机构
                StringUtils.isBlank(org) ? ORGCODE : org,
                //14 位时间
                DateUtil.getSystemTime(),
                //8 位分布式序列
                convertNumToString((long) (Math.random() * 90000 + 10000), 8)
        );
    }

    public static String generateAgreementNo() {
        return generateAgreementNo(null);
    }


    /**
     * 获取批次中的小时
     * 00:00:00~00:59:59 -> 01
     * 23:00:00~23:59:59 -> 24
     *
     * @return 当天的第几小时
     */
    private static String getBatchHourStr(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY) + 1;
        return convertNumToString(hour, 2);
    }

    /**
     * 转换数字成前补0的字符串
     *
     * @param num    数字
     * @param length 长度
     * @return 前补0的字符串
     */
    private static String convertNumToString(long num, int length) {
        StringBuilder str = new StringBuilder(String.valueOf(num));
        //前补0
        while (str.length() < length) {
            str.insert(0, "0");
        }
        //如果超长截取
        String re = str.toString();
        if (str.length() > length) {
            re = re.substring(re.length() - length);
        }
        return re;
    }

    /**
     * 核心请求流水序号	ECNY+8位当前日期+6位时间+00+8位分布式序列号+4位顺序号（共32位）
     *
     * @return 32位核心请求流水序号
     */
    public static String generateCoreReqSerno() {
        int seq = coreSequence.incrementAndGet();
        if (seq > 9999) {
            coreSequence.set(0);
            seq = coreSequence.incrementAndGet();
        }

        return commonToString(ECNY_SYS_ID,
                DateUtil.getDefaultTime(),
                COREZERO_FILL,
                // 8位
                convertNumToString((long) (Math.random() * 90000 + 10000), 8),
                // 4位
                convertNumToString(seq, 4)
        );
    }

    private static String commonToString(Object... objects) {
        StringBuilder builder = new StringBuilder();
        for (Object item : objects) {
            builder.append(item);
        }
        return builder.toString();
    }

    /**
     * 核心请求日期
     *
     * @return
     */
    public static String getCoreReqDate(String batchId) {
        if (StringUtils.isBlank(batchId) || batchId.length() < 10) {
            return DateUtil.getDefaultDate();
        }
        return batchId.substring(1, 9);
    }
}
