package com.dcits.dcpsclient.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xingjj Ashin
 * @Description TODO
 * @Date 2021/4/13 10:32
 * @Version 1.0
 */
public class FilesUtil {

    private static FilesUtil filesUtil = new FilesUtil();

    private Map<String, byte[]> msgMap = new HashMap<>();

    private FilesUtil() {
    }

    public static FilesUtil getInstance() {
        return filesUtil;
    }

    public byte[] getMsgbyte(String msgtype) {
        return msgMap.get(msgtype);
    }

    public Map<String, byte[]> getmsgMap() {
        return msgMap;
    }


    public void reload() {
        FilesUtil.getInstance().getmsgMap().clear();
    }


    public String getMsg(String msgType) {
        String fileName ="/msg" + File.separator + msgType;
        InputStream fis = null;
        try {
            fis = FilesUtil.class.getResourceAsStream(fileName);
            int length = fis.available();
            byte[] data = new byte[length];
            fis.read(data);
            fis.close();

            return new String(data, "UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
