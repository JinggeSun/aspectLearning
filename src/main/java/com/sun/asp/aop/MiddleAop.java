package com.sun.asp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author zcm
 */
@Component
@Aspect
public class MiddleAop {

    @Pointcut("execution (* com.sun.asp..*.*(..))")
    public void aspect(){}


    @Before("aspect()")
    private void before(){
        System.out.print("Before");
    }

}
