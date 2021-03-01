package com.dcits.dcwlt.gen;

import com.dcits.dcwlt.common.security.annotation.EnableCustomConfig;
import com.dcits.dcwlt.common.security.annotation.EnableRyFeignClients;
import com.dcits.dcwlt.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 代码生成
 * 
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
public class DcwltGenApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltGenApplication.class, args);
        System.out.println("#####  代码生成模块启动成功  #####\n");
    }
}
