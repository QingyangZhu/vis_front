package com.vis.accountingvis.controller;

import com.vis.accountingvis.entity.Company;
import com.vis.accountingvis.mapper.CompanyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyController(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }
    // 获取所有公司
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyMapper.selectList(null);
    }
    // 根据名称查询公司
    @GetMapping("/search")
    public Company getCompanyByName(@RequestParam String name) {
        return companyMapper.selectCompanyByName(name);
    }
}
