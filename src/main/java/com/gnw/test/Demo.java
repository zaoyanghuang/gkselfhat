package com.gnw.test;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gnw.util.ParseSocketDataUtil;

import com.google.gson.Gson;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import org.json.JSONException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import java.nio.channels.SelectionKey;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.Executors;


public class Demo {
    @Test
    public void test(){
        byte [] bytes = {108,01,01,80,00,01,00,00,00,02,37};
        System.out.println("字节本身"+bytes[0]);
    }
    @Test
    public void test1() throws JSONException {
        JSONObject jsonOne = new JSONObject();

        JSONObject jsonTwo = new JSONObject();

        jsonOne.put("name", "kewen");

        jsonOne.put("age", "24");
        jsonOne.put("index", 20);

        jsonTwo.put("hobbit", "Dota");

        jsonTwo.put("hobbit2", "wow");
        jsonTwo.put("index", 10);

        JSONObject jsonThree = new JSONObject();

        jsonThree.putAll(jsonOne);

        jsonThree.putAll(jsonTwo);

        System.out.println(jsonThree.toString());
    }
    @Test
    public void test2(){
        byte b= -121;
        byte[] bytes ={0,0};
        //String hex1= ParseSocketDataUtil.getInstance().byteToHex(b);
        //System.out.println(Integer.parseInt(hex1));
        String charStr = String.valueOf(ParseSocketDataUtil.getInstance().bytesToChars(bytes));
        System.out.println("字节数组转字符串"+charStr);
        String hex = ParseSocketDataUtil.getInstance().bytesToHex(bytes);
        //String hex = Integer.toHexString(6002);
        System.out.println(hex);
        System.out.println(Integer.parseInt( hex, 16));

    }
    @Test
    public void test3(){
        byte[] bytes ={48,48,51};
        String hexString = Integer.toHexString(135);
        System.out.println("hexString"+hexString);
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes).flip();
        CharBuffer cb = cs.decode(bb);
        System.out.println(String.valueOf(cb.array()));
    }
    @Test
    public void test4(){
      Map<String, byte[]> map = new HashMap<>();
        byte[] sk = map.get("str");
        System.out.println("sk:"+sk);
        System.out.println("sk数据:"+(sk==null));

    }
    @Test
    public void test5() {
        byte[] bytes1 = {-82, 2, 3, 4, 5, 6};
        System.out.println(bytes1[0]);
        int heightBit = (int) ((bytes1[0] >> 4) & 0x0F);

        int lowBit = (int) (0x0F & bytes1[0]);

        System.out.println(heightBit * 16 + lowBit);
    }
    @Test
    public void test6() {
        // 创建集合
        ArrayList<String> sites = new ArrayList<String>();
        sites.add("Google");
        sites.add("Runoob");
        sites.add("Taobao");
        sites.add("Zhihu");
        // 获取迭代器
        Iterator<String> it = sites.iterator();
        // 输出集合中的第一个元素
        while(it.hasNext()){
            System.out.println(it.next());
            if(it.next() == "Taobao") break;
        }
    }
    @Test
    @Async("asyncServiceExecutor")
    public void test7() {
                try {
                    for (int i =0;i<100;i++){
                        System.out.println("当前线程"+Thread.currentThread().getName()+"当前数据"+i);
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }
    @Test
    public void test8() {
        Stu stu = new Stu("测试",20);
        Gson gson = new Gson();
        String str = gson.toJson(stu);
        String jsonObject = JSONArray.toJSONString(stu);
        System.out.println("json格式"+str);
        System.out.println("String格式"+ stu.toString());
    }
    class Stu{
        String name = "张三";
        int age = 18;

        public Stu(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Stu{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
    @Test
    public void test9() {
       List<String> list = new ArrayList();
       list.add("数据1");
        list.add("数据2");
        list.add("数据3");
       Map<String,List> map = new HashMap<>();
       map.put("ceshi",list);
//       for(String str :map.keySet()){
//           System.out.println("key:"+str+"  "+"value:"+map.get(str));
//       }
        System.out.println(map.containsKey("ceshi"));
//        List listTest = map.get("ceshi1");
//        System.out.println("ceshi1的数据"+listTest);
    }

}
