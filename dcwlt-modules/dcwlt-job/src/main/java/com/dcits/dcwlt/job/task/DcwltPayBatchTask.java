package com.dcits.dcwlt.job.task;

import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.domain.TaskResult;
import com.dcits.dcwlt.common.core.utils.DateUtils;
import com.dcits.dcwlt.common.core.utils.StringUtils;
import com.dcits.dcwlt.system.api.RemotePayBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * dcwlt-pay-bathc 模块定时任务调度测试
 *
 * 注意
 * 所有方法的返回值类型必须为 TaskResult
 * 所有方法只能throws TaskException类型异常
 *
 * @author zhangyd
 */

@Component("dcwltPayBatchTask")
public class DcwltPayBatchTask {
    @Autowired
    RemotePayBatchService remotePayBatchService;

    public TaskResult statistics(String reportDate) throws Exception {
        TaskResult result = new TaskResult();
        // 解析成日期字符串
        String params = DateUtils.parseTaskDate(reportDate);
        // 把字符串用单引号包裹
        result.setInvokeTarget("dcwltPayBatchTask.statistics(" + StringUtils.toTaskString(params) + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行方法：dcwltPayBatchTask.statistics(" + StringUtils.toTaskString(reportDate) + ")");

        try {
            remotePayBatchService.statistics(params);
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            // 必须转成JSONString
            throw new Exception(JSONObject.toJSONString(result));
        }

        // 执行结果，有返回值设置返回值，没有不设置
        // result.setRet();
        // 执行成功
        result.setSuccess(true);
        return result;
    }

    public TaskResult checkData(String settleDate/*${yyyyMMdd}*/) throws Exception {
        TaskResult result = new TaskResult();
        // 解析成日期字符串
        String params = DateUtils.parseTaskDate(settleDate);
        // 把字符串用单引号包裹
        result.setInvokeTarget("dcwltPayBatchTask.checkData(" + StringUtils.toTaskString(params) + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行方法：dcwltPayBatchTask.checkData(" + StringUtils.toTaskString(settleDate) + ")");

        String ret = null;
        try {
            ret = remotePayBatchService.schedulerController(params, "B" + params + "1600", "ImportDataService");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            // 必须转成JSONString
            throw new Exception(JSONObject.toJSONString(result));
        }

        // 执行结果
        result.setRet(ret);
        if (null == ret) {
            result.setSuccess(false);
        } else {
            // 执行成功
            result.setSuccess(true);
        }
        return result;
    }
    public TaskResult partyToEffective(String settleDate/*${yyyyMMdd}*/) throws Exception {
        TaskResult result = new TaskResult();
       // 解析成日期字符串
        String params = DateUtils.parseTaskDate(settleDate);
        // 把字符串用单引号包裹
        result.setInvokeTarget("partyToEffective(" + StringUtils.toTaskString(params) + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行方法：partyToEffective(" + StringUtils.toTaskString(params) + ")");

        String ret = null;
        try {
            ret = remotePayBatchService.schedulerController(params, null, "PartyToEffectiveService");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            // 必须转成JSONString
            throw new Exception(JSONObject.toJSONString(result));
        }

        // 执行结果
        result.setRet(ret);
        if (null == ret) {
            result.setSuccess(false);
        } else {
            // 执行成功
            result.setSuccess(true);
        }
        return result;
    }
    public TaskResult partyToEffectiveAuth(String settleDate/*${yyyyMMdd}*/) throws Exception {
        TaskResult result = new TaskResult();
        // 解析成日期字符串
         String params = DateUtils.parseTaskDate(settleDate);
        // 把字符串用单引号包裹
        result.setInvokeTarget("partyToEffect-iveAuth(" + StringUtils.toTaskString(params) + ")");
        // 初始化执行结果为失败
        result.setSuccess(false);

        System.out.println("执行方法：partyToEffectiveAuth(" + StringUtils.toTaskString(params) + ")");

        String ret = null;
        try {
            ret = remotePayBatchService.schedulerController(params, null, "RightChangeService");
        } catch (Exception e) {
            result.setMessage(e.getMessage());
            // 必须转成JSONString
            throw new Exception(JSONObject.toJSONString(result));
        }

        // 执行结果
        result.setRet(ret);
        if (null == ret) {
            result.setSuccess(false);
        } else {
            // 执行成功
            result.setSuccess(true);
        }
        return result;
    }
}
