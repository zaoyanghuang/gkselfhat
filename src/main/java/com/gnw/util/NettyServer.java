package com.gnw.util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyServer {
    private static final String IP ="127.0.0.1";// "192.168.10.24";
    private static int port = 8888;
    private static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors()*2;//2倍本服务器cpu核数
    private static final int BIZTHREADSIZE = 100;//定义一个线程数
    //创建两个线程组  BOSS处理连接请求  worker处理客户端业务逻辑  都是无限循环
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);

    public void start(){
        try {
            //服务器的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置队列等待数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            ByteBuf endLimiter = Unpooled.copiedBuffer(ParseSocketDataUtil.getInstance().hexToByteArray("0D0A"));
                            //ByteBuf endLimiter = Unpooled.copiedBuffer("\n\r".getBytes());
                            //参数1024表示单条消息的最大长度，当达到该长度仍然没有找到分隔符就抛出TooLongFrame异常，第二个参数就是分隔符
                            pipeline.addLast(new DelimiterBasedFrameDecoder(1024,false,endLimiter));
                            //添加行解析器  仅以换行符作为结束符
                            //pipeline.addLast(new LineBasedFrameDecoder(176,false,true));
                            //自定义解码
                            pipeline.addLast(new MyMessageDecode());
                            //往pipeline中添加解码器  二进制转字符串
                            //pipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
                            //往pipeline中添加编码器  字符串转二进制
                            //pipeline.addLast("encoder",new StringDecoder());
                            //往pipeline中添加自定义handler  在解码编码后面
                            pipeline.addLast(new NettyServerHandler());//向pipeline中添加自定义处理类
                        }
                    });
            System.out.println("...netty server ready...");
            ChannelFuture cf = bootstrap.bind(port).sync();//绑定端口  非阻塞  绑定不成功会抛异常，
            System.out.println("...netty server started...");
            //启动后注册future监听器
            cf.channel().closeFuture().sync();
        }catch(InterruptedException e){
            System.out.println("netty服务器启动异常："+e.getMessage());
        }finally{
            //关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
