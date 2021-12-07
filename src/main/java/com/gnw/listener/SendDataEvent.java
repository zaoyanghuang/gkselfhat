package com.gnw.listener;


import org.springframework.context.ApplicationEvent;


public class SendDataEvent extends ApplicationEvent{
    public SendDataEvent(Object source) {
        super(source);
    }
//    public void printMsg(String msg){
//        System.out.println("监听到事件"+msg);
//    }
}
