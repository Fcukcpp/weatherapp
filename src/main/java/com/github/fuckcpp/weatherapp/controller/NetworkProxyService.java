package com.github.fuckcpp.weatherapp.controller;/*
作者Crying711
日期:2021/2/7
时间:21:36
*/

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fuckcpp.weatherapp.pojo.NetworkProxy;
import com.github.fuckcpp.weatherapp.pojo.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * The type Network proxy service.
 */
@Service
@Slf4j
public class NetworkProxyService
{
    private List<Proxy> proxys;
    @Value("classpath:proxy.json")
    Resource resourceFile;
    /**
     * Initialization.
     *  从文件中加载代理文件 到对象
     * @throws IOException the io exception
     */
    @PostConstruct
    private void initialization() throws IOException
    {
//        Resource resource = new ClassPathResource("proxy.json");
        ObjectMapper objectMapper = new ObjectMapper();
        NetworkProxy networkProxy = objectMapper.readValue(resourceFile.getInputStream(), NetworkProxy.class);
        this.proxys = networkProxy.getNetworkporxy();
        log.info("加载代理文件"+String.valueOf(networkProxy.getNetworkporxy().hashCode()));
    }


    /**
     * 设置代理
     * Network proxy core.
     */
    public   void   network_proxy_core()
    {   Proxy proxy =proxys.get(new Random().nextInt(proxys.size()-1));
        System.setProperty("http.proxyHost", proxy.getIp());
        System.setProperty("http.proxyPort", proxy.getPort());
        log.info("使用了代理"+proxy.getIp(),proxy.getPort());
    }

    /**
     * 关闭代理
     * Network proxy close.
     */
    public   void   network_proxy_close()
    {
        Properties prop = System.getProperties();
        prop.remove("http.proxyHost");
        prop.remove("http.proxyPort");
        log.info("关闭代理");
        Collections.shuffle(proxys);
    }

}
