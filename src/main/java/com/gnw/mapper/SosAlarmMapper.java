package com.gnw.mapper;

import com.gnw.pojo.SosAlarmCompany;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface SosAlarmMapper {
    public List<SosAlarmCompany> selSosAlarm(String gongkaId, int companyId, int dealState, int pageNum, int lineNum);
    public void dealSosAlarm(String gongkaId, Timestamp startTime, String remarks);
}
