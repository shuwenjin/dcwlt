package com.dcits.dcwlt.pay.online.serno;

import com.dcits.dcwlt.common.pay.util.APPUtil;
import com.dcits.dcwlt.common.pay.util.ConcurrentUtil;
import com.dcits.dcwlt.common.pay.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SernoService {

    public static final String QUERY_SERNO_SQL = "sequenceMapper.querySerno";


    /**
     * 流水信息
     */
    private static class SernoInfo {
        /**
         * 数据源
         */
        public final String dataSource;
        /**
         * 序列名称
         */
        public final String seqName;
        /**
         * 流水长度
         */
        public final int sernoLen;
        /**
         * 缓存长度
         */
        public final int cacheLen;
        /**
         * 后缀最大值
         */
        public final int suffixMaxInt;
        /**
         * 后缀列表
         */
        public final String[] suffixStrList;

        /**
         * 前缀值
         */
        public String prefixStr;
        /**
         * 后缀值
         */
        public int suffixInt;

        public SernoInfo(String dataSource, String seqName, int sernoLen, int cacheLen) {
            // 缓存个数
            int cacheCount = (int) Math.pow(10, cacheLen);
            // 初始化
            this.dataSource = dataSource;
            this.seqName = seqName;
            this.sernoLen = sernoLen;
            this.cacheLen = cacheLen;
            this.suffixMaxInt = cacheCount - 1;
            this.suffixStrList = new String[cacheCount];
            // 预填充后缀
            for (int i = 0; i < cacheCount; i++) {
                suffixStrList[i] = StringUtils.leftPad(String.valueOf(i), cacheLen, '0');
            }
            //
            this.prefixStr = null;
            this.suffixInt = -1;
        }
    }

    @Autowired
    private SernoService inst;

    /**
     * 流水信息映射，key为seqId
     */
    private Map<String, SernoInfo> sernoInfoMap = new ConcurrentHashMap<>();

    public <E> E selectOne(String sqlId, Object param) {
        return inst.selectOne(null, sqlId, param);
    }

    public <E> E selectOne(String dataSource, String sqlId, Object param) {
        if (StringUtils.isEmpty(dataSource)) {
            // 默认数据源
//            return DBUtil.selectOne(sqlId, param);
            return null;
        } else {
            // 指定数据源
//            return DBUtil.selectOne(dataSource, sqlId, param);
            return null;
        }
    }

    public long getSernoLong(String seqName) {
        return inst.getSernoLong(null, seqName);
    }

    public long getSernoLong(String dataSource, String seqName) {
        Map<String, String> param = new HashMap<>(1);
        param.put("seqName", seqName);
        return inst.selectOne(dataSource, QUERY_SERNO_SQL, param);
    }

    public String getSernoStr(String seqName, int seqLen) {
        return inst.getSernoStr(null, seqName, seqLen);
    }

    public String getSernoStr(String dataSource, String seqName, int seqLen) {
        // 获取
        long sernoLong = inst.getSernoLong(dataSource, seqName);
        // 左补0
        String sernoStr = StringUtils.leftPad(String.valueOf(sernoLong), seqLen, '0');
        // 超长截取
        if (sernoStr.length() > seqLen) {
            sernoStr = sernoStr.substring(sernoStr.length() - seqLen);
        }
        // 返回
        return sernoStr;
    }

    public String getSernoWithConfig(String seqId) {
        // 获取流水信息
        SernoInfo sernoInfo = ConcurrentUtil.getMapValue(sernoInfoMap, seqId, () -> {
            // 获取配置
            String appId = APPUtil.getAppId().toLowerCase();
            String dataSourceKey = String.format("%s.common.serno.%s.dataSource", appId, seqId);
            String seqNameKey = String.format("%s.common.serno.%s.seqName", appId, seqId);
            String sernoLenKey = String.format("%s.common.serno.%s.sernoLen", appId, seqId);
            String cacheLenKey = String.format("%s.common.serno.%s.cacheLen", appId, seqId);
//            String dataSource = RtpUtil.getInstance().getProperty(dataSourceKey, IDataService.DEFAULT_DSNAME);
//            String seqName = RtpUtil.getInstance().getProperty(seqNameKey);
//            int sernoLen = Integer.parseInt(RtpUtil.getInstance().getProperty(sernoLenKey));
//            int cacheLen = Integer.parseInt(RtpUtil.getInstance().getProperty(cacheLenKey));
            // 检查入参
//            if (seqName.isEmpty()) {
//                throw new IllegalArgumentException("config invalid: " + seqNameKey);
//            }
//            if (sernoLen <= 0) {
//                throw new IllegalArgumentException("config invalid : " + sernoLenKey);
//            }
//            if (cacheLen < 0 || cacheLen > sernoLen) {
//                throw new IllegalArgumentException("config invalid : " + cacheLenKey);
//            }
//            // 构造对象
//            return new SernoInfo(dataSource, seqName, sernoLen, cacheLen);
            return null;
        });

        // 不需要缓存则直接返回
        if (sernoInfo.cacheLen == 0) {
            return inst.getSernoStr(sernoInfo.dataSource, sernoInfo.seqName, sernoInfo.sernoLen);
        }

        // 内部累加方式处理流水号
        String prefixStr;
        int suffixInt;
        synchronized (sernoInfo) {
            // 首次初始化，或者后缀将要超过最大值，从DB获取前缀
            if (sernoInfo.prefixStr == null || (sernoInfo.suffixInt + 1) > sernoInfo.suffixMaxInt) {
                sernoInfo.prefixStr = inst.getSernoStr(sernoInfo.dataSource, sernoInfo.seqName,
                        sernoInfo.sernoLen - sernoInfo.cacheLen);
                sernoInfo.suffixInt = -1;
            }
            // 累加后缀
            sernoInfo.suffixInt++;
            // 复制到局部变量
            prefixStr = sernoInfo.prefixStr;
            suffixInt = sernoInfo.suffixInt;
        }

        // 前缀+后缀
        return prefixStr + sernoInfo.suffixStrList[suffixInt];
    }

    /**
     * 获取系统流水号 组成规则：系统流水号 = 请求系统英文简称（4位）+ 当前系统交易日期（8位） + 流水序号（10位）
     *
     * @return
     */
    public String getSeqNo() {
        return APPUtil.getAppId() + DateUtil.getDefaultDate() + getSernoWithConfig("sysseqno");
    }


    /**
     * 获取源发起系统流水号 源发起系统流水号= 请求系统英文简称（4位）+ 交易日期（8位）+ 交易时间（9位）+ 流水序号（11位）
     *
     * @return
     */
    public String getCnsmrSysSeqNo() {
        return APPUtil.getAppId() + DateUtil.formatMilliTime() + getSernoWithConfig("cnsmrsysseqno");
    }

    /**
     * 获取第三方流水 6位流水号
     *
     * @return
     */
    public String getCorpSysSeqNo() {
        return getSernoWithConfig("corpsysseqno");
    }

    /**
     * 文服文件序号	ECNY.UMAA.NNNNNN.S170105.GXXXXXX
     * @return
     */
    public String getUfsFileSeq() {
        return getSernoWithConfig("ufsfileseq");
    }

}
