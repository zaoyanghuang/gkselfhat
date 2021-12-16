package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class UserInfo {
    private String userName;
    private String passwords;
    private String realName;
    private String phoneNumber;
    private Timestamp creatTime;
    private String companyBelong;
    private String userRole;

    public UserInfo() {
    }

    public UserInfo(String userName, String passwords, String realName, String phoneNumber, Timestamp creatTime, String companyBelong, String userRole) {
        this.userName = userName;
        this.passwords = passwords;
        this.realName = realName;
        this.phoneNumber = phoneNumber;
        this.creatTime = creatTime;
        this.companyBelong = companyBelong;
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /*timestamp格式的get()方法上需要加上  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  timezone = "GMT+8"消除时差
     * 否则获取时间出错
     * */
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Timestamp creatTime) {
        this.creatTime = creatTime;
    }

    public String getCompanyBelong() {
        return companyBelong;
    }

    public void setCompanyBelong(String companyBelong) {
        this.companyBelong = companyBelong;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                ", passwords='" + passwords + '\'' +
                ", realName='" + realName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", creatTime=" + creatTime +
                ", companyBelong='" + companyBelong + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}
