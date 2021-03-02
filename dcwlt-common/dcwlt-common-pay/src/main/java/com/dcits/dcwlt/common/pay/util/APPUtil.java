package com.dcits.dcwlt.common.pay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class APPUtil {

    private static final Logger logger = LoggerFactory.getLogger(APPUtil.class);

    private APPUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @return
     * @Description:获取应用ID
     */
    public static String getAppId() {
        //
        return "";
    }

    /**
     * 获取服务器名称
     *
     * @return
     * @throws java.net.UnknownHostException
     * @throws java.net.SocketException
     */
    public static String getLocalHostName() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostName();
        } catch (Exception e) {
            logger.error("获取服务器名称异常：{}", e.getMessage());
            //获取不到则返回空
            return "";
        }
    }
}
