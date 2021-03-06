package com.gnw.service;


import com.gnw.pojo.RoleAuthority;
import com.gnw.pojo.UserInfo;
import com.gnw.pojo.UserRoleAuthority;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<UserRoleAuthority> selAllByCompany(String userRole,String companyBelong,int pageNum,int lineNum);
    public UserRoleAuthority selByUserName(String userRole, String companyBelong,String userName);
    public List<String> selAllCompany();
    public int updateUserInfo(String passWords,String realName,String phoneNumber,String companyBelong,String authority,String userName);
    public String insertUserInfo(UserInfo userInfo, RoleAuthority roleAuthority);
}
