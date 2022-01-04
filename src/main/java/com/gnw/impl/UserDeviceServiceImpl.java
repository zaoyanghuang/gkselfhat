package com.gnw.impl;

import com.gnw.mapper.UserDevcieMapper;
import com.gnw.pojo.UserDevice;
import com.gnw.service.UserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDeviceServiceImpl implements UserDeviceService {
    @Autowired
    private UserDevcieMapper userDevcieMapper;
    @Override
    public void insertUserDevice(UserDevice userDevice) {
        userDevcieMapper.insertUserDevice(userDevice);
    }

    @Override
    public void deleteUserDevice(String deviceId) {
        userDevcieMapper.deleteUserDevice(deviceId);
    }

    @Override
    public void updateUserDevice(String deviceId,String gongkaId, String phoneNumber, String deptName) {
        userDevcieMapper.updateUserDevice(deviceId,gongkaId, phoneNumber, deptName);
    }

    @Override
    public List<UserDevice> selUserDevice(String gongkaId,String userRole,String companyBelong) {
        return userDevcieMapper.selUserDevice(gongkaId,userRole,companyBelong);
    }
}
