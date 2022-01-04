package com.gnw.service;

import com.gnw.pojo.Company;

import java.util.Map;

public interface CompanyService {
    public void insertCompany(Company company);
    public void selAllCompany();
    public void updateCompany(Company company,String orgCompanyName);
    public void deleteCompany(String companyName);
}
