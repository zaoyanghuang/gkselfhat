package com.gnw.mapper;

import com.gnw.pojo.Company;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper {
    public void insertCompany(Company company);
    public void selAllCompany();
    public void updateCompany(Company company,String orgCompanyName);
    public void deleteCompany(String companyName);
}
