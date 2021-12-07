package com.gnw.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyAccessDenieHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(response.SC_FORBIDDEN);//sendError适用于报错且存在对应的报错页面配置作为输出显示的情况，而setStatus适用于正常响应的情况
        response.setContentType("application/json;charset=utf-8");
        PrintWriter responseWriter = response.getWriter();
        responseWriter.write("{\"result\":\"403\"}");
        responseWriter.print("{\"code\":\"403\",\"msg\":\"无权限\"}");
        responseWriter.flush();
        responseWriter.close();
    }
}
