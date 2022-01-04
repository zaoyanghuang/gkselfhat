package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class SosAlarmCompany {
    private String gongkaId;
    private Timestamp startTime;
    private Timestamp endTime;
    private double longitude;
    private double latitude;
    private String remarks;
    private String companyBelong;
    private int companyId;
    private int dealState;

    public SosAlarmCompany() {
    }

    public SosAlarmCompany(String gongkaId, Timestamp startTime, Timestamp endTime, double longitude, double latitude, String remarks, String companyBelong, int companyId, int dealState) {
        this.gongkaId = gongkaId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remarks = remarks;
        this.companyBelong = companyBelong;
        this.companyId = companyId;
        this.dealState = dealState;
    }

    public int getDealState() {
        return dealState;
    }

    public void setDealState(int dealState) {
        this.dealState = dealState;
    }

    public String getGongkaId() {
        return gongkaId;
    }

    public void setGongkaId(String gongkaId) {
        this.gongkaId = gongkaId;
    }
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
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

    public String getCompanyBelong() {
        return companyBelong;
    }

    public void setCompanyBelong(String companyBelong) {
        this.companyBelong = companyBelong;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "SosAlarmCompany{" +
                "gongkaId='" + gongkaId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", remarks='" + remarks + '\'' +
                ", companyBelong='" + companyBelong + '\'' +
                ", companyId=" + companyId +
                ", dealState=" + dealState +
                '}';
    }
}
