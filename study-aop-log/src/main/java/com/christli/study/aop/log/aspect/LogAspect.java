package com.christli.study.aop.log.aspect;

import com.christli.study.aop.log.annotation.Log;
import com.christli.study.aop.log.domain.SysOperationLog;
import com.christli.study.aop.log.service.AsyncLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private AsyncLogService asyncLogService;

    // 配置织入点
    @Pointcut("@annotation(com.christli.study.aop.log.annotation.Log)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            SysOperationLog operationLog = new SysOperationLog();
            operationLog.setStatus(0);
            if (e != null) {
                operationLog.setStatus(1);
                operationLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operationLog.setMethod(className + "." + methodName + "()");
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operationLog);
            // 保存数据库
            asyncLogService.saveSysLog(operationLog);
        } catch (Exception exp) {
            log.error("==前置通知异常==");
            log.error("日志异常信息 {}", exp);
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log          日志
     * @param operationLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, SysOperationLog operationLog) {
        // 设置action动作
        operationLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operationLog.setTitle(log.title());
    }


    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
