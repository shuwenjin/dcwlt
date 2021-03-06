package com.dcits.dcwlt.job.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dcits.dcwlt.common.core.utils.DateUtils;
import com.dcits.dcwlt.common.core.utils.SpringUtils;
import com.dcits.dcwlt.common.core.utils.StringUtils;
import com.dcits.dcwlt.job.domain.SysJob;

/**
 * 任务执行工具
 *
 * @author ruoyi
 */
public class JobInvokeUtil
{
    /**
     * 执行方法
     *
     * @param sysJob 系统任务
     */
    public static Object invokeMethod(SysJob sysJob) throws Exception
    {
        Object ret = null;
        String invokeTarget = sysJob.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        if (!isValidClassName(beanName))
        {
            Object bean = SpringUtils.getBean(beanName);
            ret = invokeMethod(bean, methodName, methodParams);
        }
        else
        {
            Object bean = Class.forName(beanName).newInstance();
            ret = invokeMethod(bean, methodName, methodParams);
        }
        return ret;
    }

    /**
     * 手动执行方法
     *
     * @param invokeTarget 调用目标字符串
     */
    public static Object invokeMethod(String invokeTarget) throws Exception
    {
        Object ret = null;
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);

        if (!isValidClassName(beanName))
        {
            Object bean = SpringUtils.getBean(beanName);
            ret = invokeMethod(bean, methodName, methodParams);
        }
        else
        {
            Object bean = Class.forName(beanName).newInstance();
            ret = invokeMethod(bean, methodName, methodParams);
        }
        return ret;
    }

    /**
     * 调用任务方法
     *
     * @param bean 目标对象
     * @param methodName 方法名称
     * @param methodParams 方法参数
     */
    private static Object invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException
    {
        Object ret = null;

        if (StringUtils.isNotNull(methodParams) && methodParams.size() > 0)
        {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            ret = method.invoke(bean, getMethodParamsValue(methodParams));
        }
        else
        {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            ret = method.invoke(bean);
        }

        return ret;
    }

    /**
     * 校验是否为为class包名
     * 
     * @param invokeTarget 名称
     * @return true是 false否
     */
    public static boolean isValidClassName(String invokeTarget)
    {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取bean名称
     * 
     * @param invokeTarget 目标字符串
     * @return bean名称
     */
    public static String getBeanName(String invokeTarget)
    {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     * 
     * @param invokeTarget 目标字符串
     * @return method方法
     */
    public static String getMethodName(String invokeTarget)
    {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 获取method方法参数相关列表
     * 
     * @param invokeTarget 目标字符串
     * @return method方法相关参数列表
     */
    public static List<Object[]> getMethodParams(String invokeTarget)
    {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr))
        {
            return null;
        }

        List<Object[]> classs = new LinkedList<>();
        // 解析 JSONObject 类型
        if (StringUtils.startsWith(methodStr, "{") && StringUtils.endsWith(methodStr, "}")) {
            classs.add(new Object[] { JSONObject.parseObject(methodStr), JSONObject.class });
            return classs;
        }
        // 解析 JSONArray 类型
        if (StringUtils.startsWith(methodStr, "[") && StringUtils.endsWith(methodStr, "]")) {
            classs.add(new Object[] { JSONArray.parseArray(methodStr), JSONArray.class });
            return classs;
        }
        String[] methodParams = methodStr.split(",");
        for (int i = 0; i < methodParams.length; i++)
        {
            String str = StringUtils.trimToEmpty(methodParams[i]);
            // String字符串类型，以'开始，以'结束
            if (StringUtils.startsWith(str, "'") && StringUtils.endsWith(str, "'"))
            {
                classs.add(new Object[] { StringUtils.replace(str, "'", ""), String.class });
            }
            // String字符串类型，自定义日期格式 例如： ${yyyy-MM-dd}
            else if (StringUtils.startsWith(str, "${") && StringUtils.endsWith(str, "}"))
            {
                classs.add(new Object[] { str, String.class });
            }
            // boolean布尔类型，等于true或者false
            else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false"))
            {
                classs.add(new Object[] { Boolean.valueOf(str), Boolean.class });
            }
            // long长整形，包含L
            else if (StringUtils.containsIgnoreCase(str, "L"))
            {
                classs.add(new Object[] { Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class });
            }
            // double浮点类型，包含D
            else if (StringUtils.containsIgnoreCase(str, "D"))
            {
                classs.add(new Object[] { Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class });
            }
            // 其他类型归类为整形
            else
            {
                classs.add(new Object[] { Integer.valueOf(str), Integer.class });
            }
        }
        return classs;
    }

    /**
     * 获取参数类型
     * 
     * @param methodParams 参数相关列表
     * @return 参数类型列表
     */
    public static Class<?>[] getMethodParamsType(List<Object[]> methodParams)
    {
        Class<?>[] classs = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams)
        {
            classs[index] = (Class<?>) os[1];
            index++;
        }
        return classs;
    }

    /**
     * 获取参数值
     * 
     * @param methodParams 参数相关列表
     * @return 参数值列表
     */
    public static Object[] getMethodParamsValue(List<Object[]> methodParams)
    {
        Object[] classs = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams)
        {
            classs[index] = (Object) os[0];
            index++;
        }
        return classs;
    }

    /**
     * 将参数列表中的日期格式串解析成日期字符串
     * @param invokeTarget
     * @return
     */
    public static String parseMethodParams(String invokeTarget)
    {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr))
        {
            return "";
        }

        // 解析 JSONObject 类型
        if (StringUtils.startsWith(methodStr, "{") && StringUtils.endsWith(methodStr, "}")) {
            return methodStr;
        }
        // 解析 JSONArray 类型
        if (StringUtils.startsWith(methodStr, "[") && StringUtils.endsWith(methodStr, "]")) {
            return methodStr;
        }
        String[] methodParams = methodStr.split(",");
        List<String> methodParamsParsed = new LinkedList<>();
        for (int i = 0; i < methodParams.length; i++)
        {
            String str = StringUtils.trimToEmpty(methodParams[i]);
            // String字符串类型，以'开始，以'结束
            if (StringUtils.startsWith(str, "'") && StringUtils.endsWith(str, "'"))
            {
                str = StringUtils.replace(str, "'", "");
                methodParamsParsed.add(StringUtils.toTaskString(DateUtils.parseTaskDate(str)));
            }
            // String字符串类型，自定义日期格式 例如： ${yyyy-MM-dd}
            else if (StringUtils.startsWith(str, "${") && StringUtils.endsWith(str, "}"))
            {
                methodParamsParsed.add(StringUtils.toTaskString(DateUtils.parseTaskDate(str)));
            }
            // 其他类型
            else
            {
                methodParamsParsed.add(str);
            }
        }
        return StringUtils.join(methodParamsParsed, ',');
    }

    /**
     * 将调用目标字符串中的日期格式串解析成日期字符串
     * @param invokeTarget
     * @return
     */
    public static String parseInvokeTarget(String invokeTarget) {
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        String params = parseMethodParams(invokeTarget);
        return beanName + "." + methodName + "(" + params + ")";
    }
}
