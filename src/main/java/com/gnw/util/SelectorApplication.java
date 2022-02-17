package com.gnw.util;



import com.gnw.service.ThreadPoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * 继承Application接口后项目启动时会按照执行顺序执行run方法
 * 通过设置Order的value来指定执行的顺序
 */

//@ServerEndpoint(value = "/websocket/{companyId}")
@Component
@Order(value = 1) //如果value 1中用while（）无限循环 则到不了2  在一个线程里
@Slf4j
//@Async
public class SelectorApplication implements ApplicationRunner{
    @Autowired
    private NettyServer nettyServer;
    @Override
    public void run(ApplicationArguments args) {
        //根据操作系统判断，如果是linux系统则加载c++方法库
        String systemType = System.getProperty("os.name");
        String ext = (systemType.toLowerCase().indexOf("win") != -1) ? ".dll" : ".so";
        System.out.println("操作系统："+ext);
        if(ext.equals(".so")) {
            try {
                NativeLoader.loader( "native" );
            } catch (Exception e) {
                System.out.println("加载so库失败");
            }
            //System.loadLibrary( "BeaconMap" );
        }else{
            try {
                NativeLoader.loader( "native" );
            } catch (Exception e) {
                System.out.println("加载dll库失败");
            }
            //System.loadLibrary( "BeaconMap" );
        }
        //args.getOptionValues("companyId");
        nettyServer.start();


//        try {
//            initServerSocket();
//        } catch (IOException e) {
//            log.info(LogHeader, "初始化ServerSocket异常，异常码："+e.getMessage());
//        }
    }



}
