package com.dcits.dcwlt.pay.batch;
/**
 * 支付批量应用
 */

import com.dcits.dcwlt.common.security.annotation.EnableCustomConfig;
import com.dcits.dcwlt.common.security.annotation.EnableRyFeignClients;
import com.dcits.dcwlt.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringCloudApplication
@ComponentScan(value = "com.dcits.dcwlt")
public class DcwltPayBatchApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(DcwltPayBatchApplication.class, args);
        System.out.println("#####  支付批量模块启动成功  #####\n");
    }
}
