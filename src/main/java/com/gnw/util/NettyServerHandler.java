package com.gnw.util;

import com.gnw.pojo.LocationPackageInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    public static List<Channel> channels = new ArrayList<>();
    //客户端通道就绪
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channels.add(channel);
        System.out.println("客户端"+channel.remoteAddress().toString().substring(1)+"上线");

    }
    //客户端未就绪
    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        Channel channel = ctx.channel();
        channels.remove(channel);
        System.out.println("客户端"+channel.remoteAddress().toString().substring(1)+"离线");
    }
    //一般作为业务数据处理  自定义解码完成的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        //读取数据
        LocationPackageInfo locationPackageInfo = (LocationPackageInfo)msg;
        System.out.println("读取到设备"+locationPackageInfo.getDevice_num()+"数据并开始调用算法");

//        channel.writeAndFlush("");
//        ctx.close();
        //写给客户端
//        ByteBuf buff = Unpooled.buffer();
//        // 对接需要16进制
//        buff.writeBytes(ConvertCode.hexString2Bytes(receiveStr));
//        channel.writeAndFlush(buff);
    }
    //连接异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();
        //channels.remove(channel);  //自决定是否移除channel
        System.out.println("自定义NettyServerHandler异常："+cause.getMessage());

    }
}


