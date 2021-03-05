package com.dcits.dcwlt.pay.online.service;


import com.dcits.dcwlt.common.pay.type.OutOrgTypeEnum;

import java.util.Date;

/**
 * 生成流水号
 *
 * @author yangqijun-yfzx
 * @see com.dcits.dcwlt.pay.online.service.impl.GenerateCodeServiceImpl
 */

public interface IGenerateCodeService {

    /**
     * 报文标识号生成
     *
     * @param outOrgType 跨行标识 {@link OutOrgTypeEnum}
     * @param messageTp  报文编号 3位大小
     * @param bucketCode 钱包ID控制位 可以为空，有值必须为两位
     *                   生成格式 yyyyMMdd(年月日) + 106(机构号)+ 1|0(跨机构标识) + 报文编号（3位） +
     *                   14位（HHmmss + 8位分布式序列号）+ 两位控制码（默认00）
     *                   + 环境标识1位
     * @return 32位报文标识号
     */
    String generateMsgId(
            OutOrgTypeEnum outOrgType,
            String messageTp,
            String bucketCode
    );

    /**
     * 报文标识号生成
     *
     * @param outOrgType 跨行标识 {@link OutOrgTypeEnum}
     * @param messageTp  报文编号 3位大小
     *                   生成格式 yyyyMMdd(年月日) + 106(机构号)+ 1|0(跨机构标识) + 报文编号（3位） +
     *                   14位（HHmmss + 8位分布式序列号）+ 两位控制码（默认00）
     *                   + 环境标识1位
     * @return 32位报文标识号
     */
    String generateMsgId(
            OutOrgTypeEnum outOrgType,
            String messageTp
    );

    /**
     * 平台流水生成
     * 生成规则 HHmmss（时分秒） + 5位分布式序列号
     *
     * @return 11位平台流水号
     */
    String generatePlatformFlowNo();

    /**
     * 生成交易批次号
     * 生成规则 B + yyyyMMddHH(年月日时) + "00"（预留两位）
     *
     * @return 13位交易批次号
     */
    String getBachNo();


    /**
     * 生成交易批次号
     *
     * @param date 传入生成批次号的时间
     *             生成规则 B + yyyyMMddHH(年月日时) + "00"（预留两位）
     * @return 13位交易批次号
     */
    String getBachNo(Date date);

    /**
     * 生成流水号
     * 生成规则 F + 106(机构号) + yyyyMMddHHmmss(年月日时分秒) + 8位分布式序列号
     *
     * @return 26位流水号
     */
    String generateFlowNo();

    /**
     * 通信级流水号
     *
     * @param msgId 32位报文标识号
     *              生成格式 32位报文标识号 + 顺序4位
     * @return 36位通讯级流水号
     */
    String generateMsgSN(String msgId);

    /**
     * 签约协议号生成
     *
     * @param org 银行机构号，调用机构机构号
     *            生成规则 生成规则 A + 调用机构号(3位) + yyyyMMddHHmmss(年月日时分秒) + 8位分布式序列号
     * @return 26位签约协议号
     */
    String generateAgreementNo(String org);

    /**
     * 签约协议号生成
     * 生成规则 生成规则 A + 调用机构号(3位) + yyyyMMddHHmmss(年月日时分秒) + 8位分布式序列号
     *
     * @return 26位签约协议号
     */
    String generateAgreementNo();

    /**
     * 核心请求流水序号	ECNY+8位当前日期+6位时间+00+8位分布式序列号+4位顺序号（共32位）
     *
     * @return 32位核心请求流水序号
     */
    public String generateCoreReqSerno();

    /**
     * 根据批次号获取核心请求日期 yyyyMMdd
     *
     * @return 8位日期
     */
    public String getCoreReqDate(String batchId);
}
