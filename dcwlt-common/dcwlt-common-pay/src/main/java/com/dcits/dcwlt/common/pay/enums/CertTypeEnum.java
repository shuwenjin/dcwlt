package com.dcits.dcwlt.common.pay.enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @date  2020/12/28
 * @version 1.0.0
 * <p>证书（密钥）类型</p>
 */
public enum CertTypeEnum {
    CT00("CT00","参与者自身签名证书私钥"),
    CT01("CT01","参与者自身签名证书公钥"),
    CT02("CT02","参与者自身加密证书私钥"),
    CT03("CT03","参与者自身加密证书公钥"),
    CT04("CT04","城银清算签名证书公钥"),
    CT05("CT05","城银清算加密证书公钥"),
    CT06("CT06","敏感信息对称密钥"),
    CT07("CT07","对账文件对称密钥"),
    CT08("CT08","服务器证书私钥"),
    CT09("CT09","服务器证书公钥"),
    CS00("CS00", "城银清算交互签名证书公钥"),
    CS01("CS01", "城银清算交互加密证书公钥"),
    ;

    private static final Set<String> enumSet = new HashSet<>(4);

    static {
        Arrays.asList(ChangeCdEnum.values()).forEach(e -> enumSet.add(e.getCode()));
    }

    public static boolean hasEnum(String code){
        return enumSet.contains(code);
    }


    private String code;

    private String desc;

    CertTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
