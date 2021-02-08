package com.github.fuckcpp.weatherapp.aspect;/*
作者Crying711
日期:2021/2/7
时间:23:04
*/

import com.github.fuckcpp.weatherapp.controller.NetworkProxyService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class NetworkProxyAop
{

    @Autowired
    NetworkProxyService networkProxyService;


    @Pointcut("execution(* com.github.fuckcpp.weatherapp.controller.GeographicalPositionService.getcity(..))")
    public void pointcut() { }

    /**
     * 前缀开启代理
     */
    @Before("pointcut()")
    public  void  start_proxy()
    {
        networkProxyService.network_proxy_core();
    }
    /**
     * 关闭代理
     */
    @After("pointcut()")
    public void close_proxy() {
        networkProxyService.network_proxy_close();
    }

    @AfterReturning(value = "pointcut()", returning = "returnObject")
    public void afterReturning(JoinPoint joinPoint, Object returnObject) {
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing afterThrowing  rollback");
    }


}
