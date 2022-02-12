package com.gnw.util;

import com.gnw.service.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
@Component
public class SelectorServer {
    private final String LogHeader = "SelectorApplication";
    private final static String RECEIVE_DATA_STRING="receiveDataString";
    private final static String RECEIVE_DATA_BYTES="receiveDataBytes";
    public final static String SERVER_IP="192.168.0.187";
    public final static int PORT=8888;
    public static ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    //存储SelectionKey的队列
    private static List<SelectionKey> writeQueue = new ArrayList<>();
    private static Selector selector = null;
    private static ServerSocketChannel serverSocketChannel;
    public static boolean closed = false;

    //添加到SelectionKey到队列
    public static void addwriteQueue(SelectionKey key){

        synchronized (writeQueue){
            writeQueue.add(key);
            //唤醒主线程
            selector.wakeup();
        }
    }
    @Autowired
    private ThreadPoolService threadPoolService;
    @Autowired
    private ParseSocketDataUtil parseSocketDataUtil;

    //初始化ServerSocket 监听设备socket  异步处理
    public void initServerSocket() throws IOException {
        //打开一个ServerSocketChannel
        serverSocketChannel = ServerSocketChannel.open();
        //获取ServerSocketChannel绑定的Socket
        ServerSocket ss = serverSocketChannel.socket();
        //InputStream inputStream = ss.accept().getInputStream();
        //设置ServerSocket监听的端口
        ss.bind(new InetSocketAddress(PORT));
        //设置ServerSocketChannel为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //打开一个通道选择器
        selector = Selector.open();
        /*
         * 5.注册事件类型
         *  sel:通道选择器
         *  ops:事件类型 ==>SelectionKey:包装类，包含事件类型和通道本身。四个常量类型表示四种事件类型
         *  SelectionKey.OP_ACCEPT 获取报文      SelectionKey.OP_CONNECT 连接
         *  SelectionKey.OP_READ 读           SelectionKey.OP_WRITE 写
         */
        //将ServerSocketChannel注册到选择器上并监听accept事件
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端地址："+getMyIp()+"正在监听端口:"+ PORT);
        while(!closed){  //连接和断开时候都能进入此监控
            //这里会发生阻塞首次调用select()方法，如果有一个通道变成就绪状态，返回了1，若再次调用select()方法，
            // 如果另一个通道就绪了，它会再次返回1。如果对第一个就绪的channel没有做任何操作，现在就有两个就绪的通道，
            // 但在每次select()方法调用之间，只有一个通道就绪了
            int n = selector.select();
            //没有就绪通道则继续监听
            if(n > 0){
                //获取SelectionKeys上已经就绪的集合  迭代
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                //遍历当前I/O通道
                while(iterator.hasNext()){
                    SelectionKey sk = iterator.next();
                    //并部署移除当前通道,而是标记当前I/O通道已经处理
                    iterator.remove();
                    //通道上是否有可接受的连接 有则绑定到选择器
                    sk.interestOps();
                    //判断事件类型 相应处理
                    if(sk.isAcceptable()){
                        ServerSocketChannel sscTmp = (ServerSocketChannel) sk.channel();
                        SocketChannel sc = sscTmp.accept();// accept()方法会一直阻塞到有新连接到达
                        //获取客户端数据 设置为非阻塞状态
                        sc.configureBlocking(false);
                        //注册到通道selector
                        sc.register(selector, SelectionKey.OP_READ);
                        if(sc!=null) {
                            NIOHandler.socketClientCount++;
                            System.out.println("第"+NIOHandler.socketClientCount+"个socketClient:"+sc.getRemoteAddress().toString()+"连接上serverSocket");
                        }else{
                            NIOHandler.socketClientCount--;
                            System.out.println("sc为空,sc被取消了");
                        }
                    }else if(sk.isReadable()){
                        sk.cancel();
                        byte[] recevieData = NIOHandler.read(sk);
                        //System.out.println("读到的数据"+Arrays.toString(receiveData));
                        long timeStart = System.currentTimeMillis();
                        long timeEnd = System.currentTimeMillis();
                        //System.out.println("耗时"+(timeEnd-timeStart));

//                            new Thread(){
//                                @Override
//                                public void run() {
//                                    long timeStart = System.currentTimeMillis();
//                                    parseSocketDataUtil.jointPackage((SelectionKey)socketData.get("SelectionKey"),(byte[])socketData.get("bytesData"));
//                                    long timeEnd = System.currentTimeMillis();
//                                    System.out.println("耗时"+(timeEnd-timeStart));
//                                    //log.info("解析结果"+jsonObject);
//                                }
//                            }.start();
//                        new Thread(){
//                            @Override
//                            public void run() {
//                                System.out.println("写入数据库的线程运行");
//                            }
//                        }.start();
//                    Runnable runnable = new Runnable() {
//                        public void run() {
//                            parseSocketDataUtil.parseData(sk);
//                        }
//                    };
//                    ScheduledExecutorService service = Executors
//                            .newSingleThreadScheduledExecutor();
//                    // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
//                    service.scheduleAtFixedRate(runnable, 2, 1, TimeUnit.SECONDS);
                    } else if(sk.isWritable()){
                        ByteArrayOutputStream baos=(ByteArrayOutputStream)sk.attachment();

                        //((SocketChannel) sk.channel()).write(ByteBuffer.wrap(s.getBytes()));
                        sk.interestOps(SelectionKey.OP_READ);
                    }
                }
            }else{
                synchronized (writeQueue){
                    while(writeQueue.size()>0){
                        SelectionKey key = writeQueue.remove(0);
                        //注册写事件
                        SocketChannel channel = (SocketChannel)key.channel();
                        Object attachment = key.attachment();
                        channel.register(selector, SelectionKey.OP_WRITE,attachment);
                    }
                }
            }
        }
    }

    //获取ip地址
    public static String getMyIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        try {
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
                Enumeration address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = (InetAddress) address.nextElement();
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
