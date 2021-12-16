package com.gnw.impl;

import com.gnw.mapper.UserDetailsMapper;
import com.gnw.pojo.UserRoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserDetailsMapper userDetailsMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //1. 查询数据库判断用户名是否存在，如果不存在抛出UsernameNotFoundException
        UserRoleAuthority userRoleAuthority = userDetailsMapper.selByUserName(s);
        //1. 查询数据库判断用户名是否存在，如果不存在抛出UsernameNotFoundException
        if(userRoleAuthority != null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //把查询出来的密码进行解析,或直接把password放到构造方法中。
        //理解:password就是数据库中查询出来的密码，查询出来的内容不是123
        String passWords = encoder.encode(userRoleAuthority.getPassWords());
        System.out.println("查询出的密码为："+passWords);
        userRoleAuthority.setPassWords(passWords);
        return userRoleAuthority;
    }
}
