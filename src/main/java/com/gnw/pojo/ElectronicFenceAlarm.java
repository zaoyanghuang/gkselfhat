package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class ElectronicFenceAlarm {
    private int efaid;
    private int gongkaId;
    private Timestamp startTime;
    private double longitude;
    private double latitude;
    private String remarks;
    private String deptName;
    private int deptId;
    private int companyId;
    private String companyName;

    public ElectronicFenceAlarm() {
    }

    public ElectronicFenceAlarm(int efaid, int gongkaId, Timestamp startTime, double longitude, double latitude, String remarks, String deptName, int deptId, int companyId, String companyName) {
        this.efaid = efaid;
        this.gongkaId = gongkaId;
        this.startTime = startTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remarks = remarks;
        this.deptName = deptName;
        this.deptId = deptId;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public int getEfaid() {
        return efaid;
    }

    public void setEfaid(int efaid) {
        this.efaid = efaid;
    }

    public int getGongkaId() {
        return gongkaId;
    }

    public void setGongkaId(int gongkaId) {
        this.gongkaId = gongkaId;
    }
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "ElectronicFenceAlarm{" +
                "efaid=" + efaid +
                ", gongkaId=" + gongkaId +
                ", startTime=" + startTime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", remarks='" + remarks + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptId=" + deptId +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
