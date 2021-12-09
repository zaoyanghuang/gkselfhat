package com.gnw.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gnw.pojo.WeatherInfo;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Controller
@RequestMapping("/weatherselbycity")
public class WeatherSelByCity {
    @RequestMapping("/getWeatherInfo")
    @ResponseBody
    public static Map<String,Object> getWeatherInfo(@RequestParam(value = "city")String city){
        Map<String,Object> map = new HashMap<>();
        String cityId = getCityId(city);
        WeatherInfo weatherInfo = getWeather(cityId);
        map.put("weatherInfo",weatherInfo);
        return map;
    }
    /**
     * 通过城市名称获取该城市的cityId 用于查询天气信息
     *
     * @param cityName
     * @return
     */
    public static String getCityId(String cityName) {
        StringBuilder sb=new StringBuilder();
        String cityId="";
        try {
            cityName = URLEncoder.encode(cityName, "UTF-8");
            String cityNaneUrl="https://geoapi.qweather.com/v2/city/lookup?location="+cityName+"&number=1&key=024dee0f2ff747ddbf05ac0dafba70be";
            URL url = new URL(cityNaneUrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);

            // 设置读取流的编码格式，自定义编码
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while((line=reader.readLine())!=null) {
                sb.append(line).append(" ");
            }
            reader.close();
            JSONObject cityIdData = JSONObject.parseObject(sb.toString());
            JSONArray locationData = cityIdData.getJSONArray("location");
            JSONObject locationDataObject = locationData.getJSONObject(0);
            cityId=locationDataObject.getString("id");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return cityId;
    }
    /**
     * 通过城市名称获取该城市的天气信息 温度 天气描述 湿度等
     *
     * @param cityId
     * @return
     */
    public  static String getWeatherDataOne(String cityId) {
        StringBuilder sb=new StringBuilder();
        try {
            cityId = URLEncoder.encode(cityId, "UTF-8");
            //String weatherRrl = "http://wthrcdn.etouch.cn/weather_mini?city="+cityName;
            String weatherRrl = "https://devapi.qweather.com/v7/weather/now?location="+cityId+"&key=024dee0f2ff747ddbf05ac0dafba70be";
            URL url = new URL(weatherRrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);

            // 设置读取流的编码格式，自定义编码
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while((line=reader.readLine())!=null) {
                sb.append(line).append(" ");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }
    /**
     * 通过城市名称获取该城市的 空气质量 PM2.5
     *
     * @param cityId
     * @return
     */
    public  static String getWeatherDataTwo(String cityId) {
        StringBuilder sb=new StringBuilder();
        try {
            cityId = URLEncoder.encode(cityId, "UTF-8");
            String weatherRrl = "https://devapi.qweather.com/v7/air/now?location="+cityId+"&key=024dee0f2ff747ddbf05ac0dafba70be";
            URL url = new URL(weatherRrl);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            GZIPInputStream gzin = new GZIPInputStream(is);

            // 设置读取流的编码格式，自定义编码
            InputStreamReader isr = new InputStreamReader(gzin, "utf-8");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while((line=reader.readLine())!=null) {
                sb.append(line).append(" ");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }
    /**
     * 将JSON格式数据进行解析 ，返回一个weather对象
     * @param cityId
     * @return
     */
    public static WeatherInfo getWeather(String cityId){
        String weatherInfoOne=getWeatherDataOne(cityId);
        String weatherInfoTwo=getWeatherDataTwo(cityId);
        JSONObject dataOfJsonOne = JSONObject.parseObject(weatherInfoOne);
        JSONObject dataOfJsonTwo = JSONObject.parseObject(weatherInfoTwo);
        //System.out.println("dataOfJsonOne==========="+dataOfJsonOne);
        //System.out.println("dataOfJsonTwo==========="+dataOfJsonTwo);
        if(dataOfJsonOne.getIntValue("code")!=200 && dataOfJsonTwo.getIntValue("code")!=200) {
            return null;
        }
        //创建WeatherInfo对象，提取所需的天气信息 date, weatherText, temperatureRange, feelsLikeTemperature,dewTemperature, humidity, category, pm2p5
        WeatherInfo weatherInfo = new WeatherInfo();
        //从json数据中提取数据
        JSONObject dataOneObject = dataOfJsonOne.getJSONObject("now");
        String date = dataOneObject.getString("obsTime");//天气更新的具体时间
        String weatherText=dataOneObject.getString("text");
        String temperatureRange=dataOneObject.getString("temp");//温度
        String feelsLikeTemperature=dataOneObject.getString("feelsLike");//体感温度
        String dewTemperature=dataOneObject.getString("dew");//露感温度
        String humidity=dataOneObject.getString("humidity");//湿度

        JSONObject dataTwoObject = dataOfJsonTwo.getJSONObject("now");
        String category=dataTwoObject.getString("category");
        String pm2p5=dataTwoObject.getString("pm2p5");
        weatherInfo.setDate(date);
        weatherInfo.setTemperatureRange(temperatureRange);
        weatherInfo.setFeelsLikeTemperature(feelsLikeTemperature);
        weatherInfo.setHumidity(humidity);
        weatherInfo.setPm2p5(pm2p5);
        weatherInfo.setWeatherText(weatherText);
        weatherInfo.setCategory(category);
        weatherInfo.setDewTemperature(dewTemperature);
        System.out.println("weatherInfo================="+weatherInfo);
        return weatherInfo;
    }
}
