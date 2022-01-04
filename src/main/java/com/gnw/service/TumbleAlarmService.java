package com.gnw.service;

import com.gnw.pojo.TumbleAlarmCompany;

import java.sql.Timestamp;
import java.util.List;

public interface TumbleAlarmService {
    public List<TumbleAlarmCompany> selTumbleAlarm(String gongkaId,int companyId,int dealState,int pageNum,int lineNum);
    public void dealTumbleAlarm(String gongkaId, Timestamp startTime,String remarks);
}
