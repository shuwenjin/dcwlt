package com.dcits.dcwlt.dcepgw.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.digest.SM3;
import cn.hutool.crypto.symmetric.SM4;
import com.dcits.dcwlt.dcepgw.certs.CertManager;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 国密工具类
 * 加解密、签名验签软件算法，如果连接密码平台或加密机，替换实现即可
 */
@Slf4j
public class SMUtil {
    public static void main(String[] args) throws Exception {
        byte[] key = genSM4Key();
        System.out.println(HexUtil.encodeHexStr(getDigitalEnvelopePlain(key),false));
        System.out.println(HexUtil.encodeHexStr(key,false));
        String keyHex = "4D3F520E91C04F643BC4A94DEB630CF5";
        String   key2 = "F2D484F5D5E695A9FCB48B6064C8081C";
        String ecs = "1s9FXQf227sRElNZXhsBnw==";
        SM4 sm4 = new SM4(HexUtil.decodeHex(keyHex));
        System.out.println(HexUtil.encodeHexStr(sm4.getSecretKey().getEncoded(),false));
        String p7 = pkcs7("hello, world".getBytes());
        System.out.println(p7);
        System.out.println(signedData_Verify(Base64.decode(p7),new HashMap()));
//        verifySig(new CMSSignedData(Base64.decode(p7)));
    }

    /**
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

        //设置BC provider
        Security.addProvider(new BouncyCastleProvider());
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

    public static boolean signedData_Verify(byte[] signedData, Map map) {
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
                    map.put("sn",sn);
                    System.out.println("sn:" + sn);
                    //证书序列号
                    String st = DateUtil.format(certHolder.getNotBefore(), "yyyy-MM-dd HH:mm:ss");
                    map.put("st",st);
                    System.out.println("st:" + st);
                    //证书序列号
                    String et = DateUtil.format(certHolder.getNotAfter(), "yyyy-MM-dd HH:mm:ss");
                    map.put("et",et);
                    System.out.println("et:" + et);
                    //公钥
                    byte[] pk = certHolder.toASN1Structure().getSubjectPublicKeyInfo().getEncoded();

                    String pkb64 = Base64.toBase64String(pk);
                    map.put("pk",pkb64);
                    System.out.println("pk:" + pkb64);
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
     */
    public static byte[] digest(String src) {
        return SM3.create().digest(src);
    }

    /**
     * 用私钥签名数据
     *
     * @param sn  签名证书序列号
     * @param src 待签名的数据
     * @return Base64编码的签名
     */
    public static String sign(String sn, String src) {
        String signature = "";
        try {
            String privateKey = CertManager.getInstance().getKeyBySn(sn);
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
     *
     * @param sn   签名证书序列号
     * @param src  原数据
     * @param sign 签名
     * @return 验签结果
     */
    public static boolean verifySign(String sn, String src, String sign) {
        boolean isVerify = false;
        try {
            //TODO
            String publicKey = CertManager.getInstance().getKeyBySn(sn);
            isVerify = SmUtil.sm2(null, publicKey).verify(SM3.create().digest(src), Base64.decode(sign));
        } catch (Exception e) {
            log.error("验签异常", e);
            throw new GwException(GwException.CODE_SIGN, "验签异常");
        }
        return isVerify;
    }


    /**
     * 解析对称加密算法
     *
     * @param env
     * @return
     */
    public static String getSymmAlg(byte[] env) {
        byte split = "|".getBytes()[0];
        if (env[1] == split) {
            if (env[0] == 1 || env[0] == 0x31) {
                return "AES";
            }
            if (env[0] == 2 || env[0] == 0x32) {
                return "SM4";
            }
        }
        if (env[2] == split) {
            if (env[1] == 0x31) {
                return "AES";
            }
            if (env[1] == 0x32) {
                return "SM4";
            }
        }
        return null;
    }

    /**
     * 解析对称秘钥
     *
     * @param env
     * @param alg
     * @return
     */
    public static byte[] getKeyInPlainEnv(byte[] env, String alg) {
        byte[] algbs = new byte[2];
        System.arraycopy(env, 0, algbs, 0, 2);
        int keyLength = 0;
        alg = new String(algbs);
        if (alg.equals("01"))
            keyLength = 32;
        if (alg.equals("02"))
            keyLength = 16;
        byte[] key = new byte[keyLength];
        System.arraycopy(env, 3, key, 0, keyLength);
        return key;
    }


    /**
     * 随机生成SM4密钥 长度 16 字节,128位
     * */
    public static byte[] genSM4Key() {
        return new SM4().getSecretKey().getEncoded();
    }

    /**获取数字信封明文
     * @param key 对称密钥
     * @return 数字信封明文byte[]
     */
    public static byte[] getDigitalEnvelopePlain(byte[] key){
        byte[] env = new byte[key.length+3];
        byte[] alg = new byte[3];
        if(32 == key.length){
            alg = "01|".getBytes();
        }else if(16 == key.length){
            alg = "02|".getBytes();
        }
        System.arraycopy(alg,0,env,0,3);
        System.arraycopy(key,0,env,3,key.length);
        return env;
    }
    /**
     * 生成数字信封
     * 使用接收方数字证书（公钥）和非对称算法（SM2）加密对称密钥
     *
     * @param sn  证书序列号
     * @param key 对称密钥
     * @return String              Base64数字信封
     */
    public static String getDigitalEnvelopeCipher(String sn, byte[] key) {
        //TODO
        String publicKey  =CertManager.getInstance().getKeyBySn(sn);
        byte[] digitalEnvelopePlain =getDigitalEnvelopePlain(key);
        String data = SmUtil.sm2(null, publicKey).encryptBase64(digitalEnvelopePlain, KeyType.PublicKey);
        return data;
    }
    /**
     * 解密数字信封获取明文密钥
     *
     * @param sn  证书序列号
     * @param digitalEnvelopeCipher 数字信封密文
     * @return byte[]               对称密钥
     */
    public static byte[] getKeyFromDigitalEnvelope(String sn, String digitalEnvelopeCipher) {
        //TODO
        String privateKey = CertManager.getInstance().getKeyBySn(sn);
        //解密出信封明文
        byte[] env = SmUtil.sm2(privateKey, null).decrypt(digitalEnvelopeCipher, KeyType.PrivateKey);
        //获取算法
        String alg = getSymmAlg(env);
        //获取密钥
        byte[] key = getKeyInPlainEnv(env,alg);
        return key;
    }

    /**
     * SM4加密
     * @param src 源字符串明文
     * @param key 密钥
     * @return base64编码的密文
     */
    public static String encryptSm4B64(String src,byte[] key){
        return SmUtil.sm4(key).encryptBase64(src);
    }
    /**
     * SM4解密
     * @param src 源B64密文字符串
     * @param key 密钥
     * @return 明文字符串
     */
    public static String decryptSm4B64(String src, byte[] key) {
        return SmUtil.sm4(key).decryptStr(src);
    }


}
