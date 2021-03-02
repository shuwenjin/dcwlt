package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @date  2021/1/4
 * @version 1.0.0
 * <p>互联互通证件类型与银行核心证件类型映射枚举</p>
 */
public enum IDTpEnum {

    IT01("IT01","10100","身份证"),
    IT02("IT02","10502","军官证"),
    IT03("IT03","10400","护照"),
    IT04("IT04","10300","户口簿"),
    IT05("IT05","10501","士兵证"),
    IT06("IT06","10701","港澳来往内地通行证"),
    IT07("IT07","10703","台湾同胞来往内地通行证"),
    IT08("IT08","10200","临时身份证"),
    IT09("IT09","11000","外国人居留证"),
    IT10("IT10","10602","警官证")
    ;

    private static final Map<String,String> dcepToBankMap = new HashMap<>(16);

    private static final Map<String,String> bankToDcepMap = new HashMap<>(16);

    static {
        Arrays.asList(IDTpEnum.values()).forEach(e -> {
            dcepToBankMap.put(e.getIDKey(),e.getIDValue());
            bankToDcepMap.put(e.getIDValue(),e.getIDKey());
        });

    }

    public static boolean hasEnum(String code){
        return dcepToBankMap.containsKey(code);
    }

    /**
     * 校验互联互通证件类型和银行核心证件类型是否一致
     * @param ecnyIDTP  互联互通证件类型
     * @param bankCoreIDTP 银行核心证件类型
     * @return <tt>true</tt> if ecnyIDTP match bankCoreIDTP
     */
    public static boolean match(String ecnyIDTP,String bankCoreIDTP){
        return bankCoreIDTP.equals(dcepToBankMap.get(ecnyIDTP));
    }

    /**
     * 根据互联互通证件类型获取银行核心证件类型
     * @param ecnyIDTP 互联互通证件类型
     * @return bankCoreIDTP
     */
    public static String getBankCoreIDTP(String ecnyIDTP){
        return dcepToBankMap.get(ecnyIDTP);
    }

    /**
     * 根据银行核心证件类型获取互联互通证件类型
     * @param bankIdTp 银行核心证件类型
     * @return 互联互通证件类型
     */
    public static String getDcepIDTP(String bankIdTp){
        return bankToDcepMap.get(bankIdTp);
    }

    /**
     * 互联互通证件类型
     */
    private String IDKey;

    /**
     * 银行核心证件类型
     */
    private String IDValue;

    /**
     * 类型描述
     */
    private String desc;

    IDTpEnum(String IDKey, String IDValue, String desc) {
        this.IDKey = IDKey;
        this.IDValue = IDValue;
        this.desc = desc;
    }

    public String getIDKey() {
        return IDKey;
    }

    public String getIDValue() {
        return IDValue;
    }

    public String getDesc() {
        return desc;
    }

}
