package com.gnw.controller;

import com.gnw.impl.UserServiceImpl;
import com.gnw.pojo.RoleAuthority;
import com.gnw.pojo.UserInfo;
import com.gnw.pojo.UserRoleAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    /*通过权限和公司名称查询所有账号  分页查询
    * 权限admin可以查看所有账号
    * 其他权限仅可以查看本公司账号*/
    @RequestMapping("/selallbycompany")
    @ResponseBody
    public Map<String,Object> selAllByCompany(@RequestParam(value = "pageNum")int pageNum,@RequestParam(value = "lineNum")int lineNum){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = userRoleAuthority.getUserRole();
        String companyBelong = userRoleAuthority.getCompanyBelong();
        List<UserRoleAuthority> userRoleAuthorities = userServiceImpl.selAllByCompany(userRole, companyBelong, pageNum, lineNum);
        map.put("userRoleAuthorities",userRoleAuthorities);
        return map;
    }
    /*通过userName模糊查找*/
    @RequestMapping("/selbyusername")
    @ResponseBody
    public Map<String,Object> selByUserName(@RequestParam(value = "userName")String userName){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = userRoleAuthority.getUserRole();
        String companyBelong = userRoleAuthority.getCompanyBelong();
        UserRoleAuthority userRoleAuthority1 = userServiceImpl.selByUserName(userRole, companyBelong, userName);
        map.put("userRoleAuthority",userRoleAuthority1);
        return map;
    }
    /*查询所有公司名字*/
    @RequestMapping("/selallcompany")
    @ResponseBody
    public Map<String,Object> selAllCompany(){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userRole = userRoleAuthority.getUserRole();
        if(userRole == "adminServer"){
            List<String> companyBelongList = userServiceImpl.selAllCompany();
            map.put("companyBelongList",companyBelongList);
        }else{
            map.put("Error", "no permission");
            return map;
        }
        return map;
    }
    /*更新用户信息
    * adminServer可以更新任何账号
    * 其他可以更新本公司账号
    * 改接口权限控制在角色权限里  不再另外判断*/
    @RequestMapping("/updateuserinfo")
    @ResponseBody
    public Map<String,Object> updateUserInfo(@RequestParam(value = "passWords")String passWords,
                                             @RequestParam(value ="realName")String realName,
                                             @RequestParam(value ="phoneNumber")String phoneNumber,
                                             @RequestParam(value ="companyBelong")String companyBelong,
                                             @RequestParam(value ="authority")String authority,
                                             @RequestParam(value = "userName")String userName){
        Map<String,Object> map = new HashMap<>();
        try{
            int n =userServiceImpl.updateUserInfo(passWords,realName,phoneNumber,companyBelong,authority,userName);
            map.put("updateNum",n);
            map.put("Result", "success");
        }catch (Exception e){
            map.put("Error","异常码："+e.getMessage());
            return map;
        }
        return map;
    }
    /*添加账号
    * 仅角色adminServer有权添加登录账号
    * 添加的账号根据公司名称分配角色：客户公司分配角色(userName+Client)对应唯一权限 服务商分配角色（adminServer）对应
    * 默认密码666666*/
    @RequestMapping("/insertuserinfo")
    @ResponseBody
    public Map<String,Object> insertUserInfo(@RequestParam(value = "userName")String userName,
                                             @RequestParam(value ="realName")String realName,
                                             @RequestParam(value ="phoneNumber")String phoneNumber,
                                             @RequestParam(value ="companyBelong")String companyBelong,
                                             @RequestParam(value ="authority")String authority){
        Map<String,Object> map = new HashMap<>();
        if(userName==null || userName.equals("") || companyBelong==null || companyBelong.equals("")||authority==null || authority.equals("")){
            map.put("Error","userName,companyBelong,authority not null");
            return map;
        }
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(currentDate);
        Timestamp createTime = new Timestamp(currentDate.getTime());
        UserInfo userInfo = new UserInfo();
        RoleAuthority roleAuthority = new RoleAuthority();
        userInfo.setCompanyBelong(companyBelong);
        userInfo.setUserName(userName);
        userInfo.setPasswords("666666");
        userInfo.setRealName(realName);
        userInfo.setPhoneNumber(phoneNumber);
        userInfo.setCreatTime(createTime);
        if(companyBelong=="湖南格纳微信息科技有限公司"){
            userInfo.setUserRole("adminServer");
        }else{
            userInfo.setUserRole(userName+"Client");
            roleAuthority.setUserRole(userName+"Client");
            roleAuthority.setAuthority(authority);
        }
        String result = userServiceImpl.insertUserInfo(userInfo, roleAuthority);
        map.put("Result", result);
        return map;
    }


}
