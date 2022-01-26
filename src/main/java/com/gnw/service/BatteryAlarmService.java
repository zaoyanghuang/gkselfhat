package com.gnw.service;

import com.gnw.pojo.BatteryAlarm;

import java.util.List;

public interface BatteryAlarmService {
    public List<BatteryAlarm> selBatteryAlarm(String gongkaId,int companyId,int pageNum,int lineNum);
}
