package com.github.fuckcpp.weatherapp.natives;/*
作者Crying711
日期:2021/2/7
时间:23:51
*/

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * The interface Weather.
 */
public interface Weather extends Library
{


    /**
     * The constant WEATHER.
     */
    Weather WEATHER = Native.load(Config.loadnative(),Weather.class);

    /**
     * Translate api key string.
     *
     * @param str the str
     * @return the string
     */
    String translate_api_key(String str);

    /**
     * Ipaddress api key string.
     *
     * @return the string
     */
    String ipaddress_api_key();

    /**
     * Weather api key string.
     *
     * @return the string
     */
    String weather_api_key();

    /**
     * Gets program pid.
     *
     * @return the program pid
     */
    int  get_program_pid();


}

/**
 * The type Config.
 */
class  Config
{

    public  static final String Osname =System.getProperty("os.name");
    /**
     * The constant Arch.
     */
    public  static final String  Arch =System.getProperty("os.arch");


    /**
     * Loadnative string.
     *
     * @return the string
     */
    public static String loadnative()
    {
        if (Osname.contains("Windows"))
        { if (Arch.equals("amd64"))
        { return  "libweather.dll"; }
        }else  if (Osname.contains("Linux"))
        { if (Arch.equals("amd64"))
        { return  "libweather.so"; }
        }
        return  "";


    }


}