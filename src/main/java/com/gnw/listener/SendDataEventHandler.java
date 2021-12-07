package com.gnw.listener;

import com.gnw.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;



public class SendDataEventHandler {
    @EventListener(SendDataEvent.class)
    public void sendDataEvent(SendDataEvent event){
        //event.printMsg("SendDataEventHandler的数据");
    }

}
