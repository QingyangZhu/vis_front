package com.vis.accountingvis.controller;

import com.vis.accountingvis.dto.CompanyMetricsDTO;
import com.vis.accountingvis.mapper.CompanyMetricsDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company-metrics")
public class CompanyMetricsController {
    private final CompanyMetricsDTOMapper companyMetricsDTOMapper;

    @Autowired
    public CompanyMetricsController(CompanyMetricsDTOMapper companyMetricsDTOMapper) {
        this.companyMetricsDTOMapper = companyMetricsDTOMapper;
    }

    @GetMapping("/search")
    public List<CompanyMetricsDTO> getCompanyMetricsByName(@RequestParam String name) {
        return companyMetricsDTOMapper.getCompanyMetricsByCompanyName(name);
    }
}
