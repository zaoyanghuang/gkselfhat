package com.gnw.mapper;


import com.gnw.pojo.UserRoleAuthority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDetailsMapper {
    /*根据用户名查询*/
    public UserRoleAuthority selByUserName(String userName);

}
