package com.gnw.mapper;

import com.gnw.pojo.ElectronicFence;
import com.gnw.pojo.ElectronicFenceAlarm;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface EleFenceAlarmMapper {
    public List<ElectronicFenceAlarm> selEleFenceAlarm(String gongkaId, int companyId, int dealState, int pageNum, int lineNum);
    public void dealEleFenceAlarm(String gongkaId, Timestamp startTime, String remarks);
}
