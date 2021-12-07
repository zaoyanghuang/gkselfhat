package com.gnw.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/*@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，
所以可以用一个静态set保存起来。
@ServerEndpoint 每个客户端连接，又新生成一个实例
*/
@ServerEndpoint("/gkSocketClient/{deviceId}")
@Component
@Slf4j
public class WebSocketServer {
    //当前连接数
    private static int onlineCount = 0;
    //concurrent包的线程安全set,用来存放每个客户端对应的Websocket对象
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    //某个客户端连接会话，通过它给客户端发送数据
    private Session session;
    //客户端Id
    private String deviceId="";
    /*建立成功调用的方法
    * @RequestParam 是从request里面拿取值，而 @PathVariable 是从一个URI模板里面来填充*/
    @OnOpen
    public void onOpen(Session session, @PathParam("deviceId")String deviceId){
        this.session = session;
        this.deviceId = deviceId;
        if(webSocketMap.containsKey(deviceId)){
            webSocketMap.remove(deviceId);
            webSocketMap.put(deviceId, this);
        }else{
            webSocketMap.put(deviceId, this);
            addOnlineCount();
        }
        log.info("客户端："+deviceId+"连接,当前在线数："+getOnlineCount());
        try{
            sendMessage("连接成功");
        }catch (IOException e ){
            log.error("用户："+deviceId+",网络异常！");
        }
    }
    /*连接关闭调用的方法*/


    /*收到客户端消息后调用的方法*/

    /*服务器主动推送消息  getAsyncRemote()和getBasicRemote()确实是异步与同步的区别*/
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);

    }
    public static synchronized int getOnlineCount(){return onlineCount;};
    public static synchronized  void addOnlineCount(){WebSocketServer.onlineCount++;}
    public static synchronized  void subOnlineCount(){WebSocketServer.onlineCount--;}


}
