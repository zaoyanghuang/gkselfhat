package com.gnw.handler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        Map<String,Object> map = new HashMap<>();
        map.put("result", "fail");
        map.put("msg", "账号未登录！");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write(JSON.toJSONString(map));
        responseWriter.flush();
        responseWriter.close();
    }
}
