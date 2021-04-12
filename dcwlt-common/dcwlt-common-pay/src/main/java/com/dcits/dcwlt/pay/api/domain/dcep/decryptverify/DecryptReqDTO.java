package com.dcits.dcwlt.pay.api.domain.dcep.decryptverify;


import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;

public class DecryptReqDTO extends DCEPReqBody {

    private String tradeType;           //交易类型
    private String encryptAlgorithm;    //非对称加密算法
    private String sysCertId;           //我方私钥id
    private String blockMode;           //分组模式
    private String paddingMode;         //填充模式
    private String keyEncryptAlgorithm; //对称加密算法
    private String keyGenRule;          //临时密钥编码规则
    private String keyCiphertext;       //临时密钥密文
    private String keyCertId;           //临时密钥id
    private String keyIV;               //初始向量
    private String textToDecrypt;       //待解密文本
    private String signatureAlgorithm;  //签名算法
    private String appId;               //第三方应用id
    private String textToVerify;        //待验签文本
    private String signature;           //签名
    private String encodingType;        //待解密文本/签名的编码方式

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getEncryptAlgorithm() {
        return encryptAlgorithm;
    }

    public void setEncryptAlgorithm(String encryptAlgorithm) {
        this.encryptAlgorithm = encryptAlgorithm;
    }

    public String getSysCertId() {
        return sysCertId;
    }

    public void setSysCertId(String sysCertId) {
        this.sysCertId = sysCertId;
    }

    public String getBlockMode() {
        return blockMode;
    }

    public void setBlockMode(String blockMode) {
        this.blockMode = blockMode;
    }

    public String getPaddingMode() {
        return paddingMode;
    }

    public void setPaddingMode(String paddingMode) {
        this.paddingMode = paddingMode;
    }

    public String getKeyEncryptAlgorithm() {
        return keyEncryptAlgorithm;
    }

    public void setKeyEncryptAlgorithm(String keyEncryptAlgorithm) {
        this.keyEncryptAlgorithm = keyEncryptAlgorithm;
    }

    public String getKeyGenRule() {
        return keyGenRule;
    }

    public void setKeyGenRule(String keyGenRule) {
        this.keyGenRule = keyGenRule;
    }

    public String getKeyCiphertext() {
        return keyCiphertext;
    }

    public void setKeyCiphertext(String keyCiphertext) {
        this.keyCiphertext = keyCiphertext;
    }

    public String getKeyCertId() {
        return keyCertId;
    }

    public void setKeyCertId(String keyCertId) {
        this.keyCertId = keyCertId;
    }

    public String getKeyIV() {
        return keyIV;
    }

    public void setKeyIV(String keyIV) {
        this.keyIV = keyIV;
    }

    public String getTextToDecrypt() {
        return textToDecrypt;
    }

    public void setTextToDecrypt(String textToDecrypt) {
        this.textToDecrypt = textToDecrypt;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTextToVerify() {
        return textToVerify;
    }

    public void setTextToVerify(String textToVerify) {
        this.textToVerify = textToVerify;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncodingType() {
        return encodingType;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    @Override
    public String toString() {
        return "decryptReqDTO{" +
                "tradeType='" + tradeType + '\'' +
                ", encryptAlgorithm='" + encryptAlgorithm + '\'' +
                ", sysCertId='" + sysCertId + '\'' +
                ", blockMode='" + blockMode + '\'' +
                ", paddingMode='" + paddingMode + '\'' +
                ", keyEncryptAlgorithm='" + keyEncryptAlgorithm + '\'' +
                ", keyGenRule='" + keyGenRule + '\'' +
                ", keyCiphertext='" + keyCiphertext + '\'' +
                ", keyCertId='" + keyCertId + '\'' +
                ", keyIV='" + keyIV + '\'' +
                ", textToDecrypt='" + textToDecrypt + '\'' +
                ", signatureAlgorithm='" + signatureAlgorithm + '\'' +
                ", appId='" + appId + '\'' +
                ", textToVerify='" + textToVerify + '\'' +
                ", signature='" + signature + '\'' +
                ", encodingType='" + encodingType + '\'' +
                '}';
    }
}
