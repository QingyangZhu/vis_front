package com.vis.accountingvis.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CompanyMetricsDTO {
    private String companyName;
    private Integer year;
    private BigDecimal peRatio;      // 市盈率
    private BigDecimal pbRatio;      // 市净率
    private BigDecimal psRatio;      // 市销率
    private BigDecimal dividendYield; // 股息率
}