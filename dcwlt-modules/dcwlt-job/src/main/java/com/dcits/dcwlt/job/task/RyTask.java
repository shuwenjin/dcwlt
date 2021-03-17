package com.dcits.dcwlt.job.task;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.utils.DateUtils;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 *
 * 注意
 * 所有方法的返回值类型必须为 TaskResult
 * 所有方法只能throws TaskException类型异常
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
    public TaskResult ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) throws Exception
    {
        String params = s;
        if(DateUtils.isVaildPattern(params)) {
            params = "'" + DateUtils.dateTime(params) + "'";
        }
        TaskResult result = new TaskResult();
        // 调用目标字符串
        result.setInvokeTarget("ryTask.ryMultipleParams(" + params + "," + b + "," + l + "L," + d + "D," + i + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行多参方法：ryTask.ryMultipleParams(" + s + "," + b + "," + l + ",L" + d + "D," + i + ")");

        double random = Math.random();
        if (random > 0.6) {
            result.setMessage("test ryTask.ryMultipleParams Exception");
            throw new Exception(JSONObject.toJSONString(result));
        }

        result.setRet(new Double(random));
        result.setSuccess(true);
        return result;
    }

    public TaskResult ryParams(String str) throws Exception
    {
        String params = str;
        if(DateUtils.isVaildPattern(params)) {
            params = "'" + DateUtils.dateTime(params) + "'";
        }
        TaskResult result = new TaskResult();
        // 调用目标字符串
        result.setInvokeTarget("ryTask.ryParams(" + params + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行有参方法：" + "ryTask.ryParams(" + str + ")");

        double random = Math.random();
        if (random > 0.1) {
            result.setMessage("test ryTask.ryParams Exception");
            throw new Exception(JSONObject.toJSONString(result));
        }

        result.setRet(new Double(random));
        result.setSuccess(true);
        return result;
    }

    public TaskResult ryNoParams() throws Exception {
        TaskResult result = new TaskResult();
        // 调用目标字符串
        result.setInvokeTarget("ryTask.ryNoParams()");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行无参方法");

        double random = Math.random();
        if (random > 0.9) {
            result.setMessage("test ryTask.ryNoParams Exception");
            throw new Exception(JSONObject.toJSONString(result));
        }

        result.setRet(new Double(random));
        result.setSuccess(true);
        return result;
    }
}
