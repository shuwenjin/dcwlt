package com.dcits.dcwlt.job;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.dcits.dcwlt.common.security.annotation.EnableCustomConfig;
import com.dcits.dcwlt.common.security.annotation.EnableRyFeignClients;
import com.dcits.dcwlt.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 定时任务
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2   
@EnableRyFeignClients
@SpringCloudApplication
public class DcwltJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltJobApplication.class, args);
        System.out.println("#####  定时任务模块启动成功  #####\n");
    }
}
