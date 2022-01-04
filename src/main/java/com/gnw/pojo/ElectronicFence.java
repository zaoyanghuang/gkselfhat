package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class ElectronicFence {
    private int efid;
    private String fenceName;
    private Timestamp createTime;
    private String lngLat;
    private double radius;
    private int workTime;
    private int banInOut;
    private int banType;
    private String deptName;
    private int companyId;
    private String companyBelong;

    public ElectronicFence() {
    }

    public ElectronicFence(int efid, String fenceName, Timestamp createTime, String lngLat, double radius, int workTime, int banInOut, int banType, String deptName, int companyId, String companyBelong) {
        this.efid = efid;
        this.fenceName = fenceName;
        this.createTime = createTime;
        this.lngLat = lngLat;
        this.radius = radius;
        this.workTime = workTime;
        this.banInOut = banInOut;
        this.banType = banType;
        this.deptName = deptName;
        this.companyId = companyId;
        this.companyBelong = companyBelong;
    }

    public int getEfid() {
        return efid;
    }

    public void setEfid(int efid) {
        this.efid = efid;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getLngLat() {
        return lngLat;
    }

    public void setLngLat(String lngLat) {
        this.lngLat = lngLat;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public int getBanInOut() {
        return banInOut;
    }

    public void setBanInOut(int banInOut) {
        this.banInOut = banInOut;
    }

    public int getBanType() {
        return banType;
    }

    public void setBanType(int banType) {
        this.banType = banType;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyBelong() {
        return companyBelong;
    }

    public void setCompanyBelong(String companyBelong) {
        this.companyBelong = companyBelong;
    }
}
