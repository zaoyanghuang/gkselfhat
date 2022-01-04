package com.gnw.impl;

import com.gnw.mapper.SosAlarmMapper;
import com.gnw.pojo.SosAlarmCompany;
import com.gnw.service.SosAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SosAlarmServiceImpl implements SosAlarmService {
    @Autowired
    private SosAlarmMapper sosAlarmMapper;
    @Override
    public List<SosAlarmCompany> selSosAlarm(String gongkaId, int companyId, int dealState,int pageNum,int lineNum) {
        return sosAlarmMapper.selSosAlarm(gongkaId, companyId, dealState, pageNum, lineNum);
    }
    @Override
    public void dealSosAlarm(String gongkaId, Timestamp startTime, String remarks) {
        sosAlarmMapper.dealSosAlarm(gongkaId, startTime, remarks);
    }
}
