package com.gnw.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Component
public class SendDataListener implements ApplicationListener<SendDataEvent>{

    @EventListener(SendDataEvent.class)
    public void onApplicationEvent(SendDataEvent sendDataEvent) {
        //sendDataEvent.printMsg("SendDataListener传入的数据");
        System.out.println("开始监听"+sendDataEvent.getClass());//不注释@Component会执行3次？？？
    }
}
