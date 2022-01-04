package com.gnw.test;

import org.junit.Test;

import java.util.Arrays;

public class Test1 {
    //字符串增加和删除里面元素
    @Test
    public void StringToArray(){
        String str = "ceshi1,ceshi2,ceshi3,ceshi4,ceshi5,";
        String str1 = str.replaceAll("ceshi2,", "");
        String [] strArrays = str1.split(",");
//        for(String string :strArrays){
//            System.out.println(string);
//        }
        String str2 = Arrays.toString(strArrays);
        String str3 = str1+"ceshi6,";
        System.out.println("str3数据:"+str3);
    }
    @Test
    public void strDemo(){
        String str = "test12345.txt";
        String str1 = str.substring(str.lastIndexOf("."));
        String str3 = "adfad,";
        String[] strs = str3.split(",");
        System.out.println("截取结果："+Arrays.toString(strs));
    }
    @Test
    public void strDemo1(){
        String str = "test12345.txt";
        String str2 = "";
        String str3 ="测试";
        //System.out.println("str长度"+str.length());
        System.out.println("str3长度"+str3.length());

    }
    @Test
    public void longLatTest(){



    }
}
