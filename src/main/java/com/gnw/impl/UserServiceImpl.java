package com.gnw.impl;

import com.gnw.mapper.UserMapper;
import com.gnw.pojo.RoleAuthority;
import com.gnw.pojo.UserInfo;
import com.gnw.pojo.UserRoleAuthority;
import com.gnw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<UserRoleAuthority> selAllByCompany(String userRole, String companyBelong,int pageNum,int lineNum) {
        return userMapper.selAllByCompany(userRole, companyBelong, pageNum, lineNum);
    }

    @Override
    public UserRoleAuthority selByUserName(String userRole, String companyBelong,String userName) {
        return userMapper.selByUserName(userRole, companyBelong, userName);
    }

    @Override
    public List<String> selAllCompany() {
        return userMapper.selAllCompany();
    }

    @Override
    public int updateUserInfo(String passWords, String realName, String phoneNumber, String companyBelong, String authority,String userName) {
        return userMapper.updateUserInfo(passWords, realName, phoneNumber, companyBelong, authority,userName);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String insertUserInfo(UserInfo userInfo, RoleAuthority roleAuthority) {
        String result = "";
        try{
            if(roleAuthority !=null){
                userMapper.insertUserInfo(userInfo);
            }
            userMapper.insertRoleAuthority(roleAuthority);
            result = "insert success";
        }catch (Exception e){
            result = "insert fail:"+e.getMessage();

        }

        return result;
    }
}
