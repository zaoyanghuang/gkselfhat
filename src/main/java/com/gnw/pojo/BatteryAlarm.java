package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class BatteryAlarm {
    private int baid;
    private String gongkaId;
    private Timestamp alarmTime;
    private double longitude;
    private double latitude;
    private int batteryNumber;

    public BatteryAlarm() {
    }

    public BatteryAlarm(int baid, String gongkaId, Timestamp alarmTime, double longitude, double latitude, int batteryNumber) {
        this.baid = baid;
        this.gongkaId = gongkaId;
        this.alarmTime = alarmTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.batteryNumber = batteryNumber;
    }

    public int getBaid() {
        return baid;
    }

    public void setBaid(int baid) {
        this.baid = baid;
    }

    public String getGongkaId() {
        return gongkaId;
    }

    public void setGongkaId(String gongkaId) {
        this.gongkaId = gongkaId;
    }
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Timestamp alarmTime) {
        this.alarmTime = alarmTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getBatteryNumber() {
        return batteryNumber;
    }

    public void setBatteryNumber(int batteryNumber) {
        this.batteryNumber = batteryNumber;
    }

    @Override
    public String toString() {
        return "BatteryAlarm{" +
                "baid=" + baid +
                ", gongkaId='" + gongkaId + '\'' +
                ", alarmTime=" + alarmTime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", batteryNumber=" + batteryNumber +
                '}';
    }
}
