package com.gnw.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class NIOHandler {
    static int socketClientCount=0;//客户端计数器
    //@SneakyThrows
    @Async("asyncServiceExecutor")
    public byte[] read(SelectionKey key){
        byte[] bytes =null;
        SocketChannel readChannel = (SocketChannel) key.channel();
        String clientAddress = null;//客户端地址
        try {
            clientAddress = readChannel.getRemoteAddress().toString();
            //I/O读写操作
            ByteBuffer buffer = ByteBuffer.allocate(1024);//单次发送字节数超过1024则会报异常
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            buffer.clear();
            int len = 0;
            len = readChannel.read(buffer);
            if(len != -1) {
                buffer.flip();//将读到的内容翻转，然后直接写出
                while (buffer.hasRemaining()) {
                    baos.write(buffer.get());
                }
                //System.out.println("读取字节长度" + baos.size());
                bytes = baos.toByteArray();
                if(baos.size()>0)
                    ParseSocketDataUtil.getInstance().parseAllPackage(key, baos.toByteArray());
                //将注册写操作添加到队列中
                //key.attach(baos);
                SelectorApplication.addwriteQueue(key);
            }else{
                key.channel().close();
                key.cancel();
                System.out.println("客户端" + readChannel.getRemoteAddress() + "连接断开；");
            }
        } catch (IOException e) {
            socketClientCount--;
            System.out.println("客户端"+clientAddress+"断开，剩客户端"+socketClientCount+"个");
            log.info("read异常，异常码:" + e.getMessage());
        }
        return bytes;
    }
    @Async("asyncServiceExecutor")
    public void write(final SelectionKey key,byte[] bytes){
        try {
            //写操作
            SocketChannel writeChannel = (SocketChannel) key.channel();
            //拿到客户端传递的数据
            ByteArrayOutputStream attachment = (ByteArrayOutputStream) key.attachment();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(bytes);
            buffer.flip();
            writeChannel.write(buffer);
            writeChannel.close();
        }catch(IOException e){
            log.info("write异常，异常码："+e.getMessage());
        }

    }
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    //向通道写数据
    private void writeDataToSocket(SelectionKey sk,byte[] bytes) throws IOException {
        SocketChannel sc = (SocketChannel) sk.channel();
        byteBuffer.clear();
        //byteBuffer=ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        while(byteBuffer.hasRemaining()){
            sc.write(byteBuffer);
        }
    }
    //从通道读取数据
    private JSONObject readDataFromSocket(SelectionKey sk){
        JSONObject jsonObject = new JSONObject();
        SocketChannel sc = (SocketChannel) sk.channel();
        byteBuffer.clear();
        List<Byte> list = new ArrayList<>();
        int readNum=0;
        boolean flag = false;
        //while( !flag ){
//            try {
//                if (!((readNum=sc.read(byteBuffer))>0)){
//                    flag = false;
//                    System.out.println("readNum<0");
//                    break;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                try {
//                    System.out.println("socket客户端连接中断");
//                    //socketClientCount--;
//                    sk.cancel();
//                    sc.close();
//                } catch (IOException ioException) {
//                    ioException.printStackTrace();
//                }
//                break;
//            }
        //byteBuffer.flip(); //读转写在这里不需要转？？
        while(byteBuffer.hasRemaining()){//判断ByteBuffer有没有到上限
            list.add(byteBuffer.get());
        }
        byteBuffer.clear();
        //}
        byte[] bytes = new byte[list.size()];
        for(int i =0;i<bytes.length;i++){
            bytes[i] = list.get(i);
        }
        jsonObject.put("SelectionKey",sk);
        jsonObject.put("bytesData", bytes);
        String s = ParseSocketDataUtil.getInstance().bytesToHex(bytes);
        jsonObject.put("StringData",s);
        return jsonObject;
    }
}
