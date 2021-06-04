package com.dcits.dcwlt.dcepgw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    /**
     * 签名加密开关
     */
    @Value("${app.isSign:false}")
    private boolean isSign;

    public boolean getIsSign() {
        return isSign;
    }
}
