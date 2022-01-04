package com.gnw.service;

import com.gnw.pojo.UserDevice;

import java.util.List;

public interface UserDeviceService {
    public void insertUserDevice(UserDevice userDevice);
    public void deleteUserDevice(String deviceId);
    public void updateUserDevice(String deviceId,String gongkaId,String phoneNumber,String deptName);
    public List<UserDevice> selUserDevice(String gongkaId,String userRole,String companyBelong);
}
