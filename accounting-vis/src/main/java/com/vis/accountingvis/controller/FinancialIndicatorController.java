package com.vis.accountingvis.controller;

import com.vis.accountingvis.entity.FinancialIndicator;
import com.vis.accountingvis.mapper.FinancialIndicatorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/indicators")
public class FinancialIndicatorController {

    private final FinancialIndicatorMapper financialIndicatorMapper;

    @Autowired
    public FinancialIndicatorController(FinancialIndicatorMapper financialIndicatorMapper) {
        this.financialIndicatorMapper = financialIndicatorMapper;
    }

    @GetMapping
    public List<FinancialIndicator> getFinancialIndicators() {
        return financialIndicatorMapper.selectList(null);
    }

}
