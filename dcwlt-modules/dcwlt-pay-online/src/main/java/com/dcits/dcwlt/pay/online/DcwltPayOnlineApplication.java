package com.dcits.dcwlt.pay.online;
/**
 * 支付联机应用
 */

import com.dcits.dcwlt.common.security.annotation.EnableCustomConfig;
import com.dcits.dcwlt.common.security.annotation.EnableRyFeignClients;
import com.dcits.dcwlt.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
public class DcwltPayOnlineApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltPayOnlineApplication.class, args);
        System.out.println("#####  支付联机模块启动成功  #####\n");
    }
}
