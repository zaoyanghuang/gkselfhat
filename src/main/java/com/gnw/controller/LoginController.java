package com.gnw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @RequestMapping("/ceshi")
    @ResponseBody
    public String login(){
        return "测试返回数据";
    }
}
