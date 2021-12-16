package com.gnw.pojo;

public class RoleAuthority {
    private String userRole;
    private String authority;

    public RoleAuthority() {
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

    public RoleAuthority(String userRole, String authority) {

        this.userRole = userRole;
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "RoleAuthority{" +
                "userRole='" + userRole + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
