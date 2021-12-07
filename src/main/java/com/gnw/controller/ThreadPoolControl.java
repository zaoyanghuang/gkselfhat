package com.gnw.controller;

import com.gnw.service.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/threadpool")
public class ThreadPoolControl {
    @Autowired
    private ThreadPoolService threadPoolService;

    @ResponseBody
    @RequestMapping("/test")
    public String doTask(){
        for (int i=0;i<10;i++){
            threadPoolService.task1(i);
            threadPoolService.task2(i);
        }
        return "success";
    }

}
