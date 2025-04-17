package com.vis.accountingvis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;

@TableName("company_financial_data")
public class CompanyFinancialData {

    @TableId(value = "Data_ID", type = IdType.AUTO)
    private Integer dataId;

    @TableField("Company_ID")
    private Integer companyId;

    @TableField("Year")
    private Integer year;

    @TableField("Indicator_ID")
    private Integer indicatorId;

    @TableField("Financial_Data_Value")
    private BigDecimal financialDataValue;

    // Getters & Setters
    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    public BigDecimal getFinancialDataValue() {
        return financialDataValue;
    }

    public void setFinancialDataValue(BigDecimal financialDataValue) {
        this.financialDataValue = financialDataValue;
    }
}