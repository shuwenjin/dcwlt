package com.dcits.dcwlt.common.pay.enums;

public enum EnvironmentEmum {
    DEV(0,"测试环境"),
    PROD(1,"生产环境"),
    PROD_COM(2,"生产压测环境");

    private int env;

    private String msg;

     EnvironmentEmum(int env, String msg) {
        this.env = env;
        this.msg = msg;
    }

    public int getEnv() {
        return env;
    }

    public void setEnv(int env) {
        this.env = env;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
