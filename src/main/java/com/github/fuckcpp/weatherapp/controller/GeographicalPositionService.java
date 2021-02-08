package com.github.fuckcpp.weatherapp.controller;/*
作者Crying711
日期:2021/2/7
时间:20:28
*/

import com.github.fuckcpp.weatherapp.controller.NetworkProxyService;
import com.github.fuckcpp.weatherapp.natives.Weather;
import com.github.fuckcpp.weatherapp.pojo.NetworkProxy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * The type Geographical position service.
 */
@Slf4j
@Service
public class GeographicalPositionService
{


    /**
     * Gets .
     *
     * @param ipadders the ipadders
     * @return the//       从Native 方法中获取api link  然后根据ip 获取地理位置 并进行AOP 操作
     * @throws IOException the io exception
     */
    public   String getcity(String ipadders) throws IOException {
        String url = Weather.WEATHER.ipaddress_api_key()+ipadders;
        Document document = Jsoup.parse(new URL(url),5000);
        Elements select = document.select(".mh-detail");
        String text = select.text();
        String[] information = text.split("  ");
        return information[1];

    }

}
