package com.dcits.dcwlt.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 监控中心
 * 
 * @author ruoyi
 */
@EnableAdminServer
@SpringCloudApplication
public class DcwltMonitorApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltMonitorApplication.class, args);
        System.out.println("#####  监控中心启动成功  #####\n");
    }
}
