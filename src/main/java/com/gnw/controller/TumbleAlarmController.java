package com.gnw.controller;

import com.gnw.impl.SosAlarmServiceImpl;
import com.gnw.impl.TumbleAlarmServiceImpl;
import com.gnw.pojo.SosAlarmCompany;
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
@RequestMapping("/tumblealarm")
public class TumbleAlarmController {
    @Autowired
    private TumbleAlarmServiceImpl tumbleAlarmServiceImpl;
    /*工卡号  处理状态 权限  公司ID查询相关sos报警信息*/
    @RequestMapping("/seltumblealarm")
    @ResponseBody
    public Map<String,Object> selTumbleAlarm(@RequestParam(value = "gongkaId")String gongkaId,
                                          @RequestParam(value = "pageNum")int pageNum,
                                          @RequestParam(value = "lineNum")int lineNum,
                                          @RequestParam(value = "dealState")int dealState){
        Map<String,Object> map = new HashMap<>();
        List<TumbleAlarmCompany> tumbleAlarmCompanyList=new ArrayList<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int companyId = userRoleAuthority.getCompanyId();
        try{
            tumbleAlarmCompanyList=tumbleAlarmServiceImpl.selTumbleAlarm(gongkaId, companyId, dealState, pageNum, lineNum);
            map.put("sosAlarmCompanyList", tumbleAlarmCompanyList);
            map.put("Success", "sel success");
        }catch (Exception e){
            map.put("Fail", "sel Exception");
            return map;
        }
        return map;
    }
    /*处理报警信息 添加备注 一个汉字占3个长度，数据库限制128长度*/
    @RequestMapping("/deltumblealarm")
    @ResponseBody
    public Map<String,Object> dealTumbleAlarm(@RequestParam(value = "gongkaId")String gongkaId,
                                           @RequestParam(value = "alarmTime")String alarmTime,
                                           @RequestParam(value = "remarks")String remarks){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int companyId = userRoleAuthority.getCompanyId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date alarmTimeUtil = sdf.parse(alarmTime);
            Timestamp startTime = new Timestamp(alarmTimeUtil.getTime());
            tumbleAlarmServiceImpl.dealTumbleAlarm(gongkaId,startTime,remarks);
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
