package com.dcits.dcwlt.pay.online.service;

import com.dcits.dcwlt.common.pay.util.DateUtil;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

/**
 * @author zhangwang
 * @version 1.0.0
 * <p>ECNY序列服务</p>
 * @date 2021/1/4
 */
@Service
public class ECNYSerNoService {

    private static final int MAX_RAND_NUM_LENGTH = 8;

    private static final String ECNY_APP_ID = "ECNY";

    /**
     * @param n length of random number
     * @return n bits random number
     */
    public String getNBitsRandNum(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("随机数的长度必须大于0");
        }
        if (n <= MAX_RAND_NUM_LENGTH) {
            SecureRandom secureRandom = new SecureRandom();
            //随机数的基数（最小值）
            int base = (int) Math.pow(10, n - 1);
            int ranNum = base + secureRandom.nextInt((int) Math.pow(10, n) - base);
            return String.valueOf(ranNum);
        } else {
            int div = n / MAX_RAND_NUM_LENGTH;
            int rem = n & (MAX_RAND_NUM_LENGTH - 1);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < div; i++) {
                sb.append(getNBitsRandNum(MAX_RAND_NUM_LENGTH));
            }
            if (rem != 0) {
                sb.append(getNBitsRandNum(rem));
            }
            return sb.toString();
        }
    }

    /**
     * @return 32位的平台流水
     */
    public String genPlatformSerNo() {
        return getNBitsRandNum(32);
    }

    /**
     * 生成报文标识号
     * @return msgId
     */
    public String genMsgId(){
        return getNBitsRandNum(35);
    }


    /**
     * 生成通讯级报文标识号
     * @return
     */
    public String genCommMsgId() {
        return getNBitsRandNum(35);
    }


    /**
     * 获取系统流水号 组成规则：系统流水号 = 请求系统英文简称（4位）+ 当前系统交易日期（8位） + 流水序号（10位）
     * @return 获取核心流水号
     */
    public String getSeqNo() {
        return  ECNY_APP_ID + DateUtil.getDefaultDate() + getNBitsRandNum(10);
    }
}
