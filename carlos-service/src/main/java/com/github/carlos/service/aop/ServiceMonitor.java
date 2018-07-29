package com.github.carlos.service.aop;


import com.github.carlos.common.constant.SysConstant;
import com.github.carlos.common.utils.ThreadLocalContext;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @author: ylong.chen@qunar.com
 * @Date: 2018/7/17 12:15
 * @description: 所有controller方法执行时间监控类
 */
@Service
@Aspect
@Order(1)
public class ServiceMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMonitor.class);

    /**
     * 在Controller的方法上生效
     *
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution( public * *(..))&&@within(org.springframework.stereotype.Service)")
    public Object methodMonitor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        String key = className + "." + methodName;
        // controller 层监控 GlobalControllerExceptionHandler 记录请求异常类型
        ThreadLocalContext.set(SysConstant.REQUEST_METHOD, key);
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            throw e;
        } finally {
            LOGGER.info("INVOKE->{} 耗时:{}ms", key, stopwatch.elapsed(TimeUnit.MILLISECONDS));
        }
    }

}
