package com.gnw.service;

import com.gnw.pojo.SosAlarmCompany;

import java.sql.Timestamp;
import java.util.List;

public interface SosAlarmService {
    public List<SosAlarmCompany> selSosAlarm(String gongkaId, int companyId, int dealState,int pageNum,int lineNum);
    public void dealSosAlarm(String gongkaId, Timestamp startTime,String remarks);
}
