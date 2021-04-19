package com.dcits.dcwlt.auth;

import com.dcits.dcwlt.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 认证授权中心
 * 
 * @author ruoyi
 */
@EnableRyFeignClients
@SpringCloudApplication
public class DcwltAuthApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltAuthApplication.class, args);
        System.out.println("#####  认证授权中心启动成功  #####\n");
    }
}
