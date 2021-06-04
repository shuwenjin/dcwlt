package com.dcits.dcwlt.dcepgw.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.SM3;
import com.dcits.dcwlt.dcepgw.exception.GwException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jcajce.provider.asymmetric.x509.CertificateFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class SMUtil {
    public static void main(String[] args) throws Exception {
        String p7 = pkcs7("hello, world".getBytes());
        System.out.println(p7);
        System.out.println(signedData_Verify(Base64.decode(p7)));
//        verifySig(new CMSSignedData(Base64.decode(p7)));
    }

    /**
     *
     * pkcs7 编码
     */
    public static String pkcs7(byte[] plainText) throws Exception {
        //加载公钥
        FileInputStream input = new FileInputStream("/Users/yunghugh/show/gm/cert2.pem");
        CertificateFactory certificateFactory = new CertificateFactory();
        X509Certificate certificate = (X509Certificate) certificateFactory.engineGenerateCertificate(input);
        input.close();

        //加载私钥，private为换成实际的私钥

//        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec("private".getBytes());
        String strtemp = "MIGHAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBG0wawIBAQQgc+MkOsA04wQzIvu3" +
                "ajnMyDY43Yh5FhN8KkdwyM5DlfWhRANCAAQ8d/p2aQvduzqxHi+fg+GAtVOoQHZ+" +
                "GilWf1iOxyb1YZLpMVhN5vX3DfOl05Uel6D63rw+QWFatf1qXoywSxa0";

        byte[] privateKeyBytes = Base64.decode(strtemp);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);

        //SM2算法实际上为ECC算法，并指定了一些参数值，所以这里的参数是EC
        KeyFactory factory = KeyFactory.getInstance("EC", "BC");
        PrivateKey privateKey = factory.generatePrivate(spec);

        //以下为签名并封装成PKCS7格式
        byte[] signedMessage;
        List<X509Certificate> certList = new ArrayList<>();
        CMSTypedData cmsData = new CMSProcessableByteArray(plainText);
        certList.add(certificate);
        JcaCertStore certs = new JcaCertStore(certList);

        CMSSignedDataGenerator cmsGenerator = new CMSSignedDataGenerator();
        ContentSigner contentSigner = new JcaContentSignerBuilder("SM3withSM2").build(privateKey);
        cmsGenerator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                new JcaDigestCalculatorProviderBuilder().setProvider("BC").build())
                .build(contentSigner, certificate));
        cmsGenerator.addCertificates(certs);

        CMSSignedData cms = cmsGenerator.generate(cmsData, true);
        signedMessage = cms.getEncoded();
        return Base64.toBase64String(signedMessage);
    }

    public static boolean signedData_Verify(byte[] signedData) {
        boolean verifyRet = true;
        try {
            // 新建PKCS#7签名数据处理对象
            CMSSignedData sign = new CMSSignedData(signedData);

            // 添加BouncyCastle作为安全提供
            Security.addProvider(new BouncyCastleProvider());

            // 获得证书信息
            Store<X509CertificateHolder> certs = sign.getCertificates();

            // 获得签名者信息
            SignerInformationStore signers = sign.getSignerInfos();
            Collection c = signers.getSigners();
            Iterator it = c.iterator();

            // 当有多个签名者信息时需要全部验证
            while (it.hasNext()) {
                SignerInformation signer = (SignerInformation) it.next();

                // 证书链
                Collection certCollection = certs.getMatches(signer.getSID());
                Iterator certIt = certCollection.iterator();
                System.out.println(certs.getMatches(null) + "collection of certs");

                while (certIt.hasNext()) {
                    System.out.println("enter while loop2");
                    X509CertificateHolder certHolder = (X509CertificateHolder) certIt.next();

                    //证书序列号
                    String sn = String.valueOf(certHolder.getSerialNumber());
                    System.out.println("sn:"+sn);
                    //证书序列号
                    String st = DateUtil.format(certHolder.getNotBefore(),"yyyy-MM-dd HH:mm:ss");
                    System.out.println("st:"+st);
                    //证书序列号
                    String et = DateUtil.format(certHolder.getNotAfter(),"yyyy-MM-dd HH:mm:ss");
                    System.out.println("et:"+et);
                    //公钥
                    byte[] pk = certHolder.toASN1Structure().getSubjectPublicKeyInfo().getEncoded();

                    String pkb64= Base64.toBase64String(pk);
                    System.out.println("pk:"+pkb64);
                    boolean re = signer.verify(new JcaSimpleSignerInfoVerifierBuilder().setProvider("BC").build(certHolder));
                    System.out.println(re);
                }
            }

        } catch (Exception e) {
            verifyRet = false;
            e.printStackTrace();
            System.out.println("验证数字签名失败");
        }
        return verifyRet;
    }

    /**
     * @param src 待计算摘要的原文
     * @return 摘要
     * */
    public static byte[] digest(String src){
        return SM3.create().digest(src);
    }

    /**
     * 用私钥签名数据
     * @param sn 签名证书序列号
     * @param src 待签名的数据
     * @return Base64编码的签名
     * */
    public static String sign(String sn,String src){
        String signature = "";
        try {
            //TODO
            String privateKey = PRIVATEKEY;
            byte[] data = SmUtil.sm2(privateKey, null).sign(digest(src));
            signature = Base64.toBase64String(data);
        } catch (Exception e) {
            log.error("签名异常", e);
            throw new GwException(GwException.CODE_SIGN, "签名异常");
        }

        return signature;
    }

    /**
     * 用公钥验签
     * @param sn 签名证书序列号
     * @param src 原数据
     * @param sign 签名
     * @return 验签结果
     * */
    public static boolean verifySign(String sn,byte[] src,String sign){
        boolean isVerify = false;
        try {
            //TODO
            String publicKey = PUBLICKEY;
            isVerify = SmUtil.sm2(null, publicKey).verify(SM3.create().digest(src), Base64.decode(sign));
        } catch (Exception e) {
            log.error("验签异常", e);
            throw new GwException(GwException.CODE_SIGN, "验签异常");
        }
        return isVerify;
    }

    public static String PUBLICKEY = "MFkwEwYHKoZIzj0CAQYIKoEcz1UBgi0DQgAETcVczGjTB4p7kerqtSDMcc2CfVI1j1Tr2tl9VV5irEKUnSq1QMRKsx1tbzMjgkZSTt/4wUNVzgGnk+D8GkHEGQ==";
    public static String PRIVATEKEY = "MIGTAgEAMBMGByqGSM49AgEGCCqBHM9VAYItBHkwdwIBAQQg5QXLcbQxbgpKAQgyBn+Lk0zZzmHPw4ZHo3UZDoFZcpegCgYIKoEcz1UBgi2hRANCAARNxVzMaNMHinuR6uq1IMxxzYJ9UjWPVOva2X1VXmKsQpSdKrVAxEqzHW1vMyOCRlJO3/jBQ1XOAaeT4PwaQcQZ";

}
