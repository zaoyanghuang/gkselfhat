package com.gnw.service;

import com.gnw.pojo.ElectronicFence;
import com.gnw.pojo.ElectronicFenceAlarm;
import com.gnw.pojo.TumbleAlarmCompany;

import java.sql.Timestamp;
import java.util.List;

public interface ElectronicFenceAlarmService {
    public List<ElectronicFenceAlarm> selEleFenceAlarm(String gongkaId, int companyId, int dealState, int pageNum, int lineNum);
    public void dealEleFenceAlarm(String gongkaId, Timestamp startTime, String remarks);

}
