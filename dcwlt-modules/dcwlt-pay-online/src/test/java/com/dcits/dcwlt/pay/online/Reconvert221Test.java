package com.dcits.dcwlt.pay.online;

import com.dcits.dcwlt.common.pay.tradeflow.TradeContext;
import com.dcits.dcwlt.pay.online.flow.receive.Reconvert221RTradeFlow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class Reconvert221Test {

    @Mock
    private Reconvert221RTradeFlow reconvert221RTradeFlow;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void do221() {
        reconvert221RTradeFlow.initRspMsg(new TradeContext<>());
    }
}
