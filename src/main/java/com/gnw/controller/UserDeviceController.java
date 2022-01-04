package com.gnw.controller;

import com.gnw.impl.UserDeviceServiceImpl;
import com.gnw.pojo.UserDevice;
import com.gnw.pojo.UserRoleAuthority;
import org.apache.catalina.User;
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
@RequestMapping("/userdevice")
public class UserDeviceController {
    @Autowired
    private UserDeviceServiceImpl userDeviceServiceImpl;
    /*新增设备信息 userName,deviceId,gongkaId,phoneNumber,deptName,companyBelong*/
    @RequestMapping("/insertuserdevice")
    @ResponseBody
    public Map<String,Object> insertUserDevice(@RequestParam(value = "userName")String userName,
                                               @RequestParam(value = "deviceId")String deviceId,
                                               @RequestParam(value = "gongkaId")String gongkaId,
                                               @RequestParam(value = "phoneNumber")String phoneNumber,
                                               @RequestParam(value = "deptName")String deptName){
        Map<String,Object> map = new HashMap<>();
        UserDevice userDevice = new UserDevice();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String companyBelong = userRoleAuthority.getCompanyBelong();
        userDevice.setUserName(userName);
        userDevice.setDeviceId(deviceId);
        userDevice.setGongkaId(gongkaId);
        userDevice.setPhoneNumber(phoneNumber);
        userDevice.setDeptName(deptName);
        userDevice.setCompanyBelong(companyBelong);
        try{
            userDeviceServiceImpl.insertUserDevice(userDevice);
            map.put("Success", "insert success");
        }catch (Exception e){
            map.put("Fail", "insert Exception:"+e.getMessage());
            return map;
        }
        return map;
    }
    /*删除设备信息*/
    @RequestMapping("/deleteuserdevice")
    @ResponseBody
    public Map<String,Object> deleteUserDevice(@RequestParam(value = "deviceId")String deviceId){
        Map<String,Object> map = new HashMap<>();
        try{
            userDeviceServiceImpl.deleteUserDevice(deviceId);
            map.put("Success", "delete success");
        }catch (Exception e){
            map.put("Fail", "delete Exception"+e.getMessage());
            return map;
        }

        return map;
    }
    /*新增设备信息*/
    @RequestMapping("/updateuserdevice")
    @ResponseBody
    public Map<String,Object> updateUserDevice(@RequestParam(value = "gongkaId")String gongkaId,
                                               @RequestParam(value = "phoneNumber")String phoneNumber,
                                               @RequestParam(value = "deptName")String deptName,
                                               @RequestParam(value = "deviceId")String deviceId){
        Map<String,Object> map = new HashMap<>();
        try{
            userDeviceServiceImpl.updateUserDevice(deviceId,gongkaId, phoneNumber, deptName);
            map.put("Success", "update success");
        }catch (Exception e){
            map.put("Fail", "update Exception:"+e.getMessage());
            return map;
        }
        return map;
    }
    /*新增设备信息
    * 非管理员账号 仅能查看自己公司设备
    * 参数为空则查询全部  非空则按照参数条件查询*/
    @RequestMapping("/seluserdevice")
    @ResponseBody
    public Map<String,Object> selUserDevice(@RequestParam(value = "gongkaId")String gongkaId){
        Map<String,Object> map = new HashMap<>();
        List<UserDevice> userDeviceList = new ArrayList<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = userRoleAuthority.getUserRole();
        String companyBelong = userRoleAuthority.getCompanyBelong();
        try{
            userDeviceList = userDeviceServiceImpl.selUserDevice(gongkaId,userRole,companyBelong);
            map.put("userDeviceList", userDeviceList);
            map.put("Success", "insert success");
        }catch (Exception e){
            map.put("Fail", "insert Exception");
            return map;
        }
        return map;
    }




}
