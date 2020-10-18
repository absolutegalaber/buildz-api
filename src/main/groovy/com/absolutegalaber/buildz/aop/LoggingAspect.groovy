package com.absolutegalaber.buildz.aop

import groovy.util.logging.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Pointcut

@Slf4j
class LoggingAspect {

    @Pointcut("within(com.absolutegalaber.buildz.service.* || com.absolutegalaber.buildz.api.v1.*)")
    void loggingPointcut() {
    }

    @AfterThrowing(pointcut = "loggingPointcut()", throwing = "e")
    void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("{}.{}() throws: {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), e.getClass().getName())
    }

    @Around("loggingPointcut()")
    Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isInfoEnabled()) {
            log.info("{}.{}( {} )", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()))
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isInfoEnabled()) {
                log.info("{}.{}(): {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), result)
            }
            return result
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName())
            throw e
        }
    }

}
