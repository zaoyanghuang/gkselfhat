package com.gnw.controller;


import com.gnw.impl.IndoorMapServiceImpl;
import com.gnw.mapper.IndoorMapMapper;
import com.gnw.pojo.IndoorMap;
import com.gnw.pojo.UserDevice;
import com.gnw.pojo.UserRoleAuthority;
import com.gnw.util.UploadFileImagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/indoormap")
public class IndoorMapController {
    @Autowired
    private IndoorMapServiceImpl indoorMapServiceImpl;
    @Autowired
    private UploadFileImagesUtil uploadFileImagesUtil;
    @Value("${file.absolutePath}")
    private String absolutePath;
    /*新增室内地图
    * 室内地图图片存放在服务器地址下  返回访问地址存入数据库*/
    @RequestMapping("/insertindoormap")
    @ResponseBody
    public Map<String, Object> insertIndoorMap(@RequestParam(value = "txtFile") MultipartFile txtFile) {
        Map<String, Object> map = new HashMap<>();
        IndoorMap indoorMap = new IndoorMap();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = userRoleAuthority.getUserRole();
        if(userRole.equals("adminServer")){
            map.put("Error","no permission");
            return map;
        }
        try {
            uploadFileImagesUtil.uploadFile(absolutePath,txtFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            indoorMapServiceImpl.insertIndoorMap(indoorMap);
            map.put("Success", "insert success");
        } catch (Exception e) {
            map.put("Fail", "insert Exception");
            return map;
        }
        return map;
    }
    /*删除室内地图
    * 把室内地图useState改为0  默认1*/
    @RequestMapping("/deleteindoormap")
    @ResponseBody
    public Map<String, Object> deleteIndoorMap(@RequestParam(value = "mapName") String mapName) {
        Map<String, Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = userRoleAuthority.getUserRole();
        if(userRole.equals("adminServer")){
            map.put("Error","no permission");
            return map;
        }
        try {
            indoorMapServiceImpl.deleteIndoorMap(mapName);
            map.put("Success", "delete success");
        } catch (Exception e) {
            map.put("Fail", "delete Exception");
            return map;
        }
        return map;
    }
    /*查询室内地图  仅adminServer有权限
    * 模糊查询参数为空则查询全部
    * 参数非空则按照参数查询*/
    @RequestMapping("/selindoormap")
    @ResponseBody
    public Map<String, Object> selIndoorMap(@RequestParam(value = "mapName") String mapName) {
        Map<String, Object> map = new HashMap<>();
        List<IndoorMap> indoorMapList = new ArrayList<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = userRoleAuthority.getUserRole();
        if(userRole.equals("adminServer")){
            map.put("Error","no permission");
            return map;
        }
        try {
            indoorMapList = indoorMapServiceImpl.selIndoorMap(mapName);
            map.put("indoorMapList", indoorMapList);
            map.put("Success", "sel success");
        } catch (Exception e) {
            map.put("Fail", "sel Exception");
            return map;
        }
        return map;
    }
}