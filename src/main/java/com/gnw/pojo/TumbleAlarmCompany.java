package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class TumbleAlarmCompany {
    private String gongkaId;
    private Timestamp alarmTime;
    private double longitude;
    private double latitude;
    private String remarks;
    private int dealState;
    private String companyBelong;
    private String companyId;

    public TumbleAlarmCompany() {
    }

    public TumbleAlarmCompany(String gongkaId, Timestamp alarmTime, double longitude, double latitude, String remarks, int dealState, String companyBelong, String companyId) {
        this.gongkaId = gongkaId;
        this.alarmTime = alarmTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remarks = remarks;
        this.dealState = dealState;
        this.companyBelong = companyBelong;
        this.companyId = companyId;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getDealState() {
        return dealState;
    }

    public void setDealState(int dealState) {
        this.dealState = dealState;
    }

    public String getCompanyBelong() {
        return companyBelong;
    }

    public void setCompanyBelong(String companyBelong) {
        this.companyBelong = companyBelong;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "TumbleAlarmCompany{" +
                "gongkaId='" + gongkaId + '\'' +
                ", alarmTime=" + alarmTime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", remarks='" + remarks + '\'' +
                ", dealState=" + dealState +
                ", companyBelong='" + companyBelong + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
