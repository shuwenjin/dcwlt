package com.dcits.dcwlt.common.pay.util;

import com.dcits.dcwlt.common.pay.exception.PlatformError;
import com.dcits.dcwlt.common.pay.exception.PlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 *
 * @Time 2019年12月23日下午7:14:38
 * @Version <p>Description:资源处理工具类 </p>
 */
public class ResUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResUtil.class);

    /**
     * 加载对象
     *
     * @param className 类名
     * @return 对象
     * @throws Error
     */
    public static <T> T loadObject(String className) {
        try {
            @SuppressWarnings("unchecked")
            Class<T> objClass = (Class<T>) Class.forName(className);
            return objClass.newInstance();
        } catch (ReflectiveOperationException ex) {
            throw new PlatformException(PlatformError.LOADCLASS_ERROR);
        }
    }

    /**
     * 关闭资源
     *
     * @param resList 资源列表
     */
    public static void closeResource(AutoCloseable... resList) {
        // 循环处理
        for (AutoCloseable res : resList) {
            // 非空
            if (res != null) {
                // 关闭
                try {
                    res.close();
                } catch (Exception ex) {
                    logger.error("关闭资源出错，忽略" + ex);
                }
            }
        }
    }

    /**
     * @param ctx       请求入参
     * @param funcName  方法名
     * @param className 类名
     * @return
     * @Description: 回调函数
     */
    public static Object callBack(Object ctx, String funcName, String className) {
        Class<?> aClass;
        Object obj = null;
        try {
            aClass = Class.forName(className);
            Method method = aClass.getMethod(funcName, Object.class);
            obj = method.invoke(null, ctx);
        } catch (ClassNotFoundException e) {
            logger.error("回调失败，未存在类：%s->%s (%s)", className, funcName, e);
            throw PlatformException.getInstance(e);
        } catch (IllegalAccessException e) {
            logger.error("回调失败：%s->%s (%s)", className, funcName, e);
            throw PlatformException.getInstance(e);
        } catch (NoSuchMethodException e) {
            logger.error("回调失败，未存回调方法：%s->%s (%s)", className, funcName, e);
            throw PlatformException.getInstance(e);
        } catch (Exception e) {
            PlatformException ee = null;
            try {
                logger.error("回调方法存在异常：%s->%s (%s)", className, funcName, e);
                Throwable thr = e.getCause();
                if (thr instanceof PlatformException) {
                    ee = (PlatformException) e.getCause();
                    logger.info(ee.getErrorCode() + ":" + ee.getErrorMsg());
                } else {
                    logger.error(e.getMessage());
                    ee = new PlatformException(PlatformError.SYSTEM_ERROR);
                }

            } catch (Exception e1) {
                ee = new PlatformException(PlatformError.OTHER_BUSI_ERROR);
            }
            throw ee;
        }
        return obj;
    }
}
