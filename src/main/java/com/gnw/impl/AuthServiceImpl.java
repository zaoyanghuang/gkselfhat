package com.gnw.impl;

import com.gnw.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public boolean hasAuth(HttpServletRequest request, Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //这里我修改为只验证最后一个/以及后面的url数据
        return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/"))));//返回判断值权限中是否包含访问的url
    }
}
