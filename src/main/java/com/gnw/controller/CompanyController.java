package com.gnw.controller;

import com.gnw.impl.CompanyServiceImpl;
import com.gnw.pojo.Company;
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
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    /*新增公司信息*/
    @RequestMapping("/insertcompay")
    @ResponseBody
    public Map<String,Object> insertCompany(@RequestParam(value = "companyName")String companyName,
                                            @RequestParam(value = "companyLocation")String companyLocation){
        Map<String,Object> map = new HashMap<>();
        Company company = new Company();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.format(date);
        Timestamp createTime = new Timestamp(date.getTime());
        company.setCompanyName(companyName);
        company.setCompanyLocation(companyLocation);
        company.setCreateTime(createTime);
        try{
            companyServiceImpl.insertCompany(company);
            map.put("Success","insert success");
        }catch (Exception e){
            map.put("Error", "insert Error:"+e.getMessage());
            return map;
        }
        return map;
    }
    /*查询所有公司信息
    * 仅服务商可以查询*/
    @RequestMapping("/selallcompany")
    @ResponseBody
    public Map<String,Object> selAllCompany(){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userRoleAuthority.getUserRole() != "adminServer"){
            map.put("Error", "no permission");
            return map;
        }
        try{
            companyServiceImpl.selAllCompany();
            map.put("Success","sel success");
        }catch (Exception e){
            map.put("Error", "sel Error:"+e.getMessage());
            return map;
        }
        return map;
    }
    /*更新公司信息*/
    @RequestMapping("/updatecompany")
    @ResponseBody
    public Map<String,Object> updateCompany(@RequestParam(value = "companyName")String companyName,
                                            @RequestParam(value = "orgCompanyName")String orgCompanyName,
                                            @RequestParam(value = "companyLocation")String companyLocation){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userRoleAuthority.getUserRole() != "adminServer"){
            map.put("Error", "no permission");
            return map;
        }
        Company company = new Company();
        company.setCompanyName(companyName);
        company.setCompanyLocation(companyLocation);
        try{
            companyServiceImpl.updateCompany(company,orgCompanyName);
            map.put("Success","update success");
        }catch (Exception e){
            map.put("Error", "update Error:"+e.getMessage());
            return map;
        }
        return map;
    }
    /*删除公司信息  依然保留公司信息 只是更改状态为0  注销*/
    @RequestMapping("/deletecompany")
    @ResponseBody
    public Map<String,Object> deleteCompany(@RequestParam(value = "companyName")String companyName){
        Map<String,Object> map = new HashMap<>();
        UserRoleAuthority userRoleAuthority = (UserRoleAuthority)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userRoleAuthority.getUserRole() != "adminServer"){
            map.put("Error", "no permission");
            return map;
        }
        try{
            companyServiceImpl.deleteCompany(companyName);
            map.put("Success","delete success");
        }catch (Exception e){
            map.put("Error", "delete Error:"+e.getMessage());
            return map;
        }
        return map;
    }


}
