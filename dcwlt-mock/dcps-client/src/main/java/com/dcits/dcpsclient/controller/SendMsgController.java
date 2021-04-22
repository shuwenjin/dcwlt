package com.dcits.dcpsclient.controller;

import com.dcits.dcpsclient.util.FilesUtil;
import com.dcits.dcpsclient.util.MsgUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author xingjj
 * @Description TODO
 * @Date 2021/4/13 10:24
 * @Version 1.0
 */
@RestController
public class SendMsgController {
    @GetMapping("/221")
    public String do221(){
        String reqMsg = MsgUtil.getReqMsg("dcep.221.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    @GetMapping("/225")
    public String do225(){
        String reqMsg = MsgUtil.getReqMsg("dcep.225.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
//        String rspMsg = getRsp(reqMsg);
//        System.out.println("交易响应报文：");
//        System.out.println(rspMsg);
        return "";
    }

    @GetMapping("/227")
    public String do227(){
        String reqMsg = MsgUtil.getReqMsg("dcep.227.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    @GetMapping("/411")
    public String do411(){
        String reqMsg = MsgUtil.getReqMsg("dcep.411.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    /**
     *身份认证
     * @return
     */
    @GetMapping("/433/01")
    public String do43301(){
        String reqMsg = MsgUtil.getReqMsg("dcep.433.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    /**
     * 身份确认
     * @return
     */
    @GetMapping("/433/02")
    public String do43302(){
        String reqMsg = MsgUtil.getReqMsg("dcep.433.001.01_02.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    /**
     * 解约申请
     * @return
     */
    @GetMapping("/433/03")
    public String do43303(){
        String reqMsg = MsgUtil.getReqMsg("dcep.433.001.01_03.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    @GetMapping("/909")
    public String do909(){
        String reqMsg = MsgUtil.getReqMsg("dcep.909.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    @GetMapping("/401")
    public String do401(){
        String reqMsg = MsgUtil.getReqMsg("dcep.401.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    @GetMapping("/711")
    public String do711(){
        String reqMsg = MsgUtil.getReqMsg("dcep.711.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    @GetMapping("/713")
    public String do713(){
        String reqMsg = MsgUtil.getReqMsg("dcep.713.001.01.xml");
        System.out.println("交易请求报文：");
        System.out.println(reqMsg);
        String rspMsg = getRsp(reqMsg);
        System.out.println("交易响应报文：");
        System.out.println(rspMsg);
        return rspMsg;
    }

    //重新加载报文
    @GetMapping("/reload")
    public String reload(){
        FilesUtil.getInstance().reload();
        return "success";
    }


    public static String  getRsp(String reqMsg) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        String rspMsg = "";
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8008/dcep");
            StringEntity stringEntity = new StringEntity(reqMsg, "UTF-8");
            httpPost.setEntity(stringEntity);
            //httpPost.setHeader("Content-Type","application/json;charset=utf-8");
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectionRequestTimeout(20000).setConnectTimeout(20000).build();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                System.out.println(response.getStatusLine());
                rspMsg = EntityUtils.toString(entity, "utf-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
