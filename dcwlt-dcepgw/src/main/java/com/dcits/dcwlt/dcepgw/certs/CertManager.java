package com.dcits.dcwlt.dcepgw.certs;

import java.util.concurrent.ConcurrentHashMap;

public class CertManager {
    private static final CertManager certManager= new CertManager();
    public static ConcurrentHashMap<String, String> certMap = new ConcurrentHashMap<>();

    public static CertManager getInstance (){
        return certManager;
    }
    /**
     * @param sn 证书序列号
     * @return 密钥
     * */
    public String getKeyBySn(String sn){
        String key = "";
        key = certMap.get(sn);
        return key;
    }

    //For TEST
    public static String PUBLICKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAETcVczGjTB4p7kerqtSDMcc2CfVI1j1Tr2tl9VV5irEKUnSq1QMRKsx1tbzMjgkZSTt/4wUNVzgGnk+D8GkHEGQ==";
    public static String PRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg5QXLcbQxbgpKAQgyBn+Lk0zZzmHPw4ZHo3UZDoFZcpegCgYIKoEcz1UBgi2hRANCAARNxVzMaNMHinuR6uq1IMxxzYJ9UjWPVOva2X1VXmKsQpSdKrVAxEqzHW1vMyOCRlJO3/jBQ1XOAaeT4PwaQcQZ";
    static {
        certMap.put("1",PUBLICKEY);
        certMap.put("2",PRIVATEKEY);

    }

}
