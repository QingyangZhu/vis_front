package com.vis.accountingvis.controller;

import com.vis.accountingvis.dto.AnalysisDTO;
import com.vis.accountingvis.mapper.AnalysisDTOMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    private final AnalysisDTOMapper analysisDTOMapper;

    @Autowired
    public AnalysisController(AnalysisDTOMapper analysisDTOMapper) {
        this.analysisDTOMapper = analysisDTOMapper;
    }

    @GetMapping("/liquidity-ratio")
    public List<AnalysisDTO> getLiquidityRatioData() {
        return analysisDTOMapper.getFinancialAnalysis("流动比率");
    }

    @GetMapping("/current-ratio")
    public List<AnalysisDTO> getCurrentRatioData() {
        return analysisDTOMapper.getFinancialAnalysis("速动比率");
    }

    @GetMapping("/roe")
    public List<AnalysisDTO> getRoeData() {
        return analysisDTOMapper.getFinancialAnalysis("净资产收益率");
    }

    @GetMapping("/profit-margin")
    public List<AnalysisDTO> getProfitMarginData() {
        return analysisDTOMapper.getFinancialAnalysis("净利率");
    }

    @GetMapping("/roa")
    public List<AnalysisDTO> getRoaData() {
        return analysisDTOMapper.getFinancialAnalysis("总资产报酬率");
    }


}