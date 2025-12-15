package com.truper.test.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimingAspect {

    @Around("@annotation(Timed)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long end = System.nanoTime();
        long duration = end - start;
        log.info("Método {} ejecutado en {} segundos ({} s)",
                joinPoint.getSignature().toShortString(), duration, duration / 60);
        return result;
    }
}
