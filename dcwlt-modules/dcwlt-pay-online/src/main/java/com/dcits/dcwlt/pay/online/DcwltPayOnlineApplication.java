package com.dcits.dcwlt.pay.online;
/**
 * 支付联机应用
 */

import com.dcits.dcwlt.common.pay.util.ApplicationContextProvider;
import com.dcits.dcwlt.common.security.annotation.EnableCustomConfig;
import com.dcits.dcwlt.common.security.annotation.EnableRyFeignClients;
import com.dcits.dcwlt.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ImportResource;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
@ImportResource("classpath:applicationContext.xml")
public class DcwltPayOnlineApplication {
    public static void main(String[] args)
    {
        ApplicationContextProvider.applicationContext = SpringApplication.run(DcwltPayOnlineApplication.class, args);

        System.out.println("#####  支付联机模块启动成功  #####\n");
    }
}
