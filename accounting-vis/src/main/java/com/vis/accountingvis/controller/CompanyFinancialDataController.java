package com.vis.accountingvis.controller;

import com.vis.accountingvis.entity.CompanyFinancialData;
import com.vis.accountingvis.mapper.CompanyFinancialDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company-financial-data")
public class CompanyFinancialDataController {
    private final CompanyFinancialDataMapper companyFinancialDataMapper;

    @Autowired
    public CompanyFinancialDataController(CompanyFinancialDataMapper companyFinancialDataMapper) {
        this.companyFinancialDataMapper = companyFinancialDataMapper;
    }

    @GetMapping
    public List<CompanyFinancialData> getCompanyFinancialData() {
        return companyFinancialDataMapper.selectList(null);
    }

}
