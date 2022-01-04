package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class UserDevice {
    private int udid;
    private String userName;
    private String deviceId;
    private String gongkaId;
    private String phoneNumber;
    private String deptName;
    private String companyBelong;
    private int onLine;
    private Timestamp lastConnectTime;

    public UserDevice(int udid, String userName, String deviceId, String gongkaId, String phoneNumber, String deptName, String companyBelong, int onLine, Timestamp lastConnectTime) {
        this.udid = udid;
        this.userName = userName;
        this.deviceId = deviceId;
        this.gongkaId = gongkaId;
        this.phoneNumber = phoneNumber;
        this.deptName = deptName;
        this.companyBelong = companyBelong;
        this.onLine = onLine;
        this.lastConnectTime = lastConnectTime;
    }

    public UserDevice() {
    }

    public int getUdid() {
        return udid;
    }

    public void setUdid(int udid) {
        this.udid = udid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGongkaId() {
        return gongkaId;
    }

    public void setGongkaId(String gongkaId) {
        this.gongkaId = gongkaId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCompanyBelong() {
        return companyBelong;
    }

    public void setCompanyBelong(String companyBelong) {
        this.companyBelong = companyBelong;
    }

    public int getOnLine() {
        return onLine;
    }

    public void setOnLine(int onLine) {
        this.onLine = onLine;
    }
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getLastConnectTime() {
        return lastConnectTime;
    }

    public void setLastConnectTime(Timestamp lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    @Override
    public String toString() {
        return "UserDevice{" +
                "udid=" + udid +
                ", userName='" + userName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", gongkaId='" + gongkaId + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", deptName='" + deptName + '\'' +
                ", companyBelong='" + companyBelong + '\'' +
                ", onLine=" + onLine +
                ", lastConnectTime=" + lastConnectTime +
                '}';
    }
}
