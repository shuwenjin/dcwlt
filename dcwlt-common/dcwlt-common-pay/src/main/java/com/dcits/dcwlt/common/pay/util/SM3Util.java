package com.dcits.dcwlt.common.pay.util;


import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.exception.PlatformException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;


/**
 *
 * @Time 2019年12月20日下午6:31:54
 * @Version v1.0
 * <p>Description: SM3工具类</p>
 */
public class SM3Util {


    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    //初始化加载BC库
    static {
        if (Security.getProperty("BC") == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }
    }

    /**
     * 字节转换成16进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * 检验散列值是否与之前的一致
     *
     * @param srcStr
     * @param sm3HexStr
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.NoSuchProviderException
     */
    public static boolean verify(String srcStr, String sm3HexStr) {
        String newHexStr = encrypt(srcStr);
        //判断散列值与之前是否一致
        return sm3HexStr.equals(newHexStr);
    }

    /**
     * 获取sm3散列值
     *
     * @param srcStr
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.NoSuchProviderException
     */
    public static String encrypt(String srcStr) {
        try {
            byte[] message = srcStr.getBytes();
            MessageDigest digest;
            digest = MessageDigest.getInstance(AppConstant.SM3, "BC");
            byte[] result = digest.digest(message);
            //转换16进制并返回
            return bytesToHex(result);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new PlatformException(PlatformError.SM3_ENCRYPT_ERROR);
        }
    }

}

