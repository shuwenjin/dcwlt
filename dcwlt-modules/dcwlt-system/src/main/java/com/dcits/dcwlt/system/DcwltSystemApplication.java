package com.dcits.dcwlt.system;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import com.dcits.dcwlt.common.security.annotation.EnableCustomConfig;
import com.dcits.dcwlt.common.security.annotation.EnableRyFeignClients;
import com.dcits.dcwlt.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 系统模块
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
public class DcwltSystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltSystemApplication.class, args);
        System.out.println("#####  系统模块启动成功  #####\n");
    }
}
