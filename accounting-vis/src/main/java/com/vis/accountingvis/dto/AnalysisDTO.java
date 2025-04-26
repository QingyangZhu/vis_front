package com.vis.accountingvis.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

//同一指标下，不同公司在不同的年份的指标表现
@Setter
@Getter
public class AnalysisDTO {
    private String companyName;
    private Integer year;
    private String indicatorName;
    private BigDecimal value;

}