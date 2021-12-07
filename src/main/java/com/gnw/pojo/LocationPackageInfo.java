package com.gnw.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

public class LocationPackageInfo {
    private int packageMarker;
    private int handMarker;
    private short reportTime;
    private short cachePackageNum;
    private String device_num;
    private String device_batch_num;
    private String client;
    private String company;
    private String versionNum;
    private JSONObject fieldLibInfo;

    public LocationPackageInfo() {
    }

    public LocationPackageInfo(int packageMarker, int handMarker, short reportTime, short cachePackageNum, String device_num, String device_batch_num, String client, String company, String versionNum, JSONObject fieldLibInfo) {
        this.packageMarker = packageMarker;
        this.handMarker = handMarker;
        this.reportTime = reportTime;
        this.cachePackageNum = cachePackageNum;
        this.device_num = device_num;
        this.device_batch_num = device_batch_num;
        this.client = client;
        this.company = company;
        this.versionNum = versionNum;
        this.fieldLibInfo = fieldLibInfo;
    }

    public int getPackageMarker() {
        return packageMarker;
    }

    public void setPackageMarker(int packageMarker) {
        this.packageMarker = packageMarker;
    }

    public int getHandMarker() {
        return handMarker;
    }

    public void setHandMarker(int handMarker) {
        this.handMarker = handMarker;
    }

    public short getReportTime() {
        return reportTime;
    }

    public void setReportTime(short reportTime) {
        this.reportTime = reportTime;
    }

    public short getCachePackageNum() {
        return cachePackageNum;
    }

    public void setCachePackageNum(short cachePackageNum) {
        this.cachePackageNum = cachePackageNum;
    }

    public String getDevice_num() {
        return device_num;
    }

    public void setDevice_num(String device_num) {
        this.device_num = device_num;
    }

    public String getDevice_batch_num() {
        return device_batch_num;
    }

    public void setDevice_batch_num(String device_batch_num) {
        this.device_batch_num = device_batch_num;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public JSONObject getFieldLibInfo() {
        return fieldLibInfo;
    }

    public void setFieldLibInfo(JSONObject fieldLibInfo) {
        this.fieldLibInfo = fieldLibInfo;
    }

    @Override
    public String toString() {
        return "LocationPackageInfo{" +
                "packageMarker=" + packageMarker +
                ", handMarker=" + handMarker +
                ", reportTime=" + reportTime +
                ", cachePackageNum=" + cachePackageNum +
                ", device_num='" + device_num + '\'' +
                ", device_batch_num='" + device_batch_num + '\'' +
                ", client='" + client + '\'' +
                ", company='" + company + '\'' +
                ", versionNum='" + versionNum + '\'' +
                ", fieldLibInfo=" + fieldLibInfo +
                '}';
    }

    public String toJsonString() {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(this);
        return jsonStr;
    }
}
