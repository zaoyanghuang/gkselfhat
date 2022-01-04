package com.gnw.impl;

import com.gnw.mapper.EleFenceAlarmMapper;
import com.gnw.pojo.ElectronicFence;
import com.gnw.pojo.ElectronicFenceAlarm;
import com.gnw.service.ElectronicFenceAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
@Service
public class EleFenceAlarmServiceImpl implements ElectronicFenceAlarmService {
    @Autowired
    private EleFenceAlarmMapper eleFenceAlarmMapper;
    @Override
    public List<ElectronicFenceAlarm> selEleFenceAlarm(String gongkaId, int companyId, int dealState, int pageNum, int lineNum) {
        return eleFenceAlarmMapper.selEleFenceAlarm(gongkaId, companyId, dealState, pageNum, lineNum);
    }
    @Override
    public void dealEleFenceAlarm(String gongkaId, Timestamp startTime, String remarks) {
        eleFenceAlarmMapper.dealEleFenceAlarm(gongkaId, startTime, remarks);

    }
}
