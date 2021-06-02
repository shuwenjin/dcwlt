package com.dcits.dcpsclient;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class DcpsClientApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("2020-10-10T09:30:30");
        System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss"));
    }

}
