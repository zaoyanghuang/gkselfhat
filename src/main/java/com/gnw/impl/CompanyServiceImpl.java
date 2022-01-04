package com.gnw.impl;

import com.gnw.mapper.CompanyMapper;
import com.gnw.pojo.Company;
import com.gnw.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Override
    public void insertCompany(Company company) {
        companyMapper.insertCompany(company);
    }

    @Override
    public void selAllCompany() {
        companyMapper.selAllCompany();
    }

    @Override
    public void updateCompany(Company company,String orgCompanyName) {
        companyMapper.updateCompany(company,orgCompanyName);
    }

    @Override
    public void deleteCompany(String companyName) {
        companyMapper.deleteCompany(companyName);
    }
}
