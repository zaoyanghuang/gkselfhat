package com.gnw.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//重写一直有问题  先废弃
public class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        ByteBuf endLimiter = Unpooled.copiedBuffer(ParseSocketDataUtil.getInstance().hexToByteArray("0D0A"));
        //参数1024表示单条消息的最大长度，当达到该长度仍然没有找到分隔符就抛出TooLongFrame异常，第二个参数就是分隔符
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024,false,endLimiter));
        //添加行解析器  仅以换行符作为结束符
        //pipeline.addLast(new LineBasedFrameDecoder(176,false,true));
        //自定义解码
        pipeline.addLast("decode",new MyMessageDecode());
        //往pipeline中添加解码器  二进制转字符串
        //pipeline.addLast("decoder",new StringDecoder(CharsetUtil.UTF_8));
        //往pipeline中添加编码器  字符串转二进制
        //pipeline.addLast("encoder",new StringDecoder());
        //往pipeline中添加自定义handler  在解码编码后面
        pipeline.addLast(new NettyServerHandler());//向pipeline中添加自定义处理类
    }
}
