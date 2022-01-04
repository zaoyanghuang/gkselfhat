package com.gnw.controller;

import com.gnw.impl.EleFenceAlarmServiceImpl;
import com.gnw.pojo.ElectronicFence;
import com.gnw.pojo.ElectronicFenceAlarm;
import com.gnw.pojo.TumbleAlarmCompany;
import com.gnw.pojo.UserRoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/electronicfence")
public class ElectronicFenceAlarmController {
    @Autowired
    private EleFenceAlarmServiceImpl eleFenceAlarmServiceImpl;
    /*工卡号  处理状态 权限  公司ID查询相关电子围栏报警信息*/
    @RequestMapping("/seltumblealarm")
    @ResponseBody
    public Map<String,Object> selEleFenceAlarm(@RequestParam(value = "gongkaId")String gongkaId,
                                             @RequestParam(value = "pageNum")int pageNum,
                                             @RequestParam(value = "lineNum")int lineNum,
                                             @RequestParam(value = "dealState")int dealState){
        Map<String,Object> map = new HashMap<>();
        List<ElectronicFenceAlarm> tumbleAlarmCompanyList=new ArrayList<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int companyId = userRoleAuthority.getCompanyId();
        try{
            tumbleAlarmCompanyList=eleFenceAlarmServiceImpl.selEleFenceAlarm(gongkaId, companyId, dealState, pageNum,lineNum);
            map.put("sosAlarmCompanyList", tumbleAlarmCompanyList);
            map.put("Success", "sel success");
        }catch (Exception e){
            map.put("Fail", "sel Exception");
            return map;
        }
        return map;
    }
    /*处理报警信息 添加备注（工卡号和时间） 一个汉字占3个长度，数据库限制128长度*/
    @RequestMapping("/deltumblealarm")
    @ResponseBody
    public Map<String,Object> dealEleFenceAlarm(@RequestParam(value = "gongkaId")String gongkaId,
                                              @RequestParam(value = "alarmTime")String alarmTime,
                                              @RequestParam(value = "remarks")String remarks){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int companyId = userRoleAuthority.getCompanyId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date alarmTimeUtil = sdf.parse(alarmTime);
            Timestamp startTime = new Timestamp(alarmTimeUtil.getTime());
            eleFenceAlarmServiceImpl.dealEleFenceAlarm(gongkaId, startTime, remarks);
            map.put("Success", "update success");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e){
            map.put("Fail", "update Exception");
            return map;
        }
        return map;
    }

}
