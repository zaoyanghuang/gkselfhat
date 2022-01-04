package com.gnw.impl;

import com.gnw.mapper.TumbleAlarmMapper;
import com.gnw.pojo.TumbleAlarmCompany;
import com.gnw.service.TumbleAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TumbleAlarmServiceImpl implements TumbleAlarmService {
    @Autowired
    private TumbleAlarmMapper tumbleAlarmMapper;
    @Override
    public List<TumbleAlarmCompany> selTumbleAlarm(String gongkaId, int companyId, int dealState, int pageNum, int lineNum) {
        return tumbleAlarmMapper.selTumbleAlarm(gongkaId, companyId, dealState, pageNum, lineNum);
    }
    @Override
    public void dealTumbleAlarm(String gongkaId, Timestamp startTime, String remarks) {
        tumbleAlarmMapper.dealTumbleAlarm(gongkaId, startTime, remarks);
    }
}
