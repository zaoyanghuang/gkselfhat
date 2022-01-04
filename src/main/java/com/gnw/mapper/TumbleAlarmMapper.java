package com.gnw.mapper;

import com.gnw.pojo.TumbleAlarmCompany;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.List;
@Mapper
public interface TumbleAlarmMapper {
    public List<TumbleAlarmCompany> selTumbleAlarm(String gongkaId, int companyId, int dealState, int pageNum, int lineNum);
    public void dealTumbleAlarm(String gongkaId, Timestamp startTime, String remarks);
}
