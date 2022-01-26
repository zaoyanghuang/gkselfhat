package com.gnw.controller;

import com.gnw.impl.BatteryAlarmServiceImpl;
import com.gnw.pojo.BatteryAlarm;
import com.gnw.pojo.UserDevice;
import com.gnw.pojo.UserRoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/batteryalarm")
public class BatteryAlarmController {
    @Autowired
    private BatteryAlarmServiceImpl batteryAlarmServiceImpl;
    /*按工号和公司ID查询低电量报警
    * 只能查询权限范围内本公司信息*/
    @RequestMapping("/selbatteryalarm")
    @ResponseBody
    public Map<String,Object> selBatteryAlarm(@RequestParam(value = "gongkaId")String gongkaId,
                                              @RequestParam(value = "pageNum")int pageNum,
                                              @RequestParam(value = "lineNum")int lineNum){
        Map<String,Object> map = new HashMap<>();
        List<BatteryAlarm> batteryAlarmList = new ArrayList<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int companyId = userRoleAuthority.getCompanyId();
        try{
            batteryAlarmList = batteryAlarmServiceImpl.selBatteryAlarm(gongkaId,companyId,pageNum,lineNum);
            map.put("batteryAlarmList", batteryAlarmList);
            map.put("Success", "sel success");
        }catch (Exception e){
            map.put("Fail", "sel Exception");
            return map;
        }
        return map;
    }

}
