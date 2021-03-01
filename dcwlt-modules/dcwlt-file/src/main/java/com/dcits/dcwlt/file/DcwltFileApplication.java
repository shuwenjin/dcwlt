package com.dcits.dcwlt.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.dcits.dcwlt.common.swagger.annotation.EnableCustomSwagger2;

/**
 * 文件服务
 * 
 * @author ruoyi
 */
@EnableCustomSwagger2
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DcwltFileApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltFileApplication.class, args);
        System.out.println("#####  文件服务模块启动成功  #####\n");
    }
}
