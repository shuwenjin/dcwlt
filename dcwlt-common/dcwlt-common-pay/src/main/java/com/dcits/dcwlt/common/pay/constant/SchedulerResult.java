package com.dcits.dcwlt.common.pay.constant;

import com.alibaba.fastjson.JSON;

/**
 * @author fsr
 * @data 2020 -03-18 11:01
 **/
public class SchedulerResult {

    private SchedulerResult() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SUCCESS_CODE = "0";
    public static final String FAIL_CODE = "99";
    public static final String NOT_DATE_CODE = "01";

    private static Result SUCCESS = new Result(SUCCESS_CODE,"处理成功");
    private static Result FAIL = new Result(FAIL_CODE,"处理失败");
    private static Result NOT_DATE = new Result(NOT_DATE_CODE,"必须输入date（执行日期）参数");





    public static String succ(){
        return JSON.toJSONString(SUCCESS);
    }

    public static String fail(){
        return JSON.toJSONString(FAIL);
    }


    public static String failForNotDate(){
        return JSON.toJSONString(NOT_DATE);
    }

    public static String result(String code,String msg){
        return JSON.toJSONString(new Result(code,msg));
    }

    public static String succMsg(String msg){
        return JSON.toJSONString(new Result(SUCCESS_CODE,msg));
    }

    public static String failMsg(String msg){
        return JSON.toJSONString(new Result(FAIL_CODE,msg));
    }






}
