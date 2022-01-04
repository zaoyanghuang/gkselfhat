package com.gnw.mapper;

import com.gnw.pojo.UserDevice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDevcieMapper {
    public void insertUserDevice(UserDevice userDevice);
    public void deleteUserDevice(String deviceId);
    public void updateUserDevice(String deviceId,String gongkaId,String phoneNumber,String deptName);
    public List<UserDevice> selUserDevice(String gongkaId,String userRole,String companyBelong);
}
