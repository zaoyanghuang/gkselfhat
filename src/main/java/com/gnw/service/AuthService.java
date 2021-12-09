package com.gnw.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    boolean hasAuth(HttpServletRequest request, Authentication authentication);
}

