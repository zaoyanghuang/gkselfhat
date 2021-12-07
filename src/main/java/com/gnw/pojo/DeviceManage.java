package com.gnw.pojo;

import com.gnw.util.ParseSocketDataUtil;

import java.util.ArrayList;
import java.util.List;

public class DeviceManage {
    //Arrays.asList("a", "b", "c")
    List<String> deviceList = new ArrayList<String>(){{
      add("00010080");
    }};//前端登录后 存入list
    private static DeviceManage deviceManage;
    private DeviceManage(){};
    public static DeviceManage getInstance(){
        if(deviceManage == null){
            synchronized (ParseSocketDataUtil.class){
                if(deviceManage == null){
                    deviceManage = new DeviceManage();
                }
            }
        }
        return deviceManage;
    }



}
