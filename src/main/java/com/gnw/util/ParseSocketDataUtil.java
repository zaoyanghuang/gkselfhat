package com.gnw.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gnw.pojo.LocationPackageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.handler.Handler;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Logger;

@Component  //放入容器
@Slf4j
public class ParseSocketDataUtil {
    //存放设备解析后的数据
    //List<String> parsedDeviceInfoList = new ArrayList<String>(){{add("测试1");add("测试2");add("测试3");}};
    List<LocationPackageInfo> parsedDeviceInfoList = new ArrayList<LocationPackageInfo>();

    //帧头
    public final byte[] FRAME_HEADER = {(byte)0xAA,(byte)0xAA};
    //    认证包	0xB1
    public final byte IDENTIFICATION_PACKAGE = (byte) 0xB1;
    //    登陆包	0xB2
    public final byte LOGIN_PACKAGE = (byte) 0xB2;
    //    定位包	0xA6
    public final byte LOCATION_PACKAGE = (byte) 0xA6;
    //    定位应答包	0xA7
    public final byte LOCATION_RESPONSE_PACKAGE = (byte) 0xA7;
    //    握手包	0xB1
    public final byte HAND_PACKAGE = (byte) 0xB1;
    //    下行控制包	0xC1
    public final byte DOWN_CONTROL_PACKAGE = (byte) 0xC1;
    //    心跳包	0xF0
    public final byte HEART_BEAT_PACKAGE = (byte) 0xF0;
    //    心跳应答包	0xF1
    public final byte HEART_BEAT_RESPONSE_PACKAGE = (byte) 0xF1;


    private static ParseSocketDataUtil parseSocketDataUtil;
    private ParseSocketDataUtil(){};
    public static ParseSocketDataUtil getInstance(){
        if(parseSocketDataUtil == null){
            synchronized (ParseSocketDataUtil.class){
                if(parseSocketDataUtil == null){
                    parseSocketDataUtil = new ParseSocketDataUtil();
                }
            }
        }

        return parseSocketDataUtil;
    }
    public LocationPackageInfo parseNettyPackage(byte [] receiveBytes){
        System.out.println("收到数据长度"+receiveBytes.length+"内容"+bytesToHex(receiveBytes));
        LocationPackageInfo locationPackageInfo=null;
        int markIndex = 0;
        while(receiveBytes.length > markIndex+4){
            if(receiveBytes[markIndex] == (byte)0xAA && receiveBytes[markIndex+1] == (byte)0xAA){
                int singelValidlen = byteToInt(receiveBytes[markIndex+3])+2;//包含固定头
                if(receiveBytes.length >= singelValidlen){ //有效长
                    byte[] checkBytes = new byte[singelValidlen-3];
                    System.arraycopy(receiveBytes, markIndex, checkBytes, 0, singelValidlen-3);
                    byte checkCode = checkCode(checkBytes,singelValidlen-3);
                    byte orgCheckCode = receiveBytes[markIndex+singelValidlen-3];
                    if(checkCode == orgCheckCode){//checkCode == orgCheckCode
                        //消息体字节数组
                        byte[] singelPackage = new byte[singelValidlen-5];
                        System.arraycopy(receiveBytes, markIndex+2, singelPackage, 0, singelValidlen-5);
                        locationPackageInfo = parseValidData(singelPackage);
                        markIndex += singelValidlen;
                    }else{
                        markIndex++;
                        log.info("单包中校验失败，继续下一包。");
                        continue;
                    }
                }else{
                    log.info("收到字节数少于有效长度。");
                    break;
                }
            }else{
                log.info("固定头非AAAA，无效数据包。");
                markIndex++;
                continue;
            }
        }
        return locationPackageInfo;
    }
    //多包数据拆解后调用单包解析
    List<Byte> cacheBytes = new LinkedList<>(); //缓存
    public void parseAllPackage(byte[] receiveBytes ){
        //System.out.println("收到需要解析数据长度："+receiveBytes.length);
        for(int i=0;i<receiveBytes.length;i++){
            cacheBytes.add(receiveBytes[i]);
        }
        byte[] parseBytes = new byte[cacheBytes.size()];
        for(int i = 0;i<cacheBytes.size();i++){
            parseBytes[i] = cacheBytes.get(i);
        }
        int markIndex = 0;
        int endIndex = 0;
        while(parseBytes.length > markIndex+4){
            if(parseBytes[markIndex] == (byte)0xAA && parseBytes[markIndex+1] == (byte)0xAA){
                   int singelValidlen = byteToInt(parseBytes[markIndex+3])+2;//包含固定头
                   if(parseBytes.length >= singelValidlen){ //有效长
                       byte[] checkBytes = new byte[singelValidlen-3];
                       System.arraycopy(parseBytes, markIndex, checkBytes, 0, singelValidlen-3);
                       byte checkCode = checkCode(checkBytes,singelValidlen-3);
                       byte orgCheckCode = parseBytes[markIndex+singelValidlen-3];
                       if(checkCode == orgCheckCode){//checkCode == orgCheckCode
                           //消息体字节数组
                           byte[] singelPackage = new byte[singelValidlen-5];
                           System.arraycopy(parseBytes, markIndex+2, singelPackage, 0, singelValidlen-5);
                           parseSingelPackage(singelPackage);
                           endIndex = markIndex+singelValidlen;
                           markIndex += singelValidlen;
                       }else{
                           markIndex++;
                           log.info("单包中校验失败，继续下一包。");
                           continue;
                       }
                   }else{
                       log.info("收到字节数少于有效长度。");
                       break;
                   }
            }else{
                log.info("固定头非AAAA，跳过继续下一字节。");
                markIndex++;
                continue;
            }
        }
        //把解析过的数据清除
        for(int i = 0;i<endIndex;i++)
            cacheBytes.remove(0);
    }
    //带返回值单包解析
    public LocationPackageInfo parseValidData(byte[] validBytes){
        //System.out.println("单包字节数组"+Arrays.toString(singelPackageBytes));
        LocationPackageInfo locationPackageInfo = new LocationPackageInfo();
        int index = 0;
        byte frameType = validBytes[0];
        index+=2;
        if(frameType == LOCATION_PACKAGE){
            locationPackageInfo.setPackageMarker(0x00000001 & (validBytes[index]>>6));//标志位  握手标志 组包标志
            locationPackageInfo.setHandMarker(0x00000001 & (validBytes[index]>>7));
            locationPackageInfo.setReportTime(validBytes[index++]);//上报时间间隔
            locationPackageInfo.setCachePackageNum(validBytes[index++]);//当前缓存包个数  4
            index +=2;
            String device_num=byteToHex(validBytes[index])+byteToHex(validBytes[index-1]);
            locationPackageInfo.setDevice_num(device_num);//设备编号
            index +=2;
            String device_batch_num=byteToHex(validBytes[index])+byteToHex(validBytes[index-1]);
            locationPackageInfo.setDevice_batch_num(device_batch_num);//设备批次号
            String device_num_final = device_batch_num+device_num;
            //判断设备ID是否合法（前端登录后调用查看该设备ID实时位置后合法）  非法则不做处理  合法需要解析
            if(!device_num_final.equals("00000000")){
                log.info("解析到设备Id:"+device_num_final);
                //log.info("设备Id为00000000,跳转至解析下一包");
                index++;//预留
                String client="";//客户
                switch(validBytes[index++]){
                    case 0:
                        client = "格纳微标准版";
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                locationPackageInfo.setClient(client);
                String company = byteToHex(validBytes[index+1]);
                locationPackageInfo.setCompany(company);
                index+=2;
                String versionNum=String.valueOf(getHeight4Bit(validBytes[index]))+"."+String.valueOf(getLow4Bit(validBytes[index]));
                locationPackageInfo.setVersionNum(versionNum);
                //System.out.println("locationPackageInfo对象"+locationPackageInfo.toString());
                byte [] field_data = new byte[validBytes.length-(index+1)];//字段库集合内容
                System.arraycopy(validBytes, index+1, field_data, 0, field_data.length);
                log.info("字段库的字节数组:"+Arrays.toString(field_data));
                if(field_data.length>0){
                    JSONObject fieldLibInfo = parseFieldData(field_data);
                    locationPackageInfo.setFieldLibInfo(fieldLibInfo);
                    System.out.println("locationPackageInfo信息"+locationPackageInfo);
                }else{
                    log.info("字段集中内容为空");
                }
            }else{
                System.out.println("该设备ID不满足条件因此不于解析。继续下一包解析!");
            }

        }else if(frameType == LOCATION_RESPONSE_PACKAGE){

        }else if(frameType == HAND_PACKAGE){

        }else if(frameType == DOWN_CONTROL_PACKAGE){

        }else if(frameType == HEART_BEAT_PACKAGE){

        }else if(frameType == HEART_BEAT_RESPONSE_PACKAGE){

        }
        return locationPackageInfo;

    }

    //对单包消息体解析
    public void parseSingelPackage(byte[] singelPackageBytes){
        //System.out.println("单包字节数组"+Arrays.toString(singelPackageBytes));
        LocationPackageInfo locationPackageInfo = new LocationPackageInfo();
        int index = 0;
        byte frameType = singelPackageBytes[0];
        index+=2;
        if(frameType == LOCATION_PACKAGE){
            locationPackageInfo.setPackageMarker(0x00000001 & (singelPackageBytes[index]>>6));//标志位  握手标志 组包标志
            locationPackageInfo.setHandMarker(0x00000001 & (singelPackageBytes[index]>>7));
            locationPackageInfo.setReportTime(singelPackageBytes[index++]);//上报时间间隔
            locationPackageInfo.setCachePackageNum(singelPackageBytes[index++]);//当前缓存包个数  4
            index +=2;
            String device_num=byteToHex(singelPackageBytes[index])+byteToHex(singelPackageBytes[index-1]);
            locationPackageInfo.setDevice_num(device_num);//设备编号
            index +=2;
            String device_batch_num=byteToHex(singelPackageBytes[index])+byteToHex(singelPackageBytes[index-1]);
            locationPackageInfo.setDevice_batch_num(device_batch_num);//设备批次号
            String device_num_final = device_batch_num+device_num;
            //判断设备ID是否合法（前端登录后调用查看该设备ID实时位置后合法）  非法则不做处理  合法需要解析
            if(!device_num_final.equals("00000000")){
                log.info("解析到设备Id:"+device_num_final);
                //log.info("设备Id为00000000,跳转至解析下一包");
                index++;//预留
                String client="";//客户
                switch(singelPackageBytes[index++]){
                    case 0:
                        client = "格纳微标准版";
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
                locationPackageInfo.setClient(client);
                String company = byteToHex(singelPackageBytes[index+1]);
                locationPackageInfo.setCompany(company);
                index+=2;
                String versionNum=String.valueOf(getHeight4Bit(singelPackageBytes[index]))+"."+String.valueOf(getLow4Bit(singelPackageBytes[index]));
                locationPackageInfo.setVersionNum(versionNum);
                //System.out.println("locationPackageInfo对象"+locationPackageInfo.toString());
                byte [] field_data = new byte[singelPackageBytes.length-(index+1)];//字段库集合内容
                System.arraycopy(singelPackageBytes, index+1, field_data, 0, field_data.length);
                log.info("字段库的字节数组:"+Arrays.toString(field_data));
                if(field_data.length>0){
                    JSONObject fieldLibInfo = parseFieldData(field_data);
                    locationPackageInfo.setFieldLibInfo(fieldLibInfo);
                    System.out.println("locationPackageInfo信息"+locationPackageInfo);
                    parsedDeviceInfoList.add(locationPackageInfo);
                }else{
                    log.info("字段集中内容为空");
                }
            }else{
                System.out.println("该设备ID不满足条件因此不于解析。继续下一包解析!");
            }

        }else if(frameType == LOCATION_RESPONSE_PACKAGE){

        }else if(frameType == HAND_PACKAGE){

        }else if(frameType == DOWN_CONTROL_PACKAGE){

        }else if(frameType == HEART_BEAT_PACKAGE){

        }else if(frameType == HEART_BEAT_RESPONSE_PACKAGE){

        }


    }
    //缓存  所有解析完的数据  按照ID存放  含data对应的sk
    Map<String,List<Map>> cacheMap = new HashMap<>();
    List<JSONObject> jsonObjectsList = new ArrayList<>();
    //多包情况下  记录上一次非完整的包字节
    Map<SelectionKey,byte[]> unfullPackage = new HashMap<>();
    //收到多包  先组包 组包完成后解析
    public void jointPackage(SelectionKey sk, byte[] allbytes) {
        System.out.println("jointPackage方法运行");
        if (sk != null && allbytes.length > 0) {
            List<byte[]> allPackageList = new ArrayList<>();
            int singlePackageIndex = 0;//单包首索引
            //查看该sk是否还有未完整包数据
            byte[] unfullPackagebytes = unfullPackage.get(sk);
            byte[] singleBytesHead = new byte[2];
            List<Byte> tempList = new ArrayList();
            System.arraycopy(allbytes, singlePackageIndex, singleBytesHead, 0, singleBytesHead.length);
            if (unfullPackagebytes == null && Arrays.equals(singleBytesHead, FRAME_HEADER)) {  //以AAAA开头 且没有没有非完整包
                tempList.clear();
                for(int i=0;i<allbytes.length;i++){
                    tempList.add(allbytes[i]);
                }
            } else if (unfullPackagebytes != null && !Arrays.equals(singleBytesHead, FRAME_HEADER)) {//当前sk有非完整包  并且该多包不是以AAAA开头
                byte[] jointPackageBytes = new byte[allbytes.length + unfullPackagebytes.length];
                System.arraycopy(unfullPackagebytes, 0, jointPackageBytes, 0, unfullPackagebytes.length);
                System.arraycopy(allbytes, 0, jointPackageBytes, unfullPackagebytes.length, allbytes.length);
                tempList.clear();
                for(int i=0;i<jointPackageBytes.length;i++){
                    tempList.add(jointPackageBytes[i]);
                }

            } else {
                log.info("解析拼接包出现异常。未完整包不能和下一包拼接成功");
            }
            //重新组包后字节数组
            byte [] sumPackageBytes = new byte[tempList.size()];
            for(int i=0;i<tempList.size();i++){
                sumPackageBytes[i]=tempList.get(i);
            }
            while (singlePackageIndex <= (sumPackageBytes.length - 1)) {
                if((sumPackageBytes.length - 1-singlePackageIndex)<=3){
                    unfullPackage.put(sk, Arrays.copyOfRange(sumPackageBytes, singlePackageIndex, sumPackageBytes.length));
                    break;
                }else if((sumPackageBytes.length-singlePackageIndex) == (sumPackageBytes[singlePackageIndex+3]+2)){
                    unfullPackage.put(sk, Arrays.copyOfRange(sumPackageBytes, singlePackageIndex, sumPackageBytes.length));
                    break;
                }
                //System.out.println("传入前的字节数组"+Arrays.toString(sumPackageBytes));
                //System.out.println("传入的字节"+sumPackageBytes[singlePackageIndex + 3]);
                //System.out.println("索引"+singlePackageIndex );

                int singlePackageLength = byteToInt(sumPackageBytes[singlePackageIndex + 3]);
                //System.out.println("单包长度"+singlePackageLength);
                byte[] singleBytes = new byte[singlePackageLength+2];
                System.arraycopy(sumPackageBytes, singlePackageIndex, singleBytes, 0, singlePackageLength+2);//单包
                //System.out.println("单包字节数组"+Arrays.toString(singleBytes));
                //解析单包  可以试试多包一起解析是不是快一点
                JSONObject jsonObject = parseData(sk,singleBytes);
                jsonObjectsList.add(jsonObject);
                singlePackageIndex+=(singlePackageLength+2);
                //System.out.println("单次循环执行完后singlePackageIndex"+singlePackageIndex);
            }
            //System.out.println("多包解析完成数据"+jsonObjectsList.toString());
        }
    }

    public JSONObject parseData(SelectionKey sk, byte[] bytes){
       JSONObject jsonObject = new JSONObject();
       if(sk!=null && bytes.length>0){
               //去除校验码后的字节数组  消息头+消息体
               byte[] checkBytes = new byte[bytes.length-3];
               for(int i=0;i<checkBytes.length;i++){
                   checkBytes[i] = bytes[i];
               }
               //校验审核
               int byteslen = checkBytes.length;
               byte checkCode = checkCode(checkBytes, byteslen);
               byte crcOrg = bytes[bytes.length-3];
               if(checkCode != crcOrg){
                   log.info("校验不通过，checkCode:"+checkCode+"crcOrg:"+crcOrg);
                   return null;
               }
               int index = 0;
               byte[] bytesHead=new byte[2];
               System.arraycopy(bytes,0,bytesHead, 0, bytesHead.length);
               index +=2;
               if(bytes.length>=174){
                   if(Arrays.equals(bytesHead, FRAME_HEADER)){//核对固定头  有效
                       if(bytes[index++] == LOCATION_PACKAGE){//定位包
                           index++;
                           jsonObject.put("packageMarker",0x00000001 & (bytes[index]>>6));//标志位  握手标志 组包标志
                           jsonObject.put("handMarker",0x00000001 & (bytes[index]>>7));
                           jsonObject.put("reportTime",bytes[index++]);//上报时间间隔
                           jsonObject.put("cachePackageNum",bytes[index++]);//当前缓存包个数
                           index +=2;
                           String device_num=byteToHex(bytes[index])+byteToHex(bytes[index-1]);
                           jsonObject.put("device_num",device_num);//设备编号
                           index +=2;
                           String device_batch_num=byteToHex(bytes[index])+byteToHex(bytes[index-1]);
                           jsonObject.put("device_batch_num",device_batch_num);//设备批次号
                           jsonObject.put("device_batch_num",device_batch_num);
                           index++;//预留
                           String client="";//客户
                           switch(bytes[index++]){
                               case 0:
                                   client = "格纳微标准版";
                                   break;
                               case 1:
                                   break;
                               case 2:
                                   break;
                           }
                           jsonObject.put("client",client);
                           String company = byteToHex(bytes[index+1]);
                           jsonObject.put("company",company);
                           index+=2;
                           String versionNum=String.valueOf(getHeight4Bit(bytes[index]))+"."+String.valueOf(getLow4Bit(bytes[index]));
                           jsonObject.put("versionNum",versionNum);
                           byte [] field_data = new byte[checkBytes.length-(index+1)];//字段库集合内容
                           System.arraycopy(bytes, index+1, field_data, 0, field_data.length);
                           log.info("字段库的字节数组:"+Arrays.toString(field_data));
                           if(field_data.length>0){
                               JSONObject parsedFieldData = parseFieldData(field_data);
                               jsonObject.putAll(parsedFieldData);
                           }else{
                               log.info("字段集中内容为空");
                           }


                       }else if(bytes[index++] == LOCATION_RESPONSE_PACKAGE){

                       }else if(bytes[index++] == HAND_PACKAGE){

                       }else if (bytes[index++] == DOWN_CONTROL_PACKAGE){

                       }else if (bytes[index++] == HEART_BEAT_PACKAGE){

                       }else if (bytes[index++] == HEART_BEAT_RESPONSE_PACKAGE){

                       }
                   }
           }



           //返回数据给sk客户端
           sk.attach("yyyyyy");
           sk.interestOps(SelectionKey.OP_WRITE);
       }else{
           System.out.println("sk和hexSocketData为空");
       }
       return jsonObject;
    }
    //字段库内容解析
    public JSONObject parseFieldData(byte[] field_data){
        //System.out.println("field_data数组长度"+field_data.length);
        JSONObject jsonObject = new JSONObject();
        int length = 0;
        while(length<field_data.length-1){//基于每个字段都有值  length-1
            String byteHead = byteToHex(field_data[length]);
            jsonObject.putAll(parseByFieldType(length,byteHead,field_data));
            length=((int)jsonObject.get("length")+1);
        }
        return jsonObject;
    }
    //判断每一种字段类型并解析
    public JSONObject parseByFieldType(int len,String str,byte[] bytes){
        JSONObject jsonObject = new JSONObject();
        switch(str) {
            case "a1":
                len++;
                jsonObject.put("batteryNum", bytes[len]);
                jsonObject.put("length",len);
                break;
            case "a2":
                byte [] imsiBytes = new byte[15];
                System.arraycopy(bytes, len+1, imsiBytes, 0, 15);
                String imsiStr="";
                for(int i =0;i<imsiBytes.length;i++){
                    imsiStr+=imsiBytes[i];
                }
                len+=15;
                jsonObject.put("imsiStr", imsiStr);
                jsonObject.put("length",len);
                break;
            case "a3":
                len+=1;
                jsonObject.put("networkDb",bytes[len]);
                jsonObject.put("length",len);
                break;
            case "b0":
                byte[] frameNumBytes = new byte[4];
                System.arraycopy(bytes, len+1, frameNumBytes, 0, 4);
                len+=4;
                jsonObject.put("frameNum",bytesToInt(frameNumBytes));
                jsonObject.put("length",len);
                break;
            case "b1":
                byte [] gpsTimeBytes = new byte[6];
                System.arraycopy(bytes, len+1, gpsTimeBytes,0,6);
                String gpsTimeStr="";
                for(int i=(gpsTimeBytes.length-1);i>=0;i--){
                    if(i>=4){
                        gpsTimeStr += gpsTimeBytes[i]+"-";
                    }else if(i==3){
                        gpsTimeStr += gpsTimeBytes[i]+" ";
                    }else if(i<3 && i>0){
                        gpsTimeStr += gpsTimeBytes[i]+":";
                    }else{
                        gpsTimeStr += gpsTimeBytes[i];
                    }
                }
                len+=6;
                jsonObject.put("gpsTimeStr",gpsTimeStr);
                jsonObject.put("length",len);
                break;
            case "b3":
                len+=1;
                int locationState = getLow3Bit(bytes[len]);
                int lbsLocation = (0x00000001 & (bytes[len]>>7));
                jsonObject.put("locationState",locationState);
                jsonObject.put("lbsLocation",lbsLocation);
                jsonObject.put("length",len);
                break;
            case "b4":
                len+=1;
                int dangerWarn = 0x00000001 & bytes[len];//危险
                int fellWarn = (0x00000001 & (bytes[len]>>1));//跌落
                int tripWarn = (0x00000001 & (bytes[len]>>2));//摔倒
                jsonObject.put("dangerWarn",dangerWarn);
                jsonObject.put("fellWarn",fellWarn);
                jsonObject.put("tripWarn",tripWarn);
                jsonObject.put("length",len);
                break;
            case "b5":
                len+=1;
                String lngLocation = getLow4Bit(bytes[len])==1?"北纬":"南纬";
                String latLocation = getHeight4Bit(bytes[len])==1?"东经":"西经";
                byte [] lngNumberBytes = new byte[4];
                byte [] latNumberBytes = new byte[4];
                System.arraycopy(bytes, len+1, lngNumberBytes, 0,4 );
                len+=4;
                System.arraycopy(bytes, len+1, latNumberBytes, 0,4 );
                len+=4;
                float lngNumber = bytesToFloat(lngNumberBytes, true);
                float latNumber = bytesToFloat(latNumberBytes, true);
                jsonObject.put("lngLocation",lngLocation);
                jsonObject.put("latLocation",latLocation);
                jsonObject.put("lngNumber",lngNumber);
                jsonObject.put("latNumber",latNumber);
                jsonObject.put("length",len);
                break;
            case "b6":
                byte[] highBytes = new byte[2];
                System.arraycopy(bytes, len+1,highBytes,0,2 );
                short highNum = bytesToShort(highBytes, true);
                len+=2;
                jsonObject.put("highNum",highNum);
                jsonObject.put("length",len);
                break;
            case "b7":
                len+=1;
                int groundSpeed = bytes[len];
                jsonObject.put("groundSpeed",groundSpeed);
                jsonObject.put("length",len);
                break;
            case "b8":
                len+=1;
                int eastSpeed = bytes[len];
                len+=1;
                int westSpeed = bytes[len];
                len+=1;
                int skySpeed = bytes[len];
                jsonObject.put("eastSpeed",eastSpeed);
                jsonObject.put("westSpeed",westSpeed);
                jsonObject.put("skySpeed",skySpeed);
                jsonObject.put("length",len);
                break;
            case "b9":
                len+=1;//byte 转 hex 再转 int
                int carryDirection = (Integer.parseInt(byteToHex(bytes[len]),16))*2;
                jsonObject.put("carryDirection",carryDirection);
                jsonObject.put("length",len);
                break;
            case "ba":
                len+=1;
                int satelliteNum = bytes[len];
                jsonObject.put("satelliteNum",satelliteNum);
                jsonObject.put("length",len);
                break;
            case "bb":
                byte[] pDopBytes = new byte[2];
                System.arraycopy(bytes, len+1,pDopBytes,0,2 );
                short pDop = bytesToShort(pDopBytes, true);
                len+=2;
                jsonObject.put("pDop",pDop);
                jsonObject.put("length",len);
                break;
            case "bc":
                byte[] mileSumBytes = new byte[4];
                System.arraycopy(bytes, len+1,mileSumBytes,0,4 );
                float mileSum = bytesToFloat(mileSumBytes, true);
                len+=4;
                jsonObject.put("mileSum",mileSum);
                jsonObject.put("length",len);
                break;
            case "bd":
                byte[] lngErrorBytes = new byte[2];
                System.arraycopy(bytes, len+1,lngErrorBytes,0,2);
                short lngError = bytesToShort(lngErrorBytes,true);
                len+=2;
                byte[] latErrorBytes = new byte[2];
                System.arraycopy(bytes, len+1,latErrorBytes,0,2);
                short latError = bytesToShort(latErrorBytes,true);
                len+=2;
                byte[] highErrorBytes = new byte[2];
                System.arraycopy(bytes, len+1,highErrorBytes,0,2);
                short highError = bytesToShort(lngErrorBytes,true);
                len+=2;
                jsonObject.put("lngError",lngError);
                jsonObject.put("latError",latError);
                jsonObject.put("highError",highError);
                jsonObject.put("length",len);
                break;
            case "c0":
                byte[] mcc4GBytes = new byte[2];
                System.arraycopy(bytes, len+1,mcc4GBytes,0,2);
                int mcc4GNum = Integer.parseInt(bytesToHex(mcc4GBytes),16);
                len+=2;
                byte[] mnc4GBytes = new byte[2];
                System.arraycopy(bytes, len+1,mnc4GBytes,0,2);
                int mnc4GNum = byte2ToInt( mnc4GBytes);
                len+=2;
                byte[] pcid4GBytes = new byte[2];
                System.arraycopy(bytes, len+1,pcid4GBytes,0,2);
                int pcid4GNum = Integer.parseInt(bytesToHex(pcid4GBytes),16);
                len+=2;
                byte[] cid4GBytes = new byte[4];
                System.arraycopy(bytes, len+1,cid4GBytes,0,4);
                int cid4GNum = bytesToInt(cid4GBytes);
                len+=4;
                String signalStrong4G =(bytes[len+1]==1?"-":"")+String.valueOf(bytes[len+2]);
                len+=2;
                jsonObject.put("mcc4GNum",mcc4GNum);
                jsonObject.put("mnc4GNum",mnc4GNum);
                jsonObject.put("pcid4GNum",pcid4GNum);
                jsonObject.put("cid4GNum",cid4GNum);
                jsonObject.put("signalStrong4G",signalStrong4G);
                jsonObject.put("length",len);
                break;
            case "c1":
                byte[] mcc2GBytes = new byte[2];
                System.arraycopy(bytes, len+1,mcc2GBytes,0,2);
                int mcc2GNum = Integer.parseInt(bytesToHex(mcc2GBytes),16);
                len+=2;
                byte[] mnc2GBytes = new byte[2];
                System.arraycopy(bytes, len+1,mnc2GBytes,0,2);
                int mnc2GNum = byte2ToInt( mnc2GBytes);
                len+=2;
                byte[] lac2GBytes = new byte[2];
                System.arraycopy(bytes, len+1,lac2GBytes,0,2);
                int lac2GNum = Integer.parseInt(bytesToHex(lac2GBytes),16);
                len+=2;
                byte[] cid2GBytes = new byte[2];
                System.arraycopy(bytes, len+1,cid2GBytes,0,2);
                int cid2GNum = byte2ToInt(cid2GBytes);
                len+=2;
                String signalStrong2G =(bytes[len+1]==1?"-":"")+String.valueOf(bytes[len+2]);
                len+=2;
                jsonObject.put("mcc2GNum",mcc2GNum);
                jsonObject.put("mnc2GNum",mnc2GNum);
                jsonObject.put("lac2GNum",lac2GNum);
                jsonObject.put("cid2GNum",cid2GNum);
                jsonObject.put("signalStrong2G",signalStrong2G);
                jsonObject.put("length",len);
                break;
            case "c2":
                byte[] guanDaoXBytes = new byte[4];
                System.arraycopy(bytes, len+1,guanDaoXBytes,0,4);
                float guanDaoX = bytesToFloat(guanDaoXBytes,true);
                len+=4;
                byte[] guanDaoYBytes = new byte[4];
                System.arraycopy(bytes, len+1,guanDaoYBytes,0,4);
                float guanDaoY = bytesToFloat(guanDaoYBytes,true);
                len+=4;
                byte[] guanDaoZBytes = new byte[4];
                System.arraycopy(bytes, len+1,guanDaoZBytes,0,4);
                float guanDaoZ = bytesToFloat(guanDaoZBytes,true);
                len+=4;
                jsonObject.put("guanDaoX",guanDaoX);
                jsonObject.put("guanDaoY",guanDaoY);
                jsonObject.put("guanDaoZ",guanDaoZ);
                jsonObject.put("length",len);
                break;
            case "c3":
                byte[] guanDaoTimeBytes = new byte[4];
                System.arraycopy(bytes, len+1,guanDaoTimeBytes,0,4);
                int guanDaoTime = bytesToInt(guanDaoTimeBytes);
                len+=4;
                jsonObject.put("guanDaoTime",guanDaoTime);
                jsonObject.put("length",len);
                break;
            case "c4":
                len+=1;
                int iBeacon = bytes[len];
                List<JSONObject> iBeaconList = new ArrayList<>();
                for(int i=0;i<iBeacon;i++){
                    JSONObject jsonObject1 = new JSONObject();
                    byte[] ibeaconIDBytes = new byte[4];
                    System.arraycopy(bytes, len+1,ibeaconIDBytes,0,4);
                    int ibeaconID = bytesToInt(ibeaconIDBytes);
                    len+=4;
                    int ibeaconRSSI = bytes[len+1];
                    len+=1;
                    byte[] ibeaconTimeBytes = new byte[4];
                    System.arraycopy(bytes, len+1,ibeaconTimeBytes,0,4);
                    int ibeaconTime = bytesToInt(ibeaconTimeBytes);
                    len+=4;
                    jsonObject1.put("ibeaconID",ibeaconID);
                    jsonObject1.put("ibeaconRSSI",ibeaconRSSI);
                    jsonObject1.put("ibeaconTime",ibeaconTime);
                    iBeaconList.add(jsonObject1);
                    jsonObject1.clear();
                }
                jsonObject.put("iBeaconList", iBeaconList);
                jsonObject.put("length",len);
                break;
            case "c5":
                len+=1;
                int deviceReset = bytes[len];
                jsonObject.put("deviceReset", deviceReset);
                jsonObject.put("length",len);
                break;
            case "c6":
                List<JSONObject> ciLiJiList = new ArrayList<>();
                len+=1;
                int ciLiJiCountSum = bytes[len];
                for(int i=0;i<ciLiJiCountSum;i++){
                    JSONObject jsonObject1 = new JSONObject();
                    int ciLiJiCount = bytes[len];
                    byte[] ciLiJiXBytes = new byte[2];
                    System.arraycopy(bytes, len+1,ciLiJiXBytes,0,2);
                    int ciLiJiX = byte2ToInt(ciLiJiXBytes);
                    len+=2;
                    byte[] ciLiJiYBytes = new byte[2];
                    System.arraycopy(bytes, len+1,ciLiJiYBytes,0,2);
                    int ciLiJiY = byte2ToInt(ciLiJiYBytes);
                    len+=2;
                    byte[] ciLiJiZBytes = new byte[2];
                    System.arraycopy(bytes, len+1,ciLiJiZBytes,0,2);
                    int ciLiJiZ = byte2ToInt(ciLiJiZBytes);
                    len+=2;
                    jsonObject1.put("ciLiJiCount",ciLiJiCount);
                    jsonObject1.put("ciLiJiX",ciLiJiX);
                    jsonObject1.put("ciLiJiY",ciLiJiY);
                    jsonObject1.put("ciLiJiZ",ciLiJiZ);
                    ciLiJiList.add(jsonObject1);
                    jsonObject1.clear();
                }
                jsonObject.put("ciLiJiList",ciLiJiList);
                jsonObject.put("length",len);
                break;
            case "c7":
                List<JSONObject> jiaJiList = new ArrayList<>();
                len+=1;
                int jiaJiCountSum = bytes[len];
                for(int i=0;i<jiaJiCountSum;i++){
                    JSONObject jsonObject1 = new JSONObject();
                    int jiaJiCount = bytes[len];
                    byte[] jiaJiXBytes = new byte[2];
                    System.arraycopy(bytes, len+1,jiaJiXBytes,0,2);
                    int jiaJiX = byte2ToInt(jiaJiXBytes);
                    len+=2;
                    byte[] jiaJiYBytes = new byte[2];
                    System.arraycopy(bytes, len+1,jiaJiYBytes,0,2);
                    int jiaJiY = byte2ToInt(jiaJiYBytes);
                    len+=2;
                    byte[] jiaJiZBytes = new byte[2];
                    System.arraycopy(bytes, len+1,jiaJiZBytes,0,2);
                    int jiaJiZ = byte2ToInt(jiaJiZBytes);
                    len+=2;
                    jsonObject1.put("jiaJiCount",jiaJiCount);
                    jsonObject1.put("jiaJiX",jiaJiX);
                    jsonObject1.put("jiaJiY",jiaJiY);
                    jsonObject1.put("jiaJiZ",jiaJiZ);
                    jiaJiList.add(jsonObject1);
                    jsonObject1.clear();
                }
                jsonObject.put("jiaJiList",jiaJiList);
                jsonObject.put("length",len);
                break;
            case "d1":
                len+=1;
                int configSendTime = bytes[len];
                jsonObject.put("configSendTime",configSendTime);
                jsonObject.put("length",len);
                break;
            case "d2":
                len+=1;
                int configIsHand = bytes[len];
                jsonObject.put("configIsHand",configIsHand);
                jsonObject.put("length",len);
                break;
            case "d3":
                len+=1;
                String downWarn="";
                switch (bytes[len]){
                    case 1:
                        downWarn="禁止驶入";
                        break;

                }
                jsonObject.put("downWarn",downWarn);
                jsonObject.put("length",len);
                break;
            case "d4":
                int locationServerIp1 = bytes[len+1];
                int locationServerIp2 = bytes[len+2];
                int locationServerIp3 = bytes[len+3];
                int locationServerIp4 = bytes[len+4];
                String locationServerIp = locationServerIp1+"."+locationServerIp2+"."+locationServerIp3+"."+locationServerIp4;
                len=+4;
                jsonObject.put("locationServerIp",locationServerIp);
                jsonObject.put("length",len);
                break;
            case "d5":
                byte[] locationServerPortBytes = new byte[2];
                System.arraycopy(bytes, len+1,locationServerPortBytes,0,2);
                int locationServerPort = Integer.parseInt(bytesToHex(locationServerPortBytes),16);
                len+=2;
                jsonObject.put("locationServerPort",locationServerPort);
                jsonObject.put("length",len);
                break;
            case "d6":
                len+=1;
                int deviceUpdateHigh = getHeight4Bit(bytes[len]);
                int deviceUpdateLow = getLow4Bit(bytes[len]);
                String deviceUpdate = deviceUpdateHigh+"."+deviceUpdateLow;
                jsonObject.put("deviceUpdate",deviceUpdate);
                jsonObject.put("length",len);
                break;
            case "d7":
                int RTKNtripCasterIp1 = bytes[len+1];
                int RTKNtripCasterIp2 = bytes[len+2];
                int RTKNtripCasterIp3 = bytes[len+3];
                int RTKNtripCasterIp4 = bytes[len+4];
                String RTKNtripCasterIp = RTKNtripCasterIp1+"."+RTKNtripCasterIp2+"."+RTKNtripCasterIp3+"."+RTKNtripCasterIp4;
                len=+4;
                jsonObject.put("RTKNtripCasterIp",RTKNtripCasterIp);
                jsonObject.put("length",len);
                break;
            case "d8":
                byte[] RTKNtripCasterPortBytes = new byte[2];
                System.arraycopy(bytes, len+1,RTKNtripCasterPortBytes,0,2);
                int RTKNtripCasterPort = Integer.parseInt(bytesToHex(RTKNtripCasterPortBytes),16);
                len+=2;
                jsonObject.put("RTKNtripCasterPort",RTKNtripCasterPort);
                jsonObject.put("length",len);
                break;
            case "d9":
                byte [] RTKNtripCountBytes = new byte[12];
                System.arraycopy(bytes, len+1,RTKNtripCountBytes,0,2);
                String RTKNtripCount = String.valueOf(bytesToChars(RTKNtripCountBytes));
                len+=12;
                jsonObject.put("RTKNtripCount",RTKNtripCount);
                jsonObject.put("length",len);
                break;
            case "da":
                byte [] RTKNtripPasswordBytes = new byte[12];
                System.arraycopy(bytes, len+1,RTKNtripPasswordBytes,0,12);
                String RTKNtripPassword = String.valueOf(bytesToChars(RTKNtripPasswordBytes));
                len+=12;
                jsonObject.put("RTKNtripPassword",RTKNtripPassword);
                jsonObject.put("length",len);
                break;
            case "db":
                len+=1;
                int downConfigHeartTime = bytes[len];
                jsonObject.put("downConfigHeartTime",downConfigHeartTime);
                jsonObject.put("length",len);
                break;
            case "dc":
                int downTrackOrderState = bytes[len+1];
                len+=2;
                jsonObject.put("downTrackOrderState",downTrackOrderState);
                jsonObject.put("length",len);
                break;
            case "dd":
                byte [] downDeviceIdBytes = new byte[4];
                System.arraycopy(bytes, len+1, downDeviceIdBytes, 0, 4);
                int downDeviceId = bytesToInt(downDeviceIdBytes);
                len+=4;
                jsonObject.put("downDeviceId",downDeviceId);
                jsonObject.put("length",len);
                break;
            case "de":
                byte [] downProductIdBytes = new byte[4];
                System.arraycopy(bytes, len+1, downProductIdBytes, 0, 4);
                int downProductId = bytesToInt(downProductIdBytes);
                len+=4;
                jsonObject.put("downProductId",downProductId);
                jsonObject.put("length",len);
                break;
            case "df":
                byte [] downAuthKeyBytes = new byte[12];
                System.arraycopy(bytes, len+1, downAuthKeyBytes, 0, 12);
                String downAuthKey = String.valueOf(bytesToChars(downAuthKeyBytes));
                len+=12;
                jsonObject.put("downAuthKey",downAuthKey);
                jsonObject.put("length",len);
                break;
            case "e0":
                len+=1;
                int deviceDataIOState_GNSS = 0x00000001 & bytes[len];
                int deviceDataIOState_RTK = 0x00000001 & (bytes[len]>>1);
                int deviceDataIOState_IMU = 0x00000001 & (bytes[len]>>2);
                int deviceDataIOState_iBeacon = 0x00000001 & (bytes[len]>>3);
                int deviceDataIOState_runMode = 0x00000001 & (bytes[len]>>5);
                int deviceDataIOState_Sleep = getHighNum(bytes[len],2);
                jsonObject.put("deviceDataIOState_GNSS",deviceDataIOState_GNSS);
                jsonObject.put("deviceDataIOState_RTK",deviceDataIOState_RTK);
                jsonObject.put("deviceDataIOState_IMU",deviceDataIOState_IMU);
                jsonObject.put("deviceDataIOState_iBeacon",deviceDataIOState_iBeacon);
                jsonObject.put("deviceDataIOState_runMode",deviceDataIOState_runMode);
                jsonObject.put("deviceDataIOState_Sleep",deviceDataIOState_Sleep);
                jsonObject.put("length",len);
                break;
            case "e2":

                break;
            case "e3":

                break;
            case "e4":

                break;
            case "e5":

                break;
            case "e6":

                break;
            case "e7":

                break;
            case "e8":

                break;
            case "e9":
                len+=1;
                int upInOutDoorLocationState = bytes[len];
                jsonObject.put("upInOutDoorLocationState",upInOutDoorLocationState);
                jsonObject.put("length",len);
                break;
            case "f1":

                break;
            case "f2":

                break;
            case "f3":

                break;
            case "f4":

                break;
            case "f5":

                break;
            case "f6":
                byte [] upAuthResponseBytes = new byte[32];
                System.arraycopy(bytes, len+1, upAuthResponseBytes, 0, 32);
                String upAuthResponse = String.valueOf(bytesToChars(upAuthResponseBytes));
                len+=32;
                jsonObject.put("upAuthResponse",upAuthResponse);
                jsonObject.put("length",len);
                break;
            case "f7":

                break;
            case "f8":

                break;
            default:
                break;
        }
        return jsonObject;
    }
    //byte 转 int
    public int byteToInt(byte data) {
        int heightBit = (int) ((data >> 4) & 0x0F);
        int lowBit = (int) (0x0F & data);
        return heightBit * 16 + lowBit;
    }

    //byte[]转char[]
    public static char[] bytesToChars(byte[] bytes) {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes).flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }
    //short与字节数组
    public  byte[] shortTobyte(short res,boolean islittleEndian) {
        int size = 1 ;
        if(islittleEndian){
            size = 0;
        }
        byte[] targerts = new byte[2];
        targerts[Math.abs(0 - size)] = (byte) (res & 0xff);
        targerts[Math.abs(1 - size)] = (byte) ((res >> 8)&0xff);
        return targerts;
    }

    public  short bytesToShort(byte[] res,boolean islittleEndian ) {
        short targets = bytesToShort1(res, 0,islittleEndian);// | 表示安位或
        return targets;
    }

    public  short bytesToShort1(byte[] bytes, int offset,boolean islittleEndian) {
        int size = 1 ;
        if(islittleEndian){
            size = 0;
        }
        return (short) ((0xff00 & (bytes[Math.abs(offset + 1-size )] << 8))
                | (0x00ff & bytes[Math.abs(offset + 0-size)]));
    }


    //byte[] 和 float 互转
    //float与字节数组
    public static int bytesToInt1(byte[] bytes, int offset,boolean islittleEndian) {
        int size = 3 ;
        if(islittleEndian){
            size = 0;
        }
        return (0xff000000 & (bytes[Math.abs(offset + 3 - size)] << 24))
                | (0x00ff0000 & (bytes[Math.abs(offset + 2-size)] << 16))
                | (0x0000ff00 & (bytes[Math.abs(offset + 1-size)] << 8))
                | (0x000000ff & bytes[Math.abs(offset + 0-size)]);
    }
    public static float bytesToFloat1(byte[] bytes, int offset,boolean islittleEndian) {
        return Float.intBitsToFloat(bytesToInt1(bytes, offset,islittleEndian));
    }

    public static float bytesToFloat(byte[] bytes,boolean islittleEndian) {
        return bytesToFloat1(bytes, 0,islittleEndian);
    }

    public static byte[] floatTobytes2(float f) {
        // 把float转换为byte[]
        int fbit = Float.floatToIntBits(f);

        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (fbit >> (24 - i * 8));
        }

        // 翻转数组
        int len = b.length;
        // 建立一个与源数组元素类型相同的数组
        byte[] dest = new byte[len];
        // 为了防止修改源数组，将源数组拷贝一份副本
        System.arraycopy(b, 0, dest, 0, len);
        byte temp;
        // 将顺位第i个与倒数第i个交换
        for (int i = 0; i < len / 2; ++i) {
            temp = dest[i];
            dest[i] = dest[len - i - 1];
            dest[len - i - 1] = temp;
        }
        return dest;
    }
    //这个函数将byte[]转换成float
    public static float bytesTofloat2(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }
    //获取一个字节的高几位 和 低几位 以及中间几位
    public static int getHighNum(byte b,int length) {
        return b>>(8-length);
    }
    public static int getLowNum(byte b,int length) {
        byte mv=(byte) (0xff>>(8-length));
        return b&mv;
    }
    public static int getMidNum(byte b,int startIndex,int endIndex) {
        byte i=(byte) getHighNum(b,endIndex+1);//先取高几位
        return getLowNum(i,endIndex-startIndex+1);//再取低几位
    }
    //获取高低四位
    public static int getHeight4Bit(byte data){//获取高四位
        int height;
        height = ((data & 0xf0) >> 4);
        return height;
    }

    public static int getLow4Bit(byte data){//获取低四位
        int low;
        low = (data & 0x0f);
        return low;
    }
    //获取一个字节高5位
    public static int getHigh5Bit(byte b){
        return b >> 3;
    }
    //获取一个字节低3位
    public static int getLow3Bit(byte b){
        return b&0x07;
    }
    //将低字节在前转为int，高字节在后的byte数组(与IntToByteArray1想对应)
    public int bytesToInt(byte[] bArr) {
        if (bArr.length != 4) {
            log.info("字节数组转int失败，数组长度不为4");
            return -1;
        }
        return (int) ((((bArr[3] & 0xff) << 24)
                | ((bArr[2] & 0xff) << 16)
                | ((bArr[1] & 0xff) << 8)
                | ((bArr[0] & 0xff) << 0)));
    }
    //两个字节转int
    public static int byte2ToInt(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }
    //字节转十六进制
    public String byteToHex(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if(hex.length() < 2){
            hex = "0" + hex;
        }
        return hex;
    }
    //byte[]字节数组转hex String
    public String bytesToHex(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<bytes.length;i++){
            String hex = Integer.toHexString(bytes[i] & 0xff);
            if(hex.length()<2){
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    //字节数组转int

    //hex字符串转byte
    public byte hexToByte(String inHex){
        return (byte)Integer.parseInt(inHex,16);
    }
    //hex字符串转byte数组
    public  byte[] hexToByteArray(String inHex){
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1){
            //奇数
            hexlen++;
            result = new byte[(hexlen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexlen/2)];
        }
        int j=0;
        for (int i = 0; i < hexlen; i+=2){
            result[j]=hexToByte(inHex.substring(i,i+2));
            j++;
        }
        return result;
    }
    //校验码
    public byte checkCode(byte[] bytes,int len){
        byte temp = 0;
        for(short i=0;i<len;i++){
            temp =(byte)(temp^bytes[i]);
        }
        return temp;
    }

}
