package com.gnw.mapper;

import com.gnw.pojo.RoleAuthority;
import com.gnw.pojo.UserInfo;
import com.gnw.pojo.UserRoleAuthority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    public List<UserRoleAuthority> selAllByCompany(String userRole, String companyBelong,int pageNum,int lineNum);
    public UserRoleAuthority selByUserName(String userRole, String companyBelong,String userName);
    public List<String> selAllCompany();
    public int updateUserInfo(String passWords,String realName,String phoneNumber,String companyBelong,String authority,String userName);
    public int insertUserInfo(UserInfo userInfo);
    public int insertRoleAuthority(RoleAuthority roleAuthority);
}
