package com.dcits.dcwlt.dcepgw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 网关启动程序
 *
 * @author yunghugh
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DcwltGWApplication {
    public static void main(String[] args) {
        SpringApplication.run(DcwltGWApplication.class, args);
        System.out.println("#####  dcep网关启动成功  #####\n");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
