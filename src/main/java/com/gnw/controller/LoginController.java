package com.gnw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @RequestMapping("/ceshi")
    @ResponseBody
    public String login(){
        //SecurityContextHolder.getContext().getAuthentication()  从sucutiy中获取用户信息
        return "测试返回数据";
    }
}
