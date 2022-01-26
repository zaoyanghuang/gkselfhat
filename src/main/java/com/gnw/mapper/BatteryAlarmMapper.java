package com.gnw.mapper;

import com.gnw.pojo.BatteryAlarm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface BatteryAlarmMapper {
    public List<BatteryAlarm> selBatteryAlarm(String gongkaId,int companyId,int pageNum,int lineNum);

}
