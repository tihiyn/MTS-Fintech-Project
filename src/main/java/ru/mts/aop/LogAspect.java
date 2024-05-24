package ru.mts.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;
import ru.mts.annotation.Logging;

@Aspect
@Component
@Slf4j
public class LogAspect {
    private static final String ENTER = ">> {}";
    private static final String EXIT = "{} <<";

    @Around("@annotation(logging) && execution(public * ru.mts..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint, Logging logging) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String logMessage = logging.value().isEmpty() ? methodName : logging.value();
        Level logLevel = Level.valueOf(logging.level().toUpperCase());

        if (logging.entering()) {
            log.atLevel(logLevel).log(ENTER, logMessage);
        }

        Object result = joinPoint.proceed();

        if (logging.exiting()) {
            log.atLevel(logLevel).log(EXIT, logMessage);
        }
        return result;
    }
}
