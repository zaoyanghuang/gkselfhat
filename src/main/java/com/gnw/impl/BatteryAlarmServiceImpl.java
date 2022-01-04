package com.gnw.impl;

import com.gnw.mapper.BatteryAlarmMapper;
import com.gnw.pojo.BatteryAlarm;
import com.gnw.service.BatteryAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatteryAlarmServiceImpl implements BatteryAlarmService {
    @Autowired
    private BatteryAlarmMapper batteryAlarmMapper;
    @Override
    public List<BatteryAlarm> selBatteryAlarm(String gongkaId,int companyId) {
        return batteryAlarmMapper.selBatteryAlarm(gongkaId,companyId);
    }
}
