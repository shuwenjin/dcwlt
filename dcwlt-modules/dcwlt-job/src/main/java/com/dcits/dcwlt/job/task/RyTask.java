package com.dcits.dcwlt.job.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.domain.TaskResult;
import com.dcits.dcwlt.common.core.utils.DateUtils;
import com.dcits.dcwlt.common.core.utils.StringUtils;
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
    /**
     * 多个参数调用实例
     * @param s String
     * @param b Boolean
     * @param l Long
     * @param d Double
     * @param i Integer
     * @return
     * @throws Exception
     */
    public TaskResult ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) throws Exception
    {
        TaskResult result = new TaskResult();
        // 如果是日期，解析成日期字符串；否则返回原字符串
        String params = DateUtils.parseTaskDate(s);

        // 业务逻辑开始前，将不同类型的参数按照com.dcits.dcwlt.job.util.JobInvokeUtil.getMethodParams方法将参数列表转成字符串
        // 定时任务中的字符串必须用单引号包裹，例如 'sss'， Boolean直接写 true/fase, Long已L结尾，Double已D结尾，Integer直接写
        // 调用目标字符串， 格式 @Component("ryTask")中名称.方法名（参数列表)
        // 把拼接好的字符串设置到调用目标字符串中， 供失败重试任务使用
        // 例如：
        // 把字符串用单引号包裹
        result.setInvokeTarget("ryTask.ryMultipleParams(" + StringUtils.toTaskString(params) + "," + b + "," + l + "L," + d + "D," + i + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行多参方法：ryTask.ryMultipleParams('" + s + "'," + b + "," + l + ",L" + d + "D," + i + ")");

        /**
         * 业务逻辑
         * ......
         * ......
         */

        /**
         * 异常处理，抛出异常信息必须将TaskResult转成JSONString
         */
        double random = Math.random();
        if (random > 0.6) {
            result.setMessage("test ryTask.ryMultipleParams Exception");
            // 必须转成JSONString
            throw new Exception(JSONObject.toJSONString(result));
        }

        result.setRet(new Double(random));
        result.setSuccess(true);
        return result;
    }

    /**
     * 单个参数调用示例
     * @param str String
     * @return TaskResult
     * @throws Exception
     */
    public TaskResult ryParams(String str) throws Exception
    {
        // 如果是日期，解析成日期字符串；否则返回原字符串
        String params = DateUtils.parseTaskDate(str);
        TaskResult result = new TaskResult();
        // 调用目标字符串
        result.setInvokeTarget("ryTask.ryParams(" + StringUtils.toTaskString(params) + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行有参方法：" + "ryTask.ryParams(" + StringUtils.toTaskString(str) + ")");

        double random = Math.random();
        if (random > 0.1) {
            result.setMessage("test ryTask.ryParams Exception");
            throw new Exception(JSONObject.toJSONString(result));
        }

        result.setRet(new Double(random));
        result.setSuccess(true);
        return result;
    }

    /**
     * 无参数调用示例
     * @return TaskResult
     * @throws Exception
     */
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

    /**
     * JSONObject参数调用示例， 只能传一个参数
     * @param json JSONObject
     * @return TaskResult
     * @throws Exception
     */
    public TaskResult JSONObjectParams(JSONObject json) throws Exception
    {
        TaskResult result = new TaskResult();
        // 调用目标字符串
        result.setInvokeTarget("ryTask.JSONObjectParams("+ json.toJSONString() + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行JSONObject参数方法： ryTask.JSONObjectParams("+ json.toJSONString() + ")");

        double random = Math.random();
        if (random > 0.5) {
            result.setMessage("test ryTask.JSONObjectParams Exception");
            throw new Exception(JSONObject.toJSONString(result));
        }

        result.setRet(new Double(random));
        result.setSuccess(true);
        return result;
    }

    /**
     * JSONArray参数调用示例， 只能传一个参数
     * @param json JSONObject
     * @return TaskResult
     * @throws Exception
     */
    public TaskResult JSONArrayParams(JSONArray json) throws Exception
    {
        TaskResult result = new TaskResult();
        // 调用目标字符串
        result.setInvokeTarget("ryTask.JSONArrayParams("+ json.toJSONString() + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行JSONArray参数方法： ryTask.JSONArrayParams("+ json.toJSONString() + ")");

        double random = Math.random();
        if (random > 0.5) {
            result.setMessage("test ryTask.JSONArrayParams Exception");
            throw new Exception(JSONObject.toJSONString(result));
        }

        result.setRet(new Double(random));
        result.setSuccess(true);
        return result;
    }
}
