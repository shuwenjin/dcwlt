package com.dcits.dcwlt.pay.online.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.pay.constant.AppConstant;
import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.exception.TransException;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqBody;
import com.dcits.dcwlt.pay.api.domain.dcep.DCEPReqDTO;
import com.dcits.dcwlt.pay.online.service.TransInService;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author
 * @Time 2021/1/3 17:18
 * @Version 1.0
 * Description:主动请求互联互通服务组件
 */
@Service
public class DcepSendService {
    private static final Logger logger = LoggerFactory.getLogger(DcepSendService.class);


    @Autowired
    private TransInService transInService;

    @Value("${dcepgw.server-addr}")
    private String dcepgw_addr;

    //核心服务器挡板
    @Value("${core.server}")
    private String core_server;
    /**
     * 请求互联互通
     *
     * @param reqDTO
     * @param <T>
     * @return
     */
    public <T extends DCEPReqBody> JSONObject sendDcep(DCEPReqDTO<T> reqDTO) {
        logger.info("请求报文：{}" + reqDTO.toString());

        //1、交易记录
        /*if (null != reqDTO.getDcepHead()) {
            transInService.saveMsg(reqDTO.getDcepHead(), AppConstant.DIRECT_SEND);
        }*/

//        //2、调用金融开发平台服务化接口，请求互联互通
//        String srvcCode = reqDTO.getHead().getSrvcCode();
//        String rspMsg = RpcHttpJsonUtil.executeExt(srvcCode, JsonUtil.toJSONString(reqDTO));
        //String srvcCode = reqDTO.getDcepHead().getMsgTp();
        String uri = dcepgw_addr;
        String reqMsg = JSONObject.toJSONString(reqDTO);
        String rspMsg = getRsp(reqMsg, uri);
        //3、响应
        return JSONObject.parseObject(rspMsg);
        //return JSONObject.parseObject("1");
    }

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
                //System.out.println(response.getStatusLine());
                rspMsg = EntityUtils.toString(entity, "utf-8");
            }
        } catch (Exception e) {
            logger.error("请求发送失败！地址：{}",uri,e);
            throw new TransException(PlatformError.COMM_ABEND.getErrorCode(), PlatformError.COMM_ABEND.getErrorMsg());
        }  finally {
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

    /**
     * get方式请求核心挡板服务
     * @param username
     * @return
     */
    public JSONObject getNonce(String username) {
        String url = core_server + username;
        JSONObject jsonObject = null;
        String nonce = null;
        //发送get请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            try {
                //获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String content = EntityUtils.toString(entity);
                    jsonObject = JSONObject.parseObject(content);
                }
            } finally {
                response.close();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

}
