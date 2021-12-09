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
}
