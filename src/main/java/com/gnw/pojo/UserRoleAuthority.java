package com.gnw.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public class UserRoleAuthority implements UserDetails {
    private String userName;
    private String passWords;
    private String companyBelong;
    private String userRole;
    private String authority;

    public UserRoleAuthority() {
    }

    public UserRoleAuthority(String userName, String passWords, String companyBelong, String userRole, String authority) {
        this.userName = userName;
        this.passWords = passWords;
        this.companyBelong = companyBelong;
        this.userRole = userRole;
        this.authority = authority;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWords() {
        return passWords;
    }

    public void setPassWords(String passWords) {
        this.passWords = passWords;
    }

    public String getCompanyBelong() {
        return companyBelong;
    }

    public void setCompanyBelong(String companyBelong) {
        this.companyBelong = companyBelong;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "UserRoleAuthority{" +
                "userName='" + userName + '\'' +
                ", passWords='" + passWords + '\'' +
                ", companyBelong='" + companyBelong + '\'' +
                ", userRole='" + userRole + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
        //注意这里不能用AuthorityUtils.createAuthorityList()  否则报错账号被锁定
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
