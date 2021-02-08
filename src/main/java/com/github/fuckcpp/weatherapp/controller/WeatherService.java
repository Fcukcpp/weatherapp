package com.github.fuckcpp.weatherapp.controller;


import com.github.fuckcpp.weatherapp.natives.Weather;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The type Weather service.
 */
@Service
public class WeatherService {
    private OkHttpClient client;
    private Response response;
    private String cityName;
    private String unit;
    private String APIkey = Weather.WEATHER.weather_api_key();

    /**
     * Get weather json object.
     *
     * @return the json object
     */
//Getting Data from OpenWeather API
//    请求天气接口

    public JSONObject getWeather(){
        client = new OkHttpClient();  //using OKHTTP dependency . You have to add this mannually form OKHTTP website
        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?q="+getCityName()+"&units="+getUnit()+"&appid="+APIkey)
                .build();

        try {
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //Getting required data from Weather JSON API
    //JSON Objects and JSON Arrays


    /**
     * Return weather array json array.
     *
     * @return the json array
     * @throws JSONException the json exception
     */
    public JSONArray returnWeatherArray() throws JSONException {
        JSONArray weatherJsonArray = getWeather().getJSONArray("weather");
        return weatherJsonArray;
    }

    /**
     * Return main object json object.
     *
     * @return the json object
     * @throws JSONException the json exception
     */
    public JSONObject returnMainObject() throws JSONException {
        JSONObject mainObject = getWeather().getJSONObject("main");
        return mainObject;
    }


    /**
     * Return wind object json object.
     *
     * @return the json object
     * @throws JSONException the json exception
     */
    public JSONObject returnWindObject() throws JSONException {
        JSONObject wind = getWeather().getJSONObject("wind");
        return wind;
        }

    /**
     * Return sys object json object.
     *
     * @return the json object
     * @throws JSONException the json exception
     */
    public JSONObject returnSysObject() throws JSONException{
        JSONObject sys = getWeather().getJSONObject("sys");
        return sys;
        } // to  pull the values of Sys from JSON


     // Getters and Setters for CityName and Unit

    /**
     * Gets city name.
     *
     * @return the city name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Sets city name.
     *
     * @param cityName the city name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }
}
