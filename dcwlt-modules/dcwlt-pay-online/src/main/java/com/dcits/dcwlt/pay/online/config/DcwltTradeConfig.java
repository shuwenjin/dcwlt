package com.dcits.dcwlt.pay.online.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:互联互通报文映射配置类
 * <bean id="DcwltTradeConfig" class="com.cgb.ecny.base.EcnyTradeConfig" init-method="init">
 * <property name="mappings">
 * <list>
 * <value>dcep.917.001.01,FinCdChngNtfctnTradeFlow,com.cgb.ecny.chnldto.trblntfctn.TrblNtfctnReq,Y</value>
 * <value>dcep.225.001.01,converTradeFlow,com.cgb.ecny.chnldto.convert.ConvertReqDTO,Y</value>
 * </list>
 * </property>
 * </bean>
 * 每一个<value>标签代表一个接口的配置，配置包含前两个必输参数与最后一个选输参数，使用英文的逗号","分隔
 * 其中第一个参数为报文编号，对应互联互通报文头的 MsgTp 字段
 * 第二个参数为交易处理器，根据第一个参数与第二个参数的配置，平台会自动将报文装箱并分发到交易处理器执行
 * 第三个参数为报文体类型，即写类名，用于公共入库转换为交易的报文实体
 * 第四个参数为堵重标识类型，N不进行堵重，其他进行堵重
 */
public class DcwltTradeConfig {

    private static Logger logger = LoggerFactory.getLogger(DcwltTradeConfig.class);

    public static final String TRADE_PROCESSOR_NAME = "tradeProcessor";
    public static final String TRADE_CLASS_NAME = "requestClassName";
    public static final String TRADE_IDEMPOTENT_CODE = "idempotentFlag";

    private List<String> mappings;
    private Map<String, Map<String, String>> tradeMappings;


    public Map<String, Map<String, String>> getTradeMappings() {
        //读取Resource目录下的XML文件
        return tradeMappings;
    }

    public void setTradeMappings(Map<String, Map<String, String>> tradeMappings) {
        this.tradeMappings = tradeMappings;
    }

    public List<String> getMappings() {
        return this.mappings;
    }

    public void setMappings(List<String> mappings) {
        this.mappings = mappings;
    }

    public void init() {
        this.tradeMappings = initTradeMappings(this.mappings, this.tradeMappings);
        this.tradeMappings.forEach((key, value) -> logger.info("EcnyTradeConfig Init, Mapping:{}-{}", key, value));
    }

    /**
     * 拆出报文编号，交易处理器和请求报文实体类
     *
     * @param mappings
     * @param tradeMappings
     * @return
     */
    private Map<String, Map<String, String>> initTradeMappings(List<String> mappings, Map<String, Map<String, String>> tradeMappings) {
        if (tradeMappings == null) {
            tradeMappings = new HashMap<>();
        }
        for (int i = 0; i < mappings.size(); i++) {
            String[] mappingStrs = mappings.get(i).split(",");
            if (mappingStrs.length < 4) {
//                throw new Exception("ecnyTradeConfig_mapping_error,your mapping params were less than 4, please check!");
            }
            Map<String, String> temp = new HashMap<>(1);
            temp.put(TRADE_PROCESSOR_NAME, mappingStrs[1]);
            temp.put(TRADE_CLASS_NAME, mappingStrs[2]);
            temp.put(TRADE_IDEMPOTENT_CODE, mappingStrs[3]);
            tradeMappings.put(mappingStrs[0], temp);
        }
        return tradeMappings;
    }

}
