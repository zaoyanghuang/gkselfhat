package com.gnw.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class Company {
    private int cid;
    private String companyName;
    private String companyLocation;
    private Timestamp createTime;
    private int businessState;

    public int getBusinessState() {
        return businessState;
    }

    public void setBusinessState(int businessState) {
        this.businessState = businessState;
    }

    public Company() {
    }

    public Company(int cid, String companyName, String companyLocation, Timestamp createTime, int businessState) {
        this.cid = cid;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
        this.createTime = createTime;
        this.businessState = businessState;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss",timezone = "GMT+8")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Company{" +
                "cid=" + cid +
                ", companyName='" + companyName + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                ", createTime=" + createTime +
                ", businessState=" + businessState +
                '}';
    }
}
