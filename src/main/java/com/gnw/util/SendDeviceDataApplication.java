package com.gnw.util;

import com.gnw.pojo.LocationPackageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


//@Component
//@Order(value = 2)
//@Async
//暂时弃用
//public class SendDeviceDataApplication implements ApplicationRunner {
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        sendDataAlg();
//    }
//    //监控实时解析完成的数据发送至算法
//    public void sendDataAlg(){
//        ParseSocketDataUtil parseSocketDataUtil = ParseSocketDataUtil.getInstance();
//        while(true){
//            try {
//                Thread.sleep(0);//以指示应挂起此线程以使其他等待线程能够执行 无需唤醒继续执行 不这样进不来？？
//                if(parseSocketDataUtil.parsedDeviceInfoList.size()>0){
//                    LocationPackageInfo locationPackageInfo = parseSocketDataUtil.parsedDeviceInfoList.remove(0);
//                    //System.out.println("开始发送parsedDeviceInfoList第一条数据"+locationPackageInfo.toJsonString());
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//}
