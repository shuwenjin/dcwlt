package com.dcits.dcwlt.dcepgw.schedule;

import com.dcits.dcwlt.dcepgw.entity.PayCommCerts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 定时任务
 */

@Configuration
@EnableScheduling
public class ScheduleTask {

    @Autowired
    private RestTemplate restTemplate;

    //url
    private static final String PAY_BATCH_GETVALIDLIST = "http://localhost:9401/certs/getValidlist";

    public static final ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    @PostConstruct
    @Scheduled(cron = "0 */1 * * * ?")
    public void getValidlist() {
        PayCommCerts[] payCommCerts = restTemplate.getForObject(PAY_BATCH_GETVALIDLIST, PayCommCerts[].class);
        List<PayCommCerts> payCommCertsList = Arrays.asList(payCommCerts);
        payCommCertsList.forEach(item->
                map.put(item.getCertNo(),item)
        );
    }

}
