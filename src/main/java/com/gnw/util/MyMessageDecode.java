package com.gnw.util;

import com.gnw.pojo.LocationPackageInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;


import java.util.List;


public class MyMessageDecode extends ByteToMessageDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> outList) throws Exception {

        //final FullHttpRequest req = (FullHttpRequest)byteBuf;
        //String uri = req.uri();
        //System.out.println("uri:"+uri.toString());
        //收到字节长度
        System.out.println("收到数据："+byteBuf);
        int length = byteBuf.readableBytes();
        System.out.println("进入自定义解码方法，开始解码,byteBuf长度"+length);
        if(length<4)
            return;
        byte [] receiveBytes = new byte[length];
        //复制内容到字节数组
        byteBuf.readBytes(receiveBytes);
        LocationPackageInfo locationPackageInfo = ParseSocketDataUtil.getInstance().parseNettyPackage(receiveBytes);
        System.out.println("解码后的设备信息"+locationPackageInfo);
        outList.add(locationPackageInfo);
    }
}
