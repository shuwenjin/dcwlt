package com.dcits.dcwlt.dcepgw.utils;


import com.dcits.dcwlt.dcepgw.exception.GwException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
public class RestUtil {


    public static String  getRsp(String msg, String uri) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        String rspMsg = "";
        try {
            HttpPost httpPost = new HttpPost(uri);
            StringEntity stringEntity = new StringEntity(msg, "UTF-8");
            httpPost.setEntity(stringEntity);
            httpPost.setHeader("Content-Type","application/json;charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectionRequestTimeout(20000).setConnectTimeout(20000).build();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                rspMsg = EntityUtils.toString(entity, "utf-8");
            }
        } catch (Exception e) {
            log.error("通讯处理异常！", e);
            throw new GwException(GwException.CODE_CALL,"通讯异常！");
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rspMsg;
    }
}

