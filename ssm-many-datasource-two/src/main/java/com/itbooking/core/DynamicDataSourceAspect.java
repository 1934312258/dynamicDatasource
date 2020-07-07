package com.itbooking.core;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Created by IDEA
 * User: 徐柯老师
 * Date: 2018-07-07 13:39
 * Desc: 定义选择数据源切面
 */
public class DynamicDataSourceAspect {

    private static final Logger logger = Logger.getLogger(DynamicDataSourceAspect.class);

    public void pointCut(){};

    public void before(JoinPoint point)
    {
        // 拿到目标对象
        Object target = point.getTarget();
        // 拿到执行的方法
        String methodName = point.getSignature().getName();
        // 拿到执行接口
        Class<?>[] clazz = target.getClass().getInterfaces();
        // 拿到执行的参数
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            // 根据方法名和参数，返回当前Method对象
            Method method = clazz[0].getMethod(methodName, parameterTypes);
            // 如果方法不等于null, 并且你方法的上面是否加了@DataSource的注解
            if (method != null && method.isAnnotationPresent(DataSource.class)) {
                // 获取@DataSource注解对象，
                DataSource data = method.getAnnotation(DataSource.class);
                // 根据@DataSource注解对象，获取注解信息 data.value() 就是你指定的枚举类的key
                DynamicDataSourceHolder.putDataSource(data.value());
                logger.info(String.format("============>执行的方法是：%s, 使用的数据源是：%s",methodName,data.value()));
            }
        } catch (Exception e) {
            logger.error(String.format("Choose DataSource error, method:%s, msg:%s", methodName, e.getMessage()));
        }
    }

    public void after(JoinPoint point) {
        DynamicDataSourceHolder.clearDataSource();
    }
}