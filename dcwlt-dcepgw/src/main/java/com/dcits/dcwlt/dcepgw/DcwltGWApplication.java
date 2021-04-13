package com.dcits.dcwlt.dcepgw;

import cn.hutool.crypto.asymmetric.SM2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动程序
 * 
 * @author yunghugh
 */
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class DcwltGWApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltGWApplication.class, args);
        System.out.println("#####  dcep网关启动成功  #####\n");
    }
}
