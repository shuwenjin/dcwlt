package com.dcits.dcwlt.job.task;

import java.io.Serializable;

/**
 * 任务执行结果对象
 *
 * @author zhangyd
 */
public class TaskResult implements Serializable {
    /** 异常信息 */
    private String message;
    /** 是否成功 */
    private boolean isSuccess;

    /** 调用目标字符串 */
    private String invokeTarget;

    /** 返回结果 */
    private Object ret;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getInvokeTarget() {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }

    public Object getRet() {
        return ret;
    }

    public void setRet(Object ret) {
        this.ret = ret;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                ", invokeTarget='" + invokeTarget + '\'' +
                ", ret=" + ret +
                '}';
    }
}
