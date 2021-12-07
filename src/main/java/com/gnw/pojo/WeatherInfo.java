package com.gnw.pojo;

import com.alibaba.fastjson.JSONObject;

public class WeatherInfo {
    /**
     * 时间  天气更新时间  非查询具体时间
     */
    private String date;

    /**
     * 天气描述  晴转多云
     */
    private String weatherText;
    /**
     * 温度
     */
    private String temperatureRange;

    /**
     * 体感温度
     */
    private String feelsLikeTemperature;
    /**
     * 露感温度
     */
    private String dewTemperature;

    /**
     * 相对湿度
     */
    private String humidity;
    /**
     * 空气质量指数等级
     */
    private String category;
    /**
     * PM2.5
     */
    private String pm2p5;

    public WeatherInfo() {
    }

    public String getDate() {
        return date;
    }

    public WeatherInfo(String date, String weatherText, String temperatureRange, String feelsLikeTemperature, String dewTemperature, String humidity, String category, String pm2p5) {
        this.date = date;
        this.weatherText = weatherText;
        this.temperatureRange = temperatureRange;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.dewTemperature = dewTemperature;
        this.humidity = humidity;
        this.category = category;
        this.pm2p5 = pm2p5;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeatherText() {
        return weatherText;
    }

    public void setWeatherText(String weatherText) {
        this.weatherText = weatherText;
    }

    public String getTemperatureRange() {
        return temperatureRange;
    }

    public void setTemperatureRange(String temperatureRange) {
        this.temperatureRange = temperatureRange;
    }

    public String getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public void setFeelsLikeTemperature(String feelsLikeTemperature) {
        this.feelsLikeTemperature = feelsLikeTemperature;
    }

    public String getDewTemperature() {
        return dewTemperature;
    }

    public void setDewTemperature(String dewTemperature) {
        this.dewTemperature = dewTemperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPm2p5() {
        return pm2p5;
    }

    public void setPm2p5(String pm2p5) {
        this.pm2p5 = pm2p5;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "date='" + date + '\'' +
                ", weatherText='" + weatherText + '\'' +
                ", temperatureRange='" + temperatureRange + '\'' +
                ", feelsLikeTemperature='" + feelsLikeTemperature + '\'' +
                ", dewTemperature='" + dewTemperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", category='" + category + '\'' +
                ", pm2p5='" + pm2p5 + '\'' +
                '}';
    }
    public String toJsonString(){
        return JSONObject.toJSONString(this);
    }
}
