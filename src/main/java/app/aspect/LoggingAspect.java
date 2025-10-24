package app.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @After("bean(userController)")
    public void logIndexControllerMethods(JoinPoint joinPoint) {

        System.out.println("Hey, another method in UserController was executed!");
    }

    @Around(value = "@annotation(app.aspect.VeryImportant)")
    public Object logVeryImportantMethodExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {


        System.out.println("Before method execution!");
        Object methodResult = proceedingJoinPoint.proceed();
        System.out.println("After method execution!");

        return methodResult;
    }
}
