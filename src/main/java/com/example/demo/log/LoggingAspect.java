package com.example.demo.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {
    private Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("@annotation(com.example.demo.log.LogExecutionTime)")
    public void stringProcessingMethods() {
    }

    @After("stringProcessingMethods()")
    public void logMethodCall(JoinPoint jp) {
        String methodName = jp.getSignature().getName();

        logger.log(Level.INFO, "название метода: " + methodName);
    }

    @AfterReturning(pointcut = "@annotation(com.example.demo.log.LogExecutionTime)", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.log(Level.INFO, "возвращенное значение: " + result.toString());
    }

    @Around("@annotation(com.example.demo.log.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.log(Level.INFO, joinPoint.getSignature() + " выполнен за " + executionTime + "мс");
        return proceed;
    }
}
